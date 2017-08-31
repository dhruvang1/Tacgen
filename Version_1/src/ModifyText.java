
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;

public class ModifyText {
    
    public int selectedRectangle =10000;
    public void modifySelectedRectangle(MouseEvent e) throws NoninvertibleTransformException{
        boolean clickedOutside = true;
        for(int i = 0; i< Screen.textboxObject.rectangleArray.size(); i++){
            if(Screen.textboxObject.rectangleArray.get(i).contains(Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e))){
                clickedOutside = false;
                if(selectedRectangle !=i){
                    selectedRectangle =i;
                }
            }
        }
        if(clickedOutside){
            selectedRectangle =10000;
            Screen.allControlsAndListeners.jLabel.setEditable(false);
            Screen.allControlsAndListeners.jSavePage2.setEnabled(false);
            Screen.allControlsAndListeners.jDeletePage2.setEnabled(false);
            Screen.allControlsAndListeners.jLabel.setEnabled(false);
        }
    }
}