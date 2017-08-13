import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

public class Zoom {
    public static void main(String[] args) throws MalformedURLException, IOException {
        JFrame frame = new JFrame();
        Box box = new Box(BoxLayout.Y_AXIS);
        BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\Saurabh Nakra\\Documents\\NetBeansProjects\\BANA_28_05\\Samples\\10_18_color.png"));
        ImageView imageViewPanel = new ImageView(bufferedImage);

        box.add(imageViewPanel);
        frame.add(box);
        // frame.pack();
        frame.getContentPane().add(imageViewPanel.getSlider(),"North");
        frame.getContentPane().add(new JScrollPane(imageViewPanel));
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

@SuppressWarnings("serial")
class ImageView extends JComponent {

    double scale;
    int panelWidth;
    int panelHeight;
    int imageWidth;
    int imageHeight;
    private BufferedImage image;
    private AffineTransform paintXfrm;
    private Point mouseDownCoord;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        try {
            paintXfrm = graphics2D.getTransform(); //clones current graphics2D transform
            paintXfrm.invert(); //sets affine transorm to its own inverse, matrix inversion
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            panelWidth = getWidth(); //panel width
            panelHeight = getHeight();// panel height
            double x = (panelWidth - scale * imageWidth)/2;
            double y = (panelHeight - scale * imageHeight)/2;
            System.out.println("x,y: "+x+" "+y);
            //x,y changes only due to scale
            //each graphics2D component has its own transform
            graphics2D.translate(x,y); // this sets origin for the graphics2D component at (x,y)
            graphics2D.scale(scale, scale); //scales xfrm
            // graphics2D.transform(xfrm); //let current transform of graphics2D be C, then this line does C = C(xfrm), basically composition of transforms
            paintXfrm.concatenate(graphics2D.getTransform()); // paintXfrm = inverse(initial graphics2D transform) * Current graphics2D transform
            graphics2D.drawImage(image,0,0,this); //renders image
            //none of the above operation change the image variable
        } catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
        }
    }

    private Hashtable getLabelTable(int min, int max, int inc) {
        Hashtable<Integer,JLabel> table = new Hashtable<>();
        for(int j = min; j <= max; j += inc) {
            String s = String.format("%.2f", (j+4)/20.0);
            table.put(Integer.valueOf(j), new JLabel(s));
        }
        return table;
    }

    public JSlider getSlider() {
        int min = 1, max = 36, inc = 5;
        final JSlider slider = new JSlider(min, max, 16);
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setLabelTable(getLabelTable(min, max, inc));
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = slider.getValue();
                // System.out.println("value: "+ value);
                scale = (value+4)/20.0;
                // System.out.println("scale: "+ scale);
                revalidate();
                repaint();
            }
        });
        return slider;
    }

    ImageView(final BufferedImage image) {
        scale = 1.0;
        this.image = image;
        // this.xfrm = xfrm;
        imageWidth = image.getWidth(); //image width
        imageHeight = image.getHeight();//image height
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    mouseDownCoord = e.getPoint();
                    paintXfrm.inverseTransform(mouseDownCoord, mouseDownCoord);
                    System.out.println("in mouse pressed: "+mouseDownCoord);
                } catch (NoninvertibleTransformException ex) {
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseDownCoord = null;
            }
        });
    }

}

