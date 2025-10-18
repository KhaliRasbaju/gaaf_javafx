package application.controllers;

import java.util.List;

import application.models.response.Proveedor;
import application.models.response.ResponseCommon;
import application.services.ProveedorService;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.stage.Popup;
import javafx.util.Duration;

public class ProveedorController {

    private final StackPane content;

    public ProveedorController(StackPane content) {
        this.content = content;
    }

    private static String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private static void showNotification(Scene scene, String text, Color color) {
        Label notification = new Label(text);
        notification.getStyleClass().add("notification-toast");
        notification.setStyle("-fx-background-color: " + toHex(color) + ";"
                + "-fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 8px;");

        Popup popup = new Popup();
        popup.getContent().add(notification);
        popup.setAutoFix(true);

        double x = scene.getWindow().getX() + scene.getWidth() / 2 - 100;
        double y = scene.getWindow().getY() + scene.getHeight() - 100;
        popup.show(scene.getWindow(), x, y);

        FadeTransition fade = new FadeTransition(Duration.seconds(2.5), notification);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(ev -> popup.hide());
        fade.play();
    }

    private void onActionProveedor() {
        try {
            content.getChildren().setAll(ProveedorFormController.getScene("Registrar", null));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    private void onActionEditar(Long nit) {
        try {
            ProveedorService service = new ProveedorService();
            Proveedor proveedor = service.obtenerProveedor(nit);
            content.getChildren().setAll(ProveedorFormController.getScene("Editar", proveedor));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    private static ResponseCommon onActionEliminar(Long nit, Button button) throws Exception {
        try {
            ProveedorService service = new ProveedorService();
            ResponseCommon respuesta = service.eliminarProveedor(nit);
            return respuesta;
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
            throw new Exception(ex);
        }
    }

    public VBox getScene(List<Proveedor> proveedores) {
    	
    	Label lblTitulo = new Label("Lista de Proveedores");
		lblTitulo.getStyleClass().add("form-title");
        Button btnAgregar = new Button("+");
        btnAgregar.getStyleClass().add("btn-agregar");

        TableView<Proveedor> table = new TableView<>();

        TableColumn<Proveedor, Long> colNit = new TableColumn<>("NIT");
        colNit.setCellValueFactory(new PropertyValueFactory<>("nit"));

        TableColumn<Proveedor, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Proveedor, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        TableColumn<Proveedor, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<Proveedor, Void> colAcciones = new TableColumn<>("Acciones");

        colAcciones.setCellFactory(param -> new TableCell<>() {
        	private final Button btnEditar = new Button("\u270E");  
        	private final Button btnEliminar = new Button("\u2716");
            private final HBox contenedor = new HBox(5, btnEditar, btnEliminar);

            {
                btnEditar.getStyleClass().add("btn-editar");
                btnEliminar.getStyleClass().add("btn-eliminar");
                contenedor.setAlignment(Pos.CENTER);

                btnEditar.setOnAction(e -> {
                    Proveedor proveedor = getTableView().getItems().get(getIndex());
                    onActionEditar(proveedor.getNit());
                });

                btnEliminar.setOnAction(e -> {
                    Proveedor proveedor = getTableView().getItems().get(getIndex());
                    try {
                        var respuesta = onActionEliminar(proveedor.getNit(), btnEliminar);
                        if (respuesta.getStatus() != 200) {
                            showNotification(btnEliminar.getScene(),
                                    String.format("❎ %s", respuesta.getMessage()), Color.RED);
                        } else {
                            showNotification(btnEliminar.getScene(),
                                    String.format("✅ %s", respuesta.getMessage()), Color.GREEN);
                            getTableView().getItems().remove(proveedor);
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

        
        table.getColumns().addAll(colNit, colNombre, colCorreo, colTelefono, colAcciones);
        
        HBox contenedorBoton = new HBox(btnAgregar);
        contenedorBoton.setAlignment(Pos.CENTER_RIGHT);
        contenedorBoton.setPadding(new Insets(0, 0, 10, 0)); 

        ObservableList<Proveedor> data = FXCollections.observableArrayList(proveedores);
        table.setItems(data);

        btnAgregar.setOnAction(e -> onActionProveedor());

        VBox layout = new VBox(10,lblTitulo ,contenedorBoton, table);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(10));
        return layout;
    }
}
