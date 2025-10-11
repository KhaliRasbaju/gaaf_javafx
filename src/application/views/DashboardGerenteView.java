package application.views;

import application.utils.SceneManager;
import javafx.scene.control.Button;

public class DashboardGerenteView extends DashboardViewBase {
    @Override
    protected void addMenuButtons() {
        Button btnReporteInventario = createMenuButton("\uD83D\uDCC4", "Reporte Inventario");
        Button btnReporteCompras = createMenuButton("\uD83D\uDCC4", "Reporte Compras");
        vbox.getChildren().addAll(btnReporteInventario, btnReporteCompras);
        // btnReporteInventario.setOnAction(e -> ... );
        // btnReporteCompras.setOnAction(e -> ... );
        addSidebarFooter("Gerente");
    }
    @Override
    protected String getTitleText() { return "📈 Dashboard"; }
    @Override
    protected void onSalir() { SceneManager.changeScene(new LoginView().getScene(), "Inicio de Sesión"); }
}