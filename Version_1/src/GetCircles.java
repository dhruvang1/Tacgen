
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
    int j = 0;
    int r = 0;
    Screen screen = new Screen(r);

    public GetCircles() {}

    public void getCircle(MouseEvent e) throws NoninvertibleTransformException {
        float x = (float) 0.0;
        float y = (float) 0.0;
        Pair<Float, Float> temp1 = new Pair<>(x, y);
        Pair<Float, Float> temp2 = new Pair<>(x, y);
        Pair<Float, Float> temp3 = new Pair<>(x, y);
        Pair<Float, Float> center = new Pair<>(x, y);

        if (!firstPointCaptured) {
            selectedPoint1 = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            firstPointCaptured = true;
            temp1.setL((float) selectedPoint1.x);
            temp1.setR((float) selectedPoint1.y);
            selectedPoint2 = null;
            selectedPoint3 = null;
            circles.add(temp1);
            circleA = temp1;

        } else if (!secondPointCaptured) {
            selectedPoint2 = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            if (Screen.linesObject.getDistance(selectedPoint2.x, selectedPoint2.y, selectedPoint1.x, selectedPoint1.y) > 5) {
                temp2.setL((float) selectedPoint2.x);
                temp2.setR((float) selectedPoint2.y);
                circles.add(temp2);
                secondPointCaptured = true;
                circleB = temp2;
            }
        } else {
            selectedPoint3 = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            if ((Screen.linesObject.getDistance(selectedPoint2.x, selectedPoint2.y, selectedPoint3.x, selectedPoint3.y) > 5) && (Screen.linesObject.getDistance(selectedPoint3.x, selectedPoint3.y, selectedPoint1.x, selectedPoint1.y) > 5)
                    && !Screen.linesObject.isCollinear(selectedPoint1.x, selectedPoint1.y, selectedPoint2.x, selectedPoint2.y, selectedPoint3.x, selectedPoint3.y)) {
                temp3.setL((float) selectedPoint3.x);
                temp3.setR((float) selectedPoint3.y);
                circles.add(temp3);
                firstPointCaptured = secondPointCaptured = false;
                circleC = temp3;
                Circle a = new Circle();
                int[] b = a.getCenter((int) (float) circleA.getL(), (int) (float) circleA.getR(), (int) (float) circleB.getL(), (int) (float) circleB.getR(), (int) (float) circleC.getL(), (int) (float) circleC.getR());
                center.setL((float) (int) b[0]);
                center.setR((float) (int) b[1]);
                centers.add(center);
                radius = a.getRadius(center, circleC);
                radii.add(radius);
                colorArray.add(Screen.currentColor);
                fillArray.add(0);
                //System.out.println(radius);
                circles.clear();
            }
            screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
            Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
        }

    }

    public void deleteTemp() {
        firstPointCaptured = false;
        secondPointCaptured = false;
        circles.clear();
    }

    public void addIndices(MouseEvent e) throws NoninvertibleTransformException {
        Point point = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
        for (int i = 0; i < centers.size(); i++) {
            float dist = (float) (Math.pow((point.x - centers.get(i).getL()), 2) + Math.pow((point.y - centers.get(i).getR()), 2));
            if (dist < Math.pow(radii.get(i), 2)) {
                if (circleIndices.contains(i)) {
                    circleIndices.remove((Integer) i);
                } else {
                    circleIndices.add(i);
                }
            }
        }
    }

    public void addColorIndices(MouseEvent e) throws NoninvertibleTransformException {
        Point point = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
        for (int i = 0; i < centers.size(); i++) {
            float dist = (float) (Math.pow((point.x - centers.get(i).getL()), 2) + Math.pow((point.y - centers.get(i).getR()), 2));
            if (dist < Math.pow(radii.get(i), 2)) {
                if (fillArray.get(i) == 1 && colorArray.get(i) != Screen.currentColor) {
                    colorArray.set(i, Screen.currentColor);
                    fillArray.set(i, 1);
                } else {
                    colorArray.set(i, Screen.currentColor);
                    fillArray.set(i, (fillArray.get(i) + 1) % 2);
                }
            }
            fillArray.set(i, 0);  // to remove fill
        }
    }

    public void deleteIndices() {
        circleIndices.sort(null);
        for (int i = circleIndices.size() - 1; i >= 0; i--) {
            colorArray.remove((int) circleIndices.get(i));

            fillArray.remove((int) circleIndices.get(i));
            centers.remove((int) circleIndices.get(i));
            radii.remove((int) circleIndices.get(i));
        }
        circleIndices.clear();
    }
}
