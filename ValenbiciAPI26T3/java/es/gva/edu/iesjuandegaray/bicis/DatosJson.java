package es.gva.edu.iesjuandegaray.bicis;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class DatosJson {
    private String datos = "";
    private String[] values;
    private int numEst;
    private static final String API_URL = "https://geoportal.valencia.es/server/rest/services/OPENDATA/Trafico/MapServer/228/query?where=1%3D1&outFields=*&returnGeometry=true&f=json";

    public DatosJson(int nE) {
        this.numEst = nE;
        this.values = new String[nE];
    }

    public void mostrarDatos(int nE) {
        this.numEst = nE;
        this.datos = "";
        this.values = new String[nE];
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(API_URL);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray features = jsonObject.getJSONArray("features");

                    for (int i = 0; i < Math.min(nE, features.length()); i++) {
                        JSONObject feature = features.getJSONObject(i);
                        JSONObject attr = feature.getJSONObject("attributes");
                        JSONObject geom = feature.getJSONObject("geometry");

                        // --- AQUÍ ESTABA EL CAMBIO ---
                        // Probamos con varios nombres porque la API a veces cambia
                        String nombreEst = attr.optString("address", 
                                           attr.optString("direccio", 
                                           attr.optString("nombre", "Estación " + (i+1))));
                        
                        int bicis = attr.optInt("available_bikes", 
                                    attr.optInt("bicisdisponibles", 0));
                        
                        int libres = attr.optInt("free_slots", 
                                     attr.optInt("borneslibres", 0));

                        double x = geom.getDouble("x");
                        double y = geom.getDouble("y");

                        String coords = ConversionGeoLongLat.conversion(x, y);
                        String[] gps = coords.split(",");

                        // Guardamos para el TextArea
                        this.datos += "Estación: " + nombreEst + "\nBicis: " + bicis + " | GPS: " + coords + "\n\n";
                        
                        // Guardamos para la BDD
                        this.values[i] = (i+1) + ";" + nombreEst + ";" + bicis + ";" + libres + ";" + gps[0] + ";" + gps[1];
                    }
                }
            }
        } catch (Exception e) {
            this.datos = "Error: " + e.getMessage();
        }
    }

    public String getDatos() { 
        return this.datos; 
    }

    public String[] getValues() { 
        return this.values; 
    }
}    