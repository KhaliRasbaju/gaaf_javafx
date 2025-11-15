package application.models.request;

public class BodegaRequest {  
	
    // ================================
    //        ATRIBUTOS PRINCIPALES
    // ================================
    private String nombre;
    
    private String ubicacion;

    
    // ================================
    //          CONSTRUCTORES
    // ================================
    public BodegaRequest() {}

    public BodegaRequest(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }
    
    // ================================
    //        GETTERS Y SETTERS
    // ================================
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

    // ================================
    //       REPRESENTACIÓN STRING
    // ================================
    @Override
    public String toString() {
        return nombre + " (" + ubicacion + ")";
    }
}
