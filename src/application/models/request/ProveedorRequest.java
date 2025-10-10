package application.models.request;

public class ProveedorRequest {
	
    private Long nit;
    
    private String nombre;
    
    private String correo;
    
    private String telefono;
    
    private CuentaRequest cuenta;
    
    private UbicacionRequest ubicacion;

    public ProveedorRequest() {}

    public ProveedorRequest(Long nit, String nombre, String correo, String telefono, CuentaRequest cuenta, UbicacionRequest ubicacion) {
        this.nit = nit;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.cuenta = cuenta;
        this.ubicacion = ubicacion;
    }

    // Getters y Setters
    public Long getNit() {
        return nit;
    }

    public void setNit(Long nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public CuentaRequest getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaRequest cuenta) {
        this.cuenta = cuenta;
    }

    public UbicacionRequest getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionRequest ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return nombre + " (" + nit + ")";
    }
	

}
