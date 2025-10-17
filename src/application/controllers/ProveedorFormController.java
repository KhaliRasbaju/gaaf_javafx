package application.controllers;

import application.models.request.CuentaRequest;
import application.models.request.ProveedorRequest;
import application.models.request.UbicacionRequest;
import application.models.response.Proveedor;
import application.services.ProveedorService;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;

public class ProveedorFormController {

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

    private static void onActionRegistrar(ProveedorRequest request) {
        try {
            ProveedorService service = new ProveedorService();
            service.crearProveedor(request);
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    private static void onActionActualizar(Long nit, ProveedorRequest request) {
        try {
            ProveedorService service = new ProveedorService();
            service.editarProveedor(nit, request);
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    public static VBox getScene(String title, Proveedor proveedor) {
        Text titulo = new Text(String.format("🏢 %s Proveedor", title));
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField txtNit = new TextField();
        txtNit.setPromptText("NIT");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");

        TextField txtCorreo = new TextField();
        txtCorreo.setPromptText("Correo");

        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");

        if (proveedor != null) {
            txtNit.setText(String.valueOf(proveedor.getNit()));
            txtNit.setDisable(true);
            txtNombre.setText(proveedor.getNombre());
            txtCorreo.setText(proveedor.getCorreo());
            txtTelefono.setText(proveedor.getTelefono());
        }

        Button btnAccion = new Button(proveedor == null ? "Registrar" : "Actualizar");
        btnAccion.getStyleClass().add("login-button");
        btnAccion.setPrefWidth(150);

        Label lblMensaje = new Label();
        lblMensaje.setTextFill(Color.RED);

        btnAccion.setOnAction(e -> {
            if (txtNit.getText().isEmpty() || txtNombre.getText().isEmpty()
                    || txtCorreo.getText().isEmpty() || txtTelefono.getText().isEmpty()) {
                lblMensaje.setText("⚠️ Completa todos los campos.");
                lblMensaje.setTextFill(Color.RED);
                return;
            }

            ProveedorRequest request = new ProveedorRequest(
                    Long.parseLong(txtNit.getText()),
                    txtNombre.getText(),
                    txtCorreo.getText(),
                    txtTelefono.getText(),
                    new CuentaRequest(),
                    new UbicacionRequest()
            );

            if (proveedor == null) {
                onActionRegistrar(request);
                showNotification(btnAccion.getScene(), "✅ Proveedor registrado correctamente", Color.GREEN);
                lblMensaje.setTextFill(Color.GREEN);
                lblMensaje.setText("✅ Proveedor registrado correctamente.");
                txtNit.clear();
                txtNombre.clear();
                txtCorreo.clear();
                txtTelefono.clear();
            } else {
                onActionActualizar(proveedor.getNit(), request);
                showNotification(btnAccion.getScene(), "✏️ Proveedor actualizado correctamente", Color.BLUE);
                lblMensaje.setTextFill(Color.BLUE);
                lblMensaje.setText("✏️ Proveedor actualizado correctamente.");
            }
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(titulo, txtNit, txtNombre, txtCorreo, txtTelefono, btnAccion, lblMensaje);
        root.setStyle("-fx-background-color: #F8F9FA;");
        return root;
    }
}
