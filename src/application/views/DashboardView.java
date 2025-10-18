package application.views;

import application.controllers.UsuarioController;
import application.services.UsuarioService;
import application.utils.SceneManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.control.Tooltip;


public class DashboardView extends DashboardViewBase{

    private boolean menuOpen = true;

    // ---------- CARGA DE VISTAS ----------

    public void onActivateUsuarios() {
    	try {
    		
    		UsuarioService service = new UsuarioService();
    		var usuarios = service.obtenerUsuarios();
    		
    		UsuarioController controller = new UsuarioController(content);
    		System.out.println("Paso Por aqui");
            content.getChildren().setAll(
            	controller.getScene(usuarios)
            );
            
        } catch (Exception ex) {
            System.out.println("Error tipo : " + ex);
        }
    }



    // ---------- CREACIÓN DE BOTÓN (ICON + LABEL) ----------
    protected Button createMenuButton(String icon, String text) {
        Text iconText = new Text(icon);
        iconText.setFont(Font.font("Segoe UI Emoji", FontWeight.BOLD, 18));
        iconText.setFill(Color.rgb(229, 229, 229)); // Cambiado a gris claro

        Text labelText = new Text(text);
        labelText.setFont(Font.font("Orbitron", FontWeight.NORMAL, 14));
        labelText.setFill(Color.rgb(229, 229, 229)); // Cambiado a gris claro
        labelText.managedProperty().bind(labelText.visibleProperty());
        HBox hbox = new HBox(10, iconText, labelText);
        hbox.setAlignment(Pos.CENTER_LEFT);
        Button btn = new Button();
        btn.setGraphic(hbox);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.getStyleClass().add("dashboard-menu-button");
        btn.getProperties().put("labelText", labelText);
        btn.getProperties().put("iconText", iconText);
        btn.getProperties().put("hbox", hbox);
        DropShadow glow = new DropShadow(20, Color.rgb(229, 229, 229));
        glow.setSpread(0.45);
        btn.setOnMouseEntered(e -> {
            btn.setEffect(glow);
            ScaleTransition st = new ScaleTransition(Duration.millis(180), iconText);
            st.setToX(1.35);
            st.setToY(1.35);
            st.play();
        });
        btn.setOnMouseExited(e -> {
            btn.setEffect(null);
            ScaleTransition st = new ScaleTransition(Duration.millis(180), iconText);
            st.setToX(1);
            st.setToY(1);
            st.play();
        });
        Tooltip tip = new Tooltip(text);
        Tooltip.install(btn, tip);
        return btn;
    }

