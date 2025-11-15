package application.controllers;

import java.util.List;
import application.models.response.Common;
import application.models.response.ResponseCommon;
import application.services.EntidadService;
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

public class EntidadBancariaController {

    // ================================
    //        CONTENEDOR PRINCIPAL
    // ================================
    private final StackPane content;
    
    // ================================
    //        CONSTRUCTOR
    // ================================

    public EntidadBancariaController(StackPane content) {
        this.content = content;
    }

   


    // ================================
    //   ACCIÓN: CREAR NUEVA ENTIDAD
    // ================================
    private void onActionEntidadBancaria() {
        try {
        	EntidadBancariaFormController controller = new EntidadBancariaFormController(content);
            content.getChildren().setAll(controller.getScene("Registrar", null));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    // ================================
    //     ACCIÓN: EDITAR ENTIDAD
    // ================================
    private  void onActionEditar(Long id) throws Exception {
        try {
            EntidadService service = new EntidadService();
            Common entidad = service.obtenerEntidad(id);
            EntidadBancariaFormController controller = new EntidadBancariaFormController(content);
            content.getChildren().setAll(controller.getScene("Editar", entidad));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }


    // ================================
    //    ACCIÓN: ELIMINAR ENTIDAD
    // ================================
    private static ResponseCommon onActionEliminar(Long id, Button button) throws Exception {
        try {
            EntidadService service = new EntidadService();
            ResponseCommon respuesta = service.eliminarEntidad(id);
            return respuesta;
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
            throw new Exception(ex);
        }
    }
    

    // ================================
    //    OBTENER ENTIDAD POR ID
    // ================================
    private static Common entidad(Long id) throws Exception {
		try {
			
			EntidadService service = new EntidadService();
			return service.obtenerEntidad(id);
			
		} catch (Exception ex) {
			
			System.out.println("Error tipo: " + ex);
			throw new Exception("Error tipo: "+ ex);
		}
	}
    

    // ================================
    //    VISTA PRINCIPAL: TABLA
    // ================================
    public VBox getScene(List<Common> entidades) {
    	Label lblTitulo = new Label("Lista de Entidades Bancarias");
		lblTitulo.getStyleClass().add("form-title");
        Button btnAgregar = new Button("+");
        btnAgregar.getStyleClass().add("btn-agregar");

        TableView<Common> table = new TableView<>();

        // Columnas
        TableColumn<Common, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Common, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Common, Void> colAcciones = new TableColumn<>("Acciones");

        colAcciones.setCellFactory(param -> new TableCell<>() {
        	private final Button btnEditar = new Button("\u270E");  
        	private final Button btnEliminar = new Button("\u2716");
            private final HBox contenedor = new HBox(5, btnEditar, btnEliminar);

            {
            	
            	
                btnEditar.getStyleClass().add("btn-editar");
                btnEliminar.getStyleClass().add("btn-eliminar");
                contenedor.setAlignment(Pos.CENTER);

                btnEditar.setOnAction(e -> {
                	try {
                		Common entidad = getTableView().getItems().get(getIndex());
                		System.out.println(entidad.getId());
                		onActionEditar(entidad.getId());
					} catch (Exception ex) {
						System.out.println("Error tipo: "+ ex);
					}
                });

                btnEliminar.setOnAction(e -> {
                    Common entidad = getTableView().getItems().get(getIndex());
                    try {
                        var respuesta = onActionEliminar(entidad.getId(), btnEliminar);
                        if (respuesta.getStatus() != 200) {
                        	NotificationManager.showNotification(btnEliminar.getScene(),
                                    String.format("❌ %s", respuesta.getMessage()), Color.RED);
                        } else {
                        	NotificationManager.showNotification(btnEliminar.getScene(),
                                    String.format("✔ %s", respuesta.getMessage()), Color.GREEN);
                            getTableView().getItems().remove(entidad);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error tipo: " + ex);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : contenedor);
            }
        });

        table.getColumns().addAll(colId, colNombre, colAcciones);
        
        HBox contenedorBoton = new HBox(btnAgregar);
        contenedorBoton.setAlignment(Pos.CENTER_RIGHT);
        contenedorBoton.setPadding(new Insets(0, 0, 10, 0)); 

        ObservableList<Common> data = FXCollections.observableArrayList(entidades);
        table.setItems(data);

        btnAgregar.setOnAction(e -> onActionEntidadBancaria());

        VBox layout = new VBox(10, lblTitulo,contenedorBoton, table);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(10));
        return layout;
    }
}

