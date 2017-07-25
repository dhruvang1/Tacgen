
public class refresh_all {
    public void refresh(){
        SCR.label_counts = 0;
        SCR.line_object = new get_lines();
    	SCR.circle_object = new get_circles();
    	SCR.region_object = new get_regions();
    	SCR.textbox_object = new get_textbox();
    	SCR.polygon_object = new get_polygon();
        SCR.arc_object = new get_arc();
        SCR.a9 = new modify_text();
        SCR.a16 = new get_paths();
        SCR.a10 = new which_polygon();
//        SCR.text_exe = new load_labels();
//        SCR.maths_science_exe = new load_lines_circles_regions();
//        SCR.a13 = new svg_generate();
        SCR.image_area_listeners = new Image_Area_Listeners();
    }
}
