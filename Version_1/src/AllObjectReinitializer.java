
public class AllObjectReinitializer {
    public void refresh(){
        Screen.label_counts = 0;
        Screen.linesObject = new GetLines();
    	Screen.circlesObject = new GetCircles();
    	Screen.regionsObject = new GetRegions();
    	Screen.textboxObject = new GetTextbox();
    	Screen.polygonObject = new GetPolygon();
        Screen.arcObject = new GetArc();
        Screen.modifyTextObject = new ModifyText();
        Screen.pathsObject = new GetPaths();
        Screen.whichPolygonObject = new whichPolygon();
        Screen.imageAreaListeners = new ImageAreaListeners();
    }
}
