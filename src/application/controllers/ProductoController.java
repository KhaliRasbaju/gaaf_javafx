package application.controllers;


import java.util.List;

import application.models.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductoController {

    public static VBox getScene(List<Producto> productos) {

        TableView<Producto> table = new TableView<>();

        // Columna ID
        TableColumn<Producto, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Columna Nombre
        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Columna Descripción
        TableColumn<Producto, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Agregar columnas a la tabla
        table.getColumns().addAll(colId, colNombre, colDescripcion);

        // Datos de prueba
        ObservableList<Producto> data = FXCollections.observableArrayList(productos);

        table.setItems(data);
        
        table.setPadding(new Insets(10, 10, 10, 10));

        return new VBox(10, table);
    }
}
