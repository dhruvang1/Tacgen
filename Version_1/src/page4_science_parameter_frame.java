public class page4_science_parameter_frame extends javax.swing.JFrame {
    public page4_science_parameter_frame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        SCRkucha2kuchjScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        SCRkucha1kuchGo_back_parameter_page4 = new javax.swing.JButton();
        SCRkucha1kuchNext_parameter_page4 = new javax.swing.JButton();
        SCRkucha1kuchpreview_button = new javax.swing.JLabel();
        SCRkucha1kuchToolBar_page4 = new javax.swing.JToolBar();
        dist_p_math_p = new javax.swing.JLabel();
        dup_line_dist_slider = new javax.swing.JSlider();
        angle_p_math_p = new javax.swing.JLabel();
        dup_line_angle_slider = new javax.swing.JSlider();
        SCRkucha1kuchzoom_slider = new javax.swing.JSlider();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        SCRkucha2kuchjScrollPane1.setViewportView(jLabel1);

        SCRkucha1kuchGo_back_parameter_page4.setText("Go Back");
        SCRkucha1kuchGo_back_parameter_page4.setToolTipText("Revert back to previous stage");
        SCRkucha1kuchGo_back_parameter_page4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SCRkucha1kuchGo_back_parameter_page4ActionPerformed(evt);
            }
        });

        SCRkucha1kuchNext_parameter_page4.setText("Next");
        SCRkucha1kuchNext_parameter_page4.setToolTipText("Go to next page if all shapes have been identified");

        SCRkucha1kuchpreview_button.setIcon(new javax.swing.ImageIcon("E:\\Acads\\CSD750\\BANA_28_05\\resources\\Images\\preview.png")); // NOI18N

        SCRkucha1kuchToolBar_page4.setRollover(true);

        dist_p_math_p.setText("<html>Distance<br>parameter</html>");
        SCRkucha1kuchToolBar_page4.add(dist_p_math_p);

        dup_line_dist_slider.setMajorTickSpacing(10);
        dup_line_dist_slider.setMaximum(50);
        dup_line_dist_slider.setMinimum(10);
        dup_line_dist_slider.setMinorTickSpacing(5);
        dup_line_dist_slider.setPaintLabels(true);
        dup_line_dist_slider.setPaintTicks(true);
        dup_line_dist_slider.setToolTipText("<html>A parameter to remove nearly detected duplicate lines.\"                 + \"<br>Duplicate lines with gap less than this value \"                 + \"<br>will be removed. Increase this \"                 + \"<br>if duplicate lines are detected</html>");
        dup_line_dist_slider.setValue(20);
        dup_line_dist_slider.setMaximumSize(new java.awt.Dimension(100, 45));
        dup_line_dist_slider.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                dup_line_dist_sliderAncestorResized(evt);
            }
        });
        SCRkucha1kuchToolBar_page4.add(dup_line_dist_slider);

        angle_p_math_p.setText("<html>Angle<br>Parameter</html>");
        SCRkucha1kuchToolBar_page4.add(angle_p_math_p);

        dup_line_angle_slider.setMajorTickSpacing(2);
        dup_line_angle_slider.setMaximum(10);
        dup_line_angle_slider.setMinorTickSpacing(1);
        dup_line_angle_slider.setPaintLabels(true);
        dup_line_angle_slider.setPaintTicks(true);
        dup_line_angle_slider.setSnapToTicks(true);
        dup_line_angle_slider.setToolTipText("<html>A parameter to remove nearly detected duplicate lines.\"                 + \"<br>Duplicate lines with angle less than this value \"                 + \"<br>will be removed. Increase this \"                 + \"<br>if duplicate lines are detected</html>");
        dup_line_angle_slider.setValue(5);
        SCRkucha1kuchToolBar_page4.add(dup_line_angle_slider);

        SCRkucha1kuchzoom_slider.setOrientation(javax.swing.JSlider.VERTICAL);

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(SCRkucha1kuchGo_back_parameter_page4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SCRkucha2kuchjScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SCRkucha1kuchNext_parameter_page4, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                            .addComponent(SCRkucha1kuchpreview_button)
                            .addComponent(SCRkucha1kuchzoom_slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(SCRkucha1kuchToolBar_page4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(SCRkucha1kuchToolBar_page4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SCRkucha2kuchjScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(SCRkucha1kuchNext_parameter_page4)
                                .addGap(50, 50, 50)
                                .addComponent(SCRkucha1kuchpreview_button))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(SCRkucha1kuchGo_back_parameter_page4)))
                        .addGap(78, 78, 78)
                        .addComponent(SCRkucha1kuchzoom_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SCRkucha1kuchGo_back_parameter_page4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SCRkucha1kuchGo_back_parameter_page4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SCRkucha1kuchGo_back_parameter_page4ActionPerformed

    private void dup_line_dist_sliderAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_dup_line_dist_sliderAncestorResized
        // TODO add your handling code here:
    }//GEN-LAST:event_dup_line_dist_sliderAncestorResized

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
            java.util.logging.Logger.getLogger(page4_maths_parameter_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(page4_maths_parameter_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(page4_maths_parameter_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(page4_maths_parameter_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new page4_maths_parameter_frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SCRkucha1kuchGo_back_parameter_page4;
    private javax.swing.JButton SCRkucha1kuchNext_parameter_page4;
    private javax.swing.JToolBar SCRkucha1kuchToolBar_page4;
    private javax.swing.JLabel SCRkucha1kuchpreview_button;
    private javax.swing.JSlider SCRkucha1kuchzoom_slider;
    private javax.swing.JScrollPane SCRkucha2kuchjScrollPane1;
    private javax.swing.JLabel angle_p_math_p;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.JLabel dist_p_math_p;
    private javax.swing.JSlider dup_line_angle_slider;
    private javax.swing.JSlider dup_line_dist_slider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
