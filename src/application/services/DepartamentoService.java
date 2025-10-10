<<<<<<< HEAD
package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.response.Common;
import application.utils.HttpClientUtil;

public class DepartamentoService {
	
	private final ObjectMapper mapper = new ObjectMapper();


    public List<Common> obtenerDepartamentos() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/ubicacion/departamento");
        return mapper.readValue(response, new TypeReference<List<Common>>() {});
    }
    
    public Common obtenerDepartamento(Long id) throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/ubicacion/departamento/"+id);
        return mapper.readValue(response, Common.class);
    }

}
=======
package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.response.Common;
import application.utils.HttpClientUtil;

public class DepartamentoService {
	
	private final ObjectMapper mapper = new ObjectMapper();


    public List<Common> obtenerDepartamentos() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/ubicacion/departamento");
        return mapper.readValue(response, new TypeReference<List<Common>>() {});
    }
    
    public Common obtenerDepartamento(Long id) throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/ubicacion/departamento/"+id);
        return mapper.readValue(response, Common.class);
    }

}
>>>>>>> b614632 (Se cambiaron los estilos y se movieron hacia el css)
