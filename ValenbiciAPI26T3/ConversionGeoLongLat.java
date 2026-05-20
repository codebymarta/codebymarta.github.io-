

import org.locationtech.proj4j.BasicCoordinateTransform;
import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.ProjCoordinate;

public class ConversionGeoLongLat {
    public static String conversion(double x, double y) {
        CRSFactory factory = new CRSFactory();
        // Definimos los sistemas de coordenadas (ETRS89 a WGS84)
        CoordinateReferenceSystem srcCRS = factory.createFromName("EPSG:25830");
        CoordinateReferenceSystem dstCRS = factory.createFromName("EPSG:4326");

        BasicCoordinateTransform transform = new BasicCoordinateTransform(srcCRS, dstCRS);
        ProjCoordinate srcCoord = new ProjCoordinate(x, y);
        ProjCoordinate dstCoord = new ProjCoordinate();

        transform.transform(srcCoord, dstCoord);
        return dstCoord.y + ", " + dstCoord.x; // Devuelve Latitud, Longitud
    }
}