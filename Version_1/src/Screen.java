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
//    int startX = -1, startY = -1;
//    static int edit_counter = 0;
    static JFrame mainFrame =null;
    static JFrame previewFrame =null;
    static Hashtable<String,String> config;
    static AllControlsAndListeners allControlsAndListeners = null;
    static InitialFrameSetup initialFrameSetup =null;
    static GetLines linesObject = null;
    static GetCircles circlesObject = null;
    static BufferedImage bufferedImageScreen = null;
    static BufferedImage bufferedImageWhite = null;
//    static BufferedImage screen1 = null;
    static GetRegions regionsObject = null;
    static GetTextbox textboxObject = null;
    static GetPolygon polygonObject = null;
    static GetArc arcObject = null;
    static ModifyText modifyTextObject = null;
    static whichPolygon whichPolygonObject = null;
    static text_exe textExeObject = null;
    static maths_science_exe maths_science_exe = null;
    static GenerateSVG svgGenerateObject = null;
    static ImageAreaListeners imageAreaListeners = null;
    static AllObjectReinitializer allObjectReinitializer = null;
    static GetPaths pathsObject = null;
    static RestoreSVG restoreSVG = null;
    static color colorObject = null;
    
    static Page0OpenImage page0OpenImage =null;
    static Page1AutoText page1AutoText =null;
    static Page2ManualText page2ManualText =null;
    static Page3AutoMathScience page3AutoMathScience =null;
    static Page4ManualMathScience page4ManualMathScience =null;
    static Page4MathParameter page4MathParameter = null;
    static Page5ColorMapping page5ColorMapping =null;
    
    static Point start = new Point();
    static Point rectangleTranslateStart = new Point();
    static File currentFile;
    static String currentFileName;
    static Color currentColor = Color.BLACK;
    
    static double zoomScale = 1.0;
    static AffineTransform zoomAffineTransform;
    
//    boolean checked = false;
//    Point startPoint;
//    Point endPoint;
    int i=0;//,j=0;
    Screen(int r){};
