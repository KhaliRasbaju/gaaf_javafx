package application.models.response;

public class Usuario {
	
    // ================================
    //        ATRIBUTOS PRINCIPALES
    // ================================
    private String usuario;
    
    private String correo;
    
    private String nombre;
    
    private String telefono;

    private String rol;

    // ================================
    //        CONSTRUCTORES
    // ================================
    public Usuario() {}



    public Usuario( String usuario, String correo, String nombre, String telefono, Boolean activo, String rol) {
		
		this.usuario = usuario;
		this.correo = correo;
		this.nombre = nombre;
		this.telefono = telefono;
		this.rol = rol;
	}

    
    
  
    // ================================
	//        GETTERS & SETTERS
	// ================================

	public String getUsuario() {
		return usuario;
	}



	public String getCorreo() {
		return correo;
	}



	public String getNombre() {
		return nombre;
	}



	public String getTelfono() {
		return telefono;
	}


	public String getRol() {
		return rol;
	}



	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}



	public void setCorreo(String correo) {
		this.correo = correo;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public void setTelefono(String telfono) {
		this.telefono = telfono;
	}



	public void setRol(String rol) {
		this.rol = rol;
	}



	@Override
    public String toString() {
        return usuario + " (" + rol + ")";
    }
}
