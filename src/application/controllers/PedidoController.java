package application.controllers;

import java.util.List;

import application.models.response.Pedido;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;



public class PedidoController {
	
	@SuppressWarnings("unchecked")
	public static VBox getScene(List<Pedido> pedidos) {

        TableView<Pedido> table = new TableView<>();

        // Columna ID
        TableColumn<Pedido, Long> colId = new TableColumn<>("ID Pedido");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Columna NIT Proveedor
        TableColumn<Pedido, Long> colNitProveedor = new TableColumn<>("NIT Proveedor");
        colNitProveedor.setCellValueFactory(new PropertyValueFactory<>("nitProveedor"));

        // Columna Valor
        TableColumn<Pedido, Double> colValor = new TableColumn<>("Valor Total");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        // Columna Fecha Pedido
        TableColumn<Pedido, String> colFechaPedido = new TableColumn<>("Fecha Pedido");
        colFechaPedido.setCellValueFactory(new PropertyValueFactory<>("fechaPedido"));

        // Columna Fecha Entrega
        TableColumn<Pedido, String> colFechaEntrega = new TableColumn<>("Fecha Entrega");
        colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));

        // Columna Recibido
        TableColumn<Pedido, Boolean> colRecibido = new TableColumn<>("Recibido");
        colRecibido.setCellValueFactory(new PropertyValueFactory<>("recibido"));
        colRecibido.setCellFactory(col -> new TableCell<>() {
            private final Text icon = new Text();

            @Override
            protected void updateItem(Boolean recibido, boolean empty) {
                super.updateItem(recibido, empty);
                if (empty || recibido == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (recibido) {
                        icon.setText("✅"); 
                        icon.getStyleClass().add("icon-recibido");
                    } else {
                        icon.setText("🕛");
                        icon.getStyleClass().add("icon-pendiente");
                    }
                    setGraphic(icon);
                    setStyle("-fx-alignment: CENTER;"); 
                }
            }
        });


        // Agregar columnas
        table.getColumns().addAll(
            colId,
            colNitProveedor,
            colValor,
            colFechaPedido,
            colFechaEntrega,
            colRecibido
        );

        // Cargar datos
        ObservableList<Pedido> data = FXCollections.observableArrayList(pedidos);
        table.setItems(data);

        // Ajustes visuales
        table.setPadding(new Insets(10));

        VBox layout = new VBox(10, table);
        layout.setPadding(new Insets(10));

        return layout;
    }
}
