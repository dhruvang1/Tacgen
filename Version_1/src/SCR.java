import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.AttributedString;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.swing.*;
public class SCR  {
    static int label_counts=0;
    int startX = -1, startY = -1;
    static int edit_counter = 0;
    static JFrame main_frame=null;
    static JFrame preview_frame=null;
    static Hashtable<String,String> config;
    static all_controls_and_listeners a1 = null;
    static initial_setup_of_frames a2=null;
    static get_lines line_object = null;
    static get_circles circle_object = null;
    static BufferedImage screen = null;
    static BufferedImage white = null;
    static BufferedImage screen1 = null;
    static get_regions region_object = null;
    static get_textbox textbox_object = null;
    static get_polygon polygon_object = null;
    static get_arc arc_object = null;
    static modify_text a9 = null;
    static which_polygon a10 = null;
    static text_exe text_exe = null;
    static maths_science_exe maths_science_exe = null;
    static svg_generate a13 = null;
    static Image_Area_Listeners image_area_listeners = null;
    static refresh_all refresh_all = null;
    static get_paths a16 = null;
    static restore_svg restore_svg = null;
    static color color_obj = null;
    
    static page0_open_image page_0=null;
    static page1_auto_text a20=null;
    static page2_manual_text a21=null;
    static page3_auto_maths_science a22=null;
    static page4_manual_maths_science a23=null;
    static page4_maths_parameter a23_maths = null;
    static page5_color_mapping a24=null;
    
    static Point start = new Point();
    static Point rectangle_translate_start = new Point();
    static File current_file;
    static String current_file_name;
    static Color current_color = Color.BLACK;
    
    static double zoom_scale = 1.0;
    static AffineTransform zoom_affine_transform;
    
    boolean checked = false;
    Point startPoint;
    Point endPoint;
    int i=0,j=0;
    SCR(int r){};
    String label;
    SCR() throws IOException{
        repaint(screen,a2.screenCopy);
        a2.screenLabel.repaint();        
    }
    public boolean rect_draw_required(){
        if(preview_frame.isVisible()
                ||SCR.a1.skip_page3.isDisplayable() 
                || SCR.a1.delete_page4.isDisplayable()
                || SCR.a1.Save.isDisplayable()
                || SCR.a1.dup_line_dist_slider.isDisplayable()
                ){
            return false;
        }
        return true;
    }
    public boolean text_shift_required(){
        if(preview_frame.isVisible()
                || SCR.a1.Select_Text.isDisplayable()
                || SCR.a1.skip_page3.isDisplayable()
                || SCR.a1.delete_page4.isDisplayable()
                || SCR.a1.Save.isDisplayable()
                || SCR.a1.dup_line_dist_slider.isDisplayable()
                ){
            return false;
        }
        return true;
    }
    
//    public void mydrawLine(Graphics2D g,int x1, int y1, int x2, int y2) throws NoninvertibleTransformException{
//        Point p1 = new Point(x1,y1);
//        p1 = SCR.a1.get_zoomed_point_coordinate(p1);
//        Point p2 = new Point(x2,y2);
//        p2 = SCR.a1.get_zoomed_point_coordinate(p2);
//        g.drawLine(p1.x,p1.y, p2.x,p2.y);        
//    }
    
