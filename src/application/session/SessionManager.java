package application.session;

public class SessionManager {
	
    // ============================================
    //           INSTANCIA SINGLETON
    // ============================================
	private static SessionManager instance;
	
    // ============================================
    //              DATOS DE LA SESIÓN
    // ============================================
	private String username;
 	private String jwtToken;
 	private String userRole;
 	private String id;

    // ============================================
    //              CONSTRUCTOR
    // ============================================
    private SessionManager() {}

    // ============================================
    //          OBTENER INSTANCIA ÚNICA
    // ============================================
    public static SessionManager getInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }
    
    // ============================================
    //          CONFIGURAR DATOS DE SESIÓN
    // ============================================
    public void setSession(String token, String role, String username, String id) {
        this.jwtToken = token;
        this.userRole = role;
        this.username = username;
        this.id = id;
    }  
    
    // ============================================
    //          METODOS GET
    // ============================================
    public String getUsername() {
		return username;
	}

	public String getToken() {
        return jwtToken;
    }

    public String getRole() {
        return userRole;
    }
    
    public String getId() {
		return id;
	}
    
    // ============================================
    //          LIMPIAR SESIÓN
    // ============================================

	public void clearSession() {
        jwtToken = null;
        userRole = null;
    }
	
	// ============================================
	//          VERIFICAR SESIÓN ACTIVA
	// ============================================

    public boolean isLoggedIn() {
        return jwtToken != null;
    }
}
