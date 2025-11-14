package application.controllers;

import application.models.request.CommonRequest;
import application.models.response.Common;
import application.models.response.ResponseCommon;
import application.services.EntidadService;
import application.utils.NotificationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class EntidadBancariaFormController {

    private final StackPane content;

    public EntidadBancariaFormController(StackPane content) {
        this.content = content;
    }

    // ===========================
    //   LÓGICA DE SERVICIO
    // ===========================

    private static ResponseCommon crearEntidad(CommonRequest request) {
        try {
            return new EntidadService().crearEntidad(request);
        } catch (Exception ex) {
            throw new RuntimeException("Error tipo: " + ex);
        }
    }

    private static ResponseCommon editarEntidad(Long id, CommonRequest request) {
        try {
            return new EntidadService().editarEntidad(id, request);
        } catch (Exception ex) {
            throw new RuntimeException("Error tipo: " + ex);
        }
    }

    // ===========================
    //  ESCENA DEL FORMULARIO
    // ===========================

    public VBox getScene(String modo, Common entidad) {

        Label lblTitulo = new Label(modo + " Entidad Bancaria");
        lblTitulo.getStyleClass().add("form-title");

        GridPane grid = new GridPane();
        grid.getStyleClass().add("form-container");
        grid.setHgap(25);
        grid.setVgap(15);
        grid.setPadding(new Insets(25));
        grid.setAlignment(Pos.CENTER);

        // Columnas
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        // -----------------------------------------
        // CAMPO NOMBRE
        // -----------------------------------------
        Label lblNombre = new Label("Nombre de la entidad");
        lblNombre.getStyleClass().add("form-label");
        grid.add(lblNombre, 0, 0);

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre de la entidad");
        txtNombre.getStyleClass().add("form-field");

        txtNombre.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            if (!newText.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*"))
                return null;
            if (newText.contains("  "))
                return null;
            if (newText.startsWith(" "))
                return null;

            return change;
        }));

        if (entidad != null)
            txtNombre.setText(entidad.getNombre());

        grid.add(txtNombre, 0, 1, 2, 1);

        // -----------------------------------------
        // BOTÓN GUARDAR
        // -----------------------------------------
        Button btnGuardar = new Button(entidad == null ? "Crear" : "Actualizar");
        btnGuardar.getStyleClass().add("form-button");
        btnGuardar.setPrefWidth(200);

        HBox contBoton = new HBox(btnGuardar);
        contBoton.setAlignment(Pos.CENTER);
        contBoton.setPadding(new Insets(10, 0, 0, 0));

        // -----------------------------------------
        // ACCIÓN DEL BOTÓN
        // -----------------------------------------

        btnGuardar.setOnAction(e -> {

            // Validación
            if (txtNombre.getText().isEmpty()) {
                NotificationManager.showNotification(
                        btnGuardar.getScene(),
                        "⚠ El nombre es obligatorio.",
                        Color.ORANGE
                );
                return;
            }

            try {
                CommonRequest request = new CommonRequest(txtNombre.getText());
                ResponseCommon response;

                // CREATE / UPDATE
                if (entidad == null)
                    response = crearEntidad(request);
                else
                    response = editarEntidad(entidad.getId(), request);

                // Limpia campo luego de guardar
                txtNombre.clear();

                // Notificaciones según estado
                if (entidad == null) { // Creación
                    if (response.getStatus() != 201) {
                        NotificationManager.showNotification(
                                btnGuardar.getScene(),
                                "❌ " + response.getMessage(),
                                Color.YELLOWGREEN
                        );
                    } else {
                        NotificationManager.showNotification(
                                btnGuardar.getScene(),
                                "✔ " + response.getMessage(),
                                Color.GREEN
                        );
                    }

                } else { // Edición
                    if (response.getStatus() != 200) {
                        NotificationManager.showNotification(
                                btnGuardar.getScene(),
                                "❌ " + response.getMessage(),
                                Color.YELLOWGREEN
                        );
                    } else {
                        NotificationManager.showNotification(
                                btnGuardar.getScene(),
                                "✏ " + response.getMessage(),
                                Color.GREEN
                        );
                    }
                }

            } catch (Exception ex) {
                NotificationManager.showNotification(
                        btnGuardar.getScene(),
                        "❌ Error al guardar la entidad bancaria",
                        Color.RED
                );
                System.out.println("Error tipo: " + ex);
            }

            // -----------------------------------------
            // RECARGA DE TABLA DESPUÉS DE GUARDAR
            // -----------------------------------------
            try {
                EntidadService service = new EntidadService();
                var entidades = service.obtenerEntidades();

                EntidadBancariaController controller = new EntidadBancariaController(content);
                content.getChildren().setAll(controller.getScene(entidades));

            } catch (Exception ex) {
                throw new RuntimeException("Error tipo: " + ex);
            }

        });

        VBox root = new VBox(20, lblTitulo, grid, contBoton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F8F9FA;");

        return root;
    }
}
