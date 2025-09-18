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
		title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

		
		TextField usernameField = new TextField();
		usernameField.setPromptText("Usuario");
		usernameField.setStyle("-fx-background-radius: 8; -fx-padding: 8;");
		
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Contraseña");
		passwordField.setStyle("-fx-background-radius: 8; -fx-padding: 8;");

		
		Button loginButton = new Button("Ingresar");
		loginButton.setStyle(
		        "-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-weight: bold;" +
		        "-fx-background-radius: 8; -fx-padding: 8 16;"
		    );
		
		Label message = new Label();
		message.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

		
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
		 vbox.setPadding(new Insets(30));
		    vbox.setStyle("-fx-background-color: linear-gradient(to bottom, #ECF0F1, #BDC3C7);");
		    vbox.setStyle("-fx-background-color: #1ABC9C;");

		return new Scene(vbox, 800, 600);
		
	}

}