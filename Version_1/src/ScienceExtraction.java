import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScienceExtraction {
    private int threshold = 100;
    private double epsilon = 2;

    public void extract(String srcImgPath, String destImgPath, HashMap<String,String> args) throws IOException{
        Mat imgSrc = Imgcodecs.imread(srcImgPath, Imgcodecs.IMREAD_COLOR);
        if (imgSrc.empty()){

        }
        Mat imgGray = new Mat();
        Imgproc.cvtColor(imgSrc,imgGray,Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(imgGray,imgGray,new Size(3.0,3.0));



        Mat cannyOutput = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();
        ArrayList<ArrayList<Point>> new_contours;
        Mat heirarchy = new Mat();

        Imgproc.Canny(imgGray,cannyOutput,threshold,threshold*2,3,false);
//        Imgcodecs.imwrite("D:\\MTP\\TacGen\\canny.jpg",cannyOutput);

        if(Boolean.valueOf(args.get("dilationCheck"))){
            int dilation_size =  Integer.parseInt(args.get("dilationValue"));
            Mat elementDilation = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(dilation_size, dilation_size));
            Imgproc.dilate(cannyOutput,cannyOutput,elementDilation);
        }
//        Imgcodecs.imwrite("C:\\Users\\Dhruvang\\Desktop\\dilate.jpg",cannyOutput);

        if(Boolean.valueOf(args.get("erosionCheck"))){
            int erosion_size = Integer.parseInt(args.get("erosionValue"));
            Mat elementErosion = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(erosion_size , erosion_size));
            Imgproc.erode(cannyOutput,cannyOutput,elementErosion);
        }
//        Imgcodecs.imwrite("C:\\Users\\Dhruvang\\Desktop\\erode.jpg",cannyOutput);

        Imgproc.findContours(cannyOutput,contours,heirarchy,Imgproc.RETR_CCOMP,Imgproc.CHAIN_APPROX_TC89_KCOS, new Point(0,0));
//        ContourDetection contourDetection = new ContourDetection();
//        new_contours = contourDetection.findContours(cannyOutput);
//
        BufferedWriter writer = new BufferedWriter(new FileWriter(destImgPath+"\\"+"regions.txt",false));
        int originalPoints = 0;
        int reducedPoints = 0;
//        for(int i=0;i<new_contours.size();i++){
//            List <Point> tempContour = new_contours.get(i);
//            originalPoints += tempContour.size();
////            tempContour = reducePoints(tempContour,0,tempContour.size()-1);
//            reducedPoints += tempContour.size();
//            int tempSize = tempContour.size();
//            for(int j=0;j<tempSize-1;j++){
//                writer.append(String.format("%d,%d,",(int)tempContour.get(j).x,(int)tempContour.get(j).y));
//            }
//            writer.append(String.format("%d,%d\n",(int)tempContour.get(tempSize-1).x,(int)tempContour.get(tempSize-1).y));
//
//        }

        boolean toReduce = Boolean.valueOf(args.get("reduceNodesCheck"));

        for(int i=0;i<contours.size();i++){
            if(heirarchy.get(0,i)[3] == -1){
                List <Point> tempContour = contours.get(i).toList();
                originalPoints += tempContour.size();
                if(toReduce){
                    epsilon = Double.valueOf(args.get("reduceNodesValue"));
                    tempContour = reducePoints(tempContour,0,tempContour.size()-1);
                }
                reducedPoints += tempContour.size();
                int tempSize = tempContour.size();
                for(int j=0;j<tempSize-1;j++){
                    writer.append(String.format("%d,%d,",(int)tempContour.get(j).x,(int)tempContour.get(j).y));
                }
                writer.append(String.format("%d,%d\n",(int)tempContour.get(tempSize-1).x,(int)tempContour.get(tempSize-1).y));
            }
        }
        System.out.println("Original points: " + originalPoints);
        System.out.println("Reduced points: " + reducedPoints);
        writer.close();
    }

    //Douglas–Peucker algorithm
    public List<Point> reducePoints(List<Point> contour, int start, int end) {
        double dmax = 0;
        int index = 0;
        for(int i=start+1;i<=end-1;i++){
            double distance = pointLineDistance(contour.get(start),contour.get(end),contour.get(i));
//            if(distance == 0){
//                System.out.println("zero distance " + contour.get(start) + contour.get(end) + contour.get(i));
//            }
            if(distance > dmax){
                dmax = distance;
                index = i;
            }
        }

        //change it to % of length
        List<Point> pointList = new ArrayList<>();
        if(dmax > epsilon){
            List<Point> pointList1 = reducePoints(contour,start,index);
            List<Point> pointList2 = reducePoints(contour,index,end);
            pointList1.remove(pointList1.size()-1); // index element will repeat
            pointList.addAll(pointList1);
            pointList.addAll(pointList2);
        } else {
            pointList.add(contour.get(start));
            pointList.add(contour.get(end));
        }
        return pointList;
    }

    public double pointLineDistance(Point start, Point end, Point point){
        if(start == end){
            return Math.sqrt((start.x - point.x)*(start.x - point.x) + (start.y - point.y)*(start.y - point.y));
        }

        double a = (start.y - end.y);
        double b = (end.x - start.x);
        double c = start.x*end.y - start.y*end.x;

        return Math.abs(a*point.x + b*point.y + c) / Math.sqrt(a*a+b*b);
    }
}
