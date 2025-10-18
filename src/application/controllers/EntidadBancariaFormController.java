package application.controllers;

import application.models.response.Common;
import application.models.response.ResponseCommon;
import application.services.EntidadService;
import application.services.EntidadService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;

public class EntidadBancariaFormController {

    public static StackPane getScene(String modo, Common entidad) {
        StackPane content = new StackPane();

        Label lblTitulo = new Label(modo + " Entidad Bancaria");
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre de la entidad");

        if (entidad != null) {
            txtNombre.setText(entidad.getNombre());
        }

        Button btnGuardar = new Button("Guardar");
        Button btnCancelar = new Button("Cancelar");

        HBox botones = new HBox(10, btnGuardar, btnCancelar);
        botones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, lblTitulo, txtNombre, botones);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        btnCancelar.setOnAction(e -> {
            try {
                EntidadService service = new EntidadService();
                var entidades = service.obtenerEntidades();
                EntidadBancariaController controller = new EntidadBancariaController(content);
                content.getChildren().setAll(controller.getScene(entidades));
            } catch (Exception ex) {
                System.out.println("Error tipo: " + ex);
            }
        });

        btnGuardar.setOnAction(e -> {
            try {
                EntidadService service = new EntidadService();
                ResponseCommon respuesta;

                if (entidad == null) {
                    respuesta = service.crearEntidad(txtNombre.getText());
                } else {
                    respuesta = service.editarEntidad(entidad.getId(), txtNombre.getText());
                }

                Color color = respuesta.getStatus() == 200 ? Color.GREEN : Color.RED;
                ProductoController.showNotification(
                        content.getScene(), respuesta.getMessage(), color
                );

            } catch (Exception ex) {
                System.out.println("Error tipo: " + ex);
            }
        });

        content.getChildren().add(layout);
        return content;
    }
}
