import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Page4ManualMathScience extends JPanel{
    Screen screen = new Screen(0);
    public Page4ManualMathScience(){
        Screen.initialFrameSetup.contentPane.removeAll();
        Screen.mainFrame.setJMenuBar(Screen.allControlsAndListeners.jMenuBar);
        refresh();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(Screen.mainFrame.getContentPane());
        Screen.mainFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Screen.allControlsAndListeners.jGoBackPage4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.drawLine, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.drawCircle, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.drawPolygon, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.drawArc, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.polygonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.polygonEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.drawPath, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.drawRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.jDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(Screen.initialFrameSetup.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Screen.allControlsAndListeners.jPreviewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Screen.allControlsAndListeners.jZoomSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addComponent(Screen.allControlsAndListeners.jNextPage4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Screen.initialFrameSetup.jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(Screen.allControlsAndListeners.jGoBackPage4)
                .addGap(16, 16, 16)
                .addComponent(Screen.allControlsAndListeners.drawLine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Screen.allControlsAndListeners.drawCircle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Screen.allControlsAndListeners.drawArc)
                .addGap(18, 18, 18)
                .addComponent(Screen.allControlsAndListeners.drawPolygon)
                .addGap(18, 18, 18)
                .addComponent(Screen.allControlsAndListeners.polygonStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Screen.allControlsAndListeners.polygonEnd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Screen.allControlsAndListeners.drawPath)
                .addGap(18, 18, 18)
                .addComponent(Screen.allControlsAndListeners.drawRegion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Screen.allControlsAndListeners.jDeleteButton)
                .addGap(23, 23, 23))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(Screen.allControlsAndListeners.jNextPage4)
                .addGap(63, 63, 63)
                .addComponent(Screen.allControlsAndListeners.jPreviewButton)
                .addGap(61, 61, 61)
                .addComponent(Screen.allControlsAndListeners.jZoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Screen.mainFrame.setExtendedState(Screen.mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        Screen.mainFrame.validate();
        Screen.mainFrame.repaint();
        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
        Screen.mainFrame.setVisible(true);
    }
    
    public void goBack() throws IOException{
        Screen.linesObject = new GetLines();
    	Screen.circlesObject = new GetCircles();
    	Screen.regionsObject = new GetRegions();
    	Screen.polygonObject = new GetPolygon();
        Screen.arcObject = new GetArc();
        Screen.pathsObject = new GetPaths();
        Screen.page3AutoMathScience =new Page3AutoMathScience();
    }
    
    private void refresh(){
        Screen.allControlsAndListeners.drawLine.setSelected(false);
        Screen.allControlsAndListeners.drawCircle.setSelected(false);
        Screen.allControlsAndListeners.drawArc.setSelected(false);
        Screen.allControlsAndListeners.drawPolygon.setSelected(false);
        Screen.allControlsAndListeners.deselectRadioButtons();
        Screen.allControlsAndListeners.drawPath.setSelected(false);
        Screen.allControlsAndListeners.drawRegion.setSelected(false);
        Screen.allControlsAndListeners.jEditPage4.setSelected(false);
        Screen.allControlsAndListeners.deleteAllTemp();
        Screen.allControlsAndListeners.jPreviewButton.setEnabled(true);
        Screen.previewFrame.setVisible(false);
    }
}
