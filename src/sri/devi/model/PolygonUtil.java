package sri.devi.model;

import java.awt.*;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Sridevi on 11/26/2015.
 */
public class PolygonUtil {
    public static int[] getXCoordinates(List<Point> points) {
        IntStream intStream = points.stream().mapToInt(value -> (int) value.getX());
        return intStream.toArray();
    }

    public static int[] getYCoordinates(List<Point> points) {
        IntStream intStream = points.stream().mapToInt(value -> (int) value.getY());
        return intStream.toArray();
    }
}
