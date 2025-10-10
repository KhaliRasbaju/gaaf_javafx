<<<<<<< HEAD
package application.models.response;

public class Bodega {
	
    private Long id;
    
    private String nombre;
    
    private String ubicacion;

    public Bodega() {}

    public Bodega(String nombre, String ubicacion, Long id) {
    	this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
=======
package application.models.response;

public class Bodega {
	
    private Long id;
    
    private String nombre;
    
    private String ubicacion;

    public Bodega() {}

    public Bodega(String nombre, String ubicacion, Long id) {
    	this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
>>>>>>> b614632 (Se cambiaron los estilos y se movieron hacia el css)
}