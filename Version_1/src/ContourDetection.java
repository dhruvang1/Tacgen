import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ContourDetection {
    private int threshold = 100;
    public ArrayList<ArrayList<Point>> findContours(Mat src){
        Mat counter =  Mat.zeros(src.rows(),src.cols(),src.type());
        ArrayList<ArrayList<Point>> contours = new ArrayList<>();
        for(int i=0;i<src.rows();i++){
            for(int j=0;j<src.cols();j++){
                if(src.get(i,j)[0] == 255 && counter.get(i,j)[0] == 0){
                    contours.add(traceBoundary(src,counter,i,j));
                }
            }
        }

//        ScienceExtraction scienceExtraction = new ScienceExtraction();
//        ArrayList<List> reducedContours = new ArrayList<>();
//        for(int i=0;i<contours.size();i++){
//            reducedContours.add(scienceExtraction.reducePoints(contours.get(i),0,contours.get(i).size()-1));
//        }

        return contours;
    }

    private ArrayList<Point> traceBoundary(Mat imgGray, Mat image, int i, int j){
        int rows = imgGray.rows();
        int cols = imgGray.cols();
        ArrayList<Point> contour = new ArrayList<>();
        contour.add(new Point(j,i));
        double []temp2 = {1};
        image.put(i,j,temp2);
        while(true){
            double []temp = {1};
            if(i > 0 && imgGray.get(i-1,j)[0] == 255 && image.get(i-1,j)[0]==0){     //N
                i--;
            }
            else if(i>0 && j>0 && imgGray.get(i-1,j-1)[0] == 255 && image.get(i-1,j-1)[0]==0){      //NW
                i--;j--;
            }
            else if(j>0 && imgGray.get(i,j-1)[0] == 255 && image.get(i,j-1)[0]==0){      //W
                j--;
            }
            else if(i < rows-1 && j>0 && imgGray.get(i+1,j-1)[0] == 255 && image.get(i+1,j-1)[0]==0){      //SW
                i++;j--;
            }
            else if(i < rows-1 && imgGray.get(i+1,j)[0] == 255 && image.get(i+1,j)[0]==0){      //S
                i++;
            }
            else if(i < rows-1 && j< cols-1 && imgGray.get(i+1,j+1)[0] == 255 && image.get(i+1,j+1)[0]==0){   //SE
                i++;j++;
            }
            else if(j < cols-1 && imgGray.get(i,j+1)[0] == 255 && image.get(i,j+1)[0]==0){      //E
                j++;
            }
            else if(i > 0 && j < cols-1 && imgGray.get(i-1,j+1)[0] == 255 && image.get(i-1,j+1)[0]==0){    //NE
                i--;j++;
            }
            else{
                break;
            }
            image.put(i,j,temp);
            contour.add(new Point(j,i));
        }
        if(contour.size() < 4){
            System.out.println("Small contour");
        }
        return contour;
    }
}
