<<<<<<< HEAD
package application.models.response;

public class Transaccion {
	
	private Long id;
	
	private String tipo;
	
	private Integer cantidad;
	
	private String observacion;
	
    private Long idPedido;
    
    private String bodega;
    
    private String producto;

	public Transaccion() {}

	public Transaccion(Long id, String tipo, Integer cantidad, String observacion, Long idPedido, String bodega,
			String producto) {
		this.id = id;
		this.tipo = tipo;
		this.cantidad = cantidad;
		this.observacion = observacion;
		this.idPedido = idPedido;
		this.bodega = bodega;
		this.producto = producto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getBodega() {
		return bodega;
	}

	public void setBodega(String bodega) {
		this.bodega = bodega;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}
    
	
    
}
=======
package application.models.response;

public class Transaccion {
	
	private Long id;
	
	private String tipo;
	
	private Integer cantidad;
	
	private String observacion;
	
    private Long idPedido;
    
    private String bodega;
    
    private String producto;

	public Transaccion() {}

	public Transaccion(Long id, String tipo, Integer cantidad, String observacion, Long idPedido, String bodega,
			String producto) {
		this.id = id;
		this.tipo = tipo;
		this.cantidad = cantidad;
		this.observacion = observacion;
		this.idPedido = idPedido;
		this.bodega = bodega;
		this.producto = producto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getBodega() {
		return bodega;
	}

	public void setBodega(String bodega) {
		this.bodega = bodega;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}
    
	
    
}
>>>>>>> b614632 (Se cambiaron los estilos y se movieron hacia el css)
