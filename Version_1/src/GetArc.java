import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

/**
 * Class to create arcs
 */
public class GetArc {
    public class Arc {
        public Pair<Integer,Integer> arcAngles;
        public Pair<Float, Float> center;
        public Float radius;
        public Color color;
        public Integer fill;
    }

    public ArrayList<Arc> allArcs = new ArrayList<>();
    private boolean firstPointCaptured = false;
    private boolean secondPointCaptured = false;
    private Point selectedPoint1, selectedPoint2, selectedPoint3;
    public ArrayList<Pair<Float,Float>> tempPoints = new ArrayList<>();
    public ArrayList<Integer> circleIndices = new ArrayList<>();
//    public ArrayList<Pair<Integer,Integer>> arcAngles = new ArrayList<>();
    //    public ArrayList<Color> colorArray = new ArrayList<>();
    //    public ArrayList<Integer> fillArray = new ArrayList<>();
    //    public ArrayList<Float> radii = new ArrayList<>();
//    public ArrayList<Pair<Float,Float>> centers = new ArrayList<>();

//    private Pair<Float, Float> arcA, arcB, arcC;
    private Screen screen = new Screen(0);
    public GetArc() {
            // TODO Auto-generated constructor stub
    }

    public void getCircle(MouseEvent e) throws NoninvertibleTransformException
    {
        float x=(float) 0.0;float y=(float) 0.0;
        int xw = 1;int xy = 1;
        Pair<Float,Float> point1 = new Pair<>(x, y);
        Pair<Float,Float> point2 = new Pair<>(x, y);
        Pair<Float,Float> point3 = new Pair<>(x, y);
        Pair<Float,Float> center = new Pair<>(x, y);
        Pair<Integer,Integer> tempAngle = new Pair<>(xw,xy);
        if (!firstPointCaptured){
            selectedPoint1 = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            firstPointCaptured = true;
            point1.setL((float) selectedPoint1.x);
            point1.setR((float) selectedPoint1.y);
            tempPoints.add(point1);
            selectedPoint2 = null;
            selectedPoint3 = null;
//            arcA=point1;
        }
        else if(!secondPointCaptured){
            selectedPoint2 = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            if(Screen.linesObject.getDistance(selectedPoint2.x,selectedPoint2.y,selectedPoint1.x,selectedPoint1.y)>5){
                point2.setL((float) selectedPoint2.x);
                point2.setR((float) selectedPoint2.y);
                tempPoints.add(point2);
                secondPointCaptured = true;
//                arcB=point2;
            }
        }
        else{
            selectedPoint3 = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            if((Screen.linesObject.getDistance(selectedPoint2.x,selectedPoint2.y,selectedPoint3.x,selectedPoint3.y)>5)&&(Screen.linesObject.getDistance(selectedPoint3.x,selectedPoint3.y,selectedPoint1.x,selectedPoint1.y)>5)
                    &&!Screen.linesObject.isCollinear(selectedPoint1.x,selectedPoint1.y,selectedPoint2.x,selectedPoint2.y,selectedPoint3.x,selectedPoint3.y)){
                point3.setL((float) selectedPoint3.x);point3.setR((float) selectedPoint3.y);
                tempPoints.add(point3);
                firstPointCaptured=secondPointCaptured = false;
//                arcC=point3;
                CircleHelper helper= new CircleHelper();
                int [] tempCenter = helper.getCenter((int)(float)tempPoints.get(0).getL(),(int)(float) tempPoints.get(0).getR(),
                        (int)(float) tempPoints.get(1).getL(),(int)(float) tempPoints.get(1).getR(),
                        (int)(float) tempPoints.get(2).getL(),(int)(float) tempPoints.get(2).getR());
                center.setL((float)(int)tempCenter[0]);
                center.setR((float)(int)tempCenter[1]);
                float radius = helper.getRadius(center, tempPoints.get(2));
                int [] b1 = helper.getArcAngles((int)(float)tempPoints.get(0).getL(),(int)(float) tempPoints.get(0).getR(),
                        (int)(float) tempPoints.get(1).getL(),(int)(float) tempPoints.get(1).getR(),
                        (int)(float) tempPoints.get(2).getL(),(int)(float) tempPoints.get(2).getR());
                tempAngle.setL(b1[0]);
                tempAngle.setR(b1[1]);
//                arcAngles.add(tempAngle);
//                centers.add(center);
//                radii.add(radius);
//                fillArray.add(0);
//                colorArray.add(Screen.currentColor);
                Arc currentArc = new Arc();
                currentArc.center = center;
                currentArc.radius = radius;
                currentArc.color = Screen.currentColor;
                currentArc.fill = 0;
                currentArc.arcAngles = tempAngle;
                allArcs.add(currentArc);
                tempPoints.clear();
            }
            screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
            Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
        }

}

