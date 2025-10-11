package application.services;

import java.util.prefs.Preferences;

public class StorageService {

	 private static final String TOKEN_KEY = "";
	    private static final String ROLE_KEY = "";
	    private final Preferences prefs = Preferences.userNodeForPackage(StorageService.class);

	    public void saveSession(String token, String role) {
	        prefs.put(TOKEN_KEY, token);
	        prefs.put(ROLE_KEY, role);
	    }

	    public String getToken() {
	        return prefs.get(TOKEN_KEY, null);
	    }

	    public String getRole() {
	        return prefs.get(ROLE_KEY, null);
	    }

	    public void clearSession() {
	        prefs.remove(TOKEN_KEY);
	        prefs.remove(ROLE_KEY);
	    }
	
}
