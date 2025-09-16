package application.views;


import application.utils.SceneManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginView {

	
	

	public Scene getScene() {
		Label title = new Label("Iniciar Sesion");
		
		TextField usernameField = new TextField();
		usernameField.setPromptText("Usuario");
		
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Contraseña");
		
		Button loginButton = new Button("Ingresar");
		
		
		Label message = new Label();
		
		loginButton.setOnAction(e -> {
			String user = usernameField.getText();
			String password = passwordField.getText();
			
			if(user.equals("admin") && password.equals("123")) {
				message.setText("Inicio completo");
				SceneManager.changeScene(new DashboardView().getScene(), "Panel de Datos");
			} else {
				message.setText("Credenciales incorrectas ❌");
			}
			
		});
		
		VBox vbox = new VBox(10, title, usernameField, passwordField, loginButton, message);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(20, 20, 20, 20));
		return new Scene(vbox, 400, 300);
		
	}

}
