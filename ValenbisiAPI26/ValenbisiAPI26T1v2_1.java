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

	
	// https://geoportal.valencia.es/server/rest/services/OPENDATA/Trafico/MapServer/228/query?where=1=1&outFields=*&f=json
    private static final String API_URL =
            "https://geoportal.valencia.es/server/rest/services/OPENDATA/Trafico/MapServer/228/query"
            + "?where=1%3D1"
            + "&outFields=*"
            + "&returnGeometry=true"
            + "&f=json";

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
                    // Los datos de cada estación están dentro del objeto "attributes"
                    JSONObject attributes = station.getJSONObject("attributes");
                    
                     for (int i = 0; i < features.length(); i++) {
                    JSONObject station = features.getJSONObject(i);
                    JSONObject attributes = station.getJSONObject("attributes");
                    
                    // Nombres de campos correctos para la API de Valencia
                    String nombre = attributes.optString("address", "Nombre no disponible"); 
                    int bicisLibres = attributes.optInt("available", 0);
                    int huecosLibres = attributes.optInt("free", 0);

                    System.out.println("ESTACIÓN: " + nombre);
                    System.out.println("  - Bicicletas disponibles: " + bicisLibres);
                    System.out.println("  - Bornetas vacías: " + huecosLibres);
                    System.out.println("--------------------------------------------------");
                }
                

                    System.out.println("ESTACIÓN: " + nombre);
                    System.out.println("  - Bicicletas disponibles: " + bicisLibres);
                    System.out.println("  - Bornetas vacías: " + huecosLibres);
                    System.out.println("  - Capacidad total: " + totalPuestos);
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


