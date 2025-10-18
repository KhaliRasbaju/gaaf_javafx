package application.controllers;

import application.models.response.Common;
import application.models.request.CommonRequest; // si no lo tienes, puedes crear uno simple
import application.services.MetodoPagoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;

public class MetodoPagoFormController {

    public static VBox getScene(String accion, Common metodoPago) {
        Label lblTitulo = new Label(accion + " Método de Pago");
        lblTitulo.getStyleClass().add("form-title");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del método de pago");

        if (metodoPago != null && metodoPago.getNombre() != null)
            txtNombre.setText(metodoPago.getNombre());

        Button btnAccion = new Button(accion);
        btnAccion.getStyleClass().add("login-button");
        btnAccion.setPrefWidth(150);

        Label lblMensaje = new Label();

        btnAccion.setOnAction(e -> {
            if (txtNombre.getText().isEmpty()) {
                lblMensaje.setText("⚠️ El nombre es obligatorio.");
                lblMensaje.setTextFill(Color.RED);
                return;
            }

            MetodoPagoService service = new MetodoPagoService();
            try {
                if (metodoPago == null) {
                    service.crearMetodo(txtNombre.getText());
                    lblMensaje.setText("✅ Método de pago registrado correctamente.");
                    lblMensaje.setTextFill(Color.GREEN);
                    txtNombre.clear();
                } else {
                    service.editarMetodo(metodoPago.getId(), txtNombre.getText());
                    lblMensaje.setText("✏️ Método de pago actualizado correctamente.");
                    lblMensaje.setTextFill(Color.BLUE);
                }
            } catch (Exception ex) {
                lblMensaje.setText("❌ Error al procesar la solicitud.");
                lblMensaje.setTextFill(Color.RED);
                System.out.println("Error: " + ex);
            }
        });

        VBox form = new VBox(10, lblTitulo, txtNombre, btnAccion, lblMensaje);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(20));
        return form;
    }
}
