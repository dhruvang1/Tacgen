import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;


public class GetArc {
    boolean firstPointCaptured = false;
    boolean secondPointCaptured = false;
    Point selectedPoint1, selectedPoint2, selectedPoint3;
    ArrayList<Pair<Float,Float>> circles = new ArrayList<>();
    ArrayList<Pair<Float,Float>> centers = new ArrayList<>();
    ArrayList<Integer> circleIndices = new ArrayList<>();
    ArrayList<Float> radii = new ArrayList<>();
    ArrayList<Integer> fillArray = new ArrayList<>();
    ArrayList<Color> colorArray = new ArrayList<>();
    ArrayList<Pair<Integer,Integer>> arcAngles = new ArrayList<>();
    
    Pair<Float, Float> arcA, arcB, arcC;
    float radius;
    Screen screen = new Screen(0);
    public GetArc() {
            // TODO Auto-generated constructor stub
    }

    public void getCircle(MouseEvent e) throws NoninvertibleTransformException
    {
            float x=(float) 0.0; float y=(float) 0.0;
            int xw = 1;int xy = 1;
            Pair<Float,Float> center = new Pair<>(x, y);
            Pair<Integer,Integer> temp_angle = new Pair<>(xw,xy);

            if (!firstPointCaptured){
                selectedPoint1 = Screen.a1.getOriginalZoomedCoordinate(e);
                firstPointCaptured = true;
                arcA.setL((float) selectedPoint1.x); arcA.setR((float) selectedPoint1.y);
                selectedPoint2 = null;
                selectedPoint3 =null;
                circles.add(arcA);
            }
            else if(!secondPointCaptured){
                selectedPoint2 = Screen.a1.getOriginalZoomedCoordinate(e);
                if(Screen.line_object.calc_distance(selectedPoint2.x, selectedPoint2.y, selectedPoint1.x, selectedPoint1.y)>5){
                    arcB.setL((float) selectedPoint2.x);arcB.setR((float) selectedPoint2.y);
                    circles.add(arcB);
                    secondPointCaptured = true;
                }
            }
            else{
                selectedPoint3 = Screen.a1.getOriginalZoomedCoordinate(e);
                if((Screen.line_object.calc_distance(selectedPoint2.x, selectedPoint2.y, selectedPoint3.x, selectedPoint3.y)>5)&&(Screen.line_object.calc_distance(selectedPoint3.x, selectedPoint3.y, selectedPoint1.x, selectedPoint1.y)>5)
                            &&!Screen.line_object.collinear(selectedPoint1.x, selectedPoint1.y, selectedPoint2.x, selectedPoint2.y, selectedPoint3.x, selectedPoint3.y)){
                    arcC.setL((float) selectedPoint3.x);arcC.setR((float) selectedPoint3.y);
                    circles.add(arcC);
                    firstPointCaptured = secondPointCaptured = false;
    //			System.out.println("The Three points are :- "+"[" +"("+Circles.get(j).getL()+","+Circles.get(j).getR()+")"+
    //					" "+"("+Circles.get(j+1).getL()+","+Circles.get(j+1).getR()+")"+" "+"("+Circles.get(j+2).getL()+","+Circles.get(j+2).getR()+")"+"]");
    //			j=j+3;
                    Circle circle = new Circle();
                    int [] b = circle.getCenter((int)(float) arcA.getL(),(int)(float) arcA.getR(),(int)(float) arcB.getL(),(int)(float) arcB.getR(),(int)(float) arcC.getL(),(int)(float) arcC.getR());
                    center.setL((float)(int)b[0]);
                    center.setR((float)(int)b[1]);
                    centers.add(center);
                    radius = circle.getRadius(center, arcC);
                    radii.add(radius);
                    b = circle.getArcAngles((int)(float) arcA.getL(),(int)(float) arcA.getR(),(int)(float) arcB.getL(),(int)(float) arcB.getR(),(int)(float) arcC.getL(),(int)(float) arcC.getR());
                    temp_angle.setL(b[0]);
                    temp_angle.setR(b[1]);
          //          System.out.println("angles : "+b1[0]+","+b1[1]);
                    arcAngles.add(temp_angle);
                    fillArray.add(0);
                    colorArray.add(Screen.current_color);
                    circles.clear();
                    
              ///      System.out.println("radius = "+radius);
                 //   System.out.println("The center is :- ("+center.getL()+","+center.getR()+")");
                }
                screen.repaint(Screen.screen, Screen.a2.screenCopy);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
            }

}

    public  int calc_an(int a1,int a2){
    int an=0;
    an = (int) ( (double)(180.0/Math.PI) * Math.acos(a1/Math.pow((Math.pow(a1,2)+Math.pow(a2,2)),0.5))  );
    if(a2>0){
        an = 360-an;
    }
    return an;    }

