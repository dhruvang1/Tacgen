
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

public class GetCircles {

    boolean firstPointCaptured = false;
    boolean secondPointCaptured = false;
    Point selectedPoint1, selectedPoint2, selectedPoint3;
    ArrayList<Pair<Float, Float>> circles = new ArrayList<>();
    ArrayList<Pair<Float, Float>> centers = new ArrayList<>();
    ArrayList<Integer> circleIndices = new ArrayList<>();
    ArrayList<Float> radii = new ArrayList<>();
    ArrayList<Integer> fillArray = new ArrayList<>();
    ArrayList<Color> colorArray = new ArrayList<>();

    Pair<Float, Float> circleA, circleB, circleC;
    float radius;
    Screen screen = new Screen(0);

    public GetCircles() {}

    public void getCircle(MouseEvent e) throws NoninvertibleTransformException {
        float x = (float) 0.0;
        float y = (float) 0.0;
        Pair<Float, Float> center = new Pair<>(x, y);

        if (!firstPointCaptured) {
            selectedPoint1 = Screen.a1.getOriginalZoomedCoordinate(e);
            firstPointCaptured = true;
            circleA.setL((float) selectedPoint1.x);
            circleA.setR((float) selectedPoint1.y);
            selectedPoint2 = null;
            selectedPoint3 = null;
            //	System.out.println("Started :- "+ s1+" , "+s2+","+s3);
            circles.add(circleA);

        } else if (!secondPointCaptured) {
            selectedPoint2 = Screen.a1.getOriginalZoomedCoordinate(e);
            if (Screen.line_object.calc_distance(selectedPoint2.x, selectedPoint2.y, selectedPoint1.x, selectedPoint1.y) > 5) {
                circleB.setL((float) selectedPoint2.x);
                circleB.setR((float) selectedPoint2.y);
                circles.add(circleB);
                secondPointCaptured = true;
            }
        } else {
            selectedPoint3 = Screen.a1.getOriginalZoomedCoordinate(e);
            if ((Screen.line_object.calc_distance(selectedPoint2.x, selectedPoint2.y, selectedPoint3.x, selectedPoint3.y) > 5) && (Screen.line_object.calc_distance(selectedPoint3.x, selectedPoint3.y, selectedPoint1.x, selectedPoint1.y) > 5)
                    && !Screen.line_object.collinear(selectedPoint1.x, selectedPoint1.y, selectedPoint2.x, selectedPoint2.y, selectedPoint3.x, selectedPoint3.y)) {
                circleC.setL((float) selectedPoint3.x);
                circleC.setR((float) selectedPoint3.y);
                circles.add(circleC);
                firstPointCaptured = secondPointCaptured = false;
                Circle circle = new Circle();
                int[] b = circle.getCenter((int) (float) circleA.getL(), (int) (float) circleA.getR(), (int) (float) circleB.getL(), (int) (float) circleB.getR(), (int) (float) circleC.getL(), (int) (float) circleC.getR());
                center.setL((float) (int) b[0]);
                center.setR((float) (int) b[1]);
                centers.add(center);
                radius = circle.getRadius(center, circleC);
                radii.add(radius);
                colorArray.add(Screen.current_color);
                fillArray.add(0);
                //System.out.println(radius);
                circles.clear();
            }
            screen.repaint(Screen.screen, Screen.a2.screenCopy);
            Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
        }

    }

    public void deleteTemp() {
        firstPointCaptured = false;
        secondPointCaptured = false;
        circles.clear();
    }

    public void addIndices(MouseEvent e) throws NoninvertibleTransformException {
        Point originalZoomedCoordinate = Screen.a1.getOriginalZoomedCoordinate(e);
        for (int i = 0; i < centers.size(); i++) {
            float distance = (float) (Math.pow((originalZoomedCoordinate.x - centers.get(i).getL()), 2) + Math.pow((originalZoomedCoordinate.y - centers.get(i).getR()), 2));
            if (distance < Math.pow(radii.get(i), 2)) {
                if (circleIndices.contains(i)) {
                    circleIndices.remove((Integer) i);
                } else {
                    circleIndices.add(i);
                }
            }
        }
    }

    public void addColorIndices(MouseEvent e) throws NoninvertibleTransformException {
        Point originalZoomedCoordinate = Screen.a1.getOriginalZoomedCoordinate(e);
        for (int i = 0; i < centers.size(); i++) {
            float distance = (float) (Math.pow((originalZoomedCoordinate.x - centers.get(i).getL()), 2) + Math.pow((originalZoomedCoordinate.y - centers.get(i).getR()), 2));
            if (distance < Math.pow(radii.get(i), 2)) {
                if (fillArray.get(i) == 1 && colorArray.get(i) != Screen.current_color) {
                    colorArray.set(i, Screen.current_color);
                    fillArray.set(i, 1);
                } else {
                    colorArray.set(i, Screen.current_color);
                    fillArray.set(i, (fillArray.get(i) + 1) % 2);
                }
            }
            fillArray.set(i, 0);  // to remove fill
        }
    }

    public void deleteIndices() {
//           ArrayList<Pair<Float,Float>> temp_Circles=new ArrayList<Pair<Float,Float>>();
//           ArrayList<Pair<Float,Float>> temp_Centers=new ArrayList<Pair<Float,Float>>();
//           ArrayList<Float> temp_Radius=new ArrayList<Float>();
        circleIndices.sort(null);
        for (int i = circleIndices.size() - 1; i >= 0; i--) {
//                temp_Circles.add(Circles.get(3*circle_indices.get(i)));
//                temp_Circles.add(Circles.get(3*circle_indices.get(i)+1));
//                temp_Circles.add(Circles.get(3*circle_indices.get(i)+2));
            colorArray.remove((int) circleIndices.get(i));

            fillArray.remove((int) circleIndices.get(i));
            centers.remove((int) circleIndices.get(i));
            radii.remove((int) circleIndices.get(i));
        }
//            Circles.removeAll(temp_Circles);
//            Centers.removeAll(temp_Centers);
//            Radius.removeAll(temp_Radius);           
        circleIndices.clear();
    }
}
