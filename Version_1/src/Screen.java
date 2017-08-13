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
public class Screen {
    static int label_counts=0;
    int startX = -1, startY = -1;
    static int edit_counter = 0;
    static JFrame main_frame=null;
    static JFrame preview_frame=null;
    static Hashtable<String,String> config;
    static AllControlsAndListeners a1 = null;
    static initial_setup_of_frames a2=null;
    static GetLines line_object = null;
    static GetCircles circle_object = null;
    static BufferedImage screen = null;
    static BufferedImage white = null;
    static BufferedImage screen1 = null;
    static GetRegions region_object = null;
    static GetTextbox textbox_object = null;
    static GetPolygon polygon_object = null;
    static GetArc arc_object = null;
    static modify_text a9 = null;
    static which_polygon a10 = null;
    static text_exe text_exe = null;
    static maths_science_exe maths_science_exe = null;
    static svg_generate a13 = null;
    static Image_Area_Listeners image_area_listeners = null;
    static refresh_all refresh_all = null;
    static GetPaths a16 = null;
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
    Screen(int r){};
    String label;
    Screen() throws IOException{
        repaint(screen,a2.screenCopy);
        a2.screenLabel.repaint();        
    }
    public boolean rect_draw_required(){
        if(preview_frame.isVisible()
                || Screen.a1.jSkipPage3.isDisplayable()
                || Screen.a1.jDeleteButton.isDisplayable()
                || Screen.a1.jSaveButton.isDisplayable()
                || Screen.a1.duplicateLineDetectionByDistance.isDisplayable()
                ){
            return false;
        }
        return true;
    }
    public boolean text_shift_required(){
        if(preview_frame.isVisible()
                || Screen.a1.jSelectText.isDisplayable()
                || Screen.a1.jSkipPage3.isDisplayable()
                || Screen.a1.jDeleteButton.isDisplayable()
                || Screen.a1.jSaveButton.isDisplayable()
                || Screen.a1.duplicateLineDetectionByDistance.isDisplayable()
                ){
            return false;
        }
        return true;
    }
    
//    public void mydrawLine(Graphics2D g,int x1, int y1, int x2, int y2) throws NoninvertibleTransformException{
//        Point p1 = new Point(x1,y1);
//        p1 = Screen.a1.get_zoomed_point_coordinate(p1);
//        Point p2 = new Point(x2,y2);
//        p2 = Screen.a1.get_zoomed_point_coordinate(p2);
//        g.drawLine(p1.x,p1.y, p2.x,p2.y);        
//    }
    