    public Scene getScene() {
        // ------- Sidebar (menu) -------
    	VBox vbox = new VBox(12);
    	vbox.setPadding(new Insets(18));
    	vbox.setPrefWidth(200);
    	vbox.getStyleClass().add("dashboard-sidebar");

    	Text title = new Text("📊 Dashboard");
    	title.getStyleClass().add("dashboard-title");

    	Button btnUsuario = createMenuButton("👤", "Usuarios");

    	// Este spacer "empuja" el footer hacia abajo
    	Region spacer = new Region();
    	VBox.setVgrow(spacer, Priority.ALWAYS);

    	vbox.getChildren().addAll(title, btnUsuario, spacer);

    	// Pie de menú lateral: nombre y campana
    	HBox sidebarFooter = new HBox();
    	sidebarFooter.setSpacing(12);
    	sidebarFooter.setAlignment(Pos.CENTER_LEFT);
    	sidebarFooter.getStyleClass().add("dashboard-sidebar-footer");

    	Text userLabel = new Text("Administrador");
    	userLabel.getStyleClass().add("dashboard-user-label");
    	userLabel.setFill(Color.WHITE);

    	Button bellButton = new Button("🔔");
    	bellButton.getStyleClass().add("dashboard-bell-button");

    	sidebarFooter.getChildren().addAll(userLabel, bellButton);
    	sidebarFooter.setPadding(new Insets(10, 10, 10, 10));

    	// Agregar al VBox al final
    	vbox.getChildren().add(sidebarFooter);

        // ------- Main content -------
      
        content.getStyleClass().add("dashboard-content");
        Text placeholder = new Text("Selecciona una opción del menú");
        content.getChildren().add(placeholder);
        HBox.setHgrow(content, Priority.ALWAYS);

        // hook actions
        btnUsuario.setOnAction(e -> onActivateUsuarios());
     

        HBox mainContainer = new HBox(vbox, content);

        // ------- Header -------
        Image logoImg = new Image(getClass().getResource("/application/resources/logoGAAF.png").toExternalForm());
        ImageView empresaLogo = new ImageView(logoImg);
        empresaLogo.setFitHeight(38);
        empresaLogo.setPreserveRatio(true);
        empresaLogo.setSmooth(true);
        empresaLogo.setCache(true);
        empresaLogo.getStyleClass().add("dashboard-header-logo");

        Text empresa = new Text("GAAF - Grupo Alimenticio Alba del Fonce SAS");
        empresa.getStyleClass().add("dashboard-header-title");

        Button toggleMenu = new Button("☰");
        toggleMenu.getStyleClass().add("dashboard-toggle-button");

        Button salir = new Button("Salir");
        salir.getStyleClass().add("dashboard-salir-button");

        // salir hover glow + press scale (keeps layout safe)
        DropShadow redGlow = new DropShadow(18, Color.web("#ff6b6b"));
        redGlow.setSpread(0.5);
        salir.setOnMouseEntered(ev -> salir.setEffect(redGlow));
        salir.setOnMouseExited(ev -> salir.setEffect(null));
        salir.setOnMousePressed(ev -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(140), salir);
            st.setToX(0.92);
            st.setToY(0.92);
            st.play();
        });
        salir.setOnMouseReleased(ev -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(140), salir);
            st.setToX(1);
            st.setToY(1);
            st.play();
        });

        salir.setOnAction(e -> {
            Timeline t = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(mainContainer.opacityProperty(), 1.0)),
                    new KeyFrame(Duration.millis(320), new KeyValue(mainContainer.opacityProperty(), 0.0))
            );
            t.setOnFinished(evt -> SceneManager.changeScene(new LoginView().getScene(), "Inicio de Sesión"));
            t.play();
        });

        Region spacerRegion = new Region();
        HBox.setHgrow(spacerRegion, Priority.ALWAYS);

        HBox headerBox = new HBox(16, toggleMenu, empresaLogo, spacerRegion, salir);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(12));
        headerBox.getStyleClass().add("dashboard-header");

        // ------- Toggle logic: minimize / expand -------
        toggleMenu.setOnAction(e -> {
            double startWidth = vbox.getWidth();
            double targetWidth = menuOpen ? 64 : 200; // 64 for compact, 200 expanded

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(vbox.prefWidthProperty(), startWidth)),
                    new KeyFrame(Duration.millis(280), new KeyValue(vbox.prefWidthProperty(), targetWidth))
            );
            timeline.play();

            // iterate buttons and hide/show label AND change alignment
            for (javafx.scene.Node node : vbox.getChildren()) {
                if (node instanceof Button btn) {
                    Object lblObj = btn.getProperties().get("labelText");
                    Object hboxObj = btn.getProperties().get("hbox");
                    if (lblObj instanceof Text labelText) {
                        boolean willShow = !menuOpen; // if we are collapsing (menuOpen==true), willShow=false
                        labelText.setVisible(willShow);
                        // center icon when collapsed
                        if (hboxObj instanceof HBox hbox) {
                            if (!willShow) {
                                hbox.setAlignment(Pos.CENTER);
                                btn.setAlignment(Pos.CENTER);
                            } else {
                                hbox.setAlignment(Pos.CENTER_LEFT);
                                btn.setAlignment(Pos.CENTER_LEFT);
                            }
                        }
                    }
                }
            }

            // change title short/long
            title.setText(menuOpen ? "📊" : "📊 Dashboard");
            menuOpen = !menuOpen;
        });

        // ------- Root -------
        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(mainContainer);

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(getClass().getResource("/application/resources/application.css").toExternalForm());
        return scene;
    }



	@Override
	protected void addMenuButtons() {
		// TODO Auto-generated method stub
		
	}



	@Override
	protected String getTitleText() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected void onSalir() {
		// TODO Auto-generated method stub
		
	}
}
