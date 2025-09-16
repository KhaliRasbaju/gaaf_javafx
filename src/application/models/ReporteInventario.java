package application.models;

public class ReporteInventario {
	private String fecha;
    private int cantidadDisponible;
    private int cantidadReservada;
    private String producto;
    private String bodega;

    // Getters y setters
    public String getFecha(){
    	return fecha; 
    }
    
    public void setFecha(String fecha) { 
    	this.fecha = fecha; 
    }

    public int getCantidadDisponible() { 
    	return cantidadDisponible; 
    }
    
    public void setCantidadDisponible(int cantidadDisponible) { 
    	this.cantidadDisponible = cantidadDisponible; 
    }

    public int getCantidadReservada() { 
    	return cantidadReservada; 
    }
    public void setCantidadReservada(int cantidadReservada) { 
    	this.cantidadReservada = cantidadReservada; 
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
}