    public boolean checkInside(int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8){
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
    public void deleteTemp(){
        firstPointCaptured = false;
        secondPointCaptured = false;
        circles.clear();
    }
        
    public void addIndices(MouseEvent e) throws NoninvertibleTransformException{
        Point originalZoomedCoordinate = Screen.a1.getOriginalZoomedCoordinate(e);
        for(int i = 0; i< centers.size(); i++){
            float distance =(float)(Math.pow((originalZoomedCoordinate.x- centers.get(i).getL()),2)+Math.pow((originalZoomedCoordinate.y- centers.get(i).getR()),2));
            int a1 = originalZoomedCoordinate.x-(int) (double) centers.get(i).getL();
            int a2 = originalZoomedCoordinate.y-(int) (double) centers.get(i).getR();
            float radius = radii.get(i);
            int startAngle = arcAngles.get(i).getL();
            int endAngle = arcAngles.get(i).getL() + arcAngles.get(i).getR();
            int midAngle = (startAngle + endAngle)/2;
            
            int a3 =  (int) (radius *Math.cos(Math.toRadians(startAngle)));
            int a4 =  (int) (-1* radius *Math.sin(Math.toRadians(startAngle)));
            int a5 =  (int) (radius *Math.cos(Math.toRadians(midAngle)));
            int a6 =  (int) (-1* radius *Math.sin(Math.toRadians(midAngle)));
            int a7 =  (int) (radius *Math.cos(Math.toRadians(endAngle)));
            int a8 =  (int) (-1* radius *Math.sin(Math.toRadians(endAngle)));
            
            if(distance <Math.pow(radii.get(i), 2)&& checkInside(a1, a2,a3,a4,a5,a6,a7,a8)){

                if(circleIndices.contains(i)){
                    circleIndices.remove((Integer)i);
                }
                else{
                    circleIndices.add(i);
                }
            }
        }
    }
    
    public void addColorIndices(MouseEvent e) throws NoninvertibleTransformException{
        Point originalZoomedCoordinate = Screen.a1.getOriginalZoomedCoordinate(e);
        for(int i = 0; i< centers.size(); i++){
            float distance =(float)(Math.pow((originalZoomedCoordinate.x- centers.get(i).getL()),2)+Math.pow((originalZoomedCoordinate.y- centers.get(i).getR()),2));
            int a1 = originalZoomedCoordinate.x-(int) (double) centers.get(i).getL();
            int a2 = originalZoomedCoordinate.y-(int) (double) centers.get(i).getR();
            float rad = radii.get(i);
            int startAngle = arcAngles.get(i).getL();
            int endAngle = arcAngles.get(i).getL() + arcAngles.get(i).getR();
            int midAngle = (startAngle + endAngle)/2;
            
            int a3 =  (int) (rad*Math.cos(Math.toRadians(startAngle)));
            int a4 =  (int) (-1*rad*Math.sin(Math.toRadians(startAngle)));
            int a5 =  (int) (rad*Math.cos(Math.toRadians(midAngle)));
            int a6 =  (int) (-1*rad*Math.sin(Math.toRadians(midAngle)));
            int a7 =  (int) (rad*Math.cos(Math.toRadians(endAngle)));
            int a8 =  (int) (-1*rad*Math.sin(Math.toRadians(endAngle)));
            
            if(distance <Math.pow(radii.get(i), 2)&& checkInside(a1, a2,a3,a4,a5,a6,a7,a8)){

                if(fillArray.get(i)==1&& colorArray.get(i)!= Screen.current_color){
                            colorArray.set(i, Screen.current_color);
                            fillArray.set(i,1);
                }
                else{
                    colorArray.set(i, Screen.current_color);
                    fillArray.set(i,(fillArray.get(i)+1)%2);
                }
            }
            fillArray.set(i, 0);  // to remove fill
        }
    }
    
    public void deleteIndices(){
//       ArrayList<Pair<Float,Float>> temp_Circles=new ArrayList<Pair<Float,Float>>();
//       ArrayList<Pair<Float,Float>> temp_Centers=new ArrayList<Pair<Float,Float>>();
//       ArrayList<Float> temp_Radius=new ArrayList<Float>();
//       ArrayList<Pair<Integer,Integer>> temp_arc_angles=new ArrayList<Pair<Integer,Integer>>();
        circleIndices.sort(null);
        for(int i = circleIndices.size()-1; i>=0; i--){
            centers.remove((int) circleIndices.get(i));
            radii.remove((int) circleIndices.get(i));
            arcAngles.remove((int) circleIndices.get(i));
            colorArray.remove((int) circleIndices.get(i));
            fillArray.remove((int) circleIndices.get(i));
        }
//        Circles.removeAll(temp_Circles);
//        Centers.removeAll(temp_Centers);
//        Radius.removeAll(temp_Radius);
//        arc_angles.removeAll(temp_arc_angles);
        circleIndices.clear();
    }
}
