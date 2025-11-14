package application.controllers;

import application.models.response.Common;
import application.models.response.ResponseCommon;
import application.models.request.CommonRequest;
import application.services.MetodoPagoService;
import application.utils.NotificationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MetodoPagoFormController {

    private final StackPane content;

    public MetodoPagoFormController(StackPane content) {
        this.content = content;
    }

    // =============================
    //     LÓGICA DE SERVICIO
    // =============================
    private static ResponseCommon onActionCrear(CommonRequest request) {
        try {
            MetodoPagoService service = new MetodoPagoService();
            return service.crearMetodo(request);
        } catch (Exception ex) {
            throw new RuntimeException("Error tipo: " + ex);
        }
    }

    private static ResponseCommon onActionEditar(Long id, CommonRequest request) {
        try {
            MetodoPagoService service = new MetodoPagoService();
            return service.editarMetodo(id, request);
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
            throw new RuntimeException("Error tipo: " + ex);
        }
    }

    // =============================
    //   ESCENA DEL FORMULARIO
    // =============================
    public VBox getScene(String accion, Common metodoPago) {

        Label lblTitulo = new Label(accion + " Método de Pago");
        lblTitulo.getStyleClass().add("form-title");

        // --- CONTENEDOR GRID ---
        GridPane grid = new GridPane();
        grid.getStyleClass().add("form-container");
        grid.setHgap(25);
        grid.setVgap(15);
        grid.setPadding(new Insets(25));
        grid.setAlignment(Pos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        // =============================
        //          CAMPO NOMBRE
        // =============================
        Label lblNombre = new Label("Nombre del método de pago");
        lblNombre.getStyleClass().add("form-label");

        grid.add(lblNombre, 0, 0);

        TextField txtNombre = new TextField();
        txtNombre.getStyleClass().add("form-field");
        txtNombre.setPromptText("Nombre del método de pago");

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

        if (metodoPago != null)
            txtNombre.setText(metodoPago.getNombre());

        grid.add(txtNombre, 0, 1, 2, 1);

        // =============================
        //         BOTÓN ACCIÓN
        // =============================
        Button btnAccion = new Button(accion);
        btnAccion.getStyleClass().add("form-button");
        btnAccion.setPrefWidth(200);

        HBox contBoton = new HBox(btnAccion);
        contBoton.setAlignment(Pos.CENTER);
        contBoton.setPadding(new Insets(10, 0, 0, 0));

        // =============================
        //     ACCIÓN DEL BOTÓN
        // =============================
        btnAccion.setOnAction(e -> {

            // --- Validación ---
            if (txtNombre.getText().isEmpty()) {
                NotificationManager.showNotification(
                        btnAccion.getScene(),
                        "⚠ El nombre es obligatorio.",
                        Color.ORANGE
                );
                return;
            }

            try {
                CommonRequest request = new CommonRequest(txtNombre.getText());

                ResponseCommon response = (metodoPago == null)
                        ? onActionCrear(request)
                        : onActionEditar(metodoPago.getId(), request);

                txtNombre.clear();

                // -------------------------
                // NOTIFICACIONES
                // -------------------------
                if (metodoPago == null) { // Crear
                    if (response.getStatus() != 201) {
                        NotificationManager.showNotification(
                                btnAccion.getScene(),
                                "❌ " + response.getMessage(),
                                Color.YELLOWGREEN
                        );
                    } else {
                        NotificationManager.showNotification(
                                btnAccion.getScene(),
                                "✔ " + response.getMessage(),
                                Color.GREEN
                        );
                    }
                } else { // Editar
                    if (response.getStatus() != 200) {
                        NotificationManager.showNotification(
                                btnAccion.getScene(),
                                "❌ " + response.getMessage(),
                                Color.YELLOWGREEN
                        );
                    } else {
                        NotificationManager.showNotification(
                                btnAccion.getScene(),
                                "✏ " + response.getMessage(),
                                Color.GREEN
                        );
                    }
                }

            } catch (Exception ex) {
                NotificationManager.showNotification(
                        btnAccion.getScene(),
                        "❌ Error al guardar el método de pago",
                        Color.RED
                );
                System.out.println("Error: " + ex);
            }

            // -------------------------
            // RECARGAR TABLA
            // -------------------------
            try {
                MetodoPagoService service = new MetodoPagoService();
                var metodos = service.obtenerMetodos();

                MetodoPagoController controller = new MetodoPagoController(content);
                content.getChildren().setAll(controller.getScene(metodos));

            } catch (Exception ex) {
                throw new RuntimeException("Error tipo: " + ex);
            }

        });

        // =============================
        //     LAYOUT FINAL
        // =============================
        VBox root = new VBox(20, lblTitulo, grid, contBoton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(35));
        root.setStyle("-fx-background-color: #F8F9FA;");

        return root;
    }
}
