<<<<<<< HEAD
package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.response.Municipio;
import application.utils.HttpClientUtil;

public class MunicipioService {

	private final ObjectMapper mapper = new ObjectMapper();
	
	public List<Municipio> obtenerMunicipiosPorDepartamento(Long id) throws Exception {
	        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/ubicacion/municipio-departamento/"+id);
	    return mapper.readValue(response, new TypeReference<List<Municipio>>() {});
	}
	
	
	public List<Municipio> obtenerMunicipio(Long id) throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/ubicacion/municipio/"+id);
    return mapper.readValue(response, new TypeReference<List<Municipio>>() {});
}
	
	    
}
=======
package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.response.Municipio;
import application.utils.HttpClientUtil;

public class MunicipioService {

	private final ObjectMapper mapper = new ObjectMapper();
	
	public List<Municipio> obtenerMunicipiosPorDepartamento(Long id) throws Exception {
	        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/ubicacion/municipio-departamento/"+id);
	    return mapper.readValue(response, new TypeReference<List<Municipio>>() {});
	}
	
	
	public List<Municipio> obtenerMunicipio(Long id) throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/ubicacion/municipio/"+id);
    return mapper.readValue(response, new TypeReference<List<Municipio>>() {});
}
	
	    
}
>>>>>>> b614632 (Se cambiaron los estilos y se movieron hacia el css)
