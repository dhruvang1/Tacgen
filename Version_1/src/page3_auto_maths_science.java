import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class page3_auto_maths_science extends JPanel{
    int r=0;
    Screen t1 = new Screen(r);
    public page3_auto_maths_science(){
        Screen.a2.pane.removeAll();
        Screen.main_frame.setJMenuBar(Screen.a1.jMenuBar);
        refresh();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(Screen.main_frame.getContentPane());
        Screen.main_frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Screen.a1.jMathButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.a1.jScienceDiagram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Screen.a1.jGoBackPage3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Screen.a2.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Screen.a1.jSkipPage3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Screen.a1.jPreviewButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Screen.a1.jZoomSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(Screen.a1.jGoBackPage3)
                        .addGap(29, 29, 29)
                        .addComponent(Screen.a1.jMathButton)
                        .addGap(39, 39, 39)
                        .addComponent(Screen.a1.jScienceDiagram))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(Screen.a1.jSkipPage3)
                        .addGap(36, 36, 36)
                        .addComponent(Screen.a1.jPreviewButton)))
                .addGap(89, 89, 89)
                .addComponent(Screen.a1.jZoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(Screen.a2.jScrollPane1)
        );

        Screen.main_frame.setExtendedState(Screen.main_frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
//        Screen.a2.pane.add(Screen.a1.combo_page3);
        Screen.main_frame.validate();
        Screen.main_frame.repaint();
        t1.repaint(Screen.screen, Screen.a2.screenCopy);
        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
        Screen.main_frame.setVisible(true);
    }
    
    public void go_back() throws IOException{
        Screen.screen = ImageIO.read(Screen.current_file);
        Screen.a2.screenCopy = new BufferedImage(
                                    (int)(Screen.zoom_scale* Screen.screen.getWidth()),
                                    (int)(Screen.zoom_scale* Screen.screen.getHeight()),
                                    Screen.screen.getType());
        Screen.a2.screenLabel = new JLabel(new ImageIcon(Screen.a2.screenCopy));
        Screen.a2.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Screen.a2.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);
        Screen.image_area_listeners.add();
        Screen.a21=new page2_manual_text();
    }
    private void refresh(){
        Screen.a9.selected_rect=10000;
        Screen.a1.duplicateLineDetectionByDistance.setValueIsAdjusting(true);
        Screen.a1.duplicateLineDetectionByDistance.setValue(10);
        Screen.a1.duplicateLineDetectionByAngle.setValueIsAdjusting(true);
        Screen.a1.duplicateLineDetectionByAngle.setValue(5);
    }
}
