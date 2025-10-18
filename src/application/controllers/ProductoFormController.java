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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
        // 🔹 Título principal
        Text titulo = new Text(String.format("📦 %s Producto", title));
        titulo.getStyleClass().add("form-title");

        // 🔹 Campos del formulario
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del producto");
        txtNombre.getStyleClass().add("form-field");

        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("CACAO", "INGREDIENTES_COMPLEMENTARIOS");
        cbTipo.setPromptText("Selecciona un tipo");
        cbTipo.getStyleClass().add("form-field");

        TextField txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción del producto");
        txtDescripcion.getStyleClass().add("form-field");

        // 🔹 Precargar datos si se está editando
        if (producto != null) {
            if (producto.getNombre() != null) txtNombre.setText(producto.getNombre());
            if (producto.getTipo() != null) cbTipo.setValue(producto.getTipo());
            if (producto.getDescripcion() != null) txtDescripcion.setText(producto.getDescripcion());
        }

        // 🔹 Botón (Registrar / Actualizar)
        Button btnAccion = new Button(producto == null ? "Registrar" : "Actualizar");
        btnAccion.getStyleClass().add("form-button");
        btnAccion.setPrefWidth(200);

        Label lblMensaje = new Label();
        lblMensaje.setTextFill(Color.RED);

        btnAccion.setOnAction(e -> {
            if (txtNombre.getText().isEmpty() || cbTipo.getValue() == null || txtDescripcion.getText().isEmpty()) {
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
                showNotification(btnAccion.getScene(), "✏️ Producto actualizado correctamente", Color.DODGERBLUE);
                lblMensaje.setTextFill(Color.DODGERBLUE);
                lblMensaje.setText("✏️ Producto actualizado correctamente.");
            }
        });

        // ======= Sección 1: Información del producto =======
        GridPane gridInfo = new GridPane();
        gridInfo.getStyleClass().add("form-container");
        gridInfo.setHgap(25);
        gridInfo.setVgap(15);
        gridInfo.setPadding(new Insets(25));
        gridInfo.setAlignment(Pos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col1.setFillWidth(true);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        col2.setFillWidth(true);
        gridInfo.getColumnConstraints().addAll(col1, col2);

        Label lblNombre = new Label("Nombre del producto");
        lblNombre.getStyleClass().add("form-label");
        gridInfo.add(lblNombre, 0, 0);
        gridInfo.add(txtNombre, 0, 1);

        Label lblTipo = new Label("Tipo de producto");
        lblTipo.getStyleClass().add("form-label");
        gridInfo.add(lblTipo, 1, 0);
        gridInfo.add(cbTipo, 1, 1);

        // ======= Sección 2: Descripción =======
        GridPane gridDescripcion = new GridPane();
        gridDescripcion.getStyleClass().add("form-container");
        gridDescripcion.setHgap(25);
        gridDescripcion.setVgap(15);
        gridDescripcion.setPadding(new Insets(25));
        gridDescripcion.setAlignment(Pos.CENTER);

        ColumnConstraints colDesc1 = new ColumnConstraints();
        colDesc1.setPercentWidth(50);
        colDesc1.setFillWidth(true);
        ColumnConstraints colDesc2 = new ColumnConstraints();
        colDesc2.setPercentWidth(50);
        colDesc2.setFillWidth(true);
        gridDescripcion.getColumnConstraints().addAll(colDesc1, colDesc2);

        Label lblDescripcion = new Label("Descripción del producto");
        lblDescripcion.getStyleClass().add("form-label");
        gridDescripcion.add(lblDescripcion, 0, 0);
        gridDescripcion.add(txtDescripcion, 0, 1, 2, 1); // ocupa las dos columnas

        // 🔹 Botón centrado
        HBox contBoton = new HBox(btnAccion);
        contBoton.setAlignment(Pos.CENTER);
        contBoton.setPadding(new Insets(10, 0, 0, 0));

        // 🔹 Layout principal
        VBox root = new VBox(20, titulo, gridInfo, gridDescripcion, contBoton, lblMensaje);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F8F9FA;");

        // 🔹 Scroll elegante
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F8F9FA;");

        VBox container = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        container.setStyle("-fx-background-color: #F8F9FA;");
        return container;
    }








}
