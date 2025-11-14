package application.controllers;

import java.util.List;

import application.models.response.Producto;
import application.models.response.ResponseCommon;
import application.services.ProductoService;
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

public class ProductoController {

    private final StackPane content;

    public ProductoController(StackPane content) {
        this.content = content;
    }

    // 🔹 Acción para abrir formulario de nuevo producto
    private void onActionProducto() {
        try {
        	ProductoFormController controller = new ProductoFormController(content);
        	
            content.getChildren().setAll(controller.getScene("Registrar", null));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }


    // 🔹 Acción para editar producto
    private void onActionEditar(Long id) {
        try {
            ProductoService service = new ProductoService();
            Producto producto = service.obtenerProducto(id);
            ProductoFormController controller = new ProductoFormController(content);     
            content.getChildren().setAll(controller.getScene("Editar", producto));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    // 🔹 Acción para eliminar producto
    private static ResponseCommon onActionEliminar(Long id, Button button) throws Exception {
        try {
            ProductoService service = new ProductoService();
            ResponseCommon respuesta = service.eliminarProducto(id);
            return respuesta;
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
            throw new Exception(ex);
        }
    }

    // ✅ Muestra la tabla con sus acciones
    public VBox getScene(List<Producto> productos) {
    	
    	Label lblTitulo = new Label("Lista de Productos");
		lblTitulo.getStyleClass().add("form-title");
    	
        Button btnAgregar = new Button("+");
        btnAgregar.getStyleClass().add("btn-agregar");

        TableView<Producto> table = new TableView<>();

        // Columnas
        TableColumn<Producto, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Producto, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Producto, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<Producto, Void> colAcciones = new TableColumn<>("Acciones");

        colAcciones.setCellFactory(param -> new TableCell<>() {
        	private final Button btnEditar = new Button("\u270E");  
        	private final Button btnEliminar = new Button("\u2716");
            private final HBox contenedor = new HBox(5, btnEditar, btnEliminar);

            {
                btnEditar.getStyleClass().add("btn-editar");
                btnEliminar.getStyleClass().add("btn-eliminar");
                contenedor.setAlignment(Pos.CENTER);

                btnEditar.setOnAction(e -> {
                    Producto producto = getTableView().getItems().get(getIndex());
                    onActionEditar(producto.getId());
                });

                btnEliminar.setOnAction(e -> {
                    Producto producto = getTableView().getItems().get(getIndex());
                    try {
                        var respuesta = onActionEliminar(producto.getId(), btnEliminar);
                        if (respuesta.getStatus() != 200) {
                        	NotificationManager.showNotification(btnEliminar.getScene(),
                                    String.format("❌ %s", respuesta.getMessage()), Color.RED);
                        } else {
                        	NotificationManager.showNotification(btnEliminar.getScene(),
                                    String.format("✔ %s", respuesta.getMessage()), Color.GREEN);
                            getTableView().getItems().remove(producto);
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
                setAlignment(Pos.CENTER);
            }
        });

        table.getColumns().addAll(colId, colNombre, colTipo, colDescripcion, colAcciones);

        HBox contenedorBoton = new HBox(btnAgregar);
        contenedorBoton.setAlignment(Pos.CENTER_RIGHT);
        contenedorBoton.setPadding(new Insets(0, 0, 10, 0)); 
        ObservableList<Producto> data = FXCollections.observableArrayList(productos);
        table.setItems(data);

        btnAgregar.setOnAction(e -> onActionProducto());

        VBox layout = new VBox(10, lblTitulo,contenedorBoton, table);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(10));
        return layout;
    }
}
