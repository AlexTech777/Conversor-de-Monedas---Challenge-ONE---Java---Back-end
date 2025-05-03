package lad.com.alura.conversormoneda;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ConsultaMoneda {
    private final String API_KEY = "215ff05f3318144acc182b6e";
    private final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public Map<String, Double> obtenerTasas() {
        URI direccion = URI.create(BASE_URL + API_KEY + "/latest/USD");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(direccion).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Moneda datos = new Gson().fromJson(response.body(), Moneda.class);
            return datos.conversion_rates();
        } catch (Exception e) {
            System.out.println("Error en la solicitud a la API: " + e.getMessage());
            return null;
        }
    }
}


