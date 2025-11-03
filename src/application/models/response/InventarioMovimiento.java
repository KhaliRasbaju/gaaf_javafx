package application.models.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class InventarioMovimiento {
	
	private Long id;
	
	private String producto;
	
	private String bodega;
	
    private String tipo;
    
    private Integer cantidad;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    
    private String observacion;
    
    public InventarioMovimiento() {}

	public InventarioMovimiento(Long id, String producto, String bodega, String tipo, Integer cantidad,
			LocalDate fecha, String observacion) {
		this.id = id;
		this.producto = producto;
		this.bodega = bodega;
		this.tipo = tipo;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.observacion = observacion;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getBodega() {
		return bodega;
	}
	
	public void setBodega(String bodega) {
		this.bodega = bodega;
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

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
}
