import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 */
public class Hindi extends javax.swing.JFrame {
    private String tempString;
    private BufferedImage screen;
    private BufferedImage screenCopy;
    private Font englishFont;
    private Font hindiFont;
    private JComboBox jComboBox;
    public Hindi() throws FontFormatException, IOException {
        initComponents();
        tempString = "";
        String[] languages = new String[]{"Language","English","Hindi"};
        jComboBox = new JComboBox(languages);
        jComboBox.setMaximumSize(new java.awt.Dimension(80, 25));
        jToolBar.setRollover(true);
        jToolBar.add(jComboBox);
        englishFont = new java.awt.Font("sansserif", 0, 24);
        hindiFont = Font.createFont(Font.PLAIN, new File("E:\\Acads\\CSD750\\BANA_28_05\\resources\\Font\\Kruti_Dev_010.ttf")).deriveFont(Font.BOLD, 24f);
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsEnvironment.registerFont(hindiFont);
        //System.out.println(jTextField1.getFont());
        jTextField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 tempString = jTextField1.getText();
                 repaint(screen,screenCopy);
                jScrollPane1.setViewportView(jLabel);
            }
        });
        jComboBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String lang=(String) jComboBox.getSelectedItem();
                switch(lang){
                    case "Hindi":
                        jTextField1.setFont(hindiFont); break;
                    case "English":
                        jTextField1.setFont(englishFont); break;
                    case "Language":
                        jTextField1.setFont(englishFont); break;
                   default:
                        jTextField1.setFont(englishFont); break;
                }
            }
        });
        File file = new File("E:\\Acads\\CSD750\\BANA_28_05\\resources\\Images\\tactile_tool.jpg");
        screen = ImageIO.read(file);
        screenCopy = new BufferedImage(
                                    screen.getWidth(),
                                    screen.getHeight(),
                                    screen.getType());
        jLabel = new JLabel(new ImageIcon(screenCopy));
        jLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jScrollPane1.setViewportView(jLabel);
        repaint(screen,screenCopy);
        jScrollPane1.setViewportView(jLabel);
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel = new javax.swing.JLabel();
        jToolBar = new javax.swing.JToolBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jLabel);

        jToolBar.setRollover(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                .addGap(147, 147, 147))
            .addComponent(jToolBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void repaint(BufferedImage orig, BufferedImage copy){
    	Graphics2D graphics2D = copy.createGraphics();
    	graphics2D.drawImage(orig,0,0, null);
    	graphics2D.setColor(Color.blue);
        graphics2D.setFont(jTextField1.getFont());
        graphics2D.drawString(tempString,100, 98);
    }
    
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
            java.util.logging.Logger.getLogger(Hindi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Hindi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Hindi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Hindi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Hindi().setVisible(true);
                } catch (FontFormatException ex) {
                    Logger.getLogger(Hindi.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Hindi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar;
    // End of variables declaration//GEN-END:variables
}
