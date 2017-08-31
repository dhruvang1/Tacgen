import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class to restore edited images from SVG files
 */
public class RestoreSVG {
    private Screen screen = new Screen(0);
    public void restore() throws SAXException, ParserConfigurationException, IOException, XPathExpressionException, ScriptException, NoSuchMethodException{
        String currentFileAbsolutePath = screen.currentFile.getAbsolutePath();
        String currentFileName = String.valueOf(screen.currentFile.getName());
        String newFileName = currentFileName+".html";
        int index=currentFileAbsolutePath.lastIndexOf("\\");
        String newFileAbsolutePath =currentFileAbsolutePath.substring(0, index+1)+ newFileName;
        File file = new File(newFileAbsolutePath);
        if(file.exists()){
            lineRestore(newFileAbsolutePath);
            circleRestore(newFileAbsolutePath);
            arcRestore(newFileAbsolutePath);
            polygonRestore(newFileAbsolutePath);
            polypathRestore(newFileAbsolutePath);
            textRestore(newFileAbsolutePath);
        }
    }

    private void lineRestore(String name) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document document = builder.parse(name);
        String xpathExpression = "//line/@*";
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expression = xpath.compile(xpathExpression);
        NodeList svgPaths = (NodeList)expression.evaluate(document, XPathConstants.NODESET);
        int x1,y1,x2,y2;
        String color;
        for(int i=0;i<svgPaths.getLength();i++){
            x1 = Integer.valueOf(svgPaths.item(i+1).getNodeValue());
            y1 = Integer.valueOf(svgPaths.item(i+3).getNodeValue());
            x2 = Integer.valueOf(svgPaths.item(i+2).getNodeValue());
            y2 = Integer.valueOf(svgPaths.item(i+4).getNodeValue());
            color = svgPaths.item(i).getNodeValue().substring(1);
            i = i+4;
            Screen.linesObject.lines.add(new Pair<>(x1,y1));
            Screen.linesObject.colorArray.add(new Color(Integer.decode("0x"+color)));
            Screen.linesObject.colorArray.add(new Color(Integer.decode("0x"+color)));
            Screen.linesObject.lines.add(new Pair<>(x2,y2));
        }
    }

    private void circleRestore(String name) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document document = builder.parse(name);
        String xpathExpression = "//circle/@*";
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expression = xpath.compile(xpathExpression);
        NodeList svgPaths = (NodeList)expression.evaluate(document, XPathConstants.NODESET);
        float cx,cy,r;
        String col;
        String stroke_col;
        for(int i=0;i<svgPaths.getLength();i++){
            cx = Float.valueOf(svgPaths.item(i).getNodeValue());
            cy = Float.valueOf(svgPaths.item(i+1).getNodeValue());
            r = Float.valueOf(svgPaths.item(i+3).getNodeValue());
            col = svgPaths.item(i+2).getNodeValue().substring(1);
            stroke_col = svgPaths.item(i+4).getNodeValue().substring(1);
            i = i+4;
            if(col.contentEquals("one")){
                Screen.circlesObject.fillArray.add(0);
                Screen.circlesObject.colorArray.add(new Color(Integer.decode("0x"+stroke_col)));
            }
            else{
                Screen.circlesObject.fillArray.add(1);
                Screen.circlesObject.colorArray.add(new Color(Integer.decode("0x"+col)));
            }
            Screen.circlesObject.centers.add(new Pair<>(cx,cy));
            Screen.circlesObject.radii.add(r);
        }   
    }
    
    private void arcRestore(String name) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document document = builder.parse(name);
        String xpathExpression = "//path/@d";
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expression = xpath.compile(xpathExpression);
        NodeList svgPaths = (NodeList)expression.evaluate(document, XPathConstants.NODESET);
        
        String xpathExpression_1 = "//path/@*";
        XPathFactory xpf_1 = XPathFactory.newInstance();
        XPath xpath_1 = xpf_1.newXPath();
        XPathExpression expression_1= xpath_1.compile(xpathExpression_1);
        NodeList svgPaths_1 = (NodeList)expression_1.evaluate(document, XPathConstants.NODESET);
        Circle c = new Circle();
        for(int i=0;i<svgPaths.getLength();i++){
            String [] p = svgPaths.item(i).getNodeValue().split(",|\\ ");
            float [] ret = c.potentialCenters(Float.valueOf(p[1]),Float.valueOf(p[2]),
                    Float.valueOf(p[13]), Float.valueOf(p[14]), Float.valueOf(p[4]), Integer.valueOf(p[9]));
            
            Pair<Float,Float> center = new Pair<Float,Float>(ret[0], ret[1]);
            Pair<Integer,Integer> temp_angle = new Pair<Integer,Integer>((int)ret[2],(int)ret[3]);
            Screen.arcObject.centers.add(center);
            Screen.arcObject.radii.add(Float.valueOf(p[4]));
            Screen.arcObject.arcAngles.add(temp_angle);
            String col = svgPaths_1.item(3*i+1).getNodeValue().substring(1);
            String stroke_col = svgPaths_1.item(3*i+2).getNodeValue().substring(1);
            if(col.contentEquals("one")){
                Screen.arcObject.fillArray.add(0);
                Screen.arcObject.colorArray.add(new Color(Integer.decode("0x"+stroke_col)));
            }
            else{
                Screen.arcObject.fillArray.add(1);
                Screen.arcObject.colorArray.add(new Color(Integer.decode("0x"+col)));
            }
            
        }
    }
    
    private void polygonRestore(String name) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document document = builder.parse(name);
        String xpathExpression = "//polygon/@points";
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expression = xpath.compile(xpathExpression);
        NodeList svgPaths = (NodeList)expression.evaluate(document, XPathConstants.NODESET);
        
        String xpathExpression_1 = "//polygon/@*";
        XPathFactory xpf_1 = XPathFactory.newInstance();
        XPath xpath_1 = xpf_1.newXPath();
        XPathExpression expression_1= xpath_1.compile(xpathExpression_1);
        NodeList svgPaths_1 = (NodeList)expression_1.evaluate(document, XPathConstants.NODESET);
        
        for(int i=0;i<svgPaths.getLength();i++){
            String [] p = svgPaths.item(i).getNodeValue().split(",|\\ ");
            ArrayList<Pair<Integer,Integer>> Points_regions=new ArrayList<Pair<Integer,Integer>>();
            for(int j =0;j<p.length-1;j++){
                Points_regions.add(new Pair<>(Integer.valueOf(p[j]),Integer.valueOf(p[j+1])));
                j++;
            }
            Points_regions.add(new Pair<>(Integer.valueOf(p[0]),Integer.valueOf(p[1])));
            String col = svgPaths_1.item(3*i).getNodeValue().substring(1);
            String stroke_col = svgPaths_1.item(3*i+2).getNodeValue().substring(1);
            if(col.contentEquals("one")){
                Screen.regionsObject.fillArray.add(0);
                Screen.regionsObject.colorArray.add(new Color(Integer.decode("0x"+stroke_col)));
            }
            else{
                Screen.regionsObject.fillArray.add(1);
                Screen.regionsObject.colorArray.add(new Color(Integer.decode("0x"+col)));
            }
            Screen.regionsObject.regions.add(Points_regions);
        }
    }
    
    private void polypathRestore(String name) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document document = builder.parse(name);
        String xpathExpression = "//polyline/@points";
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expression = xpath.compile(xpathExpression);
        NodeList svgPaths = (NodeList)expression.evaluate(document, XPathConstants.NODESET);
        
        String xpathExpression_1 = "//polyline/@*";
        XPathFactory xpf_1 = XPathFactory.newInstance();
        XPath xpath_1 = xpf_1.newXPath();
        XPathExpression expression_1= xpath_1.compile(xpathExpression_1);
        NodeList svgPaths_1 = (NodeList)expression_1.evaluate(document, XPathConstants.NODESET);
        
        for(int i=0;i<svgPaths.getLength();i++){
            String [] p = svgPaths.item(i).getNodeValue().split(",|\\ ");
            ArrayList<Pair<Integer,Integer>> Points_regions=new ArrayList<Pair<Integer,Integer>>();
            for(int j =0;j<p.length-1;j++){
                Points_regions.add(new Pair<>(Integer.valueOf(p[j]),Integer.valueOf(p[j+1])));
                j++;
            }
            Points_regions.add(new Pair<>(Integer.valueOf(p[0]),Integer.valueOf(p[1])));
            
            String col = svgPaths_1.item(3*i+2).getNodeValue().substring(1);
            
            Screen.pathsObject.colorArray.add(new Color(Integer.decode("0x"+col)));
            Screen.pathsObject.paths.add(Points_regions);
        }
    }    
    
    private void textRestore(String name) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException, ScriptException, NoSuchMethodException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document document = builder.parse(name);
        String xpathExpression = "//rect/@*";
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expression = xpath.compile(xpathExpression);
        NodeList svgPaths = (NodeList)expression.evaluate(document, XPathConstants.NODESET);
        
        
        String xpathExpression1 = "//text/text()";
        XPathFactory xpf1 = XPathFactory.newInstance();
        XPath xpath1 = xpf1.newXPath();
        XPathExpression expression1 = xpath1.compile(xpathExpression1);
        //String h = (String) expression.evaluate(document, XPathConstants.STRING);
        NodeList svgPaths1 = (NodeList)expression1.evaluate(document, XPathConstants.NODESET);
        
        String xpathExpression2 = "//text/@*";
        XPathFactory xpf2 = XPathFactory.newInstance();
        XPath xpath2 = xpf2.newXPath();
        XPathExpression expression2 = xpath2.compile(xpathExpression2);
        //String h = (String) expression.evaluate(document, XPathConstants.STRING);
        NodeList svgPaths2 = (NodeList)expression2.evaluate(document, XPathConstants.NODESET);
        
        
        int x,y,w,h;
        for(int i=0;i<svgPaths.getLength();i++){
            x = Integer.valueOf(svgPaths.item(i+3).getNodeValue());
            y = Integer.valueOf(svgPaths.item(i+4).getNodeValue());
            h = Integer.valueOf(svgPaths.item(i).getNodeValue());
            w = Integer.valueOf(svgPaths.item(i+2).getNodeValue());
            i = i+4;
            Screen.label_counts++;
            String h1 = String.valueOf(Screen.label_counts);
            String h2 = "label - "+h1;
            if(svgPaths1.item(i/5).getNodeValue().length()>0){
                h2 = svgPaths1.item(i/5).getNodeValue();
            }
            Screen.textboxObject.rectangleArray.add(new Rectangle(x, y, w, h));
            if(svgPaths2.item((i/5)*3).getNodeValue().contains("Kruti Dev 010")){
                Screen.textboxObject.languageArray.add("hin");
                ScriptEngineManager factory1 = new ScriptEngineManager();
                ScriptEngine engine = factory1.getEngineByName("JavaScript");
                engine.eval(Files.newBufferedReader(Paths.get(Screen.config.get("hindi_helper")), StandardCharsets.UTF_8));
                Invocable inv = (Invocable) engine;
                h2 = (String) inv.invokeFunction("Convert_to_Kritidev_010", h2);
            }
            else if(svgPaths2.item((i/5)*3).getNodeValue().contains("bengali")){
                Screen.textboxObject.languageArray.add("ben");
                ScriptEngineManager factory1 = new ScriptEngineManager();
                ScriptEngine engine = factory1.getEngineByName("JavaScript");
                engine.eval(Files.newBufferedReader(Paths.get(Screen.config.get("bangla_helper")), StandardCharsets.UTF_8));
                engine.eval(Files.newBufferedReader(Paths.get(Screen.config.get("bangla_helper_main")), StandardCharsets.UTF_8));
                Invocable inv = (Invocable) engine;
                h2 = (String) inv.invokeFunction("ConvertToASCII", "bijoy",h2);
            }
            else{
                Screen.textboxObject.languageArray.add("eng");
            }
            
            Screen.textboxObject.label.add(new Pair<>(h1,h2));
        }
    }    
}
