package sri.devi.model;

/**
 * Created by Sridevi on 11/24/2015.
 */
public class Lion {
    private final String id;
    private final double x;
    private final double y;

    public Lion(String id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Lion{" +
                "id='" + id + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
