package application.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import application.models.response.Common;
import application.models.response.Compra;
import application.models.response.PedidoProveedor;
import application.services.ReportePedidoProveedorService;
import application.utils.PrecioFormatter;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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

public class ReportePedidoProveedorController {
	
	// Elimina el "static" si no es necesario
	private static Integer Page = 0;

	// Usa un contenedor mutable para poder modificar el valor
	private static final Double[] valor = { null };


	
	private static void actualizarTabla(
		TableView<PedidoProveedor> tabla,
		ComboBox<String> cbEstado,
		ComboBox<String> cbMetodo,
		Double valorPedido,
		LocalDate fechaPedido,
	    LocalDate fechaEntrega,
	    Integer page
	) {
		 new Thread(() -> { // Usar hilo secundario para no congelar la UI
		        try {		     
		        	var metodo = cbMetodo.getValue() == null ? null : cbMetodo.getValue().replace(" ", "%20");
		        	ReportePedidoProveedorService service = new ReportePedidoProveedorService();
		            List<PedidoProveedor> nuevosPedidos = service.obtenerReportePedidoProveedor(
		            		fechaPedido, 
		            		fechaEntrega, 
		            		cbEstado.getValue(), 
		            		metodo,
		            		valorPedido, page).getContent();
	
		            System.out.println(nuevosPedidos);
		            Platform.runLater(() -> {
		                tabla.getItems().setAll(nuevosPedidos);
		                tabla.refresh();
		                });
	
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }).start();
		
	}
	
	@SuppressWarnings("unchecked")
	public static VBox getScene(List<PedidoProveedor> pedidos, List<Common> metodos) {
		
		Label lblTitulo = new Label("Reporte de Pedido asociado a Proveedor");
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
		cbEstado.setCellFactory(cb -> new ListCell<>() {
		    @Override
		    protected void updateItem(String item, boolean empty) {
		        super.updateItem(item, empty);
		        setText(empty || item == null ? null : item);
		    }
		});
		cbEstado.setButtonCell(new ListCell<>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? "Selecciona el estado" : item);
			}
		});
		
		Label lblMetodo= new Label("Metodo de pago:");
		lblMetodo.getStyleClass().add("filtros-label");
		
		ComboBox<String> cbMetodo = new ComboBox<>();
		cbMetodo.getItems().addAll(metodos.stream().map(p -> p.getNombre()).toList());
		cbMetodo.setPromptText("Selecciona el producto");
		cbMetodo.getStyleClass().add("filtros-combo");
		
		cbMetodo.setButtonCell(new ListCell<>() {
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
		filtrosPane.setHgap(25);
		filtrosPane.setVgap(10);
		filtrosPane.setPrefWrapLength(900); // Permite que los elementos se acomoden si el ancho es pequeño

		// Añadimos todos los filtros en orden horizontal
		filtrosPane.getChildren().addAll(
		    lblEstado, cbEstado,
		    lblMetodo, cbMetodo,
		    lblFechaPedido, dpFechaPedido,
		    lblFechaEntrega, dpFechaEntrega,
		
		    lblValorPedido, cbValorPedido
		);
        
        

        TableView<PedidoProveedor> table = new TableView<>();


        // Columna Fecha Pedido
        TableColumn<PedidoProveedor, String> colFechaPedido = new TableColumn<>("Fecha Pedido");
        colFechaPedido.setCellValueFactory(cellData -> {
            var fecha = cellData.getValue().getFechaPedido();
            String formatted = (fecha != null)
                    ? fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    : "";
            return new SimpleStringProperty(formatted);
        });

        // Columna Fecha Entrega
        TableColumn<PedidoProveedor, String> colFechaEntrega = new TableColumn<>("Fecha Entrega");
        colFechaEntrega.setCellValueFactory(cellData -> {
            var fecha = cellData.getValue().getFechaEntrega();
            String formatted = (fecha != null)
                    ? fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    : "";
            return new SimpleStringProperty(formatted);
        });

        // Columna Estado
        TableColumn<PedidoProveedor, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Columna Proveedor
        TableColumn<PedidoProveedor, String> colProveedor = new TableColumn<>("Proveedor");
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));

        // Columna Contacto Proveedor
        TableColumn<PedidoProveedor, String> colContacto = new TableColumn<>("Contacto");
        colContacto.setCellValueFactory(new PropertyValueFactory<>("contactoProveedor"));   

        // Columna Referencia de Pago
        TableColumn<PedidoProveedor, String> colReferencia = new TableColumn<>("Referencia Pago");
        colReferencia.setCellValueFactory(cellData -> {
            String referencia = cellData.getValue().getReferenciaPago();
            return new SimpleStringProperty(
                referencia != null ? referencia : "Sin referencia"
            );
        });

        
        // Columna Método de Pago
        TableColumn<PedidoProveedor, String> colMetodo = new TableColumn<>("Método Pago");
        colMetodo.setCellValueFactory(new PropertyValueFactory<>("metodoPago"));

        // Columna Número de Cuenta
        TableColumn<PedidoProveedor, String> colCuenta = new TableColumn<>("N° Cuenta");
        colCuenta.setCellValueFactory(new PropertyValueFactory<>("nuemroCuenta"));

        // Columna Tipo de Cuenta
        TableColumn<PedidoProveedor, String> colTipoCuenta = new TableColumn<>("Tipo Cuenta");
        colTipoCuenta.setCellValueFactory(new PropertyValueFactory<>("tipoCuenta"));

        // Columna Entidad Bancaria
        TableColumn<PedidoProveedor, String> colEntidad = new TableColumn<>("Entidad Bancaria");
        colEntidad.setCellValueFactory(new PropertyValueFactory<>("entidadBancaria"));

        // Columna Valor del Pedido
        TableColumn<PedidoProveedor, Double> colValor = new TableColumn<>("Valor Pedido");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorPedido"));

        colValor.setCellFactory(column -> new TableCell<PedidoProveedor, Double>() {
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

        // Agregar columnas a la tabla
        table.getColumns().addAll(
                colFechaPedido, colFechaEntrega, colEstado, colProveedor, colContacto,
                colReferencia, colMetodo, colCuenta, colTipoCuenta, colEntidad, colValor
        );

        // Convertir lista en ObservableList
        ObservableList<PedidoProveedor> data = FXCollections.observableArrayList(pedidos);
        table.setItems(data);
        
        Runnable actualizar = () -> {
        	actualizarTabla(
        			table, 
        			cbEstado, 
        			cbMetodo, 
        			valor[0],
        			dpFechaPedido.getValue(), 
        			dpFechaEntrega.getValue(), 
        			Page);
        };
        
       
        
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
            cbEstado.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? "Selecciona el estado" : item);
                }
            });

            // Producto
            cbMetodo.getSelectionModel().clearSelection();
            cbMetodo.setValue(null);
            cbMetodo.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? "Selecciona el producto" : item);
                }
            });

            // Valor
            cbValorPedido.getSelectionModel().clearSelection();
            cbValorPedido.setValue(null);
            cbValorPedido.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? "Seleccione el rango del precio" : item);
                }
            });

           
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
        cbMetodo.valueProperty().addListener((obs, oldVal, newVal) -> actualizar.run());      
        dpFechaPedido.valueProperty().addListener((obs, oldVal, newVal) -> actualizar.run());
        dpFechaEntrega.valueProperty().addListener((obs, oldVal, newVal) -> actualizar.run());

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

        

        // Ajustes visuales
        table.setPadding(new Insets(10, 10, 10, 10));
        VBox layout = new VBox(10, lblTitulo, headerFiltros,filtrosPane, table);
        layout.setAlignment(Pos.TOP_CENTER);
        return layout;
    }
}
