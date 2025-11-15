package application.controllers;

import application.models.request.CredencialesRequest;
import application.models.response.ResponseCommon;
import application.services.UsuarioService;
import application.utils.NotificationManager;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


//================================
//	CONTROLADOR FORMULARIO DE CREDENCIALES
//================================
public class CredencialesFormController {

    // ================================
    //     VALIDACIÓN PASSWORD
    // ================================
    private static final String PASSWORD_REGEX = "^[A-Za-z0-9]{8,}$";

    // ================================
    //     ATRIBUTOS DEL FORMULARIO
    // ================================
    private static PasswordField contrasena;
    private static PasswordField contrasenaConfirma;
    private static Button btnActualizar;

    private static String userId;


    // ================================
    //     ACCIÓN: EDITAR CREDENCIALES
    // ================================
    private static ResponseCommon onCambiarCredenciales(String id, CredencialesRequest request) throws Exception {
        UsuarioService service = new UsuarioService();
        return service.editarCredenciales(id, request);
    }

    // ================================
    //     CREACIÓN DE LA VISTA
    // ================================
    public static VBox getScene(String title, String id) {

        userId = id;

        Label lblTitulo = new Label(String.format("📝 %s de Usuario", title));
        lblTitulo.getStyleClass().add("form-title");

        // ================================
        //     CAMPO: CONTRASEÑA
        // ================================
        Label lblContrasena = new Label("Contraseña:");
        contrasena = new PasswordField();
        contrasena.setPromptText("Nueva contraseña");

        TextField contrasenaVisible = new TextField();
        contrasenaVisible.setVisible(false);
        contrasenaVisible.setManaged(false);

        Button togglePass1 = new Button("👁");
        togglePass1.getStyleClass().add("login-eye-button");

        StackPane panePassword = crearPasswordPane(contrasena, contrasenaVisible, togglePass1);

        // ================================
        //     CAMPO: CONFIRMAR CONTRASEÑA
        // ================================
        Label lblContrasenaConfirmar = new Label("Confirmar contraseña:");
        contrasenaConfirma = new PasswordField();
        contrasenaConfirma.setPromptText("Repetir contraseña");

        TextField contrasenaConfirmaVisible = new TextField();
        contrasenaConfirmaVisible.setVisible(false);
        contrasenaConfirmaVisible.setManaged(false);

        Button togglePass2 = new Button("👁");
        togglePass2.getStyleClass().add("login-eye-button");

        StackPane panePassword2 = crearPasswordPane(contrasenaConfirma, contrasenaConfirmaVisible, togglePass2);

        Label lblInfo = new Label("📌 Mín 8 caracteres, solo letras y números. Ambas deben coincidir.");
        lblInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #707070;");

        btnActualizar = new Button("Actualizar");
        btnActualizar.getStyleClass().add("form-button");
        btnActualizar.setDisable(true);

        agregarValidacionPassword(contrasena, contrasenaConfirma, btnActualizar);

        // ================================
        //     GRID PRINCIPAL
        // ================================
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
        btnActualizar.setOnAction(e -> cambiarCredenciales());

        //====== ROOT ======//
        VBox root = new VBox(20, lblTitulo, lblInfo, grid, btnActualizar);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F8F9FA;");

        return root;
    }



    // ================================
    //     ACCIÓN PRINCIPAL: GUARDAR CAMBIO
    // ================================
    private static void cambiarCredenciales() {

        if (!validarCampos()) {
            return;
        }

        try {
            CredencialesRequest request = new CredencialesRequest(contrasena.getText());
            var response  = onCambiarCredenciales(userId, request);
            
            if(response.getStatus() != 200) {            	
            	manejarRespuesta(true, String.format("❌ ", response.getMessage()));
            } else {
            	manejarRespuesta(true, String.format("✔ ", response.getMessage()));
            }       
        } catch (Exception ex) {
            manejarRespuesta(false, "❌ Error al cambiar la contraseña");
            System.out.println("Error tipo: " + ex);
        } finally {        	
        	contrasena.setText(null);
        	contrasena.setPromptText("Nueva contraseña");
        	contrasenaConfirma.setText(null);
        	contrasenaConfirma.setPromptText("Repetir contraseña");
        }
    }


    // ================================
    //     VALIDACIÓN GENERAL DE LOS CAMPOS
    // ================================
    private static boolean validarCampos() {

        String pass = contrasena.getText();
        String conf = contrasenaConfirma.getText();

        if (!pass.matches(PASSWORD_REGEX)) {
            manejarRespuesta(false, "❌ La contraseña debe tener mínimo 8 caracteres y ser alfanumérica");
            return false;
        }

        if (!pass.equals(conf)) {
            manejarRespuesta(false, "❌ Las contraseñas no coinciden");
            return false;
        }

        return true;
    }


    // ================================
    //     MANEJO DE RESPUESTA
    // ================================
    private static void manejarRespuesta(boolean exito, String mensaje) {
        Color color = exito ? Color.GREEN : Color.RED;

        NotificationManager.showNotification(
                btnActualizar.getScene(),
                mensaje,
                color
        );
    }


    // ================================
    //     BOTON DE MOSTRAR/OCULTAR CONTRASEÑA
    // ================================
    private static StackPane crearPasswordPane(PasswordField passField, TextField passVisible, Button toggleBtn) {

        passVisible.managedProperty().bind(passVisible.visibleProperty());
        passField.managedProperty().bind(passField.visibleProperty());

        passVisible.textProperty().bindBidirectional(passField.textProperty());

        toggleBtn.setOnAction(e -> {
            boolean visible = passVisible.isVisible();
            passVisible.setVisible(!visible);
            passField.setVisible(visible);
            toggleBtn.setText(visible ? "👁" : "🙈");
        });

        StackPane pane = new StackPane(passField, passVisible, toggleBtn);
        StackPane.setAlignment(toggleBtn, Pos.CENTER_RIGHT);
        StackPane.setMargin(toggleBtn, new Insets(0, 10, 0, 0));

        return pane;
    }

    // ================================
    //     VALIDACIÓN DINÁMICA
    // ================================
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
