package application.controllers;

import java.time.format.DateTimeFormatter;
import java.util.List;

import application.models.response.ReporteCompra;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ReporteCompraController {

    @SuppressWarnings("unchecked")
	public static VBox getScene(List<ReporteCompra> reportes) {
    	
    	Label lblTitulo = new Label("Reporte compras");
		lblTitulo.getStyleClass().add("form-title");
    	
        TableView<ReporteCompra> table = new TableView<>();

        // === Definir columnas ===
        TableColumn<ReporteCompra, Long> colId = new TableColumn<>("ID Pedido");
        colId.setCellValueFactory(new PropertyValueFactory<>("idPedido"));

        TableColumn<ReporteCompra, String> colProveedor = new TableColumn<>("Proveedor");
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));

        TableColumn<ReporteCompra, String> colContacto = new TableColumn<>("Contacto");
        colContacto.setCellValueFactory(new PropertyValueFactory<>("contactoProveedor"));

        TableColumn<ReporteCompra, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoProveedor"));

        TableColumn<ReporteCompra, String> colProducto = new TableColumn<>("Producto");
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));

        TableColumn<ReporteCompra, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<ReporteCompra, Float> colPeso = new TableColumn<>("Peso (kg)");
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));

        TableColumn<ReporteCompra, Double> colValor = new TableColumn<>("Valor Pedido");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorPedido"));

        TableColumn<ReporteCompra, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        TableColumn<ReporteCompra, Object> colFechaPedido = new TableColumn<>("Fecha Pedido");
        colFechaPedido.setCellValueFactory(new PropertyValueFactory<>("fechaPedido"));

        TableColumn<ReporteCompra, Object> colFechaEntrega = new TableColumn<>("Fecha Entrega");
        colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));

        // === Formatear fechas ===
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        colFechaPedido.setCellFactory(column -> new TableCell<ReporteCompra, Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                    setText(null);
                else
                    setText(formatter.format(((java.time.LocalDateTime) item)));
            }
        });
        colFechaEntrega.setCellFactory(column -> new TableCell<ReporteCompra, Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                    setText(null);
                else
                    setText(formatter.format(((java.time.LocalDateTime) item)));
            }
        });

        // === Agregar columnas a la tabla ===
        table.getColumns().addAll(
                colId, colProveedor, colContacto, colTelefono,
                colProducto, colCantidad, colPeso, colValor,
                colEstado, colFechaPedido, colFechaEntrega
        );

        // === Configurar datos ===
        ObservableList<ReporteCompra> data = FXCollections.observableArrayList(reportes);
        table.setItems(data);

        // === Ajustes visuales ===
        table.setPadding(new Insets(10));
        table.setStyle("-fx-font-size: 13px;");

        VBox layout = new VBox(10, lblTitulo,table);
        layout.setAlignment(Pos.TOP_CENTER);
        return layout;
    }
}
