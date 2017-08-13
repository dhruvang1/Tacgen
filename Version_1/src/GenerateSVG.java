import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import sun.security.pkcs11.wrapper.Constants;

public class GenerateSVG {
    Screen screen = new Screen(0);
    boolean isSVGBlank;
    File directory;
    public void svgLines() throws FileNotFoundException, IOException{
        File file = new File(directory,"line.svg");
        
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//        outputStreamWriter.append("<svg>");
        outputStreamWriter.append(Constants.NEWLINE);
        if(Screen.linesObject.lines.size()>1){
            isSVGBlank = false;
        }
        for(int i = 0; i< Screen.linesObject.lines.size()-1; i++){
            outputStreamWriter.append("<line x1="+"\""+ Screen.linesObject.lines.get(i).getL()+"\" ");
            outputStreamWriter.append("y1="+"\""+ Screen.linesObject.lines.get(i).getR()+"\" ");
            i++;
            outputStreamWriter.append("x2="+"\""+ Screen.linesObject.lines.get(i).getL()+"\" ");
            outputStreamWriter.append("y2="+"\""+ Screen.linesObject.lines.get(i).getR()+"\" ");
            String hash_code = rgbToHash(Screen.linesObject.colorArray.get(i).getRed(),
                    Screen.linesObject.colorArray.get(i).getGreen(),
                    Screen.linesObject.colorArray.get(i).getBlue());
            outputStreamWriter.append("stroke="+"\""+"#"+hash_code+"\"" +"/>");
            outputStreamWriter.append(Constants.NEWLINE);
        }
        outputStreamWriter.close();
    }

    public void svgArc() throws FileNotFoundException, IOException{
        File file = new File(directory,"arc.svg");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//        outputStreamWriter.append("<svg>");
        outputStreamWriter.append(Constants.NEWLINE);
        if(Screen.arcObject.radii.size()>0){
            isSVGBlank = false;
        }
        for(int i = 0; i< Screen.arcObject.radii.size(); i++){
            float centerX = Screen.arcObject.centers.get(i).getL();
            float centerY = Screen.arcObject.centers.get(i).getR();
            float radius = Screen.arcObject.radii.get(i);
            int angle1 = Screen.arcObject.arcAngles.get(i).getL();
            int angle2 = Screen.arcObject.arcAngles.get(i).getR();
            int angle3 = (angle1 + angle2)%360;
            float s1 = (float) (centerX + radius *Math.cos(Math.toRadians(angle1)));
            float s2 = (float) (centerY - radius *Math.sin(Math.toRadians(angle1)));
            float e1 = (float) (centerX + radius *Math.cos(Math.toRadians(angle3)));
            float e2 = (float) (centerY - radius *Math.sin(Math.toRadians(angle3)));
            int lsa = 0; //don't know what is this
            if(Screen.arcObject.arcAngles.get(i).getR()>180){
                lsa = 1;
            }
            String hash_code = rgbToHash(Screen.arcObject.colorArray.get(i).getRed(),
                    Screen.arcObject.colorArray.get(i).getGreen(),
                    Screen.arcObject.colorArray.get(i).getBlue());
            
            outputStreamWriter.append("<path d="+"\"M "+s1+" "+s2+" A "+ radius +" "+ radius +", 0, "+lsa+", 0, "+e1+" "+e2+"\" ");
            //outputStreamWriter.append("style="+"\""+"stroke:#006600; fill:#"+hash_code+";"+"\"" +"/>");
            if(Screen.arcObject.fillArray.get(i)==0){
                outputStreamWriter.append("stroke=\"#"+hash_code+"\" fill=\""+"none"+"\"" +"/>");//outputStreamWriter.append("stroke=\"#006600\" fill=\""+"none"+"\"" +"/>");
            }
            else{
                outputStreamWriter.append("stroke=\"#006600\" fill=\"#"+hash_code+"\"" +"/>");
            }
            outputStreamWriter.append(Constants.NEWLINE);
        }
//        outputStreamWriter.append("</svg>");
        outputStreamWriter.close();
    }    
    public String rgbToHash(int r, int g, int b){
        String redHex = Integer.toHexString(r);
        if(redHex.length()==1){
            redHex = "0"+ redHex;
        }
        String greenHex = Integer.toHexString(g);
        if(greenHex.length()==1){
            greenHex = "0"+ greenHex;
        }
        String blueHex = Integer.toHexString(b);
        if(blueHex.length()==1){
            blueHex = "0"+ blueHex;
        }
        return redHex + greenHex + blueHex;
    }
    public void svgCircles() throws FileNotFoundException, IOException{
        File file = new File(directory,"circle.svg");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//        outputStreamWriter.append("<svg>");
        outputStreamWriter.append(Constants.NEWLINE);
        if(Screen.circlesObject.radii.size()>0){
            isSVGBlank = false;
        }
        for(int i = 0; i< Screen.circlesObject.radii.size(); i++){
        //    <circle cx="196.0" cy="172.0" r="138.0" stroke="#006600" fill="#ffffff"/>
            outputStreamWriter.append("<circle cx="+"\""+ Screen.circlesObject.centers.get(i).getL()+"\" ");
            outputStreamWriter.append("cy="+"\""+ Screen.circlesObject.centers.get(i).getR()+"\" ");
            outputStreamWriter.append("r="+"\""+ Screen.circlesObject.radii.get(i)+"\" ");
            String hashCode = rgbToHash(Screen.circlesObject.colorArray.get(i).getRed(),
                    Screen.circlesObject.colorArray.get(i).getGreen(),
                    Screen.circlesObject.colorArray.get(i).getBlue());
            if(Screen.circlesObject.fillArray.get(i)==0){
                outputStreamWriter.append("stroke=\"#"+hashCode+"\" fill=\""+"none"+"\"" +"/>");//outputStreamWriter.append("stroke=\"#"+hashCode+"\" fill=\""+"none"+"\"" +"/>");//outputStreamWriter.append("stroke=\"#006600\" fill=\""+"none"+"\"" +"/>");
            }
            else{
                outputStreamWriter.append("stroke=\"#006600\" fill=\"#"+hashCode+"\"" +"/>");
            }
            outputStreamWriter.append(Constants.NEWLINE);
        }
//        outputStreamWriter.append("</svg>");
        outputStreamWriter.close();
    }

