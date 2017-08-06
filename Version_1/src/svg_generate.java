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

public class svg_generate {
    int r=0;
    Screen t1 = new Screen(r);
    boolean blanksvg;
    File dir;
    public void svg_lines() throws FileNotFoundException, IOException{
        File f = new File(dir,"line.svg");
        
        OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
//        o.append("<svg>");
        o.append(Constants.NEWLINE);
        if(Screen.line_object.Lines.size()>1){
            blanksvg = false;
        }
        for(int i = 0; i< Screen.line_object.Lines.size()-1; i++){
            o.append("<line x1="+"\""+ Screen.line_object.Lines.get(i).getL()+"\" ");
            o.append("y1="+"\""+ Screen.line_object.Lines.get(i).getR()+"\" ");
            i++;
            o.append("x2="+"\""+ Screen.line_object.Lines.get(i).getL()+"\" ");
            o.append("y2="+"\""+ Screen.line_object.Lines.get(i).getR()+"\" ");
            String hash_code = rgbtohash(Screen.line_object.color_array.get(i).getRed(),
                    Screen.line_object.color_array.get(i).getGreen(),
                    Screen.line_object.color_array.get(i).getBlue());
            o.append("stroke="+"\""+"#"+hash_code+"\"" +"/>");
            o.append(Constants.NEWLINE);  
        }
        o.close();
    }

    public void svg_arc() throws FileNotFoundException, IOException{
        File f = new File(dir,"arc.svg");
        OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
//        o.append("<svg>");
        o.append(Constants.NEWLINE);
        if(Screen.arc_object.radii.size()>0){
            blanksvg = false;
        }
        for(int i = 0; i< Screen.arc_object.radii.size(); i++){
            float c_x = Screen.arc_object.centers.get(i).getL();
            float c_y = Screen.arc_object.centers.get(i).getR();
            float r = Screen.arc_object.radii.get(i);
            int g1 = Screen.arc_object.arcAngles.get(i).getL();
            int g2 = Screen.arc_object.arcAngles.get(i).getR();
            int g3 = (g1+g2)%360;
            float s1 = (float) (c_x+r*Math.cos(Math.toRadians(g1)));
            float s2 = (float) (c_y-r*Math.sin(Math.toRadians(g1)));
            float e1 = (float) (c_x+r*Math.cos(Math.toRadians(g3)));
            float e2 = (float) (c_y-r*Math.sin(Math.toRadians(g3)));
            int lsa = 0;
            if(Screen.arc_object.arcAngles.get(i).getR()>180){
                lsa = 1;
            }
            String hash_code = rgbtohash(Screen.arc_object.colorArray.get(i).getRed(),
                    Screen.arc_object.colorArray.get(i).getGreen(),
                    Screen.arc_object.colorArray.get(i).getBlue());
            
            o.append("<path d="+"\"M "+s1+" "+s2+" A "+r+" "+r+", 0, "+lsa+", 0, "+e1+" "+e2+"\" ");
            //o.append("style="+"\""+"stroke:#006600; fill:#"+hash_code+";"+"\"" +"/>");
            if(Screen.arc_object.fillArray.get(i)==0){
                o.append("stroke=\"#"+hash_code+"\" fill=\""+"none"+"\"" +"/>");//o.append("stroke=\"#006600\" fill=\""+"none"+"\"" +"/>");
            }
            else{
                o.append("stroke=\"#006600\" fill=\"#"+hash_code+"\"" +"/>");
            }
            o.append(Constants.NEWLINE);  
        }
//        o.append("</svg>");  
        o.close();
    }    
    public String rgbtohash(int r,int g,int b){
        String g1 = Integer.toHexString(r);
        if(g1.length()==1){
            g1 = "0"+g1;
        }
        String g2 = Integer.toHexString(g);
        if(g2.length()==1){
            g2 = "0"+g2;
        }
        String g3 = Integer.toHexString(b);
        if(g3.length()==1){
            g3 = "0"+g3;
        }
        return g1+g2+g3;
    }
    public void svg_circles() throws FileNotFoundException, IOException{
        File f = new File(dir,"circle.svg");
        OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
//        o.append("<svg>");
        o.append(Constants.NEWLINE);
        if(Screen.circle_object.radii.size()>0){
            blanksvg = false;
        }
        for(int i = 0; i< Screen.circle_object.radii.size(); i++){
        //    <circle cx="196.0" cy="172.0" r="138.0" stroke="#006600" fill="#ffffff"/>
            o.append("<circle cx="+"\""+ Screen.circle_object.centers.get(i).getL()+"\" ");
            o.append("cy="+"\""+ Screen.circle_object.centers.get(i).getR()+"\" ");
            o.append("r="+"\""+ Screen.circle_object.radii.get(i)+"\" ");
            String hash_code = rgbtohash(Screen.circle_object.colorArray.get(i).getRed(),
                    Screen.circle_object.colorArray.get(i).getGreen(),
                    Screen.circle_object.colorArray.get(i).getBlue());
            if(Screen.circle_object.fillArray.get(i)==0){
                o.append("stroke=\"#"+hash_code+"\" fill=\""+"none"+"\"" +"/>");//o.append("stroke=\"#"+hash_code+"\" fill=\""+"none"+"\"" +"/>");//o.append("stroke=\"#006600\" fill=\""+"none"+"\"" +"/>");
            }
            else{
                o.append("stroke=\"#006600\" fill=\"#"+hash_code+"\"" +"/>");
            }
            o.append(Constants.NEWLINE);  
        }
//        o.append("</svg>");  
        o.close();
    }