    public void repaint(BufferedImage orig, BufferedImage copy){
//    	SCR.a2.screenCopy = new BufferedImage(
//                                    (int)(SCR.screen.getWidth()*zoom_scale),
//                                    (int)(SCR.screen.getHeight()*zoom_scale),
//                                    SCR.screen.getType());
//        SCR.a2.screenLabel = new JLabel(new ImageIcon(SCR.a2.screenCopy));
//        SCR.a2.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
//        SCR.a2.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
//        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);
//        SCR.main_frame.validate();
//        SCR.main_frame.repaint();
//        Graphics2D g = SCR.a2.screenCopy.createGraphics();

        Graphics2D g = copy.createGraphics();
        try {
            zoom_affine_transform = g.getTransform(); //clones current graphics2D transform
            zoom_affine_transform.invert(); //sets affine transorm to its own inverse, matrix inversion
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

//            int w = SCR.a2.screenLabel.getWidth(); //panel width
//            int h = SCR.a2.screenLabel.getHeight();// panel height
//            double x = (w - zoom_scale * orig.getWidth())/2;
//            double y = (h - zoom_scale * orig.getHeight())/2;
//            System.out.println("x,y: "+x+" "+y);
            //x,y changes only due to scale
            //each graphics2D component has its own transform
            //g.translate(x,y); // this sets origin for the graphics2D component at (x,y)
            g.scale(zoom_scale, zoom_scale); //scales xfrm 
            // g.transform(xfrm); //let current transform of g be C, then this line does C = C(xfrm), basically composition of transforms
            zoom_affine_transform.concatenate(g.getTransform()); // zoom_affine_transform = inverse(initial g transform) * Current g transform
            g.drawImage(orig,0,0,null); //renders image
            //none of the above operation change the image variable
        } catch (NoninvertibleTransformException ex) {
        }
        //g.drawImage(orig,0,0, null);
    	
        
        g.setColor(Color.blue);
        if(copy.getWidth()==1340){
            System.out.println("drawing points : "+line_object.Lines.size());
        }
    	for(int k=0;k<line_object.Lines.size();k++){
            g.setColor(current_color);
            
            g.fillRect(line_object.Lines.get(k).getL(), line_object.Lines.get(k).getR(), 2,2);
    	}
    	for(int k=0;k<line_object.Lines.size()-1;k++){
            g.setColor(line_object.color_array.get(k));
            if(line_object.line_indices.contains(k)){
                g.setStroke(new BasicStroke(3.0f));
                g.drawLine(line_object.Lines.get(k).getL(), line_object.Lines.get(k).getR(), line_object.Lines.get(k+1).getL(), line_object.Lines.get(k+1).getR());
                g.setStroke(new BasicStroke(1.0f));
            }
            else{
                g.drawLine(line_object.Lines.get(k).getL(), line_object.Lines.get(k).getR(), line_object.Lines.get(k+1).getL(), line_object.Lines.get(k+1).getR());
            }
            g.setColor(current_color);
            k++;
    	}
        if(polygon_object.Points!=null){
            g.setColor(current_color);
            for(int h=0;h<polygon_object.Points.size();h++){
                g.fillRect(polygon_object.Points.get(h).getL(),polygon_object.Points.get(h).getR(), 2,2);
            }
            for(int k=0;k<polygon_object.Points.size()-1;k++){
                g.drawLine(polygon_object.Points.get(k).getL(), polygon_object.Points.get(k).getR(), polygon_object.Points.get(k+1).getL(), polygon_object.Points.get(k+1).getR());
            }
            
        }
        
        if(region_object.Points_regions.size()>0){
            g.setColor(current_color);
            for(int h=0;h<region_object.Points_regions.size();h++){
                g.fillRect(region_object.Points_regions.get(h).getL(),region_object.Points_regions.get(h).getR(), 2,2);
            }
            for(int k=0;k<region_object.Points_regions.size()-1;k++){
                g.drawLine(region_object.Points_regions.get(k).getL(), region_object.Points_regions.get(k).getR(), region_object.Points_regions.get(k+1).getL(), region_object.Points_regions.get(k+1).getR());
            }
        }
        
    	for(int k=0;k<polygon_object.Polygons.size();k++){
            g.setColor(polygon_object.color_array.get(k));
            if(polygon_object.polygon_indices.contains(k)){
                g.setStroke(new BasicStroke(3.0f));
                for(int h=0;h<polygon_object.Polygons.get(k).size()-1;h++){
                    g.drawLine(polygon_object.Polygons.get(k).get(h).getL(),polygon_object.Polygons.get(k).get(h).getR(), polygon_object.Polygons.get(k).get(h+1).getL(),polygon_object.Polygons.get(k).get(h+1).getR());
                }
                g.setStroke(new BasicStroke(1.0f));
            }
            else{
                for(int h=0;h<polygon_object.Polygons.get(k).size()-1;h++){
                    g.drawLine(polygon_object.Polygons.get(k).get(h).getL(),polygon_object.Polygons.get(k).get(h).getR(), polygon_object.Polygons.get(k).get(h+1).getL(),polygon_object.Polygons.get(k).get(h+1).getR());
                }
            }
            if(polygon_object.fill_or_not.get(k)==1){
                Polygon p = new Polygon();
                for(int h=0;h<polygon_object.Polygons.get(k).size()-1;h++){
                    p.addPoint(polygon_object.Polygons.get(k).get(h).getL(), polygon_object.Polygons.get(k).get(h).getR());
                }
                g.fillPolygon(p);
            }
            g.setColor(current_color);
    	}
        
    	for(int k=0;k<region_object.Regions.size();k++){
            g.setColor(region_object.color_array.get(k));
            if(region_object.region_indices.contains(k)){
                g.setStroke(new BasicStroke(3.0f));
                for(int h=0;h<region_object.Regions.get(k).size()-1;h++){
                    g.drawLine(region_object.Regions.get(k).get(h).getL(),region_object.Regions.get(k).get(h).getR(), region_object.Regions.get(k).get(h+1).getL(),region_object.Regions.get(k).get(h+1).getR());
                }
                g.setStroke(new BasicStroke(1.0f));
            }
            else{
                for(int h=0;h<region_object.Regions.get(k).size()-1;h++){
                    g.drawLine(region_object.Regions.get(k).get(h).getL(),region_object.Regions.get(k).get(h).getR(), region_object.Regions.get(k).get(h+1).getL(),region_object.Regions.get(k).get(h+1).getR());
                }
            }
            if(region_object.fill_or_not.get(k)==1){
                Polygon p = new Polygon();
                for(int h=0;h<region_object.Regions.get(k).size()-1;h++){
                    p.addPoint(region_object.Regions.get(k).get(h).getL(), region_object.Regions.get(k).get(h).getR());
                }
                g.fillPolygon(p);
            }
            g.setColor(current_color);
    	}        
    	
    	for(int k=0;k<circle_object.Circles.size();k++){
            g.setColor(current_color);
            int r1 = (int)(float)circle_object.Circles.get(k).getL();
            int r2 = (int)(float)circle_object.Circles.get(k).getR();
            g.fillRect(r1,r2,2,2);
    	}
        
        
        for(int k=0;k<arc_object.Circles.size();k++){
            g.setColor(current_color);
            int r1 = (int)(float)arc_object.Circles.get(k).getL();
            int r2 = (int)(float)arc_object.Circles.get(k).getR();
            g.fillRect(r1,r2,2,2);
    	}

    	for(int k=0;k<circle_object.Centers.size();k++){
            int r1 = (int)(float)circle_object.Centers.get(k).getL();
            int r2 = (int)(float)circle_object.Centers.get(k).getR();
            int r3 = (int)(float)circle_object.Radius.get(k);
            g.setColor(circle_object.color_array.get(k));
            if(circle_object.circle_indices.contains(k)){
                g.setStroke(new BasicStroke(3.0f));
                g.drawArc(r1-r3,r2-r3,2*r3,2*r3, 0, 360);
                g.setStroke(new BasicStroke(1.0f));
            }
            else{
                g.drawArc(r1-r3,r2-r3,2*r3,2*r3, 0, 360); 
            }
            //System.out.println(g.getColor());
            if(circle_object.fill_or_not.get(k)==1){
                g.fillArc(r1-r3,r2-r3,2*r3,2*r3, 0, 360);
            }
            g.setColor(current_color);
    	}
        
        for(int k=0;k<arc_object.Centers.size();k++){
            int r1 = (int)(float)arc_object.Centers.get(k).getL();
            int r2 = (int)(float)arc_object.Centers.get(k).getR();
            int r3 = (int)(float)arc_object.Radius.get(k);
            int r4 = arc_object.arc_angles.get(k).getL();
            int r5 = arc_object.arc_angles.get(k).getR();
            g.setColor(arc_object.color_array.get(k));
            if(arc_object.circle_indices.contains(k)){
               g.setStroke(new BasicStroke(3.0f));
               g.drawArc(r1-r3,r2-r3,2*r3,2*r3, r4, r5);
               g.setStroke(new BasicStroke(1.0f));
            }
            else{
                g.drawArc(r1-r3,r2-r3,2*r3,2*r3, r4,r5); 
            }
            if(arc_object.fill_or_not.get(k)==1){
                g.fillArc(r1-r3,r2-r3,2*r3,2*r3, r4,r5);
            }
            g.setColor(current_color);
    	}
        
        if(a16.Points_regions.size()>0){
            g.setColor(current_color);
            for(int h=0;h<a16.Points_regions.size();h++){
                g.fillRect(a16.Points_regions.get(h).getL(),a16.Points_regions.get(h).getR(), 2,2);
            }
            for(int k=0;k<a16.Points_regions.size()-1;k++){
                g.drawLine(a16.Points_regions.get(k).getL(), a16.Points_regions.get(k).getR(), a16.Points_regions.get(k+1).getL(), a16.Points_regions.get(k+1).getR());
            }
        }
        
    	for(int k=0;k<a16.Regions.size();k++){
            g.setColor(a16.color_array.get(k));
            if(a16.region_indices.contains(k)){
                g.setStroke(new BasicStroke(3.0f));
                for(int h=0;h<a16.Regions.get(k).size()-2;h++){
                    g.drawLine(a16.Regions.get(k).get(h).getL(),a16.Regions.get(k).get(h).getR(), a16.Regions.get(k).get(h+1).getL(),a16.Regions.get(k).get(h+1).getR());
                }
                g.setStroke(new BasicStroke(1.0f));
            }
            else{
                for(int h=0;h<a16.Regions.get(k).size()-2;h++){
                    g.drawLine(a16.Regions.get(k).get(h).getL(),a16.Regions.get(k).get(h).getR(), a16.Regions.get(k).get(h+1).getL(),a16.Regions.get(k).get(h+1).getR());
                }	    
            }
            g.setColor(current_color);
    	}
        //System.out.println(a1.Label.getFont());
        for(int k=0;k<textbox_object.Rect_array.size();k++){
            g.setColor(Color.black);
            AttributedString at = new AttributedString(textbox_object.label.get(k).getR());
            
            switch (textbox_object.Language_array.get(k)) {
                case "hin":
                    //System.out.println("setting hindi font");
                    g.setFont(SCR.a1.hin_font);
                    at.addAttribute(TextAttribute.FONT, SCR.a1.hin_font);
                    break;
                case "eng":
                    g.setFont(SCR.a1.eng_font);
                    at.addAttribute(TextAttribute.FONT, SCR.a1.eng_font);
                    //System.out.println("setting english font");
                    break;
                case "ben":
                    g.setFont(SCR.a1.ben_font);
                    at.addAttribute(TextAttribute.FONT, SCR.a1.ben_font);
                    break;
            //System.out.println("setting something else");
                default:
                    break;
            }
            if(textbox_object.rect_indices.contains(k)){
    		if(!textbox_object.label.get(k).getR().contains("label -")){
                    if(text_shift_required()){
                        g.drawString(at.getIterator(),textbox_object.Rect_array.get(k).x, textbox_object.Rect_array.get(k).y-4);
                    }
                    else{
                        g.drawString(at.getIterator(),
                                textbox_object.Rect_array.get(k).x, 
                                textbox_object.Rect_array.get(k).y+textbox_object.Rect_array.get(k).height);                            
                    }
                }
                
                g.setStroke(new BasicStroke(3.0f));
                if(rect_draw_required()){
                    g.drawRect(textbox_object.Rect_array.get(k).x, textbox_object.Rect_array.get(k).y,
                            textbox_object.Rect_array.get(k).width,textbox_object.Rect_array.get(k).height);
                }
                g.setStroke(new BasicStroke(1.0f));
            }
            else{
                if(!textbox_object.label.get(k).getR().contains("label -")){
                    if(text_shift_required()){
                        g.drawString(at.getIterator(),textbox_object.Rect_array.get(k).x, textbox_object.Rect_array.get(k).y-4);
                    }
                    else{
                        g.drawString(at.getIterator(),
                                textbox_object.Rect_array.get(k).x, 
                                textbox_object.Rect_array.get(k).y+textbox_object.Rect_array.get(k).height);                            
                    }
                }
                if(rect_draw_required()){
                    g.drawRect(textbox_object.Rect_array.get(k).x, textbox_object.Rect_array.get(k).y, textbox_object.Rect_array.get(k).width,textbox_object.Rect_array.get(k).height);
                }
            }
            if(a9.selected_rect==k){
                g.setColor(Color.RED);
                if(!textbox_object.label.get(k).getR().contains("label -")){
                    if(text_shift_required()){
                        g.drawString(at.getIterator(),textbox_object.Rect_array.get(k).x, textbox_object.Rect_array.get(k).y-4);
                    }
                    else{
                        g.drawString(at.getIterator(),
                                textbox_object.Rect_array.get(k).x, 
                                textbox_object.Rect_array.get(k).y+textbox_object.Rect_array.get(k).height);                            
                    }
                }
                if(rect_draw_required()){
                    g.drawRect(textbox_object.Rect_array.get(k).x+2, textbox_object.Rect_array.get(k).y+2, textbox_object.Rect_array.get(k).width-4,textbox_object.Rect_array.get(k).height-4);
                }
                g.setColor(current_color);
            }
            g.setColor(current_color);
        }
        
    	if(textbox_object.captureRect!= null){
            g.setColor(Color.black);
            g.drawRect(textbox_object.captureRect.x, textbox_object.captureRect.y, textbox_object.captureRect.width, textbox_object.captureRect.height);
            g.setColor(current_color);
        }
    	g.dispose();
    }
    