    public void svgPolygon() throws FileNotFoundException, IOException{
        File file = new File(directory,"polygon.svg");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//        outputStreamWriter.append("<svg>");
        outputStreamWriter.append(Constants.NEWLINE);
        if(Screen.polygonObject.polygons.size()>0){
            isSVGBlank = false;
        }
        for(int i = 0; i< Screen.polygonObject.polygons.size(); i++){
            outputStreamWriter.append("<polygon points="+"\"");
            for(int j = 0; j< Screen.polygonObject.polygons.get(i).size()-1; j++){
                outputStreamWriter.append(Screen.polygonObject.polygons.get(i).get(j).getL()+","+ Screen.polygonObject.polygons.get(i).get(j).getR()+" ");
            }
            outputStreamWriter.append("\" ");
            String hashCode = rgbToHash(Screen.polygonObject.colorArray.get(i).getRed(),
                    Screen.polygonObject.colorArray.get(i).getGreen(),
                    Screen.polygonObject.colorArray.get(i).getBlue());
            
            if(Screen.polygonObject.fillOrNot.get(i)==0){
                outputStreamWriter.append("stroke=\"#"+hashCode+"\" fill=\""+"none"+"\"" +"/>");//outputStreamWriter.append("stroke=\"#006600\" fill=\""+"none"+"\"" +"/>");
            }
            else{
                outputStreamWriter.append("stroke=\"#006600\" fill=\"#"+hashCode+"\"" +"/>");
            }
            outputStreamWriter.append(Constants.NEWLINE);
        }
//        outputStreamWriter.append("</svg>");
        outputStreamWriter.close();
    }
    
