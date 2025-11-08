package application.controllers;

import application.models.response.Common;
import application.models.response.ResponseCommon;
import application.models.request.CommonRequest; 
import application.services.MetodoPagoService;
import application.utils.NotificationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MetodoPagoFormController {

	
	
	private final StackPane content;
	
	
	
	

	 public MetodoPagoFormController(StackPane content) {
		this.content = content;
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
	                NotificationManager.showNotification(btnAccion.getScene(), "✅ Método de pago creado correctamente", Color.GREEN);
	                txtNombre.clear();
	            } else {
	                var response = onActionEditar(metodoPago.getId(), request);
	                NotificationManager.showNotification(btnAccion.getScene(), response.getMessage(), Color.GREEN);
	                txtNombre.clear();
	            }
	        } catch (Exception ex) {
	        	NotificationManager.showNotification(btnAccion.getScene(), "❎ Error al guardar el método de pago", Color.RED);
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
