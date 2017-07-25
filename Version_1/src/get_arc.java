import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;


public class get_arc {
    boolean fpc = false;
    boolean spc = false;
    Point s1,s2,s3;
    ArrayList<Pair<Float,Float>> Circles=new ArrayList<Pair<Float,Float>>();
    ArrayList<Pair<Float,Float>> Centers=new ArrayList<Pair<Float,Float>>();
    ArrayList<Integer> circle_indices=new ArrayList<Integer>();
    ArrayList<Float> Radius=new ArrayList<Float>();
    ArrayList<Integer> fill_or_not=new ArrayList<Integer>();
    ArrayList<Color> color_array = new ArrayList<Color>();
    ArrayList<Pair<Integer,Integer>> arc_angles=new ArrayList<Pair<Integer,Integer>>();
    
    Pair<Float, Float>A,B,C;
    float radius;
    int j =0;
    int r =0;
    SCR t1 = new SCR(r);
    public get_arc() {
            // TODO Auto-generated constructor stub
    }
    public void get_circle(MouseEvent e) throws NoninvertibleTransformException
{	
            float x=(float) 0.0;float y=(float) 0.0;
            int xw = 1;int xy = 1;
            Pair<Float,Float> temp1 = new Pair<Float,Float>(x, y);
            Pair<Float,Float> temp2 = new Pair<Float,Float>(x, y);
            Pair<Float,Float> temp3 = new Pair<Float,Float>(x, y);
            Pair<Float,Float> center = new Pair<Float,Float>(x, y);
            Pair<Integer,Integer> temp_angle = new Pair<Integer,Integer>(xw,xy);

            if (!fpc){
                s1 = SCR.a1.get_original_point_coordinate(e);
                fpc = true;
                temp1.setL((float) s1.x); temp1.setR((float) s1.y);
                s2 = null;s3=null;
                Circles.add(temp1);
                A=temp1;
            }
            else if(!spc){
                s2 = SCR.a1.get_original_point_coordinate(e);
                if(SCR.line_object.calc_distance(s2.x,s2.y,s1.x,s1.y)>5){
                    temp2.setL((float) s2.x);temp2.setR((float) s2.y);
                    Circles.add(temp2);
                    spc = true;
                    B=temp2;
                }
            }
            else{
                s3 = SCR.a1.get_original_point_coordinate(e);
                if((SCR.line_object.calc_distance(s2.x,s2.y,s3.x,s3.y)>5)&&(SCR.line_object.calc_distance(s3.x,s3.y,s1.x,s1.y)>5)
                            &&!SCR.line_object.collinear(s1.x,s1.y,s2.x,s2.y,s3.x,s3.y)){
                    temp3.setL((float) s3.x);temp3.setR((float) s3.y);
                    Circles.add(temp3);
                    fpc=spc = false;
    //			System.out.println("The Three points are :- "+"[" +"("+Circles.get(j).getL()+","+Circles.get(j).getR()+")"+
    //					" "+"("+Circles.get(j+1).getL()+","+Circles.get(j+1).getR()+")"+" "+"("+Circles.get(j+2).getL()+","+Circles.get(j+2).getR()+")"+"]");
    //			j=j+3;
                    C=temp3;
                    circle a= new circle();
                    int [] b = a.calc((int)(float)A.getL(),(int)(float) A.getR(),(int)(float) B.getL(),(int)(float) B.getR(),(int)(float) C.getL(),(int)(float) C.getR());
                    center.setL((float)(int)b[0]);
                    center.setR((float)(int)b[1]);
                    Centers.add(center);
                    radius = a.get_radius(center, C);
                    Radius.add(radius);
                    int [] b1 = a.calc_angles((int)(float)A.getL(),(int)(float) A.getR(),(int)(float) B.getL(),(int)(float) B.getR(),(int)(float) C.getL(),(int)(float) C.getR());
                    temp_angle.setL(b1[0]);
                    temp_angle.setR(b1[1]);
          //          System.out.println("angles : "+b1[0]+","+b1[1]);
                    arc_angles.add(temp_angle);
                    fill_or_not.add(0);
                    color_array.add(SCR.current_color);
                    Circles.clear();
                    
              ///      System.out.println("radius = "+radius);
                 //   System.out.println("The center is :- ("+center.getL()+","+center.getR()+")");
                }
                t1.repaint(SCR.screen, SCR.a2.screenCopy);
                SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
            }

}

    public  int calc_an(int a1,int a2){
    int an=0;
    an = (int) ( (double)(180.0/Math.PI) * Math.acos(a1/Math.pow((Math.pow(a1,2)+Math.pow(a2,2)),0.5))  );
    if(a2>0){
        an = 360-an;
    }
    return an;    }

