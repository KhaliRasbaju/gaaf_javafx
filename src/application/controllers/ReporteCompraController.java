package application.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import application.models.response.Compra;
import application.models.response.Producto;
import application.services.ReporteCompraService;
import application.utils.PrecioFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ReporteCompraController {
	
	// Elimina el "static" si no es necesario
	private static Integer Page = 0;

	// Usa un contenedor mutable para poder modificar el valor
	private static final Double[] valor = { null };

	private static Integer cantidad = null;
	
	private static void actualizarTabla(
		TableView<Compra> tabla,
		ComboBox<String> cbEstado,
		ComboBox<String> cbProducto,
		Integer cantidad,
		Double valorPedido,
		LocalDate fechaPedido,
	    LocalDate fechaEntrega,
	    Integer page
	) {
		 new Thread(() -> { // Usar hilo secundario para no congelar la UI
		        try {
		        	
		        	var producto = cbProducto.getValue() == null ? null : cbProducto.getValue().replace(" ", "%20");
		        	ReporteCompraService service = new ReporteCompraService();
		            List<Compra> nuevasCompras = service.obtenerReporteCompras(
		            		fechaPedido, 
		            		fechaEntrega, 
		            		cbEstado.getValue(), 
		            		producto,
		            		cantidad,
		            		valorPedido, page).getContent();
	
		            System.out.println(nuevasCompras);
		            javafx.application.Platform.runLater(() -> {
		                tabla.getItems().setAll(nuevasCompras);
		                tabla.refresh();
		                });
	
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }).start();
		
	}

    @SuppressWarnings("unchecked")
	public static VBox getScene(List<Compra> reportes, List<Producto> productos) {

    	
    	Label lblTitulo = new Label("Reporte compras");
    	lblTitulo.getStyleClass().add("form-title");
    	
		
		
		Label lblFiltros = new Label("Filtros");
		lblFiltros.getStyleClass().add("filtros-subtitle");
		
		Button btnRestablecer = new Button("Restablecer");
		btnRestablecer.getStyleClass().add("filtros-reset-button");

		
		Label lblEstado = new Label("Estado:");
		lblEstado.getStyleClass().add("filtros-label");
		
		ComboBox<String> cbEstado = new ComboBox<>();
		cbEstado.getItems().addAll("PENDIENTE", "RECIBIDO");
		cbEstado.setPromptText("Selecciona el estado");
		cbEstado.getStyleClass().add("filtros-combo");
		cbEstado.setButtonCell(new ListCell<>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? "Selecciona el estado" : item);
			}
		});
		
		Label lblProducto = new Label("Producto:");
		lblProducto.getStyleClass().add("filtros-label");
		
		ComboBox<String> cbProducto = new ComboBox<>();
		cbProducto.getItems().addAll(productos.stream().map(p -> p.getNombre()).toList());
		cbProducto.setPromptText("Selecciona el producto");
		cbProducto.getStyleClass().add("filtros-combo");
		
		cbProducto.setButtonCell(new ListCell<>() {
		    @Override
		    protected void updateItem(String item, boolean empty) {
		        super.updateItem(item, empty);
		        setText(empty || item == null ? "Selecciona el producto" : item);
		    }
		});

		
		Label lblFechaPedido = new Label("Fecha Pedido:");
		lblFechaPedido.getStyleClass().add("filtros-label");
		
		DatePicker dpFechaPedido = new DatePicker();
		dpFechaPedido.setPromptText("Fecha del pedido:");
		dpFechaPedido.getStyleClass().add("filtros-date");
		
		
		Label lblFechaEntrega = new Label("Fecha Entrega:");
		lblFechaEntrega.getStyleClass().add("filtros-label");
		
		DatePicker dpFechaEntrega = new DatePicker();
		dpFechaEntrega.setPromptText("Fecha de la entrega");
		dpFechaEntrega.getStyleClass().add("filtros-date");
		
		Label lblCantidad = new Label("Cantidad:");
		lblCantidad.getStyleClass().add("filtros-label");
		
		TextField txtCantidad = new TextField();
	    txtCantidad.setPromptText("Cantidad");
	    txtCantidad.getStyleClass().add("filtros-textfield");
	    
	    Label lblValorPedido = new Label("Valor del pedido:");
	    lblValorPedido.getStyleClass().add("filtros-label");
		
		ComboBox<String> cbValorPedido = new ComboBox<>();
		cbValorPedido.getItems().addAll("$ 500.000 (COP)","$ 1'500.000 (COP)", "$ 2'200.000 (COP)", "MAX");
		cbValorPedido.setPromptText("Seleccione el rango del precio");
		cbValorPedido.getStyleClass().add("filtros-combo");
		
		
		cbValorPedido.setButtonCell(new ListCell<>() {
		    @Override
		    protected void updateItem(String item, boolean empty) {
		        super.updateItem(item, empty);
		        setText(empty || item == null ? "Seleccione el rango del precio" : item);
		    }
		});
	
		

		
		
		FlowPane filtrosPane = new FlowPane();
		filtrosPane.getStyleClass().add("filtros-container");
		filtrosPane.setHgap(10);
		filtrosPane.setVgap(25);
		filtrosPane.setPrefWrapLength(1250); 

		// Añadimos todos los filtros en orden horizontal
		filtrosPane.getChildren().addAll(
		    lblEstado, cbEstado,
		    lblProducto, cbProducto,
		    lblFechaPedido, dpFechaPedido,
		    lblFechaEntrega, dpFechaEntrega,
		    lblCantidad, txtCantidad,
		    lblValorPedido, cbValorPedido
		);
        
        
        
        TableView<Compra> table = new TableView<>();



        TableColumn<Compra, String> colProveedor = new TableColumn<>("Proveedor");
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));

        TableColumn<Compra, String> colContacto = new TableColumn<>("Contacto");
        colContacto.setCellValueFactory(new PropertyValueFactory<>("contactoProveedor"));

        TableColumn<Compra, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoProveedor"));

        TableColumn<Compra, String> colProducto = new TableColumn<>("Producto");
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));

        TableColumn<Compra, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<Compra, Float> colPeso = new TableColumn<>("Peso (kg)");
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));

        TableColumn<Compra, Double> colValor = new TableColumn<>("Valor Pedido");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorPedido"));
        
        colValor.setCellFactory(column -> new TableCell<Compra, Double>() {
            @Override
            protected void updateItem(Double valor, boolean empty) {
                super.updateItem(valor, empty);
                if (empty || valor == null) {
                    setText(null);
                } else {
                    setText(PrecioFormatter.formatearPrecio(valor));
                }
            }
        });

        TableColumn<Compra, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        TableColumn<Compra, Object> colFechaPedido = new TableColumn<>("Fecha Pedido");
        colFechaPedido.setCellValueFactory(new PropertyValueFactory<>("fechaPedido"));

        TableColumn<Compra, Object> colFechaEntrega = new TableColumn<>("Fecha Entrega");
        colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));

        // === Formatear fechas ===
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        colFechaPedido.setCellFactory(column -> new TableCell<Compra, Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                    setText(null);
                else
                    setText(formatter.format(((java.time.LocalDate) item)));
            }
        });
        colFechaEntrega.setCellFactory(column -> new TableCell<Compra, Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                    setText(null);
                else
                    setText(formatter.format(((java.time.LocalDate) item)));
            }
        });

        // === Agregar columnas a la tabla ===
        table.getColumns().addAll(
               colProveedor, colContacto, colTelefono,
                colProducto, colCantidad, colPeso, colValor,
                colEstado, colFechaPedido, colFechaEntrega
        );

        // === Configurar datos ===
        ObservableList<Compra> data = FXCollections.observableArrayList(reportes);
        table.setItems(data);
        
        
        if (!txtCantidad.getText().isEmpty()) {
            cantidad = Integer.parseInt(txtCantidad.getText());
        }
       
        
        
        
        
        Runnable actualizar = () -> {
        	actualizarTabla(
        			table, 
        			cbEstado, 
        			cbProducto, 
        			cantidad, 
        			valor[0],
        			dpFechaPedido.getValue(), 
        			dpFechaEntrega.getValue(), 
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

        
        cbValorPedido.valueProperty().addListener((obs, oldVal, newVal) -> {
		    switch (newVal) {
		        case "$ 1'500.000 (COP)":     
		        	valor[0] = 1500000.0; 
		        	break;
		        case "$ 2'200.000 (COP)":     
		        	valor[0] = 1500000.0;  
		        	break;
		        case "$ 500.000 (COP)":
		        	valor[0] = 500000.0; 
		        	break;
		        case "MAX":
		            valor[0] = null; 
		            break;
		    }
		    actualizar.run();
		});
        
        btnRestablecer.setOnAction((e) -> {
            // Estado
            cbEstado.getSelectionModel().clearSelection();
            cbEstado.setValue(null);
            cbEstado.setButtonCell(new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? "Selecciona el estado" : item);
                }
            });

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

            // Valor
            cbValorPedido.getSelectionModel().clearSelection();
            cbValorPedido.setValue(null);
            cbValorPedido.setButtonCell(new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? "Seleccione el rango del precio" : item);
                }
            });

            // Cantidad
            txtCantidad.clear();
            txtCantidad.setPromptText("Cantidad");
            cantidad = null;

            // Fechas
            dpFechaPedido.getEditor().clear();
            dpFechaEntrega.getEditor().clear();
            dpFechaPedido.setValue(null);
            dpFechaEntrega.setValue(null);
            dpFechaPedido.setPromptText("Fecha del pedido");
            dpFechaEntrega.setPromptText("Fecha de la entrega");

            // Valor interno
            valor[0] = null;

            // Quitar foco para mostrar promptText
            table.requestFocus();

            // Actualizar tabla
            actualizar.run();
        });

        cbEstado.valueProperty().addListener((obs, oldVal, newVal) -> actualizar.run());
        cbProducto.valueProperty().addListener((obs, oldVal, newVal) -> actualizar.run());
        txtCantidad.textProperty().addListener((obs, oldVal, newVal) -> actualizar.run());
        dpFechaPedido.valueProperty().addListener((obs, oldVal, newVal) -> actualizar.run());
        dpFechaEntrega.valueProperty().addListener((obs, oldVal, newVal) -> actualizar.run());

        // === Ajustes visuales ===
        table.setPadding(new Insets(10));
        table.setStyle("-fx-font-size: 13px;");
        
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

        VBox layout = new VBox(10, lblTitulo, headerFiltros,filtrosPane, table);
        layout.setAlignment(Pos.TOP_CENTER);
        return layout;
    }
}
