package application.controllers;

import java.util.List;



import application.models.ReporteInventario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class InventarioController {

	public static VBox getScene(List<ReporteInventario> inventario) {
		
		TableView<ReporteInventario> table = new TableView<>();
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		// Fecha
		TableColumn<ReporteInventario, String> colFecha = new TableColumn<>("Fecha");
		colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

		// Cantidad Disponible
		TableColumn<ReporteInventario, Integer> colCantidadDisponible = new TableColumn<>("Cantidad Disponible");
		colCantidadDisponible.setCellValueFactory(new PropertyValueFactory<>("cantidadDisponible"));

		// Cantidad Reservada
		TableColumn<ReporteInventario, Integer> colCantidadReservada = new TableColumn<>("Cantidad Reservada");
		colCantidadReservada.setCellValueFactory(new PropertyValueFactory<>("cantidadReservada"));

		// Producto
		TableColumn<ReporteInventario, String> colProducto = new TableColumn<>("Producto");
		colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));

		// Bodega
		TableColumn<ReporteInventario, String> colBodega = new TableColumn<>("Bodega");
		colBodega.setCellValueFactory(new PropertyValueFactory<>("bodega"));

		// Agregar columnas a la tabla
		table.getColumns().clear();
		table.getColumns().addAll(colFecha, colCantidadDisponible, colCantidadReservada, colProducto, colBodega);

		// Ahora le asignas los datos
		ObservableList<ReporteInventario> data = FXCollections.observableArrayList(inventario);
		table.setItems(data);
		
		
		table.setPadding(new Insets(10, 10, 10, 10));
		
		return new VBox(10, table);

	}
}
