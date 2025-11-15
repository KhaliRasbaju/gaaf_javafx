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
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class UsuarioEditarFormController {

    // ================================
    //     CONTENEDOR PRINCIPAL
    // ================================
	private final StackPane content;

    // ================================
    //     CONSTRUCTOR
    // ================================
	public UsuarioEditarFormController(StackPane content) {
		this.content = content;
	}
	
    // ============================================
    //  CONTROL DE HABILITACIÓN
    // ============================================
    private boolean disable = true;

    // ============================================
    //     MÉTODO PARA DESHABILITAR / HABILITAR CAMPOS
    // ============================================
    private void aplicarDisable(TextField txtUsuario, TextField txtCorreo,
                                TextField txtNombre, TextField txtTelefono,
                                Button btnActualizar) {
        txtUsuario.setDisable(disable);
        txtCorreo.setDisable(disable);
        txtNombre.setDisable(disable);
        txtTelefono.setDisable(disable);
        btnActualizar.setDisable(disable);
    }

    // ============================
    //    ACCIÓN: ACTUALIZAR
    // ============================
    private void onActualizar(String id, UsuarioRequest request) throws Exception {
        UsuarioService service = new UsuarioService();
        service.editarUsuario(id, request);
    }
    
    // ================================
    //      VISTA PRINCIPAL
     // ================================

    public VBox getScene(String title, UsuarioResponse usuario) {

        Label lblTitulo = new Label(String.format("📝 %s Usuario", title));
        lblTitulo.getStyleClass().add("form-title");

        Button toggleActive = new Button("Editar");
        toggleActive.getStyleClass().add("btn-active");

        Label lblUsuario = new Label("Usuario:");
        lblUsuario.getStyleClass().add("form-label");
        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario");
        txtUsuario.getStyleClass().add("form-field");

        txtUsuario.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            return newText.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]*") ? change : null;
        }));

        Label lblCorreo = new Label("Correo:");
        lblCorreo.getStyleClass().add("form-label");
        TextField txtCorreo = new TextField();
        txtCorreo.setPromptText("Correo electrónico");
        txtCorreo.getStyleClass().add("form-field");

        txtCorreo.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            return newText.matches("[a-zA-Z0-9@._-]*") ? change : null;
        }));

        Label lblNombre = new Label("Nombre:");
        lblNombre.getStyleClass().add("form-label");
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre completo");
        txtNombre.getStyleClass().add("form-field");

        txtNombre.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (!newText.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) return null;
            if (newText.contains("  ")) return null;
            if (newText.startsWith(" ")) return null;
            return change;
        }));

        Label lblTelefono = new Label("Teléfono:");
        lblTelefono.getStyleClass().add("form-label");
        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");
        txtTelefono.getStyleClass().add("form-field");

        txtTelefono.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d*") ? change : null
        ));

        Label lblRol = new Label("Rol:");
        lblRol.getStyleClass().add("form-label");
        Label lblRolUsuario = new Label();
        lblRolUsuario.getStyleClass().add("form-label");

        Button btnActualizar = new Button("Actualizar");
        btnActualizar.getStyleClass().add("form-button");

        // Asignar valores si vienen del backend
        if (usuario != null) {
            txtUsuario.setText(usuario.getUsuario());
            txtCorreo.setText(usuario.getCorreo());
            txtNombre.setText(usuario.getNombre());
            txtTelefono.setText(usuario.getTelefono());
            lblRolUsuario.setText(usuario.getRol());
        }

        aplicarDisable(txtUsuario, txtCorreo, txtNombre, txtTelefono, btnActualizar);

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

        btnActualizar.setOnAction(e -> {
        	
        	if(txtNombre.getText().isEmpty() 
        			|| txtUsuario.getText().isEmpty() 
        			|| txtCorreo.getText().isEmpty() 
        			|| txtTelefono.getText().isEmpty() 
        			||lblRolUsuario.getText().isEmpty()
        			|| !txtCorreo.getText().matches("^[A-Za-z0-9._%+-]+@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,6}$")
        			) {
        		NotificationManager.showNotification(btnActualizar.getScene(), "⚠ Completa todos los campos. El correo debe ser tener formato como gaaf@gaaf.co", Color.ORANGE);
        		return;
        	}
        	
            try {
                UsuarioRequest request = new UsuarioRequest(
                        txtNombre.getText(),
                        txtUsuario.getText(),
                        txtCorreo.getText(),
                        txtTelefono.getText(),
                        lblRolUsuario.getText()
                );
                onActualizar(usuario.getId(), request);
                NotificationManager.showNotification(btnActualizar.getScene(),
                        "✔ Información de usuario actualizada correctamente",
                        Color.GREEN);
            } catch (Exception ex) {
                NotificationManager.showNotification(btnActualizar.getScene(),
                        "❌ Error al actualizar la información del usuario",
                        Color.RED);
                ex.printStackTrace();
            }
            
            try {
				UsuarioService service = new UsuarioService();
				var user = service.obtenerUsuario(usuario.getId());
            	UsuarioEditarFormController controller = new UsuarioEditarFormController(content);
            	content.getChildren().setAll(controller.getScene("Información del ", user));
            	
            	
			} catch (Exception ex) {
				throw new RuntimeException("Error tipo: " + ex);
			}
        });

        toggleActive.setOnAction(e -> {
            disable = !disable; // alterna entre true/false
            aplicarDisable(txtUsuario, txtCorreo, txtNombre, txtTelefono, btnActualizar);
        });

        VBox root = new VBox(20, lblTitulo, toggleActive, grid, btnActualizar);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F8F9FA;");

        return root;
    }
}