    private int calc_an(int a1,int a2){
        int an=0;
        an = (int) ( (double)(180.0/Math.PI) * Math.acos(a1/Math.pow((Math.pow(a1,2)+Math.pow(a2,2)),0.5))  );
        if(a2>0){
            an = 360-an;
        }
        return an;
    }

    private boolean checkInside(int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8){
        int s0 = calc_an(a1,a2);
        int [] selectedPoint1 = new int[3];
        selectedPoint1[0] = calc_an(a3,a4);
        selectedPoint1[1] = calc_an(a5,a6);
        selectedPoint1[2] = calc_an(a7,a8);
        if(selectedPoint1[0]<selectedPoint1[2]){
            if(selectedPoint1[1]>selectedPoint1[0]&&selectedPoint1[1]<selectedPoint1[2]){
                return s0>selectedPoint1[0]&&s0<selectedPoint1[2];
            }
            else{
                return !(s0>selectedPoint1[0]&&s0<selectedPoint1[2]);
            }
        }
        else{
            if(selectedPoint1[1]<selectedPoint1[2]||selectedPoint1[0]<selectedPoint1[1]){
                return s0<selectedPoint1[2]||selectedPoint1[0]<s0;
            }
            else{
                return !(s0<selectedPoint1[2]||selectedPoint1[0]<s0);
            }
        }
    }

    public void deleteTemp(){
        firstPointCaptured = false;
        secondPointCaptured = false;
//        circles.clear();
        tempPoints.clear();
    }
        
