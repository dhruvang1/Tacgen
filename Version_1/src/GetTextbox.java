import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
//import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

public class GetTextbox {
    int currentX, currentY;
    int r = 0;
    Screen screen = new Screen(r);
    Rectangle captureRectangle;
    ArrayList<Rectangle> rectangleArray = new ArrayList<>();
    ArrayList<String> languageArray = new ArrayList<>();
    ArrayList<Integer> rectangleIndices = new ArrayList<>();
    ArrayList<Pair<String, String>> label = new ArrayList<>();

    public void translateText(MouseEvent e) throws NoninvertibleTransformException {
        int w = Screen.textbox_object.rectangleArray.get(Screen.a9.selected_rect).width;
        int h = Screen.textbox_object.rectangleArray.get(Screen.a9.selected_rect).height;
        if (new Rectangle(Screen.rectangle_translate_start, new Dimension(w, h)).contains(Screen.start)) {
            Point p = Screen.a1.getOriginalZoomedCoordinate(e);
            currentX = p.x;
            currentY = p.y;
            w = currentX - Screen.start.x;
            h = currentY - Screen.start.y;
            int oldX = Screen.rectangle_translate_start.x;
            int oldY = Screen.rectangle_translate_start.y;
            rectangleArray.get(Screen.a9.selected_rect).setLocation(oldX + w, oldY + h);
            screen.repaint(Screen.screen, Screen.a2.screenCopy);
            Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);
        }
    }

    public void selectText(MouseEvent e) throws NoninvertibleTransformException {
        Point p = Screen.a1.getOriginalZoomedCoordinate(e);
        currentX = p.x;
        currentY = p.y;
        int w = currentX - Screen.start.x;
        int h = currentY - Screen.start.y;
        captureRectangle = new Rectangle(Screen.start.x, Screen.start.y, w, h);
        screen.repaint(Screen.screen, Screen.a2.screenCopy);
        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
    }

    public void updateCaptureRectangle(MouseEvent e) throws NoninvertibleTransformException {
        Point p = Screen.a1.getOriginalZoomedCoordinate(e);
        currentX = p.x;
        currentY = p.y;
        int w = currentX - Screen.start.x;
        int h = currentY - Screen.start.y;
        if (w > 5 && h > 5) {
            Rectangle tempRectangle = new Rectangle(Screen.start.x, Screen.start.y, w, h);
            rectangleArray.add(tempRectangle);
            languageArray.add(Screen.text_exe.language);
            Screen.label_counts++;
            String h1 = "";
            String t1 = "";
            Pair<String, String> temp_pair = new Pair<String, String>(h1, t1);
            temp_pair.setL(String.valueOf(Screen.label_counts));
            temp_pair.setR("label - " + Screen.label_counts);
            label.add(temp_pair);
            Screen.a9.selected_rect = rectangleArray.size() - 1;
            Screen.a1.jLabel.setEditable(true);
            Screen.a1.jSavePage2.setEnabled(true);
            Screen.a1.jDeletePage2.setEnabled(true);
            Screen.a1.jLabel.setEnabled(true);
            Screen.a1.jLabel.requestFocus(true);

//            Screen.a1.Label.setFocusable(true);
            if (Screen.textbox_object.label.get(Screen.a9.selected_rect).getR().contains("label -")) {
                Screen.a1.jLabel.setText("");
//                Screen.a1.Label.requestFocusInWindow();
            } else {
                Screen.a1.jLabel.setText(Screen.textbox_object.label.get(Screen.a9.selected_rect).getR());
            }
        }
        captureRectangle = null;
    }

    public void addIndices(MouseEvent e) throws NoninvertibleTransformException {
        for (int i = 0; i < rectangleArray.size(); i++) {
            if (rectangleArray.get(i).contains(Screen.a1.getOriginalZoomedCoordinate(e))) {
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
        if (Screen.a9.selected_rect != 10000) {
            rectangleArray.remove(Screen.a9.selected_rect);
            label.remove(Screen.a9.selected_rect);
            languageArray.remove(Screen.a9.selected_rect);
            Screen.a1.jLabel.setEditable(false);
            Screen.a1.jSavePage2.setEnabled(false);
            Screen.a1.jDeletePage2.setEnabled(false);
            Screen.a1.jLabel.setEnabled(false);

            Screen.a1.jLabel.setText("");
            Screen.a9.selected_rect = 10000;
        }
    }
}