    public void svg_polygon() throws FileNotFoundException, IOException{
        File f = new File(dir,"polygon.svg");
        OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
//        o.append("<svg>");
        o.append(Constants.NEWLINE);
        if(Screen.polygon_object.Polygons.size()>0){
            blanksvg = false;
        }
        for(int i = 0; i< Screen.polygon_object.Polygons.size(); i++){
            o.append("<polygon points="+"\"");
            for(int j = 0; j< Screen.polygon_object.Polygons.get(i).size()-1; j++){
                o.append(Screen.polygon_object.Polygons.get(i).get(j).getL()+","+ Screen.polygon_object.Polygons.get(i).get(j).getR()+" ");
            }
            o.append("\" ");
            String hash_code = rgbtohash(Screen.polygon_object.color_array.get(i).getRed(),
                    Screen.polygon_object.color_array.get(i).getGreen(),
                    Screen.polygon_object.color_array.get(i).getBlue());
            
            if(Screen.polygon_object.fill_or_not.get(i)==0){
                o.append("stroke=\"#"+hash_code+"\" fill=\""+"none"+"\"" +"/>");//o.append("stroke=\"#006600\" fill=\""+"none"+"\"" +"/>");
            }
            else{
                o.append("stroke=\"#006600\" fill=\"#"+hash_code+"\"" +"/>");
            }
            o.append(Constants.NEWLINE);  
        }
//        o.append("</svg>");  
        o.close();
    }
    
    public void svg_region() throws FileNotFoundException, IOException{
        File f = new File(dir,"region.svg");
        OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
//        o.append("<svg>");
        o.append(Constants.NEWLINE);
        if(Screen.region_object.regions.size()>0){
            blanksvg = false;
        }
        for(int i = 0; i< Screen.region_object.regions.size(); i++){
            o.append("<polygon points="+"\"");
            for(int j = 0; j< Screen.region_object.regions.get(i).size()-1; j++){
                o.append(Screen.region_object.regions.get(i).get(j).getL()+","+ Screen.region_object.regions.get(i).get(j).getR()+" ");
            }
            String hash_code = rgbtohash(Screen.region_object.colorArray.get(i).getRed(),
                    Screen.region_object.colorArray.get(i).getGreen(),
                    Screen.region_object.colorArray.get(i).getBlue());
            o.append("\" ");
            if(Screen.region_object.fillArray.get(i)==0){
                o.append("stroke=\"#"+hash_code+"\" fill=\""+"none"+"\"" +"/>");//o.append("stroke=\"#006600\" fill=\""+"none"+"\"" +"/>");
            }
            else{
                o.append("stroke=\"#006600\" fill=\"#"+hash_code+"\"" +"/>");
            }
            
            o.append(Constants.NEWLINE);  
        }
//        o.append("</svg>");  
        o.close();
    }    
    
