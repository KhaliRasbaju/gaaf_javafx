package application.views;

import application.utils.SceneManager;
import javafx.scene.control.Button;

public class DashboardBodegaView extends DashboardViewBase {
    @Override
    protected void addMenuButtons() {
        Button btnBodegas = createMenuButton("\uD83C\uDFE2", "Bodegas");
        Button btnInventario = createMenuButton("\uD83D\uDCCB", "Inventario");
        Button btnReporteInventario = createMenuButton("\uD83D\uDCC4", "Reporte Inventario");
        vbox.getChildren().addAll(btnBodegas, btnInventario, btnReporteInventario);
        // btnBodegas.setOnAction(e -> ... );
        // btnInventario.setOnAction(e -> ... );
        // btnReporteInventario.setOnAction(e -> ... );
    }
    @Override
    protected String getTitleText() { return "📦 Dashboard"; }
    @Override
    protected void onSalir() { SceneManager.changeScene(new LoginView().getScene(), "Inicio de Sesión"); }
}
