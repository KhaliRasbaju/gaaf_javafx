package application.controllers;

import java.util.List;

import application.models.response.Bodega;
import application.models.response.ResponseCommon;
import application.services.BodegaService;
import application.utils.NotificationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class BodegaController {

    private final StackPane content;


    
    // ✅ Constructor recibe el contenedor principal (StackPane)
    public BodegaController(StackPane content) {
        this.content = content;
    }
    
    


    // ✅ Método de acción no estático
    private void onActionBodega() {
        try {
            content.getChildren().setAll(BodegaFormController.getScene("Registrar", null));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }
    
    private void onActionEditar(Long id) {
		try {
			BodegaService service = new BodegaService();
			Bodega bodega = service.obtenerBodega(id);
			content.getChildren().setAll(BodegaFormController.getScene("Editar", bodega));
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}

    private static ResponseCommon onActionEliminar(Long id, Button button) throws Exception {
		try {
			BodegaService service = new BodegaService();
			ResponseCommon respuesta = service.eliminarBodega(id);
			return respuesta;
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
			throw new Exception(ex);
		}
	}
    
    // ✅ Método NO estático — pertenece al objeto
    public VBox getScene(List<Bodega> bodegas) {
    	Label lblTitulo = new Label("Lista de Bodegas");
		lblTitulo.getStyleClass().add("form-title");
		lblTitulo.setAlignment(Pos.CENTER);
    	// 🔹 Botón de agregar tipo “+” (usará estilos desde el CSS)
    	Button btnAgregar = new Button("+");
    	btnAgregar.getStyleClass().add("btn-agregar");
    	
        TableView<Bodega> table = new TableView<>();

        TableColumn<Bodega, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Bodega, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Bodega, String> colUbicacion = new TableColumn<>("Ubicación");
        colUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        
        TableColumn<Bodega, Void> colAcciones = new TableColumn<>("Acciones");

        colAcciones.setCellFactory(param -> new TableCell<>() {
        	private final Button btnEditar = new Button("\u270E");  
        	private final Button btnEliminar = new Button("\u2716");
            private final HBox contenedor = new HBox(5, btnEditar, btnEliminar);

            {
                // 🔹 Aplicar clases CSS
                btnEditar.getStyleClass().add("btn-editar");
                btnEliminar.getStyleClass().add("btn-eliminar");

                contenedor.setAlignment(Pos.CENTER);

                // 🔹 Acción de editar
                btnEditar.setOnAction(e -> {
                    Bodega bodega = getTableView().getItems().get(getIndex());
                    onActionEditar(bodega.getId());
                });

                // 🔹 Acción de eliminar
                btnEliminar.setOnAction(e -> {
                    Bodega bodega = getTableView().getItems().get(getIndex());
                    try {
                        var respuesta = onActionEliminar(bodega.getId(), btnEliminar);
                        if (respuesta.getStatus() != 200) {
                            NotificationManager.showNotification(btnEliminar.getScene(),
                            		String.format("❎ %s", respuesta.getMessage()), Color.RED);
                        } else {
                            NotificationManager.showNotification(btnEliminar.getScene(),
                                    String.format("✅ %s", respuesta.getMessage()), Color.GREEN);
                            getTableView().getItems().remove(bodega);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error tipo: " + ex);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(contenedor);
                }
            }
        });

        table.getColumns().addAll(colId, colNombre, colUbicacion, colAcciones);
        
        HBox contenedorBoton = new HBox(btnAgregar);
        contenedorBoton.setAlignment(Pos.CENTER_RIGHT);
        contenedorBoton.setPadding(new Insets(0, 0, 10, 0)); 

        ObservableList<Bodega> data = FXCollections.observableArrayList(bodegas);
        table.setItems(data);

        // ✅ Llamamos a onActionBodega() sin problema porque ya no es estático
        btnAgregar.setOnAction(e -> onActionBodega());

        VBox layout = new VBox(10, lblTitulo,contenedorBoton, table);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(10));
        return layout;
    }
}
