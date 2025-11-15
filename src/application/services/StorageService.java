package application.services;

import java.util.prefs.Preferences;

public class StorageService {

    // ================================
    //     TOKENS PARA ALMACENAMIENTO
    // ================================
	 private static final String TOKEN_KEY = "";
	    private static final String ROLE_KEY = "";
	    private final Preferences prefs = Preferences.userNodeForPackage(StorageService.class);

	    // ================================
	    //       GUARDAR SESIÓN
	    // ================================
	    public void saveSession(String token, String role) {
	        prefs.put(TOKEN_KEY, token);
	        prefs.put(ROLE_KEY, role);
	    }

	    // ================================
	    //     OBTENER TOKEN GUARDADO
	    // ================================
	    public String getToken() {
	        return prefs.get(TOKEN_KEY, null);
	    }


	    // ================================
	    //     OBTENER ROL GUARDADO
	    // ================================
	    public String getRole() {
	        return prefs.get(ROLE_KEY, null);
	    }

	    // ================================
	    //        LIMPIAR LA SESIÓN
	    // ================================
	    public void clearSession() {
	        prefs.remove(TOKEN_KEY);
	        prefs.remove(ROLE_KEY);
	    }
	
}
