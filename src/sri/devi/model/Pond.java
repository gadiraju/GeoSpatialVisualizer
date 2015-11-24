package sri.devi.model;

/**
 * Created by Sridevi on 11/24/2015.
 */
public class Pond {
    private String id;
    private final double centerX;
    private final double centerY;
    private final double radius;

    public Pond(String id, double centerX, double centerY, double radius) {
        this.id = id;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public String getId() {
        return id;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Pond{" +
                "id='" + id + '\'' +
                ", centerX=" + centerX +
                ", centerY=" + centerY +
                ", radius=" + radius +
                '}';
    }
}
