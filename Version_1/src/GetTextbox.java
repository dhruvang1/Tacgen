import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
//import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

/**
 * Class to make editable text boxes during text extraction
 */
public class GetTextbox {
    private int currentX, currentY;
    private Screen screen = new Screen(0);
    public Rectangle captureRectangle;
    public ArrayList<Rectangle> rectangleArray = new ArrayList<>();
    public ArrayList<String> languageArray = new ArrayList<>();
    public ArrayList<Integer> rectangleIndices = new ArrayList<>();
    public ArrayList<Pair<String, String>> label = new ArrayList<>();

    public void translateText(MouseEvent e) throws NoninvertibleTransformException {
        int w = Screen.textboxObject.rectangleArray.get(Screen.modifyTextObject.selectedRectangle).width;
        int h = Screen.textboxObject.rectangleArray.get(Screen.modifyTextObject.selectedRectangle).height;
        if (new Rectangle(Screen.rectangleTranslateStart, new Dimension(w, h)).contains(Screen.start)) {
            Point p = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
            currentX = p.x;
            currentY = p.y;
            w = currentX - Screen.start.x;
            h = currentY - Screen.start.y;
            int oldX = Screen.rectangleTranslateStart.x;
            int oldY = Screen.rectangleTranslateStart.y;
            rectangleArray.get(Screen.modifyTextObject.selectedRectangle).setLocation(oldX + w, oldY + h);
            screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
            Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
        }
    }

    public void selectText(MouseEvent e) throws NoninvertibleTransformException {
        Point p = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
        currentX = p.x;
        currentY = p.y;
        int w = currentX - Screen.start.x;
        int h = currentY - Screen.start.y;
        captureRectangle = new Rectangle(Screen.start.x, Screen.start.y, w, h);
        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
    }

    public void updateCaptureRectangle(MouseEvent e) throws NoninvertibleTransformException {
        Point p = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
        currentX = p.x;
        currentY = p.y;
        int w = currentX - Screen.start.x;
        int h = currentY - Screen.start.y;
        if (w > 5 && h > 5) {
            Rectangle tempRectangle = new Rectangle(Screen.start.x, Screen.start.y, w, h);
            rectangleArray.add(tempRectangle);
            languageArray.add(Screen.textExeObject.language);
            Screen.label_counts++;
            String h1 = "";
            String t1 = "";
            Pair<String, String> temp_pair = new Pair<String, String>(h1, t1);
            temp_pair.setL(String.valueOf(Screen.label_counts));
            temp_pair.setR("label - " + Screen.label_counts);
            label.add(temp_pair);
            Screen.modifyTextObject.selectedRectangle = rectangleArray.size() - 1;
            Screen.allControlsAndListeners.jLabel.setEditable(true);
            Screen.allControlsAndListeners.jSavePage2.setEnabled(true);
            Screen.allControlsAndListeners.jDeletePage2.setEnabled(true);
            Screen.allControlsAndListeners.jLabel.setEnabled(true);
            Screen.allControlsAndListeners.jLabel.requestFocus(true);

//            Screen.a1.Label.setFocusable(true);
            if (Screen.textboxObject.label.get(Screen.modifyTextObject.selectedRectangle).getR().contains("label -")) {
                Screen.allControlsAndListeners.jLabel.setText("");
//                Screen.a1.Label.requestFocusInWindow();
            } else {
                Screen.allControlsAndListeners.jLabel.setText(Screen.textboxObject.label.get(Screen.modifyTextObject.selectedRectangle).getR());
            }
        }
        captureRectangle = null;
    }

    public void addIndices(MouseEvent e) throws NoninvertibleTransformException {
        for (int i = 0; i < rectangleArray.size(); i++) {
            if (rectangleArray.get(i).contains(Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e))) {
                if (rectangleIndices.contains(i)) {
                    rectangleIndices.remove((Integer) i);
                }
                else{
                    rectangleIndices.add(i);
                }
            }
        }
    }

    public void deleteIndices() {
        if (Screen.modifyTextObject.selectedRectangle != 10000) {
            rectangleArray.remove(Screen.modifyTextObject.selectedRectangle);
            label.remove(Screen.modifyTextObject.selectedRectangle);
            languageArray.remove(Screen.modifyTextObject.selectedRectangle);
            Screen.allControlsAndListeners.jLabel.setEditable(false);
            Screen.allControlsAndListeners.jSavePage2.setEnabled(false);
            Screen.allControlsAndListeners.jDeletePage2.setEnabled(false);
            Screen.allControlsAndListeners.jLabel.setEnabled(false);

            Screen.allControlsAndListeners.jLabel.setText("");
            Screen.modifyTextObject.selectedRectangle = 10000;
        }
    }
}
