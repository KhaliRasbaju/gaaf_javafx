package application.views;

import application.controllers.BodegaController;
import application.controllers.ReporteInventarioController;
import application.controllers.ReporteInventarioMovimientoController;
import application.controllers.TransaccionFormController;
import application.services.BodegaService;
import application.services.ReporteInventarioMovimientoService;
import application.services.ReporteInventarioProductoBodegaService;
import application.utils.SceneManager;
import javafx.scene.control.Button;

public class DashboardBodegaView extends DashboardViewBase {
	
	
	private void onActionReporteInventario() {
		try {
			ReporteInventarioProductoBodegaService service = new ReporteInventarioProductoBodegaService();
			var reporte = service.obtenerReporteReporteInventarioProductoBodega();
			content.getChildren().setAll(ReporteInventarioController.getScene(reporte));	
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	private void onActionBodega() {
		try {
			BodegaService service = new BodegaService();
			var reporte = service.obtenerBodegas();
			BodegaController controller = new BodegaController(content);
			content.getChildren().setAll(controller.getScene(reporte));	
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	private void onActionReporteInventarioMovimiento() {
		try {
			ReporteInventarioMovimientoService service = new ReporteInventarioMovimientoService();
			var reporte = service.obtenerReporteInventarioMovimiento();
			content.getChildren().setAll(ReporteInventarioMovimientoController.getScene(reporte));
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	
	private void onActionTransaccion() {
	    try {
	        content.getChildren().setAll(TransaccionFormController.getScene(content, "CREAR", null));
	    } catch (Exception ex) {
	        System.out.println("Error tipo: " + ex);
	    }
	}



	
	
    @Override
    protected void addMenuButtons() {
        Button btnBodegas = createMenuButton("\uD83C\uDFE2", "Bodegas");
        Button btnTransaccion = createMenuButton("\uD83D\uDCCB", "Transacciones");
        Button btnReporteInventario = createMenuButton("\uD83D\uDCC4", "Reporte Inventario");
        Button btnReporteMovimiento = createMenuButton("\uD83D\uDD5B", "Reporte Movimientos");
        vbox.getChildren().addAll(btnBodegas, btnTransaccion, btnReporteInventario, btnReporteMovimiento);
        btnBodegas.setOnAction(e -> onActionBodega()); 
        btnReporteInventario.setOnAction(e -> onActionReporteInventario());
        btnTransaccion.setOnAction(e -> onActionTransaccion()); 
        btnReporteMovimiento.setOnAction(e -> onActionReporteInventarioMovimiento());
        addSidebarFooter("Jefe Bodega");
    }
    @Override
    protected String getTitleText() { return "📦 Dashboard"; }
    @Override
    protected void onSalir() { SceneManager.changeScene(new LoginView().getScene(), "Inicio de Sesión"); }
}