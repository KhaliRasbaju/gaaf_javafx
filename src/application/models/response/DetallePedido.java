package application.models.response;

public class DetallePedido {
	
	// ================================
	//        ATRIBUTOS PRINCIPALES
	// ================================
	private Float fermentacion;
	
    private Float peso;
    
    private Integer cantidad;
    
    private Float humedad;
    
    private Float estadoCacao;
    
    private String producto;
    
    // ================================
    //        CONSTRUCTORES
     // ================================
	public DetallePedido() {}

	public DetallePedido(Float fermentacion, Float peso, Integer cantidad, Float humedad, Float estadoCacao,
			String producto) {
		this.fermentacion = fermentacion;
		this.peso = peso;
		this.cantidad = cantidad;
		this.humedad = humedad;
		this.estadoCacao = estadoCacao;
		this.producto = producto;
	}
	
	// ================================
	//        GETTERS & SETTERS
	// ================================

	public Float getFermentacion() {
		return fermentacion;
	}

	public void setFermentacion(Float fermentacion) {
		this.fermentacion = fermentacion;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Float getHumedad() {
		return humedad;
	}

	public void setHumedad(Float humedad) {
		this.humedad = humedad;
	}

	public Float getEstadoCacao() {
		return estadoCacao;
	}

	public void setEstadoCacao(Float estadoCacao) {
		this.estadoCacao = estadoCacao;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}
    
    
    
    
}
