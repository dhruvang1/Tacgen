import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class page4_maths_parameter extends JPanel{
    int r=0;
    Screen t1 = new Screen(r);
    public page4_maths_parameter(){
        Screen.a2.pane.removeAll();
        Screen.main_frame.setJMenuBar(Screen.a1.jMenuBar);
        refresh();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(Screen.main_frame.getContentPane());
        Screen.main_frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(Screen.a1.jMathGoBackPage4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Screen.a2.jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Screen.a1.jMathNextPage4, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                            .addComponent(Screen.a1.jPreviewButton)
                            .addComponent(Screen.a1.jZoomSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Screen.a1.jToolbarPage4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(Screen.a1.jToolbarPage4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Screen.a2.jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(Screen.a1.jMathNextPage4)
                                .addGap(50, 50, 50)
                                .addComponent(Screen.a1.jPreviewButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(Screen.a1.jMathGoBackPage4)))
                        .addGap(85, 85, 85)
                        .addComponent(Screen.a1.jZoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        Screen.main_frame.setExtendedState(Screen.main_frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        Screen.main_frame.validate();
        Screen.main_frame.repaint();
        t1.repaint(Screen.screen, Screen.a2.screenCopy);
        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
        Screen.main_frame.setVisible(true);
    }
    
    public void go_back() throws IOException{
        Screen.line_object = new get_lines();
        System.out.println("lines : "+ Screen.line_object.Lines.size());
    	Screen.circle_object = new GetCircles();
    	Screen.region_object = new GetRegions();
    	Screen.polygon_object = new get_polygon();
        Screen.arc_object = new GetArc();
        Screen.a16 = new GetPaths();
        Screen.a22=new page3_auto_maths_science();
    }
    
    private void refresh(){
        Screen.a1.deleteAllTemp();
        Screen.a1.jPreviewButton.setEnabled(true);
        Screen.preview_frame.setVisible(false);
    }
}
