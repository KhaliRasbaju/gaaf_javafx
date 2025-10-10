<<<<<<< HEAD
package application.models.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReporteInventarioProductoBodega {

	private String bodega;
	
    private String producto;
    
    private Integer cantidad;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;
    
    public ReporteInventarioProductoBodega() {}

	public ReporteInventarioProductoBodega(String bodega, String producto, Integer cantidad, LocalDateTime fecha) {
		this.bodega = bodega;
		this.producto = producto;
		this.cantidad = cantidad;
		this.fecha = fecha;
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
}
=======
package application.models.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReporteInventarioProductoBodega {

	private String bodega;
	
    private String producto;
    
    private Integer cantidad;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;
    
    public ReporteInventarioProductoBodega() {}

	public ReporteInventarioProductoBodega(String bodega, String producto, Integer cantidad, LocalDateTime fecha) {
		this.bodega = bodega;
		this.producto = producto;
		this.cantidad = cantidad;
		this.fecha = fecha;
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
}
>>>>>>> b614632 (Se cambiaron los estilos y se movieron hacia el css)
