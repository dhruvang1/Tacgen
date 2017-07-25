import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class page5_color_mapping extends JPanel{
    int r=0;
    SCR t1 = new SCR(r);
    public page5_color_mapping(){
        SCR.a2.pane.removeAll(); 
        SCR.main_frame.setJMenuBar(SCR.a1.menu_bar);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(SCR.main_frame.getContentPane());
        SCR.main_frame.getContentPane().setLayout(layout);
        refresh();
//        SCR.a1.Selected_color.setEnabled(false);
//        SCR.a1.Selected_color.setBackground(Color.black);
//        SCR.current_color=Color.black;
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SCR.a1.Selected_color, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.Go_back_page5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SCR.a2.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SCR.a1.Save, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(SCR.a1.preview_button)
                    .addComponent(SCR.a1.zoom_slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SCR.a2.jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(SCR.a1.Go_back_page5)
                        .addGap(35, 35, 35)
                        .addComponent(SCR.a1.Selected_color, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(SCR.a1.Save)
                        .addGap(49, 49, 49)
                        .addComponent(SCR.a1.preview_button)))
                .addGap(104, 104, 104)
                .addComponent(SCR.a1.zoom_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        SCR.main_frame.setExtendedState(SCR.main_frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        SCR.main_frame.validate();
        SCR.main_frame.repaint();
        t1.repaint(SCR.screen,SCR.a2.screenCopy);
        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
        SCR.main_frame.setVisible(true);
    }
        
    public void go_back() throws IOException{
        for(int i=0;i<SCR.line_object.color_array.size();i++){
            SCR.line_object.color_array.set(i, Color.black);
        }
        for(int i=0;i<SCR.circle_object.color_array.size();i++){
            SCR.circle_object.color_array.set(i, Color.black);
            SCR.circle_object.fill_or_not.set(i, 0);
        }
        for(int i=0;i<SCR.region_object.color_array.size();i++){
            SCR.region_object.color_array.set(i, Color.black);
            SCR.region_object.fill_or_not.set(i, 0);
        }
        for(int i=0;i<SCR.polygon_object.color_array.size();i++){
            SCR.polygon_object.color_array.set(i, Color.black);
            SCR.polygon_object.fill_or_not.set(i, 0);
        }
        for(int i=0;i<SCR.arc_object.color_array.size();i++){
            SCR.arc_object.color_array.set(i, Color.black);
            SCR.arc_object.fill_or_not.set(i, 0);
        }
//        for(int i=0;i<SCR.a16.color_array.size();i++){
//            SCR.a16.color_array.set(i, Color.black);
//        }
        SCR.current_color=Color.black;
        SCR.a23=new page4_manual_maths_science();
    }
    
    private void refresh(){
        SCR.a1.Fill_color.setSelected(false);
        SCR.a1.Selected_color.setBackground(Color.black);
        SCR.current_color=Color.black;
        SCR.a1.Save.setEnabled(true);
        SCR.a1.preview_button.setEnabled(true);
        SCR.preview_frame.setVisible(false);
    }
}
