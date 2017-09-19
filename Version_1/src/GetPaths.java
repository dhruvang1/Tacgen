import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

/**
 * Class to create paths
 */
public class GetPaths {
    public class Path {
        public ArrayList<Pair<Integer,Integer>> points = new ArrayList<>();
        public Color color;
    }

//    public ArrayList<ArrayList<Pair<Integer,Integer>>> paths = new ArrayList<>();
//    public ArrayList<Pair<Integer,Integer>> pathPoints = new ArrayList<>();
//    public ArrayList<Color> colorArray = new ArrayList<>();
    public ArrayList<Path> allPaths = new ArrayList<>();
    public Path currentPath = new Path();
    public ArrayList<Integer> pathIndices = new ArrayList<>();
    private Screen screen = new Screen(0);
//    boolean firstPointCaptured = false;
    
    public void getPath(MouseEvent e) throws NoninvertibleTransformException{
        Pair<Integer,Integer> tempPoint = new Pair<>(Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e).x, Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e).y);
//        pathPoints.add(temp);
        currentPath.points.add(tempPoint);
        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
    }

    public void updateRegions(){ //try removing tempRegionPoints
//        if(pathPoints.size()>5){
//            ArrayList<Pair<Integer,Integer>> tempRegionPoints =new ArrayList<>();
//            for(int i = 0; i< pathPoints.size(); i++){
//                int y1 = pathPoints.get(i).getL();
//                int y2 = pathPoints.get(i).getR();
//                Pair<Integer,Integer> temp_pair = new Pair<>(y1,y2);
//                tempRegionPoints.add(temp_pair);
//            }
//            int y1 = pathPoints.get(0).getL();
//            int y2 = pathPoints.get(0).getR();
//            Pair<Integer,Integer> temp_pair = new Pair<>(y1,y2);
//            tempRegionPoints.add(temp_pair);
//            paths.add(tempRegionPoints);
//            colorArray.add(Screen.currentColor);
////            regionPoints.clear();
//        }
//        pathPoints.clear();
        if(currentPath.points.size()>5){
            int y1 = currentPath.points.get(0).getL();
            int y2 = currentPath.points.get(0).getR();
            Pair<Integer,Integer> temp_pair = new Pair<>(y1,y2);
            currentPath.points.add(temp_pair);
            currentPath.color = Screen.currentColor;
            allPaths.add(currentPath);
        }
        currentPath = new Path();
    }
    
    public void addIndices(MouseEvent e) throws NoninvertibleTransformException{
            Point originalZoomedCoordinate = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            int regionsMaxSize = 50000;
//            for(int i = 0; i< paths.size(); i++){
//                if(paths.get(i).size()<= regionsMaxSize +1){
//                    Point [] points = new Point[paths.get(i).size()-1];
//                    for(int y=0;y<points.length;y++){
//                        if(paths.get(i).get(y).getR()==originalZoomedCoordinate.y){points[y] = new Point(paths.get(i).get(y).getL(), paths.get(i).get(y).getR()+1);}
//                        else{
//                            points[y] = new Point(paths.get(i).get(y).getL(), paths.get(i).get(y).getR());
//                        }
//                    }
            for(int i = 0; i< allPaths.size(); i++){
                Path tempPath = allPaths.get(i);
//                System.out.println(tempPath.points.size());
                if(tempPath.points.size()<= regionsMaxSize +1){
                    Point [] points = new Point[tempPath.points.size()-1];
                    for(int y=0;y<points.length;y++){
                        if(tempPath.points.get(y).getR()==originalZoomedCoordinate.y){
                            points[y] = new Point(tempPath.points.get(y).getL(), tempPath.points.get(y).getR()+1);
                        }
                        else{
                            points[y] = new Point(tempPath.points.get(y).getL(), tempPath.points.get(y).getR());
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
                    int skipIndex = (tempPath.points.size()-1)/ regionsMaxSize; //au, hg??
                    int currentIndex = 0;
                    
                    for(int y = 0; y < regionsMaxSize; y++){
                        currentIndex = currentIndex + skipIndex;
                        if(tempPath.points.get(currentIndex).getR()==originalZoomedCoordinate.y){
//                            points[y] = new Point(paths.get(i).get(currentIndex).getL(), paths.get(i).get(currentIndex).getR()+1);
                            points[y] = new Point(tempPath.points.get(currentIndex).getL(), tempPath.points.get(currentIndex).getR()+1);
                        }
                        else{
//                            points[y] = new Point(paths.get(i).get(currentIndex).getL(), paths.get(i).get(currentIndex).getR());
                            points[y] = new Point(tempPath.points.get(currentIndex).getL(), tempPath.points.get(currentIndex).getR());
                        }
                         
                    }
                //    System.out.println(hg);
//                    int index = paths.get(i).size()-2;
//                    points[regionsMaxSize]=new Point(paths.get(i).get(index).getL(), paths.get(i).get(index).getR());
                    int index = tempPath.points.size()-2;
                    points[regionsMaxSize]=new Point(tempPath.points.get(index).getL(), tempPath.points.get(index).getR());
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
//            for(int i = 0; i< paths.size(); i++){
//                if(paths.get(i).size()<= regionMaxSize +1){
//                    Point [] points = new Point[paths.get(i).size()-1];
//                    for(int y=0;y<points.length;y++){
//                        if(paths.get(i).get(y).getR()==originalZoomedCoordinate.y){points[y] = new Point(paths.get(i).get(y).getL(), paths.get(i).get(y).getR()+1);}
//                        else{
//                            points[y] = new Point(paths.get(i).get(y).getL(), paths.get(i).get(y).getR());
//                        }
//                    }
//                    if(Screen.whichPolygonObject.isInside(points, points.length, originalZoomedCoordinate)){
//                        colorArray.set(i, Screen.currentColor);
//                    }
//                }
//                else{
//                    Point [] points = new Point[regionMaxSize +1];
//                    int skipIndex = (paths.get(i).size()-1)/ regionMaxSize;
//                    int currentIndex =0;
//
//                    for(int y = 0; y< regionMaxSize; y++){
//                        currentIndex = currentIndex+skipIndex;
//
//                        if(paths.get(i).get(currentIndex).getR()==originalZoomedCoordinate.y){
//                            points[y] = new Point(paths.get(i).get(currentIndex).getL(), paths.get(i).get(currentIndex).getR()+1);
//                        }
//                        else{
//                            points[y] = new Point(paths.get(i).get(currentIndex).getL(), paths.get(i).get(currentIndex).getR());
//                        }
//
//                    }
//                    int index = paths.get(i).size()-2;
//                    points[regionMaxSize] = new Point(paths.get(i).get(index).getL(), paths.get(i).get(index).getR());
//                    if(Screen.whichPolygonObject.isInside(points, points.length, originalZoomedCoordinate)){
//                        colorArray.set(i, Screen.currentColor);
//                    }
//                }
//            }
        for(int i = 0; i< allPaths.size(); i++){
            Path tempPath = allPaths.get(i);
            if(tempPath.points.size()<= regionMaxSize +1){
                Point [] points = new Point[tempPath.points.size()-1];
                for(int y=0;y<points.length;y++){
                    if(tempPath.points.get(y).getR()==originalZoomedCoordinate.y){
                        points[y] = new Point(tempPath.points.get(y).getL(), tempPath.points.get(y).getR()+1);
                    }
                    else{
                        points[y] = new Point(tempPath.points.get(y).getL(), tempPath.points.get(y).getR());
                    }
                }
                if(Screen.whichPolygonObject.isInside(points, points.length, originalZoomedCoordinate)){
                    tempPath.color = Screen.currentColor;
                }
            }
            else{
                Point [] points = new Point[regionMaxSize +1];
                int skipIndex = (tempPath.points.size()-1)/ regionMaxSize;
                int currentIndex =0;

                for(int y = 0; y< regionMaxSize; y++){
                    currentIndex = currentIndex+skipIndex;
                    if(tempPath.points.get(currentIndex).getR()==originalZoomedCoordinate.y){
                        points[y] = new Point(tempPath.points.get(currentIndex).getL(), tempPath.points.get(currentIndex).getR()+1);
                    }
                    else{
                        points[y] = new Point(tempPath.points.get(currentIndex).getL(),tempPath.points.get(currentIndex).getR());
                    }

                }
                int index = tempPath.points.size()-2;
                points[regionMaxSize] = new Point(tempPath.points.get(index).getL(), tempPath.points.get(index).getR());
                if(Screen.whichPolygonObject.isInside(points, points.length, originalZoomedCoordinate)){
                    tempPath.color = Screen.currentColor;
                }
            }
        }
      }
        
    public void deleteIndices(){
          //ArrayList<ArrayList<Pair<Integer,Integer>>> temp_Regions=new ArrayList<ArrayList<Pair<Integer,Integer>>>();
          pathIndices.sort(null);
          for(int i = pathIndices.size()-1; i>=0; i--){
//              paths.remove((int) pathIndices.get(i));
//              colorArray.remove((int) pathIndices.get(i));
              allPaths.remove((int) pathIndices.get(i));
          }
          //Regions.removeAll(temp_Regions);
          pathIndices.clear();
      }    
    
}
