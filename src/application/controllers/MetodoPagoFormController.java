package application.controllers;

import application.models.response.Common;
import application.models.response.ResponseCommon;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MetodoPagoFormController {

	
	
	private final StackPane content;
	
	
	
	

	 public MetodoPagoFormController(StackPane content) {
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
	
	private static void onActionCrear(CommonRequest request) throws Exception {
		try {
			MetodoPagoService service = new MetodoPagoService();
			service.crearMetodo(request);
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	
	private static ResponseCommon onActionEditar( Long id,CommonRequest request) throws Exception {
		try {
			MetodoPagoService service = new MetodoPagoService();
			return service.editarMetodo(id,request);
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
			throw new Exception("Error tipo: "+ ex);
		}
	}
	
	
	public static VBox getScene(String accion, Common metodoPago) {
	    Label lblTitulo = new Label(accion + " Método de Pago");
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

	    // --- Campos ---
	    Label lblNombre = new Label("Nombre del método de pago");
	    lblNombre.getStyleClass().add("form-label");
	    grid.add(lblNombre, 0, 0);

	    TextField txtNombre = new TextField();
	    txtNombre.setPromptText("Nombre del método de pago");
	    txtNombre.getStyleClass().add("form-field");
	    if (metodoPago != null && metodoPago.getNombre() != null)
	        txtNombre.setText(metodoPago.getNombre());
	    grid.add(txtNombre, 0, 1, 2, 1); // ocupa 2 columnas para centrar

	    // --- Botón ---
	    Button btnAccion = new Button(accion);
	    btnAccion.getStyleClass().add("form-button");
	    btnAccion.setPrefWidth(200);

	    HBox contBoton = new HBox(btnAccion);
	    contBoton.setAlignment(Pos.CENTER);
	    contBoton.setPadding(new Insets(10, 0, 0, 0));

	    Label lblMensaje = new Label();
	    lblMensaje.setTextFill(Color.RED);

	    // --- Acción del botón ---
	    btnAccion.setOnAction(e -> {
	        if (txtNombre.getText().isEmpty()) {
	            lblMensaje.setText("⚠️ El nombre es obligatorio.");
	            lblMensaje.setTextFill(Color.RED);
	            return;
	        }

	        try {
	            CommonRequest request = new CommonRequest(txtNombre.getText());

	            if (metodoPago == null) {
	                onActionCrear(request);
	                showNotification(btnAccion.getScene(), "✅ Método de pago creado correctamente", Color.GREEN);
	                txtNombre.clear();
	            } else {
	                var response = onActionEditar(metodoPago.getId(), request);
	                showNotification(btnAccion.getScene(), response.getMessage(), Color.GREEN);
	                txtNombre.clear();
	            }
	        } catch (Exception ex) {
	            showNotification(btnAccion.getScene(), "❎ Error al guardar el método de pago", Color.RED);
	            System.out.println("Error: " + ex);
	        }
	    });

	    // --- Layout principal ---
	    VBox root = new VBox(20, lblTitulo, grid, contBoton, lblMensaje);
	    root.setAlignment(Pos.TOP_CENTER);
	    root.setPadding(new Insets(35));
	    root.setStyle("-fx-background-color: #F8F9FA;");

	    return root;
	}

}
