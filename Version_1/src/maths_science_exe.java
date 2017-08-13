import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class maths_science_exe {
    int r =0;
    Screen t1 = new Screen(r);
    public void load() throws FileNotFoundException, IOException, InterruptedException{
        String h1 = t1.currentFile.getAbsolutePath();
        String h2 = String.valueOf(t1.currentFile.getName());
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
        
        
        
        int angle_s = 5; // Initial threshold value of angle_small = 5
        float thresh_line_overlap_circle = (float) 20.0; // Initial threshold value for deciding if a line overlaps with any circle,
        // the lesser the value, the stricter the bound  
        float min_dist = (float) 10.0; // Initial threshold value for deciding if two end lines are nearly same
        float thresh_end_point_combined = (float) 30.0; // Initial threshold value for deciding if two end points of lines are connected
        int erode_sz = 1;
        int thresh_detect_line = 80; // The minimum number of intersections to “detect” a line - CV_HL
        int thresh_min_line_length = 50; // The minimum number of points that can form a line. 
        // Lines with less than this number of points are disregarded - CV_HL
        int thresh_min_line_gap = 50; // The maximum gap between two points to be considered in the same line - CV_HL
    
        min_dist = Screen.allControlsAndListeners.duplicateLineDetectionByDistance.getValue();
        angle_s = Screen.allControlsAndListeners.duplicateLineDetectionByAngle.getValue();
        Process pr = rt.exec("\""+ Screen.config.get("maths.exe")+"\""+" "
                +"\""+outputfile1.getAbsolutePath()+"\""+" "
                +angle_s+" "
                +thresh_line_overlap_circle+" "
                +min_dist+" "
                +thresh_end_point_combined+" "
                +erode_sz+" "
                +thresh_detect_line+" "
                +thresh_min_line_length+" "
                +thresh_min_line_gap
                ,null,dir);
        //Process pr = rt.exec("maths.exe " +"temp_bw.png");
        pr.waitFor();
        //outputfile.delete();
        
        System.out.println("Math done");
        int ind=outputfile1.getAbsolutePath().lastIndexOf("\\");
        //File f = new File(outputfile1.getAbsolutePath().substring(0, ind+1)+"output.txt");
        File f = new File(Screen.config.get("library_directory_path")+"//output.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8"));
        String s1 = br.readLine();
        while(s1!=null){
            if(s1.contains("l-")){
                //System.out.println("line detected : "+s1);
                String [] fr = s1.split("-");
                String [] gt = fr[1].split(",");
                int h11 = Integer.valueOf(gt[0]);int h12=Integer.valueOf(gt[1]);
                Pair<Integer,Integer> temp_pair = new Pair <> (h11,h12);
                h11 = Integer.valueOf(gt[2]);h12=Integer.valueOf(gt[3]);
                Pair<Integer,Integer> temp_pair_2 = new Pair <> (h11,h12);
                boolean match = false;
                for(int i = 0; i< Screen.linesObject.lines.size()-1; i+=2){
                    if(Screen.linesObject.lines.get(i).equals(temp_pair)&& Screen.linesObject.lines.get(i+1).equals(temp_pair_2)){
                        match = true;
                        //System.out.println(match);
                        break;
                    }
                }
                if(!match){
                    //System.out.println("added a line");
                    Screen.linesObject.lines.add(temp_pair);
                    Screen.linesObject.colorArray.add(Screen.currentColor);
                    Screen.linesObject.lines.add(temp_pair_2);
                    Screen.linesObject.colorArray.add(Screen.currentColor);
                }
            }
            if(s1.contains("c-")){
                String [] fr = s1.split("-");
                String [] gt = fr[1].split(",");
                float f1 = Float.valueOf(gt[0]);
                float f2 = Float.valueOf(gt[1]);
                float f3 = Float.valueOf(gt[2]);
                Pair<Float,Float> temp_pair = new Pair <> (f1,f2);
                boolean match = false;
                for(int i = 0; i< Screen.circlesObject.centers.size(); i++){
                    if(Screen.circlesObject.centers.get(i).equals_float(temp_pair)){
                        if (Math.abs(Screen.circlesObject.radii.get(i)-f3)<=3) {
                            match = true;
                            break;
                        }
                    }
                }
                if(!match){
                    Screen.circlesObject.centers.add(temp_pair);
                    Screen.circlesObject.radii.add(f3);
                    Screen.circlesObject.colorArray.add(Screen.currentColor);
                    Screen.circlesObject.fillArray.add(0);
                }
            }
            s1 = br.readLine();
        }
        br.close();
        f.delete();
    }
    public void load_science() throws FileNotFoundException, IOException, InterruptedException{
        String h1 = t1.currentFile.getAbsolutePath();
        String h2 = String.valueOf(t1.currentFile.getName());
        String ty = h2.substring(0,h2.lastIndexOf("."))+"_1"+h2.substring(h2.lastIndexOf("."));
        String h4 = h1;
        int index=h4.lastIndexOf("\\");
        h4=h4.substring(0, index+1)+ty;
        File outputfile1 = new File(h4);
        Runtime rt = Runtime.getRuntime();
        File dir = new File(Screen.config.get("library_directory_path"));
        System.out.println("Science start");
        Process pr = rt.exec("\""+ Screen.config.get("science.exe")+"\""+" "+"\""+outputfile1.getAbsolutePath()+"\"",null,dir);
        pr.waitFor();
        System.out.println("Science done");
        File f = new File(Screen.config.get("library_directory_path")+"//regions.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8"));
        String s1 = br.readLine();
        while(s1!=null){
            String [] l = s1.split(",");
            int [] points = new int[l.length];
            for(int i=0;i<l.length;i++){
                points[i] = Integer.valueOf(l[i]);
            }
            ArrayList<Pair<Integer,Integer>> temp_Points_regions=new ArrayList<>();
            for(int i=0;i<l.length-1;i++){
                Pair<Integer,Integer> temp_pair = new Pair <> (points[i],points[i+1]);
                i++;
                temp_Points_regions.add(temp_pair);
            }
            Pair<Integer,Integer> temp_pair = new Pair <> (points[0],points[1]);
            temp_Points_regions.add(temp_pair);
            boolean match = false;
            int temp_index;
            for (ArrayList<Pair<Integer, Integer>> Region : Screen.pathsObject.regions) {
                temp_index = Region.size() - 2;
                //if (Region.size() == temp_Points_regions.size() && Region.get(0).equals(temp_Points_regions.get(0)) && Region.get(temp_index).equals(temp_Points_regions.get(temp_index))) {
                if (Region.size() == temp_Points_regions.size()&&Region.get(0).equals(temp_Points_regions.get(0)) && Region.get(temp_index).equals(temp_Points_regions.get(temp_index))) {
                    match = true;
                    break;
                }
            }
            if(!match){
                Screen.pathsObject.regions.add(temp_Points_regions);
                Random rand = new Random();
                Color randomColor = new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat());
                Screen.pathsObject.colorArray.add(randomColor);
            }
            s1 = br.readLine();
        }
        br.close();
        f.delete();
    }
}
