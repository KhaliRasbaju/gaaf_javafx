package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.request.CommonRequest;
import application.models.response.Common;
import application.models.response.ResponseCommon;
import application.utils.HttpClientUtil;

public class EntidadService {
	 private final ObjectMapper mapper = new ObjectMapper();
	 
 // 🔹 OBTENER TODAS LAS ENTIDADES (GET)
    public List<Common> obtenerEntidades() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/entidad", false);
        return mapper.readValue(response, new TypeReference<List<Common>>() {});
    }

    // 🔹 CREAR ENTIDAD (POST)
    public Common crearEntidad(CommonRequest entidad) throws Exception {
        String jsonBody = mapper.writeValueAsString(entidad);
        String response = HttpClientUtil.post(ApiConfig.BASE_URL + "/entidad/crear", jsonBody, false);
        return mapper.readValue(response, Common.class);
    }

    // 🔹 EDITAR ENTIDAD (PUT)
    public Common editarEntidad(Long id, CommonRequest entidad) throws Exception {
        String jsonBody = mapper.writeValueAsString(entidad);
        String response = HttpClientUtil.put(ApiConfig.BASE_URL + "/entidad/editar/" + id, jsonBody);
        return mapper.readValue(response, Common.class);
    }
    
    public Common obtenerEntidad(Long id) throws Exception {
        String response = HttpClientUtil.delete(ApiConfig.BASE_URL + "/entidad/" + id);
        return mapper.readValue(response, Common.class);
    }
    

    // 🔹 ELIMINAR ENTIDAD (DELETE)
    public ResponseCommon eliminarEntidad(Long id) throws Exception {
        String response = HttpClientUtil.delete(ApiConfig.BASE_URL + "/entidad/" + id);
        return mapper.readValue(response, ResponseCommon.class);
    }
}
