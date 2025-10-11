package application.session;

public class SessionManager {
	private static SessionManager instance;
	private String username;
 	private String jwtToken;
 	private String userRole;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }

    public void setSession(String token, String role, String username) {
        this.jwtToken = token;
        this.userRole = role;
        this.username = username;
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

    public void clearSession() {
        jwtToken = null;
        userRole = null;
    }

    public boolean isLoggedIn() {
        return jwtToken != null;
    }
}
