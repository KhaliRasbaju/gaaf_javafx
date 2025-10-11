package application.views;

import application.controllers.PedidoController;
import application.controllers.ProductoController;
import application.controllers.ProveedorController;
import application.services.PedidoService;
import application.services.ProductoService;
import application.services.ProveedorService;
import application.utils.SceneManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Tooltip;


public class DashboardView {

    private boolean menuOpen = true;

    // ---------- CARGA DE VISTAS ----------
    private void onActivateProductos(StackPane content) {
        try {
            ProductoService service = new ProductoService();
            var productos = service.obtenerTodos();
            content.getChildren().setAll(
                    ProductoController.getScene(productos)
            );
        } catch (Exception ex) {
            System.out.println("Error tipo : " + ex);
        }
    }

    private void onActivateProveedores(StackPane content) {
        try {
            ProveedorService service = new ProveedorService();
            var proveedores = service.obtenerTodos();
            content.getChildren().setAll(
                    ProveedorController.createView(proveedores)
            );
        } catch (Exception ex) {
            System.out.println("Error tipo : " + ex);
        }
    }

  

    private void onActivatePedidos(StackPane content) {
        try {
            PedidoService service = new PedidoService();
            var pedidos = service.obtenerPedidos();
            content.getChildren().setAll(
                    PedidoController.getScene(pedidos)
            );
        } catch (Exception ex) {
            System.out.println("Error tipo : " + ex);
        }
    }

   

    // ---------- CREACIÓN DE BOTÓN (ICON + LABEL) ----------
    private Button createMenuButton(String icon, String text) {
        // Icon (emoji or glyph)
        Text iconText = new Text(icon);
        iconText.setFont(Font.font("Segoe UI Emoji", FontWeight.BOLD, 18));
        iconText.setFill(Color.web("#00E5FF"));

        // Label
        Text labelText = new Text(text);
        labelText.setFont(Font.font("Orbitron", FontWeight.NORMAL, 14));
        labelText.setFill(Color.web("#E6EEF6"));

        // make label not take space when invisible
        labelText.managedProperty().bind(labelText.visibleProperty());

        // container for icon + label
        HBox hbox = new HBox(10, iconText, labelText);
        hbox.setAlignment(Pos.CENTER_LEFT);

        Button btn = new Button();
        btn.setGraphic(hbox);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.getStyleClass().add("dashboard-menu-button");

        // Store references to adjust on toggle
        btn.getProperties().put("labelText", labelText);
        btn.getProperties().put("iconText", iconText);
        btn.getProperties().put("hbox", hbox);

        // Glow effect for whole button
        DropShadow glow = new DropShadow(20, Color.web("#00E5FF"));
        glow.setSpread(0.45);

        // Hover: glow the whole button and scale the icon
        btn.setOnMouseEntered(e -> {
            btn.setEffect(glow);
            ScaleTransition st = new ScaleTransition(Duration.millis(180), iconText);
            st.setToX(1.35);
            st.setToY(1.35);
            st.play();
        });
        btn.setOnMouseExited(e -> {
            btn.setEffect(null);
            ScaleTransition st = new ScaleTransition(Duration.millis(180), iconText);
            st.setToX(1);
            st.setToY(1);
            st.play();
        });

        // tooltip (optional): show label when minimized
        Tooltip tip = new Tooltip(text);
        Tooltip.install(btn, tip);

        return btn;
    }

