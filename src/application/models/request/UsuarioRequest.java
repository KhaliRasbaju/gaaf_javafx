package application.models.request;



public class UsuarioRequest {
	
	// ================================
	//        ATRIBUTOS PRINCIPALES
	// ================================
	private String nombre;
	

	private String usuario;
	
	
	private String correo;
	
	private String telefono;
	

	private String rol;

	

	// ================================
	//          CONSTRUCTORES
	// ================================
	public UsuarioRequest() {
	}



	public UsuarioRequest(String nombre, String usuario, String correo, String telefono, String rol) {
		this.nombre = nombre;
		this.usuario = usuario;
		this.correo = correo;
		this.telefono = telefono;
		this.rol = rol;
	}


	// ================================
	//        GETTERS Y SETTERS
	// ================================
	public String getNombre() {
		return nombre;
	}



	public String getUsuario() {
		return usuario;
	}



	public String getCorreo() {
		return correo;
	}



	public String getTelefono() {
		return telefono;
	}



	public String getRol() {
		return rol;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}



	public void setCorreo(String correo) {
		this.correo = correo;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	
}
