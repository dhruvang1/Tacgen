import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

public class GetRegions {
    ArrayList<ArrayList<Pair<Integer,Integer>>> regions =new ArrayList<ArrayList<Pair<Integer,Integer>>>();
    ArrayList<Pair<Integer,Integer>> regionPoints =new ArrayList<Pair<Integer,Integer>>();
    ArrayList<Integer> regionIndices =new ArrayList<Integer>();
    ArrayList<Integer> fillArray =new ArrayList<Integer>();
    ArrayList<Color> colorArray = new ArrayList<Color>();
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
    public void updateRegions(){
        if(regionPoints.size()>5){
            ArrayList<Pair<Integer,Integer>> tempPointsRegions=new ArrayList<Pair<Integer,Integer>>();
            for(int i = 0; i< regionPoints.size(); i++){
                int y1 = regionPoints.get(i).getL();
                int y2 = regionPoints.get(i).getR();
                Pair<Integer,Integer> temp_pair = new Pair<>(y1,y2);
                tempPointsRegions.add(temp_pair);
            }
            int y1 = regionPoints.get(0).getL();
            int y2 = regionPoints.get(0).getR();
            Pair<Integer,Integer> temp_pair = new Pair<>(y1,y2);
            tempPointsRegions.add(temp_pair);
            regions.add(tempPointsRegions);
            colorArray.add(Screen.current_color);
            fillArray.add(0);
            regionPoints.clear();
            screen.repaint(Screen.screen, Screen.a2.screenCopy);
            Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
        }
        regionPoints.clear();
        screen.repaint(Screen.screen, Screen.a2.screenCopy);
        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
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
                    int au = (regions.get(i).size()-1)/ regionsMaxSize;
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
                        if(fillArray.get(i)==1&& colorArray.get(i)!= Screen.current_color){
                            colorArray.set(i, Screen.current_color);
                            fillArray.set(i,1);
                        }
                        else{
                            colorArray.set(i, Screen.current_color);
                            fillArray.set(i,(fillArray.get(i)+1)%2);
                        }
                    }
                }
                else{
                    Point [] points = new Point[regionsMaxSize +1];
                    int au = (regions.get(i).size()-1)/ regionsMaxSize;
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
                        if(fillArray.get(i)==1&& colorArray.get(i)!= Screen.current_color){
                            colorArray.set(i, Screen.current_color);
                            fillArray.set(i,1);
                        }
                        else{
                            colorArray.set(i, Screen.current_color);
                            fillArray.set(i,(fillArray.get(i)+1)%2);
                        }
                    }
                }
                fillArray.set(i, 0);  // to remove fill
            }
      }

      public void deleteIndices(){
          regionIndices.sort(null);
          for(int i = regionIndices.size()-1; i>=0; i--){
              colorArray.remove((int) regionIndices.get(i));
              regions.remove((int) regionIndices.get(i));
              fillArray.remove((int) regionIndices.get(i));
          }
          regionIndices.clear();
      }

//      public void modify_color(MouseEvent e) throws NoninvertibleTransformException{
//          Point p= Screen.a1.getOriginalZoomedCoordinate(e);
//            int hfd = 50000;
//        //    p.y=-1*p.y;
//            for(int i = 0; i< regions.size(); i++){
//                if(regions.get(i).size()<=hfd+1){
//                    Point [] p1 = new Point[regions.get(i).size()-1];
//                    for(int y=0;y<p1.length;y++){
//                        if(regions.get(i).get(y).getR()==p.y){p1[y] = new Point(regions.get(i).get(y).getL(), regions.get(i).get(y).getR()+1);}
//                        else{
//                            p1[y] = new Point(regions.get(i).get(y).getL(), regions.get(i).get(y).getR());
//                        }
//                    }
//                    if(Screen.a10.isInside(p1, p1.length, p)){
//                        colorArray.set(i, Screen.current_color);
//                    }
//                }
//                else{
//                    Point [] p1 = new Point[hfd+1];
//                    int au = (regions.get(i).size()-1)/hfd;
//                    int hg =0;
//
//                    for(int y=0;y<hfd;y++){
//                        hg = hg+au;
//
//                        if(regions.get(i).get(hg).getR()==p.y){
//                            p1[y] = new Point(regions.get(i).get(hg).getL(), regions.get(i).get(hg).getR()+1);
//                        }
//                        else{
//                            p1[y] = new Point(regions.get(i).get(hg).getL(), regions.get(i).get(hg).getR());
//                        }
//
//                    }
//                //    System.out.println(hg);
//                    int ind = regions.get(i).size()-2;
//                    p1[hfd]=new Point(regions.get(i).get(ind).getL(), regions.get(i).get(ind).getR());
//                    if(Screen.a10.isInside(p1, p1.length, p)){
//                        colorArray.set(i, Screen.current_color);
//                    }
//                }
//
//            }
//      }
    
}
