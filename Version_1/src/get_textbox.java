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
    SCR t1 = new SCR(r);
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
        int w=SCR.textbox_object.Rect_array.get(SCR.a9.selected_rect).width;
        int h = SCR.textbox_object.Rect_array.get(SCR.a9.selected_rect).height;
        if(new Rectangle(SCR.rectangle_translate_start, new Dimension(w,h)).contains(SCR.start)){
            Point p = SCR.a1.get_original_point_coordinate(me);
            curX = p.x;
            curY = p.y;
            w=curX-SCR.start.x;h=curY-SCR.start.y;
            int old_x = SCR.rectangle_translate_start.x;
            int old_y = SCR.rectangle_translate_start.y;
            Rect_array.get(SCR.a9.selected_rect).setLocation(old_x+w,old_y+h);
            t1.repaint(SCR.screen, SCR.a2.screenCopy);
            SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);
        }
    }
    public void select_text(MouseEvent me) throws NoninvertibleTransformException{
        Point p = SCR.a1.get_original_point_coordinate(me);
        curX = p.x;
        curY = p.y;
        int w=curX-SCR.start.x;int h=curY-SCR.start.y;
        captureRect =new Rectangle(SCR.start.x, SCR.start.y,w,h);
        t1.repaint(SCR.screen, SCR.a2.screenCopy);
        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
    }
    public void update_capture_rect(MouseEvent me) throws NoninvertibleTransformException{
        Point p = SCR.a1.get_original_point_coordinate(me);
        curX = p.x;
        curY = p.y;
        int w=curX-SCR.start.x;int h=curY-SCR.start.y;
        if(w>5&&h>5){
            Rectangle tempRect =new Rectangle(SCR.start.x, SCR.start.y,w,h);
            Rect_array.add(tempRect);
            Language_array.add(SCR.text_exe.language);
            SCR.label_counts++;
            String h1 = "";String t1 = "";
            Pair<String,String> temp_pair = new Pair <String,String> (h1,t1);
            temp_pair.setL(String.valueOf(SCR.label_counts));
            temp_pair.setR("label - "+SCR.label_counts);
            label.add(temp_pair);
            SCR.a9.selected_rect = Rect_array.size()-1;
            SCR.a1.Label.setEditable(true);
            SCR.a1.save_page2.setEnabled(true);
            SCR.a1.delete_page2.setEnabled(true);
            SCR.a1.Label.setEnabled(true);
            SCR.a1.Label.requestFocus(true);
            
//            SCR.a1.Label.setFocusable(true);
            if(SCR.textbox_object.label.get(SCR.a9.selected_rect).getR().contains("label -")){
                SCR.a1.Label.setText("");
//                SCR.a1.Label.requestFocusInWindow();
            }
            else{
                SCR.a1.Label.setText(SCR.textbox_object.label.get(SCR.a9.selected_rect).getR());
            }
        }
        captureRect = null;
    }
    
    public void add_indices(MouseEvent e) throws NoninvertibleTransformException{
        for(int i=0;i<Rect_array.size();i++){
            if(Rect_array.get(i).contains(SCR.a1.get_original_point_coordinate(e))){
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
        if(SCR.a9.selected_rect!=10000){
            Rect_array.remove(SCR.a9.selected_rect);
            label.remove(SCR.a9.selected_rect);
            Language_array.remove(SCR.a9.selected_rect);
            SCR.a1.Label.setEditable(false);
            SCR.a1.save_page2.setEnabled(false);
            SCR.a1.delete_page2.setEnabled(false);
            SCR.a1.Label.setEnabled(false);
            
            SCR.a1.Label.setText("");
            SCR.a9.selected_rect=10000;
        }
    }
}
