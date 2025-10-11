package application.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sesion {

	private String usuario;
	
	private String token;
	
	private String rol;
	
	public Sesion() {}

	public Sesion(String usuario, String token, String rol) {
		this.usuario = usuario;
		this.token = token;
		this.rol = rol;
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

	@Override
	public String toString() {
		return "Usuario: " + this.usuario + "\n token: " +this.token+"\n rol: "+ this.rol;
	}

	

	
	
}
