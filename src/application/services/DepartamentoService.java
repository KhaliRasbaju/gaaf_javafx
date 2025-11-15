package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.response.Common;
import application.utils.HttpClientUtil;

public class DepartamentoService {
	
    // ================================
    //        MAPEO DEL JSON
    // ================================
	private final ObjectMapper mapper = new ObjectMapper();

    // ================================
    //     OBTENER DEPARTAMENTOS
    // ================================
    public List<Common> obtenerDepartamentos() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/ubicacion/departamento", false);
        return mapper.readValue(response, new TypeReference<List<Common>>() {});
    }
    
    // ================================
    //   OBTENER UN DEPARTAMENTO POR ID
    // ================================
    public Common obtenerDepartamento(Long id) throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/ubicacion/departamento/"+id, false);
        return mapper.readValue(response, Common.class);
    }

}