    public void addIndices(MouseEvent e) throws NoninvertibleTransformException{
        Point originalZoomedCoordinate = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
//        for(int i = 0; i< centers.size(); i++){
//            float distance =(float)(Math.pow((originalZoomedCoordinate.x- centers.get(i).getL()),2)+Math.pow((originalZoomedCoordinate.y- centers.get(i).getR()),2));
//            int a1 = originalZoomedCoordinate.x-(int) (double) centers.get(i).getL();
//            int a2 = originalZoomedCoordinate.y-(int) (double) centers.get(i).getR();
//            float radius = radii.get(i);
//            int startAngle = arcAngles.get(i).getL();
//            int endAngle = arcAngles.get(i).getL() + arcAngles.get(i).getR();
        for(int i = 0; i< allArcs.size(); i++){
            Arc currentArc = allArcs.get(i);
            float distance =(float)(Math.pow((originalZoomedCoordinate.x - currentArc.center.getL()),2)+Math.pow((originalZoomedCoordinate.y - currentArc.center.getR()),2));
            int a1 = originalZoomedCoordinate.x-(int) (double) currentArc.center.getL();
            int a2 = originalZoomedCoordinate.y-(int) (double) currentArc.center.getR();
            float radius = currentArc.radius;
            int startAngle = currentArc.arcAngles.getL();
            int endAngle = currentArc.arcAngles.getL() + currentArc.arcAngles.getR();
            int midAngle = (startAngle + endAngle)/2;

            
            int a3 =  (int) (radius *Math.cos(Math.toRadians(startAngle)));
            int a4 =  (int) (-1* radius *Math.sin(Math.toRadians(startAngle)));
            int a5 =  (int) (radius *Math.cos(Math.toRadians(midAngle)));
            int a6 =  (int) (-1* radius *Math.sin(Math.toRadians(midAngle)));
            int a7 =  (int) (radius *Math.cos(Math.toRadians(endAngle)));
            int a8 =  (int) (-1* radius *Math.sin(Math.toRadians(endAngle)));
            
//            if(distance < Math.pow(radii.get(i), 2)&& checkInside(a1, a2,a3,a4,a5,a6,a7,a8)){
            if(distance < Math.pow(radius, 2) && checkInside(a1, a2,a3,a4,a5,a6,a7,a8)){
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
        Point originalZoomedCoordinate = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
//        for(int i = 0; i< centers.size(); i++){
//            float distance =(float)(Math.pow((originalZoomedCoordinate.x- centers.get(i).getL()),2)+Math.pow((originalZoomedCoordinate.y- centers.get(i).getR()),2));
//            int a1 = originalZoomedCoordinate.x-(int) (double) centers.get(i).getL();
//            int a2 = originalZoomedCoordinate.y-(int) (double) centers.get(i).getR();
//            float rad = radii.get(i);
//            int startAngle = arcAngles.get(i).getL();
//            int endAngle = arcAngles.get(i).getL() + arcAngles.get(i).getR();
        for(int i = 0; i< allArcs.size(); i++){
            Arc currentArc = allArcs.get(i);
            float distance =(float)(Math.pow((originalZoomedCoordinate.x - currentArc.center.getL()),2)+Math.pow((originalZoomedCoordinate.y - currentArc.center.getR()),2));
            int a1 = originalZoomedCoordinate.x-(int) (double) currentArc.center.getL();
            int a2 = originalZoomedCoordinate.y-(int) (double) currentArc.center.getR();
            float radius = currentArc.radius;
            int startAngle = currentArc.arcAngles.getL();
            int endAngle = currentArc.arcAngles.getL() + currentArc.arcAngles.getR();
            int midAngle = (startAngle + endAngle)/2;
            
            int a3 =  (int) (radius*Math.cos(Math.toRadians(startAngle)));
            int a4 =  (int) (-1*radius*Math.sin(Math.toRadians(startAngle)));
            int a5 =  (int) (radius*Math.cos(Math.toRadians(midAngle)));
            int a6 =  (int) (-1*radius*Math.sin(Math.toRadians(midAngle)));
            int a7 =  (int) (radius*Math.cos(Math.toRadians(endAngle)));
            int a8 =  (int) (-1*radius*Math.sin(Math.toRadians(endAngle)));
            
//            if(distance <Math.pow(radii.get(i), 2)&& checkInside(a1, a2,a3,a4,a5,a6,a7,a8)){
//                if(fillArray.get(i)==1&& colorArray.get(i)!= Screen.currentColor){
//                        colorArray.set(i, Screen.currentColor);
//                        fillArray.set(i,1);
//                }
//                else{
//                    colorArray.set(i, Screen.currentColor);
//                    fillArray.set(i,(fillArray.get(i)+1)%2);
//                }
//            }
//            fillArray.set(i, 0);  // to remove fill
            if(distance < Math.pow(radius, 2)&& checkInside(a1, a2,a3,a4,a5,a6,a7,a8)){
                if(currentArc.fill == 1 && currentArc.color != Screen.currentColor){
                    currentArc.color = Screen.currentColor;
                    currentArc.fill = 1;
                }
                else{
                    currentArc.color = Screen.currentColor;
                    currentArc.fill = (currentArc.fill + 1)%2;
                }
            }
            currentArc.fill = 0;  // to remove fill
        }
    }
    
    public void deleteIndices(){
        circleIndices.sort(null);
        for(int i = circleIndices.size()-1; i>=0; i--){
//            centers.remove((int) circleIndices.get(i));
//            radii.remove((int) circleIndices.get(i));
//            arcAngles.remove((int) circleIndices.get(i));
//            colorArray.remove((int) circleIndices.get(i));
//            fillArray.remove((int) circleIndices.get(i));
            allArcs.remove((int) circleIndices.get(i));
        }
        circleIndices.clear();
    }
}
