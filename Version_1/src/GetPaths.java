import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

/**
 * Class to create paths
 */
public class GetPaths {
    public ArrayList<ArrayList<Pair<Integer,Integer>>> paths = new ArrayList<>();
    public ArrayList<Pair<Integer,Integer>> pathPoints = new ArrayList<>();
    public ArrayList<Color> colorArray = new ArrayList<>();
    public ArrayList<Integer> pathIndices = new ArrayList<>();
    private Screen screen = new Screen(0);
//    boolean firstPointCaptured = false;
    
    public void getPath(MouseEvent e) throws NoninvertibleTransformException{
        Pair<Integer,Integer> temp = new Pair<>(Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e).x, Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e).y);
        pathPoints.add(temp);
        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
    }

    public void updateRegions(){ //try removing tempRegionPoints
        if(pathPoints.size()>5){
            ArrayList<Pair<Integer,Integer>> tempRegionPoints =new ArrayList<>();
            for(int i = 0; i< pathPoints.size(); i++){
                int y1 = pathPoints.get(i).getL();
                int y2 = pathPoints.get(i).getR();
                Pair<Integer,Integer> temp_pair = new Pair<>(y1,y2);
                tempRegionPoints.add(temp_pair);
            }
            int y1 = pathPoints.get(0).getL();
            int y2 = pathPoints.get(0).getR();
            Pair<Integer,Integer> temp_pair = new Pair<>(y1,y2);
            tempRegionPoints.add(temp_pair);
            paths.add(tempRegionPoints);
            colorArray.add(Screen.currentColor);
//            regionPoints.clear();
        }
        pathPoints.clear();
    }
    
    public void addIndices(MouseEvent e) throws NoninvertibleTransformException{
            Point originalZoomedCoordinate = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            int regionsMaxSize = 50000;
        //    originalZoomedCoordinate.y=-1*originalZoomedCoordinate.y;
            for(int i = 0; i< paths.size(); i++){
                if(paths.get(i).size()<= regionsMaxSize +1){
                    Point [] points = new Point[paths.get(i).size()-1];
                    for(int y=0;y<points.length;y++){
                        if(paths.get(i).get(y).getR()==originalZoomedCoordinate.y){points[y] = new Point(paths.get(i).get(y).getL(), paths.get(i).get(y).getR()+1);}
                        else{
                            points[y] = new Point(paths.get(i).get(y).getL(), paths.get(i).get(y).getR());
                        }
                    }
                    if(Screen.whichPolygonObject.isInside(points, points.length, originalZoomedCoordinate)){
                        if(pathIndices.contains(i)){
                            pathIndices.remove((Integer)i);
                        }
                        else{
                            pathIndices.add(i);
                        }
                    }
                }
                else{
                    Point [] points = new Point[regionsMaxSize +1];
                    int au = (paths.get(i).size()-1)/ regionsMaxSize; //au, hg??
                    int hg =0;
                    
                    for(int y = 0; y< regionsMaxSize; y++){
                        hg = hg+au;
                        
                        if(paths.get(i).get(hg).getR()==originalZoomedCoordinate.y){
                            points[y] = new Point(paths.get(i).get(hg).getL(), paths.get(i).get(hg).getR()+1);
                        }
                        else{
                            points[y] = new Point(paths.get(i).get(hg).getL(), paths.get(i).get(hg).getR());
                        }
                         
                    }
                //    System.out.println(hg);
                    int index = paths.get(i).size()-2;
                    points[regionsMaxSize]=new Point(paths.get(i).get(index).getL(), paths.get(i).get(index).getR());
                    if(Screen.whichPolygonObject.isInside(points, points.length, originalZoomedCoordinate)){
                        if(pathIndices.contains(i)){
                            pathIndices.remove((Integer)i);
                        }
                        else{
                            pathIndices.add(i);
                        }
                    }
                }
                
            }
      }
      
    public void addColorIndices(MouseEvent e) throws NoninvertibleTransformException{
            Point originalZoomedCoordinate = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            int regionMaxSize = 50000;
        //    originalZoomedCoordinate.y=-1*originalZoomedCoordinate.y;
            for(int i = 0; i< paths.size(); i++){
                if(paths.get(i).size()<= regionMaxSize +1){
                    Point [] points = new Point[paths.get(i).size()-1];
                    for(int y=0;y<points.length;y++){
                        if(paths.get(i).get(y).getR()==originalZoomedCoordinate.y){points[y] = new Point(paths.get(i).get(y).getL(), paths.get(i).get(y).getR()+1);}
                        else{
                            points[y] = new Point(paths.get(i).get(y).getL(), paths.get(i).get(y).getR());
                        }
                    }
                    if(Screen.whichPolygonObject.isInside(points, points.length, originalZoomedCoordinate)){
                        colorArray.set(i, Screen.currentColor);
                    }
                }
                else{
                    Point [] points = new Point[regionMaxSize +1];
                    int au = (paths.get(i).size()-1)/ regionMaxSize;
                    int hg =0;
                    
                    for(int y = 0; y< regionMaxSize; y++){
                        hg = hg+au;
                        
                        if(paths.get(i).get(hg).getR()==originalZoomedCoordinate.y){
                            points[y] = new Point(paths.get(i).get(hg).getL(), paths.get(i).get(hg).getR()+1);
                        }
                        else{
                            points[y] = new Point(paths.get(i).get(hg).getL(), paths.get(i).get(hg).getR());
                        }
                         
                    }
                    int index = paths.get(i).size()-2;
                    points[regionMaxSize] = new Point(paths.get(i).get(index).getL(), paths.get(i).get(index).getR());
                    if(Screen.whichPolygonObject.isInside(points, points.length, originalZoomedCoordinate)){
                        colorArray.set(i, Screen.currentColor);
                    }
                }
            }
      }
        
    public void deleteIndices(){
          //ArrayList<ArrayList<Pair<Integer,Integer>>> temp_Regions=new ArrayList<ArrayList<Pair<Integer,Integer>>>();
          pathIndices.sort(null);
          for(int i = pathIndices.size()-1; i>=0; i--){
              paths.remove((int) pathIndices.get(i));
              colorArray.remove((int) pathIndices.get(i));
          }
          //Regions.removeAll(temp_Regions);
          pathIndices.clear();
      }    
    
}
