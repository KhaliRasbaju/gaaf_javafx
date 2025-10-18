package application.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import application.models.request.DetallePedidoRequest;
import application.models.request.MedioPagoRequest;
import application.models.request.PedidoRequest;
import application.models.response.Common;
import application.models.response.Pedido;
import application.models.response.Proveedor;
import application.models.response.Producto;
import application.services.PedidoService;
import application.services.ProveedorService;
import application.services.ProductoService;
import application.services.EntidadService;
import application.services.MetodoPagoService;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;

public class PedidoFormController {

    private static Long idMetodoPago;
    private static Long nitProveedor;
    private static Long idProductoSeleccionado;

    // Lista para los detalles del pedido
    private static final ObservableList<DetallePedidoRequest> listaDetalles = FXCollections.observableArrayList();

    private static String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private static void showNotification(Scene scene, String text, Color color) {
        Label notification = new Label(text);
        notification.getStyleClass().add("notification-toast");
        notification.setStyle("-fx-background-color: " + toHex(color) + ";"
                + "-fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 8px;");

        Popup popup = new Popup();
        popup.getContent().add(notification);
        popup.setAutoFix(true);

        double x = scene.getWindow().getX() + scene.getWidth() / 2 - 100;
        double y = scene.getWindow().getY() + scene.getHeight() - 100;
        popup.show(scene.getWindow(), x, y);

        FadeTransition fade = new FadeTransition(Duration.seconds(2.5), notification);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(ev -> popup.hide());
        fade.play();
    }

