package application.controllers;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import application.models.Pedido;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.shape.SVGPath;



public class PedidoController {
	public static VBox getScene(List<Pedido> pedidos) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		TableView<Pedido> table = new TableView<>();
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		TableColumn<Pedido, Long> colNit = new TableColumn<>("NIT Proveedor");
		colNit.setCellValueFactory(new PropertyValueFactory<>("nitProveedor"));

		
		TableColumn<Pedido, String> colProductos = new TableColumn<>("Productos");
		colProductos.setCellValueFactory(cellData -> {
		    List<String> productos = cellData.getValue().getProductos();
		    return new javafx.beans.property.SimpleStringProperty(String.join(", ", productos));
		});

		
		TableColumn<Pedido, Integer> colCantidad = new TableColumn<>("Cantidad");
		colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

		
		TableColumn<Pedido, Double> colValor = new TableColumn<>("Valor");
		colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		
		colValor.setCellFactory(column -> new TableCell<Pedido, Double>() {
		    private final DecimalFormat df = new DecimalFormat("#,###");

		    @Override
		    protected void updateItem(Double item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText(null);
		        } else {
		            setText(df.format(item));
		        }
		    }
		});

		
		TableColumn<Pedido, String> colFechaPedido = new TableColumn<>("Fecha Pedido");
		colFechaPedido.setCellValueFactory(new PropertyValueFactory<>("fechaPedido"));
		colFechaPedido.setCellFactory(column -> new TableCell<Pedido, String>() {
		    @Override
		    protected void updateItem(String item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText(null);
		        } else {
		            // Convertimos el String ISO a LocalDateTime
		            LocalDateTime fecha = LocalDateTime.parse(item);
		            setText(fecha.format(formatter));
		        }
		    }
		});


		
		TableColumn<Pedido, Boolean> colRecibido = new TableColumn<>("Recibido");
		colRecibido.setCellValueFactory(new PropertyValueFactory<>("recibido"));
		colRecibido.setCellFactory(column -> new TableCell<Pedido, Boolean>() {
		    private final Label iconLabel = new Label();

		    @Override
		    protected void updateItem(Boolean item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty || item == null) {
		            setGraphic(null);
		        } else {
		            if (item) {
		                // Ícono verde (check) cuando recibido = true
		                iconLabel.setGraphic(new SVGPath() {{
		                    setContent("M10 15l-3.5-3.5 1.41-1.41L10 12.17l5.09-5.09L16.5 8.5z"); // check mark
		                }});
		                iconLabel.setStyle("-fx-text-fill: green;");
		            } else {
		                // Ícono rojo (X) cuando recibido = false
		                iconLabel.setGraphic(new SVGPath() {{
		                    setContent("M6 18L18 6M6 6l12 12"); // X
		                }});
		                iconLabel.setStyle("-fx-text-fill: red;");
		            }
		            setGraphic(iconLabel);
		        }
		    }
		});
		
		TableColumn<Pedido, String> colFechaEntrega = new TableColumn<>("Fecha Entrega");
		colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));
		colFechaEntrega.setCellFactory(column -> new TableCell<Pedido, String>() {
		    @Override
		    protected void updateItem(String item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText(null);
		        } else {
		            // Convertimos el String ISO a LocalDateTime
		            LocalDateTime fecha = LocalDateTime.parse(item);
		            setText(fecha.format(formatter));
		        }
		    }
		});


		
		table.getColumns().addAll(colNit, colProductos, colCantidad, colValor, colFechaPedido, colRecibido, colFechaEntrega);

		
		ObservableList<Pedido> data = FXCollections.observableArrayList(pedidos);
		table.setItems(data);
		
		
		table.setPadding(new Insets(10, 10, 10, 10));
		
		return new VBox(10, table);

	}
}
