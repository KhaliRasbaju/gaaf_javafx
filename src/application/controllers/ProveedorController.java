package application.controllers;

import java.util.List;

import application.models.response.Proveedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProveedorController {

	public static VBox getScene(List<Proveedor> proveedores) {

        TableView<Proveedor> table = new TableView<>();

        // 🔹 Columna NIT
        TableColumn<Proveedor, Long> colNit = new TableColumn<>("NIT");
        colNit.setCellValueFactory(new PropertyValueFactory<>("nit"));

        // 🔹 Columna Nombre
        TableColumn<Proveedor, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // 🔹 Columna Teléfono
        TableColumn<Proveedor, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        // 🔹 Columna Correo
        TableColumn<Proveedor, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));



        // Agregar columnas a la tabla
        table.getColumns().addAll(colNit, colNombre, colTelefono, colCorreo);

        // Cargar datos
        ObservableList<Proveedor> data = FXCollections.observableArrayList(proveedores);
        table.setItems(data);

        // Ajustes visuales
        table.setPadding(new Insets(10, 10, 10, 10));
        table.setPrefHeight(400);

        VBox layout = new VBox(10, table);
        layout.setPadding(new Insets(10, 10, 10, 10));

        return layout;
    }
}
