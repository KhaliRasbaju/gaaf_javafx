package application.controllers;

import application.models.request.UsuarioRequest;
import application.models.response.UsuarioResponse;
import application.services.UsuarioService;
import application.utils.NotificationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class UsuarioEditarFormController {

	
	private static void onActualizar(String id, UsuarioRequest request) throws Exception {
		try {
			
			UsuarioService service = new UsuarioService();
			service.editarUsuario(id, request);
			
			
		} catch (Exception ex) {
			throw new Exception("Error tipo" + ex);
		}
	}
	
	
	public static VBox getScene(String title, UsuarioResponse usuario) {
		Label lblTitulo = new Label(String.format("📝 %s de Usuario", title));
	    lblTitulo.getStyleClass().add("form-title");
	    
	    
	    
	    Label lblUsuario = new Label("Usuario:");
	    lblUsuario.getStyleClass().add("form-label");
	    TextField txtUsuario = new TextField();
	    txtUsuario.setPromptText("Usuario");
	    txtUsuario.getStyleClass().add("form-field");

	    Label lblCorreo = new Label("Correo:");
	    lblCorreo.getStyleClass().add("form-label");	  
	    TextField txtCorreo = new TextField();
	    txtCorreo.setPromptText("Correo electrónico");
	    txtCorreo.getStyleClass().add("form-field");

	    Label lblNombre = new Label("Nombre:");
	    lblNombre.getStyleClass().add("form-label");
	    TextField txtNombre = new TextField();
	    txtNombre.setPromptText("Nombre completo");
	    txtNombre.getStyleClass().add("form-field");

	    Label lblTelefono = new Label("Teléfono:");
	    lblTelefono.getStyleClass().add("form-label");
	    TextField txtTelefono = new TextField();
	    txtTelefono.setPromptText("Teléfono");
	    txtTelefono.getStyleClass().add("form-field");

	    Label lblRol = new Label("Rol:");
	    lblRol.getStyleClass().add("form-label");
	    Label lblRolUsuario = new Label();
	    lblRolUsuario.getStyleClass().add("form-label");
	    
	    Button btnActualizar = new Button("Actualizar");
	    btnActualizar.getStyleClass().add("form-button");
	    
	    if(usuario != null) {
	    	txtUsuario.setText(usuario.getUsuario());
	    	txtCorreo.setText(usuario.getCorreo());
	    	txtNombre.setText(usuario.getNombre());
	    	txtTelefono.setText(usuario.getTelefono());
	    	lblRolUsuario.setText(usuario.getRol());
	    }
	    
	    GridPane grid = new GridPane();
	    grid.getStyleClass().add("form-container");
	    grid.setHgap(20);
	    grid.setVgap(15);
	    grid.setAlignment(Pos.CENTER);

	    int row = 0;
	    grid.add(lblUsuario, 0, row);
	    grid.add(txtUsuario, 1, row++);

	    grid.add(lblCorreo, 0, row);
	    grid.add(txtCorreo, 1, row++);

	    grid.add(lblNombre, 0, row);
	    grid.add(txtNombre, 1, row++);

	    grid.add(lblTelefono, 0, row);
	    grid.add(txtTelefono, 1, row++);
	    grid.add(lblRol, 0, row);
	    grid.add(lblRolUsuario, 1, row++);
	    
	    
	    btnActualizar.setOnAction((e) -> {
	    	
	    	
	    	
	    	try {	  
	    		UsuarioRequest request = new UsuarioRequest(txtNombre.getText(), txtUsuario.getText(), txtCorreo.getText(), txtTelefono.getText(), lblRolUsuario.getText());
	    		onActualizar(usuario.getId(), request);
	    		NotificationManager.showNotification(btnActualizar.getScene(), "✅ Información de usuario actualizada correctamente", Color.GREEN);
			} catch (Exception ex) {
				NotificationManager.showNotification(btnActualizar.getScene(), "❎ Error al actulizar la información del usuario", Color.RED);
				System.out.println("Error tipo:" + ex);
			}
	    });
	    
	    VBox root = new VBox(20, lblTitulo, grid, btnActualizar);
	    root.setAlignment(Pos.CENTER);
	    root.setPadding(new Insets(30));
	    root.setStyle("-fx-background-color: #F8F9FA;");
	    return root;

	}
}
