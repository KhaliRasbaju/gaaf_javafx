package application.controllers;



import application.models.request.RegistrarRequest;
import application.models.request.UsuarioRequest;
import application.models.response.UsuarioResponse;
import application.services.AutentificacionService;
import application.services.UsuarioService;
import application.utils.NotificationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RegistrarFormController {
	
	
	
	private static void onActionRegistrar(RegistrarRequest request) {
		try {
			AutentificacionService service = new AutentificacionService();
			service.registrarUsuario(request);
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
			throw new RuntimeException("Error tipo "+ ex);
		}
	}
	
	private static void onActionEditar( String id, UsuarioRequest request) {
		try {
			UsuarioService service = new UsuarioService();
			service.editarUsuario(id, request);
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
			throw new RuntimeException("Error tipo "+ ex);
		}
	}
	
	
	public static VBox getScene(String title, UsuarioResponse usuario) {
	    // --- TÍTULO ---
	    Label lblTitulo = new Label(String.format("📝 %s de Usuario", title));
	    lblTitulo.getStyleClass().add("form-title");

	    // --- CAMPOS ---
	    TextField txtUsuario = new TextField();
	    txtUsuario.setPromptText("Usuario");
	    txtUsuario.getStyleClass().add("form-field");
	    txtUsuario.setTextFormatter(new TextFormatter<>(change -> {
	        String newText = change.getControlNewText();

	        // Solo letras y números
	        if (!newText.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]*")) {
	            return null; // Bloquear carácter
	        }

	        return change;
	    }));
	    TextField txtCorreo = new TextField();
	    txtCorreo.setPromptText("Correo electrónico");
	    txtCorreo.getStyleClass().add("form-field");
	    
	    txtCorreo.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            // Solo letras, números, @, ., _, -
            if (newText.matches("[a-zA-Z0-9@._-]*")) {
                return change;
            }
            return null;
        }));

	    TextField txtNombre = new TextField();
	    txtNombre.setPromptText("Nombre completo");
	    txtNombre.getStyleClass().add("form-field");
	    

        txtNombre.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            // Solo letras (incluye tildes y ñ) y espacios
            if (!newText.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                return null; // bloquea caracteres inválidos
            }

            // Evitar dos espacios seguidos
            if (newText.contains("  ")) {
                return null;
            }

            // Evitar espacio al inicio
            if (newText.startsWith(" ")) {
                return null;
            }

            return change;
        }));

	    TextField txtTelefono = new TextField();
	    txtTelefono.setPromptText("Teléfono");
	    txtTelefono.getStyleClass().add("form-field");
	    txtTelefono.setTextFormatter(new TextFormatter<>(change -> {
        	if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
        

	 // Campo de contraseña real
	    PasswordField passwordField = new PasswordField();
	    passwordField.setPromptText("Contraseña");
	    passwordField.getStyleClass().add("form-field");

	    // Campo visible (texto normal)
	    TextField txtContraseña = new TextField();
	    txtContraseña.setPromptText("Contraseña");
	    txtContraseña.getStyleClass().add("form-field");
	    txtContraseña.setVisible(false);
	    txtContraseña.setManaged(false);

	    // Botón con icono de ojo
	    Button toggleEye = new Button("👁");
	    toggleEye.getStyleClass().add("login-eye-button");
	    toggleEye.setFocusTraversable(false); // No robar foco

	    toggleEye.setOnAction(e -> {
	        boolean showing = txtContraseña.isVisible();

	        if (showing) {
	            // Volver a ocultar contraseña
	            passwordField.setText(txtContraseña.getText());
	            txtContraseña.setVisible(false);
	            txtContraseña.setManaged(false);
	            passwordField.setVisible(true);
	            passwordField.setManaged(true);
	            toggleEye.setText("👁");
	        } else {
	            // Mostrar contraseña
	            txtContraseña.setText(passwordField.getText());
	            txtContraseña.setVisible(true);
	            txtContraseña.setManaged(true);
	            passwordField.setVisible(false);
	            passwordField.setManaged(false);

	            toggleEye.setText("🙈"); 
	        }
	    });


	    // Mantener valores sincronizados mientras se escribe
	    passwordField.textProperty().addListener((obs, oldV, newV) -> {
	        if (!txtContraseña.isVisible()) txtContraseña.setText(newV);
	    });
	    txtContraseña.textProperty().addListener((obs, oldV, newV) -> {
	        if (txtContraseña.isVisible()) passwordField.setText(newV);
	    });

	    // Contenedor con icono a la derecha
	    StackPane passwordPane = new StackPane();
	    passwordPane.setAlignment(Pos.CENTER_RIGHT);
	    passwordPane.getChildren().addAll(passwordField, txtContraseña, toggleEye);
	    StackPane.setMargin(toggleEye, new Insets(0, 10, 0, 0));

	    ComboBox<String> cbRol = new ComboBox<>();
	    cbRol.getItems().addAll("Administrador", "Coordinador de Compras", "Gerente", "Jefe de Bodega");
	    cbRol.setPromptText("Selecciona un rol");
	    cbRol.getStyleClass().add("form-field");

	    Button btnRegistrar = new Button(usuario == null ? "Registrar" : "Actualizar");
	    btnRegistrar.getStyleClass().add("form-button");

	    

	    // --- CARGA DE DATOS SI EXISTE EL USUARIO ---
	    if (usuario != null) {
	        txtUsuario.setText(usuario.getUsuario());
	        txtCorreo.setText(usuario.getCorreo());
	        txtNombre.setText(usuario.getNombre());
	        txtTelefono.setText(usuario.getTelefono());
	        txtContraseña.setVisible(false);

	        switch (usuario.getRol()) {
	            case "ADMIN" -> cbRol.setValue("Administrador");
	            case "COORDINADOR_COMPRAS" -> cbRol.setValue("Coordinador de Compras");
	            case "GERENTE" -> cbRol.setValue("Gerente");
	            case "JEFE_BODEGA" -> cbRol.setValue("Jefe de Bodega");
	            default -> System.out.println("Rol no reconocido: " + usuario.getRol());
	        }
	    }

	    // --- GRIDPANE ---
	    GridPane grid = new GridPane();
	    grid.getStyleClass().add("form-container");
	    grid.setHgap(20);
	    grid.setVgap(15);
	    grid.setAlignment(Pos.CENTER);

	    int row = 0;
	    grid.add(new Label("Usuario:"), 0, row);
	    grid.add(txtUsuario, 1, row++);

	    grid.add(new Label("Correo:"), 0, row);
	    grid.add(txtCorreo, 1, row++);

	    grid.add(new Label("Nombre:"), 0, row);
	    grid.add(txtNombre, 1, row++);

	    grid.add(new Label("Teléfono:"), 0, row);
	    grid.add(txtTelefono, 1, row++);

	    if (usuario == null) { 
	        grid.add(new Label("Contraseña:"), 0, row);
	        grid.add(passwordPane, 1, row++); //
	    }



	    grid.add(new Label("Rol:"), 0, row);
	    grid.add(cbRol, 1, row++);

	    // --- BOTÓN ---
	    btnRegistrar.setOnAction(e -> {
	        try {
	            String rol = "";
	            boolean camposIncompletos =
	                    txtUsuario.getText().isEmpty() ||
	                    txtCorreo.getText().isEmpty() ||
	                    txtNombre.getText().isEmpty() ||
	                    txtTelefono.getText().isEmpty() ||
	                    (usuario == null && txtContraseña.getText().isEmpty()) ||
	                    cbRol.getValue() == null;

	            if (camposIncompletos) {
	            	NotificationManager.showNotification(btnRegistrar.getScene(),"⚠️ Por favor, completa todos los campos." , Color.RED);
	                return;
	            }

	            switch (cbRol.getValue()) {
	                case "Administrador" -> rol = "ADMIN";
	                case "Coordinador de Compras" -> rol = "COORDINADOR_COMPRAS";
	                case "Gerente" -> rol = "GERENTE";
	                case "Jefe de Bodega" -> rol = "JEFE_BODEGA";
	                default -> System.out.println("Rol no reconocido: " + cbRol.getValue());
	            }

	            if (usuario != null) {
	                UsuarioRequest request = new UsuarioRequest(
	                        txtNombre.getText(),
	                        txtUsuario.getText(),
	                        txtCorreo.getText(),
	                        txtTelefono.getText(),
	                        rol
	                );
	                onActionEditar(usuario.getId(), request);
	                NotificationManager.showNotification(btnRegistrar.getScene(), "✅ Usuario actualizado correctamente", Color.GREEN);
	            } else {
	                RegistrarRequest request = new RegistrarRequest(
	                        txtUsuario.getText(),
	                        txtCorreo.getText(),
	                        txtNombre.getText(),
	                        txtTelefono.getText(),
	                        txtContraseña.getText(),
	                        rol
	                );
	                onActionRegistrar(request);
	                NotificationManager.showNotification(btnRegistrar.getScene(), "✅ Usuario creado correctamente", Color.GREEN);
	            }

	          

	            txtUsuario.clear();
	            txtCorreo.clear();
	            txtNombre.clear();
	            txtTelefono.clear();
	            txtContraseña.clear();
	            cbRol.setValue(null);

	        } catch (Exception ex) {
	            System.out.println("Error tipo: " + ex);
	            NotificationManager.showNotification(btnRegistrar.getScene(), "❌ Error al registrar/actualizar usuario", Color.RED);
	        }
	    });

	    // --- VBOX PRINCIPAL ---
	    VBox root = new VBox(20, lblTitulo, grid, btnRegistrar);
	    root.setAlignment(Pos.CENTER);
	    root.setPadding(new Insets(30));
	    root.setStyle("-fx-background-color: #F8F9FA;");

	    return root;
	}

}
