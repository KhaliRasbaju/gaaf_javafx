package application.models.request;

public class TransaccionRequest {
	

	// ================================
	//        ATRIBUTOS PRINCIPALES
	// ================================
	private Long idProducto;
	
	private Long idPedido;
	
	private String observacion;
	
	private String tipo;
	
	private Long idBodega;
	
	private Integer cantidad;
	
	// ================================
	//          CONSTRUCTORES
	// ================================
	public TransaccionRequest() {}

	public TransaccionRequest(Long idProducto, Long idPedido, String observacion, String tipo, Long idBodega,
			Integer cantidad) {
		this.idProducto = idProducto;
		this.idPedido = idPedido;
		this.observacion = observacion;
		this.tipo = tipo;
		this.idBodega = idBodega;
		this.cantidad = cantidad;
	}

	// ================================
	//        GETTERS Y SETTERS
	// ================================
	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getIdBodega() {
		return idBodega;
	}

	public void setIdBodega(Long idBodega) {
		this.idBodega = idBodega;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
