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

/**
 * Class to create SVG downloadable file
 */
public class GenerateSVG {
    private boolean isSVGBlank;
    private File directory;
    private void svgLines() throws FileNotFoundException, IOException{
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
            outputStreamWriter.append("stroke=\"black\"/>");
            outputStreamWriter.append(Constants.NEWLINE);
        }
        outputStreamWriter.close();
    }

    private void svgArc() throws IOException{
        File file = new File(directory,"arc.svg");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//        outputStreamWriter.append("<svg>");
        outputStreamWriter.append(Constants.NEWLINE);
//        if(Screen.arcObject.radii.size()>0){
        if(Screen.arcObject.allArcs.size()>0){
            isSVGBlank = false;
        }
//        for(int i = 0; i< Screen.arcObject.radii.size(); i++){
//            float centerX = Screen.arcObject.centers.get(i).getL();
//            float centerY = Screen.arcObject.centers.get(i).getR();
//            float radius = Screen.arcObject.radii.get(i);
//            int angle1 = Screen.arcObject.arcAngles.get(i).getL();
//            int angle2 = Screen.arcObject.arcAngles.get(i).getR();
        for(int i = 0; i< Screen.arcObject.allArcs.size(); i++){
            GetArc.Arc tempArc = Screen.arcObject.allArcs.get(i);
            float centerX = tempArc.center.getL();
            float centerY = tempArc.center.getR();
            float radius = tempArc.radius;
            int angle1 = tempArc.arcAngles.getL();
            int angle2 = tempArc.arcAngles.getR();
            int angle3 = (angle1 + angle2)%360;
            float s1 = (float) (centerX + radius *Math.cos(Math.toRadians(angle1)));
            float s2 = (float) (centerY - radius *Math.sin(Math.toRadians(angle1)));
            float e1 = (float) (centerX + radius *Math.cos(Math.toRadians(angle3)));
            float e2 = (float) (centerY - radius *Math.sin(Math.toRadians(angle3)));
            int lsa = 0; //don't know what is this
//            if(Screen.arcObject.arcAngles.get(i).getR()>180){
            if(angle2 > 180){
                lsa = 1;
            }
//            String hash_code = rgbToHash(Screen.arcObject.colorArray.get(i).getRed(),
//                    Screen.arcObject.colorArray.get(i).getGreen(),
//                    Screen.arcObject.colorArray.get(i).getBlue());
            String hash_code = rgbToHash(tempArc.color.getRed(),
                    tempArc.color.getGreen(),
                    tempArc.color.getBlue());
            
            outputStreamWriter.append("<path class=\"fil1\" d="+"\"M "+s1+" "+s2+" A "+ radius +" "+ radius +", 0, "+lsa+", 0, "+e1+" "+e2+"\" ");
            //outputStreamWriter.append("style="+"\""+"stroke:#006600; fill:#"+hash_code+";"+"\"" +"/>");
//            if(Screen.arcObject.fillArray.get(i)==0){
            if(tempArc.fill==0){
                outputStreamWriter.append("stroke=\"black\" fill=\"none\"/>");//outputStreamWriter.append("stroke=\"#006600\" fill=\""+"none"+"\"" +"/>");
            }
            else{
                outputStreamWriter.append("stroke=\"black\" fill=\"none\"/>");
            }
            outputStreamWriter.append(Constants.NEWLINE);
        }
//        outputStreamWriter.append("</svg>");
        outputStreamWriter.close();
    }    
    private String rgbToHash(int r, int g, int b){
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
    private void svgCircles() throws  IOException{
        File file = new File(directory,"circle.svg");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//        outputStreamWriter.append("<svg>");
        outputStreamWriter.append(Constants.NEWLINE);
//        if(Screen.circlesObject.radii.size()>0){
        if(Screen.circlesObject.allCircles.size()>0){
            isSVGBlank = false;
        }
//        for(int i = 0; i< Screen.circlesObject.radii.size(); i++){
        for(int i = 0; i< Screen.circlesObject.allCircles.size(); i++){
        //    <circle cx="196.0" cy="172.0" r="138.0" stroke="#006600" fill="#ffffff"/>
//            outputStreamWriter.append("<circle cx="+"\""+ Screen.circlesObject.centers.get(i).getL()+"\" ");
//            outputStreamWriter.append("cy="+"\""+ Screen.circlesObject.centers.get(i).getR()+"\" ");
//            outputStreamWriter.append("r="+"\""+ Screen.circlesObject.radii.get(i)+"\" ");
//            String hashCode = rgbToHash(Screen.circlesObject.colorArray.get(i).getRed(),
//                    Screen.circlesObject.colorArray.get(i).getGreen(),
//                    Screen.circlesObject.colorArray.get(i).getBlue());
            outputStreamWriter.append("<circle cx="+"\""+ Screen.circlesObject.allCircles.get(i).center.getL()+"\" ");
            outputStreamWriter.append("cy="+"\""+ Screen.circlesObject.allCircles.get(i).center.getR()+"\" ");
            outputStreamWriter.append("r="+"\""+ Screen.circlesObject.allCircles.get(i).radius+"\" ");
            String hashCode = rgbToHash(Screen.circlesObject.allCircles.get(i).color.getRed(),
                    Screen.circlesObject.allCircles.get(i).color.getGreen(),
                    Screen.circlesObject.allCircles.get(i).color.getBlue());
//            if(Screen.circlesObject.fillArray.get(i)==0){
            if(Screen.circlesObject.allCircles.get(i).fill==0){
                outputStreamWriter.append("stroke=\"black\" fill=\"none\"/>");//outputStreamWriter.append("stroke=\"#"+hashCode+"\" fill=\""+"none"+"\"" +"/>");//outputStreamWriter.append("stroke=\"#006600\" fill=\""+"none"+"\"" +"/>");
            }
            else{
                outputStreamWriter.append("stroke=\"black\" fill=\"none\"/>");
            }
            outputStreamWriter.append(Constants.NEWLINE);
        }
//        outputStreamWriter.append("</svg>");
        outputStreamWriter.close();
    }

    private void svgBezier() throws FileNotFoundException, IOException{
        File file = new File(directory,"bezier.svg");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//        outputStreamWriter.append("<svg>");
        outputStreamWriter.append(Constants.NEWLINE);
//        if(Screen.polygonObject.polygons.size()>0){
        if(Screen.bezierObject.allBeziers.size() >0){
            isSVGBlank = false;
        }
//        for(int i = 0; i< Screen.polygonObject.polygons.size(); i++){
//            outputStreamWriter.append("<polygon points="+"\"");
//            for(int j = 0; j< Screen.polygonObject.polygons.get(i).size()-1; j++){
//                outputStreamWriter.append(Screen.polygonObject.polygons.get(i).get(j).getL()+","+ Screen.polygonObject.polygons.get(i).get(j).getR()+" ");
//            }
//            outputStreamWriter.append("\" ");
//            String hashCode = rgbToHash(Screen.polygonObject.colorArray.get(i).getRed(),
//                    Screen.polygonObject.colorArray.get(i).getGreen(),
//                    Screen.polygonObject.colorArray.get(i).getBlue());
//
//            if(Screen.polygonObject.fillOrNot.get(i)==0){
        for(int i = 0; i< Screen.bezierObject.allBeziers.size(); i++){
            GetBezier.Bezier tempBezier = Screen.bezierObject.allBeziers.get(i);
            outputStreamWriter.append("<path  class=\"fil1\" d=\"M" + tempBezier.startPoint.x +" "+ tempBezier.startPoint.y+"C");
            for(int j = 0; j< tempBezier.endPoints.size(); j++){
                outputStreamWriter.append(tempBezier.firstControlPoints.get(j).x+","+ tempBezier.firstControlPoints.get(j).y+" ");
                outputStreamWriter.append(tempBezier.secondControlPoints.get(j).x+","+ tempBezier.secondControlPoints.get(j).y+" ");
                outputStreamWriter.append(tempBezier.endPoints.get(j).x+","+ tempBezier.endPoints.get(j).y+" ");
            }
            outputStreamWriter.append("\" ");
            outputStreamWriter.append("stroke=\"black\" fill=\"none\"/>");

            outputStreamWriter.append(Constants.NEWLINE);
        }
//        outputStreamWriter.append("</svg>");
        outputStreamWriter.close();
    }

    private void svgPolygon() throws FileNotFoundException, IOException{
        File file = new File(directory,"polygon.svg");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//        outputStreamWriter.append("<svg>");
        outputStreamWriter.append(Constants.NEWLINE);
//        if(Screen.polygonObject.polygons.size()>0){
        if(Screen.polygonObject.allPolygons.size()>0){
            isSVGBlank = false;
        }
//        for(int i = 0; i< Screen.polygonObject.polygons.size(); i++){
//            outputStreamWriter.append("<polygon points="+"\"");
//            for(int j = 0; j< Screen.polygonObject.polygons.get(i).size()-1; j++){
//                outputStreamWriter.append(Screen.polygonObject.polygons.get(i).get(j).getL()+","+ Screen.polygonObject.polygons.get(i).get(j).getR()+" ");
//            }
//            outputStreamWriter.append("\" ");
//            String hashCode = rgbToHash(Screen.polygonObject.colorArray.get(i).getRed(),
//                    Screen.polygonObject.colorArray.get(i).getGreen(),
//                    Screen.polygonObject.colorArray.get(i).getBlue());
//
//            if(Screen.polygonObject.fillOrNot.get(i)==0){
        for(int i = 0; i< Screen.polygonObject.allPolygons.size(); i++){
            GetPolygon.Polygon tempPolygon = Screen.polygonObject.allPolygons.get(i);
            outputStreamWriter.append("<polygon  class=\"fil1\" points=\"");
            for(int j = 0; j< tempPolygon.points.size()-1; j++){
                outputStreamWriter.append(tempPolygon.points.get(j).getL()+","+ tempPolygon.points.get(j).getR()+" ");
            }
            outputStreamWriter.append("\" ");
            String hashCode = rgbToHash(tempPolygon.color.getRed(),
                    tempPolygon.color.getGreen(),
                    tempPolygon.color.getBlue());

            if(tempPolygon.fill==0){
                outputStreamWriter.append("stroke=\"black\" fill=\"none\"/>");//outputStreamWriter.append("stroke=\"#006600\" fill=\""+"none"+"\"" +"/>");
            }
            else{
                outputStreamWriter.append("stroke=\"black\" fill=\"none\"/>");
            }
            outputStreamWriter.append(Constants.NEWLINE);
        }
//        outputStreamWriter.append("</svg>");
        outputStreamWriter.close();
    }
    
    private void svgRegion() throws FileNotFoundException, IOException{
        File file = new File(directory,"region.svg");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//        outputStreamWriter.append("<svg>");
        outputStreamWriter.append(Constants.NEWLINE);
        if(Screen.regionsObject.regions.size()>0){
            isSVGBlank = false;
        }
        for(int i = 0; i< Screen.regionsObject.regions.size(); i++){
            outputStreamWriter.append("<polygon class=\"fil1\" points="+"\"");
            for(int j = 0; j< Screen.regionsObject.regions.get(i).size()-1; j++){
                outputStreamWriter.append(Screen.regionsObject.regions.get(i).get(j).getL()+","+ Screen.regionsObject.regions.get(i).get(j).getR()+" ");
            }
            String hashCode = rgbToHash(Screen.regionsObject.colorArray.get(i).getRed(),
                    Screen.regionsObject.colorArray.get(i).getGreen(),
                    Screen.regionsObject.colorArray.get(i).getBlue());
            outputStreamWriter.append("\" ");
            if(Screen.regionsObject.fillArray.get(i)==0){
                outputStreamWriter.append("stroke=\"black\"/>");//outputStreamWriter.append("stroke=\"#006600\" fill=\""+"none"+"\"" +"/>");
            }
            else{
                outputStreamWriter.append("stroke=\"black\" fill=\"none\"/>");
            }
            
            outputStreamWriter.append(Constants.NEWLINE);
        }
//        outputStreamWriter.append("</svg>");
        outputStreamWriter.close();
    }    
    
    private void svgPathRegion() throws FileNotFoundException, IOException{
        File file = new File(directory,"path_region.svg");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//        outputStreamWriter.append("<svg>");
        outputStreamWriter.append(Constants.NEWLINE);
//        if(Screen.pathsObject.paths.size()>0){
        if(Screen.pathsObject.allPaths.size()>0){
            isSVGBlank = false;
        }
//        for(int i = 0; i< Screen.pathsObject.paths.size(); i++){
//            outputStreamWriter.append("<polyline points="+"\"");
//            for(int j = 0; j< Screen.pathsObject.paths.get(i).size()-1; j++){
//                outputStreamWriter.append(Screen.pathsObject.paths.get(i).get(j).getL()+","+ Screen.pathsObject.paths.get(i).get(j).getR()+" ");
//            }
//            String hashCode = rgbToHash(Screen.pathsObject.colorArray.get(i).getRed(),
//                    Screen.pathsObject.colorArray.get(i).getGreen(),
//                    Screen.pathsObject.colorArray.get(i).getBlue());
        for(int i = 0; i< Screen.pathsObject.allPaths.size(); i++){
            String hashCode = rgbToHash(Screen.pathsObject.allPaths.get(i).color.getRed(),
                    Screen.pathsObject.allPaths.get(i).color.getGreen(),
                    Screen.pathsObject.allPaths.get(i).color.getBlue());
            outputStreamWriter.append("<polyline class=\"fil1\" points="+"\"");
            for(int j = 0; j< Screen.pathsObject.allPaths.get(i).points.size()-1; j++){
                outputStreamWriter.append(Screen.pathsObject.allPaths.get(i).points.get(j).getL()+","+ Screen.pathsObject.allPaths.get(i).points.get(j).getR()+" ");
            }
            outputStreamWriter.append("\" ");
            outputStreamWriter.append(StringConstants.polylineStyle +"\" />");
            outputStreamWriter.append(Constants.NEWLINE);
        }
//        outputStreamWriter.append("</svg>");
        outputStreamWriter.close();
    }

    private void svgText() throws FileNotFoundException, IOException, ScriptException, NoSuchMethodException{
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
    private void svgRect() throws FileNotFoundException, IOException{
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
                outputStreamWriter.append("style="+"\""+"stroke:black;fill:none; "+"\"" +">");
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
        svgBezier();
        if(!isSVGBlank){
            String currentFileAbsolutePath = Screen.currentFile.getAbsolutePath();
            String currentFileName = Screen.currentFileName;
//            if(currentFileName.indexOf(".")>0)
//                currentFileName = currentFileName.substring(0,currentFileName.lastIndexOf("."));
            String newFileName = currentFileName+".svg";
//            System.out.println(newFileName);
                    //currentFileName.substring(0,currentFileName.indexOf("."))+".html";
            int index=currentFileAbsolutePath.lastIndexOf("\\");
            String newFileAbsolutePath =currentFileAbsolutePath.substring(0, index+1)+ newFileName;
            File file = new File(newFileAbsolutePath);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
//            outputStreamWriter.append("<!DOCTYPE html>");
//            outputStreamWriter.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
            outputStreamWriter.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            outputStreamWriter.append(Constants.NEWLINE);
//            outputStreamWriter.append("<!DOCTYPE svg >");
//            outputStreamWriter.append(Constants.NEWLINE);
//            outputStreamWriter.append("<html>");
//            outputStreamWriter.append(Constants.NEWLINE);
//            outputStreamWriter.append("<meta http-equiv="+" \"Content-Type\" "+" content="+" \"text/html; charset=UTF-8\" "+"></meta>");
//            outputStreamWriter.append(Constants.NEWLINE);
//            outputStreamWriter.append("<body>");
//            outputStreamWriter.append(Constants.NEWLINE);
//            outputStreamWriter.append("<div>");
//            outputStreamWriter.append(Constants.NEWLINE);
            String screenWidthHeight = "width="+"\""+ Screen.bufferedImageScreen.getWidth() +"\" ";
            screenWidthHeight = screenWidthHeight + "height="+"\""+ Screen.bufferedImageScreen.getHeight()+"\"";
            outputStreamWriter.append("<svg "+ StringConstants.svgurls +"\n"+ screenWidthHeight +"\n"+ StringConstants.svgVersion +"\n"+
                    StringConstants.svgStyle + "\n"+StringConstants.svgViewbox +"\n"+ StringConstants.svgDocNameKey+"\""+currentFileName+".svg"+"\""+">");
            outputStreamWriter.append(Constants.NEWLINE);
            outputStreamWriter.append(StringConstants.svgDefs);
            outputStreamWriter.append(Constants.NEWLINE);
            outputStreamWriter.append(StringConstants.svgGroupId);

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

            file1 = new File(directory,"bezier.svg");
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

            outputStreamWriter.append("</g>");
            outputStreamWriter.append(Constants.NEWLINE);
            outputStreamWriter.append("</svg>");
            outputStreamWriter.append(Constants.NEWLINE);
//            outputStreamWriter.append("</div>");
//            outputStreamWriter.append(Constants.NEWLINE);
//            outputStreamWriter.append("</body>");
//            outputStreamWriter.append(Constants.NEWLINE);
//            outputStreamWriter.append("</html>");
            outputStreamWriter.close();

//            File temp = new File("temp.svg");
//            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(temp),"UTF-8");
//            file = new File(newFileAbsolutePath);
//            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
//            line  = bufferedReader.readLine();
//            while(line!=null){
//                if("<rect".equals(line.substring(0,5))){
////                    System.out.println(line);
//                    line = bufferedReader.readLine();
//                }
//                outputStreamWriter.append(line);
//                outputStreamWriter.append(Constants.NEWLINE);
//                line = bufferedReader.readLine();
//            }
//            file.delete();
//            System.out.println(file.exists());
////            if(currentFileName.indexOf(".")>0)
////                currentFileName = currentFileName.substring(0,currentFileName.lastIndexOf("."));
//            newFileName = currentFileName+".svg";
//            index=currentFileAbsolutePath.lastIndexOf("\\");
//            newFileAbsolutePath =currentFileAbsolutePath.substring(0, index+1)+ newFileName;
//            System.out.println(temp.renameTo(new File(newFileAbsolutePath)));
//            System.out.println(temp.getAbsolutePath());
//            outputStreamWriter.close();

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
