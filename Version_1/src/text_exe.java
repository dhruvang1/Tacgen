import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

public class text_exe {
    int r =0;
    Screen t1 = new Screen(r);
    String language="eng";
    public void load() throws FileNotFoundException, IOException, InterruptedException, ScriptException, NoSuchMethodException{
        Runtime set_tessdata_prefix = Runtime.getRuntime();
        Process pr_set = set_tessdata_prefix.exec("setx TESSDATA_PREFIX \""+ Screen.config.get("library_directory_path")+"\"");
        pr_set.waitFor();
        Runtime rt = Runtime.getRuntime();
        File dir = new File(Screen.config.get("library_directory_path"));
        System.out.println("text start");
        //System.out.println(Screen.config.get("text.exe"));
        //System.out.println(Screen.current_file.getAbsolutePath());
        Process pr = rt.exec("\""+ Screen.config.get("text.exe")+"\""+" "+"\""+ Screen.current_file.getAbsolutePath()+"\""+" "+language,null,dir);
        //Process pr = rt.exec("E:/Acads/CSD750/BANA_28_05/text.exe "+Screen.current_file.getAbsolutePath()+" "+language);
        pr.waitFor();
        System.out.println("text done");
        File f = new File(Screen.current_file.getAbsolutePath()+".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        
        String s1 = br.readLine();
        while(s1!=null){
            if(s1.contains("-")){
                int y = s1.indexOf("-");
                String [] fr = s1.split("-");
                fr[1] = s1.substring(y+1);
                String label = fr[1];
                String [] gt = fr[0].split(",");
                Rectangle r1 = new Rectangle(Integer.valueOf(gt[0]),Integer.valueOf(gt[1]),Integer.valueOf(gt[2]),Integer.valueOf(gt[3]));
               // Pair<String,Rectangle> p = new Pair<String,Rectangle>(language,r1);
                if(!Screen.textbox_object.rectangleArray.contains(r1)){
                    switch (language) {
                        case "hin":
                            {
                                ScriptEngineManager factory = new ScriptEngineManager();
                                ScriptEngine engine = factory.getEngineByName("JavaScript");
                                engine.eval(Files.newBufferedReader(Paths.get(Screen.config.get("hindi_helper")), StandardCharsets.UTF_8));
                                Invocable inv = (Invocable) engine;
                                //inv.invokeFunction("convert_to_unicode", str );
                                label = (String) inv.invokeFunction("Convert_to_Kritidev_010", label);
                                break;
                            }
                        case "ben":
                            {
                                ScriptEngineManager factory = new ScriptEngineManager();
                                ScriptEngine engine = factory.getEngineByName("JavaScript");
                                engine.eval(Files.newBufferedReader(Paths.get(Screen.config.get("bangla_helper")), StandardCharsets.UTF_8));
                                engine.eval(Files.newBufferedReader(Paths.get(Screen.config.get("bangla_helper_main")), StandardCharsets.UTF_8));
                                Invocable inv = (Invocable) engine;
                                label = (String) inv.invokeFunction("ConvertToASCII", "bijoy",label);
                                break;
                            }
                        case "eng":
                            break;
                        default:
                            break;
                    }
                    
                    String h1 ="";String t1 = "";
                    Screen.label_counts++;
                    Pair<String,String> temp_pair = new Pair <String,String> (h1,t1);
                    temp_pair.setL(String.valueOf(Screen.label_counts));
                    temp_pair.setR(label);
                    Screen.textbox_object.label.add(temp_pair);
                    Screen.textbox_object.rectangleArray.add(r1);
                    Screen.textbox_object.languageArray.add(language);
                }
            }
            s1 = br.readLine();
        }
        br.close();
    }
    public void store() throws FileNotFoundException, IOException, SAXException, ParserConfigurationException, XPathExpressionException, ScriptException, NoSuchMethodException{
        Screen.a13.svg_file();
        BufferedImage temp = ImageIO.read(t1.current_file);
        for(int i = 0; i< Screen.textbox_object.rectangleArray.size(); i++){
            int x1 = Screen.textbox_object.rectangleArray.get(i).x;
            int y1 = Screen.textbox_object.rectangleArray.get(i).y;
            int w = Screen.textbox_object.rectangleArray.get(i).width;
            int h = Screen.textbox_object.rectangleArray.get(i).height;
            int r = 255;
            int g = 255;
            int b = 255;
            int col = (r << 16) | (g << 8) | b;
            for(int f1 = y1;f1<y1+h;f1++){
                for(int g1 = x1;g1<x1+w;g1++){
                    temp.setRGB(g1, f1, col);
                }
            }
        }
        String h1 = t1.current_file.getAbsolutePath();
        String h2 = String.valueOf(t1.current_file.getName());
        String ty = h2.substring(0,h2.lastIndexOf("."))+"_1"+h2.substring(h2.lastIndexOf("."));
        String h4 = h1;
        int index=h4.lastIndexOf("\\");
        h4=h4.substring(0, index+1)+ty;
        File outputfile = new File(h4);
        ImageIO.write(temp, "PNG", outputfile);
        
//        Screen.current_file = outputfile;
        Screen.screen = ImageIO.read(outputfile);
        //Screen.a2.pane.remove(Screen.a2.screenLabel);
        Screen.a2.screenCopy = new BufferedImage(
                                    (int)(Screen.zoom_scale* Screen.screen.getWidth()),
                                    (int)(Screen.zoom_scale* Screen.screen.getHeight()),
                                    Screen.screen.getType());
        Screen.a2.screenLabel = new JLabel(new ImageIcon(Screen.a2.screenCopy));
        Screen.a2.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Screen.a2.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);
        Screen.refresh_all.refresh();
        Screen.restore_svg.restore();
        Screen.main_frame.validate();
        Screen.main_frame.repaint();
        t1.repaint(Screen.screen, Screen.a2.screenCopy);
        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
        Screen.main_frame.setVisible(true);
    }
}
