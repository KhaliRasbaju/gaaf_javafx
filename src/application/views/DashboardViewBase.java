package application.views;

import application.controllers.CredencialesFormController;
import application.controllers.UsuarioEditarFormController;
import application.services.UsuarioService;
import application.session.SessionManager;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.control.Tooltip;

public abstract class DashboardViewBase {
	
    protected boolean menuOpen = true;
    protected VBox vbox;
    protected StackPane content;
    protected Text title;
    protected HBox mainContainer;
    protected HBox headerBox;
    protected Button toggleMenu;
    protected Button settingsButton;
    protected ImageView empresaLogo;

    private VBox settingsMenu;
    private boolean settingsOpen = false;

    private Text userLabel; // ✅ Guardamos la referencia

    
    public DashboardViewBase() {
        vbox = new VBox(12);
        vbox.setPadding(new Insets(18));
        vbox.setMinWidth(140);
        vbox.setMaxWidth(300);
        vbox.setPrefWidth(300);
        vbox.getStyleClass().add("dashboard-sidebar");

        title = new Text(getTitleText());
        title.getStyleClass().add("dashboard-title");
        vbox.getChildren().add(title);

        addMenuButtons();

        content = new StackPane();
        content.getStyleClass().add("dashboard-content");
        Text placeholder = new Text("Selecciona una opción del menú");
        content.getChildren().add(placeholder);
        HBox.setHgrow(content, Priority.ALWAYS);

        mainContainer = new HBox(vbox, content);

        Image logoImg = new Image(getClass().getResource("/application/resources/logoGAAF.png").toExternalForm());
        empresaLogo = new ImageView(logoImg);
        empresaLogo.setFitHeight(38);
        empresaLogo.setPreserveRatio(true);

        toggleMenu = new Button("☰");
        toggleMenu.getStyleClass().add("dashboard-toggle-button");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        headerBox = new HBox(16, toggleMenu, empresaLogo, spacer);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(12));
        headerBox.getStyleClass().add("dashboard-header");

