package application.controllers;

import java.time.LocalDate;
import java.util.List;

import application.models.response.Compra;
import application.models.response.InventarioProductoBodega;
import application.models.response.Producto;
import application.models.response.ReporteInventarioProductoBodega;
import application.services.ReporteCompraService;
import application.services.ReporteInventarioProductoBodegaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ReporteInventarioController {
	
	// ================================
    //      VARIABLES ESTÁTICAS
    // ================================
	private static Integer Page = 0;
	private static Integer cantidad = null;

    // ============================================
    //     METODO PARA ACTUALIZAR LA TABLA
    // ============================================
	private static void actualizarTabla(
			TableView<InventarioProductoBodega> tabla,
			ComboBox<String> cbProducto,
			Integer cantidad,		
		    LocalDate fecha,
		    Integer page
		) {
			 new Thread(() -> { // Usar hilo secundario para no congelar la UI
			        try {
			        	
			        	var producto = cbProducto.getValue() == null ? null : cbProducto.getValue().replace(" ", "%20");
			        	ReporteInventarioProductoBodegaService service = new ReporteInventarioProductoBodegaService();
			            List<InventarioProductoBodega> nuevoInventario = service.obtenerReporteReporteInventarioProductoBodega(
			            		producto,
			            		cantidad,
			            		fecha, 
			            		page).getContent();
		
			            System.out.println(nuevoInventario);
			            javafx.application.Platform.runLater(() -> {
			                tabla.getItems().setAll(nuevoInventario);
			                tabla.refresh();
			                });
		
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			    }).start();
			
		}

	// ================================
    //     VISTA PRINCIPAL
    // ================================
	public static VBox getScene(List<InventarioProductoBodega> reportes, List<Producto> productos) {

		Label lblTitulo = new Label("Reporte del Inventario");
		lblTitulo.getStyleClass().add("form-title");
		
		Label lblFiltros = new Label("Filtros");
		lblFiltros.getStyleClass().add("filtros-subtitle");
		
		
		Button btnRestablecer = new Button("Restablecer");
		btnRestablecer.getStyleClass().add("filtros-reset-button");
		
		
		Label lblFecha = new Label("Fecha:");
		lblFecha.getStyleClass().add("filtros-label");
		
		DatePicker dpFecha = new DatePicker();
		dpFecha.setPromptText("Selecciona la fecha:");
		dpFecha.getStyleClass().add("filtros-date");
		
		
		Label lblCantidad = new Label("Cantidad:");
		lblCantidad.getStyleClass().add("filtros-label");
		
		TextField txtCantidad = new TextField();
	    txtCantidad.setPromptText("Cantidad");
	    txtCantidad.getStyleClass().add("filtros-textfield");
	    txtCantidad.setTextFormatter(new TextFormatter<>(change -> {
	        String nuevo = change.getControlNewText();
	        if (!nuevo.matches("\\d*")) {
	            return null;
	        }	  
	        
	        if (nuevo.length() > 9) {
	            return null;
	        }
	        return change;
	    }));
	    
	    
	    Label lblProducto = new Label("Producto:");
		lblProducto.getStyleClass().add("filtros-label");
		
		ComboBox<String> cbProducto = new ComboBox<>();
		cbProducto.getItems().addAll(productos.stream().map(p -> p.getNombre()).toList());
		cbProducto.setPromptText("Selecciona el producto");
		cbProducto.getStyleClass().add("filtros-combo");
		
		cbProducto.setButtonCell(new javafx.scene.control.ListCell<>() {
		    @Override
		    protected void updateItem(String item, boolean empty) {
		        super.updateItem(item, empty);
		        setText(empty || item == null ? "Selecciona el producto" : item);
		    }
		});
		
		FlowPane filtrosPane = new FlowPane();
		filtrosPane.getStyleClass().add("filtros-container");
		filtrosPane.setHgap(10);
		filtrosPane.setVgap(25);
		filtrosPane.setPrefWrapLength(900);
		
		filtrosPane.getChildren().addAll(			  
			    lblProducto, cbProducto,			 
			    lblFecha, dpFecha,
			    lblCantidad, txtCantidad
			);
		
		
	    TableView<InventarioProductoBodega> table = new TableView<>();
	
	    // Columna Bodega
	    TableColumn<InventarioProductoBodega, String> colBodega = new TableColumn<>("Bodega");
	    colBodega.setCellValueFactory(new PropertyValueFactory<>("bodega"));
	
	    // Columna Producto
	    TableColumn<InventarioProductoBodega, String> colProducto = new TableColumn<>("Producto");
	    colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
	
	    // Columna Cantidad
	    TableColumn<InventarioProductoBodega, Integer> colCantidad = new TableColumn<>("Cantidad");
	    colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
	
	    // Columna Fecha
	    TableColumn<InventarioProductoBodega, String> colFecha = new TableColumn<>("Fecha");
	    colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
	
	    // Agregar columnas a la tabla
	    table.getColumns().addAll(colBodega, colProducto, colCantidad, colFecha);
	
	    // Convertir lista a ObservableList
	    ObservableList<InventarioProductoBodega> data = FXCollections.observableArrayList(reportes);
	    table.setItems(data);
	    if (!txtCantidad.getText().isEmpty()) {
            cantidad = Integer.parseInt(txtCantidad.getText());
        }
                                
        Runnable actualizar = () -> {
        	actualizarTabla(
        			table,         			
        			cbProducto, 
        			cantidad, 
        			dpFecha.getValue(),
        			Page);
        };
        
        txtCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                cantidad = null;
            } else {
                try {
                    cantidad = Integer.parseInt(newValue);
                } catch (NumberFormatException ex) {
                    cantidad = null; 
                }
            }
            actualizar.run();
        });

      
        btnRestablecer.setOnAction((e) -> {
            

            // Producto
            cbProducto.getSelectionModel().clearSelection();
            cbProducto.setValue(null);
            cbProducto.setButtonCell(new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? "Selecciona el producto" : item);
                }
            });

            
            // Cantidad
            txtCantidad.clear();
            txtCantidad.setPromptText("Cantidad");
            cantidad = null;

            // Fechas
            dpFecha.getEditor().clear();           
            dpFecha.setValue(null);           
            dpFecha.setPromptText("Fecha del pedido");
          

            // Quitar foco para mostrar promptText
            table.requestFocus();

            // Actualizar tabla
            actualizar.run();
        });

     
        cbProducto.valueProperty().addListener((obs, oldVal, newVal) -> actualizar.run());
        txtCantidad.textProperty().addListener((obs, oldVal, newVal) -> actualizar.run());
        dpFecha.valueProperty().addListener((obs, oldVal, newVal) -> actualizar.run());
      

	
	    // Padding y espaciado
	    table.setPadding(new Insets(10, 10, 10, 10));

	    HBox headerFiltros = new HBox(lblFiltros, btnRestablecer);
        headerFiltros.setAlignment(Pos.CENTER_LEFT);
        headerFiltros.setSpacing(10);
        HBox.setHgrow(btnRestablecer, Priority.ALWAYS);
        headerFiltros.setStyle("-fx-alignment: center-left; -fx-spacing: 10; -fx-padding: 5 10;");
        headerFiltros.setMaxWidth(Double.MAX_VALUE);

        // Distribución tipo justify-between
        headerFiltros.setSpacing(Region.USE_COMPUTED_SIZE);
        headerFiltros.setHgrow(lblFiltros, Priority.ALWAYS);
        headerFiltros.setStyle("-fx-alignment: center; -fx-padding: 5 10;");
        HBox.setHgrow(lblFiltros, Priority.ALWAYS);
        headerFiltros.setFillHeight(true);
        HBox.setHgrow(btnRestablecer, Priority.NEVER);
        headerFiltros.setStyle("-fx-alignment: center-left; -fx-spacing: 10; -fx-padding: 5 10;");
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        headerFiltros.getChildren().setAll(lblFiltros, spacer, btnRestablecer);
        
	    VBox layout = new VBox(10, lblTitulo, headerFiltros, filtrosPane, table);
        layout.setAlignment(Pos.TOP_CENTER);
        return layout;
    }
}
