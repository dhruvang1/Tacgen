import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

public class GetPaths {
    ArrayList<ArrayList<Pair<Integer,Integer>>> regions = new ArrayList<>();
    ArrayList<Pair<Integer,Integer>> regionPoints = new ArrayList<>();
    ArrayList<Color> colorArray = new ArrayList<>();
    ArrayList<Integer> regionIndices = new ArrayList<>();
    Screen screen = new Screen(0);
//    boolean firstPointCaptured = false;
    
    public void getRegion(MouseEvent e) throws NoninvertibleTransformException
    {
        Pair<Integer,Integer> temp = new Pair<>(Screen.a1.getOriginalZoomedCoordinate(e).x, Screen.a1.getOriginalZoomedCoordinate(e).y);
        regionPoints.add(temp);
  //      System.out.println(Screen.a1.getOriginalZoomedCoordinate(e).x+","+Screen.a1.getOriginalZoomedCoordinate(e).y);
        screen.repaint(Screen.screen, Screen.a2.screenCopy);
        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
    }

    public void updateRegions(){ //try removing tempRegionPoints
        if(regionPoints.size()>5){
            ArrayList<Pair<Integer,Integer>> tempRegionPoints =new ArrayList<>();
            for(int i = 0; i< regionPoints.size(); i++){
                int y1 = regionPoints.get(i).getL();
                int y2 = regionPoints.get(i).getR();
                Pair<Integer,Integer> temp_pair = new Pair<>(y1,y2);
                tempRegionPoints.add(temp_pair);
            }
            int y1 = regionPoints.get(0).getL();
            int y2 = regionPoints.get(0).getR();
            Pair<Integer,Integer> temp_pair = new Pair<>(y1,y2);
            tempRegionPoints.add(temp_pair);
            regions.add(tempRegionPoints);
            colorArray.add(Screen.current_color);
//            regionPoints.clear();
        }
        regionPoints.clear();
    }
    
      public void addIndices(MouseEvent e) throws NoninvertibleTransformException{
            Point originalZoomedCoordinate = Screen.a1.getOriginalZoomedCoordinate(e);
            int regionsMaxSize = 50000;
        //    originalZoomedCoordinate.y=-1*originalZoomedCoordinate.y;
            for(int i = 0; i< regions.size(); i++){
                if(regions.get(i).size()<= regionsMaxSize +1){
                    Point [] points = new Point[regions.get(i).size()-1];
                    for(int y=0;y<points.length;y++){
                        if(regions.get(i).get(y).getR()==originalZoomedCoordinate.y){points[y] = new Point(regions.get(i).get(y).getL(), regions.get(i).get(y).getR()+1);}
                        else{
                            points[y] = new Point(regions.get(i).get(y).getL(), regions.get(i).get(y).getR());
                        }
                    }
                    if(Screen.a10.isInside(points, points.length, originalZoomedCoordinate)){
                        if(regionIndices.contains(i)){
                            regionIndices.remove((Integer)i);
                        }
                        else{
                            regionIndices.add(i);
                        }
                    }
                }
                else{
                    Point [] points = new Point[regionsMaxSize +1];
                    int au = (regions.get(i).size()-1)/ regionsMaxSize; //au, hg??
                    int hg =0;
                    
                    for(int y = 0; y< regionsMaxSize; y++){
                        hg = hg+au;
                        
                        if(regions.get(i).get(hg).getR()==originalZoomedCoordinate.y){
                            points[y] = new Point(regions.get(i).get(hg).getL(), regions.get(i).get(hg).getR()+1);
                        }
                        else{
                            points[y] = new Point(regions.get(i).get(hg).getL(), regions.get(i).get(hg).getR());
                        }
                         
                    }
                //    System.out.println(hg);
                    int index = regions.get(i).size()-2;
                    points[regionsMaxSize]=new Point(regions.get(i).get(index).getL(), regions.get(i).get(index).getR());
                    if(Screen.a10.isInside(points, points.length, originalZoomedCoordinate)){
                        if(regionIndices.contains(i)){
                            regionIndices.remove((Integer)i);
                        }
                        else{
                            regionIndices.add(i);
                        }
                    }
                }
                
            }
      }
      
      
      public void addColorIndices(MouseEvent e) throws NoninvertibleTransformException{
            Point originalZoomedCoordinate = Screen.a1.getOriginalZoomedCoordinate(e);
            int regionMaxSize = 50000;
        //    originalZoomedCoordinate.y=-1*originalZoomedCoordinate.y;
            for(int i = 0; i< regions.size(); i++){
                if(regions.get(i).size()<= regionMaxSize +1){
                    Point [] points = new Point[regions.get(i).size()-1];
                    for(int y=0;y<points.length;y++){
                        if(regions.get(i).get(y).getR()==originalZoomedCoordinate.y){points[y] = new Point(regions.get(i).get(y).getL(), regions.get(i).get(y).getR()+1);}
                        else{
                            points[y] = new Point(regions.get(i).get(y).getL(), regions.get(i).get(y).getR());
                        }
                    }
                    if(Screen.a10.isInside(points, points.length, originalZoomedCoordinate)){
                        colorArray.set(i, Screen.current_color);
                    }
                }
                else{
                    Point [] points = new Point[regionMaxSize +1];
                    int au = (regions.get(i).size()-1)/ regionMaxSize;
                    int hg =0;
                    
                    for(int y = 0; y< regionMaxSize; y++){
                        hg = hg+au;
                        
                        if(regions.get(i).get(hg).getR()==originalZoomedCoordinate.y){
                            points[y] = new Point(regions.get(i).get(hg).getL(), regions.get(i).get(hg).getR()+1);
                        }
                        else{
                            points[y] = new Point(regions.get(i).get(hg).getL(), regions.get(i).get(hg).getR());
                        }
                         
                    }
                    int index = regions.get(i).size()-2;
                    points[regionMaxSize] = new Point(regions.get(i).get(index).getL(), regions.get(i).get(index).getR());
                    if(Screen.a10.isInside(points, points.length, originalZoomedCoordinate)){
                        colorArray.set(i, Screen.current_color);
                    }
                }
            }
      }
        
      public void deleteIndices(){
          //ArrayList<ArrayList<Pair<Integer,Integer>>> temp_Regions=new ArrayList<ArrayList<Pair<Integer,Integer>>>();
          regionIndices.sort(null);
          for(int i = regionIndices.size()-1; i>=0; i--){
              regions.remove((int) regionIndices.get(i));
              colorArray.remove((int) regionIndices.get(i));
          }
          //Regions.removeAll(temp_Regions);
          regionIndices.clear();
      }    
    
}
