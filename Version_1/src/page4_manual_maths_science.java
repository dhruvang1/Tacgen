import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class page4_manual_maths_science extends JPanel{
    int r=0;
    SCR t1 = new SCR(r);
    public page4_manual_maths_science(){
        SCR.a2.pane.removeAll(); 
        SCR.main_frame.setJMenuBar(SCR.a1.menu_bar);
        refresh();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(SCR.main_frame.getContentPane());
        SCR.main_frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SCR.a1.Go_back_page4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.Line, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.Circle, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.Polygon, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.Arc, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.polygon_start, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.polygon_end, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.Path, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.Region, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.delete_page4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(SCR.a2.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SCR.a1.preview_button, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SCR.a1.zoom_slider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addComponent(SCR.a1.Next_page4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SCR.a2.jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(SCR.a1.Go_back_page4)
                .addGap(16, 16, 16)
                .addComponent(SCR.a1.Line)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SCR.a1.Circle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SCR.a1.Arc)
                .addGap(18, 18, 18)
                .addComponent(SCR.a1.Polygon)
                .addGap(18, 18, 18)
                .addComponent(SCR.a1.polygon_start)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SCR.a1.polygon_end)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SCR.a1.Path)
                .addGap(18, 18, 18)
                .addComponent(SCR.a1.Region)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SCR.a1.delete_page4)
                .addGap(23, 23, 23))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(SCR.a1.Next_page4)
                .addGap(63, 63, 63)
                .addComponent(SCR.a1.preview_button)
                .addGap(61, 61, 61)
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
        SCR.line_object = new get_lines();
    	SCR.circle_object = new get_circles();
    	SCR.region_object = new get_regions();
    	SCR.polygon_object = new get_polygon();
        SCR.arc_object = new get_arc();
        SCR.a16 = new get_paths();
        SCR.a22=new page3_auto_maths_science();
    }
    
    private void refresh(){
        SCR.a1.Line.setSelected(false);
        SCR.a1.Circle.setSelected(false);
        SCR.a1.Arc.setSelected(false);
        SCR.a1.Polygon.setSelected(false);
        SCR.a1.deselect_rb1_rb2();
        SCR.a1.Path.setSelected(false);
        SCR.a1.Region.setSelected(false);
        SCR.a1.Edit_page4.setSelected(false);
        SCR.a1.delete_all_temp();
        SCR.a1.preview_button.setEnabled(true);
        SCR.preview_frame.setVisible(false);
    }
}
