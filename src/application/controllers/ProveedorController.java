package application.controllers;

import java.util.List;

import application.models.response.Proveedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProveedorController {

    public static VBox createView(List<Proveedor> proveedores) {
        // Crear tabla
        TableView<Proveedor> table = new TableView<>();
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Columnas
        TableColumn<Proveedor, Long> colNit = new TableColumn<>("NIT");
        colNit.setCellValueFactory(new PropertyValueFactory<>("nit"));

        TableColumn<Proveedor, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Proveedor, String> colDireccion = new TableColumn<>("Direccion");
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<Proveedor, String> colTelefono = new TableColumn<>("Telefono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<Proveedor, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        table.getColumns().addAll(colNit, colNombre, colDireccion, colTelefono, colCorreo);

        // Datos iniciales (mock)
        ObservableList<Proveedor> data = FXCollections.observableArrayList(proveedores);
        table.setItems(data);


        // Layout
        return new VBox(10, table);
    }
}
