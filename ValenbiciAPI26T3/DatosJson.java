

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class DatosJson {
    private String datos = "";
    private String[] values;
    private static String API_URL = "https://geoportal.valencia.es/server/rest/services/OPENDATA/Trafico/MapServer/228/query?where=1%3D1&outFields=*&returnGeometry=true&f=json";

    public void mostrarDatos(int nE) {
        this.datos = "";
        this.values = new String[nE];
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(API_URL);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(result);
                JSONArray features = jsonObject.getJSONArray("features");

                for (int i = 0; i < Math.min(nE, features.length()); i++) {
                    JSONObject feature = features.getJSONObject(i);
                    JSONObject attributes = feature.getJSONObject("attributes");
                    JSONObject geometry = feature.getJSONObject("geometry");

                    String nombre = attributes.getString("nombre");
                    int bicis = attributes.getInt("bicicletas_disponibles");
                    double x = geometry.getDouble("x");
                    double y = geometry.getDouble("y");

                  
                    String coordsGPS = ConversionGeoLongLat.conversion(x, y);

                    this.datos += "Estación: " + nombre + "\nBicis: " + bicis + "\nGPS: " + coordsGPS + "\n\n";
                    this.values[i] = nombre + ";" + bicis + ";" + coordsGPS;
                }
            }
        } catch (Exception e) {
            this.datos = "Error: " + e.getMessage();
        }
    }

    public String getDatos() { return datos; }
}
