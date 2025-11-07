package application.session;

public class SessionManager {
	private static SessionManager instance;
	private String username;
 	private String jwtToken;
 	private String userRole;
 	private String id;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }

    public void setSession(String token, String role, String username, String id) {
        this.jwtToken = token;
        this.userRole = role;
        this.username = username;
        this.id = id;
    }  
    
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

	public void clearSession() {
        jwtToken = null;
        userRole = null;
    }

    public boolean isLoggedIn() {
        return jwtToken != null;
    }
}
