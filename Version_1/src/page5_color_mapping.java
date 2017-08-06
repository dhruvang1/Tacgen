import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class page5_color_mapping extends JPanel{
    int r=0;
    Screen t1 = new Screen(r);
    public page5_color_mapping(){
        Screen.a2.pane.removeAll();
        Screen.main_frame.setJMenuBar(Screen.a1.jMenuBar);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(Screen.main_frame.getContentPane());
        Screen.main_frame.getContentPane().setLayout(layout);
        refresh();
//        Screen.a1.Selected_color.setEnabled(false);
//        Screen.a1.Selected_color.setBackground(Color.black);
//        Screen.current_color=Color.black;
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Screen.a1.jSelectedColor, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.a1.jGoBackPage5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Screen.a2.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Screen.a1.jSaveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(Screen.a1.jPreviewButton)
                    .addComponent(Screen.a1.jZoomSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Screen.a2.jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(Screen.a1.jGoBackPage5)
                        .addGap(35, 35, 35)
                        .addComponent(Screen.a1.jSelectedColor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(Screen.a1.jSaveButton)
                        .addGap(49, 49, 49)
                        .addComponent(Screen.a1.jPreviewButton)))
                .addGap(104, 104, 104)
                .addComponent(Screen.a1.jZoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        Screen.main_frame.setExtendedState(Screen.main_frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        Screen.main_frame.validate();
        Screen.main_frame.repaint();
        t1.repaint(Screen.screen, Screen.a2.screenCopy);
        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
        Screen.main_frame.setVisible(true);
    }
        
    public void go_back() throws IOException{
        for(int i = 0; i< Screen.line_object.color_array.size(); i++){
            Screen.line_object.color_array.set(i, Color.black);
        }
        for(int i = 0; i< Screen.circle_object.colorArray.size(); i++){
            Screen.circle_object.colorArray.set(i, Color.black);
            Screen.circle_object.fillArray.set(i, 0);
        }
        for(int i = 0; i< Screen.region_object.colorArray.size(); i++){
            Screen.region_object.colorArray.set(i, Color.black);
            Screen.region_object.fillArray.set(i, 0);
        }
        for(int i = 0; i< Screen.polygon_object.color_array.size(); i++){
            Screen.polygon_object.color_array.set(i, Color.black);
            Screen.polygon_object.fill_or_not.set(i, 0);
        }
        for(int i = 0; i< Screen.arc_object.colorArray.size(); i++){
            Screen.arc_object.colorArray.set(i, Color.black);
            Screen.arc_object.fillArray.set(i, 0);
        }
//        for(int i=0;i<Screen.a16.color_array.size();i++){
//            Screen.a16.color_array.set(i, Color.black);
//        }
        Screen.current_color=Color.black;
        Screen.a23=new page4_manual_maths_science();
    }
    
    private void refresh(){
        Screen.a1.jFillColor.setSelected(false);
        Screen.a1.jSelectedColor.setBackground(Color.black);
        Screen.current_color=Color.black;
        Screen.a1.jSaveButton.setEnabled(true);
        Screen.a1.jPreviewButton.setEnabled(true);
        Screen.preview_frame.setVisible(false);
    }
}
