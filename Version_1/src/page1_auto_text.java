import javax.swing.JFrame;

public class page1_auto_text{
    int r=0;
    SCR t1 = new SCR(r);
    public page1_auto_text(){
        SCR.a2.pane.removeAll();
        SCR.main_frame.setJMenuBar(SCR.a1.menu_bar);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(SCR.main_frame.getContentPane());
        SCR.main_frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SCR.a1.detect_text, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SCR.a2.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SCR.a1.skip_page1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.zoom_slider, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(SCR.a1.ToolBar_page1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SCR.a1.ToolBar_page1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SCR.a1.detect_text)
                            .addComponent(SCR.a1.skip_page1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(SCR.a1.zoom_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(SCR.a2.jScrollPane1)))
        );        
        SCR.main_frame.setExtendedState(SCR.main_frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        SCR.main_frame.validate();
        SCR.main_frame.repaint();
        t1.repaint(SCR.screen,SCR.a2.screenCopy);
        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
        SCR.main_frame.setVisible(true);
        SCR.a1.preview_button.setEnabled(true);
        SCR.preview_frame.setVisible(false);
    }
}
