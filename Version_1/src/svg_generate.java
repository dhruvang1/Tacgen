import java.awt.Desktop;
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
    SCR t1 = new SCR(r);
    boolean blanksvg;
    File dir;
    public void svg_lines() throws FileNotFoundException, IOException{
        File f = new File(dir,"line.svg");
        
        OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
//        o.append("<svg>");
        o.append(Constants.NEWLINE);
        if(SCR.line_object.Lines.size()>1){
            blanksvg = false;
        }
        for(int i=0;i<SCR.line_object.Lines.size()-1;i++){
            o.append("<line x1="+"\""+SCR.line_object.Lines.get(i).getL()+"\" ");
            o.append("y1="+"\""+SCR.line_object.Lines.get(i).getR()+"\" ");
            i++;
            o.append("x2="+"\""+SCR.line_object.Lines.get(i).getL()+"\" ");
            o.append("y2="+"\""+SCR.line_object.Lines.get(i).getR()+"\" ");
            String hash_code = rgbtohash(SCR.line_object.color_array.get(i).getRed(),
                    SCR.line_object.color_array.get(i).getGreen(),
                    SCR.line_object.color_array.get(i).getBlue());
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
        if(SCR.arc_object.Radius.size()>0){
            blanksvg = false;
        }
        for(int i=0;i<SCR.arc_object.Radius.size();i++){
            float c_x = SCR.arc_object.Centers.get(i).getL();
            float c_y = SCR.arc_object.Centers.get(i).getR();
            float r = SCR.arc_object.Radius.get(i);
            int g1 = SCR.arc_object.arc_angles.get(i).getL();
            int g2 = SCR.arc_object.arc_angles.get(i).getR();
            int g3 = (g1+g2)%360;
            float s1 = (float) (c_x+r*Math.cos(Math.toRadians(g1)));
            float s2 = (float) (c_y-r*Math.sin(Math.toRadians(g1)));
            float e1 = (float) (c_x+r*Math.cos(Math.toRadians(g3)));
            float e2 = (float) (c_y-r*Math.sin(Math.toRadians(g3)));
            int lsa = 0;
            if(SCR.arc_object.arc_angles.get(i).getR()>180){
                lsa = 1;
            }
            String hash_code = rgbtohash(SCR.arc_object.color_array.get(i).getRed(),
                    SCR.arc_object.color_array.get(i).getGreen(),
                    SCR.arc_object.color_array.get(i).getBlue());
            
            o.append("<path d="+"\"M "+s1+" "+s2+" A "+r+" "+r+", 0, "+lsa+", 0, "+e1+" "+e2+"\" ");
            //o.append("style="+"\""+"stroke:#006600; fill:#"+hash_code+";"+"\"" +"/>");
            if(SCR.arc_object.fill_or_not.get(i)==0){
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
        if(SCR.circle_object.Radius.size()>0){
            blanksvg = false;
        }
        for(int i=0;i<SCR.circle_object.Radius.size();i++){
        //    <circle cx="196.0" cy="172.0" r="138.0" stroke="#006600" fill="#ffffff"/>
            o.append("<circle cx="+"\""+SCR.circle_object.Centers.get(i).getL()+"\" ");
            o.append("cy="+"\""+SCR.circle_object.Centers.get(i).getR()+"\" ");
            o.append("r="+"\""+SCR.circle_object.Radius.get(i)+"\" ");
            String hash_code = rgbtohash(SCR.circle_object.color_array.get(i).getRed(),
                    SCR.circle_object.color_array.get(i).getGreen(),
                    SCR.circle_object.color_array.get(i).getBlue());
            if(SCR.circle_object.fill_or_not.get(i)==0){
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
        if(SCR.polygon_object.Polygons.size()>0){
            blanksvg = false;
        }
        for(int i=0;i<SCR.polygon_object.Polygons.size();i++){
            o.append("<polygon points="+"\"");
            for(int j=0;j<SCR.polygon_object.Polygons.get(i).size()-1;j++){
                o.append(SCR.polygon_object.Polygons.get(i).get(j).getL()+","+SCR.polygon_object.Polygons.get(i).get(j).getR()+" ");
            }
            o.append("\" ");
            String hash_code = rgbtohash(SCR.polygon_object.color_array.get(i).getRed(),
                    SCR.polygon_object.color_array.get(i).getGreen(),
                    SCR.polygon_object.color_array.get(i).getBlue());
            
            if(SCR.polygon_object.fill_or_not.get(i)==0){
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
        if(SCR.region_object.Regions.size()>0){
            blanksvg = false;
        }
        for(int i=0;i<SCR.region_object.Regions.size();i++){
            o.append("<polygon points="+"\"");
            for(int j=0;j<SCR.region_object.Regions.get(i).size()-1;j++){
                o.append(SCR.region_object.Regions.get(i).get(j).getL()+","+SCR.region_object.Regions.get(i).get(j).getR()+" ");
            }
            String hash_code = rgbtohash(SCR.region_object.color_array.get(i).getRed(),
                    SCR.region_object.color_array.get(i).getGreen(),
                    SCR.region_object.color_array.get(i).getBlue());
            o.append("\" ");
            if(SCR.region_object.fill_or_not.get(i)==0){
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
        if(SCR.a16.Regions.size()>0){
            blanksvg = false;
        }
        for(int i=0;i<SCR.a16.Regions.size();i++){
            o.append("<polyline points="+"\"");
            for(int j=0;j<SCR.a16.Regions.get(i).size()-1;j++){
                o.append(SCR.a16.Regions.get(i).get(j).getL()+","+SCR.a16.Regions.get(i).get(j).getR()+" ");
            }
            String hash_code = rgbtohash(SCR.a16.color_array.get(i).getRed(),
                    SCR.a16.color_array.get(i).getGreen(),
                    SCR.a16.color_array.get(i).getBlue());
            
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
        for(int i=0;i<SCR.textbox_object.Rect_array.size();i++){
            if(!SCR.textbox_object.label.get(i).getR().contains("label -")){
                blanksvg = false;
                int x1 = SCR.textbox_object.Rect_array.get(i).x;
                int y1 = SCR.textbox_object.Rect_array.get(i).y+SCR.textbox_object.Rect_array.get(i).height;
                int y3 = SCR.textbox_object.Rect_array.get(i).height;
                y3 = 15;
                o.append("<text x="+"\""+x1+"\" ");
                o.append("y="+"\""+y1+"\" ");
                String language = SCR.textbox_object.Language_array.get(i);
                String ret = SCR.textbox_object.label.get(i).getR();
                
                switch (language) {
                    case "hin":
                        {
                            o.append("style="+"\""+"font-family: Kruti Dev 010; font-size : "+y3+"; stroke : #000000; fill : #000000;"+"\"" +">");
                            ScriptEngineManager factory = new ScriptEngineManager();
                            ScriptEngine engine = factory.getEngineByName("JavaScript");
                            engine.eval(Files.newBufferedReader(Paths.get(SCR.config.get("hindi_helper")), StandardCharsets.UTF_8));
                            Invocable inv = (Invocable) engine;
                            ret = (String) inv.invokeFunction("convert_to_unicode", ret );
                            break;
                        }
                    case "ben":
                        {
                            o.append("style="+"\""+"font-family: bengali; font-size : "+y3+"; stroke : #000000; fill : #000000;"+"\"" +">");
                            ScriptEngineManager factory = new ScriptEngineManager();
                            ScriptEngine engine = factory.getEngineByName("JavaScript");
                            engine.eval(Files.newBufferedReader(Paths.get(SCR.config.get("bangla_helper")), StandardCharsets.UTF_8));
                            engine.eval(Files.newBufferedReader(Paths.get(SCR.config.get("bangla_helper_main_2")), StandardCharsets.UTF_8));
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
        for(int i=0;i<SCR.textbox_object.Rect_array.size();i++){
            if(!SCR.textbox_object.label.get(i).getR().contains("label -")){
                blanksvg = false;
                int x1 = SCR.textbox_object.Rect_array.get(i).x;
                int y1 = SCR.textbox_object.Rect_array.get(i).y;
                int h1 = SCR.textbox_object.Rect_array.get(i).height;
                int w1 = SCR.textbox_object.Rect_array.get(i).width;
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
        dir = new File(SCR.config.get("library_directory_path"));
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
            String ht1 = "width="+"\""+ SCR.screen.getWidth() +"\" ";
            ht1 = ht1 + "height="+"\""+ SCR.screen.getHeight()+"\"";
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
