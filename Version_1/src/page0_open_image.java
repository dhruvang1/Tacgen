import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class page0_open_image{
    int r=0;
    SCR t1 = new SCR(r);
    public page0_open_image() throws IOException{
        SCR.refresh_all.refresh();
        SCR.a2.pane.removeAll();
        SCR.main_frame.setJMenuBar(SCR.a1.menu_bar);
        refresh();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(SCR.main_frame.getContentPane());
        SCR.main_frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SCR.a2.jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SCR.a2.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );
        SCR.main_frame.setExtendedState(SCR.main_frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        SCR.main_frame.validate();
        SCR.main_frame.repaint();
        t1.repaint(SCR.screen,SCR.a2.screenCopy);
        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
        SCR.main_frame.setVisible(true);
        
    }
    
    private void refresh() throws IOException{
        File f = new File(SCR.config.get("Home_Image"));
        SCR.screen = ImageIO.read(f);
        SCR.a2.screenCopy = new BufferedImage(
                                    SCR.screen.getWidth(),
                                    SCR.screen.getHeight(),
                                    SCR.screen.getType());
        SCR.a2.screenLabel = new JLabel(new ImageIcon(SCR.a2.screenCopy));
        SCR.a2.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        SCR.a2.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);
        SCR.current_file=f;
    }
}
