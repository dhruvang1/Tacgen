import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
//import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;


public class get_textbox {
    int curX,curY ;
    int r =0;
    Screen t1 = new Screen(r);
    Rectangle captureRect;
    ArrayList<Rectangle> Rect_array=new ArrayList<Rectangle>();
    ArrayList<String> Language_array= new ArrayList<String>();
    ArrayList<Integer> rect_indices=new ArrayList<Integer>();
    ArrayList<Pair<String,String>> label=new ArrayList<Pair<String,String>>();
    
//    ArrayList<String> label=new ArrayList<String>();
    
    public get_textbox() {
            // TODO Auto-generated constructor stub
    }
    public void translate_text(MouseEvent me) throws NoninvertibleTransformException{
        int w= Screen.textbox_object.Rect_array.get(Screen.a9.selected_rect).width;
        int h = Screen.textbox_object.Rect_array.get(Screen.a9.selected_rect).height;
        if(new Rectangle(Screen.rectangle_translate_start, new Dimension(w,h)).contains(Screen.start)){
            Point p = Screen.a1.getOriginalZoomedCoordinate(me);
            curX = p.x;
            curY = p.y;
            w=curX- Screen.start.x;h=curY- Screen.start.y;
            int old_x = Screen.rectangle_translate_start.x;
            int old_y = Screen.rectangle_translate_start.y;
            Rect_array.get(Screen.a9.selected_rect).setLocation(old_x+w,old_y+h);
            t1.repaint(Screen.screen, Screen.a2.screenCopy);
            Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);
        }
    }
    public void select_text(MouseEvent me) throws NoninvertibleTransformException{
        Point p = Screen.a1.getOriginalZoomedCoordinate(me);
        curX = p.x;
        curY = p.y;
        int w=curX- Screen.start.x;int h=curY- Screen.start.y;
        captureRect =new Rectangle(Screen.start.x, Screen.start.y,w,h);
        t1.repaint(Screen.screen, Screen.a2.screenCopy);
        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
    }
    public void update_capture_rect(MouseEvent me) throws NoninvertibleTransformException{
        Point p = Screen.a1.getOriginalZoomedCoordinate(me);
        curX = p.x;
        curY = p.y;
        int w=curX- Screen.start.x;int h=curY- Screen.start.y;
        if(w>5&&h>5){
            Rectangle tempRect =new Rectangle(Screen.start.x, Screen.start.y,w,h);
            Rect_array.add(tempRect);
            Language_array.add(Screen.text_exe.language);
            Screen.label_counts++;
            String h1 = "";String t1 = "";
            Pair<String,String> temp_pair = new Pair <String,String> (h1,t1);
            temp_pair.setL(String.valueOf(Screen.label_counts));
            temp_pair.setR("label - "+ Screen.label_counts);
            label.add(temp_pair);
            Screen.a9.selected_rect = Rect_array.size()-1;
            Screen.a1.jLabel.setEditable(true);
            Screen.a1.jSavePage2.setEnabled(true);
            Screen.a1.jDeletePage2.setEnabled(true);
            Screen.a1.jLabel.setEnabled(true);
            Screen.a1.jLabel.requestFocus(true);
            
//            Screen.a1.Label.setFocusable(true);
            if(Screen.textbox_object.label.get(Screen.a9.selected_rect).getR().contains("label -")){
                Screen.a1.jLabel.setText("");
//                Screen.a1.Label.requestFocusInWindow();
            }
            else{
                Screen.a1.jLabel.setText(Screen.textbox_object.label.get(Screen.a9.selected_rect).getR());
            }
        }
        captureRect = null;
    }
    
    public void add_indices(MouseEvent e) throws NoninvertibleTransformException{
        for(int i=0;i<Rect_array.size();i++){
            if(Rect_array.get(i).contains(Screen.a1.getOriginalZoomedCoordinate(e))){
                if(rect_indices.contains(i)){
                    rect_indices.remove((Integer)i );
                }
                else{
                    rect_indices.add(i);
                }
            }
        }
    }
    public void delete_indices(){
        if(Screen.a9.selected_rect!=10000){
            Rect_array.remove(Screen.a9.selected_rect);
            label.remove(Screen.a9.selected_rect);
            Language_array.remove(Screen.a9.selected_rect);
            Screen.a1.jLabel.setEditable(false);
            Screen.a1.jSavePage2.setEnabled(false);
            Screen.a1.jDeletePage2.setEnabled(false);
            Screen.a1.jLabel.setEnabled(false);
            
            Screen.a1.jLabel.setText("");
            Screen.a9.selected_rect=10000;
        }
    }
}
