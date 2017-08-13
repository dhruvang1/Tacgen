import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Page3AutoMathScience extends JPanel{
    Screen screen = new Screen(0);
    public Page3AutoMathScience(){
        Screen.initialFrameSetup.contentPane.removeAll();
        Screen.mainFrame.setJMenuBar(Screen.allControlsAndListeners.jMenuBar);
        refresh();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(Screen.mainFrame.getContentPane());
        Screen.mainFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Screen.allControlsAndListeners.jMathButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.jScienceDiagram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Screen.allControlsAndListeners.jGoBackPage3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Screen.initialFrameSetup.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Screen.allControlsAndListeners.jSkipPage3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Screen.allControlsAndListeners.jPreviewButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Screen.allControlsAndListeners.jZoomSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(Screen.allControlsAndListeners.jGoBackPage3)
                        .addGap(29, 29, 29)
                        .addComponent(Screen.allControlsAndListeners.jMathButton)
                        .addGap(39, 39, 39)
                        .addComponent(Screen.allControlsAndListeners.jScienceDiagram))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(Screen.allControlsAndListeners.jSkipPage3)
                        .addGap(36, 36, 36)
                        .addComponent(Screen.allControlsAndListeners.jPreviewButton)))
                .addGap(89, 89, 89)
                .addComponent(Screen.allControlsAndListeners.jZoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(Screen.initialFrameSetup.jScrollPane1)
        );

        Screen.mainFrame.setExtendedState(Screen.mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
//        Screen.a2.pane.add(Screen.a1.combo_page3);
        Screen.mainFrame.validate();
        Screen.mainFrame.repaint();
        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
        Screen.mainFrame.setVisible(true);
    }
    
    public void goBack() throws IOException{
        Screen.bufferedImageScreen = ImageIO.read(Screen.currentFile);
        Screen.initialFrameSetup.screenCopy = new BufferedImage(
                                    (int)(Screen.zoomScale * Screen.bufferedImageScreen.getWidth()),
                                    (int)(Screen.zoomScale * Screen.bufferedImageScreen.getHeight()),
                                    Screen.bufferedImageScreen.getType());
        Screen.initialFrameSetup.screenLabel = new JLabel(new ImageIcon(Screen.initialFrameSetup.screenCopy));
        Screen.initialFrameSetup.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Screen.initialFrameSetup.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
        Screen.imageAreaListeners.add();
        Screen.page2ManualText =new Page2ManualText();
    }
    private void refresh(){
        Screen.modifyTextObject.selectedRectangle =10000;
        Screen.allControlsAndListeners.duplicateLineDetectionByDistance.setValueIsAdjusting(true);
        Screen.allControlsAndListeners.duplicateLineDetectionByDistance.setValue(10);
        Screen.allControlsAndListeners.duplicateLineDetectionByAngle.setValueIsAdjusting(true);
        Screen.allControlsAndListeners.duplicateLineDetectionByAngle.setValue(5);
    }
}
