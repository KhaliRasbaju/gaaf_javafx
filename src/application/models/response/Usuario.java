package application.models.response;

public class Usuario {
	
    private Long id;
    
    private String usuario;
    
    private String correo;
    
    private String nombre;
    
    private String telefono;

    private String rol;

    public Usuario() {}


    public Usuario(Long id, String usuario, String correo, String nombre, String telefono,  String rol) {
        this.id = id;
        this.usuario = usuario;
        this.correo = correo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.rol = rol;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    @Override
    public String toString() {
        return usuario + " (" + rol + ")";
    }
}
