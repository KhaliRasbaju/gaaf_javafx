package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.request.CuentaRequest;
import application.models.response.Cuenta;
import application.models.response.ResponseCommon;
import application.utils.HttpClientUtil;

public class CuentaService {
	
    // ================================
    //        MAPEO DEL JSON
    // ================================
	private final ObjectMapper mapper = new ObjectMapper();

    // ================================
    //     OBTENER TODAS LAS CUENTAS
    // ================================
    public List<Cuenta> obetenrCuentas() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/cuenta", false);
        return mapper.readValue(response, new TypeReference<List<Cuenta>>() {});
    }

    // ================================
    //          EDITAR CUENTA
    // ================================
    public Cuenta editarCuenta(Long id, CuentaRequest cuenta) throws Exception {
        String jsonBody = mapper.writeValueAsString(cuenta);
        String response = HttpClientUtil.put(ApiConfig.BASE_URL + "/cuenta/editar/" + id, jsonBody);
        return mapper.readValue(response, Cuenta.class);
    }
    
    // ================================
    //          OBTENER CUENTA
    // ================================
    public Cuenta obtenerCuenta(Long id) throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/cuenta/" + id, false);
        return mapper.readValue(response, Cuenta.class);
    }

    // ================================
    //          ELIMINAR CUENTA
    // ================================
    public ResponseCommon eliminarCuenta(Long id) throws Exception {
        String response = HttpClientUtil.delete(ApiConfig.BASE_URL + "/cuenta/" + id);
        return mapper.readValue(response, ResponseCommon.class);
    }

}
