import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Page5ColorMapping extends JPanel{
    private Screen screen = new Screen(0);
    public Page5ColorMapping(){
        Screen.initialFrameSetup.contentPane.removeAll();
        Screen.mainFrame.setJMenuBar(Screen.allControlsAndListeners.jMenuBar);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(Screen.mainFrame.getContentPane());
        Screen.mainFrame.getContentPane().setLayout(layout);
        refresh();

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Screen.allControlsAndListeners.jSelectedColor, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.jGoBackPage5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Screen.initialFrameSetup.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Screen.allControlsAndListeners.jSaveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(Screen.allControlsAndListeners.jPreviewButton)
                    .addComponent(Screen.allControlsAndListeners.jZoomSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Screen.initialFrameSetup.jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(Screen.allControlsAndListeners.jGoBackPage5)
                        .addGap(35, 35, 35)
                        .addComponent(Screen.allControlsAndListeners.jSelectedColor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(Screen.allControlsAndListeners.jSaveButton)
                        .addGap(49, 49, 49)
                        .addComponent(Screen.allControlsAndListeners.jPreviewButton)))
                .addGap(104, 104, 104)
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
        for(int i = 0; i< Screen.linesObject.colorArray.size(); i++){
            Screen.linesObject.colorArray.set(i, Color.black);
        }
//        for(int i = 0; i< Screen.circlesObject.colorArray.size(); i++){
//            Screen.circlesObject.colorArray.set(i, Color.black);
//            Screen.circlesObject.fillArray.set(i, 0);
//        }
        for(int i = 0; i< Screen.circlesObject.allCircles.size(); i++){
            Screen.circlesObject.allCircles.get(i).color = Color.black;
            Screen.circlesObject.allCircles.get(i).fill = 0;
        }
        for(int i = 0; i< Screen.regionsObject.colorArray.size(); i++){
            Screen.regionsObject.colorArray.set(i, Color.black);
            Screen.regionsObject.fillArray.set(i, 0);
        }
        for(int i = 0; i< Screen.polygonObject.colorArray.size(); i++){
            Screen.polygonObject.colorArray.set(i, Color.black);
            Screen.polygonObject.fillOrNot.set(i, 0);
        }
//        for(int i = 0; i< Screen.arcObject.colorArray.size(); i++){
//            Screen.arcObject.colorArray.set(i, Color.black);
//            Screen.arcObject.fillArray.set(i, 0);
//        }
        for(int i = 0; i< Screen.arcObject.allArcs.size(); i++){
            Screen.arcObject.allArcs.get(i).color = Color.black;
            Screen.arcObject.allArcs.get(i).fill = 0;
        }
//        for(int i=0;i<Screen.a16.color_array.size();i++){
//            Screen.a16.color_array.set(i, Color.black);
//        }
        Screen.currentColor = Color.black;
        Screen.page4ManualMathScience = new Page4ManualMathScience();
    }
    
    private void refresh(){
        Screen.allControlsAndListeners.jFillColor.setSelected(false);
        Screen.allControlsAndListeners.jSelectedColor.setBackground(Color.black);
        Screen.currentColor =Color.black;
        Screen.allControlsAndListeners.jSaveButton.setEnabled(true);
        Screen.allControlsAndListeners.jPreviewButton.setEnabled(true);
        Screen.previewFrame.setVisible(false);
    }
}
