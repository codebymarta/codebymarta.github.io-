
	package es.gva.edu.iesjuandegaray.bicis;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class ValenbisiAPI26T1v2_1 {

private static final String API_URL =
"https://geoportal.valencia.es/server/rest/services/OPENDATA/Trafico/MapServer/228/query?where=1=1&outFields=*&f=json";

    public static void main(String[] args) {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(API_URL);
            HttpResponse response = httpClient.execute(request);

            HttpEntity entity = response.getEntity();

            if (entity != null) {

                String result = EntityUtils.toString(entity);

                // Convertimos a JSON
                JSONObject jsonObject = new JSONObject(result);

                // Obtenemos el array "features"
                JSONArray features = jsonObject.getJSONArray("features");

                System.out.println("Número de estaciones: " + features.length());
                System.out.println();

                // BUCLE RECORRE VECTOR FEATURES MOSTRANDO LOS DATOS SOLICITADOS.
                
                  for (int i = 0; i < features.length(); i++) {
                    
                   JSONObject station = features.getJSONObject(i);
                    
                    JSONObject attrs = station.getJSONObject("attributes");
                    String nombre = attrs.optString("address", "N/A");
                    int bicis = attrs.optInt("available", 0);
                    int huecos = attrs.optInt("free", 0);
                    int total = attrs.optInt("total", 0);
                    
                     System.out.println("ESTACIÓN: " + nombre);
                    System.out.println("  - Bicicletas disponibles: " + bicis);
                    System.out.println("  - Bornetas vacías: " + huecos);
                    System.out.println("  - Capacidad total: " + total);
                    System.out.println("--------------------------------------------------");
                    }
            }
                
            } catch (IOException e) {
            System.out.println("Error en la petición HTTP:");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error procesando JSON:");
            e.printStackTrace();
        }
    }
}
	
