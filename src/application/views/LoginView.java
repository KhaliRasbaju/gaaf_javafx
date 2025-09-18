package application.views;

import application.utils.SceneManager;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginView {

    public Scene getScene() {
        // 🔹 Título principal
        Label title = new Label("LOGIN GAAF");
        title.setFont(Font.font("Orbitron", FontWeight.BOLD, 32));
        title.setTextFill(Color.CYAN);
        title.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,255,255,0.8), 25, 0.4, 0, 0);");

        // 🔹 Icono de candado
        Label lockIcon = new Label("🔒");
        lockIcon.setFont(Font.font("Segoe UI Emoji", 60));
        lockIcon.setTextFill(Color.WHITE);
        lockIcon.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,255,255,0.7), 20, 0.4, 0, 0);");

        // 🔹 Campo Usuario
        TextField usernameField = new TextField();
        usernameField.setPromptText("Usuario");
        usernameField.setPrefWidth(280);
        usernameField.setStyle(
                "-fx-background-color: rgba(44, 62, 80, 0.9);" +
                "-fx-text-fill: white; -fx-prompt-text-fill: gray;" +
                "-fx-background-radius: 10; -fx-padding: 6 10;"
        );

        // 🔹 Campo Contraseña con "Ojito" dentro
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        passwordField.setPrefWidth(250);
        passwordField.setStyle(
                "-fx-background-color: rgba(44, 62, 80, 0.9);" +
                "-fx-text-fill: white; -fx-prompt-text-fill: gray;" +
                "-fx-background-radius: 10; -fx-padding: 6 10;"
        );

        TextField visiblePassword = new TextField();
        visiblePassword.setPromptText("Contraseña");
        visiblePassword.setVisible(false);
        visiblePassword.setManaged(false);
        visiblePassword.setPrefWidth(250);
        visiblePassword.setStyle(passwordField.getStyle());

        // 🔹 Ojito iluminado
        Button toggleEye = new Button("👁");
        toggleEye.setStyle(
                "-fx-background-color: transparent; -fx-cursor: hand;" +
                "-fx-font-size: 14; -fx-text-fill: white;"
        );

        toggleEye.setOnMouseEntered(e -> toggleEye.setStyle(
                "-fx-background-color: transparent; -fx-cursor: hand;" +
                "-fx-font-size: 14; -fx-text-fill: cyan;" +
                "-fx-effect: dropshadow(gaussian, cyan, 15, 0.5, 0, 0);"
        ));
        toggleEye.setOnMouseExited(e -> toggleEye.setStyle(
                "-fx-background-color: transparent; -fx-cursor: hand;" +
                "-fx-font-size: 14; -fx-text-fill: white;"
        ));

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
        loginButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #00c6ff, #0072ff);" +
                "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;" +
                "-fx-background-radius: 20; -fx-cursor: hand; -fx-padding: 8 0;"
        );

        loginButton.setOnMouseEntered(e -> loginButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #0072ff, #00c6ff);" +
                "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;" +
                "-fx-background-radius: 20; -fx-effect: dropshadow(gaussian, cyan, 15, 0.4, 0, 0);"
        ));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #00c6ff, #0072ff);" +
                "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;" +
                "-fx-background-radius: 20; -fx-padding: 8 0;"
        ));

        // 🔹 Evento de Login con notificación moderna
        loginButton.setOnAction(e -> {
            String user = usernameField.getText();
            String pass = passwordField.isVisible() ? passwordField.getText() : visiblePassword.getText();

            if (user.equals("admin") && pass.equals("123")) {
                showNotification(loginButton.getScene(), "✔ Acceso autorizado", Color.LIMEGREEN);
                SceneManager.changeScene(new DashboardView().getScene(), "Panel de Datos");
            } else {
                showNotification(loginButton.getScene(), "❌ Credenciales incorrectas", Color.RED);
            }
        });

        // 🔹 Layout principal
        VBox vbox = new VBox(15, title, lockIcon, usernameField, passwordPane, loginButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40));
        vbox.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #141E30, #243B55);" +
                "-fx-background-radius: 15;"
        );

        return new Scene(vbox, 800, 600);
    }

    // 🔹 Método para notificaciones modernas tipo Toast
    private void showNotification(Scene scene, String text, Color color) {
        Label notification = new Label(text);
        notification.setTextFill(Color.WHITE);
        notification.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        notification.setStyle(
                "-fx-background-color: " + toHex(color) + ";" +
                "-fx-background-radius: 10; -fx-padding: 10 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 15, 0.5, 0, 0);"
        );

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

