package application.models.response;

import java.util.List;

public class Proveedor {
	
    // ================================
    //        ATRIBUTOS PRINCIPALES
    // ================================
	private Long nit;
	
    private String nombre;
    
    private String telefono;
    
    private String correo;
    
    
    private List<Ubicacion> ubicacion;
    
    private List<Cuenta> cuenta;

    // ================================
    //        CONSTRUCTORES
    // ================================
    public Proveedor() {}
 
    
    public Proveedor(Long nit, String nombre, String telefono, String correo, 
			List<Ubicacion> ubicacion, List<Cuenta> cuenta) {
		this.nit = nit;
		this.nombre = nombre;
		this.telefono = telefono;
		this.correo = correo;
		this.ubicacion = ubicacion;
		this.cuenta = cuenta;
	}
    
    // ================================
    //        GETTERS & SETTERS
     // ================================
    public long getNit() {
        return nit;
    }

	public void setNit(long nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public List<Ubicacion> getUbicacion() {
		return ubicacion;
	}


	public void setUbicacion(List<Ubicacion> ubicacion) {
		this.ubicacion = ubicacion;
	}


	public List<Cuenta> getCuenta() {
		return cuenta;
	}


	public void setCuenta(List<Cuenta> cuenta) {
		this.cuenta = cuenta;
	}
}
