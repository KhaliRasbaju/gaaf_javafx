package application.controllers;

import java.util.List;

import application.models.response.Bodega;
import application.models.response.ResponseCommon;
import application.services.BodegaService;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.util.Duration;

public class BodegaController {

    private final StackPane content;

    
    // ✅ Constructor recibe el contenedor principal (StackPane)
    public BodegaController(StackPane content) {
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


    // ✅ Método de acción no estático
    private void onActionBodega() {
        try {
            content.getChildren().setAll(BodegaFormController.getScene("Registrar", null));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }
    
    private void onActionEditar(Long id) {
		try {
			BodegaService service = new BodegaService();
			Bodega bodega = service.obtenerBodega(id);
			content.getChildren().setAll(BodegaFormController.getScene("Editar", bodega));
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}

    private static ResponseCommon onActionEliminar(Long id, Button button) throws Exception {
		try {
			BodegaService service = new BodegaService();
			ResponseCommon respuesta = service.eliminarBodega(id);
			return respuesta;
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
			throw new Exception(ex);
		}
	}
    
    // ✅ Método NO estático — pertenece al objeto
    public VBox getScene(List<Bodega> bodegas) {
    	// 🔹 Botón de agregar tipo “+” (usará estilos desde el CSS)
    	Button btnAgregar = new Button("+");
    	btnAgregar.getStyleClass().add("btn-agregar");
    	
        TableView<Bodega> table = new TableView<>();

        TableColumn<Bodega, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Bodega, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Bodega, String> colUbicacion = new TableColumn<>("Ubicación");
        colUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        
        TableColumn<Bodega, Void> colAcciones = new TableColumn<>("Acciones");

        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("✏️");
            private final Button btnEliminar = new Button("🗑️");
            private final HBox contenedor = new HBox(5, btnEditar, btnEliminar);

            {
                // 🔹 Aplicar clases CSS
                btnEditar.getStyleClass().add("btn-editar");
                btnEliminar.getStyleClass().add("btn-eliminar");

                contenedor.setAlignment(Pos.CENTER);

                // 🔹 Acción de editar
                btnEditar.setOnAction(e -> {
                    Bodega bodega = getTableView().getItems().get(getIndex());
                    onActionEditar(bodega.getId());
                });

                // 🔹 Acción de eliminar
                btnEliminar.setOnAction(e -> {
                    Bodega bodega = getTableView().getItems().get(getIndex());
                    try {
                        var respuesta = onActionEliminar(bodega.getId(), btnEliminar);
                        if (respuesta.getStatus() != 200) {
                            showNotification(btnEliminar.getScene(),
                                    String.format("❎ %s", respuesta.getMessage()), Color.RED);
                        } else {
                            showNotification(btnEliminar.getScene(),
                                    String.format("✅ %s", respuesta.getMessage()), Color.GREEN);
                            getTableView().getItems().remove(bodega);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error tipo: " + ex);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(contenedor);
                }
            }
        });

        table.getColumns().addAll(colId, colNombre, colUbicacion, colAcciones);

        ObservableList<Bodega> data = FXCollections.observableArrayList(bodegas);
        table.setItems(data);

        // ✅ Llamamos a onActionBodega() sin problema porque ya no es estático
        btnAgregar.setOnAction(e -> onActionBodega());

        VBox layout = new VBox(10, btnAgregar, table);
        layout.setPadding(new Insets(10));
        return layout;
    }
}
