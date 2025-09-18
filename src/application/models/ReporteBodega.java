package application.models;

public class ReporteBodega {
	private String bodega;
    private Integer cantidadDisponibleTotal;
    private Integer cantidadReservadaTotal;
    private Integer total;

    // Getters y setters
    public String getBodega(){ 
    	return bodega; 
    }
    public void setBodega(String bodega) { 
    	this.bodega = bodega; 
    }

    public Integer getCantidadDisponibleTotal() { 
    	return cantidadDisponibleTotal; 
    }
    public void setCantidadDisponibleTotal(Integer cantidadDisponibleTotal) { 
    	this.cantidadDisponibleTotal = cantidadDisponibleTotal; 
    }

    public Integer getCantidadReservadaTotal() {
    	return cantidadReservadaTotal; 
    }
    public void setCantidadReservadaTotal(Integer cantidadReservadaTotal) { 
    	this.cantidadReservadaTotal = cantidadReservadaTotal; 
    }

    public Integer getTotal() { 
    	return total; 
    }
    
    public void setTotal(Integer total) { 
    	this.total = total; 
    }
}
