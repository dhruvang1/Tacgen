import java.io.File;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.KeyStroke;

public class page2_manual_text{
    int r=0;
    SCR t1 = new SCR(r);
    public page2_manual_text(){
        SCR.a2.pane.removeAll();
        SCR.main_frame.setJMenuBar(SCR.a1.menu_bar);
        refresh();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(SCR.main_frame.getContentPane());
        SCR.main_frame.getContentPane().setLayout(layout);
        SCR.a1.Modify_Label.setSelected(false);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SCR.a1.Separator1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SCR.a1.Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SCR.a1.save_page2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SCR.a1.Separator2))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SCR.a1.delete_page2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.Go_back_page2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SCR.a2.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SCR.a1.preview_button, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(SCR.a1.Next_page2, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(SCR.a1.zoom_slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addComponent(SCR.a1.Go_back_page2)
                                .addGap(18, 18, 18)
                                .addComponent(SCR.a1.delete_page2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(SCR.a1.Next_page2)
                                .addGap(18, 18, 18)
                                .addComponent(SCR.a1.preview_button)))
                        .addGap(80, 80, 80)
                        .addComponent(SCR.a1.zoom_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(SCR.a2.jScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SCR.a1.Separator2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.Separator1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCR.a1.Label)
                    .addComponent(SCR.a1.save_page2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        
        SCR.main_frame.setExtendedState(SCR.main_frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        SCR.main_frame.validate();
        SCR.main_frame.repaint();
        t1.repaint(SCR.screen,SCR.a2.screenCopy);
        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
        SCR.main_frame.setVisible(true);
    }
    
    public void go_back(){
        SCR.label_counts = 0;
        SCR.textbox_object = new get_textbox();
        SCR.a9 = new modify_text();
        File f = new File(SCR.current_file.getAbsolutePath()+".txt");
        f.delete();
        SCR.a20=new page1_auto_text();
    }
    
    private void refresh(){
        SCR.a9.selected_rect=10000;
        SCR.a1.Select_Text.setSelected(false);
        SCR.a1.Edit.setSelected(false);
        SCR.a1.Modify_Label.setSelected(false);
        SCR.a1.Label.setEditable(false);
        SCR.a1.save_page2.setEnabled(false);
        SCR.a1.delete_page2.setEnabled(false);
        SCR.a1.Label.setText("");
        SCR.a1.preview_button.setEnabled(true);
        SCR.preview_frame.setVisible(false);
    }
}
