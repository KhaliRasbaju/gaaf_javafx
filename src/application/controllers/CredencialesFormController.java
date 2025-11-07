package application.controllers;

import application.models.request.CredencialesRequest;
import application.models.request.UsuarioRequest;
import application.services.UsuarioService;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.util.Duration;

public class CredencialesFormController {
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
	
	
	private static void onCambiarCredenciales(String id, CredencialesRequest request) throws Exception {
		try {
			UsuarioService service = new UsuarioService();
			service.editarCredenciales(id, request);
		} catch (Exception ex) {
			throw new Exception("Error tipo: "+ex);
		}
	}
	
	public static VBox getScene(String title, String id) {
		
		Label lblTitulo = new Label(String.format("📝 %s de Usuario", title));
	    lblTitulo.getStyleClass().add("form-title");
	    
	    Label lblContrasena = new Label("Contraeña: ");
	    lblContrasena.getStyleClass().add("form-label");
	    PasswordField contrasena = new PasswordField();
	    contrasena.setPromptText("Ingrese la nueva contraseña");
	    
	    Label lblContrasenaConfimar = new Label("Confirmar contraeña: ");
	    lblContrasenaConfimar.getStyleClass().add("form-label");
	    PasswordField contrasenaConfirma = new PasswordField();
	    contrasenaConfirma.setPromptText("Ingrese de nuevo la contraseña");
	    
	    Button btnActualizar = new Button("Actualizar");
	    btnActualizar.getStyleClass().add("form-button");
	    
	    GridPane grid = new GridPane();
	    grid.getStyleClass().add("form-container");
	    grid.setHgap(20);
	    grid.setVgap(15);
	    grid.setAlignment(Pos.CENTER);

	    int row = 0;
	    grid.add(lblContrasena, 0, row);
	    grid.add(contrasena, 1, row++);

	    grid.add(lblContrasenaConfimar, 0, row);
	    grid.add(contrasenaConfirma, 1, row++);

	    btnActualizar.setOnAction((e) -> {
	    	try {
				
	    		CredencialesRequest request = new CredencialesRequest(contrasena.getText());
	    		onCambiarCredenciales(id, request);
	    		showNotification(btnActualizar.getScene(), "✅ Contraseña cambiada exitosamente", Color.GREEN);
			} catch (Exception ex) {
				showNotification(btnActualizar.getScene(), "❎ Error al cambiar la contraseña", Color.RED);
				System.out.println("Error tipo: "+ex);
			}
	    });
	    
	    VBox root = new VBox(20, lblTitulo, grid, btnActualizar);
	    root.setAlignment(Pos.CENTER);
	    root.setPadding(new Insets(30));
	    root.setStyle("-fx-background-color: #F8F9FA;");
	    return root;
	}
}
