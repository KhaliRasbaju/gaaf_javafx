package application.controllers;

import application.models.request.CommonRequest;
import application.models.response.Common;
import application.models.response.ResponseCommon;
import application.services.EntidadService;
import application.services.EntidadService;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;

public class EntidadBancariaFormController {

	private final StackPane content;
	
	
	
	 public EntidadBancariaFormController(StackPane content) {
		this.content = content;
	}

	 private static String toHex(Color color) {
	        return String.format("#%02X%02X%02X",
	                (int) (color.getRed() * 255),
	                (int) (color.getGreen() * 255),
	                (int) (color.getBlue() * 255));
	    }

    /** 🔹 Muestra un pequeño mensaje tipo Toast */
    private static void showNotification(Scene scene, String text, Color color) {
        Label notification = new Label(text);
        notification.getStyleClass().add("notification-toast");
        notification.setStyle("-fx-background-color: " + toHex(color) + ";"
                + "-fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 8px;");

        Popup popup = new Popup();
        popup.getContent().add(notification);
        popup.setAutoFix(true);

        // Posición en parte inferior central
        double x = scene.getWindow().getX() + scene.getWidth() / 2 - 100;
        double y = scene.getWindow().getY() + scene.getHeight() - 100;
        popup.show(scene.getWindow(), x, y);

        // 🔹 Animación FadeOut
        FadeTransition fade = new FadeTransition(Duration.seconds(2.5), notification);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(ev -> popup.hide());
        fade.play();
    }

	
	
	private static void onActionCrear(CommonRequest request) {
		try {
			 EntidadService service = new EntidadService();
			 service.crearEntidad(request);
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	
	
	private static void onActionEditar(Long id,CommonRequest request) {
		try {
			EntidadService service = new EntidadService();
			service.editarEntidad(id, request);
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	
	
	
	
    public static StackPane getScene(String modo, Common entidad) {
        StackPane content = new StackPane();

        Label lblTitulo = new Label(modo + " Entidad Bancaria");
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre de la entidad");

        if (entidad != null) {
            txtNombre.setText(entidad.getNombre());
        }

        Button btnGuardar = new Button("Guardar");
    
        HBox botones = new HBox(10, btnGuardar);
        botones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, lblTitulo, txtNombre, botones);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));


        btnGuardar.setOnAction(e -> {
            try {
            	CommonRequest request = new CommonRequest(txtNombre.getText());
                if (entidad == null) {
                   onActionCrear(request);
                   showNotification(btnGuardar.getScene(), "✅ Entidad bancaria creada correctamente", Color.GREEN);
                   txtNombre.clear();
                } else {
                   onActionEditar(entidad.getId(), request);	
                   showNotification(btnGuardar.getScene(), "✅ Entidad bancaria actualizada correctamente", Color.GREEN);
                   txtNombre.clear();
                }
            } catch (Exception ex) {
            	showNotification(btnGuardar.getScene(), "❎ Error al guardar la entidad bancaria", Color.RED);
                System.out.println("Error tipo: " + ex);
            }
        });

        content.getChildren().add(layout);
        return content;
    }
}
