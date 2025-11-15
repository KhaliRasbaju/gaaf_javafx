package application.views;

import application.controllers.UsuarioController;
import application.services.UsuarioService;
import application.utils.SceneManager;
import javafx.scene.control.Button;

public class DashboardView extends DashboardViewBase {

	// ================================
	//        EVENTO: USUARIOS
	// ================================
    private void onActionUsuarios() {
        try {
            UsuarioService service = new UsuarioService();
            var usuarios = service.obtenerUsuarios();

            UsuarioController controller = new UsuarioController(content);
            System.out.println("→ Clic en Usuarios");

            content.getChildren().setAll(controller.getScene(usuarios));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
            ex.printStackTrace();
        }
    }

    // ================================
    //       CONFIGURAR BOTONES
    // ================================
    @Override
    protected void addMenuButtons() {
        Button btnUsuarios = createMenuButton("👤", "Usuarios");
        vbox.getChildren().addAll(btnUsuarios);

        // Footer lateral (Administrador)
        addSidebarFooter("Administrador");

        // Acciones
        btnUsuarios.setOnAction(e -> onActionUsuarios());
    }

    // ================================
    //          TITULO DEL MENU
    // ================================
    @Override
    protected String getTitleText() {
        return "📊 Dashboard";
    }

    // ================================
    //            BOTÓN SALIR
    // ================================
    @Override
    protected void onSalir() {
        SceneManager.changeScene(new LoginView().getScene(), "Inicio de Sesión");
    }
}
