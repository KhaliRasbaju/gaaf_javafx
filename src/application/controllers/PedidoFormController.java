package application.controllers;

import java.time.LocalDateTime;

import application.models.request.PedidoRequest;
import application.models.response.Pedido;
import application.services.PedidoService;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;

public class PedidoFormController {

    /* ---------------- UTILIDADES ---------------- */

    private static String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private static void showNotification(Scene scene, String text, Color color) {
        Label notification = new Label(text);
        notification.getStyleClass().add("notification-toast");
        notification.setStyle("-fx-background-color: " + toHex(color) + ";");

        Popup popup = new Popup();
        popup.getContent().add(notification);
        popup.setAutoFix(true);

        double x = scene.getWindow().getX() + scene.getWidth() / 2 - 100;
        double y = scene.getWindow().getY() + scene.getHeight() - 100;
        popup.show(scene.getWindow(), x, y);

        FadeTransition fade = new FadeTransition(Duration.seconds(2.5), notification);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> popup.hide());
        fade.play();
    }

    /* ---------------- ACCIONES ---------------- */

    private static void onActionRegistrar(PedidoRequest request) {
        try {
            PedidoService service = new PedidoService();
            service.crearPedido(request);
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
            throw new RuntimeException("Error tipo " + ex);
        }
    }

    private static void onActionActualizar(Long id, PedidoRequest request) {
        try {
            PedidoService service = new PedidoService();
            service.editarPedido(id, request);
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    /* ---------------- FORMULARIO ---------------- */

    public static VBox getScene(String title, Pedido pedido) {
        // 🔹 Título
        Text titulo = new Text(String.format("📦 %s Pedido", title));
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // 🔹 Campos de texto
        TextField txtNitProveedor = new TextField();
        txtNitProveedor.setPromptText("NIT del Proveedor");

        TextField txtValor = new TextField();
        txtValor.setPromptText("Valor Total");

        TextField txtFechaPedido = new TextField();
        txtFechaPedido.setPromptText("Fecha del Pedido (YYYY-MM-DD)");

        TextField txtFechaEntrega = new TextField();
        txtFechaEntrega.setPromptText("Fecha de Entrega (YYYY-MM-DD)");

        TextField txtRecibido = new TextField();
        txtRecibido.setPromptText("Recibido (true / false)");

        // 🔹 Precargar datos si se está editando
        if (pedido != null) {
            if (pedido.getNitProveedor() != null)
                txtNitProveedor.setText(String.valueOf(pedido.getNitProveedor()));
            if (pedido.getValor() != null)
                txtValor.setText(String.valueOf(pedido.getValor()));
            if (pedido.getFechaPedido() != null)
                txtFechaPedido.setText(pedido.getFechaPedido().toString());
            if (pedido.getFechaEntrega() != null)
                txtFechaEntrega.setText(pedido.getFechaEntrega().toString());
            txtRecibido.setText(String.valueOf(pedido.getRecibido()));

        }

        // 🔹 Botón principal
        Button btnAccion = new Button(pedido == null ? "Registrar" : "Actualizar");
        btnAccion.getStyleClass().add("login-button");
        btnAccion.setPrefWidth(150);

        Label lblMensaje = new Label();
        lblMensaje.setTextFill(Color.RED);

        // 🔹 Acción del botón
        btnAccion.setOnAction(e -> {
            if (txtNitProveedor.getText().isEmpty() || txtValor.getText().isEmpty()
                    || txtFechaPedido.getText().isEmpty() || txtFechaEntrega.getText().isEmpty()
                    || txtRecibido.getText().isEmpty()) {
                lblMensaje.setText("⚠️ Por favor, completa todos los campos.");
                lblMensaje.setTextFill(Color.RED);
                return;
            }

            try {
                PedidoRequest request = new PedidoRequest();
                request.setNitProveedor(Long.parseLong(txtNitProveedor.getText()));
                request.setValor(Double.parseDouble(txtValor.getText()));
                request.setFechaPedido(LocalDateTime.parse(txtFechaPedido.getText()));
                request.setFechaEntrega(LocalDateTime.parse(txtFechaEntrega.getText()));
                request.setRecibido(Boolean.parseBoolean(txtRecibido.getText()));

                if (pedido == null) {
                    onActionRegistrar(request);
                    showNotification(btnAccion.getScene(), "✅ Pedido registrado correctamente", Color.GREEN);
                    lblMensaje.setText("✅ Pedido registrado correctamente.");
                    lblMensaje.setTextFill(Color.GREEN);

                    txtNitProveedor.clear();
                    txtValor.clear();
                    txtFechaPedido.clear();
                    txtFechaEntrega.clear();
                    txtRecibido.clear();
                } else {
                    onActionActualizar(pedido.getId(), request);
                    showNotification(btnAccion.getScene(), "✏️ Pedido actualizado correctamente", Color.BLUE);
                    lblMensaje.setText("✏️ Pedido actualizado correctamente.");
                    lblMensaje.setTextFill(Color.BLUE);
                }
            } catch (Exception ex) {
                lblMensaje.setText("❌ Error al procesar los datos.");
                lblMensaje.setTextFill(Color.RED);
                System.out.println("Error tipo: " + ex);
            }
        });

        // 🔹 Layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(
                titulo,
                txtNitProveedor,
                txtValor,
                txtFechaPedido,
                txtFechaEntrega,
                txtRecibido,
                btnAccion,
                lblMensaje
        );
        root.setStyle("-fx-background-color: #F8F9FA;");

        return root;
    }
}

