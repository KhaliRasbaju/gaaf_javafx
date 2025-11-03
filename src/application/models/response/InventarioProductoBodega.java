package application.models.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class InventarioProductoBodega {


	private Long id;
	
	private String bodega;
	
    private String producto;
    
    private Integer cantidad;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    
    public InventarioProductoBodega() {}

	

	public InventarioProductoBodega(Long id, String bodega, String producto, Integer cantidad, LocalDate fecha) {
		this.id = id;
		this.bodega = bodega;
		this.producto = producto;
		this.cantidad = cantidad;
		this.fecha = fecha;
	}

	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
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

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
}
