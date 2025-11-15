package application.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.request.RegistrarRequest;
import application.models.request.SesionRequest;
import application.models.response.Sesion;
import application.models.response.Usuario;
import application.utils.HttpClientUtil;

public class AutentificacionService {
	
    // ================================
    //        MAPEO DEL JSON
    // ================================
	private final ObjectMapper mapper = new ObjectMapper();

    // ================================
    //     REGISTRAR NUEVO USUARIO
    // ================================
	public Usuario registrarUsuario(RegistrarRequest registrar) throws Exception {
        String jsonBody = mapper.writeValueAsString(registrar);
        String response = HttpClientUtil.post(ApiConfig.BASE_AUTH_URL + "/auth/registrar", jsonBody, false);
        return mapper.readValue(response, Usuario.class);
    }
	
    // ================================
    //         INICIAR SESIÓN
    // ================================
	public Sesion iniciarSesion(SesionRequest sesion) throws Exception{
		String jsonBody = mapper.writeValueAsString(sesion);
	    String response = HttpClientUtil.post(ApiConfig.BASE_AUTH_URL + "/auth/iniciar", jsonBody, true);
        return mapper.readValue(response, Sesion.class);
	}
}
