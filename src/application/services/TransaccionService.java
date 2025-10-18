package application.services;


import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.request.TransaccionRequest;
import application.models.response.Transaccion;
import application.utils.HttpClientUtil;

public class TransaccionService {

    private final ObjectMapper mapper = new ObjectMapper();

    // 🔹 CREAR TRANSACCIÓN (POST)
    public Transaccion crearTransaccion(TransaccionRequest transaccion) throws Exception {
        String jsonBody = mapper.writeValueAsString(transaccion);
        String response = HttpClientUtil.post(ApiConfig.BASE_URL + "/transaccion/crear", jsonBody, false);
        return mapper.readValue(response, Transaccion.class);
    }


}
