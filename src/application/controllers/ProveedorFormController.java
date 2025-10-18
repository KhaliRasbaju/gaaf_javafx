package application.controllers;

import java.util.List;

import application.models.request.CuentaRequest;
import application.models.request.ProveedorRequest;
import application.models.request.UbicacionRequest;
import application.models.response.Bodega;
import application.models.response.Common;
import application.models.response.Municipio;
import application.models.response.Proveedor;
import application.services.DepartamentoService;
import application.services.EntidadService;
import application.services.MunicipioService;
import application.services.ProveedorService;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;

public class ProveedorFormController {

	
	private static Long idEntidad;
	private static Long idDepartamento;
	private static Long idMunicipio;
	
	
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


    public static VBox getScene(String title, Proveedor proveedor) throws Exception {

        // --- TÍTULO ---
        Label lblTitulo = new Label(String.format("🏢 %s Proveedor", title));
        lblTitulo.getStyleClass().add("form-title");

        // --- CAMPOS ---
        TextField txtNit = new TextField();
        txtNit.setPromptText("NIT");
        txtNit.getStyleClass().add("form-field");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtNombre.getStyleClass().add("form-field");

        TextField txtCorreo = new TextField();
        txtCorreo.setPromptText("Correo");
        txtCorreo.getStyleClass().add("form-field");

        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");
        txtTelefono.getStyleClass().add("form-field");

        TextField txtNumeroCuenta = new TextField();
        txtNumeroCuenta.setPromptText("Número de cuenta");
        txtNumeroCuenta.getStyleClass().add("form-field");

        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("CORRIENTE", "AHORROS", "DEPOSITO_ELECTRONICO", "EMPRESARIAL");
        cbTipo.setPromptText("Tipo de cuenta");
        cbTipo.getStyleClass().add("form-field");

        ComboBox<String> cbEntidad = new ComboBox<>();
        var entidades = entidades();
        for (Common entidad : entidades) {
            cbEntidad.getItems().add(entidad.getNombre());
        }
        cbEntidad.setPromptText("Entidad bancaria");
        cbEntidad.getStyleClass().add("form-field");

        ComboBox<String> cbDepartamento = new ComboBox<>();
        var departamentos = departamento();
        for (Common departamento : departamentos) {
            cbDepartamento.getItems().add(departamento.getNombre());
        }
        cbDepartamento.setPromptText("Departamento");
        cbDepartamento.getStyleClass().add("form-field");

        ComboBox<String> cbMunicipio = new ComboBox<>();
        cbMunicipio.setPromptText("Municipio");
        cbMunicipio.setVisible(false);
        cbMunicipio.getStyleClass().add("form-field");

        TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Dirección");
        txtDireccion.getStyleClass().add("form-field");

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
                    showNotification(btnAccion.getScene(), "✅ Proveedor registrado correctamente", Color.GREEN);
                } else {
                    onActionActualizar(proveedor.getNit(), request);
                    showNotification(btnAccion.getScene(), "✏️ Proveedor actualizado correctamente", Color.GREEN);
                }

                txtNit.clear();
                txtNombre.clear();
                txtCorreo.clear();
                txtTelefono.clear();
                txtDireccion.clear();

            } catch (Exception ex2) {
                showNotification(btnAccion.getScene(), "❎ Error en el registro del proveedor", Color.RED);
            }
        });

        // --- GRIDPANE (2 COLUMNAS) ---
        GridPane grid = new GridPane();
        grid.setHgap(25);
        grid.setVgap(14);
        grid.setAlignment(Pos.CENTER);

        // Primera columna izquierda
        grid.add(new Label("NIT:"), 0, 0);
        grid.add(txtNit, 1, 0);

        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);

        grid.add(new Label("Correo:"), 0, 2);
        grid.add(txtCorreo, 1, 2);

        grid.add(new Label("Teléfono:"), 0, 3);
        grid.add(txtTelefono, 1, 3);

        // Segunda columna derecha (continuación)
        grid.add(new Label("Número de cuenta:"), 0, 4);
        grid.add(txtNumeroCuenta, 1, 4);

        grid.add(new Label("Tipo de cuenta:"), 0, 5);
        grid.add(cbTipo, 1, 5);

        grid.add(new Label("Entidad bancaria:"), 0, 6);
        grid.add(cbEntidad, 1, 6);

        grid.add(new Label("Departamento:"), 0, 7);
        grid.add(cbDepartamento, 1, 7);

        grid.add(new Label("Municipio:"), 0, 8);
        grid.add(cbMunicipio, 1, 8);

        grid.add(new Label("Dirección:"), 0, 9);
        grid.add(txtDireccion, 1, 9);

        // --- ENVOLTORIO PRINCIPAL ---
        VBox wrapper = new VBox(20);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.getStyleClass().add("form-container");
        wrapper.getChildren().addAll(lblTitulo, grid, btnAccion, lblMensaje);

        VBox layout = new VBox(wrapper);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f3f4f6;");
        layout.setPrefHeight(600);
        layout.setPrefWidth(900);

        return layout;
    }


}
