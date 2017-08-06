
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;

public class modify_text {
    
    int selected_rect=10000;
    public void modify_selected_rect(MouseEvent e) throws NoninvertibleTransformException{
        boolean outside_click = true;
        for(int i = 0; i< Screen.textbox_object.Rect_array.size(); i++){
            if(Screen.textbox_object.Rect_array.get(i).contains(Screen.a1.getOriginalZoomedCoordinate(e))){
                outside_click = false;
                if(selected_rect==i){
                    //selected_rect=10000;
                    //Screen.a1.Label.setEditable(false);
                    //Screen.a1.save_page2.setEnabled(false);
                    //Screen.a1.delete_page2.setEnabled(false);
                    //Screen.a1.Label.setEnabled(false);
                }
                else{
                    selected_rect=i;
                }
            }
        }
        if(outside_click){
            selected_rect=10000;
            Screen.a1.jLabel.setEditable(false);
            Screen.a1.jSavePage2.setEnabled(false);
            Screen.a1.jDeletePage2.setEnabled(false);
            Screen.a1.jLabel.setEnabled(false);
        }
    }
}