

public class Circle {
	
	public float getRadius(Pair<Float,Float> A, Pair<Float,Float> B){
        return (float) Math.pow(Math.pow((A.getL()-B.getL()), 2) + Math.pow((A.getR()-B.getR()), 2),0.5);
    }

    public  int getAngleFromSides(int a1, int a2){
        int angle = (int) ((180.0/Math.PI) * Math.acos(a1/Math.pow((Math.pow(a1,2)+Math.pow(a2,2)),0.5))  );
        if(a2>0){
            angle = 360- angle;
        }
        return angle;
    }

    public int[] getArcAngles(int a1, int a2, int b1, int b2, int c1, int c2){
        int[] center = getCenter(a1,a2,b1,b2,c1,c2);
        int centerX = center[0];
        int centerY = center[1];
        int [] anglesFromCenter = new int[3];
        int [] arc_angles = new int[2];
        
        anglesFromCenter[0] = getAngleFromSides(a1- centerX,a2- centerY);
        anglesFromCenter[1] = getAngleFromSides(b1- centerX,b2- centerY);
        anglesFromCenter[2] = getAngleFromSides(c1- centerX,c2- centerY);
        //System.out.println(s1[0]+","+s1[1]+","+s1[2]);
        if(anglesFromCenter[0]< anglesFromCenter[2]){
            
            if(anglesFromCenter[1]> anglesFromCenter[0]&& anglesFromCenter[1]< anglesFromCenter[2]){
                arc_angles[1]= anglesFromCenter[2]- anglesFromCenter[0];
                arc_angles[0]= anglesFromCenter[0];
            }
            else{
                arc_angles[0]= anglesFromCenter[2];
                arc_angles[1]=360-(anglesFromCenter[2]- anglesFromCenter[0]);
            }
        }
        else{
            
            if(anglesFromCenter[1]< anglesFromCenter[2]|| anglesFromCenter[0]< anglesFromCenter[1]){
                arc_angles[1]=360- anglesFromCenter[0]+ anglesFromCenter[2];
                arc_angles[0]= anglesFromCenter[0];
            }
            else{
                arc_angles[0]= anglesFromCenter[2];
                arc_angles[1]= anglesFromCenter[0]- anglesFromCenter[2];
            }
        }
        return arc_angles;
    }

    public int[] getCenter(int a1, int a2, int b1, int b2, int c1, int c2){
        int m1,m2,n1,n2; //mid points
        m1=(a1+b1)/2;
        m2=(a2+b2)/2;
        n1=(a1+c1)/2;
        n2=(a2+c2)/2;
        int centerX;
        int centerY;
        int a,b,c,d,e,f;
            if(a1==c1){
                d=0;e=1;f=-n2;
                centerY = n2;
                if(a2==b2){
                    centerX = m1;
                }
                else{
                    a=b1-a1;
                    b=b2-a2;
                    c=-1*(a*m1+b*m2);
                    centerX = (b*f-c)/a;
                }
            }
            else if(a1==b1){
                a=0;b=1;c=-m2;
                centerY = m2;
                if(a2==c2){
                    centerX = n1;
                }
                else{
                    d=c1-a1;
                    e=c2-a2;
                    f=-1*(d*n1+e*n2);
                    centerX = (e*c-f)/d;
                }
            }
            else if(a2==c2){
                d=1;e=0;f=-n1;
                centerX = n1;
                if(a1==b1){
                    centerY = m2;
                }
                else{
                    a=b1-a1;
                    b=b2-a2;
                    c=-1*(a*m1+b*m2);
                    centerY = (-1*c-a*n1)/b;
                }
            }
            else if(a2==b2){
                a=1;b=0;c=-m1;
                centerX = m1;
                if(a1==c1){
                    centerY = m2;
                }
                else{
                    d=c1-a1;
                    e=c2-a2;
                    f=-1*(d*n1+e*n2);
                    centerY = (-1*f-d*m1)/e;
                }
            }
            else{
                a=b1-a1;
                b=b2-a2;
                c=-1*(a*m1+b*m2);
                d=c1-a1;
                e=c2-a2;
                f=-1*(d*n1+e*n2);
                centerY = (a*f-c*d)/(b*d-a*e);
                centerX = (b*f-c*e)/(a*e-b*d);
            }
            int [] ret = new int[3];
            ret[0] = centerX;
            ret[1] = centerY;
            return ret;
    }
    
