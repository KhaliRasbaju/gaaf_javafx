package application.models;

public class ReporteBodega {
	private String bodega;
    private int cantidadDisponibleTotal;
    private int cantidadReservadaTotal;
    private int total;

    // Getters y setters
    public String getBodega(){ 
    	return bodega; 
    }
    public void setBodega(String bodega) { 
    	this.bodega = bodega; 
    }

    public int getCantidadDisponibleTotal() { 
    	return cantidadDisponibleTotal; 
    }
    public void setCantidadDisponibleTotal(int cantidadDisponibleTotal) { 
    	this.cantidadDisponibleTotal = cantidadDisponibleTotal; 
    }

    public int getCantidadReservadaTotal() {
    	return cantidadReservadaTotal; 
    }
    public void setCantidadReservadaTotal(int cantidadReservadaTotal) { 
    	this.cantidadReservadaTotal = cantidadReservadaTotal; 
    }

    public int getTotal() { 
    	return total; 
    }
    
    public void setTotal(int total) { 
    	this.total = total; 
    }
}
