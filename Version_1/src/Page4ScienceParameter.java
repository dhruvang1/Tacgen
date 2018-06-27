import javax.swing.*;
import java.io.IOException;

public class Page4ScienceParameter extends JPanel{
    private Screen screen = new Screen(0);
    public Page4ScienceParameter(){
        Screen.initialFrameSetup.contentPane.removeAll();
        Screen.mainFrame.setJMenuBar(Screen.allControlsAndListeners.jMenuBar);
        refresh();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(Screen.mainFrame.getContentPane());
        Screen.mainFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(Screen.allControlsAndListeners.jScienceGoBackPage4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(Screen.initialFrameSetup.jScrollPane1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(Screen.allControlsAndListeners.jScienceNextPage4, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                                        .addComponent(Screen.allControlsAndListeners.jPreviewButton)
                                                        .addComponent(Screen.allControlsAndListeners.jZoomSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(Screen.allControlsAndListeners.jToolbarSciencePage4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(Screen.allControlsAndListeners.jToolbarSciencePage4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Screen.initialFrameSetup.jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(41, 41, 41)
                                                                .addComponent(Screen.allControlsAndListeners.jScienceNextPage4)
                                                                .addGap(50, 50, 50)
                                                                .addComponent(Screen.allControlsAndListeners.jPreviewButton))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(42, 42, 42)
                                                                .addComponent(Screen.allControlsAndListeners.jScienceGoBackPage4)))
                                                .addGap(85, 85, 85)
                                                .addComponent(Screen.allControlsAndListeners.jZoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))))
        );

        Screen.mainFrame.setExtendedState(Screen.mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        Screen.mainFrame.validate();
        Screen.mainFrame.repaint();
        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
        Screen.mainFrame.setVisible(true);
    }

    public void goBack() throws IOException {
        Screen.linesObject = new GetLines();
        System.out.println("lines : "+ Screen.linesObject.lines.size());
        Screen.circlesObject = new GetCircles();
        Screen.regionsObject = new GetRegions();
        Screen.polygonObject = new GetPolygon();
        Screen.arcObject = new GetArc();
        Screen.pathsObject = new GetPaths();
        Screen.page3AutoMathScience =new Page3AutoMathScience();
    }

    private void refresh(){
        Screen.allControlsAndListeners.deleteAllTemp();
        Screen.allControlsAndListeners.jPreviewButton.setEnabled(true);
        Screen.previewFrame.setVisible(false);
    }

}
