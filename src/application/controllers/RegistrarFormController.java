package application.controllers;



import application.models.request.RegistrarRequest;
import application.models.request.UsuarioRequest;
import application.models.response.UsuarioResponse;
import application.services.AutentificacionService;
import application.services.UsuarioService;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;
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

	    TextField txtCorreo = new TextField();
	    txtCorreo.setPromptText("Correo electrónico");
	    txtCorreo.getStyleClass().add("form-field");

	    TextField txtNombre = new TextField();
	    txtNombre.setPromptText("Nombre completo");
	    txtNombre.getStyleClass().add("form-field");

	    TextField txtTelefono = new TextField();
	    txtTelefono.setPromptText("Teléfono");
	    txtTelefono.getStyleClass().add("form-field");

	    PasswordField txtContraseña = new PasswordField();
	    txtContraseña.setPromptText("Contraseña");
	    txtContraseña.getStyleClass().add("form-field");

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

	    if (usuario == null) { // Solo si es registro
	        grid.add(new Label("Contraseña:"), 0, row);
	        grid.add(txtContraseña, 1, row++);
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
	               showNotification(btnRegistrar.getScene(),"⚠️ Por favor, completa todos los campos." , Color.RED);
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
	                showNotification(btnRegistrar.getScene(), "✅ Usuario actualizado correctamente", Color.GREEN);
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
	                showNotification(btnRegistrar.getScene(), "✅ Usuario creado correctamente", Color.GREEN);
	            }

	          

	            txtUsuario.clear();
	            txtCorreo.clear();
	            txtNombre.clear();
	            txtTelefono.clear();
	            txtContraseña.clear();
	            cbRol.setValue(null);

	        } catch (Exception ex) {
	            System.out.println("Error tipo: " + ex);
	            showNotification(btnRegistrar.getScene(), "❌ Error al registrar/actualizar usuario", Color.RED);
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
