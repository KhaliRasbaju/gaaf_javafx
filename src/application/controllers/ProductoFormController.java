package application.controllers;

import application.models.request.ProductoRequest;
import application.models.response.Producto;
import application.services.ProductoService;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;

public class ProductoFormController {

    /** 🔹 Convierte Color a formato HEX */
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

        double x = scene.getWindow().getX() + scene.getWidth() / 2 - 100;
        double y = scene.getWindow().getY() + scene.getHeight() - 100;
        popup.show(scene.getWindow(), x, y);

        FadeTransition fade = new FadeTransition(Duration.seconds(2.5), notification);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(ev -> popup.hide());
        fade.play();
    }

    /** 🔹 Acción para registrar un producto */
    private static void onActionRegistrar(ProductoRequest request) {
        try {
            ProductoService service = new ProductoService();
            service.crearProducto(request);
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
            throw new RuntimeException("Error tipo " + ex);
        }
    }

    /** 🔹 Acción para actualizar un producto existente */
    private static void onActionActualizar(ProductoRequest request, Long id ) {
        try {
            ProductoService service = new ProductoService();
            service.editarProducto(request, id);
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    /** 🔹 Construye y retorna la escena del formulario */
    public static VBox getScene(String title, Producto producto) {
        // 🔹 Título dinámico
        Text titulo = new Text(String.format("📦 %s Producto", title));
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // 🔹 Campos del formulario
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del producto");

        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("CACAO", "INGREDIENTES_COMPLEMENTARIOS");
        cbTipo.setPromptText("Selecciona un tipo");

        TextField txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción del producto");

        // 🔹 Precargar datos si se está editando
        if (producto != null) {
            if (producto.getNombre() != null) txtNombre.setText(producto.getNombre());
            if (producto.getTipo() != null) cbTipo.setValue(producto.getTipo());
            if (producto.getDescripcion() != null) txtDescripcion.setText(producto.getDescripcion());
        }

        // 🔹 Botón (Registrar / Actualizar)
        Button btnAccion = new Button(producto == null ? "Registrar" : "Actualizar");
        btnAccion.getStyleClass().add("login-button");
        btnAccion.setPrefWidth(150);

        Label lblMensaje = new Label();
        lblMensaje.setTextFill(Color.RED);

        // 🔹 Acción del botón
        btnAccion.setOnAction(e -> {
            if (txtNombre.getText().isEmpty() || cbTipo.getValue().isEmpty() || txtDescripcion.getText().isEmpty()) {
                lblMensaje.setText("⚠️ Por favor, completa todos los campos.");
                lblMensaje.setTextFill(Color.RED);
                return;
            }

            ProductoRequest request = new ProductoRequest(
                txtNombre.getText(),
                cbTipo.getValue(),
                txtDescripcion.getText()
            );

            if (producto == null) {
                onActionRegistrar(request);
                showNotification(btnAccion.getScene(), "✅ Producto registrado correctamente", Color.GREEN);
                lblMensaje.setTextFill(Color.GREEN);
                lblMensaje.setText("✅ Producto registrado correctamente.");
                txtNombre.clear();
                cbTipo.setValue(null);
                txtDescripcion.clear();
            } else {
                onActionActualizar(request, producto.getId());
                showNotification(btnAccion.getScene(), "✏️ Producto actualizado correctamente", Color.BLUE);
                lblMensaje.setTextFill(Color.BLUE);
                lblMensaje.setText("✏️ Producto actualizado correctamente.");
            }
        });

        // 🔹 Diseño general del formulario
        VBox root = new VBox(10);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(
            titulo,
            txtNombre,
            cbTipo,
            txtDescripcion,
            btnAccion,
            lblMensaje
        );

        root.setStyle("-fx-background-color: #F8F9FA;");
        return root;
    }
}
