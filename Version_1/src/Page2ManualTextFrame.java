/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pkp
 */
public class Page2ManualTextFrame extends javax.swing.JFrame {

    /**
     * Creates new form page1_frame
     */
    public Page2ManualTextFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SCRkucha2kuchjScrollPane1 = new javax.swing.JScrollPane();
        SCRkucha1kuchGo_back_page2 = new javax.swing.JButton();
        SCRkucha1kuchSeparator2 = new javax.swing.JSeparator();
        SCRkucha1kuchSeparator1 = new javax.swing.JSeparator();
        SCRkucha1kuchLabel = new javax.swing.JTextField();
        SCRkucha1kuchdelete_page2 = new javax.swing.JButton();
        SCRkucha1kuchsave_page2 = new javax.swing.JButton();
        SCRkucha1kuchNext_page2 = new javax.swing.JButton();
        SCRkucha1kuchpreview_button = new javax.swing.JLabel();
        SCRkucha1kuchzoom_slider = new javax.swing.JSlider();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setForeground(java.awt.Color.white);

        SCRkucha1kuchGo_back_page2.setIcon(new javax.swing.ImageIcon("E:\\Acads\\CSD750\\BANA_28_05\\resources\\Images\\goback.png")); // NOI18N
        SCRkucha1kuchGo_back_page2.setToolTipText("Revert back to previous stage");
        SCRkucha1kuchGo_back_page2.setBorderPainted(false);
        SCRkucha1kuchGo_back_page2.setContentAreaFilled(false);
        SCRkucha1kuchGo_back_page2.setFocusable(false);

        SCRkucha1kuchSeparator2.setBackground(new java.awt.Color(240, 240, 240));
        SCRkucha1kuchSeparator2.setForeground(new java.awt.Color(240, 240, 240));
        SCRkucha1kuchSeparator2.setEnabled(false);

        SCRkucha1kuchSeparator1.setBackground(new java.awt.Color(240, 240, 240));
        SCRkucha1kuchSeparator1.setForeground(new java.awt.Color(240, 240, 240));
        SCRkucha1kuchSeparator1.setEnabled(false);

        SCRkucha1kuchLabel.setFont(new java.awt.Font("Courier New", 0, 24)); // NOI18N
        SCRkucha1kuchLabel.setToolTipText("Modify labels here");

        SCRkucha1kuchdelete_page2.setText("Delete");
        SCRkucha1kuchdelete_page2.setToolTipText("Delete selected text boxes");

        SCRkucha1kuchsave_page2.setText("save");

        SCRkucha1kuchNext_page2.setText("Next");

        SCRkucha1kuchpreview_button.setIcon(new javax.swing.ImageIcon("E:\\Acads\\CSD750\\BANA_28_05\\resources\\Images\\delete.png")); // NOI18N

        SCRkucha1kuchzoom_slider.setMajorTickSpacing(5);
        SCRkucha1kuchzoom_slider.setMaximum(36);
        SCRkucha1kuchzoom_slider.setMinimum(1);
        SCRkucha1kuchzoom_slider.setMinorTickSpacing(1);
        SCRkucha1kuchzoom_slider.setOrientation(javax.swing.JSlider.VERTICAL);
        SCRkucha1kuchzoom_slider.setPaintLabels(true);
        SCRkucha1kuchzoom_slider.setPaintTicks(true);
        SCRkucha1kuchzoom_slider.setSnapToTicks(true);
        SCRkucha1kuchzoom_slider.setValue(16);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SCRkucha1kuchSeparator1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SCRkucha1kuchLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SCRkucha1kuchsave_page2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SCRkucha1kuchSeparator2))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SCRkucha1kuchdelete_page2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCRkucha1kuchGo_back_page2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SCRkucha2kuchjScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SCRkucha1kuchpreview_button, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(SCRkucha1kuchNext_page2, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(SCRkucha1kuchzoom_slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addComponent(SCRkucha1kuchGo_back_page2)
                                .addGap(18, 18, 18)
                                .addComponent(SCRkucha1kuchdelete_page2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(SCRkucha1kuchNext_page2)
                                .addGap(18, 18, 18)
                                .addComponent(SCRkucha1kuchpreview_button)))
                        .addGap(80, 80, 80)
                        .addComponent(SCRkucha1kuchzoom_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(SCRkucha2kuchjScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SCRkucha1kuchSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCRkucha1kuchSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCRkucha1kuchLabel)
                    .addComponent(SCRkucha1kuchsave_page2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Page2ManualTextFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Page2ManualTextFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Page2ManualTextFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Page2ManualTextFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Page2ManualTextFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SCRkucha1kuchGo_back_page2;
    private javax.swing.JTextField SCRkucha1kuchLabel;
    private javax.swing.JButton SCRkucha1kuchNext_page2;
    private javax.swing.JSeparator SCRkucha1kuchSeparator1;
    private javax.swing.JSeparator SCRkucha1kuchSeparator2;
    private javax.swing.JButton SCRkucha1kuchdelete_page2;
    private javax.swing.JLabel SCRkucha1kuchpreview_button;
    private javax.swing.JButton SCRkucha1kuchsave_page2;
    private javax.swing.JSlider SCRkucha1kuchzoom_slider;
    private javax.swing.JScrollPane SCRkucha2kuchjScrollPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
