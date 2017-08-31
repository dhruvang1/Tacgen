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

public class InitialFrameSetup {
    private static final long serialVersionUID = 1L;
    public javax.swing.JScrollPane jScrollPane1;
    public JLabel screenLabel = null;
    public Container contentPane = null;
    public BufferedImage screenCopy;
    public BufferedImage whitecopy;
    public JScrollPane preview;
    public JLabel previewLabel = null;
    public javax.swing.JScrollPane previewScrollPane;
    public InitialFrameSetup() throws IOException {
        screenLabel = new JLabel();
        preview = new JScrollPane();
        previewLabel = new JLabel();
        previewScrollPane = new javax.swing.JScrollPane();
        Screen.previewFrame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        previewScrollPane.setViewportView(previewLabel);

        JScrollBar previewScrollPaneHorizontalScrollBar = previewScrollPane.getHorizontalScrollBar();
        previewScrollPaneHorizontalScrollBar.setUnitIncrement(20);

        InputMap previewScrollPaneHorizontalScrollBarInputMap = previewScrollPaneHorizontalScrollBar.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW );
        previewScrollPaneHorizontalScrollBarInputMap.put( KeyStroke.getKeyStroke( "RIGHT" ), "positiveUnitIncrement" );
        previewScrollPaneHorizontalScrollBarInputMap.put( KeyStroke.getKeyStroke( "LEFT" ),"negativeUnitIncrement");
        JScrollBar previewScrollPaneVerticalScrollBar = previewScrollPane.getVerticalScrollBar();
        previewScrollPaneVerticalScrollBar.setUnitIncrement(20);

        InputMap previewScrollPaneVerticalScrollBarInputMap = previewScrollPaneVerticalScrollBar.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW );
        previewScrollPaneVerticalScrollBarInputMap.put( KeyStroke.getKeyStroke( "DOWN" ), "positiveUnitIncrement" );
        previewScrollPaneVerticalScrollBarInputMap.put( KeyStroke.getKeyStroke( "UP" ), "negativeUnitIncrement" );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(Screen.previewFrame.getContentPane());
        Screen.previewFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(previewScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(previewScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
        //Screen.preview_frame.pack();

        File file = new File(Screen.config.get("white"));
        Screen.bufferedImageWhite = ImageIO.read(file);
        whitecopy = new BufferedImage(
                                    Screen.bufferedImageWhite.getWidth(),
                                    Screen.bufferedImageWhite.getHeight(),
                                    Screen.bufferedImageWhite.getType());
        previewLabel = new JLabel(new ImageIcon(whitecopy));
        previewLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        previewLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        preview.setViewportView(previewLabel);

        jScrollPane1 = new javax.swing.JScrollPane();
        JScrollBar jScrollPane1HorizontalScrollBar = jScrollPane1.getHorizontalScrollBar();
        jScrollPane1HorizontalScrollBar.setUnitIncrement(20);

        InputMap jScrollPane1HorizontalScrollBarInputMap = jScrollPane1HorizontalScrollBar.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW );
        jScrollPane1HorizontalScrollBarInputMap.put( KeyStroke.getKeyStroke( "RIGHT" ), "positiveUnitIncrement" );
        jScrollPane1HorizontalScrollBarInputMap.put( KeyStroke.getKeyStroke( "LEFT" ),"negativeUnitIncrement");
        JScrollBar jScrollPane1VerticalScrollBar = jScrollPane1.getVerticalScrollBar();
        jScrollPane1VerticalScrollBar.setUnitIncrement(20);

        InputMap jScrollPane1VerticalScrollBarInputMap = jScrollPane1VerticalScrollBar.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW );
        jScrollPane1VerticalScrollBarInputMap.put( KeyStroke.getKeyStroke( "DOWN" ), "positiveUnitIncrement" );
        jScrollPane1VerticalScrollBarInputMap.put( KeyStroke.getKeyStroke( "UP" ), "negativeUnitIncrement" );

        contentPane = Screen.mainFrame.getContentPane();
    }
}
