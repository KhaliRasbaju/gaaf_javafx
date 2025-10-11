package application.views;

import application.controllers.ReporteCompraController;
import application.controllers.ReporteInventarioController;
import application.services.ReporteCompraService;
import application.services.ReporteInventarioProductoBodegaService;
import application.utils.SceneManager;
import javafx.scene.control.Button;

public class DashboardGerenteView extends DashboardViewBase {

    private void onActionReporteInventario() {
        try {
            System.out.println("→ Clic en Reporte Inventario");
            ReporteInventarioProductoBodegaService service = new ReporteInventarioProductoBodegaService();
            var reporte = service.obtenerReporteReporteInventarioProductoBodega();
            
            // ✅ Usamos el content del padre (no uno nuevo)
            content.getChildren().setAll(ReporteInventarioController.getScene(reporte));
        } catch (Exception ex) {
            System.out.println("Error tipo : " + ex);
            ex.printStackTrace();
        }
    }
    
    private void onActionReporteCompra() {
        try {
            System.out.println("→ Clic en Reporte Inventario");
            ReporteCompraService service = new ReporteCompraService();
            var reporte = service.obtenerReporteCompras();
            
            // ✅ Usamos el content del padre (no uno nuevo)
            content.getChildren().setAll(ReporteCompraController.getScene(reporte));
        } catch (Exception ex) {
            System.out.println("Error tipo : " + ex);
            ex.printStackTrace();
        }
    }


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

    @Override
    protected String getTitleText() { 
        return "📈 Dashboard"; 
    }

    @Override
    protected void onSalir() { 
        SceneManager.changeScene(new LoginView().getScene(), "Inicio de Sesión"); 
    }
}
