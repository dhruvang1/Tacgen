import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

/**
 * Class to create circles
 */
public class GetCircles {
    class Circle{
        Pair<Float, Float> center;
        Float radius;
        Color color;
        Integer fill;
    }

    public ArrayList<Circle> allCircles = new ArrayList<>();
    private boolean firstPointCaptured = false;
    private boolean secondPointCaptured = false;
    private Point selectedPoint1, selectedPoint2, selectedPoint3;
    public ArrayList<Pair<Float, Float>> tempPoints = new ArrayList<>();
    public ArrayList<Integer> circleIndices = new ArrayList<>();
//    public ArrayList<Pair<Float, Float>> circles = new ArrayList<>();
//    public ArrayList<Float> radii = new ArrayList<>();
//    public ArrayList<Integer> fillArray = new ArrayList<>();
//    public ArrayList<Color> colorArray = new ArrayList<>();

//    private Pair<Float, Float> circleA, circleB, circleC;
    private Screen screen = new Screen(0);

    public GetCircles() {}

    public void getCircle(MouseEvent e) throws NoninvertibleTransformException {
        float x = (float) 0.0;
        float y = (float) 0.0;
        Pair<Float, Float> point1 = new Pair<>(x, y);
        Pair<Float, Float> point2 = new Pair<>(x, y);
        Pair<Float, Float> point3 = new Pair<>(x, y);
        Pair<Float, Float> center = new Pair<>(x, y);

        if (!firstPointCaptured) {
            selectedPoint1 = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            firstPointCaptured = true;
            point1.setL((float) selectedPoint1.x);
            point1.setR((float) selectedPoint1.y);
            selectedPoint2 = null;
            selectedPoint3 = null;
            tempPoints.add(point1);
//            circles.add(point1);
//            circleA = point1;
        } else if (!secondPointCaptured) {
            selectedPoint2 = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            if (Screen.linesObject.getDistance(selectedPoint2.x, selectedPoint2.y, selectedPoint1.x, selectedPoint1.y) > 5) {
                point2.setL((float) selectedPoint2.x);
                point2.setR((float) selectedPoint2.y);
                secondPointCaptured = true;
                tempPoints.add(point2);
//                circles.add(point2);
//                circleB = point2;
            }
        } else {
            selectedPoint3 = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            if ((Screen.linesObject.getDistance(selectedPoint2.x, selectedPoint2.y, selectedPoint3.x, selectedPoint3.y) > 5) && (Screen.linesObject.getDistance(selectedPoint3.x, selectedPoint3.y, selectedPoint1.x, selectedPoint1.y) > 5)
                    && !Screen.linesObject.isCollinear(selectedPoint1.x, selectedPoint1.y, selectedPoint2.x, selectedPoint2.y, selectedPoint3.x, selectedPoint3.y)) {
                point3.setL((float) selectedPoint3.x);
                point3.setR((float) selectedPoint3.y);
                firstPointCaptured = secondPointCaptured = false;
                tempPoints.add(point3);
//                circles.add(point3);
//                circleC = point3;
                CircleHelper helper = new CircleHelper();
                int[] tempCenter = helper.getCenter((int) (float) tempPoints.get(0).getL(), (int) (float) tempPoints.get(0).getR(),
                                      (int) (float) tempPoints.get(1).getL(), (int) (float) tempPoints.get(1).getR(),
                                      (int) (float) tempPoints.get(2).getL(), (int) (float) tempPoints.get(2).getR());
//                float radius = helper.getRadius(center, circleC);
                center.setL((float) (int) tempCenter[0]);
                center.setR((float) (int) tempCenter[1]);
                float radius = helper.getRadius(center, tempPoints.get(2));
//                centers.add(center);
//                radii.add(radius);
//                colorArray.add(Screen.currentColor);
//                fillArray.add(0);
                //System.out.println(radius);
//                circles.clear();
                Circle currentCircle = new Circle();
                currentCircle.center = center;
                currentCircle.radius = radius;
                currentCircle.color = Screen.currentColor;
                currentCircle.fill = 0;
                tempPoints.clear();
                allCircles.add(currentCircle);
            }
            screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
            Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
        }

    }

    public void deleteTemp() {
        firstPointCaptured = false;
        secondPointCaptured = false;
//        circles.clear();
        tempPoints.clear();
    }

    public void addIndices(MouseEvent e) throws NoninvertibleTransformException {
        Point point = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
//        for (int i = 0; i < centers.size(); i++) {
//            float dist = (float) (Math.pow((point.x - centers.get(i).getL()), 2) + Math.pow((point.y - centers.get(i).getR()), 2));
//            if (dist < Math.pow(radii.get(i), 2)) {
//                if (circleIndices.contains(i)) {
//                    circleIndices.remove((Integer) i);
//                } else {
//                    circleIndices.add(i);
//                }
//            }
//        }

        for (int i = 0; i < allCircles.size(); i++) {
            float dist = (float) (Math.pow((point.x - allCircles.get(i).center.getL()), 2) + Math.pow((point.y - allCircles.get(i).center.getR()), 2));
            if (dist < Math.pow(allCircles.get(i).radius, 2)) {
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
//        for (int i = 0; i < centers.size(); i++) {
//            float dist = (float) (Math.pow((point.x - centers.get(i).getL()), 2) + Math.pow((point.y - centers.get(i).getR()), 2));
//            if (dist < Math.pow(radii.get(i), 2)) {
//                if (fillArray.get(i) == 1 && colorArray.get(i) != Screen.currentColor) {
//                    colorArray.set(i, Screen.currentColor);
//                    fillArray.set(i, 1);
//                } else {
//                    colorArray.set(i, Screen.currentColor);
//                    fillArray.set(i, (fillArray.get(i) + 1) % 2);
//                }
//            }
//            fillArray.set(i, 0);  // to remove fill
//        }

        for (int i = 0; i < allCircles.size(); i++) {   //doubt
            Circle tempCircle = allCircles.get(i);
            float dist = (float) (Math.pow((point.x - tempCircle.center.getL()), 2) + Math.pow((point.y - tempCircle.center.getR()), 2));
            if (dist < Math.pow(tempCircle.radius, 2)) {
                if (tempCircle.fill == 1 && tempCircle.color != Screen.currentColor) {
                    tempCircle.color = Screen.currentColor;
                    tempCircle.fill = 1;
                } else {
                    tempCircle.color = Screen.currentColor;
                    tempCircle.fill = (tempCircle.fill + 1)%2;
                }
            }
            tempCircle.fill = 0;  // to remove fill
        }
    }

    public void deleteIndices() {
        circleIndices.sort(null);
        for (int i = circleIndices.size() - 1; i >= 0; i--) {
//            colorArray.remove((int) circleIndices.get(i));
//
//            fillArray.remove((int) circleIndices.get(i));
//            centers.remove((int) circleIndices.get(i));
//            radii.remove((int) circleIndices.get(i));
            allCircles.remove((int) circleIndices.get(i));
        }
        circleIndices.clear();
    }
}
