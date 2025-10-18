package application.views;

import application.controllers.EntidadBancariaController;
import application.controllers.MetodoPagoController;
import application.controllers.PedidoController;
import application.controllers.ProductoController;
import application.controllers.ProveedorController;
import application.controllers.ReporteCompraController;
import application.controllers.ReportePedidoProveedorController;
import application.services.EntidadService;
import application.services.MetodoPagoService;
import application.services.PedidoService;
import application.services.ProductoService;
import application.services.ProveedorService;
import application.services.ReporteCompraService;
import application.services.ReportePedidoProveedorService;
import application.utils.SceneManager;
import javafx.scene.control.Button;

public class DashboardComprasView extends DashboardViewBase {
	
	
	private void onActionReporteCompra() {
		try {
			ReporteCompraService service = new ReporteCompraService();
			var reporte = service.obtenerReporteCompras();
			content.getChildren().setAll(ReporteCompraController.getScene(reporte));
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	private void onActionReportePedidosProveedor() {
		try {
			ReportePedidoProveedorService service = new ReportePedidoProveedorService();
			var reporte = service.obtenerReportePedidoProveedor();
			content.getChildren().setAll(ReportePedidoProveedorController.getScene(reporte));
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	
	private void onActionProveedor() {
	    try {
	        ProveedorService service = new ProveedorService();
	        var proveedores = service.obtenerProveedores();
	        ProveedorController controller = new ProveedorController(content);
	        content.getChildren().setAll(controller.getScene(proveedores));
	    } catch (Exception ex) {
	        System.out.println("Error tipo: " + ex);
	    }
	}

	private void onActionPedido() {
	    try {
	        PedidoService service = new PedidoService();
	        var pedidos = service.obtenerPedidos();
	        PedidoController controller = new PedidoController(content);
	        content.getChildren().setAll(controller.getScene(pedidos));
	    } catch (Exception ex) {
	        System.out.println("Error tipo: " + ex);
	    }
	}


	private void onActionProducto() {
	    try {
	        ProductoService service = new ProductoService();
	        var productos = service.obtenerProductos();
	        ProductoController controller = new ProductoController(content);
	        content.getChildren().setAll(controller.getScene(productos));

	    } catch (Exception ex) {
	        System.out.println("Error tipo: " + ex);
	    }
	}
	
	private void onActionMetodoPago() {
	    try {
	        MetodoPagoService service = new MetodoPagoService();
	        var metodos = service.obtenerMetodos();
	        MetodoPagoController controller = new MetodoPagoController(content);
	        content.getChildren().setAll(controller.getScene(metodos));

	    } catch (Exception ex) {
	        System.out.println("Error tipo: " + ex);
	    }
	}
	
	private void onActionEntidadBancaria() {
	    try {
	        EntidadService service = new EntidadService();
	        var entidades = service.obtenerEntidades();
	        EntidadBancariaController controller = new EntidadBancariaController(content);
	        content.getChildren().setAll(controller.getScene(entidades));

	    } catch (Exception ex) {
	        System.out.println("Error tipo: " + ex);
	    }
	}


    @Override
    protected void addMenuButtons() {
        Button btnProductos = createMenuButton("\uD83D\uDCBC", "Productos");
        Button btnProveedor = createMenuButton("\uD83D\uDE9A", "Proveedores");
        Button btnEntidadBancaria = createMenuButton("\uD83C\uDFE6", "Entidad Bancaria"); 
        Button btnMetodoPago = createMenuButton("\uD83D\uDCB3", "Metodo de Pago");   
        Button btnPedido = createMenuButton("\uD83D\uDED2", "Pedidos");
        Button btnReporteCompras = createMenuButton("\uD83D\uDCC4", "Reporte Compras");
        Button btnReportePedidoProveedor = createMenuButton("\uD83D\uDCE6", "Reporte Pedidos - Proveedor");
        vbox.getChildren().addAll(btnProductos, btnProveedor,btnEntidadBancaria, btnMetodoPago, btnPedido, btnReporteCompras, btnReportePedidoProveedor);
        btnProductos.setOnAction(e -> onActionProducto());
        btnProveedor.setOnAction(e -> onActionProveedor());
        btnEntidadBancaria.setOnAction(e -> onActionEntidadBancaria());
        btnMetodoPago.setOnAction(e -> onActionMetodoPago());
        btnPedido.setOnAction(e -> onActionPedido());
        btnReporteCompras.setOnAction(e -> onActionReporteCompra());
        btnReportePedidoProveedor.setOnAction(e -> onActionReportePedidosProveedor());
        addSidebarFooter("Coord. Compras");
    }
    @Override
    protected String getTitleText() { return "📊 Dashboard"; }
    @Override
    protected void onSalir() { SceneManager.changeScene(new LoginView().getScene(), "Inicio de Sesión"); }
}