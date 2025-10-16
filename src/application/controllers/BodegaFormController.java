package application.controllers;

import application.models.request.BodegaRequest;
import application.models.response.Bodega;
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
            System.out.println(service.crearBodega(request)); 
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
            throw new RuntimeException("Error tipo " + ex);
        }
    }
    
    private static void onActionActualizar(Long id, BodegaRequest request) {
		try {
			BodegaService service = new BodegaService();
			service.editarBodega(id, request);
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}

    /** 🔹 Construye y retorna el formulario de registro de bodega */
    public static VBox getScene(String title, Bodega bodega) {
    // 🔹 Título dinámico
    Text titulo = new Text(String.format("🏢 %s de Bodega", title));
    titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    // 🔹 Campos
    TextField txtNombre = new TextField();
    txtNombre.setPromptText("Nombre de la bodega");

    TextField txtUbicacion = new TextField();
    txtUbicacion.setPromptText("Ubicación de la bodega");

    // 🔹 Si la bodega no es nula → precargar datos
    if (bodega != null) {
        if (bodega.getNombre() != null) {
            txtNombre.setText(bodega.getNombre());
        }
        if (bodega.getUbicacion() != null) {
            txtUbicacion.setText(bodega.getUbicacion());
        }
    }

    // 🔹 Botón (cambia el texto según si es registrar o editar)
    Button btnAccion = new Button(bodega == null ? "Registrar" : "Actualizar");
    btnAccion.getStyleClass().add("login-button");
    btnAccion.setPrefWidth(150);

    Label lblMensaje = new Label();
    lblMensaje.setTextFill(Color.RED);

    // 🔹 Acción del botón
    btnAccion.setOnAction(e -> {
        if (txtNombre.getText().isEmpty() || txtUbicacion.getText().isEmpty()) {
            lblMensaje.setText("⚠️ Por favor, completa todos los campos.");
            lblMensaje.setTextFill(Color.RED);
            return;
        }

        BodegaRequest request = new BodegaRequest(
            txtNombre.getText(),
            txtUbicacion.getText()
        );

        // Si es nuevo registro
        if (bodega == null) {
            onActionRegistrar(request);
            showNotification(btnAccion.getScene(), "✅ Bodega registrada correctamente", Color.GREEN);
            lblMensaje.setTextFill(Color.GREEN);
            lblMensaje.setText("✅ Bodega registrada correctamente.");
            txtNombre.clear();
            txtUbicacion.clear();
        } 
        // Si es edición
        else {
            onActionActualizar(bodega.getId(), request);
            showNotification(btnAccion.getScene(), "✏️ Bodega actualizada correctamente", Color.BLUE);
            lblMensaje.setTextFill(Color.BLUE);
            lblMensaje.setText("✏️ Bodega actualizada correctamente.");
        }
    });

    // 🔹 Diseño del layout
    VBox root = new VBox(10);
    root.setPadding(new Insets(30));
    root.setAlignment(Pos.CENTER);
    root.getChildren().addAll(
        titulo,
        txtNombre,
        txtUbicacion,
        btnAccion,
        lblMensaje
    );

    root.setStyle("-fx-background-color: #F8F9FA;");
    return root;
}

}
