package application.models.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReporteInventarioMovimiento {

	private String producto;
	
	private String bodega;
	
    private String tipo;
    
    private Integer cantidad;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fecha;
    
    private String observacion;
    
    public ReporteInventarioMovimiento() {}

	public ReporteInventarioMovimiento(String producto, String bodega, String tipo, Integer cantidad,
			LocalDateTime fecha, String observacion) {
		this.producto = producto;
		this.bodega = bodega;
		this.tipo = tipo;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.observacion = observacion;
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

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
}
