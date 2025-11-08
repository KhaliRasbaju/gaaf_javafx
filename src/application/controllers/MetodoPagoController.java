package application.controllers;

import java.util.List;

import application.models.response.Common;
import application.models.response.ResponseCommon;
import application.services.MetodoPagoService;
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

public class MetodoPagoController {

    private final StackPane content;

    public MetodoPagoController(StackPane content) {
        this.content = content;
    }


    // 🔹 Acción para abrir formulario (nuevo método de pago)
    private void onActionMetodoPago() {
        try {
            content.getChildren().setAll(MetodoPagoFormController.getScene("Registrar", null));
        } catch (Exception ex) {
            System.out.println("Error al abrir formulario: " + ex);
        }
    }

    // 🔹 Acción para editar método de pago
    private void onActionEditar(Long id) {
        try {
            MetodoPagoService service = new MetodoPagoService();
            Common metodoPago = service.obtenerMetodo(id);
            MetodoPagoFormController controller = new MetodoPagoFormController(content);
            content.getChildren().setAll(controller.getScene("Editar", metodoPago));
        } catch (Exception ex) {
            System.out.println("Error al editar método de pago: " + ex);
        }
    }

    // 🔹 Acción para eliminar método de pago
    private static ResponseCommon onActionEliminar(Long id, Button button) throws Exception {
        try {
            MetodoPagoService service = new MetodoPagoService();
            ResponseCommon respuesta = service.eliminarMetodo(id);
            return respuesta;
        } catch (Exception ex) {
            System.out.println("Error al eliminar método de pago: " + ex);
            throw new Exception(ex);
        }
    }

    // ✅ Muestra la tabla con los métodos de pago
    public VBox getScene(List<Common> metodosPago) {
    	
    	Label lblTitulo = new Label("Lista de Metodos de Pagos");
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
                    Common metodoPago = getTableView().getItems().get(getIndex());
                    onActionEditar(metodoPago.getId());
                });

                btnEliminar.setOnAction(e -> {
                    Common metodoPago = getTableView().getItems().get(getIndex());
                    try {
                        var respuesta = onActionEliminar(metodoPago.getId(), btnEliminar);
                        if (respuesta.getStatus() != 200) {
                        	NotificationManager.showNotification(btnEliminar.getScene(),
                                    String.format("❎ %s", respuesta.getMessage()), Color.RED);
                        } else {
                        	NotificationManager.showNotification(btnEliminar.getScene(),
                                    String.format("✅ %s", respuesta.getMessage()), Color.GREEN);
                            getTableView().getItems().remove(metodoPago);
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
        

        ObservableList<Common> data = FXCollections.observableArrayList(metodosPago);
        table.setItems(data);

        btnAgregar.setOnAction(e -> onActionMetodoPago());

        VBox layout = new VBox(10, lblTitulo,contenedorBoton, table);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(10));
        return layout;
    }
}