    public void svgRegion() throws FileNotFoundException, IOException{
        File file = new File(directory,"region.svg");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//        outputStreamWriter.append("<svg>");
        outputStreamWriter.append(Constants.NEWLINE);
        if(Screen.regionsObject.regions.size()>0){
            isSVGBlank = false;
        }
        for(int i = 0; i< Screen.regionsObject.regions.size(); i++){
            outputStreamWriter.append("<polygon points="+"\"");
            for(int j = 0; j< Screen.regionsObject.regions.get(i).size()-1; j++){
                outputStreamWriter.append(Screen.regionsObject.regions.get(i).get(j).getL()+","+ Screen.regionsObject.regions.get(i).get(j).getR()+" ");
            }
            String hashCode = rgbToHash(Screen.regionsObject.colorArray.get(i).getRed(),
                    Screen.regionsObject.colorArray.get(i).getGreen(),
                    Screen.regionsObject.colorArray.get(i).getBlue());
            outputStreamWriter.append("\" ");
            if(Screen.regionsObject.fillArray.get(i)==0){
                outputStreamWriter.append("stroke=\"#"+hashCode+"\" fill=\""+"none"+"\"" +"/>");//outputStreamWriter.append("stroke=\"#006600\" fill=\""+"none"+"\"" +"/>");
            }
            else{
                outputStreamWriter.append("stroke=\"#006600\" fill=\"#"+hashCode+"\"" +"/>");
            }
            
            outputStreamWriter.append(Constants.NEWLINE);
        }
//        outputStreamWriter.append("</svg>");
        outputStreamWriter.close();
    }    
    
    public void svgPathRegion() throws FileNotFoundException, IOException{
        File file = new File(directory,"path_region.svg");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//        outputStreamWriter.append("<svg>");
        outputStreamWriter.append(Constants.NEWLINE);
        if(Screen.pathsObject.regions.size()>0){
            isSVGBlank = false;
        }
        for(int i = 0; i< Screen.pathsObject.regions.size(); i++){
            outputStreamWriter.append("<polyline points="+"\"");
            for(int j = 0; j< Screen.pathsObject.regions.get(i).size()-1; j++){
                outputStreamWriter.append(Screen.pathsObject.regions.get(i).get(j).getL()+","+ Screen.pathsObject.regions.get(i).get(j).getR()+" ");
            }
            String hashCode = rgbToHash(Screen.pathsObject.colorArray.get(i).getRed(),
                    Screen.pathsObject.colorArray.get(i).getGreen(),
                    Screen.pathsObject.colorArray.get(i).getBlue());
            
            outputStreamWriter.append("\" ");
            outputStreamWriter.append("stroke=\"#"+hashCode+"\" fill=\"none\""+"/>");
            outputStreamWriter.append(Constants.NEWLINE);
        }
//        outputStreamWriter.append("</svg>");
        outputStreamWriter.close();
    }
    public void svgText() throws FileNotFoundException, IOException, ScriptException, NoSuchMethodException{
        File file = new File(directory,"text.svg");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//        outputStreamWriter.append("<svg>");
        outputStreamWriter.append(Constants.NEWLINE);
        for(int i = 0; i< Screen.textboxObject.rectangleArray.size(); i++){
            if(!Screen.textboxObject.label.get(i).getR().contains("label -")){
                isSVGBlank = false;
                int x1 = Screen.textboxObject.rectangleArray.get(i).x;
                int y1 = Screen.textboxObject.rectangleArray.get(i).y+ Screen.textboxObject.rectangleArray.get(i).height;
//                int y3 = Screen.textboxObject.rectangleArray.get(i).height;
                int y3 = 15;
                outputStreamWriter.append("<text x="+"\""+x1+"\" ");
                outputStreamWriter.append("y="+"\""+y1+"\" ");
                String language = Screen.textboxObject.languageArray.get(i);
                String ret = Screen.textboxObject.label.get(i).getR();
                
                switch (language) {
                    case "hin":
                        {
                            outputStreamWriter.append("style="+"\""+"font-family: Kruti Dev 010; font-size : "+y3+"; stroke : #000000; fill : #000000;"+"\"" +">");
                            ScriptEngineManager factory = new ScriptEngineManager();
                            ScriptEngine engine = factory.getEngineByName("JavaScript");
                            engine.eval(Files.newBufferedReader(Paths.get(Screen.config.get("hindi_helper")), StandardCharsets.UTF_8));
                            Invocable inv = (Invocable) engine;
                            ret = (String) inv.invokeFunction("convert_to_unicode", ret );
                            break;
                        }
                    case "ben":
                        {
                            outputStreamWriter.append("style="+"\""+"font-family: bengali; font-size : "+y3+"; stroke : #000000; fill : #000000;"+"\"" +">");
                            ScriptEngineManager factory = new ScriptEngineManager();
                            ScriptEngine engine = factory.getEngineByName("JavaScript");
                            engine.eval(Files.newBufferedReader(Paths.get(Screen.config.get("bangla_helper")), StandardCharsets.UTF_8));
                            engine.eval(Files.newBufferedReader(Paths.get(Screen.config.get("bangla_helper_main_2")), StandardCharsets.UTF_8));
                            Invocable inv = (Invocable) engine;
                            ret = (String) inv.invokeFunction("ConvertToUnicode", "bijoy",ret);
                            break;
                        }
                    case "eng":
                        outputStreamWriter.append("style="+"\""+"font-family: SansSerif; font-size : "+y3+"; stroke : #000000; fill : #000000;"+"\"" +">");
                        break;
                    default:
                        break;
                }
                outputStreamWriter.append(ret);
                outputStreamWriter.append("</text>");
                outputStreamWriter.append(Constants.NEWLINE);
            }
        }
//        outputStreamWriter.append("</svg>");
        outputStreamWriter.close();
    }
    public void svgRect() throws FileNotFoundException, IOException{
        File file = new File(directory,"rect.svg");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
        outputStreamWriter.append(Constants.NEWLINE);
        for(int i = 0; i< Screen.textboxObject.rectangleArray.size(); i++){
            if(!Screen.textboxObject.label.get(i).getR().contains("label -")){
                isSVGBlank = false;
                int x = Screen.textboxObject.rectangleArray.get(i).x;
                int y = Screen.textboxObject.rectangleArray.get(i).y;
                int height = Screen.textboxObject.rectangleArray.get(i).height;
                int width = Screen.textboxObject.rectangleArray.get(i).width;
                outputStreamWriter.append("<rect x="+"\""+x+"\" ");
                outputStreamWriter.append("y="+"\""+y+"\" ");
                outputStreamWriter.append("height="+"\""+height+"\" ");
                outputStreamWriter.append("width="+"\""+width+"\" ");
                outputStreamWriter.append("style="+"\""+"stroke : #ffffff; fill : none;"+"\"" +">");
                outputStreamWriter.append("</rect>");
                outputStreamWriter.append(Constants.NEWLINE);
            }
        }
        outputStreamWriter.close();
    }
    
