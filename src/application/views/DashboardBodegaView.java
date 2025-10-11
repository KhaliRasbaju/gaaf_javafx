package application.views;

import application.controllers.ReporteInventarioController;
import application.controllers.ReporteInventarioMovimientoController;
import application.services.ReporteInventarioMovimientoService;
import application.services.ReporteInventarioProductoBodegaService;
import application.utils.SceneManager;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

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
	
	private void onActionReporteInventarioMovimiento() {
		try {
			ReporteInventarioMovimientoService service = new ReporteInventarioMovimientoService();
			var reporte = service.obtenerReporteInventarioMovimiento();
			content.getChildren().setAll(ReporteInventarioMovimientoController.getScene(reporte));
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	
	
    @Override
    protected void addMenuButtons() {
        Button btnBodegas = createMenuButton("\uD83C\uDFE2", "Bodegas");
        Button btnInventario = createMenuButton("\uD83D\uDCCB", "Inventario");
        Button btnReporteInventario = createMenuButton("\uD83D\uDCC4", "Reporte Inventario");
        Button btnReporteMovimiento = createMenuButton("\uD83D\uDD5B", "Reporte Movimientos");
        vbox.getChildren().addAll(btnBodegas, btnInventario, btnReporteInventario, btnReporteMovimiento);
        // btnBodegas.setOnAction(e -> ... );
        btnReporteInventario.setOnAction(e -> onActionReporteInventario());
        btnReporteMovimiento.setOnAction(e -> onActionReporteInventarioMovimiento());
        addSidebarFooter("Jefe Bodega");
    }
    @Override
    protected String getTitleText() { return "📦 Dashboard"; }
    @Override
    protected void onSalir() { SceneManager.changeScene(new LoginView().getScene(), "Inicio de Sesión"); }
}