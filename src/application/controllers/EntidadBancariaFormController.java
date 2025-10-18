package application.controllers;

import application.models.request.CommonRequest;
import application.models.response.Common;
import application.models.response.ResponseCommon;
import application.services.EntidadService;
import application.services.EntidadService;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;

public class EntidadBancariaFormController {

	private final StackPane content;
	
	
	
	 public EntidadBancariaFormController(StackPane content) {
		this.content = content;
	}

	 private static String toHex(Color color) {
	        return String.format("#%02X%02X%02X",
	                (int) (color.getRed() * 255),
	                (int) (color.getGreen() * 255),
	                (int) (color.getBlue() * 255));
	    }

    /** 🔹 Muestra un pequeño mensaje tipo Toast */
    private static void showNotification(Scene scene, String text, Color color) {
        Label notification = new Label(text);
        notification.getStyleClass().add("notification-toast");
        notification.setStyle("-fx-background-color: " + toHex(color) + ";"
                + "-fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 8px;");

        Popup popup = new Popup();
        popup.getContent().add(notification);
        popup.setAutoFix(true);

        // Posición en parte inferior central
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

	
	
	private static void onActionCrear(CommonRequest request) {
		try {
			 EntidadService service = new EntidadService();
			 service.crearEntidad(request);
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	
	
	private static void onActionEditar(Long id,CommonRequest request) {
		try {
			EntidadService service = new EntidadService();
			service.editarEntidad(id, request);
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	
	
	
	
	public static VBox getScene(String modo, Common entidad) {
	    Label lblTitulo = new Label(modo + " Entidad Bancaria");
	    lblTitulo.getStyleClass().add("form-title");

	    // --- GRID FORMULARIO ---
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

	    // --- Campo Nombre ---
	    Label lblNombre = new Label("Nombre de la entidad");
	    lblNombre.getStyleClass().add("form-label");
	    grid.add(lblNombre, 0, 0);

	    TextField txtNombre = new TextField();
	    txtNombre.setPromptText("Nombre de la entidad");
	    txtNombre.getStyleClass().add("form-field");
	    if (entidad != null && entidad.getNombre() != null)
	        txtNombre.setText(entidad.getNombre());
	    grid.add(txtNombre, 0, 1, 2, 1); // ocupa dos columnas

	    // --- Botón ---
	    Button btnGuardar = new Button(entidad == null ? "Crear" : "Actualizar");
	    btnGuardar.getStyleClass().add("form-button");
	    btnGuardar.setPrefWidth(200);

	    HBox contBoton = new HBox(btnGuardar);
	    contBoton.setAlignment(Pos.CENTER);
	    contBoton.setPadding(new Insets(10, 0, 0, 0));

	    Label lblMensaje = new Label();
	    lblMensaje.setTextFill(Color.RED);

	    // --- Acción del botón ---
	    btnGuardar.setOnAction(e -> {
	        if (txtNombre.getText().isEmpty()) {
	            lblMensaje.setText("⚠️ El nombre es obligatorio.");
	            lblMensaje.setTextFill(Color.RED);
	            return;
	        }

	        try {
	            CommonRequest request = new CommonRequest(txtNombre.getText());
	            if (entidad == null) {
	                onActionCrear(request);
	                showNotification(btnGuardar.getScene(), "✅ Entidad bancaria creada correctamente", Color.GREEN);
	                txtNombre.clear();
	            } else {
	                onActionEditar(entidad.getId(), request);
	                showNotification(btnGuardar.getScene(), "✅ Entidad bancaria actualizada correctamente", Color.GREEN);
	                txtNombre.clear();
	            }
	        } catch (Exception ex) {
	            showNotification(btnGuardar.getScene(), "❎ Error al guardar la entidad bancaria", Color.RED);
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
