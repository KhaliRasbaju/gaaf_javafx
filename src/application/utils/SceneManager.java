package application.utils;

import javafx.scene.Scene;
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
		stage.show();
	}
	
}
