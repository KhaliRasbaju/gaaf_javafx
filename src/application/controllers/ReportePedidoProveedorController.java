package application.controllers;

import java.time.format.DateTimeFormatter;
import java.util.List;

import application.models.response.ReportePedidoProveedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ReportePedidoProveedorController {
	
	public static VBox getScene(List<ReportePedidoProveedor> pedidos) {
		
		Label lblTitulo = new Label("Reporte de Pedido asociado a Proveedor");
		lblTitulo.getStyleClass().add("form-title");

        TableView<ReportePedidoProveedor> table = new TableView<>();

        // Columna ID Pedido
        TableColumn<ReportePedidoProveedor, Long> colId = new TableColumn<>("ID Pedido");
        colId.setCellValueFactory(new PropertyValueFactory<>("idPedido"));

        // Columna Fecha Pedido
        TableColumn<ReportePedidoProveedor, String> colFechaPedido = new TableColumn<>("Fecha Pedido");
        colFechaPedido.setCellValueFactory(cellData -> {
            var fecha = cellData.getValue().getFechaPedido();
            String formatted = (fecha != null)
                    ? fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    : "";
            return new javafx.beans.property.SimpleStringProperty(formatted);
        });

        // Columna Fecha Entrega
        TableColumn<ReportePedidoProveedor, String> colFechaEntrega = new TableColumn<>("Fecha Entrega");
        colFechaEntrega.setCellValueFactory(cellData -> {
            var fecha = cellData.getValue().getFechaEntrega();
            String formatted = (fecha != null)
                    ? fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    : "";
            return new javafx.beans.property.SimpleStringProperty(formatted);
        });

        // Columna Estado
        TableColumn<ReportePedidoProveedor, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Columna Proveedor
        TableColumn<ReportePedidoProveedor, String> colProveedor = new TableColumn<>("Proveedor");
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));

        // Columna Contacto Proveedor
        TableColumn<ReportePedidoProveedor, String> colContacto = new TableColumn<>("Contacto");
        colContacto.setCellValueFactory(new PropertyValueFactory<>("contactoProveedor"));

        // Columna Referencia de Pago
        TableColumn<ReportePedidoProveedor, String> colReferencia = new TableColumn<>("Referencia Pago");
        colReferencia.setCellValueFactory(new PropertyValueFactory<>("referenciaPago"));

        // Columna Método de Pago
        TableColumn<ReportePedidoProveedor, String> colMetodo = new TableColumn<>("Método Pago");
        colMetodo.setCellValueFactory(new PropertyValueFactory<>("metodoPago"));

        // Columna Número de Cuenta
        TableColumn<ReportePedidoProveedor, String> colCuenta = new TableColumn<>("N° Cuenta");
        colCuenta.setCellValueFactory(new PropertyValueFactory<>("nuemroCuenta"));

        // Columna Tipo de Cuenta
        TableColumn<ReportePedidoProveedor, String> colTipoCuenta = new TableColumn<>("Tipo Cuenta");
        colTipoCuenta.setCellValueFactory(new PropertyValueFactory<>("tipoCuenta"));

        // Columna Entidad Bancaria
        TableColumn<ReportePedidoProveedor, String> colEntidad = new TableColumn<>("Entidad Bancaria");
        colEntidad.setCellValueFactory(new PropertyValueFactory<>("entidadBancaria"));

        // Columna Valor del Pedido
        TableColumn<ReportePedidoProveedor, Double> colValor = new TableColumn<>("Valor Pedido");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorPedido"));

        // Agregar columnas a la tabla
        table.getColumns().addAll(
                colId, colFechaPedido, colFechaEntrega, colEstado, colProveedor, colContacto,
                colReferencia, colMetodo, colCuenta, colTipoCuenta, colEntidad, colValor
        );

        // Convertir lista en ObservableList
        ObservableList<ReportePedidoProveedor> data = FXCollections.observableArrayList(pedidos);
        table.setItems(data);

        // Ajustes visuales
        table.setPadding(new Insets(10, 10, 10, 10));
        VBox layout = new VBox(10, lblTitulo,table);
        layout.setAlignment(Pos.TOP_CENTER);
        return layout;
    }
}
