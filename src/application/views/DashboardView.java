package application.views;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DashboardView {

	private boolean menuOpen = true;
	
	public Scene getScene() {
		VBox vbox = new VBox(15);
		vbox.setPadding(new Insets(20));
		vbox.setPrefWidth(200);
		vbox.setStyle("-fx-background-color: #2C3E50;");
		
		Text title = new Text("Dasboard");
		title.setFont(Font.font(20));
		title.setFill(Color.WHITE);
		
		
		Button btnProductos = new Button("Productos");
        Button btnProveedor = new Button("Proveedor");
        Button btnBodega = new Button("Bodega");
        Button btnInventario = new Button("Inventario");
        Button btnPedido = new Button("Pedido");
        
        vbox.getChildren().addAll(title, btnProductos, btnProveedor, btnBodega, btnInventario, btnPedido);
        
        
        StackPane content = new StackPane();
        content.setStyle("-fx-background-color: #ECF0F1;");
        content.getChildren().add(new Text("Selecciona una opcion del menu"));
        
        btnProductos.setOnAction(e -> content.getChildren().setAll(new Text("Vista de Productos")));
        btnProveedor.setOnAction(e -> content.getChildren().setAll(new Text("Vista de Proveedor")));
        btnBodega.setOnAction(e -> content.getChildren().setAll(new Text("Vista de Bodega")));
        btnInventario.setOnAction(e -> content.getChildren().setAll(new Text("Vista de Inventario")));
        btnPedido.setOnAction(e -> content.getChildren().setAll(new Text("Vista de Pedido")));
        
        
        Button toggleMenu = new Button("☰");
        
        toggleMenu.setOnAction(e -> {
        	TranslateTransition slide = new TranslateTransition(Duration.millis(300), vbox);
        	
        	if(menuOpen) {
        		slide.setToX(-200);
        		menuOpen = false;
        	}else {
        		slide.setToX(0);
        		menuOpen = true;
        	}
        	slide.play();
        	
        });
		
        
        BorderPane root = new BorderPane();
        root.setLeft(vbox);
        root.setTop(toggleMenu);
        root.setCenter(content);
        
        Scene scene = new Scene(root, 800, 600);
        return  scene;
        
	}
}
