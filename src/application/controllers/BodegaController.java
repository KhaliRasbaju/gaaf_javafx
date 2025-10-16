package application.controllers;

import java.util.List;

import application.models.response.Bodega;
import application.services.BodegaService;
import application.views.DashboardViewBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class BodegaController extends DashboardViewBase {
	private static void onActionBodega() {
		try {
			BodegaService service = new BodegaService();
			content.getChildren().setAll(BodegaFormController.getScene());	
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	public static VBox getScene(List<Bodega> bodegas) {
		Button btnAgregar = new Button("Agregar Bodega");
        TableView<Bodega> table = new TableView<>();
        

        // Columna ID
        TableColumn<Bodega, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Columna Nombre
        TableColumn<Bodega, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Columna Ubicación
        TableColumn<Bodega, String> colUbicacion = new TableColumn<>("Ubicación");
        colUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

        // Agregar columnas a la tabla
        table.getColumns().addAll(colId, colNombre, colUbicacion);

        // Cargar datos en la tabla
        ObservableList<Bodega> data = FXCollections.observableArrayList(bodegas);
        table.setItems(data);

        // Estilos visuales
        table.setPadding(new Insets(10));
        btnAgregar.setOnAction(e -> onActionBodega());

        // Contenedor principal
        VBox layout = new VBox(10,btnAgregar, table);
        layout.setPadding(new Insets(10));

        return layout;
    }
	@Override
	protected void addMenuButtons() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected String getTitleText() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void onSalir() {
		// TODO Auto-generated method stub
		
	}

}
