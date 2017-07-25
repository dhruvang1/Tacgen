import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class page3_auto_maths_science extends JPanel{
    int r=0;
    SCR t1 = new SCR(r);
    public page3_auto_maths_science(){
        SCR.a2.pane.removeAll();
        SCR.main_frame.setJMenuBar(SCR.a1.menu_bar);
        refresh();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(SCR.main_frame.getContentPane());
        SCR.main_frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SCR.a1.Math_diagram, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.Science_diagram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SCR.a1.Go_back_page3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SCR.a2.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SCR.a1.skip_page3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SCR.a1.preview_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SCR.a1.zoom_slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(SCR.a1.Go_back_page3)
                        .addGap(29, 29, 29)
                        .addComponent(SCR.a1.Math_diagram)
                        .addGap(39, 39, 39)
                        .addComponent(SCR.a1.Science_diagram))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(SCR.a1.skip_page3)
                        .addGap(36, 36, 36)
                        .addComponent(SCR.a1.preview_button)))
                .addGap(89, 89, 89)
                .addComponent(SCR.a1.zoom_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(SCR.a2.jScrollPane1)
        );

        SCR.main_frame.setExtendedState(SCR.main_frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
//        SCR.a2.pane.add(SCR.a1.combo_page3);
        SCR.main_frame.validate();
        SCR.main_frame.repaint();
        t1.repaint(SCR.screen,SCR.a2.screenCopy);
        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
        SCR.main_frame.setVisible(true);
    }
    
    public void go_back() throws IOException{
        SCR.screen = ImageIO.read(SCR.current_file);
        SCR.a2.screenCopy = new BufferedImage(
                                    (int)(SCR.zoom_scale*SCR.screen.getWidth()),
                                    (int)(SCR.zoom_scale*SCR.screen.getHeight()),
                                    SCR.screen.getType());
        SCR.a2.screenLabel = new JLabel(new ImageIcon(SCR.a2.screenCopy));
        SCR.a2.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        SCR.a2.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);
        SCR.image_area_listeners.add();
        SCR.a21=new page2_manual_text();
    }
    private void refresh(){
        SCR.a9.selected_rect=10000;
        SCR.a1.dup_line_dist_slider.setValueIsAdjusting(true);
        SCR.a1.dup_line_dist_slider.setValue(10);
        SCR.a1.dup_line_angle_slider.setValueIsAdjusting(true);
        SCR.a1.dup_line_angle_slider.setValue(5);
    }
}
