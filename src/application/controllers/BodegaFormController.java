package application.controllers;

import application.models.request.BodegaRequest;
import application.models.response.Bodega;
import application.services.BodegaService;
import application.utils.NotificationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class BodegaFormController {
	
	


    /** 🔹 Lógica de acción para registrar una bodega */
    private static void onActionRegistrar(BodegaRequest request) {
        try {
            BodegaService service = new BodegaService();
            System.out.println(service.crearBodega(request)); 
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
            throw new RuntimeException("Error tipo " + ex);
        }
    }
    
    private static void onActionActualizar(Long id, BodegaRequest request) {
		try {
			BodegaService service = new BodegaService();
			service.editarBodega(id, request);
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}

    /** 🔹 Construye y retorna el formulario de registro de bodega */
    public static VBox getScene(String title, Bodega bodega) {
        // --- Título ---
        Label lblTitulo = new Label(String.format("🏢 %s de Bodega", title));
        lblTitulo.getStyleClass().add("form-title");

        // --- GRID PARA CAMPOS ---
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

        // Campo Nombre
        Label lblNombre = new Label("Nombre de la bodega");
        lblNombre.getStyleClass().add("form-label");
        grid.add(lblNombre, 0, 0);

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre de la bodega");
        txtNombre.getStyleClass().add("form-field");
        if (bodega != null && bodega.getNombre() != null)
            txtNombre.setText(bodega.getNombre());
        grid.add(txtNombre, 0, 1, 2, 1); // ocupa dos columnas

        // Campo Ubicación
        Label lblUbicacion = new Label("Ubicación de la bodega");
        lblUbicacion.getStyleClass().add("form-label");
        grid.add(lblUbicacion, 0, 2);

        TextField txtUbicacion = new TextField();
        txtUbicacion.setPromptText("Ubicación de la bodega");
        txtUbicacion.getStyleClass().add("form-field");
        if (bodega != null && bodega.getUbicacion() != null)
            txtUbicacion.setText(bodega.getUbicacion());
        grid.add(txtUbicacion, 0, 3, 2, 1); // ocupa dos columnas

        // --- Botón ---
        Button btnAccion = new Button(bodega == null ? "Registrar" : "Actualizar");
        btnAccion.getStyleClass().add("form-button");
        btnAccion.setPrefWidth(200);

        HBox contBoton = new HBox(btnAccion);
        contBoton.setAlignment(Pos.CENTER);
        contBoton.setPadding(new Insets(10, 0, 0, 0));

        Label lblMensaje = new Label();
        lblMensaje.setTextFill(Color.RED);

        // --- Acción del botón ---
        btnAccion.setOnAction(e -> {
            if (txtNombre.getText().isEmpty() || txtUbicacion.getText().isEmpty()) {
                lblMensaje.setText("⚠️ Por favor, completa todos los campos.");
                lblMensaje.setTextFill(Color.RED);
                return;
            }

            BodegaRequest request = new BodegaRequest(txtNombre.getText(), txtUbicacion.getText());

            try {
                if (bodega == null) {
                    onActionRegistrar(request);
                    NotificationManager.showNotification(btnAccion.getScene(), "✅ Bodega registrada correctamente", Color.GREEN);
                    lblMensaje.setText("✅ Bodega registrada correctamente.");
                    lblMensaje.setTextFill(Color.GREEN);
                    txtNombre.clear();
                    txtUbicacion.clear();
                } else {
                    onActionActualizar(bodega.getId(), request);
                    NotificationManager.showNotification(btnAccion.getScene(), "✏️ Bodega actualizada correctamente", Color.BLUE);
                    lblMensaje.setText("✏️ Bodega actualizada correctamente.");
                    lblMensaje.setTextFill(Color.BLUE);
                }
            } catch (Exception ex) {
            	NotificationManager.showNotification(btnAccion.getScene(), "❎ Error al guardar la bodega", Color.RED);
                lblMensaje.setText("❎ Error al guardar la bodega");
                lblMensaje.setTextFill(Color.RED);
                System.out.println("Error tipo: " + ex);
            }
        });

        // --- Layout principal ---
        VBox root = new VBox(20, lblTitulo, grid, contBoton, lblMensaje);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F8F9FA;");

        return root;
    }


}
