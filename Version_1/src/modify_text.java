
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;

public class modify_text {
    
    int selected_rect=10000;
    public void modify_selected_rect(MouseEvent e) throws NoninvertibleTransformException{
        boolean outside_click = true;
        for(int i=0;i<SCR.textbox_object.Rect_array.size();i++){
            if(SCR.textbox_object.Rect_array.get(i).contains(SCR.a1.get_original_point_coordinate(e))){
                outside_click = false;
                if(selected_rect==i){
                    //selected_rect=10000;
                    //SCR.a1.Label.setEditable(false);
                    //SCR.a1.save_page2.setEnabled(false);
                    //SCR.a1.delete_page2.setEnabled(false);
                    //SCR.a1.Label.setEnabled(false);
                }
                else{
                    selected_rect=i;
                }
            }
        }
        if(outside_click){
            selected_rect=10000;
            SCR.a1.Label.setEditable(false);
            SCR.a1.save_page2.setEnabled(false);
            SCR.a1.delete_page2.setEnabled(false);
            SCR.a1.Label.setEnabled(false);
        }
    }
}