
public class refresh_all {
    public void refresh(){
        Screen.label_counts = 0;
        Screen.line_object = new get_lines();
    	Screen.circle_object = new GetCircles();
    	Screen.region_object = new GetRegions();
    	Screen.textbox_object = new get_textbox();
    	Screen.polygon_object = new get_polygon();
        Screen.arc_object = new GetArc();
        Screen.a9 = new modify_text();
        Screen.a16 = new GetPaths();
        Screen.a10 = new which_polygon();
//        Screen.text_exe = new load_labels();
//        Screen.maths_science_exe = new load_lines_circles_regions();
//        Screen.a13 = new svg_generate();
        Screen.image_area_listeners = new Image_Area_Listeners();
    }
}