    public void repaint(BufferedImage orig, BufferedImage copy){
//    	Screen.a2.screenCopy = new BufferedImage(
//                                    (int)(Screen.screen.getWidth()*zoom_scale),
//                                    (int)(Screen.screen.getHeight()*zoom_scale),
//                                    Screen.screen.getType());
//        Screen.a2.screenLabel = new JLabel(new ImageIcon(Screen.a2.screenCopy));
//        Screen.a2.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
//        Screen.a2.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
//        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);
//        Screen.main_frame.validate();
//        Screen.main_frame.repaint();
//        Graphics2D g = Screen.a2.screenCopy.createGraphics();

        Graphics2D g = copy.createGraphics();
        try {
            zoom_affine_transform = g.getTransform(); //clones current graphics2D transform
            zoom_affine_transform.invert(); //sets affine transorm to its own inverse, matrix inversion
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

//            int w = Screen.a2.screenLabel.getWidth(); //panel width
//            int h = Screen.a2.screenLabel.getHeight();// panel height
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
            System.out.println("drawing points : "+line_object.lines.size());
        }
    	for(int k = 0; k<line_object.lines.size(); k++){
            g.setColor(current_color);
            
            g.fillRect(line_object.lines.get(k).getL(), line_object.lines.get(k).getR(), 2,2);
    	}
    	for(int k = 0; k<line_object.lines.size()-1; k++){
            g.setColor(line_object.colorArray.get(k));
            if(line_object.lineIndices.contains(k)){
                g.setStroke(new BasicStroke(3.0f));
                g.drawLine(line_object.lines.get(k).getL(), line_object.lines.get(k).getR(), line_object.lines.get(k+1).getL(), line_object.lines.get(k+1).getR());
                g.setStroke(new BasicStroke(1.0f));
            }
            else{
                g.drawLine(line_object.lines.get(k).getL(), line_object.lines.get(k).getR(), line_object.lines.get(k+1).getL(), line_object.lines.get(k+1).getR());
            }
            g.setColor(current_color);
            k++;
    	}
        if(polygon_object.points !=null){
            g.setColor(current_color);
            for(int h = 0; h<polygon_object.points.size(); h++){
                g.fillRect(polygon_object.points.get(h).getL(),polygon_object.points.get(h).getR(), 2,2);
            }
            for(int k = 0; k<polygon_object.points.size()-1; k++){
                g.drawLine(polygon_object.points.get(k).getL(), polygon_object.points.get(k).getR(), polygon_object.points.get(k+1).getL(), polygon_object.points.get(k+1).getR());
            }
            
        }
        
        if(region_object.regionPoints.size()>0){
            g.setColor(current_color);
            for(int h = 0; h<region_object.regionPoints.size(); h++){
                g.fillRect(region_object.regionPoints.get(h).getL(),region_object.regionPoints.get(h).getR(), 2,2);
            }
            for(int k = 0; k<region_object.regionPoints.size()-1; k++){
                g.drawLine(region_object.regionPoints.get(k).getL(), region_object.regionPoints.get(k).getR(), region_object.regionPoints.get(k+1).getL(), region_object.regionPoints.get(k+1).getR());
            }
        }
        
    	for(int k = 0; k<polygon_object.polygons.size(); k++){
            g.setColor(polygon_object.colorArray.get(k));
            if(polygon_object.polygonIndices.contains(k)){
                g.setStroke(new BasicStroke(3.0f));
                for(int h = 0; h<polygon_object.polygons.get(k).size()-1; h++){
                    g.drawLine(polygon_object.polygons.get(k).get(h).getL(),polygon_object.polygons.get(k).get(h).getR(), polygon_object.polygons.get(k).get(h+1).getL(),polygon_object.polygons.get(k).get(h+1).getR());
                }
                g.setStroke(new BasicStroke(1.0f));
            }
            else{
                for(int h = 0; h<polygon_object.polygons.get(k).size()-1; h++){
                    g.drawLine(polygon_object.polygons.get(k).get(h).getL(),polygon_object.polygons.get(k).get(h).getR(), polygon_object.polygons.get(k).get(h+1).getL(),polygon_object.polygons.get(k).get(h+1).getR());
                }
            }
            if(polygon_object.fillOrNot.get(k)==1){
                Polygon p = new Polygon();
                for(int h = 0; h<polygon_object.polygons.get(k).size()-1; h++){
                    p.addPoint(polygon_object.polygons.get(k).get(h).getL(), polygon_object.polygons.get(k).get(h).getR());
                }
                g.fillPolygon(p);
            }
            g.setColor(current_color);
    	}
        
    	for(int k = 0; k<region_object.regions.size(); k++){
            g.setColor(region_object.colorArray.get(k));
            if(region_object.regionIndices.contains(k)){
                g.setStroke(new BasicStroke(3.0f));
                for(int h = 0; h<region_object.regions.get(k).size()-1; h++){
                    g.drawLine(region_object.regions.get(k).get(h).getL(),region_object.regions.get(k).get(h).getR(), region_object.regions.get(k).get(h+1).getL(),region_object.regions.get(k).get(h+1).getR());
                }
                g.setStroke(new BasicStroke(1.0f));
            }
            else{
                for(int h = 0; h<region_object.regions.get(k).size()-1; h++){
                    g.drawLine(region_object.regions.get(k).get(h).getL(),region_object.regions.get(k).get(h).getR(), region_object.regions.get(k).get(h+1).getL(),region_object.regions.get(k).get(h+1).getR());
                }
            }
            if(region_object.fillArray.get(k)==1){
                Polygon p = new Polygon();
                for(int h = 0; h<region_object.regions.get(k).size()-1; h++){
                    p.addPoint(region_object.regions.get(k).get(h).getL(), region_object.regions.get(k).get(h).getR());
                }
                g.fillPolygon(p);
            }
            g.setColor(current_color);
    	}        
    	
    	for(int k = 0; k<circle_object.circles.size(); k++){
            g.setColor(current_color);
            int r1 = (int)(float)circle_object.circles.get(k).getL();
            int r2 = (int)(float)circle_object.circles.get(k).getR();
            g.fillRect(r1,r2,2,2);
    	}
        
        
        for(int k = 0; k<arc_object.circles.size(); k++){
            g.setColor(current_color);
            int r1 = (int)(float)arc_object.circles.get(k).getL();
            int r2 = (int)(float)arc_object.circles.get(k).getR();
            g.fillRect(r1,r2,2,2);
    	}

    	for(int k = 0; k<circle_object.centers.size(); k++){
            int r1 = (int)(float)circle_object.centers.get(k).getL();
            int r2 = (int)(float)circle_object.centers.get(k).getR();
            int r3 = (int)(float)circle_object.radii.get(k);
            g.setColor(circle_object.colorArray.get(k));
            if(circle_object.circleIndices.contains(k)){
                g.setStroke(new BasicStroke(3.0f));
                g.drawArc(r1-r3,r2-r3,2*r3,2*r3, 0, 360);
                g.setStroke(new BasicStroke(1.0f));
            }
            else{
                g.drawArc(r1-r3,r2-r3,2*r3,2*r3, 0, 360); 
            }
            //System.out.println(g.getColor());
            if(circle_object.fillArray.get(k)==1){
                g.fillArc(r1-r3,r2-r3,2*r3,2*r3, 0, 360);
            }
            g.setColor(current_color);
    	}
        
        for(int k = 0; k<arc_object.centers.size(); k++){
            int r1 = (int)(float)arc_object.centers.get(k).getL();
            int r2 = (int)(float)arc_object.centers.get(k).getR();
            int r3 = (int)(float)arc_object.radii.get(k);
            int r4 = arc_object.arcAngles.get(k).getL();
            int r5 = arc_object.arcAngles.get(k).getR();
            g.setColor(arc_object.colorArray.get(k));
            if(arc_object.circleIndices.contains(k)){
               g.setStroke(new BasicStroke(3.0f));
               g.drawArc(r1-r3,r2-r3,2*r3,2*r3, r4, r5);
               g.setStroke(new BasicStroke(1.0f));
            }
            else{
                g.drawArc(r1-r3,r2-r3,2*r3,2*r3, r4,r5); 
            }
            if(arc_object.fillArray.get(k)==1){
                g.fillArc(r1-r3,r2-r3,2*r3,2*r3, r4,r5);
            }
            g.setColor(current_color);
    	}
        
        if(a16.regionPoints.size()>0){
            g.setColor(current_color);
            for(int h = 0; h<a16.regionPoints.size(); h++){
                g.fillRect(a16.regionPoints.get(h).getL(),a16.regionPoints.get(h).getR(), 2,2);
            }
            for(int k = 0; k<a16.regionPoints.size()-1; k++){
                g.drawLine(a16.regionPoints.get(k).getL(), a16.regionPoints.get(k).getR(), a16.regionPoints.get(k+1).getL(), a16.regionPoints.get(k+1).getR());
            }
        }
        
    	for(int k = 0; k<a16.regions.size(); k++){
            g.setColor(a16.colorArray.get(k));
            if(a16.regionIndices.contains(k)){
                g.setStroke(new BasicStroke(3.0f));
                for(int h = 0; h<a16.regions.get(k).size()-2; h++){
                    g.drawLine(a16.regions.get(k).get(h).getL(),a16.regions.get(k).get(h).getR(), a16.regions.get(k).get(h+1).getL(),a16.regions.get(k).get(h+1).getR());
                }
                g.setStroke(new BasicStroke(1.0f));
            }
            else{
                for(int h = 0; h<a16.regions.get(k).size()-2; h++){
                    g.drawLine(a16.regions.get(k).get(h).getL(),a16.regions.get(k).get(h).getR(), a16.regions.get(k).get(h+1).getL(),a16.regions.get(k).get(h+1).getR());
                }	    
            }
            g.setColor(current_color);
    	}
        //System.out.println(a1.Label.getFont());
        for(int k = 0; k<textbox_object.rectangleArray.size(); k++){
            g.setColor(Color.black);
            AttributedString at = new AttributedString(textbox_object.label.get(k).getR());
            
            switch (textbox_object.languageArray.get(k)) {
                case "hin":
                    //System.out.println("setting hindi font");
                    g.setFont(Screen.a1.hindiFont);
                    at.addAttribute(TextAttribute.FONT, Screen.a1.hindiFont);
                    break;
                case "eng":
                    g.setFont(Screen.a1.englishFont);
                    at.addAttribute(TextAttribute.FONT, Screen.a1.englishFont);
                    //System.out.println("setting english font");
                    break;
                case "ben":
                    g.setFont(Screen.a1.bengaliFont);
                    at.addAttribute(TextAttribute.FONT, Screen.a1.bengaliFont);
                    break;
            //System.out.println("setting something else");
                default:
                    break;
            }
            if(textbox_object.rectangleIndices.contains(k)){
    		if(!textbox_object.label.get(k).getR().contains("label -")){
                    if(text_shift_required()){
                        g.drawString(at.getIterator(),textbox_object.rectangleArray.get(k).x, textbox_object.rectangleArray.get(k).y-4);
                    }
                    else{
                        g.drawString(at.getIterator(),
                                textbox_object.rectangleArray.get(k).x,
                                textbox_object.rectangleArray.get(k).y+textbox_object.rectangleArray.get(k).height);
                    }
                }
                
                g.setStroke(new BasicStroke(3.0f));
                if(rect_draw_required()){
                    g.drawRect(textbox_object.rectangleArray.get(k).x, textbox_object.rectangleArray.get(k).y,
                            textbox_object.rectangleArray.get(k).width,textbox_object.rectangleArray.get(k).height);
                }
                g.setStroke(new BasicStroke(1.0f));
            }
            else{
                if(!textbox_object.label.get(k).getR().contains("label -")){
                    if(text_shift_required()){
                        g.drawString(at.getIterator(),textbox_object.rectangleArray.get(k).x, textbox_object.rectangleArray.get(k).y-4);
                    }
                    else{
                        g.drawString(at.getIterator(),
                                textbox_object.rectangleArray.get(k).x,
                                textbox_object.rectangleArray.get(k).y+textbox_object.rectangleArray.get(k).height);
                    }
                }
                if(rect_draw_required()){
                    g.drawRect(textbox_object.rectangleArray.get(k).x, textbox_object.rectangleArray.get(k).y, textbox_object.rectangleArray.get(k).width,textbox_object.rectangleArray.get(k).height);
                }
            }
            if(a9.selected_rect==k){
                g.setColor(Color.RED);
                if(!textbox_object.label.get(k).getR().contains("label -")){
                    if(text_shift_required()){
                        g.drawString(at.getIterator(),textbox_object.rectangleArray.get(k).x, textbox_object.rectangleArray.get(k).y-4);
                    }
                    else{
                        g.drawString(at.getIterator(),
                                textbox_object.rectangleArray.get(k).x,
                                textbox_object.rectangleArray.get(k).y+textbox_object.rectangleArray.get(k).height);
                    }
                }
                if(rect_draw_required()){
                    g.drawRect(textbox_object.rectangleArray.get(k).x+2, textbox_object.rectangleArray.get(k).y+2, textbox_object.rectangleArray.get(k).width-4,textbox_object.rectangleArray.get(k).height-4);
                }
                g.setColor(current_color);
            }
            g.setColor(current_color);
        }
        
    	if(textbox_object.captureRectangle != null){
            g.setColor(Color.black);
            g.drawRect(textbox_object.captureRectangle.x, textbox_object.captureRectangle.y, textbox_object.captureRectangle.width, textbox_object.captureRectangle.height);
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
        Screen t1= new Screen(0);
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
        
        main_frame.setIconImage(ImageIO.read(new File(Screen.config.get("Frame_Icon"))));
    	main_frame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        main_frame.setSize(800,600);
        main_frame.setExtendedState(main_frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        a1 = new AllControlsAndListeners();
    	a2 = new initial_setup_of_frames();
    	line_object = new GetLines();
    	circle_object = new GetCircles();
    	region_object = new GetRegions();
    	textbox_object = new GetTextbox();
    	polygon_object = new GetPolygon();
        arc_object = new GetArc();
        a9 = new modify_text();
        a10 = new which_polygon();
        a16 = new GetPaths();
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
                Screen main = new Screen();
            } catch (IOException ex) {
                error.write("Invoke later method shows error\n");
            }
            main_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            main_frame.setVisible(true);
        });
        error.close();
    }
}		