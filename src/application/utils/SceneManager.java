package application.utils;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SceneManager {

	private static Stage stage;
	
	public static void setStage(Stage st) {
		stage = st;
	}
	
	public static void changeScene(Scene scene, String title) {
		stage.setTitle(title);
		scene.getStylesheets().add(SceneManager.class.getResource("/application/resources/application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(true);
        
     
		
        // Esperar al siguiente ciclo de JavaFX y luego forzar el tamaño al de la pantalla
        Platform.runLater(() -> {
            // Obtener dimensiones de la pantalla principal
            var screenBounds = Screen.getPrimary().getVisualBounds();

            // Forzar tamaño al 100% de la pantalla
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());

            // Ahora bloquear redimensionado si quieres que no cambie el tamaño
            stage.setResizable(false);
        });
		stage.show();
	}
	
}