    public void svg_path_region() throws FileNotFoundException, IOException{
        File f = new File(dir,"path_region.svg");
        OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
//        o.append("<svg>");
        o.append(Constants.NEWLINE);
        if(Screen.a16.regions.size()>0){
            blanksvg = false;
        }
        for(int i = 0; i< Screen.a16.regions.size(); i++){
            o.append("<polyline points="+"\"");
            for(int j = 0; j< Screen.a16.regions.get(i).size()-1; j++){
                o.append(Screen.a16.regions.get(i).get(j).getL()+","+ Screen.a16.regions.get(i).get(j).getR()+" ");
            }
            String hash_code = rgbtohash(Screen.a16.colorArray.get(i).getRed(),
                    Screen.a16.colorArray.get(i).getGreen(),
                    Screen.a16.colorArray.get(i).getBlue());
            
            o.append("\" ");
            o.append("stroke=\"#"+hash_code+"\" fill=\"none\""+"/>");
            o.append(Constants.NEWLINE);  
        }
//        o.append("</svg>");  
        o.close();
    }
    public void svg_text() throws FileNotFoundException, IOException, ScriptException, NoSuchMethodException{
        File f = new File(dir,"text.svg");
        OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
//        o.append("<svg>");
        o.append(Constants.NEWLINE);
        for(int i = 0; i< Screen.textbox_object.Rect_array.size(); i++){
            if(!Screen.textbox_object.label.get(i).getR().contains("label -")){
                blanksvg = false;
                int x1 = Screen.textbox_object.Rect_array.get(i).x;
                int y1 = Screen.textbox_object.Rect_array.get(i).y+ Screen.textbox_object.Rect_array.get(i).height;
                int y3 = Screen.textbox_object.Rect_array.get(i).height;
                y3 = 15;
                o.append("<text x="+"\""+x1+"\" ");
                o.append("y="+"\""+y1+"\" ");
                String language = Screen.textbox_object.Language_array.get(i);
                String ret = Screen.textbox_object.label.get(i).getR();
                
                switch (language) {
                    case "hin":
                        {
                            o.append("style="+"\""+"font-family: Kruti Dev 010; font-size : "+y3+"; stroke : #000000; fill : #000000;"+"\"" +">");
                            ScriptEngineManager factory = new ScriptEngineManager();
                            ScriptEngine engine = factory.getEngineByName("JavaScript");
                            engine.eval(Files.newBufferedReader(Paths.get(Screen.config.get("hindi_helper")), StandardCharsets.UTF_8));
                            Invocable inv = (Invocable) engine;
                            ret = (String) inv.invokeFunction("convert_to_unicode", ret );
                            break;
                        }
                    case "ben":
                        {
                            o.append("style="+"\""+"font-family: bengali; font-size : "+y3+"; stroke : #000000; fill : #000000;"+"\"" +">");
                            ScriptEngineManager factory = new ScriptEngineManager();
                            ScriptEngine engine = factory.getEngineByName("JavaScript");
                            engine.eval(Files.newBufferedReader(Paths.get(Screen.config.get("bangla_helper")), StandardCharsets.UTF_8));
                            engine.eval(Files.newBufferedReader(Paths.get(Screen.config.get("bangla_helper_main_2")), StandardCharsets.UTF_8));
                            Invocable inv = (Invocable) engine;
                            ret = (String) inv.invokeFunction("ConvertToUnicode", "bijoy",ret);
                            break;
                        }
                    case "eng":
                        o.append("style="+"\""+"font-family: SansSerif; font-size : "+y3+"; stroke : #000000; fill : #000000;"+"\"" +">");
                        break;
                    default:
                        break;
                }
                o.append(ret);
                o.append("</text>");
                o.append(Constants.NEWLINE);  
            }
        }
//        o.append("</svg>");  
        o.close();
    }
    public void svg_rect() throws FileNotFoundException, IOException{
        File f = new File(dir,"rect.svg");
        OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
        o.append(Constants.NEWLINE);
        for(int i = 0; i< Screen.textbox_object.Rect_array.size(); i++){
            if(!Screen.textbox_object.label.get(i).getR().contains("label -")){
                blanksvg = false;
                int x1 = Screen.textbox_object.Rect_array.get(i).x;
                int y1 = Screen.textbox_object.Rect_array.get(i).y;
                int h1 = Screen.textbox_object.Rect_array.get(i).height;
                int w1 = Screen.textbox_object.Rect_array.get(i).width;
                o.append("<rect x="+"\""+x1+"\" ");
                o.append("y="+"\""+y1+"\" ");
                o.append("height="+"\""+h1+"\" ");
                o.append("width="+"\""+w1+"\" ");
                o.append("style="+"\""+"stroke : #ffffff; fill : none;"+"\"" +">");
                o.append("</rect>");
                o.append(Constants.NEWLINE);  
            }
        }
        o.close();
    }
    
