package application.controllers;

import java.util.List;
import application.models.response.Pedido;
import application.models.response.ResponseCommon;
import application.services.PedidoService;
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

public class PedidoController {

    private final StackPane content;

    public PedidoController(StackPane content) {
        this.content = content;
    }
    
    private void onActionAgregar() {
        try {
        	PedidoFormController controller = new PedidoFormController();
            content.getChildren().setAll(controller.getScene("Registrar", null));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    private void onActionEditar(Long id) {
        try {
            PedidoService service = new PedidoService();
            Pedido pedido = service.obtenerPedido(id);
            PedidoFormController controller = new PedidoFormController();
            content.getChildren().setAll(controller.getScene("Editar", pedido));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }
    
    private ResponseCommon onActionRecibir(Long id) throws Exception {
		try {
			PedidoService service = new PedidoService();
			System.out.println(id);
			return service.recibirPedido(id);
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
			throw new Exception("Error tipo: "+ ex);
		}
	}

    private ResponseCommon onActionEliminar(Long id) throws Exception {
        PedidoService service = new PedidoService();
        return service.eliminarPedido(id);
    }

   

    // 🔹 Escena principal (tabla)
    @SuppressWarnings("unchecked")
    public VBox getScene(List<Pedido> pedidos) {

    	Label lblTitulo = new Label("Lista de Pedidos");
		lblTitulo.getStyleClass().add("form-title");
        Button btnAgregar = new Button("+");
        btnAgregar.getStyleClass().add("btn-agregar");
        btnAgregar.setAlignment(Pos.CENTER);

        TableView<Pedido> table = new TableView<>();

        TableColumn<Pedido, Long> colId = new TableColumn<>("ID Pedido");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Pedido, Long> colNitProveedor = new TableColumn<>("NIT Proveedor");
        colNitProveedor.setCellValueFactory(new PropertyValueFactory<>("nitProveedor"));

        TableColumn<Pedido, Double> colValor = new TableColumn<>("Valor Total");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        TableColumn<Pedido, String> colFechaPedido = new TableColumn<>("Fecha Pedido");
        colFechaPedido.setCellValueFactory(new PropertyValueFactory<>("fechaPedido"));

        TableColumn<Pedido, String> colFechaEntrega = new TableColumn<>("Fecha Entrega");
        colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));
        
        colFechaEntrega.setCellFactory(col -> new TableCell<>() {
            private final Button btnRecibir = new Button("Recibir");
            private final Label lblFecha = new Label();

            {
                btnRecibir.getStyleClass().add("btn-recibir");
                btnRecibir.setOnAction(e -> {
                    Pedido pedido = getTableView().getItems().get(getIndex());
                   
                    try {
                        var resp = onActionRecibir(pedido.getId());
                        if (resp.getStatus() == 200) {
                            // Actualizamos la fechaEntrega en la tabla
                            pedido.setFechaEntrega("Actualizando ...");
                            getTableView().refresh();
                            NotificationManager.showNotification(btnRecibir.getScene(), resp.getMessage(), Color.GREEN);
                        } else {
                        	NotificationManager.showNotification(btnRecibir.getScene(), "❌ " + resp.getMessage(), Color.RED);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error tipo: " + ex);
                        NotificationManager.showNotification(btnRecibir.getScene(), "❌ Error al recibir pedido", Color.RED);
                    }
                });
            }

            @Override
            protected void updateItem(String fechaEntrega, boolean empty) {
                super.updateItem(fechaEntrega, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    if (fechaEntrega == null || fechaEntrega.isEmpty()) {
                        setGraphic(btnRecibir);
                        setAlignment(Pos.CENTER);
                    } else {
                        lblFecha.setText(fechaEntrega);
                        setGraphic(lblFecha);
                        setAlignment(Pos.CENTER);
                    }
                }
            }
        });

        TableColumn<Pedido, Boolean> colRecibido = new TableColumn<>("Recibido");
        colRecibido.setCellValueFactory(new PropertyValueFactory<>("recibido"));
        colRecibido.setCellFactory(col -> new TableCell<>() {
            private final Label label = new Label();

            @Override
            protected void updateItem(Boolean recibido, boolean empty) {
                super.updateItem(recibido, empty);
                if (empty || recibido == null) {
                    setGraphic(null);
                } else {
                    label.setText(recibido ? "✅" : "🕛");
                    setGraphic(label);
                    setAlignment(Pos.CENTER);
                }
            }
        });

     
        TableColumn<Pedido, Void> colAcciones = new TableColumn<>("Acciones");
        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("\u270E");  
        	private final Button btnEliminar = new Button("\u2716");
            private final HBox contenedor = new HBox(5, btnEditar, btnEliminar);

            {
                btnEditar.getStyleClass().add("btn-editar");
                btnEliminar.getStyleClass().add("btn-eliminar");
                btnEditar.setAlignment(Pos.CENTER);
                btnEliminar.setAlignment(Pos.CENTER);
                contenedor.setAlignment(Pos.CENTER);

                btnEditar.setOnAction(e -> {
                    Pedido pedido = getTableView().getItems().get(getIndex());
                    onActionEditar(pedido.getId());
                });

                btnEliminar.setOnAction(e -> {
                    Pedido pedido = getTableView().getItems().get(getIndex());
                    try {
                        var resp = onActionEliminar(pedido.getId());
                        if (resp.getStatus() == 200) {
                        	NotificationManager.showNotification(btnEliminar.getScene(), "✅ Pedido eliminado", Color.GREEN);
                            getTableView().getItems().remove(pedido);
                        } else {
                        	NotificationManager.showNotification(btnEliminar.getScene(), "❌ " + resp.getMessage(), Color.RED);
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
                    Pedido pedido = getTableView().getItems().get(getIndex());
                    if (pedido.getRecibido()) { 
                        Label lblSinAccion = new Label("Sin acciones");
                        lblSinAccion.getStyleClass().add("label-sin-acciones");
                        setGraphic(lblSinAccion);
                        setAlignment(Pos.CENTER);
                    } else {
                        setGraphic(contenedor);
                        setAlignment(Pos.CENTER);
                    }
                }
            }
        });


        table.getColumns().addAll(colId, colNitProveedor, colValor, colFechaPedido, colFechaEntrega, colRecibido, colAcciones);

        ObservableList<Pedido> data = FXCollections.observableArrayList(pedidos);
        table.setItems(data);
        
        HBox contenedorBoton = new HBox(btnAgregar);
        contenedorBoton.setAlignment(Pos.CENTER_RIGHT);
        contenedorBoton.setPadding(new Insets(0, 0, 10, 0)); 

        btnAgregar.setOnAction(e -> onActionAgregar());

        VBox layout = new VBox(10,lblTitulo, contenedorBoton, table);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(10));

        return layout;
    }

    
}
