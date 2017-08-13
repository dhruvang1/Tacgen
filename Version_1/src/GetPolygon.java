import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;


public class GetPolygon {
    int x =0;int y=0;
    ArrayList<ArrayList<Pair<Integer,Integer>>> polygons = new ArrayList<>();
    ArrayList<Pair<Integer,Integer>> points = new ArrayList<>();
    ArrayList<Integer> polygonIndices = new ArrayList<>();
    ArrayList<Integer> fillOrNot = new ArrayList<>();
    ArrayList<Color> colorArray = new ArrayList<>();
    boolean firstPointCaptured = false;
    //	boolean end_selected = false;
    Point startPoint;
    Point nextPoint;
    Pair<Integer,Integer> startPointPair = new Pair<>(x, y);
    int r =0;
    Screen screen = new Screen(r);
    int i=0;

    public GetPolygon() {}

    public void getPolygons(MouseEvent e) throws NoninvertibleTransformException
    {
        Pair<Integer,Integer> temp = new Pair<>(x, y);
        if (Screen.a1.polygonStart.isSelected()&&!firstPointCaptured)
        {
            startPoint = Screen.a1.getOriginalZoomedCoordinate(e);
            firstPointCaptured = true;
            temp.setL(startPoint.x);
            temp.setR(startPoint.y);
            nextPoint = null;
            points = new ArrayList<>();
            points.add(temp);
            startPointPair =temp;
            Screen.a1.polygonStart.setSelected(false);
            Screen.a1.polygonStart.setEnabled(false);
            Screen.a1.polygonEnd.setEnabled(true);
        }
        else
        {
            if(Screen.a1.polygonEnd.isSelected()){
                firstPointCaptured = false;
                if(points.size()>2){
                    points.add(startPointPair);
                    ArrayList<Pair<Integer,Integer>> tempPoints = new ArrayList<>();
                    for(int g = 0; g< points.size(); g++){
                        int y1 = points.get(g).getL();
                        int y2 = points.get(g).getR();
                        tempPoints.add(new Pair<>(y1,y2));
                    }
                    polygons.add(tempPoints);
                    fillOrNot.add(0);
                    colorArray.add(Screen.current_color);
                }
                points = null;
                screen.repaint(Screen.screen, Screen.a2.screenCopy);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
                Screen.a1.polygonEnd.setSelected(false);
                Screen.a1.polygonEnd.setEnabled(false);
                Screen.a1.polygonStart.setEnabled(true);
                //Points.clear();
                //	System.out.println("size == "+Polygons.get(0).size());
            }
            if(firstPointCaptured){
                nextPoint = Screen.a1.getOriginalZoomedCoordinate(e);
                temp.setL(nextPoint.x);
                temp.setR(nextPoint.y);
                points.add(temp);
                screen.repaint(Screen.screen, Screen.a2.screenCopy);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
            }
        }
    }

    public void deleteTemp(){
        firstPointCaptured = false;
        points = null;
        Screen.a1.deselectRadioButtons();
    }

    public void addIndices(MouseEvent e) throws NoninvertibleTransformException{
        Point p= Screen.a1.getOriginalZoomedCoordinate(e);
        //    p.y=-1*p.y;
        for(int i = 0; i< polygons.size(); i++){
            Point [] p1 = new Point[polygons.get(i).size()-1];
            for(int y=0;y<p1.length;y++){
                p1[y] = new Point(polygons.get(i).get(y).getL(), polygons.get(i).get(y).getR());
            }
            if(Screen.a10.isInside(p1, p1.length, p)){
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
        Point p= Screen.a1.getOriginalZoomedCoordinate(e);
        //    p.y=-1*p.y;
        for(int i = 0; i< polygons.size(); i++){
            Point [] p1 = new Point[polygons.get(i).size()-1];
            for(int y=0;y<p1.length;y++){
                p1[y] = new Point(polygons.get(i).get(y).getL(), polygons.get(i).get(y).getR());
            }
            if(Screen.a10.isInside(p1, p1.length, p)){
                if(fillOrNot.get(i)==1&& colorArray.get(i)!= Screen.current_color){
                    colorArray.set(i, Screen.current_color);
                    fillOrNot.set(i,1);
                }
                else{
                    colorArray.set(i, Screen.current_color);
                    fillOrNot.set(i,(fillOrNot.get(i)+1)%2);
                }
            }
            fillOrNot.set(i, 0);  // to remove fill
        }
    }

    public void deleteIndices(){
        // ArrayList<ArrayList<Pair<Integer,Integer>>> temp_Polygons=new ArrayList<ArrayList<Pair<Integer,Integer>>>();
        polygonIndices.sort(null);
        for(int i = polygonIndices.size()-1; i>=0; i--){
            polygons.remove((int) polygonIndices.get(i));
            colorArray.remove((int) polygonIndices.get(i));
            fillOrNot.remove((int) polygonIndices.get(i));
        }
//          Polygons.removeAll(temp_Polygons);
        polygonIndices.clear();
    }
}
