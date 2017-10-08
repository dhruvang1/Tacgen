import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScienceExtraction {
    private int threshold = 100;

    public void extract(String srcImgPath, String destImgPath) throws IOException{
        Mat imgSrc = Imgcodecs.imread(srcImgPath, Imgcodecs.IMREAD_COLOR);
        if (imgSrc.empty()){

        }
        Mat imgGray = new Mat();
        Imgproc.cvtColor(imgSrc,imgGray,Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(imgGray,imgGray,new Size(3.0,3.0));

        Mat cannyOutput = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();
        Mat heirarchy = new Mat();
        Imgproc.Canny(imgGray,cannyOutput,threshold,threshold*2,3,false);

        Imgproc.findContours(cannyOutput,contours,heirarchy,Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
        BufferedWriter writer = new BufferedWriter(new FileWriter(destImgPath+"\\"+"regions.txt",false));
        for(int i=0;i<contours.size();i++){
            List <Point> tempContour = contours.get(i).toList();
            int tempSize = tempContour.size();
            for(int j=0;j<tempSize-1;j++){
                writer.append(String.format("%d,%d,",(int)tempContour.get(j).x,(int)tempContour.get(j).y));
            }
            writer.append(String.format("%d,%d\n",(int)tempContour.get(tempSize-1).x,(int)tempContour.get(tempSize-1).y));
        }
        writer.close();
    }
}
