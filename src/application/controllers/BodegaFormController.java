package application.controllers;

import java.util.List;

import application.models.request.BodegaRequest;
import application.models.response.Bodega;
import application.models.response.ResponseCommon;
import application.services.BodegaService;
import application.utils.NotificationManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class BodegaFormController {

    private final StackPane content;

    // 🔹 Campos como atributos para poder usarlos fuera de getScene()
    private TextField txtNombre;
    private TextField txtUbicacion;
    private Button btnAccion;

    // 🔹 Bodega actual (null si es registro)
    private Bodega bodegaActual;

    public BodegaFormController(StackPane content) {
        this.content = content;
    }

    // ⭐ Acción registrar
    private static ResponseCommon onActionRegistrar(BodegaRequest request) {
        try {
            BodegaService service = new BodegaService();
            return service.crearBodega(request);
        } catch (Exception ex) {
            throw new RuntimeException("Error al registrar: " + ex);
        }
    }

    // ⭐ Acción actualizar
    private static ResponseCommon onActionActualizar(Long id, BodegaRequest request) {
        try {
            BodegaService service = new BodegaService();
            return service.editarBodega(id, request);
        } catch (Exception ex) {
            throw new RuntimeException("Error al actualizar: " + ex);
        }
    }

    /** ============================================================
     *  🔷 Construcción del formulario
     *  ============================================================ */
    public VBox getScene(String title, Bodega bodega) {

        this.bodegaActual = bodega;

        // --- Título ---
        Label lblTitulo = new Label(String.format("🏢 %s de Bodega", title));
        lblTitulo.getStyleClass().add("form-title");

        // --- GRID ---
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

        // -------------------------------
        // 🔹 Campo Nombre
        // -------------------------------
        Label lblNombre = new Label("Nombre de la bodega");
        lblNombre.getStyleClass().add("form-label");
        grid.add(lblNombre, 0, 0);

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre de la bodega");
        txtNombre.getStyleClass().add("form-field");
        txtNombre.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            if (!newText.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) return null;
            if (newText.contains("  ")) return null;
            if (newText.startsWith(" ")) return null;

            return change;
        }));

        if (bodega != null && bodega.getNombre() != null)
            txtNombre.setText(bodega.getNombre());

        grid.add(txtNombre, 0, 1, 2, 1);

        // -------------------------------
        // 🔹 Campo Ubicación
        // -------------------------------
        Label lblUbicacion = new Label("Ubicación de la bodega");
        lblUbicacion.getStyleClass().add("form-label");
        grid.add(lblUbicacion, 0, 2);

        txtUbicacion = new TextField();
        txtUbicacion.setPromptText("Ubicación de la bodega");
        txtUbicacion.getStyleClass().add("form-field");

        if (bodega != null && bodega.getUbicacion() != null)
            txtUbicacion.setText(bodega.getUbicacion());

        grid.add(txtUbicacion, 0, 3, 2, 1);

        // -------------------------------
        // 🔹 Botón Registrar / Actualizar
        // -------------------------------
        btnAccion = new Button(bodega == null ? "Registrar" : "Actualizar");
        btnAccion.getStyleClass().add("form-button");
        btnAccion.setPrefWidth(200);

        btnAccion.setOnAction(e -> guardarBodega());

        HBox contBoton = new HBox(btnAccion);
        contBoton.setAlignment(Pos.CENTER);
        contBoton.setPadding(new Insets(10, 0, 0, 0));

        // --- Layout principal ---
        VBox root = new VBox(20, lblTitulo, grid, contBoton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F8F9FA;");

        return root;
    }


    /** ============================================================
     *  🔷 Lógica principal: Guardar Bodega
     *  ============================================================ */
    private void guardarBodega() {

        if (!validarCampos()) {
            return;
        }

        BodegaRequest request = new BodegaRequest(
                txtNombre.getText(),
                txtUbicacion.getText()
        );

        try {
            ResponseCommon response = (bodegaActual == null)
                    ? onActionRegistrar(request)
                    : onActionActualizar(bodegaActual.getId(), request);

            manejarRespuesta(response);

            if (bodegaActual == null && response.getStatus() == 201) {
                limpiarCampos();
            }

        } catch (Exception ex) {
            NotificationManager.showNotification(
                    btnAccion.getScene(),
                    "❌ Error al guardar la bodega",
                    Color.RED
            );
            ex.printStackTrace();
        }

        recargarVista();
    }

    /** ============================================================
     *  🔷 Validaciones
     *  ============================================================ */
    private boolean validarCampos() {

        if (txtNombre.getText().isEmpty() || txtUbicacion.getText().isEmpty()) {
            NotificationManager.showNotification(
                    btnAccion.getScene(),
                    "⚠ Por favor, completa todos los campos.",
                    Color.ORANGE
            );
            return false;
        }

        return true;
    }

    /** ============================================================
     *  🔷 Manejo de respuesta
     *  ============================================================ */
    private void manejarRespuesta(ResponseCommon response) {

        boolean exito = response.getStatus() == 200 || response.getStatus() == 201;
        Color color = exito ? Color.GREEN : Color.YELLOWGREEN;

        String icono;

        if (bodegaActual == null) {
            icono = exito ? "✔ " : "❌ ";
        } else {
            icono = exito ? "✏ " : "❌ ";
        }

        NotificationManager.showNotification(
                btnAccion.getScene(),
                icono + response.getMessage(),
                color
        );
    }

    /** ============================================================
     *  🔷 Limpieza de campos
     *  ============================================================ */
    private void limpiarCampos() {
        txtNombre.clear();
        txtUbicacion.clear();
    }

    /** ============================================================
     *  🔷 Recarga de vista principal
     *  ============================================================ */
    private void recargarVista() {
        try {
            BodegaService service = new BodegaService();
            List<Bodega> bodegas = service.obtenerBodegas();

            BodegaController controller = new BodegaController(content);
            content.getChildren().setAll(controller.getScene(bodegas));

        } catch (Exception ex) {
            throw new RuntimeException("Error al recargar bodegas", ex);
        }
    }

}
