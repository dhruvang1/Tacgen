

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pkp
 */
public class page1_auto_text_frame extends javax.swing.JFrame {

    /**
     * Creates new form page1_frame
     */
    public page1_auto_text_frame() {
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

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        SCRkucha1kuchdetect_text = new javax.swing.JButton();
        SCRkucha1kuchskip_page1 = new javax.swing.JButton();
        SCRkucha2kuchjScrollPane1 = new javax.swing.JScrollPane();
        SCRkucha1kuchToolBar_page1 = new javax.swing.JToolBar();
        SCRkucha1kuchcombo_page1 = new javax.swing.JComboBox();
        SCRkucha1kuchjSlider1 = new javax.swing.JSlider();
        SCRkucha1kuchzoom_slider = new javax.swing.JSlider();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        SCRkucha1kuchdetect_text.setText("Detect Text");
        SCRkucha1kuchdetect_text.setToolTipText("Automatically detect text and go to next stage");

        SCRkucha1kuchskip_page1.setText("Skip");
        SCRkucha1kuchskip_page1.setToolTipText("Go to next stage without text detection");

        SCRkucha2kuchjScrollPane1.setBorder(null);

        SCRkucha1kuchToolBar_page1.setRollover(true);
        SCRkucha1kuchToolBar_page1.setPreferredSize(new java.awt.Dimension(69, 27));

        SCRkucha1kuchcombo_page1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Language", "English", "Hindi" }));
        SCRkucha1kuchcombo_page1.setMaximumSize(new java.awt.Dimension(72, 50));
        SCRkucha1kuchcombo_page1.setPreferredSize(new java.awt.Dimension(72, 25));
        SCRkucha1kuchcombo_page1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SCRkucha1kuchcombo_page1ActionPerformed(evt);
            }
        });
        SCRkucha1kuchToolBar_page1.add(SCRkucha1kuchcombo_page1);
        SCRkucha1kuchToolBar_page1.add(SCRkucha1kuchjSlider1);

        SCRkucha1kuchzoom_slider.setMajorTickSpacing(5);
        SCRkucha1kuchzoom_slider.setMaximum(36);
        SCRkucha1kuchzoom_slider.setMinimum(1);
        SCRkucha1kuchzoom_slider.setMinorTickSpacing(1);
        SCRkucha1kuchzoom_slider.setOrientation(javax.swing.JSlider.VERTICAL);
        SCRkucha1kuchzoom_slider.setPaintLabels(true);
        SCRkucha1kuchzoom_slider.setPaintTicks(true);
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
                .addComponent(SCRkucha1kuchdetect_text, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SCRkucha2kuchjScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SCRkucha1kuchskip_page1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCRkucha1kuchzoom_slider, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(SCRkucha1kuchToolBar_page1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SCRkucha1kuchToolBar_page1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SCRkucha1kuchdetect_text)
                            .addComponent(SCRkucha1kuchskip_page1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(SCRkucha1kuchzoom_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(SCRkucha2kuchjScrollPane1)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SCRkucha1kuchcombo_page1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SCRkucha1kuchcombo_page1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SCRkucha1kuchcombo_page1ActionPerformed

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
            java.util.logging.Logger.getLogger(page1_auto_text_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(page1_auto_text_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(page1_auto_text_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(page1_auto_text_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new page1_auto_text_frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar SCRkucha1kuchToolBar_page1;
    private javax.swing.JComboBox SCRkucha1kuchcombo_page1;
    private javax.swing.JButton SCRkucha1kuchdetect_text;
    private javax.swing.JSlider SCRkucha1kuchjSlider1;
    private javax.swing.JButton SCRkucha1kuchskip_page1;
    private javax.swing.JSlider SCRkucha1kuchzoom_slider;
    private javax.swing.JScrollPane SCRkucha2kuchjScrollPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    // End of variables declaration//GEN-END:variables
}
