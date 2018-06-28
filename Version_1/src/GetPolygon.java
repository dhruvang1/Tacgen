import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

/**
 * Class to create polygons
 */
public class GetPolygon {
    class Polygon {
        public ArrayList<Pair<Integer,Integer>> points = new ArrayList<>();
        public Integer fill;
        public Color color;
    }

    private int x = 0; private int y = 0;
//    public ArrayList<ArrayList<Pair<Integer,Integer>>> polygons = new ArrayList<>();
//    public ArrayList<Pair<Integer,Integer>> points = new ArrayList<>();
//    public ArrayList<Integer> fillOrNot = new ArrayList<>();
//    public ArrayList<Color> colorArray = new ArrayList<>();
    public ArrayList<Polygon> allPolygons = new ArrayList<>();
    public ArrayList<Integer> polygonIndices = new ArrayList<>();
    boolean firstPointCaptured = false;
    private Pair<Integer,Integer> startPointPair = new Pair<>(x, y);
    private Screen screen = new Screen(0);
    public Polygon currentPolygon = new Polygon();

    public GetPolygon() {}

    public void getPolygons(MouseEvent e) throws NoninvertibleTransformException
    {
        Pair<Integer,Integer> tempPoint = new Pair<>(x, y);
        if (Screen.allControlsAndListeners.polygonStart.isSelected() && !firstPointCaptured)
        {
            Point startPoint = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            firstPointCaptured = true;
            tempPoint.setL(startPoint.x);
            tempPoint.setR(startPoint.y);
//            points = new ArrayList<>();
//            points.add(tempPoint);
            currentPolygon.points.add(tempPoint);
            startPointPair = tempPoint;
            Screen.allControlsAndListeners.polygonStart.setSelected(false);
            Screen.allControlsAndListeners.polygonStart.setEnabled(false);
            Screen.allControlsAndListeners.polygonEnd.setEnabled(true);
        }
        else
        {
            if(Screen.allControlsAndListeners.polygonEnd.isSelected()){
                firstPointCaptured = false;
//                if(points.size() > 2){
//                    points.add(startPointPair);
//                    ArrayList<Pair<Integer,Integer>> tempPoints = new ArrayList<>();
//                    for(int g = 0; g< points.size(); g++){
//                        int y1 = points.get(g).getL();
//                        int y2 = points.get(g).getR();
//                        tempPoints.add(new Pair<>(y1,y2));
//                    }
//                    polygons.add(tempPoints);
//                    fillOrNot.add(0);
//                    colorArray.add(Screen.currentColor);
//                }
//                points = null;
                if(currentPolygon.points.size() > 2){
                    currentPolygon.points.add(startPointPair);
                    currentPolygon.fill = 0;
                    currentPolygon.color = Screen.currentColor;
                    allPolygons.add(currentPolygon);
                }
                currentPolygon = new Polygon();
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
                Screen.allControlsAndListeners.polygonEnd.setSelected(false);
                Screen.allControlsAndListeners.polygonEnd.setEnabled(false);
                Screen.allControlsAndListeners.polygonStart.setEnabled(true);
                //Points.clear();
                //	System.out.println("size == "+Polygons.get(0).size());
            }
            if(firstPointCaptured){
                Point nextPoint = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
                tempPoint.setL(nextPoint.x);
                tempPoint.setR(nextPoint.y);
//                points.add(tempPoint);
                currentPolygon.points.add(tempPoint);
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
            }
        }
    }

    public void deleteTemp(){
        firstPointCaptured = false;
//        points = null;
        currentPolygon = new Polygon();
        Screen.allControlsAndListeners.deselectPolygonRadioButtons();
    }

    public void addIndices(MouseEvent e) throws NoninvertibleTransformException{
        Point p = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
//        for(int i = 0; i< polygons.size(); i++){
//            Point [] p1 = new Point[polygons.get(i).size()-1];
//            for(int y=0;y<p1.length;y++){
//                p1[y] = new Point(polygons.get(i).get(y).getL(), polygons.get(i).get(y).getR());
//            }
        for(int i = 0; i< allPolygons.size(); i++){
            Polygon tempPolygon = allPolygons.get(i);
            Point [] pointArray = new Point[tempPolygon.points.size()-1];
            for(int j=0; j<pointArray.length; j++){
                pointArray[j] = new Point(tempPolygon.points.get(j).getL(), tempPolygon.points.get(j).getR());
            }
            if(Screen.whichPolygonObject.isInside(pointArray, pointArray.length, p)){
                if(polygonIndices.contains(i)){
                    polygonIndices.remove((Integer)i);
                }
                else{
                    polygonIndices.add(i);
                }
            }
        }
    }

    public void addColorIndices(MouseEvent e) throws NoninvertibleTransformException{
        Point p= Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
        //    p.y=-1*p.y;
//        for(int i = 0; i< polygons.size(); i++){
//            Point [] p1 = new Point[polygons.get(i).size()-1];
//            for(int y=0;y<p1.length;y++){
//                p1[y] = new Point(polygons.get(i).get(y).getL(), polygons.get(i).get(y).getR());
//            }
//            if(Screen.whichPolygonObject.isInside(p1, p1.length, p)){
//                if(fillOrNot.get(i)==1&& colorArray.get(i)!= Screen.currentColor){
//                    colorArray.set(i, Screen.currentColor);
//                    fillOrNot.set(i,1);
//                }
//                else{
//                    colorArray.set(i, Screen.currentColor);
//                    fillOrNot.set(i,(fillOrNot.get(i)+1)%2);
//                }
//            }
//            fillOrNot.set(i, 0);  // to remove fill
//        }
        for(int i = 0; i< allPolygons.size(); i++){
            Polygon tempPolygon = allPolygons.get(i);
            Point [] pointArray = new Point[tempPolygon.points.size()-1];
            for(int j=0;j<pointArray.length;j++){
                pointArray[j] = new Point(tempPolygon.points.get(j).getL(), tempPolygon.points.get(j).getR());
            }
            if(Screen.whichPolygonObject.isInside(pointArray, pointArray.length, p)){
                if(tempPolygon.fill==1 && tempPolygon.color!= Screen.currentColor){
                    tempPolygon.color = Screen.currentColor;
                    tempPolygon.fill = 1;
                }
                else{
                    tempPolygon.color = Screen.currentColor;
                    tempPolygon.fill = (tempPolygon.fill + 1)%2;
                }
            }
            tempPolygon.fill = 0;  // to remove fill
        }
    }

    public void deleteIndices(){
        // ArrayList<ArrayList<Pair<Integer,Integer>>> temp_Polygons=new ArrayList<ArrayList<Pair<Integer,Integer>>>();
        polygonIndices.sort(null);
        for(int i = polygonIndices.size()-1; i>=0; i--){
//            polygons.remove((int) polygonIndices.get(i));
//            colorArray.remove((int) polygonIndices.get(i));
//            fillOrNot.remove((int) polygonIndices.get(i));
            allPolygons.remove((int) polygonIndices.get(i));
        }
//          Polygons.removeAll(temp_Polygons);
        polygonIndices.clear();
    }
}