    public float dist(float mid_x, float mid_y, float radius, float s1, float s2){
        float d= (float) Math.sqrt(Math.pow(s1-mid_x, 2)+Math.pow(s2-mid_y, 2));
        return (float) Math.sqrt(Math.pow(radius, 2)-Math.pow(d, 2));
    }
    
    public float[] potential_centers(float s1,float s2,float e1,float e2,float radius, int lsa){
        float slope;
        float mid_x;
        float mid_y;
        float d;
        float[] cent= new float[4];  // x1,y1,    x2,y2
        if(s1==e1){
            mid_x = s1;
            mid_y= (s2+e2)/2f;
            d= dist(mid_x,mid_y,radius,s1,s2);
            cent[0]=mid_x+d;
            cent[1]= mid_y;
            cent[2]=mid_x-d;
            cent[3]= mid_y;
        }
        else if(s2==e2){
            mid_x =(s1+e1)/2f;
            mid_y = s2;
            d= dist(mid_x,mid_y,radius,s1,s2);
            cent[0]=mid_x;
            cent[1]= mid_y+d;
            cent[2]=mid_x;
            cent[3]= mid_y-d;
        }
        else{
            mid_x =(s1+e1)/2f;
            mid_y = (s2+e2)/2f;
        //    System.out.println("mid x "+mid_x+"mid y "+mid_y);
            d= dist(mid_x,mid_y,radius,s1,s2);
        //    System.out.println("d "+d);
            slope= (s1-e1)/(s2-e2);
            double theta= Math.atan(slope);
        //    System.out.println("theta "+Math.toDegrees(theta));
            cent[0]=(float) (mid_x+d*Math.cos(theta));
            cent[1]=(float) (mid_y-d*Math.sin(theta));
            cent[2]=(float) (mid_x-d*Math.cos(theta));
            cent[3]=(float) (mid_y+d*Math.sin(theta));
        }
//        System.out.println("1 x "+cent[0]+" 1 y "+cent[1]);
//        System.out.println("2 x "+cent[2]+" 2 y "+cent[3]);
        float fx= e1-s1;
        float fy= e2-s2;
        float p1x=cent[0]-s1;
        float p1y=cent[1]-s2;
        float p2x=cent[2]-s1;
        float p2y=cent[3]-s2;
        
        float f_cross_p1= fx*p1y-fy*p1x;
        float f_cross_p2= fx*p2y-fy*p2x;
        float[] final_cent=new float[2];
        if(lsa==0){
            if(f_cross_p1<0){
                final_cent[0]=cent[0];
                final_cent[1]=cent[1];
            }
            else{
                final_cent[0]=cent[2];
                final_cent[1]=cent[3];
            }
        }
        else{
            if(f_cross_p1<0){
                final_cent[0]=cent[2];
                final_cent[1]=cent[3];
            }
            else{
                final_cent[0]=cent[0];
                final_cent[1]=cent[1];
            }
        }
//        System.out.println("final cent 0 "+final_cent[0]);
//        System.out.println("final cent 1 "+final_cent[1]);

        int[] arc_angles=new int[2];
//        System.out.println("getAngleFromSides for s "+(int)(s1-final_cent[0])+" , "+(int)(s2-final_cent[1]));
//        System.out.println("getAngleFromSides for e "+(int)(e1-final_cent[0])+" , "+(int)(e2-final_cent[1]));
        int s= getAngleFromSides((int)(s1-final_cent[0]),(int)(s2-final_cent[1]));
        int e= getAngleFromSides((int)(e1-final_cent[0]),(int)(e2-final_cent[1]));
//        System.out.println("s angle "+s);
//        System.out.println("e angle "+e);
        arc_angles[0]=s;
        if(e<s){
            arc_angles[1]=360-(s-e);
        }
        else{
            arc_angles[1]=e-s;
        }
//        System.out.println("arc angle 0 "+arc_angles[0]);
//        System.out.println("arc angle 1 "+arc_angles[1]);
        float[] ret = new float[4]; // cx,cy, arc_angle0, arc_angle1(converted to float)
        ret[0]=final_cent[0];
        ret[1]=final_cent[1];
        ret[2]=arc_angles[0];
        ret[3]=arc_angles[1];
        return ret;
    }
    
    
    
    public static void main(String[] args){
//        int [] s = getArcAngles(50,0,0,50,100,50);
//        System.out.println(s[0]+","+s[1]);
    }
}
