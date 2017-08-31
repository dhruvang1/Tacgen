import java.io.File;
import javax.swing.JFrame;

public class Page2ManualText {
    private Screen screen = new Screen(0);
    public Page2ManualText(){
        Screen.initialFrameSetup.contentPane.removeAll();
        Screen.mainFrame.setJMenuBar(Screen.allControlsAndListeners.jMenuBar);
        refresh();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(Screen.mainFrame.getContentPane());
        Screen.mainFrame.getContentPane().setLayout(layout);
        Screen.allControlsAndListeners.jModifyLabel.setSelected(false);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Screen.allControlsAndListeners.jSeparator1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Screen.allControlsAndListeners.jLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Screen.allControlsAndListeners.jSavePage2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Screen.allControlsAndListeners.jSeparator2))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Screen.allControlsAndListeners.jDeletePage2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.jGoBackPage2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Screen.initialFrameSetup.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Screen.allControlsAndListeners.jPreviewButton, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(Screen.allControlsAndListeners.jNextPage2, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(Screen.allControlsAndListeners.jZoomSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Screen.allControlsAndListeners.jGoBackPage2)
                                .addGap(18, 18, 18)
                                .addComponent(Screen.allControlsAndListeners.jDeletePage2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Screen.allControlsAndListeners.jNextPage2)
                                .addGap(18, 18, 18)
                                .addComponent(Screen.allControlsAndListeners.jPreviewButton)))
                        .addGap(80, 80, 80)
                        .addComponent(Screen.allControlsAndListeners.jZoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Screen.initialFrameSetup.jScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Screen.allControlsAndListeners.jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Screen.allControlsAndListeners.jLabel)
                    .addComponent(Screen.allControlsAndListeners.jSavePage2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        
        Screen.mainFrame.setExtendedState(Screen.mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        Screen.mainFrame.validate();
        Screen.mainFrame.repaint();
        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
        Screen.mainFrame.setVisible(true);
    }
    
    public void goBack(){
        Screen.label_counts = 0;
        Screen.textboxObject = new GetTextbox();
        Screen.modifyTextObject = new ModifyText();
        File file = new File(Screen.currentFile.getAbsolutePath()+".txt");
        file.delete();
        Screen.page1AutoText =new Page1AutoText();
    }
    
    private void refresh(){
        Screen.modifyTextObject.selectedRectangle =10000;
        Screen.allControlsAndListeners.jSelectText.setSelected(false);
        Screen.allControlsAndListeners.jEdit.setSelected(false);
        Screen.allControlsAndListeners.jModifyLabel.setSelected(false);
        Screen.allControlsAndListeners.jLabel.setEditable(false);
        Screen.allControlsAndListeners.jSavePage2.setEnabled(false);
        Screen.allControlsAndListeners.jDeletePage2.setEnabled(false);
        Screen.allControlsAndListeners.jLabel.setText("");
        Screen.allControlsAndListeners.jPreviewButton.setEnabled(true);
        Screen.previewFrame.setVisible(false);
    }
}
