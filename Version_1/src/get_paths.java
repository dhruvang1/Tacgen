import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

public class get_paths{
    ArrayList<ArrayList<Pair<Integer,Integer>>> Regions=new ArrayList<ArrayList<Pair<Integer,Integer>>>();
    ArrayList<Pair<Integer,Integer>> Points_regions=new ArrayList<Pair<Integer,Integer>>();
    ArrayList<Color> color_array = new ArrayList<Color>();
    ArrayList<Integer> region_indices=new ArrayList<Integer>();
    int r=0;
    SCR t1 = new SCR(r);
    boolean firstPointCaptured = false;
    
    public void get_region(MouseEvent e) throws NoninvertibleTransformException
    {
        Pair<Integer,Integer> temp = new Pair<>(SCR.a1.get_original_point_coordinate(e).x,SCR.a1.get_original_point_coordinate(e).y);
        Points_regions.add(temp);
  //      System.out.println(SCR.a1.get_original_point_coordinate(e).x+","+SCR.a1.get_original_point_coordinate(e).y);
        t1.repaint(SCR.screen, SCR.a2.screenCopy);
        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
    }
    public void update_regions(){
        if(Points_regions.size()>5){
            ArrayList<Pair<Integer,Integer>> temp_Points_regions=new ArrayList<Pair<Integer,Integer>>();
            for(int i=0;i<Points_regions.size();i++){
                int y1 = Points_regions.get(i).getL();
                int y2 = Points_regions.get(i).getR();
                Pair<Integer,Integer> temp_pair = new Pair<>(y1,y2);
                temp_Points_regions.add(temp_pair);
            }
            int y1 = Points_regions.get(0).getL();
            int y2 = Points_regions.get(0).getR();
            Pair<Integer,Integer> temp_pair = new Pair<>(y1,y2);
            temp_Points_regions.add(temp_pair);
            Regions.add(temp_Points_regions);
            color_array.add(SCR.current_color);
            Points_regions.clear();
        }
        Points_regions.clear();
    }
    
      public void add_indices(MouseEvent e) throws NoninvertibleTransformException{
            Point p=SCR.a1.get_original_point_coordinate(e);
            int hfd = 50000;
        //    p.y=-1*p.y;
            for(int i=0;i<Regions.size();i++){
                if(Regions.get(i).size()<=hfd+1){
                    Point [] p1 = new Point[Regions.get(i).size()-1];
                    for(int y=0;y<p1.length;y++){
                        if(Regions.get(i).get(y).getR()==p.y){p1[y] = new Point(Regions.get(i).get(y).getL(),Regions.get(i).get(y).getR()+1);}
                        else{
                            p1[y] = new Point(Regions.get(i).get(y).getL(),Regions.get(i).get(y).getR());
                        }
                    }
                    if(SCR.a10.isInside(p1, p1.length, p)){
                        if(region_indices.contains(i)){
                            region_indices.remove((Integer)i);
                        }
                        else{
                            region_indices.add(i);
                        }
                    }
                }
                else{
                    Point [] p1 = new Point[hfd+1];
                    int au = (Regions.get(i).size()-1)/hfd;
                    int hg =0;
                    
                    for(int y=0;y<hfd;y++){
                        hg = hg+au;
                        
                        if(Regions.get(i).get(hg).getR()==p.y){
                            p1[y] = new Point(Regions.get(i).get(hg).getL(),Regions.get(i).get(hg).getR()+1);
                        }
                        else{
                            p1[y] = new Point(Regions.get(i).get(hg).getL(),Regions.get(i).get(hg).getR());
                        }
                         
                    }
                //    System.out.println(hg);
                    int ind = Regions.get(i).size()-2;
                    p1[hfd]=new Point(Regions.get(i).get(ind).getL(),Regions.get(i).get(ind).getR());
                    if(SCR.a10.isInside(p1, p1.length, p)){
                        if(region_indices.contains(i)){
                            region_indices.remove((Integer)i);
                        }
                        else{
                            region_indices.add(i);
                        }
                    }
                }
                
            }
      }
      
      
      public void add_color_indices(MouseEvent e) throws NoninvertibleTransformException{
            Point p=SCR.a1.get_original_point_coordinate(e);
            int hfd = 50000;
        //    p.y=-1*p.y;
            for(int i=0;i<Regions.size();i++){
                if(Regions.get(i).size()<=hfd+1){
                    Point [] p1 = new Point[Regions.get(i).size()-1];
                    for(int y=0;y<p1.length;y++){
                        if(Regions.get(i).get(y).getR()==p.y){p1[y] = new Point(Regions.get(i).get(y).getL(),Regions.get(i).get(y).getR()+1);}
                        else{
                            p1[y] = new Point(Regions.get(i).get(y).getL(),Regions.get(i).get(y).getR());
                        }
                    }
                    if(SCR.a10.isInside(p1, p1.length, p)){
                        color_array.set(i,SCR.current_color);
                    }
                }
                else{
                    Point [] p1 = new Point[hfd+1];
                    int au = (Regions.get(i).size()-1)/hfd;
                    int hg =0;
                    
                    for(int y=0;y<hfd;y++){
                        hg = hg+au;
                        
                        if(Regions.get(i).get(hg).getR()==p.y){
                            p1[y] = new Point(Regions.get(i).get(hg).getL(),Regions.get(i).get(hg).getR()+1);
                        }
                        else{
                            p1[y] = new Point(Regions.get(i).get(hg).getL(),Regions.get(i).get(hg).getR());
                        }
                         
                    }
                    int ind = Regions.get(i).size()-2;
                    p1[hfd]=new Point(Regions.get(i).get(ind).getL(),Regions.get(i).get(ind).getR());
                    if(SCR.a10.isInside(p1, p1.length, p)){
                        color_array.set(i,SCR.current_color);
                    }
                }
            }
      }
        
      public void delete_indices(){
          //ArrayList<ArrayList<Pair<Integer,Integer>>> temp_Regions=new ArrayList<ArrayList<Pair<Integer,Integer>>>();
          region_indices.sort(null);
          for(int i=region_indices.size()-1;i>=0;i--){
              Regions.remove((int)region_indices.get(i));
              color_array.remove((int)region_indices.get(i));
          }
          //Regions.removeAll(temp_Regions);
          region_indices.clear();
      }    
    
}
