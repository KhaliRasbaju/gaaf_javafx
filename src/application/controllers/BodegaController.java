package application.controllers;

import java.util.List;

import application.models.response.Bodega;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BodegaController {

    private final StackPane content;

    // ✅ Constructor recibe el contenedor principal (StackPane)
    public BodegaController(StackPane content) {
        this.content = content;
    }

    // ✅ Método de acción no estático
    private void onActionBodega() {
        try {
            content.getChildren().setAll(BodegaFormController.getScene());
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    // ✅ Método NO estático — pertenece al objeto
    public VBox getScene(List<Bodega> bodegas) {
        Button btnAgregar = new Button("Agregar Bodega");
        TableView<Bodega> table = new TableView<>();

        TableColumn<Bodega, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Bodega, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Bodega, String> colUbicacion = new TableColumn<>("Ubicación");
        colUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

        table.getColumns().addAll(colId, colNombre, colUbicacion);

        ObservableList<Bodega> data = FXCollections.observableArrayList(bodegas);
        table.setItems(data);

        // ✅ Llamamos a onActionBodega() sin problema porque ya no es estático
        btnAgregar.setOnAction(e -> onActionBodega());

        VBox layout = new VBox(10, btnAgregar, table);
        layout.setPadding(new Insets(10));
        return layout;
    }
}
