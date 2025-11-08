package application.controllers;

import application.models.request.CredencialesRequest;
import application.services.UsuarioService;
import application.utils.NotificationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CredencialesFormController {
	
	
	
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
	    		NotificationManager.showNotification(btnActualizar.getScene(), "✅ Contraseña cambiada exitosamente", Color.GREEN);
			} catch (Exception ex) {
				NotificationManager.showNotification(btnActualizar.getScene(), "❎ Error al cambiar la contraseña", Color.RED);
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
