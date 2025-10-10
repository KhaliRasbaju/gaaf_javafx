<<<<<<< HEAD
package application.models.response;

import java.util.List;

public class Proveedor {
	
	private Long nit;
	
    private String nombre;
    
    private String telefono;
    
    private String correo;
    
    private String direccion;
    
    private List<Ubicacion> ubicacion;
    
    private List<Cuenta> cuenta;

    public Proveedor() {}
    // Constructor
    

    // Getters y Setters
    public long getNit() {
        return nit;
    }

    public Proveedor(Long nit, String nombre, String telefono, String correo, String direccion,
			List<Ubicacion> ubicacion, List<Cuenta> cuenta) {
		this.nit = nit;
		this.nombre = nombre;
		this.telefono = telefono;
		this.correo = correo;
		this.direccion = direccion;
		this.ubicacion = ubicacion;
		this.cuenta = cuenta;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
=======
package application.models.response;

import java.util.List;

public class Proveedor {
	
	private Long nit;
	
    private String nombre;
    
    private String telefono;
    
    private String correo;
    
    private String direccion;
    
    private List<Ubicacion> ubicacion;
    
    private List<Cuenta> cuenta;

    public Proveedor() {}
    // Constructor
    

    // Getters y Setters
    public long getNit() {
        return nit;
    }

    public Proveedor(Long nit, String nombre, String telefono, String correo, String direccion,
			List<Ubicacion> ubicacion, List<Cuenta> cuenta) {
		this.nit = nit;
		this.nombre = nombre;
		this.telefono = telefono;
		this.correo = correo;
		this.direccion = direccion;
		this.ubicacion = ubicacion;
		this.cuenta = cuenta;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
>>>>>>> b614632 (Se cambiaron los estilos y se movieron hacia el css)