    public void svg_file() throws FileNotFoundException, IOException, ScriptException, NoSuchMethodException{
        blanksvg = true;
        dir = new File(Screen.config.get("library_directory_path"));
        svg_lines();
        svg_arc();
        svg_circles();
        svg_polygon();
        svg_region();
        svg_text();
        svg_rect();
        svg_path_region();
        if(!blanksvg){
            String h1 = t1.current_file.getAbsolutePath();
            String h2 = t1.current_file_name;
            String ty = h2+".html";
                    //h2.substring(0,h2.indexOf("."))+".html";
            String h4 = h1;
            int index=h4.lastIndexOf("\\");
            h4=h4.substring(0, index+1)+ty;
            File f = new File(h4);
            OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
            o.append("<!DOCTYPE html>");
            o.append(Constants.NEWLINE);
            o.append("<html>");
            o.append(Constants.NEWLINE);
            o.append("<meta http-equiv="+" \"Content-Type\" "+" content="+" \"text/html; charset=UTF-8\" "+"></meta>");
            o.append(Constants.NEWLINE);
            o.append("<body>");
            o.append(Constants.NEWLINE);
            o.append("<div>");
            o.append(Constants.NEWLINE);
            String ht1 = "width="+"\""+ Screen.screen.getWidth() +"\" ";
            ht1 = ht1 + "height="+"\""+ Screen.screen.getHeight()+"\"";
            o.append("<svg "+ht1+">");
            o.append(Constants.NEWLINE);

            File f1 = new File(dir,"line.svg");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f1),"UTF-8"));
            String ht  = br.readLine();
            while(ht!=null){
               o.append(ht);
               o.append(Constants.NEWLINE);
               ht = br.readLine();
            }
            br.close();
            f1.delete();

            f1 = new File(dir,"arc.svg");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f1),"UTF-8"));
            ht  = br.readLine();
            while(ht!=null){
               o.append(ht);
               o.append(Constants.NEWLINE);
               ht = br.readLine();
            }
            br.close();
            f1.delete();
            f1 = new File(dir,"circle.svg");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f1),"UTF-8"));
            ht  = br.readLine();
            while(ht!=null){
               o.append(ht);
               o.append(Constants.NEWLINE);
               ht = br.readLine();
            }
            br.close();        
            f1.delete();
            f1 = new File(dir,"polygon.svg");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f1),"UTF-8"));
            ht  = br.readLine();
            while(ht!=null){
               o.append(ht);
               o.append(Constants.NEWLINE);
               ht = br.readLine();
            }
            br.close();
            f1.delete();
            f1 = new File(dir,"path_region.svg");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f1),"UTF-8"));
            ht  = br.readLine();
            while(ht!=null){
               o.append(ht);
               o.append(Constants.NEWLINE);
               ht = br.readLine();
            }
            br.close();
            f1.delete();
            f1 = new File(dir,"region.svg");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f1),"UTF-8"));
            ht  = br.readLine();
            while(ht!=null){
               o.append(ht);
               o.append(Constants.NEWLINE);
               ht = br.readLine();
            }
            br.close();
            f1.delete();
            f1 = new File(dir,"text.svg");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f1),"UTF-8"));
            ht  = br.readLine();
            while(ht!=null){
               o.append(ht);
               o.append(Constants.NEWLINE);
               ht = br.readLine();
            }
            br.close();
            f1.delete();
            f1 = new File(dir,"rect.svg");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f1),"UTF-8"));
            ht  = br.readLine();
            while(ht!=null){
               o.append(ht);
               o.append(Constants.NEWLINE);
               ht = br.readLine();
            }
            br.close();
            f1.delete();
            o.append("</svg>");
            o.append(Constants.NEWLINE);
            o.append("</div>");
            o.append(Constants.NEWLINE);
            o.append("</body>");
            o.append(Constants.NEWLINE);
            o.append("</html>"); 
            o.close();
        }
        else{
            File f1 = new File(dir,"line.svg");
            f1.delete();
            f1 = new File(dir,"arc.svg");
            f1.delete();
            f1 = new File(dir,"circle.svg");
            f1.delete();
            f1 = new File(dir,"polygon.svg");
            f1.delete();
            f1 = new File(dir,"path_region.svg");
            f1.delete();
            f1 = new File(dir,"region.svg");
            f1.delete();
            f1 = new File(dir,"text.svg");
            f1.delete();
            f1 = new File(dir,"rect.svg");
            f1.delete();
        }
    } 
}
