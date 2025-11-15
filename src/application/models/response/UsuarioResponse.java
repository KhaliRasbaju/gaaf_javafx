package application.models.response;

public class UsuarioResponse {

    // ================================
    //        ATRIBUTOS PRINCIPALES
    // ================================
	private String id;
	    
    private String usuario;
    
    private String correo;
    
    private String nombre;
    
    private String telefono;
    
    private Boolean activo;

    private String rol;

    // ================================
    //        CONSTRUCTORES
    // ================================
	public UsuarioResponse() {
	}

	public UsuarioResponse(String id, String usuario, String correo, String nombre, String telefono, Boolean activo,
			String rol) {
		this.id = id;
		this.usuario = usuario;
		this.correo = correo;
		this.nombre = nombre;
		this.telefono = telefono;
		this.activo = activo;
		this.rol = rol;
	}

	// ================================
	//        GETTERS & SETTERS
	// ================================
	public String getId() {
		return id;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getCorreo() {
		return correo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public Boolean getActivo() {
		return activo;
	}

	public String getRol() {
		return rol;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
    
    
    
	
}
