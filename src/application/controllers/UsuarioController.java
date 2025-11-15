package application.controllers;

import java.util.List;

import application.models.response.ResponseCommon;
import application.models.response.UsuarioResponse;
import application.services.UsuarioService;
import application.session.SessionManager;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class UsuarioController {

    // ================================
    //     CONTENEDOR PRINCIPAL
    // ================================
	private final StackPane content;

    // ================================
    //     CONSTRUCTOR
    // ================================
	public UsuarioController(StackPane content) {
		this.content = content;
	}

	// ============================================================
	//        ACCIÓN: ABRIR FORMULARIO DE CREAR USUARIO
	// ============================================================	
	private void onActionCrear() {
		try {
			
			RegistrarFormController controller  = new RegistrarFormController(content);
			content.getChildren().addAll(controller.getScene("Registrar", null));
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	
	// ============================================================
	//          ACCIÓN: ABRIR FORMULARIO DE EDITAR USUARIO
	// ============================================================	
	private void onActionEditar(String id) {
		try {
			
			UsuarioService service = new UsuarioService();
			var usuario = service.obtenerUsuario(id);
			
			RegistrarFormController controller  = new RegistrarFormController(content);
			content.getChildren().addAll(controller.getScene("Editar", usuario));
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
		}
	}
	
	// ============================================================
	//                  ACCIÓN: ELIMINAR USUARIO
	// ============================================================	
	private ResponseCommon onActionEliminar(String id) throws Exception {
		try {
			UsuarioService service = new UsuarioService();
			return service.eliminarUsuario(id);
		} catch (Exception ex) {
			System.out.println("Error tipo: " + ex);
			throw new Exception("Error tipo: "+ ex);
		}
	}
	
	// ============================================================
	//                OBTENER ID DEL USUARIO LOGUEADO
	// ============================================================	
	private static String getUserId() {
		return SessionManager.getInstance().getId();
	}
	
    // ================================
    //     VISTA PRINCIPAL
    // ================================
	public VBox getScene(List<UsuarioResponse> usuarios) {
		
		Button btnAgregar = new Button("+");
		btnAgregar.getStyleClass().add("btn-agregar");
		Label lblTitulo = new Label("Lista de Usuarios");
		lblTitulo.getStyleClass().add("form-title");

		TableView<UsuarioResponse> table = new TableView<>();
		table.setPrefHeight(400);
		table.setStyle("-fx-background-color: white; -fx-border-color: #E0E0E0; -fx-border-radius: 8px;");

		// --- Columnas ---
		TableColumn<UsuarioResponse, String> colUsuario = new TableColumn<>("Usuario");
		colUsuario.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getUsuario()));

		TableColumn<UsuarioResponse, String> colNombre = new TableColumn<>("Nombre");
		colNombre.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNombre()));

		TableColumn<UsuarioResponse, String> colCorreo = new TableColumn<>("Correo");
		colCorreo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCorreo()));

		TableColumn<UsuarioResponse, String> colTelefono = new TableColumn<>("Teléfono");
		colTelefono.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTelefono()));

		TableColumn<UsuarioResponse, String> colRol = new TableColumn<>("Rol");
		colRol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getRol()));

		TableColumn<UsuarioResponse, Boolean> colActivo = new TableColumn<>("Activo");
		colActivo.setCellFactory(col -> new TableCell<UsuarioResponse, Boolean>() {
			private final Label label = new Label();

			@Override
			protected void updateItem(Boolean activo, boolean empty) {
				super.updateItem(activo, empty);
				if (empty || getTableRow() == null || getTableRow().getItem() == null) {
					setGraphic(null);
				} else {
					UsuarioResponse u = getTableRow().getItem();
					label.setText(u.getActivo() ? "Sí" : "No");
					label.setTextFill(u.getActivo() ? Color.GREEN : Color.RED);
					setGraphic(label);
					setAlignment(Pos.CENTER);
				}
			}
		});

		TableColumn<UsuarioResponse, Void> colAcciones = new TableColumn<>("Acciones");
		colAcciones.setCellFactory(col -> new TableCell<UsuarioResponse, Void>() {
		    private final Button btnEditar = new Button("\u270E"); 
		    private final Button btnEliminar = new Button("\u2716"); 
		    private final HBox box = new HBox(5, btnEditar, btnEliminar);
		    private final Label lblSinAcciones = new Label("Sin acciones");

		    {
		        btnEditar.getStyleClass().add("btn-editar");
		        btnEliminar.getStyleClass().add("btn-eliminar");
		        box.setAlignment(Pos.CENTER);
		        lblSinAcciones.getStyleClass().add("label-sin-acciones");

		        btnEditar.setOnAction(e -> {
		            UsuarioResponse u = getTableView().getItems().get(getIndex());
		            onActionEditar(u.getId());
		        });

		        btnEliminar.setOnAction(e -> {
		            try {
		                UsuarioResponse u = getTableView().getItems().get(getIndex());
		                var response = onActionEliminar(u.getId());
		                getTableView().getItems().remove(u);
		                
		                if (response.getStatus() != 200) {
		                	
		                	NotificationManager.showNotification(btnEliminar.getScene(), String.format("❌ %s", response.getMessage()), Color.RED);
		                } else {		                	
		                	NotificationManager.showNotification(btnEliminar.getScene(), String.format("✔️ %s", response.getMessage()), Color.GREEN);
		                }
		                
		            } catch (Exception ex) {
		            	NotificationManager.showNotification(btnEliminar.getScene(), "❌ Error al eliminar el usuario", Color.RED);	                
		            }
		        });
		    }

		    
		    
		    @Override
		    protected void updateItem(Void item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty) {
		            setGraphic(null);
		            return;
		        }

		        UsuarioResponse usuario = getTableView().getItems().get(getIndex());

		           String userIdFila = usuario.getId();
		        if (userIdFila == null) {
		            System.out.println("⚠️ Usuario con ID null: " + usuario.getNombre());
		        }

		        String userIdSesion = getUserId();
		        System.out.println(getUserId());

		        if (userIdFila != null && userIdSesion != null && userIdFila.equals(userIdSesion)) {
		            setGraphic(lblSinAcciones);
		            setAlignment(Pos.CENTER);
		        } else {
		            setGraphic(box);
		        }

		    }
		});

		table.getColumns().addAll(colUsuario, colNombre, colCorreo, colTelefono, colRol, colActivo, colAcciones);

		btnAgregar.setOnAction(e -> onActionCrear());
		
		  HBox contenedorBoton = new HBox(btnAgregar);
	        contenedorBoton.setAlignment(Pos.CENTER_RIGHT);
	        contenedorBoton.setPadding(new Insets(0, 0, 10, 0)); 
		// --- Cargar datos ---
		ObservableList<UsuarioResponse> data = FXCollections.observableArrayList(usuarios);
		table.setItems(data);

		VBox root = new VBox(20, lblTitulo,contenedorBoton, table);
		root.setAlignment(Pos.TOP_CENTER);
		root.setPadding(new Insets(30));
		root.setStyle("-fx-background-color: #F8F9FA;");

		return root;
	}

}