    public Scene getScene() {
        // ------- Sidebar (menu) -------
        VBox vbox = new VBox(12);
        vbox.setPadding(new Insets(18));
        vbox.setPrefWidth(200);
        vbox.getStyleClass().add("dashboard-sidebar");

        Text title = new Text("📊 Dashboard");
        title.getStyleClass().add("dashboard-title");

        Button btnProductos = createMenuButton("📦", "Productos");
        Button btnProveedor = createMenuButton("🚚", "Proveedores");
        Button btnInventario = createMenuButton("📋", "Inventario");
        Button btnPedido = createMenuButton("🛒", "Pedidos");
        Button btnReporteInventario = createMenuButton("📑", "Reporte Inventario");

        vbox.getChildren().addAll(title, btnProductos, btnProveedor, btnInventario, btnPedido, btnReporteInventario);

        // ------- Main content -------
        StackPane content = new StackPane();
        content.getStyleClass().add("dashboard-content");
        Text placeholder = new Text("Selecciona una opción del menú");
        content.getChildren().add(placeholder);
        HBox.setHgrow(content, Priority.ALWAYS);

        // hook actions
        btnProductos.setOnAction(e -> onActivateProductos(content));
        btnProveedor.setOnAction(e -> onActivateProveedores(content));
        btnPedido.setOnAction(e -> onActivatePedidos(content));

        HBox mainContainer = new HBox(vbox, content);

        // ------- Header -------
        Text empresa = new Text("GAAF - Grupo Alimenticio Alba del Fonce SAS");
        empresa.getStyleClass().add("dashboard-header-title");

        Button toggleMenu = new Button("☰");
        toggleMenu.getStyleClass().add("dashboard-toggle-button");

        Button salir = new Button("Salir");
        salir.getStyleClass().add("dashboard-salir-button");

        // salir hover glow + press scale (keeps layout safe)
        DropShadow redGlow = new DropShadow(18, Color.web("#ff6b6b"));
        redGlow.setSpread(0.5);
        salir.setOnMouseEntered(ev -> salir.setEffect(redGlow));
        salir.setOnMouseExited(ev -> salir.setEffect(null));
        salir.setOnMousePressed(ev -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(140), salir);
            st.setToX(0.92);
            st.setToY(0.92);
            st.play();
        });
        salir.setOnMouseReleased(ev -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(140), salir);
            st.setToX(1);
            st.setToY(1);
            st.play();
        });

        salir.setOnAction(e -> {
            Timeline t = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(mainContainer.opacityProperty(), 1.0)),
                    new KeyFrame(Duration.millis(320), new KeyValue(mainContainer.opacityProperty(), 0.0))
            );
            t.setOnFinished(evt -> SceneManager.changeScene(new LoginView().getScene(), "Inicio de Sesión"));
            t.play();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox headerBox = new HBox(16, toggleMenu, empresa, spacer, salir);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(12));
        headerBox.getStyleClass().add("dashboard-header");

        // ------- Toggle logic: minimize / expand -------
        toggleMenu.setOnAction(e -> {
            double startWidth = vbox.getWidth();
            double targetWidth = menuOpen ? 64 : 200; // 64 for compact, 200 expanded

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(vbox.prefWidthProperty(), startWidth)),
                    new KeyFrame(Duration.millis(280), new KeyValue(vbox.prefWidthProperty(), targetWidth))
            );
            timeline.play();

            // iterate buttons and hide/show label AND change alignment
            for (javafx.scene.Node node : vbox.getChildren()) {
                if (node instanceof Button btn) {
                    Object lblObj = btn.getProperties().get("labelText");
                    Object hboxObj = btn.getProperties().get("hbox");
                    if (lblObj instanceof Text labelText) {
                        boolean willShow = !menuOpen; // if we are collapsing (menuOpen==true), willShow=false
                        labelText.setVisible(willShow);
                        // center icon when collapsed
                        if (hboxObj instanceof HBox hbox) {
                            if (!willShow) {
                                hbox.setAlignment(Pos.CENTER);
                                btn.setAlignment(Pos.CENTER);
                            } else {
                                hbox.setAlignment(Pos.CENTER_LEFT);
                                btn.setAlignment(Pos.CENTER_LEFT);
                            }
                        }
                    }
                }
            }

            // change title short/long
            title.setText(menuOpen ? "📊" : "📊 Dashboard");
            menuOpen = !menuOpen;
        });

        // ------- Root -------
        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(mainContainer);

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(getClass().getResource("/application/resources/application.css").toExternalForm());
        return scene;
    }
}