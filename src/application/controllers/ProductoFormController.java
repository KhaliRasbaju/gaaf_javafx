package application.controllers;

import application.models.request.ProductoRequest;
import application.models.response.Producto;
import application.models.response.ResponseCommon;
import application.services.ProductoService;
import application.utils.NotificationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ProductoFormController {

    // ================================================
    //        CONTENEDOR PRINCIPAL
    // ================================================
	private final StackPane content;

    public ProductoFormController(StackPane content) {
        this.content = content;
    }

    // ================================================
    //     ACCIÓN: REGISTRAR UN NUEVO PRODUCTO
    // ================================================
    private static ResponseCommon onActionRegistrar(ProductoRequest request) {
        try {
            ProductoService service = new ProductoService();
            return service.crearProducto(request);
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
            throw new RuntimeException("Error tipo " + ex);
        }
    }

    // ================================================
    //     ACCIÓN: ACTUALIZAR PRODUCTO EXISTENTE
    // ================================================
    private static ResponseCommon onActionActualizar(ProductoRequest request, Long id ) {
        try {
            ProductoService service = new ProductoService();
            return service.editarProducto(request, id);
        } catch (Exception ex) {
            throw new RuntimeException("Error tipo: " + ex);
        }
    }

    // ================================
    //     CREACIÓN DE LA VISTA
    // ================================
    public VBox getScene(String title, Producto producto) {
        // 🔹 Título principal
        Text titulo = new Text(String.format("📦 %s Producto", title));
        titulo.getStyleClass().add("form-title");

        // 🔹 Campos del formulario
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del producto");
        txtNombre.getStyleClass().add("form-field");
        txtNombre.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            // Solo letras (incluye tildes y ñ) y espacios
            if (!newText.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                return null; // bloquea caracteres inválidos
            }

            // Evitar dos espacios seguidos
            if (newText.contains("  ")) {
                return null;
            }

            // Evitar espacio al inicio
            if (newText.startsWith(" ")) {
                return null;
            }

            return change;
        }));

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
            cbTipo.setDisable(true);
        }

        // 🔹 Botón (Registrar / Actualizar)
        Button btnAccion = new Button(producto == null ? "Registrar" : "Actualizar");
        btnAccion.getStyleClass().add("form-button");
        btnAccion.setPrefWidth(200);

       

        btnAccion.setOnAction(e -> {

            try {
                // -----------------------------
                //   Validación
                // -----------------------------
                if (txtNombre.getText().isEmpty() ||
                    cbTipo.getValue() == null ||
                    txtDescripcion.getText().isEmpty()) {
                	NotificationManager.showNotification(btnAccion.getScene(), "⚠ Por favor, completa todos los campos.", Color.ORANGE);
                  
                    return;
                }

                // -----------------------------
                //   Construcción del request
                // -----------------------------
                ProductoRequest request = new ProductoRequest(
                        txtNombre.getText(),
                        cbTipo.getValue(),
                        txtDescripcion.getText()
                );

                // -----------------------------
                //   Registrar o Editar
                // -----------------------------
                var esNuevo = (producto == null);
                var response = esNuevo
                        ? onActionRegistrar(request)
                        : onActionActualizar(request, producto.getId());

                // -----------------------------
                //   Notificación
                // -----------------------------
                int successCode = esNuevo ? 201 : 200;
                String icon = esNuevo ? "✔" : "✏";

                if (response.getStatus() != successCode) {
                    NotificationManager.showNotification(
                            btnAccion.getScene(),
                            String.format("❌ %s", response.getMessage()),
                            Color.YELLOWGREEN
                    );
                } else {
                    NotificationManager.showNotification(
                            btnAccion.getScene(),
                            String.format("%s %s", icon, response.getMessage()),
                            Color.GREEN
                    );
                }

                // -----------------------------
                //   Limpiar campos
                // -----------------------------
                txtNombre.clear();
                cbTipo.setValue(null);
                txtDescripcion.clear();

            } catch (Exception ex) {
                NotificationManager.showNotification(
                        btnAccion.getScene(),
                        "❎ Error al guardar el producto",
                        Color.RED
                );
                System.out.println("Error tipo: " + ex);
            }

            // -----------------------------
            //   Recargar listado
            // -----------------------------
            try {
                ProductoService service = new ProductoService();
                var productos = service.obtenerProductos();
                ProductoController controller = new ProductoController(content);
                content.getChildren().setAll(controller.getScene(productos));

            } catch (Exception ex) {
                throw new RuntimeException("Error tipo: " + ex);
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
        VBox root = new VBox(20, titulo, gridInfo, gridDescripcion, contBoton);
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
