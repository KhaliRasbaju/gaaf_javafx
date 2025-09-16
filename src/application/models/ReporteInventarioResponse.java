package application.models;

import java.util.List;

public class ReporteInventarioResponse {
	 private List<ReporteInventario> reporteInventario;
	    private List<ReporteBodega> reporteBodega;
	    private int cantidad_total;

	    // Getters y setters
	    public List<ReporteInventario> getReporteInventario() { 
	    	return reporteInventario; 
	    }
	    
	    public void setReporteInventario(List<ReporteInventario> reporteInventario) { 
	    	this.reporteInventario = reporteInventario; 
	    }

	    public List<ReporteBodega> getReporteBodega() { 
	    	return reporteBodega; 
	    }
	    
	    public void setReporteBodega(List<ReporteBodega> reporteBodega) { 
	    	this.reporteBodega = reporteBodega; 
	    }

	    public int getCantidad_total() { 
	    	return cantidad_total; 
	    }
	    
	    public void setCantidad_total(int cantidad_total) { 
	    	this.cantidad_total = cantidad_total; 
	    }
}
