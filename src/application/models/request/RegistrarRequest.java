package application.models.request;

public class RegistrarRequest {
	
	// ================================
	//        ATRIBUTOS PRINCIPALES
	// ================================
    private String usuario;
    
    private String correo;
    
    private String nombre;
    
    private String telefono;
    
    private String contraseña;
    
    private String rol;

    // ================================
    //          CONSTRUCTORES
    // ================================
    public RegistrarRequest() {}

    public RegistrarRequest(String usuario, String correo, String nombre, String telefono, String contraseña, String rol) {
        this.usuario = usuario;
        this.correo = correo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // ================================
    //        GETTERS Y SETTERS
    // ================================
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
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

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}
