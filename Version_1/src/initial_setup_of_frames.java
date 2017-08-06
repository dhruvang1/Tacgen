import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class initial_setup_of_frames {
    int r=0;
    Screen t1;
    private static final long serialVersionUID = 1L;
    public javax.swing.JScrollPane jScrollPane1;
    JLabel screenLabel=null;
    Container pane = null;
    BufferedImage screenCopy;
    BufferedImage whitecopy;
    JScrollPane preview;
    JLabel preview_Label=null;
    public javax.swing.JScrollPane preview_ScrollPane;
    public initial_setup_of_frames() throws IOException {
        screenLabel = new JLabel();
        preview = new JScrollPane();
        preview_Label = new JLabel();
        preview_ScrollPane = new javax.swing.JScrollPane();
        Screen.preview_frame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        
        preview_ScrollPane.setViewportView(preview_Label);
        
        JScrollBar horizontal = preview_ScrollPane.getHorizontalScrollBar();
        horizontal.setUnitIncrement(20);
        
        InputMap horizontalMap = horizontal.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW );
        horizontalMap.put( KeyStroke.getKeyStroke( "RIGHT" ), "positiveUnitIncrement" );
        horizontalMap.put( KeyStroke.getKeyStroke( "LEFT" ),"negativeUnitIncrement");
        JScrollBar vertical = preview_ScrollPane.getVerticalScrollBar();
        vertical.setUnitIncrement(20);
        
        InputMap verticalMap = vertical.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW );
        verticalMap.put( KeyStroke.getKeyStroke( "DOWN" ), "positiveUnitIncrement" );
        verticalMap.put( KeyStroke.getKeyStroke( "UP" ), "negativeUnitIncrement" );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(Screen.preview_frame.getContentPane());
        Screen.preview_frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(preview_ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(preview_ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
        //Screen.preview_frame.pack();
        
        File f = new File(Screen.config.get("white"));
        Screen.white = ImageIO.read(f);
        whitecopy = new BufferedImage(
                                    Screen.white.getWidth(),
                                    Screen.white.getHeight(),
                                    Screen.white.getType());
        preview_Label = new JLabel(new ImageIcon(whitecopy));
        preview_Label.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        preview_Label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        preview.setViewportView(preview_Label);
        
        t1 = new Screen(r);
        jScrollPane1 = new javax.swing.JScrollPane();
        JScrollBar hor = jScrollPane1.getHorizontalScrollBar();
        hor.setUnitIncrement(20);
        
        InputMap horMap = hor.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW );
        horMap.put( KeyStroke.getKeyStroke( "RIGHT" ), "positiveUnitIncrement" );
        horMap.put( KeyStroke.getKeyStroke( "LEFT" ),"negativeUnitIncrement");
        JScrollBar vert = jScrollPane1.getVerticalScrollBar();
        vert.setUnitIncrement(20);
        
        InputMap vertMap = vert.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW );
        vertMap.put( KeyStroke.getKeyStroke( "DOWN" ), "positiveUnitIncrement" );
        vertMap.put( KeyStroke.getKeyStroke( "UP" ), "negativeUnitIncrement" );

        pane = Screen.main_frame.getContentPane();
    }
}
