package application.controllers;

import application.models.request.TransaccionRequest;
import application.models.response.ResponseCommon;
import application.models.response.Bodega;
import application.models.response.Producto;
import application.services.TransaccionService;
import application.services.ProductoService;
import application.services.BodegaService;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.util.Duration;

import java.util.List;

public class TransaccionFormController {

    private final StackPane content;
    private static Long idProducto;
    private static Long idBodega;
    private static Long idPedido;
  

    public TransaccionFormController(StackPane content) {
        this.content = content;
    }

    // ✅ Carga la escena del formulario

    // 🔹 Convierte color a hexadecimal (para notificaciones)
    private static String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    // 🔹 Notificación tipo toast
    private static void showNotification(Scene scene, String text, Color color) {
        Label notification = new Label(text);
        notification.getStyleClass().add("notification-toast");
        notification.setStyle("-fx-background-color: " + toHex(color) + ";"
                + "-fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 8px;");

        Popup popup = new Popup();
        popup.getContent().add(notification);
        popup.setAutoFix(true);

        double x = scene.getWindow().getX() + scene.getWidth() / 2 - 100;
        double y = scene.getWindow().getY() + scene.getHeight() - 100;
        popup.show(scene.getWindow(), x, y);

        FadeTransition fade = new FadeTransition(Duration.seconds(2.5), notification);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(ev -> popup.hide());
        fade.play();
    }
    
    
    private static ResponseCommon onActionCrear(TransaccionRequest request) throws Exception {
		try {
			TransaccionService service = new TransaccionService();
			return service.crearTransaccion(request);
		} catch (Exception ex) {
			
			System.out.println("Error tipo: " + ex);
			
			throw new Exception("Error tipo: " + ex);
		}
	}
    
    
    private static List<Bodega> bodegas() throws Exception{
    	try {
    		BodegaService service = new BodegaService();
    		return service.obtenerBodegas();
		} catch (Exception ex) {
			throw new Exception("Error tipo: " + ex);
		}
    }
    
    private static Bodega bodega(Long id) throws Exception{
    	try {
    		BodegaService service = new BodegaService();
    		return service.obtenerBodega(id);
		} catch (Exception ex) {
			throw new Exception("Error tipo: " + ex);
		}
    }
    
    private static List<Producto> productos() throws Exception {
		try {
			ProductoService service = new ProductoService();
			return service.obtenerProductos();
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
			throw new Exception("Error tipo: " + ex);
		}
	}
    
    private static Producto producto(Long id) throws Exception {
    	try {
    		ProductoService service = new ProductoService();
    		return service.obtenerProducto(id);
		} catch (Exception ex) {
			throw new Exception("Error tipo: " + ex);
		}
    }
    
   