    private static void onActionRegistrar(PedidoRequest request) {
        try {
            PedidoService service = new PedidoService();
            System.out.println(service.crearPedido(request));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    // Obtener listas auxiliares
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
        
    

    private static List<Common> metodosPago() throws Exception {
        MetodoPagoService service = new MetodoPagoService();
        return service.obtenerMetodos();
    }

    private static Producto producto(Long id) throws Exception {
        ProductoService service = new ProductoService();
        return service.obtenerProducto(id);
    }
    
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

    public static VBox getScene(String title, Pedido pedido) throws Exception {

        Text titulo = new Text(String.format("📦 %s Pedido", title));
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

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

        DatePicker dpFechaPedido = new DatePicker(LocalDate.now());
        DatePicker dpFechaEntrega = new DatePicker();

        // Combobox método de pago
        ComboBox<String> cbMetodoPago = new ComboBox<>();
        var listaMetodos = metodosPago();
        for (Common mp : listaMetodos) {
            cbMetodoPago.getItems().add(mp.getNombre());
        }
        cbMetodoPago.setPromptText("Selecciona un método de pago");

        TextField txtReferencia = new TextField();
        txtReferencia.setPromptText("Referencia del pago");

        // ---- Tabla de detalles ----
        TableView<DetallePedidoRequest> tablaDetalles = new TableView<>(listaDetalles);

        TableColumn<DetallePedidoRequest, Float> colFermentacion = new TableColumn<>("Fermentación");
        colFermentacion.setCellValueFactory(c -> new javafx.beans.property.SimpleFloatProperty(c.getValue().getFermentacion()).asObject());

        TableColumn<DetallePedidoRequest, Float> colPeso = new TableColumn<>("Peso");
        colPeso.setCellValueFactory(c -> new javafx.beans.property.SimpleFloatProperty(c.getValue().getPeso()).asObject());

        TableColumn<DetallePedidoRequest, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getCantidad()).asObject());

        TableColumn<DetallePedidoRequest, Float> colHumedad = new TableColumn<>("Humedad");
        colHumedad.setCellValueFactory(c -> new javafx.beans.property.SimpleFloatProperty(c.getValue().getHumedad()).asObject());

        TableColumn<DetallePedidoRequest, Float> colEstadoCacao = new TableColumn<>("Estado Cacao");
        colEstadoCacao.setCellValueFactory(c -> new javafx.beans.property.SimpleFloatProperty(c.getValue().getEstadoCacao()).asObject());

        TableColumn<DetallePedidoRequest, Long> colProducto = new TableColumn<>("Producto ID");
        colProducto.setCellValueFactory(c -> new javafx.beans.property.SimpleLongProperty(c.getValue().getIdProducto()).asObject());

        tablaDetalles.getColumns().addAll(colFermentacion, colPeso, colCantidad, colHumedad, colEstadoCacao, colProducto);
        tablaDetalles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // ---- Formulario para agregar detalle ----
        ComboBox<String> cbProducto = new ComboBox<>();
        var listaProductos = productos();
        for (Producto p : listaProductos) {
            cbProducto.getItems().add(p.getNombre() + " (ID: " + p.getId() + ")");
        }
        cbProducto.setPromptText("Selecciona producto");

        TextField txtFermentacion = new TextField();
        txtFermentacion.setPromptText("Fermentación");

        TextField txtPeso = new TextField();
        txtPeso.setPromptText("Peso");

        TextField txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad");

        TextField txtHumedad = new TextField();
        txtHumedad.setPromptText("Humedad");

        TextField txtEstadoCacao = new TextField();
        txtEstadoCacao.setPromptText("Estado cacao");

        Button btnAgregarDetalle = new Button("Agregar detalle");
        btnAgregarDetalle.setOnAction(e -> {
            try {
                idProductoSeleccionado = listaProductos.stream()
                        .filter(p -> cbProducto.getValue().contains(String.valueOf(p.getId())))
                        .findFirst()
                        .map(Producto::getId)
                        .orElse(null);

                DetallePedidoRequest detalle = new DetallePedidoRequest(
                        Float.parseFloat(txtFermentacion.getText()),
                        Float.parseFloat(txtPeso.getText()),
                        Integer.parseInt(txtCantidad.getText()),
                        Float.parseFloat(txtHumedad.getText()),
                        Float.parseFloat(txtEstadoCacao.getText()),
                        idProductoSeleccionado
                );

                listaDetalles.add(detalle);

                txtFermentacion.clear();
                txtPeso.clear();
                txtCantidad.clear();
                txtHumedad.clear();
                txtEstadoCacao.clear();
                cbProducto.getSelectionModel().clearSelection();

            } catch (Exception ex) {
                System.out.println("Error agregando detalle: " + ex);
            }
        });

        VBox formDetalle = new VBox(5, cbProducto, txtFermentacion, txtPeso, txtCantidad, txtHumedad, txtEstadoCacao, btnAgregarDetalle);
        formDetalle.setPadding(new Insets(10));
        formDetalle.setAlignment(Pos.CENTER_LEFT);

        // ---- Botón principal ----
        Button btnAccion = new Button(pedido == null ? "Registrar" : "Actualizar");
        btnAccion.getStyleClass().add("login-button");
        btnAccion.setPrefWidth(150);

        Label lblMensaje = new Label();
        lblMensaje.setTextFill(Color.RED);

        btnAccion.setOnAction(e -> {
            try {
                nitProveedor = listaProveedores.stream()
                        .filter(p -> cbProveedor.getValue().contains(String.valueOf(p.getNit())))
                        .findFirst()
                        .map(Proveedor::getNit)
                        .orElse(null);

                idMetodoPago = listaMetodos.stream()
                        .filter(m -> m.getNombre().equals(cbMetodoPago.getValue()))
                        .findFirst()
                        .map(Common::getId)
                        .orElse(null);

                MedioPagoRequest medioPago = new MedioPagoRequest(txtReferencia.getText(), idMetodoPago);

                PedidoRequest request = new PedidoRequest(
                        nitProveedor,
                        Double.parseDouble(txtValor.getText()),
                        dpFechaPedido.getValue().atStartOfDay(),
                        medioPago,
                        new ArrayList<>(listaDetalles)
                );
                request.setFechaEntrega(dpFechaEntrega.getValue() != null ? dpFechaEntrega.getValue().atStartOfDay() : null);

                if (pedido == null) {
                    onActionRegistrar(request);
                    showNotification(btnAccion.getScene(), "✅ Pedido registrado correctamente", Color.GREEN);
                } else {
                    // Aquí podrías añadir lógica para actualizar pedidos existentes
                    showNotification(btnAccion.getScene(), "✏️ Pedido actualizado correctamente", Color.GREEN);
                }

                cbProveedor.getSelectionModel().clearSelection();
                txtValor.clear();
                txtReferencia.clear();
                listaDetalles.clear();

            } catch (Exception ex) {
                System.out.println("Error tipo: " + ex);
                showNotification(btnAccion.getScene(), "❎ Error en el registro del pedido", Color.RED);
            }
        });

        VBox root = new VBox(12);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(
                titulo, cbProveedor, txtValor, dpFechaPedido, dpFechaEntrega,
                cbMetodoPago, txtReferencia, new Separator(),
                new Label("Detalles del pedido:"), formDetalle, tablaDetalles,
                btnAccion, lblMensaje
        );
        root.setStyle("-fx-background-color: #F8F9FA;");

        return root;
    }
}