    public void fill_config() throws FileNotFoundException, IOException{
        File f = new File(System.getProperty("user.dir")+"\\resources\\config");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
            String s1 = br.readLine();
            while(s1!=null){
                String[] arr= s1.split("##");
                config.put(arr[0], System.getProperty("user.dir")+arr[1]);
                s1 = br.readLine();
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        SCR t1= new SCR(0);
        config = new Hashtable<>();
        t1.fill_config();
//        System.setProperty("file.encoding","UTF-8");
        PrintWriter error = new PrintWriter(new FileOutputStream(new File(config.get("error_log")),false));
        error.write("TacGen started\n");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            error.write("Frame Style(Look and Feel) could not be set to windows classic\n");
        }
        
        main_frame= new JFrame("TacGen : Tool For Interactive Tactile Generation ");
        preview_frame = new JFrame("Preview : See the progress so Far ");
        
        main_frame.setIconImage(ImageIO.read(new File(SCR.config.get("Frame_Icon"))));
    	main_frame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        main_frame.setSize(800,600);
        main_frame.setExtendedState(main_frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        a1 = new all_controls_and_listeners();
    	a2 = new initial_setup_of_frames();
    	line_object = new get_lines();
    	circle_object = new get_circles();
    	region_object = new get_regions();
    	textbox_object = new get_textbox();
    	polygon_object = new get_polygon();
        arc_object = new get_arc();
        a9 = new modify_text();
        a10 = new which_polygon();
        a16 = new get_paths();
        text_exe = new text_exe();
        maths_science_exe = new maths_science_exe();
        a13 = new svg_generate();
        image_area_listeners = new Image_Area_Listeners();
        refresh_all = new refresh_all();
        restore_svg = new restore_svg();
        color_obj = new color();
        page_0=new page0_open_image();

    	SwingUtilities.invokeLater(() -> {
            try {
                SCR main = new SCR();
            } catch (IOException ex) {
                error.write("Invoke later method shows error\n");
            }
            main_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            main_frame.setVisible(true);
        });
        error.close();
    }
}		