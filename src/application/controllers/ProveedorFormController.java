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
        Text titulo = new Text(String.format("🏢 %s Proveedor", title));
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField txtNit = new TextField();
        txtNit.setPromptText("NIT");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");

        TextField txtCorreo = new TextField();
        txtCorreo.setPromptText("Correo");

        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");
        
        TextField txtNumeroCuenta = new TextField();
        txtNumeroCuenta.setPromptText("Numero de cuenta");
        
        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("CORRIENTE", "AHORROS", "DEPOSITO_ELECTRONICO", "EMPRESARIAL");
        cbTipo.setPromptText("Selecciona un tipo de cuenta");
        
        ComboBox<String> cbEntidad = new ComboBox<>();
        var entidades = entidades();
        for (Common entidad : entidades) {
			cbEntidad.getItems().add(entidad.getNombre());
		}
        cbEntidad.setPromptText("Selecciona una entidad bancaria");
        
        ComboBox<String> cbDepartamento = new ComboBox<>();
        var departamentos = departamento();
        for (Common departamento : departamentos) {
			cbDepartamento.getItems().add(departamento.getNombre());
		}
        
        
        
        cbDepartamento.setPromptText("Selecciona un departamento");
        
        ComboBox<String> cbMunicipio = new ComboBox<>();
        cbMunicipio.setPromptText("Selecciona un municipio");
        cbMunicipio.setVisible(false);
        
        TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Direccion");

        
        cbDepartamento.setOnAction(event -> {
            String nombreDepto = cbDepartamento.getValue();

            // Obtener el ID del departamento seleccionado
            idDepartamento = departamentos.stream()
                    .filter(dep -> dep.getNombre().equals(nombreDepto))
                    .findFirst()
                    .map(Common::getId)
                    .orElse(null);
            
            System.out.println(idDepartamento);

            if (idDepartamento == null) {
                cbMunicipio.getItems().clear();
                cbMunicipio.setVisible(false);
                cbMunicipio.getParent().requestLayout(); // 🔧 Forzar redibujado
                return;
            }
            try {
                // Cargar municipios asociados
                var municipios = municipios(idDepartamento);
                cbMunicipio.getItems().clear();
                for (Municipio m : municipios) {
                    cbMunicipio.getItems().add(m.getNombre());
                    System.out.println(m.getNombre());
                }

                // Mostrar el ComboBox si hay municipios
                cbMunicipio.setVisible(true);
                cbMunicipio.managedProperty().bind(cbMunicipio.visibleProperty());
                cbMunicipio.getParent().requestLayout();

            } catch (Exception ex) {
                System.out.println("Error al cargar municipios: " + ex.getMessage());
                cbMunicipio.setVisible(false);
                cbMunicipio.getParent().requestLayout();
            }
        });
        


        if (proveedor != null) {
   
            txtNit.setText(String.valueOf(proveedor.getNit()));
            txtNit.setDisable(true);
            txtNombre.setText(proveedor.getNombre());
            txtCorreo.setText(proveedor.getCorreo());
            txtTelefono.setText(proveedor.getTelefono());
            proveedor.getCuenta().stream().forEach(c -> {
            	try {
					txtNumeroCuenta.setText(c.getNumero());
					cbTipo.setValue(c.getTipo());
					cbEntidad.setValue(c.getEntidad().getNombre());
				} catch (Exception ex) {
					
					System.out.println("Error tipo: " + ex);
				}
            });
            
            proveedor.getUbicacion().forEach(u -> {
                try {
                    txtDireccion.setText(u.getDireccion());

                    // Seleccionar departamento
                    cbDepartamento.setValue(u.getMunicipio().getDepartameto());

                    // 🔹 Buscar el ID del departamento
                    idDepartamento = departamento().stream()
                        .filter(dep -> dep.getNombre().equals(u.getMunicipio().getDepartameto()))
                        .findFirst()
                        .map(Common::getId)
                        .orElse(null);

                    if (idDepartamento != null) {
                        // 🔹 Cargar todos los municipios del departamento
                        var listaMunicipios = municipios(idDepartamento);
                        cbMunicipio.getItems().clear();
                        for (Municipio m : listaMunicipios) {
                            cbMunicipio.getItems().add(m.getNombre());
                        }

                        // Seleccionar el municipio correspondiente
                        cbMunicipio.setValue(u.getMunicipio().getNombre());
                        cbMunicipio.setVisible(true);
                        cbMunicipio.managedProperty().bind(cbMunicipio.visibleProperty());
                        cbMunicipio.getParent().requestLayout();
                    }

                } catch (Exception ex) {
                    System.out.println("Error tipo: " + ex);
                }
            });
        }
        
        

        Button btnAccion = new Button(proveedor == null ? "Registrar" : "Actualizar");
        btnAccion.getStyleClass().add("login-button");
        btnAccion.setPrefWidth(150);

        Label lblMensaje = new Label();
        lblMensaje.setTextFill(Color.RED);

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
            			 .filter(municipio -> municipio.getNombre().equals(cbMunicipio.getValue()))
            			 .findFirst()
            			 .map(Municipio::getId)
            			 .orElse(null);
            	 
            	 System.out.println("Municipio: " + cbMunicipio.getValue());
            	 System.out.println(idMunicipio);
            	 System.out.println("Ubicacion :" + txtDireccion.getText());
            	 
            	 CuentaRequest cuentaRequest = new CuentaRequest(Long.parseLong(txtNumeroCuenta.getText()), cbTipo.getValue(), idEntidad);
            	 UbicacionRequest ubicacionRequest = new UbicacionRequest(txtDireccion.getText(), idMunicipio);
            	 
            	 System.out.println(ubicacionRequest.getIdMunicipio());
            	 System.out.println(ubicacionRequest.getDireccion());
            	 
            	 
            	 
            	 
            	 
            	 
            	 ProveedorRequest request = new ProveedorRequest(
            			 Long.parseLong(txtNit.getText()),
            			 txtNombre.getText(),
            			 txtCorreo.getText(),
            			 txtTelefono.getText(),
            			 cuentaRequest,
            			 ubicacionRequest
            			 );
            	 
            	 System.out.println(request.toString());
            	 
            	 if (proveedor == null) {
            		 onActionRegistrar(request);
            		 showNotification(btnAccion.getScene(), "✅ Proveedor registrado correctamente", Color.GREEN);
            		 txtNit.clear();
            		 txtNombre.clear();
            		 txtCorreo.clear();
            		 txtTelefono.clear();
            		 txtDireccion.clear();
            	 } else {
            		 onActionActualizar(proveedor.getNit(), request);
            		 showNotification(btnAccion.getScene(), "✏️ Proveedor actualizado correctamente", Color.GREEN);
            		 txtNit.clear();
            		 txtNombre.clear();
            		 txtCorreo.clear();
            		 txtTelefono.clear();
            		 txtDireccion.clear();
            		 
            	 }
             } catch (Exception ex) {
            	 System.out.println("Error tipo 3: " + ex);
            	 showNotification(btnAccion.getScene(), "❎ Error en el registro del proveedor", Color.RED);
             }
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(titulo, txtNit, txtNombre, txtCorreo, txtTelefono, txtNumeroCuenta,cbTipo, cbEntidad, cbDepartamento, 
        		cbMunicipio, txtDireccion, 
        		btnAccion, lblMensaje);
        root.setStyle("-fx-background-color: #F8F9FA;");
        return root;
    }
}
