package sri.devi.Frame;

import sri.devi.model.Lion;
import sri.devi.model.Pond;
import sri.devi.model.Region;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static sri.devi.model.PolygonUtil.getXCoordinates;
import static sri.devi.model.PolygonUtil.getYCoordinates;

/**
 * Created by Sridevi on 11/26/2015.
 */
public class Visualizer {

    private final List<Lion> lions;
    private final List<Pond> ponds;
    private final List<Region> regions;
    private final Polygon selectedPolygon;
    private List<Lion> insideLions;
    private List<Pond> insidePonds;

    public Visualizer(List<Lion> lions, List<Pond> ponds, List<Region> regions) {
        this.lions = lions;
        this.ponds = ponds;
        this.regions = regions;
        this.selectedPolygon = null;
        this.insideLions = new ArrayList<Lion>();
        this.insidePonds = new ArrayList<Pond>();
        initComponents();
    }

    private void initComponents() {
        JFrame mainMap = new JFrame();
        mainMap.setResizable(false);

        mainMap.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        List<Polygon> polygons = regions.stream().map(region -> new Polygon(getXCoordinates(region.getPoints()),
                getYCoordinates(region.getPoints()),
                region.getPoints().size())).collect(Collectors.toList());


        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                polygons.forEach(g::drawPolygon);
                g.setColor(Color.GREEN);
                lions.forEach(lion -> g.fillOval((int) lion.getX(), (int) lion.getY(), 7, 7));
                g.setColor(Color.BLUE);
                ponds.forEach(ponds -> g.fillOval((int) ponds.getCenterX(), (int) ponds.getCenterY(), (int) ponds.getRadius(), (int) ponds.getRadius()));
                g.setColor(Color.RED);
                insideLions.forEach(lion -> g.fillOval((int) lion.getX(), (int) lion.getY(), 7, 7));
                insidePonds.forEach(pond -> g.fillOval((int) pond.getCenterX(), (int) pond.getCenterY(), (int) pond.getRadius(), (int) pond.getRadius()));
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 600);
            }
        };

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                polygons.stream().filter(polygon -> polygon.contains(me.getX(), me.getY())).findFirst().ifPresent(clickedRegion -> {
                    insideLions = lions.stream().filter(lion -> clickedRegion.contains(lion.getX(), lion.getY())).collect(Collectors.toList());
                    insidePonds = ponds.stream().filter(pond -> clickedRegion.contains(pond.getCenterX(), pond.getCenterY())).collect(Collectors.toList());
                });
                p.repaint();
            }
        };
        p.addMouseListener(ma);//add listener to panel
        mainMap.add(p);

        mainMap.pack();
        mainMap.setVisible(true);
    }
}
