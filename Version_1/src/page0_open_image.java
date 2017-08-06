import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class page0_open_image{
    int r=0;
    Screen t1 = new Screen(r);
    public page0_open_image() throws IOException{
        Screen.refresh_all.refresh();
        Screen.a2.pane.removeAll();
        Screen.main_frame.setJMenuBar(Screen.a1.jMenuBar);
        refresh();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(Screen.main_frame.getContentPane());
        Screen.main_frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Screen.a2.jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Screen.a2.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );
        Screen.main_frame.setExtendedState(Screen.main_frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        Screen.main_frame.validate();
        Screen.main_frame.repaint();
        t1.repaint(Screen.screen, Screen.a2.screenCopy);
        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
        Screen.main_frame.setVisible(true);
        
    }
    
    private void refresh() throws IOException{
        File f = new File(Screen.config.get("Home_Image"));
        Screen.screen = ImageIO.read(f);
        Screen.a2.screenCopy = new BufferedImage(
                                    Screen.screen.getWidth(),
                                    Screen.screen.getHeight(),
                                    Screen.screen.getType());
        Screen.a2.screenLabel = new JLabel(new ImageIcon(Screen.a2.screenCopy));
        Screen.a2.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Screen.a2.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);
        Screen.current_file=f;
    }
}
