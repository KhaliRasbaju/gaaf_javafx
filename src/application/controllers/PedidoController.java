package application.controllers;

import java.util.List;
import application.models.response.Pedido;
import application.models.response.ResponseCommon;
import application.services.PedidoService;
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

public class PedidoController {

    private final StackPane content;

    public PedidoController(StackPane content) {
        this.content = content;
    }

    // 🔹 Escena principal (tabla)
    @SuppressWarnings("unchecked")
    public VBox getScene(List<Pedido> pedidos) {

        Button btnAgregar = new Button("+");
        btnAgregar.getStyleClass().add("btn-agregar");

        TableView<Pedido> table = new TableView<>();

        TableColumn<Pedido, Long> colId = new TableColumn<>("ID Pedido");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Pedido, Long> colNitProveedor = new TableColumn<>("NIT Proveedor");
        colNitProveedor.setCellValueFactory(new PropertyValueFactory<>("nitProveedor"));

        TableColumn<Pedido, Double> colValor = new TableColumn<>("Valor Total");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        TableColumn<Pedido, String> colFechaPedido = new TableColumn<>("Fecha Pedido");
        colFechaPedido.setCellValueFactory(new PropertyValueFactory<>("fechaPedido"));

        TableColumn<Pedido, String> colFechaEntrega = new TableColumn<>("Fecha Entrega");
        colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));

        TableColumn<Pedido, Boolean> colRecibido = new TableColumn<>("Recibido");
        colRecibido.setCellValueFactory(new PropertyValueFactory<>("recibido"));
        colRecibido.setCellFactory(col -> new TableCell<>() {
            private final Label label = new Label();

            @Override
            protected void updateItem(Boolean recibido, boolean empty) {
                super.updateItem(recibido, empty);
                if (empty || recibido == null) {
                    setGraphic(null);
                } else {
                    label.setText(recibido ? "✅" : "🕛");
                    setGraphic(label);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        TableColumn<Pedido, Void> colAcciones = new TableColumn<>("Acciones");
        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("✏️");
            private final Button btnEliminar = new Button("🗑️");
            private final HBox contenedor = new HBox(5, btnEditar, btnEliminar);

            {
                btnEditar.getStyleClass().add("btn-editar");
                btnEliminar.getStyleClass().add("btn-eliminar");
                contenedor.setAlignment(Pos.CENTER);

                btnEditar.setOnAction(e -> {
                    Pedido pedido = getTableView().getItems().get(getIndex());
                    onActionEditar(pedido.getId());
                });

                btnEliminar.setOnAction(e -> {
                    Pedido pedido = getTableView().getItems().get(getIndex());
                    try {
                        var resp = onActionEliminar(pedido.getId());
                        if (resp.getStatus() == 200) {
                            showNotification(btnEliminar.getScene(), "✅ Pedido eliminado", Color.GREEN);
                            getTableView().getItems().remove(pedido);
                        } else {
                            showNotification(btnEliminar.getScene(), "❌ " + resp.getMessage(), Color.RED);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error tipo: " + ex);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else setGraphic(contenedor);
            }
        });

        table.getColumns().addAll(colId, colNitProveedor, colValor, colFechaPedido, colFechaEntrega, colRecibido, colAcciones);

        ObservableList<Pedido> data = FXCollections.observableArrayList(pedidos);
        table.setItems(data);

        btnAgregar.setOnAction(e -> onActionAgregar());

        VBox layout = new VBox(10, btnAgregar, table);
        layout.setPadding(new Insets(10));

        return layout;
    }

    private void onActionAgregar() {
        try {
            content.getChildren().setAll(PedidoFormController.getScene("Registrar", null));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    private void onActionEditar(Long id) {
        try {
            PedidoService service = new PedidoService();
            Pedido pedido = service.obtenerPedido(id);
            content.getChildren().setAll(PedidoFormController.getScene("Editar", pedido));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    private ResponseCommon onActionEliminar(Long id) throws Exception {
        PedidoService service = new PedidoService();
        return service.eliminarPedido(id);
    }

    private static void showNotification(Scene scene, String text, Color color) {
        Label notification = new Label(text);
        notification.setStyle("-fx-background-color: " + toHex(color) + "; -fx-text-fill: white; -fx-padding: 10px;");
        notification.setAlignment(Pos.CENTER);

        Popup popup = new Popup();
        popup.getContent().add(notification);
        popup.show(scene.getWindow());

        FadeTransition fade = new FadeTransition(Duration.seconds(2.5), notification);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> popup.hide());
        fade.play();
    }

    private static String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
