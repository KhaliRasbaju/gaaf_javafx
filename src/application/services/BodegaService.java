package application.services;


import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.request.BodegaRequest;
import application.models.response.Bodega;
import application.models.response.ResponseCommon;
import application.utils.HttpClientUtil;

public class BodegaService {

    private final ObjectMapper mapper = new ObjectMapper();


    public List<Bodega> obtenerBodegas() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/bodega", false);
        return mapper.readValue(response, new TypeReference<List<Bodega>>() {});
    }

   
    public Bodega crearBodega(BodegaRequest bodega) throws Exception {
        String jsonBody = mapper.writeValueAsString(bodega);
        String response = HttpClientUtil.post(ApiConfig.BASE_URL + "/bodega/crear", jsonBody, false);
        return mapper.readValue(response, Bodega.class);
    }
  
    public Bodega editarBodega(Long id, BodegaRequest bodega) throws Exception {
        String jsonBody = mapper.writeValueAsString(bodega);
        String response = HttpClientUtil.put(ApiConfig.BASE_URL + "/bodega/" + id, jsonBody);
        return mapper.readValue(response, Bodega.class);
    }
    
    public Bodega obtenerBodega(Long id) throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/bodega/"+id, false);
        return mapper.readValue(response, Bodega.class);
    }

   
    public ResponseCommon eliminarBodega(Long id) throws Exception {
        String response = HttpClientUtil.delete(ApiConfig.BASE_URL + "/bodega/" + id);
        return mapper.readValue(response, ResponseCommon.class);
    }
}