        toggleMenu.setOnAction(e -> toggleMenu());
    }

    protected abstract void addMenuButtons();
    protected abstract String getTitleText();
    protected abstract void onSalir();

    protected Button createMenuButton(String icon, String text) {
        Text iconText = new Text(icon);
        iconText.getStyleClass().add("dashboard-icon");

        Text labelText = new Text(text);
        labelText.getStyleClass().add("dashboard-header-title");
        labelText.managedProperty().bind(labelText.visibleProperty());

        HBox hbox = new HBox(10, iconText, labelText);
        hbox.setMinHeight(40);
        hbox.setAlignment(Pos.CENTER_LEFT);

        Button btn = new Button();
        btn.setGraphic(hbox);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.getStyleClass().add("dashboard-menu-button");

        btn.getProperties().put("labelText", labelText);
        btn.getProperties().put("hbox", hbox);

        DropShadow glow = new DropShadow(20, Color.web("#E5E5E5"));
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

        Tooltip.install(btn, new Tooltip(text));
        return btn;
    }

    private void toggleSettingsMenu() {
        settingsOpen = !settingsOpen;
        settingsMenu.setVisible(settingsOpen);
    }

    private void toggleMenu() {
        double start = vbox.getWidth();
        double target = menuOpen ? 140 : 300;

        Timeline t = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(vbox.minWidthProperty(), start),
                        new KeyValue(vbox.maxWidthProperty(), start)
                ),
                new KeyFrame(Duration.millis(280),
                        new KeyValue(vbox.minWidthProperty(), target, Interpolator.EASE_BOTH),
                        new KeyValue(vbox.maxWidthProperty(), target, Interpolator.EASE_BOTH)
                )
        );
        t.play();

        
        for (Node node : vbox.getChildren()) {

            if (node instanceof Button btn) {
                Text lbl = (Text) btn.getProperties().get("labelText");
                HBox hb = (HBox) btn.getProperties().get("hbox");

                if (lbl != null && hb != null) {
                    boolean showText = !menuOpen;

                    lbl.setVisible(showText);
                   
                }
            }

            // ✅ Footer: usuario
            if (node.getProperties().containsKey("labelTextFooter")) {
                Text lblFooter = (Text) node.getProperties().get("labelTextFooter");
                lblFooter.setVisible(!menuOpen);
            }

            // ✅ Settings menu completo
            if (node instanceof VBox settingsContainer) {
                if (settingsContainer == settingsMenu) {
                    for (Node item : settingsContainer.getChildren()) {
                        if (item instanceof Button btn) {
                            Text lbl = (Text) btn.getProperties().get("labelText");
                            HBox hb = (HBox) btn.getProperties().get("hbox");

                            if (lbl != null && hb != null) {
                                lbl.setVisible(!menuOpen);
                               
                            }
                        }
                    }
                }
            }
        }


        menuOpen = !menuOpen;

        // ✅ Si el sidebar se cierra, se oculta settings automáticamente
        if (!menuOpen && settingsOpen) toggleSettingsMenu();
    }


    public Scene getScene() {
        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(mainContainer);
        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(getClass().getResource("/application/resources/application.css").toExternalForm());
        return scene;
    }

    protected void addSidebarFooter(String roleName) {
        Region push = new Region();
        VBox.setVgrow(push, Priority.ALWAYS);
        vbox.getChildren().add(push);

        userLabel = new Text(roleName);
        userLabel.getStyleClass().add("dashboard-user-label");
        userLabel.setFill(Color.WHITE);
        userLabel.managedProperty().bind(userLabel.visibleProperty());
        vbox.getProperties().put("labelTextFooter", userLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Text settingsIcon = new Text("⚙");
        settingsIcon.getStyleClass().add("dashboard-icon");

        settingsButton = new Button();
        settingsButton.setGraphic(settingsIcon);
        settingsButton.getStyleClass().add("dashboard-settings-button");
        settingsButton.setTooltip(new Tooltip("Configuración"));
        settingsButton.setOnAction(e -> toggleSettingsMenu());

        HBox footer = new HBox(10, userLabel, spacer, settingsButton);
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.setPadding(new Insets(10));
        vbox.getChildren().add(footer);
        footer.getProperties().put("labelTextFooter", userLabel);

        settingsMenu = new VBox(6);
        settingsMenu.setPadding(new Insets(8));
        settingsMenu.setVisible(false);
        settingsMenu.getStyleClass().add("dashboard-settings-menu");
        settingsMenu.getChildren().add(createSettingsItem("🪪", "Información actual"));
        settingsMenu.getChildren().add(createSettingsItem("🔑", "Cambiar contraseña"));
        settingsMenu.setAlignment(Pos.CENTER_LEFT); 
        Button logoutBtn = createSettingsItem("🚪", "Cerrar sesión");
        logoutBtn.setOnAction(e -> onSalir());

        settingsMenu.getChildren().add(logoutBtn);
        vbox.getChildren().add(settingsMenu);
    }

    private Button createSettingsItem(String icon, String text) {
        Text iconText = new Text(icon);
        iconText.getStyleClass().add("dashboard-icon");

        Text labelText = new Text(text);
        labelText.getStyleClass().add("dashboard-header-title");
        labelText.managedProperty().bind(labelText.visibleProperty());

        HBox hbox = new HBox(10, iconText, labelText);
        hbox.setMinHeight(40);
        hbox.setAlignment(Pos.CENTER_LEFT);

        Button btn = new Button();
        btn.setGraphic(hbox);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.getStyleClass().add("dashboard-settings-item");

        btn.getProperties().put("labelText", labelText);
        btn.getProperties().put("hbox", hbox);

        // ✅ Animación hover igual que menú
        DropShadow glow = new DropShadow(20, Color.web("#E5E5E5"));
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
        
        btn.setOnAction((e) -> {
        	switch (icon) {
				case "🪪": {
					
					try {
						onObtenerInformacion();
						break;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				case "🔑": {
					try {
						onCredenciales();
						break;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
				default:
					throw new IllegalArgumentException("Unexpected value: " + icon);
			}
        });

        Tooltip.install(btn, new Tooltip(text));
        return btn;
    }
    
    private static String getId() {
	    return SessionManager.getInstance().getId();
	}
    
    private void onObtenerInformacion() throws Exception {
    	try {
    		String id = getId();
			UsuarioService service = new UsuarioService();
			var usuario = service.obtenerUsuario(id);
			UsuarioEditarFormController controller = new UsuarioEditarFormController();
			content.getChildren().add(controller.getScene("Información del", usuario));
		} catch (Exception ex) {
			throw new Exception("Error tipo: "+ ex );
		}
    }
    
    private void onCredenciales() throws Exception{
    	try {    		
    		String id = getId();
			content.getChildren().add(CredencialesFormController.getScene("Cambiar contraseña del ", id));
		} catch (Exception ex) {
			throw new Exception("Error tipo: "+ ex );
		}
    }
    
    

}
