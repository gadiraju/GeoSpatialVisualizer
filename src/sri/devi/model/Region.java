package sri.devi.model;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Sridevi on 11/24/2015.
 */
public class Region {
    private final String id;
    private final List<Point> points;

    public Region(String id, List<Point> points) {
        this.id = id;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public List<Point> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id='" + id + '\'' +
                ", points=" + points +
                '}';
    }
}
