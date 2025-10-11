package application.controllers;

import java.time.format.DateTimeFormatter;
import java.util.List;

import application.models.response.ReporteInventarioMovimiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ReporteInventarioMovimientoController {

    public static VBox getScene(List<ReporteInventarioMovimiento> movimientos) {

        TableView<ReporteInventarioMovimiento> table = new TableView<>();

        // Columna Producto
        TableColumn<ReporteInventarioMovimiento, String> colProducto = new TableColumn<>("Producto");
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));

        // Columna Bodega
        TableColumn<ReporteInventarioMovimiento, String> colBodega = new TableColumn<>("Bodega");
        colBodega.setCellValueFactory(new PropertyValueFactory<>("bodega"));

        // Columna Tipo (Entrada / Salida)
        TableColumn<ReporteInventarioMovimiento, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        // Columna Cantidad
        TableColumn<ReporteInventarioMovimiento, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        // Columna Fecha (formateada)
        TableColumn<ReporteInventarioMovimiento, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(cellData -> {
            var fecha = cellData.getValue().getFecha();
            String formatted = (fecha != null)
                    ? fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    : "";
            return new javafx.beans.property.SimpleStringProperty(formatted);
        });

        // Columna Observación
        TableColumn<ReporteInventarioMovimiento, String> colObservacion = new TableColumn<>("Observación");
        colObservacion.setCellValueFactory(new PropertyValueFactory<>("observacion"));

        // Agregar columnas a la tabla
        table.getColumns().addAll(colProducto, colBodega, colTipo, colCantidad, colFecha, colObservacion);

        // Convertir lista a ObservableList
        ObservableList<ReporteInventarioMovimiento> data = FXCollections.observableArrayList(movimientos);
        table.setItems(data);

        // Ajustes visuales
        table.setPadding(new Insets(10, 10, 10, 10)); 

        return new VBox(10, table);
    }
}
