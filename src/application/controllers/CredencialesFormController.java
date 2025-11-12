package application.controllers;

import application.models.request.CredencialesRequest;
import application.services.UsuarioService;
import application.utils.NotificationManager;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class CredencialesFormController {

    private static final String PASSWORD_REGEX = "^[A-Za-z0-9]{8,}$";

    private static void onCambiarCredenciales(String id, CredencialesRequest request) throws Exception {
        UsuarioService service = new UsuarioService();
        service.editarCredenciales(id, request);
    }

    public static VBox getScene(String title, String id) {

        Label lblTitulo = new Label(String.format("📝 %s de Usuario", title));
        lblTitulo.getStyleClass().add("form-title");

        //====== CAMPO PASSWORD ======//
        Label lblContrasena = new Label("Contraseña:");
        PasswordField contrasena = new PasswordField();
        contrasena.setPromptText("Nueva contraseña");
        TextField contrasenaVisible = new TextField();
        contrasenaVisible.setVisible(false);
        contrasenaVisible.setManaged(false);

        Button togglePass1 = new Button("👁");
        togglePass1.getStyleClass().add("login-eye-button");

        StackPane panePassword = crearPasswordPane(contrasena, contrasenaVisible, togglePass1);

        //====== CAMPO CONFIRM PASSWORD ======//
        Label lblContrasenaConfirmar = new Label("Confirmar contraseña:");
        PasswordField contrasenaConfirma = new PasswordField();
        contrasenaConfirma.setPromptText("Repetir contraseña");
        TextField contrasenaConfirmaVisible = new TextField();
        contrasenaConfirmaVisible.setVisible(false);
        contrasenaConfirmaVisible.setManaged(false);

        Button togglePass2 = new Button("👁");
        togglePass2.getStyleClass().add("login-eye-button");

        StackPane panePassword2 = crearPasswordPane(contrasenaConfirma, contrasenaConfirmaVisible, togglePass2);

        //====== INFO + BOTÓN ======//
        Label lblInfo = new Label("📌 Mín 8 caracteres, solo letras y números. Ambas deben coincidir.");
        lblInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #707070;");

        Button btnActualizar = new Button("Actualizar");
        btnActualizar.getStyleClass().add("form-button");
        btnActualizar.setDisable(true);

        agregarValidacionPassword(contrasena, contrasenaConfirma, btnActualizar);

        //====== GRID LAYOUT ======//
        GridPane grid = new GridPane();
        grid.getStyleClass().add("form-container");
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        int row = 0;
        grid.add(lblContrasena, 0, row);
        grid.add(panePassword, 1, row++);
        grid.add(lblContrasenaConfirmar, 0, row);
        grid.add(panePassword2, 1, row++);

        //====== ACCIÓN BOTÓN ======//
        btnActualizar.setOnAction(e -> {
            if (!contrasena.getText().equals(contrasenaConfirma.getText())) {
                NotificationManager.showNotification(btnActualizar.getScene(),
                        "❌ Las contraseñas no coinciden", Color.RED);
                return;
            }

            try {
                CredencialesRequest request = new CredencialesRequest(contrasena.getText());
                onCambiarCredenciales(id, request);
                NotificationManager.showNotification(btnActualizar.getScene(),
                        "✅ Contraseña cambiada exitosamente", Color.GREEN);
            } catch (Exception ex) {
                NotificationManager.showNotification(btnActualizar.getScene(),
                        "❎ Error al cambiar la contraseña", Color.RED);
                System.out.println("Error tipo: " + ex);
            }
        });

        VBox root = new VBox(20, lblTitulo, lblInfo, grid, btnActualizar);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F8F9FA;");

        return root;
    }

    // ✅ Controle mostrarse/ocultarse con ojito
    private static StackPane crearPasswordPane(PasswordField passField, TextField passVisible, Button toggleBtn) {

        passVisible.managedProperty().bind(passVisible.visibleProperty());
        passField.managedProperty().bind(passField.visibleProperty());

        passVisible.textProperty().bindBidirectional(passField.textProperty());

        toggleBtn.setOnAction(e -> {
            if (passVisible.isVisible()) {
                passVisible.setVisible(false);
                passField.setVisible(true);
                toggleBtn.setText("👁");
            } else {
                passVisible.setVisible(true);
                passField.setVisible(false);
                toggleBtn.setText("🙈");
            }
        });

        StackPane pane = new StackPane(passField, passVisible, toggleBtn);
        StackPane.setAlignment(toggleBtn, Pos.CENTER_RIGHT);
        StackPane.setMargin(toggleBtn, new Insets(0, 10, 0, 0));

        return pane;
    }

    // ✅ Validación en tiempo real
    private static void agregarValidacionPassword(PasswordField campo, PasswordField confirmar, Button btn) {
        ChangeListener<String> validar = (obs, ov, nv) -> {
            String pass = campo.getText();
            String conf = confirmar.getText();

            boolean valido = pass.matches(PASSWORD_REGEX);
            boolean coincide = pass.equals(conf);

            btn.setDisable(!(valido && coincide));
            campo.getStyleClass().removeAll("validar-contrasena-coincide", "validar-contrasena-no-coincide");
            confirmar.getStyleClass().removeAll("validar-contrasena-coincide", "validar-contrasena-no-coincide");

            campo.getStyleClass().add(valido ?
                    "validar-contrasena-coincide" :
                    "validar-contrasena-no-coincide");
            confirmar.getStyleClass().add(coincide ?
                    "validar-contrasena-coincide" :
                    "validar-contrasena-no-coincide");
        };

        campo.textProperty().addListener(validar);
        confirmar.textProperty().addListener(validar);
    }
}
