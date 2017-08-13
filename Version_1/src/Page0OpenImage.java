import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Page0OpenImage {
    Screen screen = new Screen(0);
    public Page0OpenImage() throws IOException{
        Screen.allObjectReinitializer.refresh();
        Screen.initialFrameSetup.contentPane.removeAll();
        Screen.mainFrame.setJMenuBar(Screen.allControlsAndListeners.jMenuBar);
        refresh();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(Screen.mainFrame.getContentPane());
        Screen.mainFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Screen.initialFrameSetup.jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Screen.initialFrameSetup.jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );
        Screen.mainFrame.setExtendedState(Screen.mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        Screen.mainFrame.validate();
        Screen.mainFrame.repaint();
        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
        Screen.mainFrame.setVisible(true);
    }
    
    private void refresh() throws IOException{
        File file = new File(Screen.config.get("Home_Image"));
        Screen.bufferedImageScreen = ImageIO.read(file);
        Screen.initialFrameSetup.screenCopy = new BufferedImage(
                                    Screen.bufferedImageScreen.getWidth(),
                                    Screen.bufferedImageScreen.getHeight(),
                                    Screen.bufferedImageScreen.getType());
        Screen.initialFrameSetup.screenLabel = new JLabel(new ImageIcon(Screen.initialFrameSetup.screenCopy));
        Screen.initialFrameSetup.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Screen.initialFrameSetup.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
        Screen.currentFile =file;
    }
}