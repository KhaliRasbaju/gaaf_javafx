package application;
	
import application.utils.SceneManager;
import application.views.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;



public class Main extends Application {
	
	// ======================================================
    //   INICIO DE LA APLICACIÓN
    // ======================================================
	public void start(Stage primaryStage) throws Exception {
		try {
			
			SceneManager.setStage(primaryStage);
			Scene loginScene = new LoginView().getScene();
			  primaryStage.setTitle("Inicio de Sesión");
	            primaryStage.setScene(loginScene);
	            primaryStage.getIcons().add(
	                    new Image(getClass().getResourceAsStream("/application/resources/logoGAAF.png"))
	                );
	            primaryStage.setResizable(false);
	            primaryStage.setMaximized(true);
	            primaryStage.show();
	            
	       
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
