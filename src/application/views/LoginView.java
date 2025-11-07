package application.views;

import application.models.request.SesionRequest;
import application.models.response.Sesion;
import application.services.AutentificacionService;
import application.services.StorageService;
import application.session.SessionManager;
import application.utils.SceneManager;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.util.Duration;

public class LoginView {

	private final StorageService storage = new StorageService();
	
    public Scene getScene() {
        // 🔹 Logo principal (reemplaza el texto "LOGIN GAAF")
        Image logoImg = new Image(getClass().getResource("/application/resources/logoGAAF.png").toExternalForm());
        ImageView logoView = new ImageView(logoImg);
        logoView.setFitWidth(320);
        logoView.setPreserveRatio(true);
        logoView.setSmooth(true);
        logoView.setCache(true);

        // 🔹 Icono de candado
        Label lockIcon = new Label("🔒");
        lockIcon.getStyleClass().add("login-lock-icon");

        // 🔹 Campo Usuario
        TextField usernameField = new TextField();
        usernameField.setPromptText("Usuario");
        usernameField.setPrefWidth(280);
        usernameField.getStyleClass().add("login-textfield");

        // 🔹 Campo Contraseña con "Ojito" dentro
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        passwordField.setPrefWidth(250);
        passwordField.getStyleClass().add("login-textfield");

        TextField visiblePassword = new TextField();
        visiblePassword.setPromptText("Contraseña");
        visiblePassword.setVisible(false);
        visiblePassword.setManaged(false);
        visiblePassword.setPrefWidth(250);
        visiblePassword.getStyleClass().add("login-textfield");

        // 🔹 Ojito iluminado
        Button toggleEye = new Button("👁");
        toggleEye.getStyleClass().add("login-eye-button");

        toggleEye.setOnMouseEntered(e -> toggleEye.getStyleClass().add("login-eye-button:hover"));
        toggleEye.setOnMouseExited(e -> toggleEye.getStyleClass().remove("login-eye-button:hover"));

        toggleEye.setOnAction(e -> {
            if (visiblePassword.isVisible()) {
                passwordField.setText(visiblePassword.getText());
                visiblePassword.setVisible(false);
                visiblePassword.setManaged(false);
                passwordField.setVisible(true);
                passwordField.setManaged(true);
            } else {
                visiblePassword.setText(passwordField.getText());
                visiblePassword.setVisible(true);
                visiblePassword.setManaged(true);
                passwordField.setVisible(false);
                passwordField.setManaged(false);
            }
        });

        // 🔹 Caja con campo + ojito dentro
        StackPane passwordPane = new StackPane();
        passwordPane.setAlignment(Pos.CENTER_RIGHT);
        passwordPane.getChildren().addAll(passwordField, visiblePassword, toggleEye);
        StackPane.setMargin(toggleEye, new Insets(0, 10, 0, 0));

        // 🔹 Botón Login futurista
        Button loginButton = new Button("INGRESAR");
        loginButton.setPrefWidth(280);
        loginButton.getStyleClass().add("login-button");

        // 🔹 Evento de Login con notificación moderna
        loginButton.setOnAction(e -> {
            String user = usernameField.getText();
            String pass = passwordField.isVisible() ? passwordField.getText() : visiblePassword.getText();
            
            try {
            	
            	AutentificacionService service = new AutentificacionService();
            	Sesion sesion = service.iniciarSesion(new SesionRequest(user, pass));
            	SessionManager.getInstance().setSession(sesion.getToken(), sesion.getRol(), sesion.getUsuario(), sesion.getId());
            	System.out.println(SessionManager.getInstance().getToken());
            	storage.saveSession(sesion.getToken(), sesion.getRol());
            	
            	switch (sesion.getRol().toUpperCase()) {
                case "COORDINADOR_COMPRAS":
                    showNotification(loginButton.getScene(), "✔ Acceso Coord. Compras", Color.LIMEGREEN);
                    SceneManager.changeScene(new DashboardComprasView().getScene(), "Panel Compras");
                    break;
                case "JEFE_BODEGA":
                    showNotification(loginButton.getScene(), "✔ Acceso Jefe Bodega", Color.LIMEGREEN);
                    SceneManager.changeScene(new DashboardBodegaView().getScene(), "Panel Bodega");
                    break;
                case "GERENTE":
                    showNotification(loginButton.getScene(), "✔ Acceso Gerente", Color.LIMEGREEN);
                    SceneManager.changeScene(new DashboardGerenteView().getScene(), "Panel Gerente");
                    break;
                case "ADMIN":
                    showNotification(loginButton.getScene(), "✔ Acceso Administrador", Color.LIMEGREEN);
                    SceneManager.changeScene(new DashboardView().getScene(), "Panel de Administrador");
                    break;
                default:
                    showNotification(loginButton.getScene(), "❌ Rol desconocido: " + sesion.getRol(), Color.RED);
                    break;
            	}
            } catch (Exception ex) {
            	System.out.println(ex.getMessage());
            	usernameField.setText("");
            	passwordField.setText("");
            	showNotification(loginButton.getScene(), "❌ Credenciales incorrectas", Color.RED);
			}
            

           
        });

        // 🔹 Layout principal (logo reemplaza el título)
        VBox vbox = new VBox(15, logoView, lockIcon, usernameField, passwordPane, loginButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40));
        vbox.getStyleClass().add("login-root");

        Scene scene = new Scene(vbox, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/application/resources/application.css").toExternalForm());
        return scene;
    }

    // 🔹 Método para notificaciones modernas tipo Toast
    private void showNotification(Scene scene, String text, Color color) {
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

    // 🔹 Convertir Color a HEX
    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}