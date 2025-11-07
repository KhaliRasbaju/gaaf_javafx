package application.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sesion {

	private String usuario;
	
	private String token;
	
	private String rol;
	
	private String id;
	
	public Sesion() {}

	public Sesion(String usuario, String token, String rol, String id) {
		this.usuario = usuario;
		this.token = token;
		this.rol = rol;
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Usuario: " + this.usuario + "\n token: " +this.token+"\n rol: "+ this.rol;
	}

	

	
	
}
