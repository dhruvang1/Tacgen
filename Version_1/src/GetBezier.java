import org.opencv.core.Point;

import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

/**
 * Class to create Bezier curves
 */
public class GetBezier {
    public class Bezier{
        public Point startPoint;
        public ArrayList<Point> firstControlPoints;
        public ArrayList<Point> secondControlPoints;
        public ArrayList<Point> endPoints;

        public Bezier(){
            firstControlPoints = new ArrayList<>();
            secondControlPoints = new ArrayList<>();
            endPoints = new ArrayList<>();
        }
    }
    private Screen screen = new Screen(0);
    public ArrayList<Bezier> allBeziers = new ArrayList<>();
    public Bezier currentBezier = new Bezier();
    public int clickCount = 0;
    public boolean firstPointCaptured = false;

    public GetBezier(){}

    public void getBeziers(MouseEvent e) throws NoninvertibleTransformException{
        if(Screen.allControlsAndListeners.bezierStart.isSelected() & !firstPointCaptured){
            java.awt.Point currentPoint = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            currentBezier.startPoint = new Point(currentPoint.x,currentPoint.y);
            firstPointCaptured = true;
            Screen.allControlsAndListeners.bezierStart.setSelected(false);
            Screen.allControlsAndListeners.bezierStart.setEnabled(false);
            Screen.allControlsAndListeners.bezierEnd.setEnabled(true);
        }
        else{
            if(Screen.allControlsAndListeners.bezierEnd.isSelected()){
                firstPointCaptured = false;
                int endPointsSize = currentBezier.endPoints.size();
                int controlPointsSize = currentBezier.firstControlPoints.size();
                if (endPointsSize > controlPointsSize) {  //last tangent click is not done
                    currentBezier.endPoints.remove(endPointsSize - 1);
                }
                if (controlPointsSize > 0) {
                    allBeziers.add(currentBezier);
                }
                currentBezier = new Bezier();
                clickCount = 0;
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
                Screen.allControlsAndListeners.bezierEnd.setSelected(false);
                Screen.allControlsAndListeners.bezierEnd.setEnabled(false);
                Screen.allControlsAndListeners.bezierStart.setEnabled(true);

                System.out.println("Done this step");
            }
            if(firstPointCaptured){
                java.awt.Point currentPoint = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
                Point tempPoint = new Point(currentPoint.x,currentPoint.y);
                if(clickCount % 2 == 0){       //clickCount even
                    clickCount++;
                    currentBezier.endPoints.add(tempPoint);
                }
                else{           //clickCount odd
                    clickCount++;
                    CalculateControlPoints(currentBezier,tempPoint);

                }
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
            }
        }


//        else if(Screen.allControlsAndListeners.bezierEnd.isSelected()){
//            Screen.allControlsAndListeners.bezierEnd.setSelected(false);
//            Screen.allControlsAndListeners.bezierEnd.setEnabled(false);
//            Screen.allControlsAndListeners.bezierStart.setEnabled(true);
//            int endPointsSize = currentBezier.endPoints.size();
//            int controlPointsSize = currentBezier.firstControlPoints.size();
//            if (endPointsSize > controlPointsSize) {  //last tangent click is not done
//                currentBezier.endPoints.remove(endPointsSize - 1);
//            }
//            if (controlPointsSize > 0) {
//                allBeziers.add(currentBezier);
//            }
//            currentBezier = new Bezier();
//            clickCount = 0;
//            screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
//            Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
//
//            System.out.println("Done this step");
//        }
//        else{
//            java.awt.Point currentPoint = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
//            Point tempPoint = new Point(currentPoint.x,currentPoint.y);
//            if(clickCount % 2 == 0){       //clickCount even
//                clickCount++;
//                currentBezier.endPoints.add(tempPoint);
//            }
//            else{           //clickCount odd
//                clickCount++;
//                CalculateControlPoints(currentBezier,tempPoint);
//
//            }
//            screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
//            Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
//        }
    }

    public void CalculateControlPoints(Bezier bezier, Point tempPoint){
        Point startPoint,endPoint, control1 , control2;
        int size = currentBezier.endPoints.size();
        if(size == 1){
            startPoint = currentBezier.startPoint;
            control1 = new Point(startPoint.x,startPoint.y);
        }else{
            startPoint = currentBezier.endPoints.get(size - 2);
            Point prevControl2 = bezier.secondControlPoints.get(size - 2);
            control1 = new Point(2*startPoint.x - prevControl2.x,2*startPoint.y - prevControl2.y);
        }
        endPoint = currentBezier.endPoints.get(size - 1);
        control2 = new Point(endPoint.x - (tempPoint.x - endPoint.x)/3,endPoint.y - (tempPoint.y - endPoint.y)/3);

        bezier.firstControlPoints.add(control1);
        bezier.secondControlPoints.add(control2);
    }

    public void deleteTemp(){
        currentBezier = new Bezier();
        firstPointCaptured = false;
        clickCount = 0;
        Screen.allControlsAndListeners.deselectBezierRadioButtons();
    }

}
