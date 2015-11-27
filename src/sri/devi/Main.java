package sri.devi;

import oracle.jdbc.driver.OracleDriver;
import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;
import sri.devi.Frame.Visualizer;
import sri.devi.model.Lion;
import sri.devi.model.Pond;
import sri.devi.model.Region;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        Statement stmt = constructStatement();

        List<Lion> lions = fetchLions(stmt);
        List<Pond> ponds = fetchPonds(stmt);
        List<Region> regions = fetchRegions(stmt);

        SwingUtilities.invokeLater(() -> new Visualizer(lions, ponds, regions));
    }

    private static List<Region> fetchRegions(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM REGION");
        List<Region> regions = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("REGION_ID");
            STRUCT regionBoundary = (STRUCT) rs.getObject("REGION_BOUNDARY");
            JGeometry geometry = JGeometry.load(regionBoundary);
            Region region = new Region(id, calculatePoints(geometry.getOrdinatesArray()));
            regions.add(region);
        }
        return regions;
    }

    private static List<Point> calculatePoints(double[] ordinatesArray) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < ordinatesArray.length; i = i + 2) {
            points.add(new Point((int) ordinatesArray[i], (int) ordinatesArray[i + 1]));
        }
        return points;
    }

    private static Statement constructStatement() throws SQLException {
        String host = "localhost";
        String port = "1521";
        String sid = "xe";

        String thinConn = String.format("jdbc:oracle:thin:@%s:%s:%s", host, port, sid);
        Driver driver = new OracleDriver();
        Connection connection = DriverManager.getConnection(thinConn, "PANTHERA", "Lion");
        return connection.createStatement();
    }

    private static List<Pond> fetchPonds(Statement stmt) throws SQLException {
        ResultSet rs2 = stmt.executeQuery("SELECT * FROM POND");
        List<Pond> ponds = new ArrayList<>();
        while (rs2.next()) {
            String id = rs2.getString("POND_ID");
            STRUCT pondBoundary = (STRUCT) rs2.getObject("POND_BOUNDARY");
            JGeometry geometry = JGeometry.load(pondBoundary);
            Rectangle2D bounds2D = geometry.createShape().getBounds2D();
            Pond pond = new Pond(id, bounds2D.getCenterX(), bounds2D.getCenterY(), (bounds2D.getWidth()) / 2);
            ponds.add(pond);
        }
        return ponds;
    }

    private static List<Lion> fetchLions(Statement stmt) throws SQLException {
        ResultSet rs1 = stmt.executeQuery("SELECT * FROM LION");
        List<Lion> lions = new ArrayList<>();
        while (rs1.next()) {
            String id = rs1.getString("LION_ID");
            STRUCT lionPoint = (STRUCT) rs1.getObject("LION_POINT");
            JGeometry geometry = JGeometry.load(lionPoint);
            Lion lion = new Lion(id, geometry.getJavaPoint().getX(), geometry.getJavaPoint().getY());
            lions.add(lion);
        }
        return lions;
    }
}
