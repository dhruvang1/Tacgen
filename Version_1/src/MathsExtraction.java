import com.sun.javafx.geom.Vec3f;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgproc.Imgproc.*;

public class MathsExtraction {
    Point subtract(Point p1,Point p2){
        return new Point(p1.x-p2.x,p1.y-p2.y);
    }

    Point add(Point p1,Point p2){
        return new Point(p1.x+p2.x,p1.y+p2.y);
    }

    Point scalar(double d,Point p){
        return new Point(d*p.x,d*p.y);
    }

    class Vec4i{
        int [] points = new int[4];
        Vec4i(){}

        Vec4i(Vec4i v){
//            for(int i=0;i<v.points.length;i++){
//                points[i] = v.points[i];
//            }
            System.arraycopy(v.points,0,points,0,4);
        }

        Vec4i(int a,int b,int c,int d){
            points[0] = a;
            points[1] = b;
            points[2] = c;
            points[3] = d;
        }
    }

    /// check if line segments p0p1 and p2p3 intersect
    Boolean line_intersection(Point p0, Point p1, Point p2, Point p3, Point r){ //check r pass by pointer

        Point s1,s2,s3;
        s1 = subtract(p1,p0);
        s2 = subtract(p3,p2);
        s3 = subtract(p0,p2);
        double s, t;

        s = ((s1.x * s3.y) - (s1.y * s3.x))/ (s1.x*s2.y - s2.x*s1.y);
        t = ((s2.x * s3.y) - (s2.y * s3.x))/ (s1.x*s2.y - s2.x*s1.y);

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1){
            // Collision detected
            r.x = p0.x + (t * s1.x);
            r.y = p0.y + (t * s1.y);
            return true;
        }
        // No collision, lines may be close together but non intersecting
        return false;
    }

    /// Shortest distance between p0 and p1-----p2
    double point_distance( Point p0, Point p1, Point p2){

        Point A = subtract(p0,p1);
        Point B = subtract(p2,p1);

        double dot = A.x*B.x + A.y*B.y;
        double len_sq = B.x*B.x + B.y*B.y;
        double param = -1;
        if (len_sq != 0) //in case of zero length
            param = dot / len_sq;

        double xx, yy;

        if (param < 0){
            xx = p1.x; yy = p1.y;
        } else if (param > 1){
            xx = p2.x; yy = p2.y;
        } else {
            xx = p1.x + param * B.x;
            yy = p1.y + param * B.y;
        }

        double dx = p0.x - xx;
        double dy = p0.y - yy;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /// Check line segment p0p1 is shorter than p2p3
    int is_shorter(Point p0, Point p1, Point p2, Point p3){

        Point A = subtract(p0,p1);
        Point B = subtract(p2,p3);

        double len1 = A.x*A.x + A.y*A.y;
        double len2 = B.x*B.x + B.y*B.y;

        if (len1<len2)
            return -1;
        else if (len1 == len2)
            return 0;
        else
            return 1;
    }

    /// Find angle between line segment p1---O and p2---O
    double angle_between(Point p1, Point p2, Point O){
        Point s1, s2;
        s1 = subtract(p1, O);
        s2 = subtract(p2,O);

        double m12 = Math.sqrt(s1.x*s1.x + s1.y*s1.y);
        double m13 = Math.sqrt(s2.x*s2.x + s2.y*s2.y);

        double theta = Math.acos( (s1.x*s2.x + s1.y*s2.y) / (m12 * m13) );
        return (theta*180)/ Math.PI;
    }

    /// check if line segments p0p1 and p2p3 are nearly same (non intersecting but same)
    Boolean nearly_same(Point p0, Point p1, Point p2, Point p3, double thresh_min_line_dist, int angle_s){

        double a = Math.min(point_distance(p0,p2,p3), point_distance(p1,p2,p3));
        double b = Math.min(point_distance(p2,p0,p1), point_distance(p3,p0,p1));

        double slope1 = (p1.y - p0.y)/(p1.x - p0.x);
        double slope2 = (p3.y - p2.y)/(p3.x - p2.x);
        slope1 = (Math.atan(slope1)*180)/Math.PI;
        slope2 = (Math.atan(slope2)*180)/Math.PI;

        double m = Math.min(a,b);

        if (m<thresh_min_line_dist && Math.abs(slope1-slope2)<angle_s)
            return true;
        else
            return false;
    }

    /// check if nearly same lines p0p1 and p2p3 are in order p0p2p1p3 and p0p1 can be extended till p3
    Point extend(Point p0, Point p1, Point p2, Point p3){
        double dy = p1.y - p0.y;
        double dx = p1.x - p0.x;
        double slope = dy/dx;
        double angle = Math.abs((Math.atan(slope)*180)/Math.PI);

//        if(p0.x==619){
            //cout<<slope<<p0<<p1<<p2<<p3<<endl;
//        }
        int flag = 0;
        if (angle<45){
            if(p0.x<p2.x && p2.x<p1.x && p1.x<p3.x)
                flag = 1;
            if(p0.x>p2.x && p2.x>p1.x && p1.x>p3.x)
                flag = 1;
        } else {
            if(p0.y<p2.y && p2.y<p1.y && p1.y<p3.y)
                flag = 2;
            if(p0.y>p2.y && p2.y>p1.y && p1.y>p3.y)
                flag = 2;
        }
        Point pnew = new Point(0, 0);
        if(flag == 1){
            pnew.x = p3.x;
            pnew.y = p0.y + (slope*(pnew.x - p0.x));
        }
        if(flag == 2){
            pnew.y = p3.y;
            pnew.x = p0.x + ((pnew.y - p0.y)/slope);
        }
        if(flag == 0)
            pnew = p1;

        return pnew;
    }

    /// Check if line segment p0---p1 overlaps with any circle detected
    Boolean overlaps(Point p0, Point p1, ArrayList<Point3> circles, double thresh_line_overlap_circle){
        ArrayList<Point> p_arr = new ArrayList<>();
        p_arr.add(p0);
        p_arr.add(p1);

        /// take points along the line segment
        Point p2 = scalar(0.5,add(p0,p1));
        Point p3 = scalar(0.5,add(p0,p2));
        Point p4 = scalar(0.5,add(p2,p1));
        p_arr.add(p2);
        p_arr.add(p3);
        p_arr.add(p4);

        int over=0;  //check

        //cout<<"\n"<<p0<<p1;
        for(int i = 0; i < circles.size(); i++ )
        {
            Point C = new Point(circles.get(i).x, circles.get(i).y);
            double R = circles.get(i).z;

            /// check overlap with every circle
            for(int j=0; j<p_arr.size();j++){
                Point A = subtract(p_arr.get(j),C);
                double len1 = A.x*A.x + A.y*A.y;
                double len = Math.sqrt(len1) - R;
                //cout<<len<<" ";
                if(Math.abs(len)>thresh_line_overlap_circle){
                    over = 0;
                    break;
                } else {
                    over = 1;
                }
            }

            if (over == 1){
                return true;
            }
        }
        return false;
    }

    /// Remove extra lines detected by Hough Transform
    ArrayList<Vec4i> remove_duplicates(ArrayList<Vec4i> lines, ArrayList<Point3> circles, int angle_s, double thresh_line_overlap_circle, double thresh_min_line_dist){
        ///removing duplicates
        ArrayList<Vec4i> min_lines = new ArrayList<>();
        for( int i = 0; i < lines.size(); i++ ){ //check
            int flag = 0;
            for(int j = 0; j < lines.size(); j++ ){ //check
                if(i!=j){
                    Point a = new Point(lines.get(i).points[0], lines.get(i).points[1]);
                    Point b = new Point(lines.get(i).points[2], lines.get(i).points[3]);
                    Point c = new Point(lines.get(j).points[0], lines.get(j).points[1]);
                    Point d = new Point(lines.get(j).points[2], lines.get(j).points[3]);
                    Point e = new Point(0,0);
                    Boolean ans = line_intersection(a,b,c,d,e);
                    /// intersecting lines, check angle
                    if (ans){
                        double angle = angle_between(a,c,e);
                        if(angle < angle_s || angle > (180-angle_s)){
                            /// check if longer. no, discard.
                            int temp = is_shorter(a,b,c,d);
                            if (temp<0 || (temp==0 && i<j))
                                flag = 1;
                        }
                    } else {
                        /// non intersecting lines, check if nearly parallel
                        if(nearly_same(a,b,c,d,thresh_min_line_dist,angle_s)){
                            //b = extend(a,b,c,d);
                            //lines[i][2] = b.x; lines[i][3] = b.y;
                            //cout<<a<<b<<c<<d<<endl;
                            int temp = is_shorter(a,b,c,d);
                            if (temp<0 || (temp==0 && i<j)){
                                //cout<<"Flag = true"<<endl;  
                                flag = 1;
                            }
                        }
                    }
                }
            }
            if (flag == 0){
                min_lines.add(new Vec4i(lines.get(i)));
            }
        }
        lines.clear();
        for(int i=0;i<min_lines.size();i++){
            lines.add(new Vec4i(min_lines.get(i)));
        }
        min_lines.clear();

        ///Remove lines on circle boundaries
        for( int i = 0; i < lines.size(); i++ ){
            Point a = new Point(lines.get(i).points[0], lines.get(i).points[1]);
            Point b = new Point(lines.get(i).points[2], lines.get(i).points[3]);
            if(!overlaps(a,b,circles,thresh_line_overlap_circle)){
                min_lines.add(new Vec4i(lines.get(i)));
            }
        }
        return min_lines;
    }

    /// Merge similar lines detected by Hough Transform to elongate them
    ArrayList<Vec4i> merge_lines(ArrayList<Vec4i> lines,double thresh_min_line_dist,int angle_s){

        for( int i = 0; i < lines.size(); i++ ){
            for(int j = 0; j < lines.size(); j++ ){
                if(i!=j){
                    Point a = new Point(lines.get(i).points[0], lines.get(i).points[1]);
                    Point b = new Point(lines.get(i).points[2], lines.get(i).points[3]);
                    Point c = new Point(lines.get(j).points[0], lines.get(j).points[1]);
                    Point d = new Point(lines.get(j).points[2], lines.get(j).points[3]);
                    Point e = new Point(0,0);
                    if(nearly_same(a,b,c,d,thresh_min_line_dist,angle_s)){
                        b = extend(a,b,c,d);
                        lines.get(i).points[2] = (int)b.x;
                        lines.get(i).points[3] = (int)b.y;
                    }
                }
            }
        }
        return lines;
    }

/// --------------------------- Taking Average of 2 nearly similar detected lines ---------------------------

    ArrayList<Vec4i> heuristic_avg(ArrayList<Vec4i> lines,double thresh_min_line_dist ,int angle_s){
        ArrayList<Vec4i> new_lines = new ArrayList<>();
        int[] array = new int[lines.size()];
        for( int i = 0; i < lines.size(); i++ ){
            // cout<<"i = " + i<<endl;
            for(int j = i+1; j < lines.size(); j++ ){
                //  cout<<"j = " + j<<endl;
                Point a = new Point(lines.get(i).points[0], lines.get(i).points[1]);
                Point b = new Point(lines.get(i).points[2], lines.get(i).points[3]);
                Point c = new Point(lines.get(j).points[0], lines.get(j).points[1]);
                Point d = new Point(lines.get(j).points[2], lines.get(j).points[3]);
                if(nearly_same(a,b,c,d,2*thresh_min_line_dist,2*angle_s)){
                    array[i]=1;
                    array[j]=1;
                    double A_x = (a.x+c.x)/2;
                    double A_y = (a.y+c.y)/2;
                    double B_x = (b.x+d.x)/2;
                    double B_y = (b.y+d.y)/2;
                    //Point A = Point(A_x,A_y);
                    //Point B = Point(B_x,B_y);
                    new_lines.add(new Vec4i((int)A_x,(int)A_y,(int)B_x,(int)B_y));
                }

                // double dist_A = min(point_distance(a,c,d), point_distance(b,c,d));
                // double dist_B = min(point_distance(c,a,b), point_distance(d,a,b));

                // euclidean distance
                //double dist_A = Math.sqrt((a.x-c.x)*(a.x-c.x) + (a.y-c.y)*(a.y-c.y));
                //double dist_B = Math.sqrt((b.x-d.x)*(b.x-d.x) + (b.y-d.y)*(b.y-d.y));
//        if(dist_A<2.0 && dist_B<2.0){
//            flag = 1;
//            continue;
//        }

            }

        }
        for(int k=0;k<lines.size();k++){
            if(array[k]!=1){
                //cout<<"checking = "+ k<<endl;
                new_lines.add(new Vec4i(lines.get(k)));
            }
        }

        return new_lines;
    }

/// --------------------------- Combining points of two connected lines ---------------------------

    ArrayList<Vec4i> combining_end_points(ArrayList<Vec4i> lines,double thresh_end_point_combined ){

        int size = lines.size();
        int[][] array = new int [2*size][2*size];
        //int  m =0;
        for( int i = 0; i < size; i++ ){
            // cout<<"i = " + i<<endl;
            //int k = 2*i+1;
            Point a = new Point(lines.get(i).points[0], lines.get(i).points[1]);
            Point b = new Point(lines.get(i).points[2], lines.get(i).points[3]);
            double dist_AB = Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
            int m = 2*i;
            array[m][m+1]  = array[m+1][m]  = (int)dist_AB; //Extra


            for(int j = i+1; j < size; j++ ){
                //  cout<<"j = " + j<<endl;

                Point c = new Point(lines.get(j).points[0], lines.get(j).points[1]);
                Point d = new Point(lines.get(j).points[2], lines.get(j).points[3]);

                double dist_AC = Math.sqrt((a.x-c.x)*(a.x-c.x) + (a.y-c.y)*(a.y-c.y));
                double dist_BD = Math.sqrt((b.x-d.x)*(b.x-d.x) + (b.y-d.y)*(b.y-d.y));
                double dist_AD = Math.sqrt((a.x-d.x)*(a.x-d.x) + (a.y-d.y)*(a.y-d.y));
                double dist_BC = Math.sqrt((b.x-c.x)*(b.x-c.x) + (b.y-c.y)*(b.y-c.y));
                //double dist_CD = Math.sqrt((c.x-d.x)*(c.x-d.x) + (c.y-d.y)*(c.y-d.y));
                int k = 2*j;
                array[m][k]  = array[k][m]  = (int)dist_AC;
                array[m][k+1]  = array[k+1][m]  = (int)dist_AD;
                array[m+1][k]  = array[k][m+1]  = (int)dist_BC;
                array[m+1][k+1]  = array[k+1][m+1]  = (int)dist_BD;
                //array[m+2][m+3]  = array[m+3][m+2]  = dist_CD; // Extra

                //    k=k+2;

            }
            //m=m+2;

        }
        ArrayList<Integer> line_no = new ArrayList<Integer>();
        ArrayList<Integer> point_no = new ArrayList<Integer>();
        for(int p=0;p<2*size;p++){
            int alpha = 0;
            int r=0;
            int line_first = (p/2);
            int mod = p%2;
            if(mod == 1){r=2;}
            double sum_x = lines.get(line_first).points[r];
            double sum_y = lines.get(line_first).points[r+1];
            line_no.add(line_first);
            point_no.add(mod);
            for(int q=p+1;q<2*size;q++){
                if(array[p][q]<thresh_end_point_combined){
                    alpha++;
                    int s=0;
                    int mod2 = q%2;
                    if(mod2 == 1){s=2;}
                    int line_second = (q/2);
                    line_no.add(line_second);
                    point_no.add(mod2);
                    sum_x = sum_x+lines.get(line_second).points[s];
                    sum_y = sum_y+lines.get(line_second).points[s+1];
                }
            }
            if(alpha>0){
                double x = sum_x / (alpha+1) ;
                double y = sum_y / (alpha+1) ;
                while(alpha>=0){
                    int l_n = line_no.get(line_no.size()-1);
                    line_no.remove(line_no.size()-1);
                    int p_n = point_no.get(point_no.size()-1);
                    point_no.remove(point_no.size()-1);
                    lines.get(l_n).points[2*p_n] = (int)x;
                    lines.get(l_n).points[2*p_n+1] =(int)y;
                    alpha--;
                }
            }
            line_no.clear();point_no.clear();
        }

        return lines;
    }

    /// --------------------------- MAIN ---------------------------

    /** @function main */
    int extract( String[] args) throws IOException
    {
        Mat src_gray = new Mat();
        Mat src = Imgcodecs.imread(args[1], Imgcodecs.IMREAD_COLOR);
//        src = imread( argv[1], 1 );
        int a1=5;   // Initial threshold value of angle_small = 5
        double thresh_line_overlap_circle = 15.0; // Initial threshold value for deciding if a line overlaps with any circle,
        // the lesser the value, the stricter the bound

        double thresh_min_line_dist = 10.0;       // Initial threshold value for deciding if two end lines are nearly same
        double thresh_end_point_combined = 30.0;  // Initial threshold value for deciding if two end points of lines are connected
        int erode_sz = 1;
        int thresh_detect_line = 80;              // The minimum number of intersections to “detect” a line - CV_HL
        int thresh_min_line_length = 50;          // The minimum number of points that can form a line.
        // Lines with less than this number of points are disregarded - CV_HL
        int thresh_min_line_gap = 50;             // The maximum gap between two points to be considered in the same line - CV_HL
        if(args.length != 2 ){
            a1 = Integer.parseInt(args[2]);
            thresh_line_overlap_circle = Float.parseFloat(args[3]);
            thresh_min_line_dist = Integer.parseInt(args[4]);
            thresh_end_point_combined = Float.parseFloat(args[5]);
            erode_sz =  Integer.parseInt(args[6]);
            thresh_detect_line =  Integer.parseInt(args[7]);
            thresh_min_line_length =  Integer.parseInt(args[8]);
            thresh_min_line_gap =  Integer.parseInt(args[9]);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(args[0] + "\\" +"output.txt",false));
        /// Read the image

        if(src.empty()){ //check
            return -1;
        }
        Mat dst = src.clone();
        int i = 29;
        bilateralFilter(src,dst,i,i*2,i/2);
        src = dst;
        /// Convert it to gray
        cvtColor( src, src_gray, COLOR_BGR2GRAY );

        /// Reduce the noise so we avoid false circle detection
        GaussianBlur( src_gray, src_gray, new Size(9, 9), 2, 2 );

        /// -----------------------------------CIRCLE DETECTION----------------------------------
        ArrayList<Point3> circles = new ArrayList<>();
        int thresh = 40;

        for(i=0;i<7;i++){
            Mat tempCircles = new Mat();
            thresh = 80;
            Boolean flag = true;
            while (flag){
                HoughCircles( src_gray, tempCircles, HOUGH_GRADIENT, 1, src_gray.rows()/8, thresh*2, thresh, 150*i, 150*(i+1));
                //cout<<thresh<<" "<<i<<"\n";
                if((!tempCircles.empty()) || thresh <= 60){
                    flag = false;
                } else {
                    thresh = thresh - 10;
                }
            }
//            tempCircles.get(0,0,new byte[tempCircles.width()*tempCircles.height()]); //check
//            for(long b=0;b<cir.size();b++)
//            circles.addAll(cir);
            System.out.println(tempCircles.cols());
            for(int j=0;j<tempCircles.cols();j++){
                double[] data = tempCircles.get(0,j);
                circles.add(new Point3(data[0],data[1],data[2]));
            }
        }


        /// -----------------------------------LINE DETECTION-----------------------------------
        Mat blursrc = new Mat(), dst2 = new Mat(), cdst = new Mat();

        /// Eroding to avoid double edges

        //GaussianBlur( src, blursrc, Size(9, 9), 2, 2 );
        // int erode_sz = 2;
        Mat element = getStructuringElement(MORPH_CROSS,new Size(2*erode_sz + 1, 2*erode_sz + 1), new Point(erode_sz, erode_sz));
        erode(src,blursrc,element);
//        imshow("abc",blursrc);

        /// Detecting lines
        Canny(blursrc, dst2, 50, 200, 3,true);
        cvtColor(dst2, cdst, COLOR_GRAY2BGR);

        Mat lineMatrix = new Mat();
        ArrayList<Vec4i> lines = new ArrayList<>(), min_lines = new ArrayList<>(), new_lines = new ArrayList<>();
        HoughLinesP( dst2, lineMatrix, 1, Math.PI/180, thresh_detect_line, thresh_min_line_length, thresh_min_line_gap );

        System.out.println(lineMatrix.cols() + " " + lineMatrix.rows());
//        for(int j=0;j<lineMatrix.cols();j++){
//            double[] points = lineMatrix.get(0,j);
//            lines.add(new Vec4i((int)points[0],(int)points[1],(int)points[2],(int)points[3]));
//        }
        for(int j=0;j<lineMatrix.rows();j++){
            double[] points = lineMatrix.get(j,0);
            lines.add(new Vec4i((int)points[0],(int)points[1],(int)points[2],(int)points[3]));
        }

        /// Remove extra lines
        lines = merge_lines(lines,thresh_min_line_dist,a1);
        lines = remove_duplicates(lines,circles,a1,thresh_line_overlap_circle,thresh_min_line_dist);
        lines = heuristic_avg(lines,thresh_min_line_dist,a1);
        lines = combining_end_points(lines,thresh_end_point_combined);

        /// ----------------------------------WRITING ON FILE-----------------------------------
        /// Draw the lines detected

        int j ,k= 0 ;
        for (i = 0; i < lines.size(); i++) {
            if(i%2==0){j=255;k=0;}
            else {k = 255;j=0;}
            line(src, new Point(lines.get(i).points[0], lines.get(i).points[1]),
                    new Point(lines.get(i).points[2], lines.get(i).points[3]), new Scalar(255,0,255), 3, Core.LINE_AA,0) ; //check shift
            writer.append("l-" +lines.get(i).points[0] +"," +lines.get(i).points[1]+"," +lines.get(i).points[2]+","+lines.get(i).points[3]+ "\n");
        }


        // cout<<"Number of lines: "<<lines.size()<<endl;
        // cout<<"Number of circles: "<<circles.size()<<endl;

        /// Draw the circles detected
        for( i = 0; i < circles.size(); i++ )
        {
            Point center = new Point(Math.round(circles.get(i).x), Math.round(circles.get(i).y));
            int radius = (int)Math.round(circles.get(i).z);
            // circle center
            circle( src, center, 3, new Scalar(0,255,0), -1, 8, 0 );
            // circle outline
            circle( src, center, radius, new Scalar(0,0,255), 3, 8, 0 );
            writer.append("c-"+center.x+","+center.y+","+radius+"\n");
        }

        /// Show your results
//        namedWindow( "Hough Circle Transform Demo", WINDOW_NORMAL );
//        imshow( "Hough Circle Transform Demo", src);
//        //imshow("detected lines", cdst);
//        myfile.close();
//        //imwrite("image_out.jpg",src);

//        waitKey(0);
        writer.close();
        return 0;
    }
}
