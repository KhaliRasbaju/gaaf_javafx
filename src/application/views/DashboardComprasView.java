package application.views;

import application.controllers.PedidoController;
import application.controllers.ProductoController;
import application.controllers.ProveedorController;
import application.utils.SceneManager;
import javafx.scene.control.Button;

public class DashboardComprasView extends DashboardViewBase {
    @Override
    protected void addMenuButtons() {
        Button btnProductos = createMenuButton("\uD83D\uDCBC", "Productos");
        Button btnProveedor = createMenuButton("\uD83D\uDE9A", "Proveedores");
        Button btnPedido = createMenuButton("\uD83D\uDED2", "Pedidos");
        Button btnReporteCompras = createMenuButton("\uD83D\uDCC4", "Reporte Compras");
        vbox.getChildren().addAll(btnProductos, btnProveedor, btnPedido, btnReporteCompras);
        btnProductos.setOnAction(e -> content.getChildren().setAll(ProductoController.getScene(null)));
        btnProveedor.setOnAction(e -> content.getChildren().setAll(ProveedorController.createView(null)));
        btnPedido.setOnAction(e -> content.getChildren().setAll(PedidoController.getScene(null)));
        // btnReporteCompras.setOnAction(e -> ... );
    }
    @Override
    protected String getTitleText() { return "📊 Dashboard"; }
    @Override
    protected void onSalir() { SceneManager.changeScene(new LoginView().getScene(), "Inicio de Sesión"); }
}