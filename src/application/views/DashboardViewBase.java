package application.views;

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
    protected Button salir;
    protected ImageView empresaLogo;

    public DashboardViewBase() {
        vbox = new VBox(12);
        vbox.setPadding(new Insets(18));
        vbox.setPrefWidth(200);
        vbox.getStyleClass().add("dashboard-sidebar");

        title = new Text(getTitleText());
        title.getStyleClass().add("dashboard-title");
        vbox.getChildren().add(title);

        addMenuButtons();
        // El pie se agrega en cada rol específico usando addSidebarFooter(roleName)

        content = new StackPane();
        content.getStyleClass().add("dashboard-content");
        Text placeholder = new Text("Selecciona una opción del menú");
        content.getChildren().add(placeholder);
        HBox.setHgrow(content, Priority.ALWAYS);

        mainContainer = new HBox(vbox, content);

        // Reemplazar el texto por el logo
        Image logoImg = new Image(getClass().getResource("/application/resources/logoGAAF.png").toExternalForm());
        empresaLogo = new ImageView(logoImg);
        empresaLogo.setFitHeight(38);
        empresaLogo.setPreserveRatio(true);
        empresaLogo.setSmooth(true);
        empresaLogo.setCache(true);
        empresaLogo.getStyleClass().add("dashboard-header-logo");

        toggleMenu = new Button("☰");
        toggleMenu.getStyleClass().add("dashboard-toggle-button");

        salir = new Button("Salir");
        salir.getStyleClass().add("dashboard-salir-button");
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
        salir.setOnAction(e -> onSalir());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        headerBox = new HBox(16, toggleMenu, empresaLogo, spacer, salir);
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
        iconText.setStyle("-fx-font-family: 'Segoe UI Emoji'; -fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #E5E5E5;");
        Text labelText = new Text(text);
        labelText.setStyle("-fx-font-family: 'Orbitron'; -fx-font-size: 14px; -fx-fill: #E5E5E5;");
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
        Tooltip tip = new Tooltip(text);
        Tooltip.install(btn, tip);
        return btn;
    }

    private void toggleMenu() {
        double startWidth = vbox.getWidth();
        double targetWidth = menuOpen ? 64 : 200;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(vbox.prefWidthProperty(), startWidth)),
                new KeyFrame(Duration.millis(280), new KeyValue(vbox.prefWidthProperty(), targetWidth))
        );
        timeline.play();
        for (javafx.scene.Node node : vbox.getChildren()) {
            if (node instanceof Button btn) {
                Object lblObj = btn.getProperties().get("labelText");
                Object hboxObj = btn.getProperties().get("hbox");
                if (lblObj instanceof Text labelText) {
                    boolean willShow = !menuOpen;
                    labelText.setVisible(willShow);
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
        title.setText(menuOpen ? getTitleText().split(" ")[0] : getTitleText());
        menuOpen = !menuOpen;
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
 
    	Region spacer = new Region();
    	VBox.setVgrow(spacer, Priority.ALWAYS);

    	vbox.getChildren().addAll(spacer);
        
        HBox sidebarFooter = new HBox();
    	sidebarFooter.setSpacing(12);
    	sidebarFooter.setAlignment(Pos.CENTER_LEFT);
    	sidebarFooter.getStyleClass().add("dashboard-sidebar-footer");

    	Text userLabel = new Text(roleName);
    	userLabel.getStyleClass().add("dashboard-user-label");
    	userLabel.setFill(Color.WHITE);

    	Button bellButton = new Button("🔔");
    	bellButton.getStyleClass().add("dashboard-bell-button");

    	sidebarFooter.getChildren().addAll(userLabel, bellButton);
    	sidebarFooter.setPadding(new Insets(10, 10, 10, 10));

    	// Agregar al VBox al final
    	vbox.getChildren().add(sidebarFooter);
    }
}