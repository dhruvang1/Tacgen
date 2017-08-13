import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

public class GetLines {
    ArrayList<Pair<Integer,Integer>> lines;
    ArrayList<Integer> lineIndices;
    ArrayList<Color> colorArray;
    boolean firstPointCaptured; 
    Point startPoint;
    Point endPoint;
    int r =0;
    Screen screen = new Screen(r);
    int i=0;

    public GetLines() {
        lines = new ArrayList<>();
        lineIndices = new ArrayList<>();
        colorArray =new ArrayList<>();
        firstPointCaptured=false;
    }

    public boolean isCollinear(int x1, int y1, int x2, int y2, int x3, int y3) {
        return (y1 - y2) * (x1 - x3) == (y1 - y3) * (x1 - x2);
    }

    public float getDistance(int x1, int y1, int x2, int y2){
        return (float)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    public void getLine(MouseEvent e) throws NoninvertibleTransformException
    {	
        int x=0,y=0;
        Pair<Integer,Integer> point = new Pair<>(x, y);
        if (!firstPointCaptured)
        {
            startPoint = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            firstPointCaptured = true;
            point.setL(startPoint.x);
            point.setR(startPoint.y);
            endPoint = null;
            System.out.println("lines size : "+ lines.size());
            lines.add(point);
            colorArray.add(Screen.currentColor);
        }
        else
        {
            endPoint = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            if(getDistance(endPoint.x, endPoint.y, lines.get(lines.size()-1).getL() , lines.get(lines.size()-1).getR())>5){
                point.setL(endPoint.x);
                point.setR(endPoint.y);
                lines.add(point);
                firstPointCaptured = false;
                colorArray.add(Screen.currentColor);
            }
            screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
            Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
        }
    }

    public void deleteTemp(){
        firstPointCaptured = false;
        int n = lines.size();
        if(n%2==1){
            lines.remove(n-1);
            colorArray.remove(n-1);
        }
    }

    public void addIndices(MouseEvent e) throws NoninvertibleTransformException{
        for(int i = 0; i< lines.size()-1; i++){
            int x1 = lines.get(i).getL();
            int y1 = lines.get(i).getR();
            int x2 = lines.get(i+1).getL();
            int y2 = lines.get(i+1).getR();
            int p1 = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e).x;
            int p2 = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e).y;
            double d1 =Math.pow( Math.pow(x1-p1, 2)+Math.pow(y1-p2, 2) , 0.5);
            double d2 =Math.pow( Math.pow(x2-p1, 2)+Math.pow(y2-p2, 2) , 0.5);
            double d3 =Math.pow( Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2) , 0.5);
            double s = ((d1+d2+d3)/2.0);
            double area = Math.pow(s*(s-d1)*(s-d2)*(s-d3),0.5);
            double h = (2.0*area)/d3;
            if(h<4 && d1<d3 && d2<d3){
                if(lineIndices.contains(i)){
                    lineIndices.remove((Integer) i);
                    lineIndices.remove((Integer) (i+1));
                }
                else{
                    lineIndices.add(i);
                    lineIndices.add(i+1);
                }
            }
            i = i+1;
        }
    }

    public void addColorIndices(MouseEvent e) throws NoninvertibleTransformException{
        for(int i = 0; i< lines.size()-1; i++){
            int x1 = lines.get(i).getL();
            int y1 = lines.get(i).getR();
            int x2 = lines.get(i+1).getL();
            int y2 = lines.get(i+1).getR();
            int p1 = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e).x;
            int p2 = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e).y;
            double d1 =Math.pow( Math.pow(x1-p1, 2)+Math.pow(y1-p2, 2) , 0.5);
            double d2 =Math.pow( Math.pow(x2-p1, 2)+Math.pow(y2-p2, 2) , 0.5);
            double d3 =Math.pow( Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2) , 0.5);
            double s = ((d1+d2+d3)/2.0);
            double area = Math.pow(s*(s-d1)*(s-d2)*(s-d3),0.5);
            double h = (2.0*area)/d3;
            if(h<4 && d1<d3 && d2<d3){
                colorArray.set(i, Screen.currentColor);
                colorArray.set(i+1, Screen.currentColor);
            }
            i = i+1;
        }
    }

    public void deleteLineIndices(){
//            ArrayList<Pair<Integer,Integer>> temp_Lines=new ArrayList<Pair<Integer,Integer>>();
        lineIndices.sort(null);
        for (int i = lineIndices.size()-1; i>=0; i--){
            //temp_Lines.add(Lines.get(line_indices.get(i)));
            lines.remove((int) lineIndices.get(i));
            colorArray.remove((int) lineIndices.get(i));
        }
        //Lines.removeAll(temp_Lines);
        lineIndices.clear();
    }
}
