package application.controllers;

import java.util.List;

import application.models.response.ReporteInventarioProductoBodega;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ReporteInventarioController {
	
	public static VBox getScene(List<ReporteInventarioProductoBodega> reportes) {

	    TableView<ReporteInventarioProductoBodega> table = new TableView<>();
	
	    // Columna Bodega
	    TableColumn<ReporteInventarioProductoBodega, String> colBodega = new TableColumn<>("Bodega");
	    colBodega.setCellValueFactory(new PropertyValueFactory<>("bodega"));
	
	    // Columna Producto
	    TableColumn<ReporteInventarioProductoBodega, String> colProducto = new TableColumn<>("Producto");
	    colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
	
	    // Columna Cantidad
	    TableColumn<ReporteInventarioProductoBodega, Integer> colCantidad = new TableColumn<>("Cantidad");
	    colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
	
	    // Columna Fecha
	    TableColumn<ReporteInventarioProductoBodega, String> colFecha = new TableColumn<>("Fecha");
	    colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
	
	    // Agregar columnas a la tabla
	    table.getColumns().addAll(colBodega, colProducto, colCantidad, colFecha);
	
	    // Convertir lista a ObservableList
	    ObservableList<ReporteInventarioProductoBodega> data = FXCollections.observableArrayList(reportes);
	    table.setItems(data);
	
	    // Padding y espaciado
	    table.setPadding(new Insets(10, 10, 10, 10));

        return new VBox(10, table);
    }
}