    // ✅ Crea el formulario de transacción
    public VBox getScene() throws Exception {

        // --- CAMPOS ---
        ComboBox<Producto> cmbProducto = new ComboBox<>();
        ComboBox<Bodega> cmbBodega = new ComboBox<>();
        ComboBox<String> cmbTipo = new ComboBox<>();
        TextField txtIdPedido = new TextField();
        TextField txtCantidad = new TextField();
        TextArea txtObservacion = new TextArea();
        txtIdPedido.setVisible(false);

        // Aplicación de clases CSS
        cmbProducto.getStyleClass().add("form-field");
        cmbBodega.getStyleClass().add("form-field");
        cmbTipo.getStyleClass().add("form-field");
        txtIdPedido.getStyleClass().add("form-field");
        txtCantidad.getStyleClass().add("form-field");
        txtObservacion.getStyleClass().add("form-textarea");

        List<Producto> productos = productos();
        List<Bodega> bodegas = bodegas();

        cmbProducto.setItems(FXCollections.observableArrayList(productos));
        cmbProducto.setPromptText("Seleccione un producto");

        cmbProducto.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
        cmbProducto.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Seleccione un producto" : item.getNombre());
            }
        });
        
        
        cmbBodega.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Bodega item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("%s (%s)", item.getNombre(), item.getUbicacion()));
            }
        });
        cmbBodega.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Bodega item, boolean empty) {
                super.updateItem(item, empty);
                // En el botón mostramos nombre + ubicación
                setText(empty || item == null ? "Seleccione una bodega"
                        : item.getNombre());
            }
        });
        cmbBodega.setItems(FXCollections.observableArrayList(bodegas));
        cmbBodega.setPromptText("Seleccione una bodega");

        cmbTipo.setItems(FXCollections.observableArrayList("ENTRADA", "MERMA", "PRODUCCION"));
        cmbTipo.setPromptText("Seleccione tipo");

        txtIdPedido.setPromptText("ID Pedido (opcional)");
        txtCantidad.setPromptText("Cantidad");
        txtObservacion.setPromptText("Observación");

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.getStyleClass().add("form-button");

     // --- GRIDPANE 2 COLUMNAS ---
        GridPane grid = new GridPane();
        grid.getStyleClass().add("form-container");
        grid.setHgap(20);
        grid.setVgap(15);
        
        Label lblMensaje = new Label();
        lblMensaje.setTextFill(Color.RED);
        Label lblIdPedido = new Label("ID Pedido:");
        grid.add(lblIdPedido, 0, 3);
        grid.add(txtIdPedido, 1, 3);
        lblIdPedido.setVisible(false);
       
        cmbTipo.setOnAction(e -> {
        	System.out.println(cmbTipo.getValue());
        	 if(cmbTipo.getValue().equals("ENTRADA")) {
             	txtIdPedido.setVisible(true);
             	lblIdPedido.setVisible(true);
             }else {
            	txtIdPedido.setVisible(false);
             	lblIdPedido.setVisible(false);
             }

        });
        
        // --- EVENTO BOTÓN ---
        btnRegistrar.setOnAction(e -> {
            try {
                 idBodega = cmbBodega.getValue() != null ? cmbBodega.getValue().getId() : null;
                 idProducto = cmbProducto.getValue() != null ? cmbProducto.getValue().getId() : null;
                String tipo = cmbTipo.getValue();

                if (idBodega == null || tipo == null || txtCantidad.getText().isEmpty()) {
                    lblMensaje.setText("⚠️ Todos los campos obligatorios deben completarse.");
                    lblMensaje.setTextFill(Color.RED);
                    return;
                }
               
            
                 idPedido = txtIdPedido.getText().isEmpty() ? null : Long.parseLong(txtIdPedido.getText());

                TransaccionRequest request = new TransaccionRequest(
                        idProducto,
                        idPedido != null ? idPedido: null,
                        txtObservacion.getText(),
                        tipo,
                        idBodega,
                        Integer.parseInt(txtCantidad.getText())
                );

                var response = onActionCrear(request);
              
                if(response.getStatus() != 200) {
                	showNotification(btnRegistrar.getScene(), response.getMessage(), Color.RED);
                } else {
                	  showNotification(btnRegistrar.getScene(), response.getMessage(), Color.GREEN);
                }
                
                cmbProducto.setValue(null);
                cmbBodega.setValue(null);
                cmbTipo.setValue(null);
                txtIdPedido.clear();
                txtCantidad.clear();
                txtObservacion.clear();
                lblMensaje.setText("");
            } catch (Exception ex) {
                System.out.println("Error tipo: " + ex);
                showNotification(btnRegistrar.getScene(), "❌ Error al registrar la transacción", Color.RED);
            }
        });

        

        grid.add(new Label("Producto:"), 0, 0);
        grid.add(cmbProducto, 1, 0);

        grid.add(new Label("Bodega:"), 0, 1);
        grid.add(cmbBodega, 1, 1);

        
        grid.add(new Label("Tipo:"), 0, 2);
        grid.add(cmbTipo, 1, 2);
    

       
		grid.add(new Label("Cantidad:"), 0, 4);
		grid.add(txtCantidad, 1, 4);
        

        grid.add(new Label("Observación:"), 0, 5);
        grid.add(txtObservacion, 1, 5);
        grid.setAlignment(Pos.CENTER);
        // --- VBOX PRINCIPAL ---
        VBox root = new VBox(15, new Label("Registrar Transacción") {{
            getStyleClass().add("form-title");
        }}, grid, btnRegistrar, lblMensaje);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F8F9FA;");

        return root;
    }



}
