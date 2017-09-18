import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class to run math/science extraction
 */
public class maths_science_exe {

    private Screen screen = new Screen(0);

    public void load() throws IOException, InterruptedException{
        String h1 = Screen.currentFile.getAbsolutePath();
        String h2 = String.valueOf(Screen.currentFile.getName());
        String ty = h2.substring(0,h2.lastIndexOf("."))+"_1"+h2.substring(h2.lastIndexOf("."));
        String h4 = h1;
        int index=h4.lastIndexOf("\\");
        h4=h4.substring(0, index+1)+ty;
        File outputfile1 = new File(h4);
        
        // clear all data structures related to maths
        Screen.linesObject = new GetLines();
    	Screen.circlesObject = new GetCircles();
    	Screen.regionsObject = new GetRegions();
    	Screen.polygonObject = new GetPolygon();
        Screen.arcObject = new GetArc();
        Screen.pathsObject = new GetPaths();
        
        
//        BufferedImage temp = ImageIO.read(outputfile1);
//        int r = 255;
//        int g = 255;
//        int b = 255;
//        int col = (r << 16) | (g << 8) | b;
//        int col_black = 0;
//        int h = temp.getHeight();
//        int w = temp.getWidth();
//        int count_black = 0;
//        int count_white = 0;
//        for(int f1 = 0;f1<h;f1++){
//            for(int g1 = 0;g1<w;g1++){
//                int sRbgColor = temp.getRGB(g1,f1);
//                Color c = new Color(sRbgColor);
//                int red = c.getRed();
//                int green = c.getGreen();
//                int blue = c.getBlue();
//                if(red<240 && green <240){
//                    temp.setRGB(g1, f1, col);
//                    count_white++;
//                }
//                else{
//                    count_black++;
//                    temp.setRGB(g1, f1, col_black);
//                }
//            }
//        }
//        System.out.println(count_white);
//        System.out.println(count_black);
//        File outputfile = new File("temp_bw.png");
//        ImageIO.write(temp, "PNG", outputfile);
        
        Runtime rt = Runtime.getRuntime();
        File dir = new File(Screen.config.get("library_directory_path"));
        System.out.println("Math start");
        
        
        
        float thresh_line_overlap_circle = (float) 20.0; // Initial threshold value for deciding if a line overlaps with any circle,
        // the lesser the value, the stricter the bound  
//        float minDistForSimilarLines = (float) 10.0; // Initial threshold value for deciding if two end lines are nearly same
        float thresholdForLineConnectedEndPoints = (float) 30.0; // Initial threshold value for deciding if two end points of lines are connected
        int erodeSize = 1;
        int minNumIntersectionsToDetectLine = 80; // The minimum number of intersections to “detect” a line - CV_HL
        int minNumPointsForLine = 50; // The minimum number of points that can form a line. Lines with less than this number of points are disregarded - CV_HL
        int thresholdGapBetweenTwoPointsInSameLine = 50; // The maximum gap between two points to be considered in the same line - CV_HL
    
        int minDistForSimilarLines = Screen.allControlsAndListeners.duplicateLineDetectionByDistance.getValue();
        int angleSmall = Screen.allControlsAndListeners.duplicateLineDetectionByAngle.getValue();
        Process pr = rt.exec("\""+ Screen.config.get("maths.exe")+"\""+" "
                +"\""+outputfile1.getAbsolutePath()+"\""+" "
                + angleSmall +" "
                +thresh_line_overlap_circle+" "
                + minDistForSimilarLines +" "
                + thresholdForLineConnectedEndPoints +" "
                + erodeSize +" "
                + minNumIntersectionsToDetectLine +" "
                + minNumPointsForLine +" "
                + thresholdGapBetweenTwoPointsInSameLine
                ,null,dir);
        //Process pr = rt.exec("maths.exe " +"temp_bw.png");
        pr.waitFor();
        //outputfile.delete();
        
        System.out.println("Math done");
//        int ind=outputfile1.getAbsolutePath().lastIndexOf("\\");
        //File file = new File(outputfile1.getAbsolutePath().substring(0, ind+1)+"output.txt");
        File file = new File(Screen.config.get("library_directory_path")+"//output.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        String line = bufferedReader.readLine();
        while(line!=null){
            if(line.contains("l-")){
                //System.out.println("line detected : "+line);
                String [] fr = line.split("-");
                String [] gt = fr[1].split(",");
                int h11 = Integer.valueOf(gt[0]);int h12=Integer.valueOf(gt[1]);
                Pair<Integer,Integer> tempPair = new Pair <> (h11,h12);
                h11 = Integer.valueOf(gt[2]);h12=Integer.valueOf(gt[3]);
                Pair<Integer,Integer> tempPair2 = new Pair <> (h11,h12);
                boolean match = false;
                for(int i = 0; i< Screen.linesObject.lines.size()-1; i+=2){
                    if(Screen.linesObject.lines.get(i).equals(tempPair)&& Screen.linesObject.lines.get(i+1).equals(tempPair2)){
                        match = true;
                        //System.out.println(match);
                        break;
                    }
                }
                if(!match){
                    //System.out.println("added a line");
                    Screen.linesObject.lines.add(tempPair);
                    Screen.linesObject.colorArray.add(Screen.currentColor);
                    Screen.linesObject.lines.add(tempPair2);
                    Screen.linesObject.colorArray.add(Screen.currentColor);
                }
            }
            if(line.contains("c-")){
                String [] fr = line.split("-");
                String [] gt = fr[1].split(",");
                float f1 = Float.valueOf(gt[0]);
                float f2 = Float.valueOf(gt[1]);
                float f3 = Float.valueOf(gt[2]);
                Pair<Float,Float> tempPair = new Pair <> (f1,f2);
                boolean match = false;
//                for(int i = 0; i< Screen.circlesObject.centers.size(); i++){
//                    if(Screen.circlesObject.centers.get(i).equalsFloat(tempPair)){
//                        if (Math.abs(Screen.circlesObject.radii.get(i)-f3)<=3) {
//                            match = true;
//                            break;
//                        }
//                    }
//                }
//                if(!match){
//                    Screen.circlesObject.centers.add(tempPair);
//                    Screen.circlesObject.radii.add(f3);
//                    Screen.circlesObject.colorArray.add(Screen.currentColor);
//                    Screen.circlesObject.fillArray.add(0);
//                }
                for(int i = 0; i< Screen.circlesObject.allCircles.size(); i++){
                    if(Screen.circlesObject.allCircles.get(i).center.equalsFloat(tempPair)){
                        if (Math.abs(Screen.circlesObject.allCircles.get(i).radius-f3)<=3) {
                            match = true;
                            break;
                        }
                    }
                }
                if(!match){
//                    Screen.circlesObject.centers.add(tempPair);
//                    Screen.circlesObject.radii.add(f3);
//                    Screen.circlesObject.colorArray.add(Screen.currentColor);
//                    Screen.circlesObject.fillArray.add(0);
                    GetCircles.Circle tempCircle = Screen.circlesObject.new Circle();
                    tempCircle.center = tempPair;
                    tempCircle.radius = f3;
                    tempCircle.color = Screen.currentColor;
                    tempCircle.fill = 0;
                    Screen.circlesObject.allCircles.add(tempCircle);
                }
            }
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        file.delete();
    }
    public void load_science() throws IOException, InterruptedException{
        String h1 = screen.currentFile.getAbsolutePath();
        String h2 = String.valueOf(screen.currentFile.getName());
        String ty = h2.substring(0,h2.lastIndexOf("."))+"_1"+h2.substring(h2.lastIndexOf("."));
        String h4 = h1;
        int index=h4.lastIndexOf("\\");
        h4=h4.substring(0, index+1)+ty;
        File outputFile = new File(h4);
        Runtime runtime = Runtime.getRuntime();
        File dir = new File(Screen.config.get("library_directory_path"));
        System.out.println("Science start");
        Process process = runtime.exec("\""+ Screen.config.get("science.exe")+"\""+" "+"\""+ outputFile.getAbsolutePath()+"\"",null,dir);
        process.waitFor();
        System.out.println("Science done");
        File file = new File(Screen.config.get("library_directory_path")+"//regions.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        String line = bufferedReader.readLine();
        while(line!=null){
            String [] l = line.split(",");
            int [] points = new int[l.length];
            for(int i=0;i<l.length;i++){
                points[i] = Integer.valueOf(l[i]);
            }
            ArrayList<Pair<Integer,Integer>> tempPointsRegions=new ArrayList<>();
            for(int i=0;i<l.length-1;i++){
                Pair<Integer,Integer> temp_pair = new Pair <> (points[i],points[i+1]);
                i++;
                tempPointsRegions.add(temp_pair);
            }
            Pair<Integer,Integer> tempPair = new Pair <> (points[0],points[1]);
            tempPointsRegions.add(tempPair);
            boolean match = false;
            int tempIndex;
            for (ArrayList<Pair<Integer, Integer>> Region : Screen.pathsObject.paths) {
                tempIndex = Region.size() - 2;
                //if (Region.size() == tempPointsRegions.size() && Region.get(0).equals(tempPointsRegions.get(0)) && Region.get(tempIndex).equals(tempPointsRegions.get(tempIndex))) {
                if (Region.size() == tempPointsRegions.size()&&Region.get(0).equals(tempPointsRegions.get(0)) && Region.get(tempIndex).equals(tempPointsRegions.get(tempIndex))) {
                    match = true;
                    break;
                }
            }
            if(!match){
                Screen.pathsObject.paths.add(tempPointsRegions);
                Random rand = new Random();
                Color randomColor = new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat());
                Screen.pathsObject.colorArray.add(randomColor);
            }
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        file.delete();
    }
}
