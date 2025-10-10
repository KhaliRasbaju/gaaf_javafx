package application.models.response;


public class Cuenta {
	
	private Long id;
	
	private String numero;
	
	private String tipo;
	
	private Common entidad;
	
	public Cuenta() {}

	public Cuenta(Long id, String numero, String tipo, Common entidad) {
		this.id = id;
		this.numero = numero;
		this.tipo = tipo;
		this.entidad = entidad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Common getEntidad() {
		return entidad;
	}

	public void setEntidad(Common entidad) {
		this.entidad = entidad;
	}

}
