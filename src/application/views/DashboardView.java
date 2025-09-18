package application.views;

import application.controllers.InventarioController;
import application.controllers.PedidoController;
import application.controllers.ProductoController;
import application.controllers.ProveedorController;
import application.controllers.ReporteInventarioController;
import application.services.PedidoService;
import application.services.ProductoService;
import application.services.ProveedorService;
import application.services.ReporteInventarioService;
import application.utils.SceneManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DashboardView {

    private boolean menuOpen = true;
    
 
    private void onActivateProductos(StackPane content) {
        try {
            ProductoService service = new ProductoService();
            var productos = service.getProducts();
            System.out.println(productos);
            content.getChildren().setAll(
                    ProductoController.getScene(productos)
            );
        } catch (Exception ex) {
            System.out.println("Error tipo : " + ex.toString());
        }
    }

    private void onActivateProveedores(StackPane content) {
        try {
            ProveedorService service = new ProveedorService();
            var proveedores = service.getAll();
            System.out.println(proveedores);
            content.getChildren().setAll(
                    ProveedorController.createView(proveedores)
            );
        } catch (Exception ex) {
            System.out.println("Error tipo : " + ex.toString());
        }
    }

    private void onActivateInventario(StackPane content) {
        try {
            ReporteInventarioService service = new ReporteInventarioService();
            var reporte = service.getReporte();

            content.getChildren().setAll(
                    InventarioController.getScene(reporte.getReporteInventario())
            );
        } catch (Exception ex) {
            System.out.println("Error tipo : " + ex.toString());
        }
    }

    private void onActivatePedidos(StackPane content) {
        try {
            PedidoService service = new PedidoService();
            var pedidos = service.getPedidos();

            content.getChildren().setAll(
                    PedidoController.getScene(pedidos)
            );
        } catch (Exception ex) {
            System.out.println("Error tipo : " + ex.toString());
        }
    }

    private void onActivateReporteInventario(StackPane content) {
        try {
            ReporteInventarioService service = new ReporteInventarioService();
            var reporte = service.getReporte();
            System.out.println(reporte);
            content.getChildren().setAll(
                    ReporteInventarioController.getScene(reporte)
            );
        } catch (Exception ex) {
            System.out.println("Error tipo : " + ex.toString());
        }
    }


    public Scene getScene() {
        // Menú lateral
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setPrefWidth(200);
        vbox.setStyle("-fx-background-color: #2C3E50;");

        Text title = new Text("Dashboard");
        title.setFont(Font.font(20));
        title.setFill(Color.WHITE);

        Button btnProductos = new Button("Productos");
        Button btnProveedor = new Button("Proveedor");
        Button btnInventario = new Button("Inventario");
        Button btnPedido = new Button("Pedido");
        Button btnReporteInventario = new Button("Reporte Inventario");

        vbox.getChildren().addAll(title, btnProductos, btnProveedor, btnInventario, btnPedido, btnReporteInventario);

        
        
        
        // Contenido principal
        StackPane content = new StackPane();
        content.setStyle("-fx-background-color: #ECF0F1;");
        content.getChildren().add(new Text("Selecciona una opción del menú"));

        
        
        // Hace que el content crezca
        HBox.setHgrow(content, Priority.ALWAYS);
        content.setMaxWidth(Double.MAX_VALUE);
        content.setMaxHeight(Double.MAX_VALUE);

        
        
        // Main container con menú y contenido
        HBox mainContainer = new HBox(vbox, content);
        
        btnProductos.setOnAction(e -> onActivateProductos(content));
        btnProveedor.setOnAction(e -> onActivateProveedores(content));
        btnInventario.setOnAction(e -> onActivateInventario(content));
        btnPedido.setOnAction(e -> onActivatePedidos(content));
        btnReporteInventario.setOnAction(e -> onActivateReporteInventario(content));


        // Header
        Button toggleMenu = new Button("☰");
        Button salir = new Button("Salir");
        salir.setStyle("-fx-background-color: #E62727; -fx-text-fill: #FFFFFF");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox headerBox = new HBox(10, toggleMenu, spacer, salir);

        // Animación del menú
        toggleMenu.setOnAction(e -> {
            double startWidth = vbox.getWidth();
            double targetWidth = menuOpen ? 0 : 200;

            Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                    new KeyValue(vbox.prefWidthProperty(), startWidth)
                ),
                new KeyFrame(Duration.millis(300),
                    new KeyValue(vbox.prefWidthProperty(), targetWidth)
                )
            );
            timeline.play();

            menuOpen = !menuOpen;
        });

        salir.setOnAction(e -> {
            SceneManager.changeScene(new LoginView().getScene(), "Inicio de Sesión");
        });

        // Layout raíz
        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(mainContainer);

        return new Scene(root, 800, 600);
    }
}
