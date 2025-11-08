package application.controllers;

import java.util.List;

import application.models.request.CuentaRequest;
import application.models.request.ProveedorRequest;
import application.models.request.UbicacionRequest;
import application.models.response.Common;
import application.models.response.Municipio;
import application.models.response.Proveedor;
import application.services.DepartamentoService;
import application.services.EntidadService;
import application.services.MunicipioService;
import application.services.ProveedorService;
import application.utils.NotificationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ProveedorFormController {

	
	private static Long idEntidad;
	private static Long idDepartamento;
	private static Long idMunicipio;
	
	

    private static void onActionRegistrar(ProveedorRequest request) {
        try {
            ProveedorService service = new ProveedorService();
            System.out.println(service.crearProveedor(request));
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }

    private static void onActionActualizar(Long nit, ProveedorRequest request) {
        try {
            ProveedorService service = new ProveedorService();
            service.editarProveedor(nit, request);
        } catch (Exception ex) {
            System.out.println("Error tipo: " + ex);
        }
    }
    
    private static List<Common> entidades() throws Exception {
        try {
            EntidadService service = new EntidadService();
            System.out.println(service.obtenerEntidades());
            return service.obtenerEntidades();
        } catch (Exception ex) 
        
        {
        	System.out.println("Error tipo: " + ex);
        	throw new Exception("Error tipo: " + ex);   
        }
    }
    
    private static List<Common> departamento() throws Exception {
        try {
            DepartamentoService service = new DepartamentoService();
            System.out.println(service.obtenerDepartamentos());
            return service.obtenerDepartamentos();
        } catch (Exception ex) 
        
        {
        	System.out.println("Error tipo 1: " + ex);
        	throw new Exception("Error tipo 1 : " + ex);   
        }
    }
    
    private static List<Municipio> municipios(Long id) throws Exception {
        try {
            MunicipioService service = new MunicipioService();
            System.out.println(service.obtenerMunicipiosPorDepartamento(id));
            return service.obtenerMunicipiosPorDepartamento(id);
        } catch (Exception ex) 
        
        {
        	System.out.println("Error tipo 2: " + ex);
        	throw new Exception("Error tipo 2: " + ex);   
        }
    }
    
    private static Municipio  municipio(Long id) throws Exception {
        try {
            MunicipioService service = new MunicipioService();
            System.out.println(service.obtenerMunicipio(id));
            return service.obtenerMunicipio(id);
        } catch (Exception ex) 
        
        {
        	System.out.println("Error tipo 2: " + ex);
        	throw new Exception("Error tipo 2: " + ex);   
        }
    }


    public static ScrollPane getScene(String title, Proveedor proveedor) throws Exception {
    	
    	 // --- TÍTULO ---
        Label titulo = new Label(String.format("🏢 %s Proveedor", title));
        titulo.getStyleClass().add("form-title");


        GridPane gridInfo = new GridPane();
        gridInfo.getStyleClass().add("form-container");
        gridInfo.setHgap(25);
        gridInfo.setVgap(15);
        gridInfo.setPadding(new Insets(25));
        gridInfo.setAlignment(Pos.CENTER);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        gridInfo.getColumnConstraints().addAll(col1, col2);
    	
       
        // --- CAMPOS ---
        TextField txtNit = new TextField();
        txtNit.setPromptText("NIT");
        txtNit.getStyleClass().add("form-field");
        Label lblNit = new Label("NIT");
        lblNit.getStyleClass().add("form-label");
        gridInfo.add(lblNit, 0, 0);
        gridInfo.add(txtNit, 0, 1);


        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtNombre.getStyleClass().add("form-field");
        Label lblNombre = new Label("Nombre");
        lblNombre.getStyleClass().add("form-label");
        gridInfo.add(lblNombre, 1, 0);
        gridInfo.add(txtNombre, 1, 1);

        TextField txtCorreo = new TextField();
        txtCorreo.setPromptText("Correo");
        txtCorreo.getStyleClass().add("form-field");
        Label lblCorreo = new Label("Correo");
        lblCorreo.getStyleClass().add("form-label");
        gridInfo.add(lblCorreo, 0, 2);
        gridInfo.add(txtCorreo, 0, 3);

        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");
        txtTelefono.getStyleClass().add("form-field");
        Label lblTelefono = new Label("Teléfono");
        lblTelefono.getStyleClass().add("form-label");
        gridInfo.add(lblTelefono, 1, 2);
        gridInfo.add(txtTelefono, 1, 3);

       
        
        GridPane gridCuenta = new GridPane();
        gridCuenta.getStyleClass().add("form-container");
        gridCuenta.setHgap(25);
        gridCuenta.setVgap(15);
        gridCuenta.setPadding(new Insets(25));
        gridCuenta.setAlignment(Pos.CENTER);
        
        ColumnConstraints colCb1 = new ColumnConstraints();
        colCb1.setPercentWidth(50);
        ColumnConstraints colCb2 = new ColumnConstraints();
        colCb2.setPercentWidth(50);
        gridCuenta.getColumnConstraints().addAll(colCb1, colCb2);
        
        TextField txtNumeroCuenta = new TextField();
        txtNumeroCuenta.setPromptText("Número de cuenta");
        txtNumeroCuenta.getStyleClass().add("form-field");
        Label lblNumeroCuenta = new Label("Número de cuenta");
        lblNumeroCuenta.getStyleClass().add("form-label");
        gridCuenta.add(lblNumeroCuenta, 1, 0);
        gridCuenta.add(txtNumeroCuenta, 1, 1);

        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("CORRIENTE", "AHORROS", "DEPOSITO_ELECTRONICO", "EMPRESARIAL");
        cbTipo.setPromptText("Tipo de cuenta");
        cbTipo.getStyleClass().add("form-field");
        Label lblTipoCuenta = new Label("Tipo de cuenta");
        lblTipoCuenta.getStyleClass().add("form-label");
        gridCuenta.add(lblTipoCuenta, 0, 2);
        gridCuenta.add(cbTipo, 0, 3);

        ComboBox<String> cbEntidad = new ComboBox<>();
        var entidades = entidades();
        for (Common entidad : entidades) {
            cbEntidad.getItems().add(entidad.getNombre());
        }
        cbEntidad.setPromptText("Entidad bancaria");
        cbEntidad.getStyleClass().add("form-field");
        Label lblEntidad = new Label("Entidad bancaria");
        lblEntidad.getStyleClass().add("form-label");
        gridCuenta.add(lblEntidad, 0, 0);
        gridCuenta.add(cbEntidad, 0, 1);
        
        GridPane gridUbicacion = new GridPane();
        gridUbicacion.getStyleClass().add("form-container");
        gridUbicacion.setHgap(25);
        gridUbicacion.setVgap(15);
        gridUbicacion.setPadding(new Insets(25));
        gridUbicacion.setAlignment(Pos.CENTER);

        ColumnConstraints colUb1 = new ColumnConstraints();
        colUb1.setPercentWidth(50);
        ColumnConstraints colUb2 = new ColumnConstraints();
        colUb2.setPercentWidth(50);
        gridUbicacion.getColumnConstraints().addAll(colUb1, colUb2);
        

        ComboBox<String> cbDepartamento = new ComboBox<>();
        var departamentos = departamento();
        for (Common departamento : departamentos) {
            cbDepartamento.getItems().add(departamento.getNombre());
        }
        cbDepartamento.setPromptText("Departamento");
        cbDepartamento.getStyleClass().add("form-field");
        Label lblDepartamento = new Label("Departamento");
        lblDepartamento.getStyleClass().add("form-label");
        gridUbicacion.add(lblDepartamento, 1, 0);
        gridUbicacion.add(cbDepartamento, 1, 1);

        ComboBox<String> cbMunicipio = new ComboBox<>();
        cbMunicipio.setPromptText("Municipio");
        cbMunicipio.setVisible(false);
        cbMunicipio.getStyleClass().add("form-field");
        Label lblMunicipio = new Label("Ciudad");
        lblMunicipio.getStyleClass().add("form-label");
        gridUbicacion.add(lblMunicipio, 0, 0);
        gridUbicacion.add(cbMunicipio, 0, 1);



        TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Dirección");
        txtDireccion.getStyleClass().add("form-field");
        Label lblDireccion = new Label("Dirección");
        lblDireccion.getStyleClass().add("form-label");
        gridUbicacion.add(lblDireccion, 0, 2);
        gridUbicacion.add(txtDireccion, 0, 3, 2, 1);

        // --- EVENTO DEPTO → MUNICIPIOS ---
        cbDepartamento.setOnAction(event -> {
            String nombreDepto = cbDepartamento.getValue();
            idDepartamento = departamentos.stream()
                    .filter(dep -> dep.getNombre().equals(nombreDepto))
                    .findFirst()
                    .map(Common::getId)
                    .orElse(null);

            if (idDepartamento == null) {
                cbMunicipio.getItems().clear();
                cbMunicipio.setVisible(false);
                cbMunicipio.getParent().requestLayout();
                return;
            }

            try {
                var listaMunicipios = municipios(idDepartamento);
                cbMunicipio.getItems().clear();
                for (Municipio m : listaMunicipios) {
                    cbMunicipio.getItems().add(m.getNombre());
                }
                cbMunicipio.setVisible(true);
                cbMunicipio.managedProperty().bind(cbMunicipio.visibleProperty());
                cbMunicipio.getParent().requestLayout();
            } catch (Exception ex) {
                cbMunicipio.setVisible(false);
                cbMunicipio.getParent().requestLayout();
            }
        });

        // --- DATOS EXISTENTES (editar) ---
        if (proveedor != null) {
            txtNit.setText(String.valueOf(proveedor.getNit()));
            txtNit.setDisable(true);
            txtNombre.setText(proveedor.getNombre());
            txtCorreo.setText(proveedor.getCorreo());
            txtTelefono.setText(proveedor.getTelefono());

            proveedor.getCuenta().forEach(c -> {
                try {
                    txtNumeroCuenta.setText(c.getNumero());
                    cbTipo.setValue(c.getTipo());
                    cbEntidad.setValue(c.getEntidad().getNombre());
                } catch (Exception ignored) {}
            });

            proveedor.getUbicacion().forEach(u -> {
                try {
                    txtDireccion.setText(u.getDireccion());
                    cbDepartamento.setValue(u.getMunicipio().getDepartameto());

                    idDepartamento = departamento().stream()
                            .filter(dep -> dep.getNombre().equals(u.getMunicipio().getDepartameto()))
                            .findFirst()
                            .map(Common::getId)
                            .orElse(null);

                    if (idDepartamento != null) {
                        var listaMunicipios = municipios(idDepartamento);
                        cbMunicipio.getItems().clear();
                        for (Municipio m : listaMunicipios) {
                            cbMunicipio.getItems().add(m.getNombre());
                        }
                        cbMunicipio.setValue(u.getMunicipio().getNombre());
                        cbMunicipio.setVisible(true);
                        cbMunicipio.managedProperty().bind(cbMunicipio.visibleProperty());
                    }
                } catch (Exception ignored) {}
            });
        }

        // --- BOTÓN ---
        Button btnAccion = new Button(proveedor == null ? "Registrar" : "Actualizar");
        btnAccion.getStyleClass().add("form-button");
        HBox contBoton = new HBox(btnAccion);
        contBoton.setAlignment(Pos.CENTER);
        contBoton.setPadding(new Insets(10, 0, 20, 0));

        Label lblMensaje = new Label();
        lblMensaje.setTextFill(Color.RED);

        // --- EVENTO BOTÓN ---
        btnAccion.setOnAction(e -> {
            if (txtNit.getText().isEmpty() || txtNombre.getText().isEmpty()
                    || txtCorreo.getText().isEmpty() || txtTelefono.getText().isEmpty()) {
                lblMensaje.setText("⚠️ Completa todos los campos.");
                lblMensaje.setTextFill(Color.RED);
                return;
            }

            idEntidad = entidades.stream()
                    .filter(entidad -> entidad.getNombre().equals(cbEntidad.getValue()))
                    .findFirst()
                    .map(Common::getId)
                    .orElse(null);

            try {
                idMunicipio = municipios(idDepartamento).stream()
                        .filter(m -> m.getNombre().equals(cbMunicipio.getValue()))
                        .findFirst()
                        .map(Municipio::getId)
                        .orElse(null);

                CuentaRequest cuentaRequest = new CuentaRequest(
                        Long.parseLong(txtNumeroCuenta.getText()), cbTipo.getValue(), idEntidad);

                UbicacionRequest ubicacionRequest = new UbicacionRequest(
                        txtDireccion.getText(), idMunicipio);

                ProveedorRequest request = new ProveedorRequest(
                        Long.parseLong(txtNit.getText()),
                        txtNombre.getText(),
                        txtCorreo.getText(),
                        txtTelefono.getText(),
                        cuentaRequest,
                        ubicacionRequest
                );

                if (proveedor == null) {
                    onActionRegistrar(request);
                    NotificationManager.showNotification(btnAccion.getScene(), "✅ Proveedor registrado correctamente", Color.GREEN);
                } else {
                    onActionActualizar(proveedor.getNit(), request);
                    NotificationManager.showNotification(btnAccion.getScene(), "✏️ Proveedor actualizado correctamente", Color.GREEN);
                }

                txtNit.clear();
                txtNombre.clear();
                txtCorreo.clear();
                txtTelefono.clear();
                txtDireccion.clear();

            } catch (Exception ex2) {
            	NotificationManager.showNotification(btnAccion.getScene(), "❎ Error en el registro del proveedor", Color.RED);
            }
        });

       

        // --- ENVOLTORIO PRINCIPAL ---
        VBox root = new VBox(20, titulo, gridInfo, gridUbicacion, gridCuenta, contBoton, lblMensaje);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F8F9FA;");

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background-color: transparent;");

        return scroll;
    }


}
