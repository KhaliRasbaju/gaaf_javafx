package application.controllers;

import application.models.request.TransaccionRequest;
import application.models.response.ResponseCommon;
import application.models.response.Transaccion;
import application.models.response.Bodega;
import application.models.response.Common;
import application.models.response.Producto;
import application.services.TransaccionService;
import application.services.ProductoService;
import application.services.BodegaService;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.util.Duration;

import java.util.List;

public class TransaccionFormController {

    private final StackPane content;
    private final String modo;
    private final Transaccion transaccion;

    public TransaccionFormController(StackPane content, String modo, Transaccion transaccion) {
        this.content = content;
        this.modo = modo;
        this.transaccion = transaccion;
    }

    // ✅ Carga la escena del formulario

    // 🔹 Convierte color a hexadecimal (para notificaciones)
    private static String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    // 🔹 Notificación tipo toast
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

    // ✅ Crea el formulario de transacción
    private VBox getScene() throws Exception {
        // Campos
        ComboBox<Producto> cmbProducto = new ComboBox<>();
        ComboBox<Bodega> cmbBodega = new ComboBox<>();
        ComboBox<String> cmbTipo = new ComboBox<>();
        TextField txtIdPedido = new TextField();
        TextField txtCantidad = new TextField();
        TextArea txtObservacion = new TextArea();

        // Cargar combos desde servicios
        ProductoService productoService = new ProductoService();
        BodegaService bodegaService = new BodegaService();

        List<Producto> productos = productoService.obtenerProductos();
        List<Bodega> bodegas = bodegaService.obtenerBodegas();

        cmbProducto.setItems(FXCollections.observableArrayList(productos));
        cmbProducto.setPromptText("Seleccione un producto");
        cmbProducto.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
        cmbProducto.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Seleccione un producto" : item.getNombre());
            }
        });

        cmbBodega.setItems(FXCollections.observableArrayList(bodegas));
        cmbBodega.setPromptText("Seleccione una bodega");
        cmbBodega.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Bodega item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
        cmbBodega.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Bodega item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Seleccione una bodega" : item.getNombre());
            }
        });

        cmbTipo.setItems(FXCollections.observableArrayList("ENTRADA", "SALIDA"));
        cmbTipo.setPromptText("Seleccione tipo");

        txtIdPedido.setPromptText("ID Pedido (opcional)");
        txtCantidad.setPromptText("Cantidad");
        txtObservacion.setPromptText("Observación");
        txtObservacion.setPrefRowCount(3);

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.getStyleClass().add("btn-guardar");

        btnRegistrar.setOnAction(e -> {
            try {
                Producto producto = cmbProducto.getValue();
                Bodega bodega = cmbBodega.getValue();
                String tipo = cmbTipo.getValue();
                String observacion = txtObservacion.getText();
                Integer cantidad = Integer.parseInt(txtCantidad.getText());
                Long idPedido = txtIdPedido.getText().isEmpty() ? null : Long.parseLong(txtIdPedido.getText());

                if (producto == null || bodega == null || tipo == null) {
                    showNotification(btnRegistrar.getScene(), "⚠️ Todos los campos obligatorios deben completarse.", Color.RED);
                    return;
                }

                TransaccionRequest request = new TransaccionRequest(
                        producto.getId(),
                        idPedido,
                        observacion,
                        tipo,
                        bodega.getId(),
                        cantidad
                );

                TransaccionService service = new TransaccionService();
                Transaccion respuesta = service.crearTransaccion(request);

                showNotification(btnRegistrar.getScene(), "✅ Transacción registrada exitosamente", Color.GREEN);

                // Limpieza del formulario
                cmbProducto.setValue(null);
                cmbBodega.setValue(null);
                cmbTipo.setValue(null);
                txtIdPedido.clear();
                txtCantidad.clear();
                txtObservacion.clear();

            } catch (Exception ex) {
                System.out.println("Error tipo: " + ex);
                showNotification(btnRegistrar.getScene(), "❌ Error al registrar la transacción", Color.RED);
            }
        });

        VBox form = new VBox(10,
                new Label("Registrar Transacción"),
                cmbProducto,
                cmbBodega,
                cmbTipo,
                txtIdPedido,
                txtCantidad,
                txtObservacion,
                btnRegistrar
        );

        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(20));
        form.setMaxWidth(400);
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, #00000055, 5, 0, 0, 1);");

        VBox layout = new VBox(form);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f3f4f6;");
        return layout;
    }
}
