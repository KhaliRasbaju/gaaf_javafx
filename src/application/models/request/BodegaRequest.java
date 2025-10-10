package application.models.request;

public class BodegaRequest {  
	
    private String nombre;
    
    private String ubicacion;

    public BodegaRequest() {}

    public BodegaRequest(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return nombre + " (" + ubicacion + ")";
    }
}
