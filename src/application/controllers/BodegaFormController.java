package application.controllers;

import application.models.request.BodegaRequest;
import application.services.BodegaService;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;

public class BodegaFormController {
	private final StackPane content;
	 /** 🔹 Convierte Color a formato HEX para aplicar en CSS dinámico */
    private static String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    /** 🔹 Muestra un pequeño mensaje tipo Toast */
    private static void showNotification(Scene scene, String text, Color color) {
        Label notification = new Label(text);
        notification.getStyleClass().add("notification-toast");
        notification.setStyle("-fx-background-color: " + toHex(color) + ";"
                + "-fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 8px;");

        Popup popup = new Popup();
        popup.getContent().add(notification);
        popup.setAutoFix(true);

        // Posición en parte inferior central
        double x = scene.getWindow().getX() + scene.getWidth() / 2 - 100;
        double y = scene.getWindow().getY() + scene.getHeight() - 100;
        popup.show(scene.getWindow(), x, y);

        // 🔹 Animación FadeOut
        FadeTransition fade = new FadeTransition(Duration.seconds(2.5), notification);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(ev -> popup.hide());
        fade.play();
    }

    /** 🔹 Lógica de acción para registrar una bodega */
    private static void onActionRegistrar(BodegaRequest request) {
        try {
            BodegaService service = new BodegaService();
            service.crearBodega(request);
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
            throw new RuntimeException("Error tipo " + ex);
        }
    }

    /** 🔹 Construye y retorna el formulario de registro de bodega */
    public static VBox getScene() {
        // Título
        Text titulo = new Text("🏢 Registro de Bodega");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Campos
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre de la bodega");

        TextField txtUbicacion = new TextField();
        txtUbicacion.setPromptText("Ubicación de la bodega");

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.getStyleClass().add("login-button");
        btnRegistrar.setPrefWidth(150);

        Label lblMensaje = new Label();
        lblMensaje.setTextFill(Color.RED);

        // Acción del botón
        btnRegistrar.setOnAction(e -> {
            if (txtNombre.getText().isEmpty() || txtUbicacion.getText().isEmpty()) {
                lblMensaje.setText("⚠️ Por favor, completa todos los campos.");
                return;
            }

            BodegaRequest request = new BodegaRequest(
                    txtNombre.getText(),
                    txtUbicacion.getText()
            );

            onActionRegistrar(request);
            showNotification(btnRegistrar.getScene(), "✅ Bodega registrada correctamente", Color.GREEN);

            lblMensaje.setTextFill(Color.GREEN);
            lblMensaje.setText("✅ Bodega registrada correctamente.");

            txtNombre.clear();
            txtUbicacion.clear();
        });

        // Diseño del layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(
                titulo,
                txtNombre,
                txtUbicacion,
                btnRegistrar,
                lblMensaje
        );

        root.setStyle("-fx-background-color: #F8F9FA;");
        return root;
    }

}
