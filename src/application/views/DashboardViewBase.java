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
import javafx.scene.control.ScrollPane;

public abstract class DashboardViewBase {

    // ================================
    //       ATRIBUTOS PRINCIPALES
    // ================================
    protected boolean menuOpen = true;
    protected VBox vbox;
    protected StackPane content;
    protected Text title;
    protected HBox mainContainer;
    protected HBox headerBox;
    protected Button toggleMenu;
    protected Button settingsButton;
    protected ImageView empresaLogo;
    private ScrollPane sidebarScroll;

    private VBox settingsMenu;
    private boolean settingsOpen = false;

    private Text userLabel;

    // ================================
    //       CONSTRUCTOR
    // ================================
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

        // SCROLLPANE DEL SIDEBAR (★ NUEVO ★)
        sidebarScroll = new ScrollPane(vbox);
        sidebarScroll.setFitToWidth(true);
        sidebarScroll.setFitToHeight(true);
        sidebarScroll.setMinHeight(0); 
        sidebarScroll.setPrefHeight(Double.MAX_VALUE);
        HBox.setHgrow(sidebarScroll, Priority.NEVER);
        VBox.setVgrow(vbox, Priority.ALWAYS);


        // Hacerlo totalmente transparente
        sidebarScroll.setStyle("-fx-background-color: transparent;");
        sidebarScroll.setBackground(Background.EMPTY);
        sidebarScroll.setBorder(Border.EMPTY);

        sidebarScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sidebarScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        mainContainer = new HBox(sidebarScroll, content);

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

    // ============================================
    //         METODOS ABSTRACTOS DEL DASHBOARD
    // ============================================
    protected abstract void addMenuButtons();
    protected abstract String getTitleText();
    protected abstract void onSalir();

    // ============================================
    //         CREAR BOTONES DEL MENU
    // ============================================
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

    // ============================================
    //          MOSTRAR / OCULTAR AJUSTES
    // ============================================
    private void toggleSettingsMenu() {
        settingsOpen = !settingsOpen;
        settingsMenu.setVisible(settingsOpen);
    }

    // ============================================
    //          MOSTRAR / OCULTAR MENU
    // ============================================
    private void toggleMenu() {

        double start = sidebarScroll.getPrefWidth();
        double target = menuOpen ? 140 : 300;

        Timeline t = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(sidebarScroll.prefWidthProperty(), start),
                        new KeyValue(sidebarScroll.minWidthProperty(), start),
                        new KeyValue(sidebarScroll.maxWidthProperty(), start)
                ),
                new KeyFrame(Duration.millis(280),
                        new KeyValue(sidebarScroll.prefWidthProperty(), target, Interpolator.EASE_BOTH),
                        new KeyValue(sidebarScroll.minWidthProperty(), target, Interpolator.EASE_BOTH),
                        new KeyValue(sidebarScroll.maxWidthProperty(), target, Interpolator.EASE_BOTH)
                )
        );

        t.play();


        // 🔥 Ocultar todos los textos: botones, footer, y settings
        for (Node node : vbox.getChildren()) {

            // Menú principal
            if (node instanceof Button btn) {
                Text lbl = (Text) btn.getProperties().get("labelText");
                if (lbl != null) lbl.setVisible(!menuOpen);
            }

            // Footer (usuario + engranaje)
            if (node.getProperties().containsKey("labelTextFooter")) {
                Text lblFooter = (Text) node.getProperties().get("labelTextFooter");
                if (lblFooter != null) lblFooter.setVisible(!menuOpen);
            }

            // ⚙ Textos del menú de configuración
            if (node instanceof VBox settingsBox) {
                for (Node n : settingsBox.getChildren()) {
                    if (n instanceof Button btnSettings) {
                        Text lblSettings = (Text) btnSettings.getProperties().get("labelText");
                        if (lblSettings != null) lblSettings.setVisible(!menuOpen);
                    }
                }
            }
        }

        menuOpen = !menuOpen;

        if (!menuOpen && settingsOpen) toggleSettingsMenu();
    }


    // ============================================
    //              VISTA PRINCIPAL
    // ============================================
    public Scene getScene() {
        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(mainContainer);
        root.setCenter(mainContainer);
        VBox.setVgrow(mainContainer, Priority.ALWAYS);
        HBox.setHgrow(content, Priority.ALWAYS);
        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(getClass().getResource("/application/resources/application.css").toExternalForm());
        return scene;
    }

    // ============================================
    //        AÑADIR FOOTER AL SIDEBAR
    // ============================================
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

        Button logoutBtn = createSettingsItem("🚪", "Cerrar sesión");
        logoutBtn.setOnAction(e -> onSalir());
        settingsMenu.getChildren().add(logoutBtn);

        vbox.getChildren().add(settingsMenu);
    }

    // ============================================
    //       CREAR ITEM DEL MENÚ DE AJUSTES
    // ============================================
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
            try {
                switch (icon) {
                    case "🪪": onObtenerInformacion(); break;
                    case "🔑": onCredenciales(); break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Tooltip.install(btn, new Tooltip(text));
        return btn;
    }

    // ============================================
    //        OBTENER ID DE SESIÓN ACTUAL
    // ============================================
    private static String getId() {
        return SessionManager.getInstance().getId();
    }

    // ============================================
    //         ACCION: INFORMACIÓN DEL USUARIO
    // ============================================
    private void onObtenerInformacion() throws Exception {
        try {
            String id = getId();
            UsuarioService service = new UsuarioService();
            var usuario = service.obtenerUsuario(id);
            UsuarioEditarFormController controller = new UsuarioEditarFormController(content);
            content.getChildren().add(controller.getScene("Información del", usuario));
        } catch (Exception ex) {
            throw new Exception("Error tipo: " + ex);
        }
    }
    
    // ============================================
    //       ACCION: CAMBIAR CREDENCIALES
    // ============================================
    private void onCredenciales() throws Exception {
        try {
            String id = getId();
            content.getChildren().add(CredencialesFormController.getScene("Cambiar contraseña del ", id));
        } catch (Exception ex) {
            throw new Exception("Error tipo: " + ex);
        }
    }
}
