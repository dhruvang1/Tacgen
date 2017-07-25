import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class page4_maths_parameter extends JPanel{
    int r=0;
    SCR t1 = new SCR(r);
    public page4_maths_parameter(){
        SCR.a2.pane.removeAll(); 
        SCR.main_frame.setJMenuBar(SCR.a1.menu_bar);
        refresh();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(SCR.main_frame.getContentPane());
        SCR.main_frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(SCR.a1.Go_back_parameter_page4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SCR.a2.jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SCR.a1.Next_parameter_page4, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                            .addComponent(SCR.a1.preview_button)
                            .addComponent(SCR.a1.zoom_slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(SCR.a1.ToolBar_page4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(SCR.a1.ToolBar_page4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SCR.a2.jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(SCR.a1.Next_parameter_page4)
                                .addGap(50, 50, 50)
                                .addComponent(SCR.a1.preview_button))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(SCR.a1.Go_back_parameter_page4)))
                        .addGap(85, 85, 85)
                        .addComponent(SCR.a1.zoom_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
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
        System.out.println("lines : "+SCR.line_object.Lines.size());
    	SCR.circle_object = new get_circles();
    	SCR.region_object = new get_regions();
    	SCR.polygon_object = new get_polygon();
        SCR.arc_object = new get_arc();
        SCR.a16 = new get_paths();
        SCR.a22=new page3_auto_maths_science();
    }
    
    private void refresh(){
        SCR.a1.delete_all_temp();
        SCR.a1.preview_button.setEnabled(true);
        SCR.preview_frame.setVisible(false);
    }
}
