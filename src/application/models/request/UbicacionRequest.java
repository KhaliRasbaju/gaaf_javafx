package application.models.request;

public class UbicacionRequest {


    private String direccion;
    
    private Long idMunicipio;

    public UbicacionRequest() {}

    public UbicacionRequest(String direccion, Long idMunicipio) {
        this.direccion = direccion;
        this.idMunicipio = idMunicipio;
    }

    // Getters y Setters
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

}
