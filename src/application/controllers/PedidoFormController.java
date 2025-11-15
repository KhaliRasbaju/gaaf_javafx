package application.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.models.request.DetallePedidoRequest;
import application.models.request.MedioPagoRequest;
import application.models.request.PedidoRequest;
import application.models.response.Common;
import application.models.response.Pedido;
import application.models.response.Proveedor;
import application.models.response.ResponseCommon;
import application.models.response.Producto;
import application.services.PedidoService;
import application.services.ProveedorService;
import application.utils.NotificationManager;
import application.utils.PrecioFormatter;
import application.services.ProductoService;
import application.services.MetodoPagoService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PedidoFormController {

    // ================================
    //     ATRIBUTOS ESTATICOS
    // ================================
    private static Long idMetodoPago;
    private static Long nitProveedor;
    private static Long idProductoSeleccionado;
    
    // ================================
    //     CONTENEDOR PRINCIPAL
    // ================================
    private final StackPane content;

    // ================================
    //     CONSTRUCTOR
    // ================================
    public PedidoFormController(StackPane content) {
        this.content = content;
    }


    // ================================
    //     LISTA DE DETALLES DEL PEDIDO
    // ================================
    private final ObservableList<DetallePedidoRequest> listaDetalles = FXCollections.observableArrayList();

   
    // ================================
    //     ACCIÓN: REGISTRAR PEDIDO
    // ================================
    private static ResponseCommon onActionRegistrar(PedidoRequest request) {
        try {
            PedidoService service = new PedidoService();
            return service.crearPedido(request);
        } catch (Exception ex) {
            throw new RuntimeException("Error tipo: " + ex);
        }
    }
  
    // ================================
    //     ACCIÓN: EDITAR PEDIDO
    // ================================
    private static ResponseCommon onActionEditar(Long idPedido, PedidoRequest request) {
		try {
			PedidoService service = new PedidoService();
			return service.editarPedido(idPedido, request);
		} catch (Exception ex) {
			throw new RuntimeException("Error tipo: " + ex);
		}
	}

    // ================================
    //     OBTENER LISTA DE PROVEEDORES
    // ================================
    private static List<Proveedor> proveedores() throws Exception {
        try {
        	ProveedorService service = new ProveedorService();
            return service.obtenerProveedores();
        } catch (Exception ex) 
        
        {
        	System.out.println("Error tipo: " + ex);
        	throw new Exception("Error tipo: " + ex);   
        }
    }
        
    
    // ================================
    //     OBTENER LISTA DE MÉTODOS DE PAGO
    // ================================
    private static List<Common> metodosPago() throws Exception {
        MetodoPagoService service = new MetodoPagoService();
        return service.obtenerMetodos();
    }

    // ================================
    //     OBTENER UN PRODUCTO POR ID
    // ================================
    private static Producto producto(Long id) throws Exception {
        ProductoService service = new ProductoService();
        return service.obtenerProducto(id);
    }

    // ================================
    //     OBTENER LISTA DE PRODUCTOS
    // ================================
    private static List<Producto> productos() throws Exception {
        try {
        	ProductoService service = new ProductoService();
            return service.obtenerProductos();
        } catch (Exception ex) 
        
        {
        	System.out.println("Error tipo: " + ex);
        	throw new Exception("Error tipo: " + ex);   
        }
    }

    // ================================
    //     CREACIÓN DE LA VISTA
    // ================================
    public ScrollPane getScene(String title, Pedido pedido) throws Exception {
    	
    	
    	

        Text titulo = new Text(String.format("📦 %s Pedido", title));
        
        

        // Combobox proveedor
        ComboBox<String> cbProveedor = new ComboBox<>();
        var listaProveedores = proveedores();
        for (Proveedor p : listaProveedores) {
            cbProveedor.getItems().add(p.getNombre() + " (" + p.getNit() + ")");
        }
        cbProveedor.setPromptText("Selecciona un proveedor");

        // Campos básicos
        TextField txtValor = new TextField();
        txtValor.setPromptText("Valor total del pedido");
        txtValor.setTextFormatter(new TextFormatter<>(change -> {
        	if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
        
        PrecioFormatter.aplicarFormato(txtValor);

        DatePicker dpFechaPedido = new DatePicker(LocalDate.now());
        dpFechaPedido.setPromptText("Fecha del pedido");

        // Combobox método de pago
        ComboBox<String> cbMetodoPago = new ComboBox<>();
        var listaMetodos = metodosPago();
        for (Common mp : listaMetodos) {
            cbMetodoPago.getItems().add(mp.getNombre());
        }
        cbMetodoPago.setPromptText("Selecciona un método de pago");

        TextField txtReferencia = new TextField();
        txtReferencia.setPromptText("Referencia del pago");
        
        txtReferencia.setVisible(false);
        
        if(pedido != null) {
        	txtReferencia.setVisible(false);
        }
        
     // --- CAMPOS PRINCIPALES ---
        GridPane formGrid = new GridPane();
        formGrid.getStyleClass().add("form-container");
        formGrid.setHgap(25);
        formGrid.setVgap(12);
        formGrid.setPadding(new Insets(25));
        formGrid.setAlignment(Pos.TOP_CENTER);

        // === FILA 1 ===
        Label lblProveedor = new Label("Proveedor");
        lblProveedor.getStyleClass().add("form-label");
        formGrid.add(lblProveedor, 0, 0);
        cbProveedor.getStyleClass().add("form-field");
        formGrid.add(cbProveedor, 0, 1);

        Label lblValor = new Label("Valor total");
        lblValor.getStyleClass().add("form-label");
        formGrid.add(lblValor, 1, 0);
        txtValor.getStyleClass().add("form-field");
        formGrid.add(txtValor, 1, 1);

        // === FILA 2 ===
        Label lblFecha = new Label("Fecha del pedido");
        lblFecha.getStyleClass().add("form-label");
        formGrid.add(lblFecha, 0, 2);
        dpFechaPedido.getStyleClass().add("form-field");
        formGrid.add(dpFechaPedido, 0, 3);

        Label lblMetodoPago = new Label("Método de pago");
        lblMetodoPago.getStyleClass().add("form-label");
        formGrid.add(lblMetodoPago, 1, 2);
        cbMetodoPago.getStyleClass().add("form-field");
        formGrid.add(cbMetodoPago, 1, 3);

        // === FILA 3 ===
        Label lblReferencia = new Label("Referencia de pago");
        lblReferencia.getStyleClass().add("form-label");
        formGrid.add(lblReferencia, 0, 4);
        txtReferencia.getStyleClass().add("form-field");
        formGrid.add(txtReferencia, 0, 5);
        lblReferencia.setVisible(false);
      


        GridPane formDetalle = new GridPane();
        formDetalle.setHgap(15);
        formDetalle.setVgap(10);
        formDetalle.setAlignment(Pos.CENTER);
        formDetalle.setPadding(new Insets(10, 0, 10, 0));
        formDetalle.getStyleClass().add("form-container");

        
        // ---- Formulario para agregar detalle ----
        
        ComboBox<String> cbProducto = new ComboBox<>();
        var listaProductos = productos();
        for (Producto p : listaProductos) {
            cbProducto.getItems().add(p.getNombre() + " (ID: " + p.getId() + ")");
        }
        Label lblProducto = new Label("Producto");
        lblProducto.getStyleClass().add("form-label");
        formDetalle.add(lblProducto, 0, 0);
        cbProducto.getStyleClass().add("form-field");
        formDetalle.add(cbProducto, 0, 1);
        cbProducto.setPromptText("Selecciona producto");
        

        TextField txtFermentacion = new TextField();
        txtFermentacion.setPromptText("Fermentación");
        
        txtFermentacion.setTextFormatter(new TextFormatter<>(change -> {
        	if (change.getControlNewText().matches("\\d*(\\.\\d{0,2})?")) {
                return change;
            }
            return null;
        }));

		Label lblFermentacion = new Label("Fermentación");
		lblFermentacion.getStyleClass().add("form-label");
		formDetalle.add(lblFermentacion, 1, 0);
		txtFermentacion.getStyleClass().add("form-field");
		formDetalle.add(txtFermentacion, 1, 1);

        TextField txtPeso = new TextField();
        txtPeso.setPromptText("Peso");
        txtPeso.setTextFormatter(new TextFormatter<>(change -> {
        	if (change.getControlNewText().matches("\\d*(\\.\\d{0,2})?")) {
                return change;
            }
            return null;
        }));

        Label lblPeso = new Label("Peso");
        lblPeso.getStyleClass().add("form-label");
        formDetalle.add(lblPeso, 0, 2);
        txtPeso.getStyleClass().add("form-field");
        formDetalle.add(txtPeso, 0, 3);
        
        TextField txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad");
        
        txtCantidad.setTextFormatter(new TextFormatter<>(change -> {
        	if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
        
        Label lblCantidad = new Label("Cantidad");
        lblCantidad.getStyleClass().add("form-label");
        formDetalle.add(lblCantidad, 1, 2);
        txtCantidad.getStyleClass().add("form-field");
        formDetalle.add(txtCantidad, 1, 3);

        TextField txtHumedad = new TextField();
        txtHumedad.setPromptText("Humedad");
        
        txtHumedad.setTextFormatter(new TextFormatter<>(change -> {
        	if (change.getControlNewText().matches("\\d*(\\.\\d{0,2})?")) {
                return change;
            }
            return null;
        }));
        
        Label lblHumedad = new Label("Humedad");
        lblHumedad.getStyleClass().add("form-label");
        formDetalle.add(lblHumedad, 0, 4);
        txtHumedad.getStyleClass().add("form-field");
        formDetalle.add(txtHumedad, 0, 5);

        TextField txtEstadoCacao = new TextField();
        txtEstadoCacao.setPromptText("Estado cacao");
        txtEstadoCacao.setTextFormatter(new TextFormatter<>(change -> {
        	if (change.getControlNewText().matches("\\d*(\\.\\d{0,2})?")) {
                return change;
            }
            return null;
        }));
        
        Label lblEstado = new Label("Estado del cacao");
        lblEstado.getStyleClass().add("form-label");
        formDetalle.add(lblEstado, 1, 4);
        txtEstadoCacao.getStyleClass().add("form-field");
        formDetalle.add(txtEstadoCacao, 1, 5);

        

        Button btnAgregarDetalle = new Button("Agregar detalle");

        
        btnAgregarDetalle.getStyleClass().add("form-button");
        btnAgregarDetalle.setPrefWidth(200);
        formDetalle.add(btnAgregarDetalle, 0, 6, 2, 1);
        GridPane.setHalignment(btnAgregarDetalle, javafx.geometry.HPos.CENTER);
        TableView<DetallePedidoRequest> tablaDetalles = new TableView<>();
        tablaDetalles.setItems(listaDetalles);
        tablaDetalles.setEditable(true);
        

       

        // Usa ReadOnlyObjectWrapper (la forma correcta si tu modelo no usa JavaFX Properties)
        TableColumn<DetallePedidoRequest, Float> colFermentacion = new TableColumn<>("Fermentación");
      
        colFermentacion.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getFermentacion()));

        TableColumn<DetallePedidoRequest, Float> colPeso = new TableColumn<>("Peso");
        colPeso.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getPeso()));

        TableColumn<DetallePedidoRequest, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getCantidad()));

        TableColumn<DetallePedidoRequest, Float> colHumedad = new TableColumn<>("Humedad");
        colHumedad.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getHumedad()));

        TableColumn<DetallePedidoRequest, Float> colEstadoCacao = new TableColumn<>("Estado Cacao");
        colEstadoCacao.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getEstadoCacao()));

        TableColumn<DetallePedidoRequest, Long> colProducto = new TableColumn<>("Producto ID");
        colProducto.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getIdProducto()));

        ScrollPane scrollTabla = new ScrollPane(tablaDetalles);
        scrollTabla.setFitToWidth(true); 
        scrollTabla.setPrefHeight(20);
        scrollTabla.setMinHeight(100);
        scrollTabla.setMaxHeight(100);
        scrollTabla.setPadding(new Insets(10));
        
        // Columna de eliminar
        TableColumn<DetallePedidoRequest, Void> colEliminar = new TableColumn<>("Acción");
        colEliminar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEliminar = new Button("🗑");

            {
                btnEliminar.setOnAction(e -> {
                    DetallePedidoRequest item = getTableView().getItems().get(getIndex());
                    listaDetalles.remove(item);
                });
                btnEliminar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnEliminar);
            }
        });

        // Agrega las columnas
        tablaDetalles.getColumns().addAll(
            colFermentacion, colPeso, colCantidad, colHumedad, colEstadoCacao, colProducto, colEliminar
        );
        
        
       
        btnAgregarDetalle.setOnAction(e -> {
            try {
                idProductoSeleccionado = listaProductos.stream()
                        .filter(p -> cbProducto.getValue() != null && cbProducto.getValue().contains(String.valueOf(p.getId())))
                        .findFirst()
                        .map(Producto::getId)
                        .orElse(null);

                if (idProductoSeleccionado == null) {
                	NotificationManager.showNotification(cbProducto.getScene(), "⚠️ Debes seleccionar un producto", Color.ORANGE);
                    return;
                }

                // Buscar si ya existe un detalle con ese producto
                DetallePedidoRequest existente = listaDetalles.stream()
                        .filter(d -> d.getIdProducto().equals(idProductoSeleccionado))
                        .findFirst()
                        .orElse(null);

                if (existente != null) {
                    // 🔁 Actualiza los valores existentes
                    existente.setFermentacion(Float.parseFloat(txtFermentacion.getText()));
                    existente.setPeso(Float.parseFloat(txtPeso.getText()));
                    existente.setCantidad(Integer.parseInt(txtCantidad.getText()));
                    existente.setHumedad(Float.parseFloat(txtHumedad.getText()));
                    existente.setEstadoCacao(Float.parseFloat(txtEstadoCacao.getText()));

                    tablaDetalles.refresh(); // refresca la tabla visualmente
                    NotificationManager.showNotification(cbProducto.getScene(), "✏ Detalle actualizado", Color.DODGERBLUE);
                } else {
                    // ➕ Agrega un nuevo detalle
                    DetallePedidoRequest nuevo = new DetallePedidoRequest(
                            Float.parseFloat(txtFermentacion.getText()),
                            Float.parseFloat(txtPeso.getText()),
                            Integer.parseInt(txtCantidad.getText()),
                            Float.parseFloat(txtHumedad.getText()),
                            Float.parseFloat(txtEstadoCacao.getText()),
                            idProductoSeleccionado
                    );
                    listaDetalles.add(nuevo);
                    tablaDetalles.refresh();
                    NotificationManager.showNotification(cbProducto.getScene(), "✔ Detalle agregado", Color.GREEN);
                }
                tablaDetalles.refresh();

                // Limpia los campos
                txtFermentacion.clear();
                txtPeso.clear();
                txtCantidad.clear();
                txtHumedad.clear();
                txtEstadoCacao.clear();
                cbProducto.getSelectionModel().clearSelection();

            } catch (Exception ex) {
                System.out.println("Error agregando detalle: " + ex);
                NotificationManager.showNotification(cbProducto.getScene(), "❌ Error agregando detalle", Color.RED);
            }
        });
        
        tablaDetalles.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtFermentacion.setText(String.valueOf(newSel.getFermentacion()));
                txtPeso.setText(String.valueOf(newSel.getPeso()));
                txtCantidad.setText(String.valueOf(newSel.getCantidad()));
                txtHumedad.setText(String.valueOf(newSel.getHumedad()));
                txtEstadoCacao.setText(String.valueOf(newSel.getEstadoCacao()));

                // Selecciona el producto en el combo
                for (Producto p : listaProductos) {
                    if (p.getId().equals(newSel.getIdProducto())) {
                        cbProducto.setValue(p.getNombre() + " (ID: " + p.getId() + ")");
                        break;
                    }
                }
            }
            tablaDetalles.refresh();
        });
        
        if (pedido != null) {
            // Rellenar campos generales
            cbProveedor.setValue(listaProveedores.stream()
                    .filter(p -> p.getNit() == pedido.getNitProveedor())
                    .findFirst()
                    .map(p -> p.getNombre() + " (" + p.getNit() + ")")
                    .orElse(null));

            txtValor.setTextFormatter(null);
            String valorPlano = new BigDecimal(String.valueOf(pedido.getValor())).toPlainString();
            txtValor.setText(PrecioFormatter.formatearPrecio(Double.valueOf(valorPlano))); // formatea antes de mostrar
            PrecioFormatter.aplicarFormato(txtValor); 
            
            dpFechaPedido.setValue(pedido.getFechaPedido().toLocalDate());

            cbMetodoPago.setValue(pedido.getMedioPago().getMetodoPago());
            
            if(!pedido.getMedioPago().getReferencia().equals("null")) {
            	txtReferencia.setText(pedido.getMedioPago().getReferencia());
            	lblReferencia.setVisible(true);
            	txtReferencia.setVisible(true);
            }
            

         // Limpia la lista antes de rellenarla
            listaDetalles.clear();

            for (var det : pedido.getDetallePedido()) {
                var idProducto = productos().stream()
                        .filter(p -> p.getNombre().equals(det.getProducto()))
                        .findFirst()
                        .map(Producto::getId)
                        .orElse(null);

                listaDetalles.add(new DetallePedidoRequest(
                        det.getFermentacion(),
                        det.getPeso(),
                        det.getCantidad(),
                        det.getHumedad(),
                        det.getEstadoCacao(),
                        idProducto
                ));
            }

            System.out.println(listaDetalles.toString());
            // Solo una vez, después del for:
            tablaDetalles.refresh();
            System.out.println("Detalles cargados: " + listaDetalles.size());

        }

        // ---- Botón principal ----
        Button btnAccion = new Button(title);
        btnAccion.getStyleClass().add("login-button");
        btnAccion.setPrefWidth(150);
        
       

        Label lblMensaje = new Label();
        lblMensaje.setTextFill(Color.RED);

        btnAccion.setOnAction(e -> {
            try {

                // -----------------------------
                //   Obtener NIT del proveedor
                // -----------------------------
                nitProveedor = listaProveedores.stream()
                        .filter(p -> cbProveedor.getValue().contains(String.valueOf(p.getNit())))
                        .findFirst()
                        .map(Proveedor::getNit)
                        .orElse(null);

                // -----------------------------
                //   Obtener ID del método pago
                // -----------------------------
                idMetodoPago = listaMetodos.stream()
                        .filter(m -> m.getNombre().equals(cbMetodoPago.getValue()))
                        .findFirst()
                        .map(Common::getId)
                        .orElse(null);

                // -----------------------------
                //   Asignar referencia null si es Efectivo
                // -----------------------------
                if ("Efectivo".equals(cbMetodoPago.getValue())) {
                    txtReferencia.setText("null");
                }

                // -----------------------------
                //   Construcción de objetos
                // -----------------------------
                MedioPagoRequest medioPago = new MedioPagoRequest(
                        txtReferencia.getText(),
                        idMetodoPago
                );

                Double valor = Double.parseDouble(
                        txtValor.getText().replace(".", "").replace("$", "").trim()
                );

                PedidoRequest request = new PedidoRequest(
                        nitProveedor,
                        valor,
                        dpFechaPedido.getValue().atStartOfDay(),
                        medioPago,
                        new ArrayList<>(listaDetalles)
                );

                // -----------------------------
                //   Crear o Editar
                // -----------------------------
                var response = (pedido == null)
                        ? onActionRegistrar(request)
                        : onActionEditar(pedido.getId(), request);

                // -----------------------------
                //   Notificaciones
                // -----------------------------
                boolean esNuevo = pedido == null;
                int successCode = esNuevo ? 201 : 200;

                if (response.getStatus() != successCode) {
                    NotificationManager.showNotification(
                            btnAccion.getScene(),
                            String.format("❌ %s", response.getMessage()),
                            Color.YELLOWGREEN
                    );
                } else {
                    String icon = esNuevo ? "✔" : "✏";
                    NotificationManager.showNotification(
                            btnAccion.getScene(),
                            String.format("%s %s", icon, response.getMessage()),
                            Color.GREEN
                    );
                }

                // -----------------------------
                //   Limpiar campos
                // -----------------------------
                cbProveedor.getSelectionModel().clearSelection();
                txtValor.clear();
                txtReferencia.clear();
                listaDetalles.clear();

            } catch (Exception ex) {
                System.out.println("Error tipo: " + ex);
                NotificationManager.showNotification(
                        btnAccion.getScene(),
                        "❌ Error en el registro del pedido",
                        Color.RED
                );
            }

            // -----------------------------
            //   Recargar listado
            // -----------------------------
            try {
                PedidoService service = new PedidoService();
                var pedidos = service.obtenerPedidos();

                PedidoController controller = new PedidoController(content);
                content.getChildren().setAll(controller.getScene(pedidos));

            } catch (Exception ex) {
                throw new RuntimeException("Error tipo: " + ex);
            }

        });

      
        titulo.getStyleClass().add("form-title");

        
        
        
        cbMetodoPago.setOnAction(e -> {
        	System.out.println(cbMetodoPago.getValue());
        	if(cbMetodoPago.getValue().equals("Efectivo")) {
        		txtReferencia.setVisible(false);
        		lblReferencia.setVisible(false);
        	}else {
        		txtReferencia.setVisible(true);
        		lblReferencia.setVisible(true);
        	}
        });
        
        // --- SECCIÓN DETALLES ---
        Label lblDetalles = new Label("Detalles del pedido");
        lblDetalles.getStyleClass().add("form-title");
        lblDetalles.setPadding(new Insets(10, 0, 5, 0));

       
        tablaDetalles.setPlaceholder(new Label("No hay detalles disponibles"));

        scrollTabla.setStyle("-fx-background-color: transparent;");
      


        btnAccion.getStyleClass().add("form-button");
        btnAccion.setPrefWidth(220);




        VBox root = new VBox(18,
                titulo,
                formGrid,
                lblDetalles,
                formDetalle,
                scrollTabla,
                btnAccion,
                lblMensaje
        );
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(35));
        root.setStyle("-fx-background-color: #F8F9FA;");

        // --- OPCIONAL: Scroll general (para pantallas pequeñas) ---
        ScrollPane scrollRoot = new ScrollPane(root);
        scrollRoot.setFitToWidth(true);
        scrollRoot.getStyleClass().add("custom-scroll");
        return scrollRoot;
    
    }
}