    public void svgFile() throws FileNotFoundException, IOException, ScriptException, NoSuchMethodException{
        isSVGBlank = true;
        directory = new File(Screen.config.get("library_directory_path"));
        svgLines();
        svgArc();
        svgCircles();
        svgPolygon();
        svgRegion();
        svgText();
        svgRect();
        svgPathRegion();
        if(!isSVGBlank){
            String currentFileAbsolutePath = screen.currentFile.getAbsolutePath();
            String currentFileName = screen.currentFileName;
            String newFileName = currentFileName+".html";
                    //currentFileName.substring(0,currentFileName.indexOf("."))+".html";
            int index=currentFileAbsolutePath.lastIndexOf("\\");
            String newFileAbsolutePath =currentFileAbsolutePath.substring(0, index+1)+ newFileName;
            File file = new File(newFileAbsolutePath);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            outputStreamWriter.append("<!DOCTYPE html>");
            outputStreamWriter.append(Constants.NEWLINE);
            outputStreamWriter.append("<html>");
            outputStreamWriter.append(Constants.NEWLINE);
            outputStreamWriter.append("<meta http-equiv="+" \"Content-Type\" "+" content="+" \"text/html; charset=UTF-8\" "+"></meta>");
            outputStreamWriter.append(Constants.NEWLINE);
            outputStreamWriter.append("<body>");
            outputStreamWriter.append(Constants.NEWLINE);
            outputStreamWriter.append("<div>");
            outputStreamWriter.append(Constants.NEWLINE);
            String screenWidthHeight = "width="+"\""+ Screen.bufferedImageScreen.getWidth() +"\" ";
            screenWidthHeight = screenWidthHeight + "height="+"\""+ Screen.bufferedImageScreen.getHeight()+"\"";
            outputStreamWriter.append("<svg "+ screenWidthHeight +">");
            outputStreamWriter.append(Constants.NEWLINE);

            File file1 = new File(directory,"line.svg");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file1),"UTF-8"));
            String line  = bufferedReader.readLine();
            while(line!=null){
               outputStreamWriter.append(line);
               outputStreamWriter.append(Constants.NEWLINE);
               line = bufferedReader.readLine();
            }
            bufferedReader.close();
            file1.delete();

            file1 = new File(directory,"arc.svg");
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file1),"UTF-8"));
            line  = bufferedReader.readLine();
            while(line!=null){
               outputStreamWriter.append(line);
               outputStreamWriter.append(Constants.NEWLINE);
               line = bufferedReader.readLine();
            }
            bufferedReader.close();
            file1.delete();
            file1 = new File(directory,"circle.svg");
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file1),"UTF-8"));
            line  = bufferedReader.readLine();
            while(line!=null){
               outputStreamWriter.append(line);
               outputStreamWriter.append(Constants.NEWLINE);
               line = bufferedReader.readLine();
            }
            bufferedReader.close();
            file1.delete();
            file1 = new File(directory,"polygon.svg");
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file1),"UTF-8"));
            line  = bufferedReader.readLine();
            while(line!=null){
               outputStreamWriter.append(line);
               outputStreamWriter.append(Constants.NEWLINE);
               line = bufferedReader.readLine();
            }
            bufferedReader.close();
            file1.delete();
            file1 = new File(directory,"path_region.svg");
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file1),"UTF-8"));
            line  = bufferedReader.readLine();
            while(line!=null){
               outputStreamWriter.append(line);
               outputStreamWriter.append(Constants.NEWLINE);
               line = bufferedReader.readLine();
            }
            bufferedReader.close();
            file1.delete();
            file1 = new File(directory,"region.svg");
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file1),"UTF-8"));
            line  = bufferedReader.readLine();
            while(line!=null){
               outputStreamWriter.append(line);
               outputStreamWriter.append(Constants.NEWLINE);
               line = bufferedReader.readLine();
            }
            bufferedReader.close();
            file1.delete();
            file1 = new File(directory,"text.svg");
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file1),"UTF-8"));
            line  = bufferedReader.readLine();
            while(line!=null){
               outputStreamWriter.append(line);
               outputStreamWriter.append(Constants.NEWLINE);
               line = bufferedReader.readLine();
            }
            bufferedReader.close();
            file1.delete();
            file1 = new File(directory,"rect.svg");
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file1),"UTF-8"));
            line  = bufferedReader.readLine();
            while(line!=null){
               outputStreamWriter.append(line);
               outputStreamWriter.append(Constants.NEWLINE);
               line = bufferedReader.readLine();
            }
            bufferedReader.close();
            file1.delete();
            outputStreamWriter.append("</svg>");
            outputStreamWriter.append(Constants.NEWLINE);
            outputStreamWriter.append("</div>");
            outputStreamWriter.append(Constants.NEWLINE);
            outputStreamWriter.append("</body>");
            outputStreamWriter.append(Constants.NEWLINE);
            outputStreamWriter.append("</html>");
            outputStreamWriter.close();
        }
        else{
            File file = new File(directory,"line.svg");
            file.delete();
            file = new File(directory,"arc.svg");
            file.delete();
            file = new File(directory,"circle.svg");
            file.delete();
            file = new File(directory,"polygon.svg");
            file.delete();
            file = new File(directory,"path_region.svg");
            file.delete();
            file = new File(directory,"region.svg");
            file.delete();
            file = new File(directory,"text.svg");
            file.delete();
            file = new File(directory,"rect.svg");
            file.delete();
        }
    } 
}
