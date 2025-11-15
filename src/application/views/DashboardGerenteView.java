package application.views;

import application.controllers.ReporteCompraController;
import application.controllers.ReporteInventarioController;
import application.services.ProductoService;
import application.services.ReporteCompraService;
import application.services.ReporteInventarioProductoBodegaService;
import application.utils.SceneManager;
import javafx.scene.control.Button;

public class DashboardGerenteView extends DashboardViewBase {

		// ================================
		//   EVENTO: REPORTE INVENTARIO
		// ================================
    private void onActionReporteInventario() {
        try {
        	ProductoService serviceP = new ProductoService();
			var productos = serviceP.obtenerProductos();
            System.out.println("→ Clic en Reporte Inventario");
            ReporteInventarioProductoBodegaService service = new ReporteInventarioProductoBodegaService();
            var reporte = service.obtenerReporteReporteInventarioProductoBodega(null, null, null, null).getContent();          
            content.getChildren().setAll(ReporteInventarioController.getScene(reporte, productos));
        } catch (Exception ex) {
            System.out.println("Error tipo : " + ex);
            ex.printStackTrace();
        }
    }
    	// ================================
    	//   EVENTO: REPORTE COMPRAS
    	// ================================
    private void onActionReporteCompra() {
        try {
            System.out.println("→ Clic en Reporte Inventario");
            ProductoService serviceP = new ProductoService();
			var productos = serviceP.obtenerProductos();
            ReporteCompraService service = new ReporteCompraService();
            var reporte = service.obtenerReporteCompras(null, null, null, null, null, null, null).getContent();        
            content.getChildren().setAll(ReporteCompraController.getScene(reporte, productos));
        } catch (Exception ex) {
            System.out.println("Error tipo : " + ex);
            ex.printStackTrace();
        }
    }

    // ================================
    //       CONFIGURAR BOTONES
    // ================================
    @Override
    protected void addMenuButtons() {
        Button btnReporteInventario = createMenuButton("📄", "Reporte Inventario");
        Button btnReporteCompras = createMenuButton("📊", "Reporte Compras");
        
        vbox.getChildren().addAll(btnReporteInventario, btnReporteCompras);
        addSidebarFooter("Gerente");
        
        // ✅ Sin crear un nuevo StackPane aquí
        btnReporteInventario.setOnAction(e -> onActionReporteInventario());
        btnReporteCompras.setOnAction(e -> onActionReporteCompra());
    }

     // ================================
	 //       TITULO DEL MENU
	 // ================================
    @Override
    protected String getTitleText() { 
        return "📈 Dashboard"; 
    }

    // ================================
    //          BOTON SALIR
    // ================================
    @Override
    protected void onSalir() { 
        SceneManager.changeScene(new LoginView().getScene(), "Inicio de Sesión"); 
    }
}