//    String label;
    Screen() throws IOException{
        repaint(bufferedImageScreen, initialFrameSetup.screenCopy);
        initialFrameSetup.screenLabel.repaint();
    }
    public boolean rectangleDrawRequired(){
        if(previewFrame.isVisible()
                || Screen.allControlsAndListeners.jSkipPage3.isDisplayable()
                || Screen.allControlsAndListeners.jDeleteButton.isDisplayable()
                || Screen.allControlsAndListeners.jSaveButton.isDisplayable()
                || Screen.allControlsAndListeners.duplicateLineDetectionByDistance.isDisplayable()
                ){
            return false;
        }
        return true;
    }
    public boolean textShiftRequired(){
        if(previewFrame.isVisible()
                || Screen.allControlsAndListeners.jSelectText.isDisplayable()
                || Screen.allControlsAndListeners.jSkipPage3.isDisplayable()
                || Screen.allControlsAndListeners.jDeleteButton.isDisplayable()
                || Screen.allControlsAndListeners.jSaveButton.isDisplayable()
                || Screen.allControlsAndListeners.duplicateLineDetectionByDistance.isDisplayable()
                ){
            return false;
        }
        return true;
    }
    
    public void repaint(BufferedImage orig, BufferedImage copy){
        Graphics2D graphics2D = copy.createGraphics();
        try {
            zoomAffineTransform = graphics2D.getTransform(); //clones current graphics2D transform
            zoomAffineTransform.invert(); //sets affine transorm to its own inverse, matrix inversion
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            graphics2D.scale(zoomScale, zoomScale); //scales xfrm
            zoomAffineTransform.concatenate(graphics2D.getTransform()); // zoom_affine_transform = inverse(initial graphics2D transform) * Current graphics2D transform
            graphics2D.drawImage(orig,0,0,null); //renders image
        } catch (NoninvertibleTransformException ex) {
        }

        
        graphics2D.setColor(Color.blue);
        if(copy.getWidth()==1340){
            System.out.println("drawing points : "+ linesObject.lines.size());
        }
    	for(int k = 0; k< linesObject.lines.size(); k++){
            graphics2D.setColor(currentColor);
            
            graphics2D.fillRect(linesObject.lines.get(k).getL(), linesObject.lines.get(k).getR(), 2,2);
    	}
    	for(int k = 0; k< linesObject.lines.size()-1; k++){
            graphics2D.setColor(linesObject.colorArray.get(k));
            if(linesObject.lineIndices.contains(k)){
                graphics2D.setStroke(new BasicStroke(3.0f));
                graphics2D.drawLine(linesObject.lines.get(k).getL(), linesObject.lines.get(k).getR(), linesObject.lines.get(k+1).getL(), linesObject.lines.get(k+1).getR());
                graphics2D.setStroke(new BasicStroke(1.0f));
            }
            else{
                graphics2D.drawLine(linesObject.lines.get(k).getL(), linesObject.lines.get(k).getR(), linesObject.lines.get(k+1).getL(), linesObject.lines.get(k+1).getR());
            }
            graphics2D.setColor(currentColor);
            k++;
    	}
        if(polygonObject.points !=null){
            graphics2D.setColor(currentColor);
            for(int h = 0; h< polygonObject.points.size(); h++){
                graphics2D.fillRect(polygonObject.points.get(h).getL(), polygonObject.points.get(h).getR(), 2,2);
            }
            for(int k = 0; k< polygonObject.points.size()-1; k++){
                graphics2D.drawLine(polygonObject.points.get(k).getL(), polygonObject.points.get(k).getR(), polygonObject.points.get(k+1).getL(), polygonObject.points.get(k+1).getR());
            }
            
        }
        
        if(regionsObject.regionPoints.size()>0){
            graphics2D.setColor(currentColor);
            for(int h = 0; h< regionsObject.regionPoints.size(); h++){
                graphics2D.fillRect(regionsObject.regionPoints.get(h).getL(), regionsObject.regionPoints.get(h).getR(), 2,2);
            }
            for(int k = 0; k< regionsObject.regionPoints.size()-1; k++){
                graphics2D.drawLine(regionsObject.regionPoints.get(k).getL(), regionsObject.regionPoints.get(k).getR(), regionsObject.regionPoints.get(k+1).getL(), regionsObject.regionPoints.get(k+1).getR());
            }
        }
        
    	for(int k = 0; k< polygonObject.polygons.size(); k++){
            graphics2D.setColor(polygonObject.colorArray.get(k));
            if(polygonObject.polygonIndices.contains(k)){
                graphics2D.setStroke(new BasicStroke(3.0f));
                for(int h = 0; h< polygonObject.polygons.get(k).size()-1; h++){
                    graphics2D.drawLine(polygonObject.polygons.get(k).get(h).getL(), polygonObject.polygons.get(k).get(h).getR(), polygonObject.polygons.get(k).get(h+1).getL(), polygonObject.polygons.get(k).get(h+1).getR());
                }
                graphics2D.setStroke(new BasicStroke(1.0f));
            }
            else{
                for(int h = 0; h< polygonObject.polygons.get(k).size()-1; h++){
                    graphics2D.drawLine(polygonObject.polygons.get(k).get(h).getL(), polygonObject.polygons.get(k).get(h).getR(), polygonObject.polygons.get(k).get(h+1).getL(), polygonObject.polygons.get(k).get(h+1).getR());
                }
            }
            if(polygonObject.fillOrNot.get(k)==1){
                Polygon polygon = new Polygon();
                for(int h = 0; h< polygonObject.polygons.get(k).size()-1; h++){
                    polygon.addPoint(polygonObject.polygons.get(k).get(h).getL(), polygonObject.polygons.get(k).get(h).getR());
                }
                graphics2D.fillPolygon(polygon);
            }
            graphics2D.setColor(currentColor);
    	}
        
    	for(int k = 0; k< regionsObject.regions.size(); k++){
            graphics2D.setColor(regionsObject.colorArray.get(k));
            if(regionsObject.regionIndices.contains(k)){
                graphics2D.setStroke(new BasicStroke(3.0f));
                for(int h = 0; h< regionsObject.regions.get(k).size()-1; h++){
                    graphics2D.drawLine(regionsObject.regions.get(k).get(h).getL(), regionsObject.regions.get(k).get(h).getR(), regionsObject.regions.get(k).get(h+1).getL(), regionsObject.regions.get(k).get(h+1).getR());
                }
                graphics2D.setStroke(new BasicStroke(1.0f));
            }
            else{
                for(int h = 0; h< regionsObject.regions.get(k).size()-1; h++){
                    graphics2D.drawLine(regionsObject.regions.get(k).get(h).getL(), regionsObject.regions.get(k).get(h).getR(), regionsObject.regions.get(k).get(h+1).getL(), regionsObject.regions.get(k).get(h+1).getR());
                }
            }
            if(regionsObject.fillArray.get(k)==1){
                Polygon polygon = new Polygon();
                for(int h = 0; h< regionsObject.regions.get(k).size()-1; h++){
                    polygon.addPoint(regionsObject.regions.get(k).get(h).getL(), regionsObject.regions.get(k).get(h).getR());
                }
                graphics2D.fillPolygon(polygon);
            }
            graphics2D.setColor(currentColor);
    	}        
    	
    	for(int k = 0; k< circlesObject.circles.size(); k++){
            graphics2D.setColor(currentColor);
            int x = (int)(float) circlesObject.circles.get(k).getL();
            int y = (int)(float) circlesObject.circles.get(k).getR();
            graphics2D.fillRect(x, y,2,2);
    	}
        
        
        for(int k = 0; k< arcObject.circles.size(); k++){
            graphics2D.setColor(currentColor);
            int x = (int)(float) arcObject.circles.get(k).getL();
            int y = (int)(float) arcObject.circles.get(k).getR();
            graphics2D.fillRect(x, y,2,2);
    	}

    	for(int k = 0; k< circlesObject.centers.size(); k++){
            int x = (int)(float) circlesObject.centers.get(k).getL();
            int y = (int)(float) circlesObject.centers.get(k).getR();
            int radius = (int)(float) circlesObject.radii.get(k);
            graphics2D.setColor(circlesObject.colorArray.get(k));
            if(circlesObject.circleIndices.contains(k)){
                graphics2D.setStroke(new BasicStroke(3.0f));
                graphics2D.drawArc(x - radius, y - radius,2* radius,2* radius, 0, 360);
                graphics2D.setStroke(new BasicStroke(1.0f));
            }
            else{
                graphics2D.drawArc(x - radius, y - radius,2* radius,2* radius, 0, 360);
            }
            //System.out.println(graphics2D.getColor());
            if(circlesObject.fillArray.get(k)==1){
                graphics2D.fillArc(x - radius, y - radius,2* radius,2* radius, 0, 360);
            }
            graphics2D.setColor(currentColor);
    	}
        
        for(int k = 0; k< arcObject.centers.size(); k++){
            int x = (int)(float) arcObject.centers.get(k).getL();
            int y = (int)(float) arcObject.centers.get(k).getR();
            int radius = (int)(float) arcObject.radii.get(k);
            int u = arcObject.arcAngles.get(k).getL();
            int v = arcObject.arcAngles.get(k).getR();
            graphics2D.setColor(arcObject.colorArray.get(k));
            if(arcObject.circleIndices.contains(k)){
               graphics2D.setStroke(new BasicStroke(3.0f));
               graphics2D.drawArc(x - radius, y - radius,2* radius,2* radius, u, v);
               graphics2D.setStroke(new BasicStroke(1.0f));
            }
            else{
                graphics2D.drawArc(x - radius, y - radius,2* radius,2* radius, u, v);
            }
            if(arcObject.fillArray.get(k)==1){
                graphics2D.fillArc(x - radius, y - radius,2* radius,2* radius, u, v);
            }
            graphics2D.setColor(currentColor);
    	}
        
        if(pathsObject.regionPoints.size()>0){
            graphics2D.setColor(currentColor);
            for(int h = 0; h< pathsObject.regionPoints.size(); h++){
                graphics2D.fillRect(pathsObject.regionPoints.get(h).getL(), pathsObject.regionPoints.get(h).getR(), 2,2);
            }
            for(int k = 0; k< pathsObject.regionPoints.size()-1; k++){
                graphics2D.drawLine(pathsObject.regionPoints.get(k).getL(), pathsObject.regionPoints.get(k).getR(), pathsObject.regionPoints.get(k+1).getL(), pathsObject.regionPoints.get(k+1).getR());
            }
        }
        
    	for(int k = 0; k< pathsObject.regions.size(); k++){
            graphics2D.setColor(pathsObject.colorArray.get(k));
            if(pathsObject.regionIndices.contains(k)){
                graphics2D.setStroke(new BasicStroke(3.0f));
                for(int h = 0; h< pathsObject.regions.get(k).size()-2; h++){
                    graphics2D.drawLine(pathsObject.regions.get(k).get(h).getL(), pathsObject.regions.get(k).get(h).getR(), pathsObject.regions.get(k).get(h+1).getL(), pathsObject.regions.get(k).get(h+1).getR());
                }
                graphics2D.setStroke(new BasicStroke(1.0f));
            }
            else{
                for(int h = 0; h< pathsObject.regions.get(k).size()-2; h++){
                    graphics2D.drawLine(pathsObject.regions.get(k).get(h).getL(), pathsObject.regions.get(k).get(h).getR(), pathsObject.regions.get(k).get(h+1).getL(), pathsObject.regions.get(k).get(h+1).getR());
                }	    
            }
            graphics2D.setColor(currentColor);
    	}
        for(int k = 0; k< textboxObject.rectangleArray.size(); k++){
            graphics2D.setColor(Color.black);
            AttributedString attributedString = new AttributedString(textboxObject.label.get(k).getR());
            
            switch (textboxObject.languageArray.get(k)) {
                case "hin":
                    graphics2D.setFont(Screen.allControlsAndListeners.hindiFont);
                    attributedString.addAttribute(TextAttribute.FONT, Screen.allControlsAndListeners.hindiFont);
                    break;
                case "eng":
                    graphics2D.setFont(Screen.allControlsAndListeners.englishFont);
                    attributedString.addAttribute(TextAttribute.FONT, Screen.allControlsAndListeners.englishFont);
                    break;
                case "ben":
                    graphics2D.setFont(Screen.allControlsAndListeners.bengaliFont);
                    attributedString.addAttribute(TextAttribute.FONT, Screen.allControlsAndListeners.bengaliFont);
                    break;
                default:
                    break;
            }
            if(textboxObject.rectangleIndices.contains(k)){
    		if(!textboxObject.label.get(k).getR().contains("label -")){
                    if(textShiftRequired()){
                        graphics2D.drawString(attributedString.getIterator(), textboxObject.rectangleArray.get(k).x, textboxObject.rectangleArray.get(k).y-4);
                    }
                    else{
                        graphics2D.drawString(attributedString.getIterator(),
                                textboxObject.rectangleArray.get(k).x,
                                textboxObject.rectangleArray.get(k).y+ textboxObject.rectangleArray.get(k).height);
                    }
                }
                
                graphics2D.setStroke(new BasicStroke(3.0f));
                if(rectangleDrawRequired()){
                    graphics2D.drawRect(textboxObject.rectangleArray.get(k).x, textboxObject.rectangleArray.get(k).y,
                            textboxObject.rectangleArray.get(k).width, textboxObject.rectangleArray.get(k).height);
                }
                graphics2D.setStroke(new BasicStroke(1.0f));
            }
            else{
                if(!textboxObject.label.get(k).getR().contains("label -")){
                    if(textShiftRequired()){
                        graphics2D.drawString(attributedString.getIterator(), textboxObject.rectangleArray.get(k).x, textboxObject.rectangleArray.get(k).y-4);
                    }
                    else{
                        graphics2D.drawString(attributedString.getIterator(),
                                textboxObject.rectangleArray.get(k).x,
                                textboxObject.rectangleArray.get(k).y+ textboxObject.rectangleArray.get(k).height);
                    }
                }
                if(rectangleDrawRequired()){
                    graphics2D.drawRect(textboxObject.rectangleArray.get(k).x, textboxObject.rectangleArray.get(k).y, textboxObject.rectangleArray.get(k).width, textboxObject.rectangleArray.get(k).height);
                }
            }
            if(modifyTextObject.selectedRectangle ==k){
                graphics2D.setColor(Color.RED);
                if(!textboxObject.label.get(k).getR().contains("label -")){
                    if(textShiftRequired()){
                        graphics2D.drawString(attributedString.getIterator(), textboxObject.rectangleArray.get(k).x, textboxObject.rectangleArray.get(k).y-4);
                    }
                    else{
                        graphics2D.drawString(attributedString.getIterator(),
                                textboxObject.rectangleArray.get(k).x,
                                textboxObject.rectangleArray.get(k).y+ textboxObject.rectangleArray.get(k).height);
                    }
                }
                if(rectangleDrawRequired()){
                    graphics2D.drawRect(textboxObject.rectangleArray.get(k).x+2, textboxObject.rectangleArray.get(k).y+2, textboxObject.rectangleArray.get(k).width-4, textboxObject.rectangleArray.get(k).height-4);
                }
                graphics2D.setColor(currentColor);
            }
            graphics2D.setColor(currentColor);
        }
        
    	if(textboxObject.captureRectangle != null){
            graphics2D.setColor(Color.black);
            graphics2D.drawRect(textboxObject.captureRectangle.x, textboxObject.captureRectangle.y, textboxObject.captureRectangle.width, textboxObject.captureRectangle.height);
            graphics2D.setColor(currentColor);
        }
    	graphics2D.dispose();
    }
    
    public void fillConfig() throws FileNotFoundException, IOException{
        File file = new File(System.getProperty("user.dir")+"\\resources\\config");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line = bufferedReader.readLine();
            while(line!=null){
                String[] arr= line.split("##");
                config.put(arr[0], System.getProperty("user.dir")+arr[1]);
                line = bufferedReader.readLine();
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        Screen screen= new Screen(0);
        config = new Hashtable<>();
        screen.fillConfig();
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File(config.get("error_log")),false));
        printWriter.write("TacGen started\n");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            printWriter.write("Frame Style(Look and Feel) could not be set to windows classic\n");
        }
        
        mainFrame = new JFrame("TacGen : Tool For Interactive Tactile Generation ");
        previewFrame = new JFrame("Preview : See the progress so Far ");
        
        mainFrame.setIconImage(ImageIO.read(new File(Screen.config.get("Frame_Icon"))));
    	mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        mainFrame.setSize(800,600);
        mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        allControlsAndListeners = new AllControlsAndListeners();
    	initialFrameSetup = new InitialFrameSetup();
    	linesObject = new GetLines();
    	circlesObject = new GetCircles();
    	regionsObject = new GetRegions();
    	textboxObject = new GetTextbox();
    	polygonObject = new GetPolygon();
        arcObject = new GetArc();
        modifyTextObject = new ModifyText();
        whichPolygonObject = new whichPolygon();
        pathsObject = new GetPaths();
        textExeObject = new text_exe();
        maths_science_exe = new maths_science_exe();
        svgGenerateObject = new GenerateSVG();
        imageAreaListeners = new ImageAreaListeners();
        allObjectReinitializer = new AllObjectReinitializer();
        restoreSVG = new RestoreSVG();
        colorObject = new color();
        page0OpenImage =new Page0OpenImage();

    	SwingUtilities.invokeLater(() -> {
            try {
                Screen main = new Screen();
            } catch (IOException ex) {
                printWriter.write("Invoke later method shows printWriter\n");
            }
            mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            mainFrame.setVisible(true);
        });
        printWriter.close();
    }
}		