    public boolean check_inside(int a1,int a2,int a3,int a4,int a5,int a6,int a7, int a8){
        int s0 = calc_an(a1,a2);
        int [] s1 = new int[3];
        s1[0] = calc_an(a3,a4);
        s1[1] = calc_an(a5,a6);
        s1[2] = calc_an(a7,a8);
        if(s1[0]<s1[2]){
            if(s1[1]>s1[0]&&s1[1]<s1[2]){
                return s0>s1[0]&&s0<s1[2];
            }
            else{
                return !(s0>s1[0]&&s0<s1[2]);
            }
        }
        else{
            if(s1[1]<s1[2]||s1[0]<s1[1]){
                return s0<s1[2]||s1[0]<s0;
            }
            else{
                return !(s0<s1[2]||s1[0]<s0);
            }
        }
    }
    public void delete_temp(){
        fpc = false; 
        spc = false;
        Circles.clear();
    }
        
    public void add_indices(MouseEvent e) throws NoninvertibleTransformException{
        Point p=SCR.a1.get_original_point_coordinate(e);
        for(int i=0;i<Centers.size();i++){
            float dist=(float)(Math.pow((p.x-Centers.get(i).getL()),2)+Math.pow((p.y-Centers.get(i).getR()),2));
            int a1 = p.x-(int) (double)Centers.get(i).getL();
            int a2 = p.y-(int) (double)Centers.get(i).getR();
            float rad = Radius.get(i);
            int s_a = arc_angles.get(i).getL();
            int e_a = arc_angles.get(i).getL() + arc_angles.get(i).getR();
            int m_a = (s_a+e_a)/2;
            
            int a3 =  (int) (rad*Math.cos(Math.toRadians(s_a)));
            int a4 =  (int) (-1*rad*Math.sin(Math.toRadians(s_a)));
            int a5 =  (int) (rad*Math.cos(Math.toRadians(m_a)));
            int a6 =  (int) (-1*rad*Math.sin(Math.toRadians(m_a)));
            int a7 =  (int) (rad*Math.cos(Math.toRadians(e_a)));
            int a8 =  (int) (-1*rad*Math.sin(Math.toRadians(e_a)));
            
            if(dist<Math.pow(Radius.get(i), 2)&&check_inside(a1, a2,a3,a4,a5,a6,a7,a8)){

                if(circle_indices.contains(i)){
                    circle_indices.remove((Integer)i);
                }
                else{
                    circle_indices.add(i);
                }
            }
        }
    }
    
    public void add_color_indices(MouseEvent e) throws NoninvertibleTransformException{
        Point p=SCR.a1.get_original_point_coordinate(e);
        for(int i=0;i<Centers.size();i++){
            float dist=(float)(Math.pow((p.x-Centers.get(i).getL()),2)+Math.pow((p.y-Centers.get(i).getR()),2));
            int a1 = p.x-(int) (double)Centers.get(i).getL();
            int a2 = p.y-(int) (double)Centers.get(i).getR();
            float rad = Radius.get(i);
            int s_a = arc_angles.get(i).getL();
            int e_a = arc_angles.get(i).getL() + arc_angles.get(i).getR();
            int m_a = (s_a+e_a)/2;
            
            int a3 =  (int) (rad*Math.cos(Math.toRadians(s_a)));
            int a4 =  (int) (-1*rad*Math.sin(Math.toRadians(s_a)));
            int a5 =  (int) (rad*Math.cos(Math.toRadians(m_a)));
            int a6 =  (int) (-1*rad*Math.sin(Math.toRadians(m_a)));
            int a7 =  (int) (rad*Math.cos(Math.toRadians(e_a)));
            int a8 =  (int) (-1*rad*Math.sin(Math.toRadians(e_a)));
            
            if(dist<Math.pow(Radius.get(i), 2)&&check_inside(a1, a2,a3,a4,a5,a6,a7,a8)){

                if(fill_or_not.get(i)==1&&color_array.get(i)!=SCR.current_color){
                            color_array.set(i,SCR.current_color);
                            fill_or_not.set(i,1);
                }
                else{
                    color_array.set(i,SCR.current_color);
                    fill_or_not.set(i,(fill_or_not.get(i)+1)%2);
                }
            }
            fill_or_not.set(i, 0);  // to remove fill
        }
    }
    
    public void delete_indices(){
//       ArrayList<Pair<Float,Float>> temp_Circles=new ArrayList<Pair<Float,Float>>();
//       ArrayList<Pair<Float,Float>> temp_Centers=new ArrayList<Pair<Float,Float>>();
//       ArrayList<Float> temp_Radius=new ArrayList<Float>();
//       ArrayList<Pair<Integer,Integer>> temp_arc_angles=new ArrayList<Pair<Integer,Integer>>();
        circle_indices.sort(null);
        for(int i=circle_indices.size()-1;i>=0;i--){
            Centers.remove((int)circle_indices.get(i));
            Radius.remove((int)circle_indices.get(i));
            arc_angles.remove((int)circle_indices.get(i));
            color_array.remove((int)circle_indices.get(i));
            fill_or_not.remove((int)circle_indices.get(i));
        }
//        Circles.removeAll(temp_Circles);
//        Centers.removeAll(temp_Centers);
//        Radius.removeAll(temp_Radius);
//        arc_angles.removeAll(temp_arc_angles);
        circle_indices.clear();
    }
}
