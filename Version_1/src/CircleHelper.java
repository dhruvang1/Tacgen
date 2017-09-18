/**
 * Class to help GetCircles class
 */
public class CircleHelper {

    public float getRadius(Pair<Float,Float> A, Pair<Float,Float> B){
        float rad;
        rad = (float) Math.pow(Math.pow((A.getL()-B.getL()), 2) + Math.pow((A.getR()-B.getR()), 2),0.5);

        return rad;
    }
    public  int getAnglesFromSides(int a1, int a2){
        int an=0;
        an = (int) ( (double)(180.0/Math.PI) * Math.acos(a1/Math.pow((Math.pow(a1,2)+Math.pow(a2,2)),0.5))  );
        if(a2>0){
            an = 360-an;
        }
        return an;
    }

    public int[] getArcAngles(int a1, int a2, int b1, int b2, int c1, int c2){
        int [] p = getCenter(a1,a2,b1,b2,c1,c2);
        int c_x = p[0];
        int c_y = p[1];
        int [] s1 = new int[3];
        int [] arc_angles = new int[2];

        s1[0] = getAnglesFromSides(a1-c_x,a2-c_y);

        s1[1] = getAnglesFromSides(b1-c_x,b2-c_y);
        s1[2] = getAnglesFromSides(c1-c_x,c2-c_y);
        //System.out.println(s1[0]+","+s1[1]+","+s1[2]);
        if(s1[0]<s1[2]){

            if(s1[1]>s1[0]&&s1[1]<s1[2]){
                arc_angles[1]=s1[2]-s1[0];
                arc_angles[0]=s1[0];
            }
            else{
                arc_angles[0]=s1[2];
                arc_angles[1]=360-(s1[2]-s1[0]);
            }
        }
        else{

            if(s1[1]<s1[2]||s1[0]<s1[1]){
                arc_angles[1]=360-s1[0]+s1[2];
                arc_angles[0]=s1[0];
            }
            else{
                arc_angles[0]=s1[2];
                arc_angles[1]=s1[0]-s1[2];
            }
        }
        return arc_angles;
    }
    public int[] getCenter(int a1, int a2, int b1, int b2, int c1, int c2){
        int m1,m2;
        int n1,n2;
        m1=(a1+b1)/2;
        m2=(a2+b2)/2;
        n1=(a1+c1)/2;
        n2=(a2+c2)/2;
        int cenx = 0;
        int ceny = 0;
        int a = 0,b = 0,c = 0;
        int d = 0,e = 0,f = 0;
        if(a1==c1){
            d=0;e=1;f=-n2;ceny = n2;
            if(a2==b2){
                cenx = m1;
            }
            else{
                a=b1-a1;
                b=b2-a2;
                c=-1*(a*m1+b*m2);
                cenx = (b*f-c)/a;
            }
        }
        else if(a1==b1){
            a=0;b=1;c=-m2;ceny = m2;
            if(a2==c2){
                cenx = n1;
            }
            else{
                d=c1-a1;
                e=c2-a2;
                f=-1*(d*n1+e*n2);
                cenx = (e*c-f)/d;
            }
        }
        else if(a2==c2){
            d=1;e=0;f=-n1;cenx = n1;
            if(a1==b1){
                ceny = m2;
            }
            else{
                a=b1-a1;
                b=b2-a2;
                c=-1*(a*m1+b*m2);
                ceny = (-1*c-a*n1)/b;
            }
        }
        else if(a2==b2){
            a=1;b=0;c=-m1;cenx = m1;
            if(a1==c1){
                ceny = m2;
            }
            else{
                d=c1-a1;
                e=c2-a2;
                f=-1*(d*n1+e*n2);
                ceny = (-1*f-d*m1)/e;
            }
        }
        else{
            a=b1-a1;
            b=b2-a2;
            c=-1*(a*m1+b*m2);
            d=c1-a1;
            e=c2-a2;
            f=-1*(d*n1+e*n2);
            ceny = (a*f-c*d)/(b*d-a*e);
            cenx = (b*f-c*e)/(a*e-b*d);
        }
        int [] ret = new int[3];
        ret[0] = cenx;
        ret[1] = ceny;
        return ret;
    }

    public float getDistance(float mid_x, float mid_y, float radius, float s1, float s2){
        float d= (float) Math.sqrt(Math.pow(s1-mid_x, 2)+Math.pow(s2-mid_y, 2));
        return (float) Math.sqrt(Math.pow(radius, 2)-Math.pow(d, 2));
    }

    public float[] potentialCenters(float s1, float s2, float e1, float e2, float radius, int lsa){
        float slope;
        float midX;
        float midY;
        float distance;
        float[] center = new float[4];  // x1,y1,    x2,y2
        if(s1==e1){
            midX = s1;
            midY= (s2+e2)/2f;
            distance = getDistance(midX,midY,radius,s1,s2);
            center[0]=midX+ distance;
            center[1]= midY;
            center[2]=midX- distance;
            center[3]= midY;
        }
        else if(s2==e2){
            midX =(s1+e1)/2f;
            midY = s2;
            distance = getDistance(midX,midY,radius,s1,s2);
            center[0]=midX;
            center[1]= midY+ distance;
            center[2]=midX;
            center[3]= midY- distance;
        }
        else{
            midX =(s1+e1)/2f;
            midY = (s2+e2)/2f;
            distance = getDistance(midX,midY,radius,s1,s2);
            slope= (s1-e1)/(s2-e2);
            double theta= Math.atan(slope);
            center[0]=(float) (midX+ distance *Math.cos(theta));
            center[1]=(float) (midY- distance *Math.sin(theta));
            center[2]=(float) (midX- distance *Math.cos(theta));
            center[3]=(float) (midY+ distance *Math.sin(theta));
        }
        float fx= e1-s1;
        float fy= e2-s2;
        float point1x= center[0]-s1;
        float point1y= center[1]-s2;
        float point2x= center[2]-s1;
        float point2y= center[3]-s2;

        float fCrossP1= fx*point1y-fy*point1x;
        float f_cross_p2= fx*point2y-fy*point2x;
        float[] finalCenter=new float[2];
        if(lsa==0){
            if(fCrossP1<0){
                finalCenter[0]= center[0];
                finalCenter[1]= center[1];
            }
            else{
                finalCenter[0]= center[2];
                finalCenter[1]= center[3];
            }
        }
        else{
            if(fCrossP1<0){
                finalCenter[0]= center[2];
                finalCenter[1]= center[3];
            }
            else{
                finalCenter[0]= center[0];
                finalCenter[1]= center[1];
            }
        }

        int[] arcAngles=new int[2];
        int s= getAnglesFromSides((int)(s1-finalCenter[0]),(int)(s2-finalCenter[1]));
        int e= getAnglesFromSides((int)(e1-finalCenter[0]),(int)(e2-finalCenter[1]));
        arcAngles[0]=s;
        if(e<s){
            arcAngles[1]=360-(s-e);
        }
        else{
            arcAngles[1]=e-s;
        }
        float[] ret = new float[4]; // cx,cy, arc_angle0, arc_angle1(converted to float)
        ret[0]=finalCenter[0];
        ret[1]=finalCenter[1];
        ret[2]=arcAngles[0];
        ret[3]=arcAngles[1];
        return ret;
    }



    public static void main(String[] args){
    }
}
