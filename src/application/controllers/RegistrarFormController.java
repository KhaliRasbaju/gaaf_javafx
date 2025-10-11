package application.controllers;



import application.models.request.RegistrarRequest;
import application.services.AutentificacionService;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;

public class RegistrarFormController {
	
	 private static String toHex(Color color) {
	        return String.format("#%02X%02X%02X",
	                (int) (color.getRed() * 255),
	                (int) (color.getGreen() * 255),
	                (int) (color.getBlue() * 255));
	    }
	
	private static void showNotification(Scene scene, String text, Color color) {
        Label notification = new Label(text);
        notification.getStyleClass().add("notification-toast");
        // Color de fondo dinámico para el toast
        notification.setStyle("-fx-background-color: " + toHex(color) + ";");

        Popup popup = new Popup();
        popup.getContent().add(notification);
        popup.setAutoFix(true);

        // Posicionar en la parte inferior del centro
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
	
	private static void onActionRegistrar(RegistrarRequest request) {
		try {
			AutentificacionService service = new AutentificacionService();
			service.registrarUsuario(request);
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
			throw new RuntimeException("Error tipo "+ ex);
		}
	}
	
	
	public static VBox getScene() {
        // Título
        Text titulo = new Text("📝 Registro de Usuario");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Campos del formulario
        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario");

        TextField txtCorreo = new TextField();
        txtCorreo.setPromptText("Correo electrónico");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre completo");

        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");

        PasswordField txtContraseña = new PasswordField();
        txtContraseña.setPromptText("Contraseña");

        ComboBox<String> cbRol = new ComboBox<>();
        cbRol.getItems().addAll("Administrador", "Coordinado de Compras", "Gerente", "Jefe de Bodega");
        cbRol.setPromptText("Selecciona un rol");

        Button btnRegistrar = new Button("Registrar");
        
        btnRegistrar.getStyleClass().add("login-button");
        btnRegistrar.setPrefWidth(150);

        Label lblMensaje = new Label();
        lblMensaje.setTextFill(Color.RED);

        btnRegistrar.setOnAction(e -> {
        	var rol  = "";
            if (txtUsuario.getText().isEmpty() ||
                txtCorreo.getText().isEmpty() ||
                txtNombre.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() ||
                txtContraseña.getText().isEmpty() ||
                cbRol.getValue() == null) {

                lblMensaje.setText("⚠️ Por favor, completa todos los campos.");
                return;
            }
            
            switch (cbRol.getValue()) {
			case "Administrador": {
				
				rol = "ADMIN";
			}
			case "Coordinado de Compras": {
				rol = "COORDINADOR_COMPRAS"; 
			}
			case "Gerente": {
				rol =  "GERENTE";
			}
			case "Jefe de Bodega" : {
				rol =  "JEFE_BODEGA";
			}
			default:
				System.out.println("Error");
			}

            RegistrarRequest request = new RegistrarRequest(
                txtUsuario.getText(),
                txtCorreo.getText(),
                txtNombre.getText(),
                txtTelefono.getText(),
                txtContraseña.getText(),
                rol
            );

            onActionRegistrar(request);
            showNotification(btnRegistrar.getScene(), "✅ Usuario creado correctamente ", Color.RED);

            lblMensaje.setTextFill(Color.GREEN);
            lblMensaje.setText("✅ Usuario registrado correctamente.");

            txtUsuario.clear();
            txtCorreo.clear();
            txtNombre.clear();
            txtTelefono.clear();
            txtContraseña.clear();
            cbRol.setValue(null);
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(
            titulo,
            txtUsuario,
            txtCorreo,
            txtNombre,
            txtTelefono,
            txtContraseña,
            cbRol,
            btnRegistrar,
            lblMensaje
        );

        root.setStyle("-fx-background-color: #F8F9FA;");
        return root;
    }
}
