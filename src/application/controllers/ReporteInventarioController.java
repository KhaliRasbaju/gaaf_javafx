package application.controllers;

import application.models.ReporteBodega;
import application.models.ReporteInventarioResponse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class ReporteInventarioController {
	
	public static VBox getScene(ReporteInventarioResponse reporte) {
		
		var bodega = reporte.getReporteBodega();
		Integer total = reporte.getCantidad_total();
		

		Label lblTitulo = new Label("Producto Total");
		Label lblValor = new Label(total.toString());
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		HBox totalBox = new HBox(10, lblTitulo, spacer, lblValor);

		totalBox.setPadding(new Insets(20,20,20,20));
		
		
		TableView<ReporteBodega> table = new TableView<>();
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TableColumn<ReporteBodega, String> colBodega = new TableColumn<>("Bodega");
        colBodega.setCellValueFactory(new PropertyValueFactory<>("bodega"));
        TableColumn<ReporteBodega, Integer> colCantidadDisponible = new TableColumn<>("Cantidad Disponible");
        colCantidadDisponible.setCellValueFactory(new PropertyValueFactory<>("cantidadDisponibleTotal"));
        TableColumn<ReporteBodega, Integer> colCantidadReservada = new TableColumn<>("Cantidad Reservada");
        colCantidadReservada.setCellValueFactory(new PropertyValueFactory<>("cantidadReservadaTotal"));
        TableColumn<ReporteBodega, Integer> colTotal = new TableColumn<>("Cantidad Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        table.getColumns().clear();
        table.getColumns().addAll(colBodega, colCantidadDisponible, colCantidadReservada, colTotal);
        ObservableList<ReporteBodega> data = FXCollections.observableArrayList(bodega);
        table.setItems(data);
        table.setPadding(new Insets(10, 10, 10, 10));
        

       

       return new VBox(10, totalBox,table);
        
	}

}
