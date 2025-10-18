package application.controllers;

import java.util.List;

import application.models.response.Common;
import application.models.response.ResponseCommon;
import application.services.MetodoPagoService;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class MetodoPagoController {

    private final StackPane content;

    public MetodoPagoController(StackPane content) {
        this.content = content;
    }

    // 🔹 Convierte un color a formato hexadecimal
    private static String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    /** 🔹 Muestra una notificación tipo "toast" */
    private static void showNotification(Scene scene, String text, Color color) {
        Label notification = new Label(text);
        notification.getStyleClass().add("notification-toast");
        notification.setStyle("-fx-background-color: " + toHex(color) + ";"
                + "-fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 8px;");

        Popup popup = new Popup();
        popup.getContent().add(notification);
        popup.setAutoFix(true);

        double x = scene.getWindow().getX() + scene.getWidth() / 2 - 100;
        double y = scene.getWindow().getY() + scene.getHeight() - 100;
        popup.show(scene.getWindow(), x, y);

        FadeTransition fade = new FadeTransition(Duration.seconds(2.5), notification);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(ev -> popup.hide());
        fade.play();
    }

    // 🔹 Acción para abrir formulario (nuevo método de pago)
    private void onActionMetodoPago() {
        try {
            content.getChildren().setAll(MetodoPagoFormController.getScene("Registrar", null));
        } catch (Exception ex) {
            System.out.println("Error al abrir formulario: " + ex);
        }
    }

    // 🔹 Acción para editar método de pago
    private void onActionEditar(Long id) {
        try {
            MetodoPagoService service = new MetodoPagoService();
            Common metodoPago = service.obtenerMetodo(id);
            MetodoPagoFormController controller = new MetodoPagoFormController(content);
            content.getChildren().setAll(controller.getScene("Editar", metodoPago));
        } catch (Exception ex) {
            System.out.println("Error al editar método de pago: " + ex);
        }
    }

    // 🔹 Acción para eliminar método de pago
    private static ResponseCommon onActionEliminar(Long id, Button button) throws Exception {
        try {
            MetodoPagoService service = new MetodoPagoService();
            ResponseCommon respuesta = service.eliminarMetodo(id);
            return respuesta;
        } catch (Exception ex) {
            System.out.println("Error al eliminar método de pago: " + ex);
            throw new Exception(ex);
        }
    }

    // ✅ Muestra la tabla con los métodos de pago
    public VBox getScene(List<Common> metodosPago) {
        Button btnAgregar = new Button("+");
        btnAgregar.getStyleClass().add("btn-agregar");

        TableView<Common> table = new TableView<>();

        // Columnas
        TableColumn<Common, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Common, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Common, Void> colAcciones = new TableColumn<>("Acciones");

        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("✏️");
            private final Button btnEliminar = new Button("🗑️");
            private final HBox contenedor = new HBox(5, btnEditar, btnEliminar);

            {
                btnEditar.getStyleClass().add("btn-editar");
                btnEliminar.getStyleClass().add("btn-eliminar");
                contenedor.setAlignment(Pos.CENTER);

                btnEditar.setOnAction(e -> {
                    Common metodoPago = getTableView().getItems().get(getIndex());
                    onActionEditar(metodoPago.getId());
                });

                btnEliminar.setOnAction(e -> {
                    Common metodoPago = getTableView().getItems().get(getIndex());
                    try {
                        var respuesta = onActionEliminar(metodoPago.getId(), btnEliminar);
                        if (respuesta.getStatus() != 200) {
                            showNotification(btnEliminar.getScene(),
                                    String.format("❎ %s", respuesta.getMessage()), Color.RED);
                        } else {
                            showNotification(btnEliminar.getScene(),
                                    String.format("✅ %s", respuesta.getMessage()), Color.GREEN);
                            getTableView().getItems().remove(metodoPago);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error tipo: " + ex);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : contenedor);
            }
        });

        table.getColumns().addAll(colId, colNombre, colAcciones);

        ObservableList<Common> data = FXCollections.observableArrayList(metodosPago);
        table.setItems(data);

        btnAgregar.setOnAction(e -> onActionMetodoPago());

        VBox layout = new VBox(10, btnAgregar, table);
        layout.setPadding(new Insets(10));
        return layout;
    }
}
