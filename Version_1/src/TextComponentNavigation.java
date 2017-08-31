import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 *
 */
public class TextComponentNavigation extends JPanel
{
    private JTextArea textArea;
    private CustomNavigationFilter navigationFilter;
    private int move;
 
    public TextComponentNavigation()
    {
        setLayout( new BorderLayout() );
 
        JButton button = new JButton("Add Move");
        button.setFocusable( false );
        button.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                textArea.append(move + ": move" + move++ + " ");
                navigationFilter.addCaretStop(textArea.getDocument().getLength());
                int position = textArea.getDocument().getLength();
 
                if (textArea.getCaretPosition() != position)
                    textArea.setCaretPosition( position );
            }
        });
 
 
        textArea = new JTextArea(5, 40);
        textArea.setEditable( false );
        textArea.setLineWrap(true);
        navigationFilter = new CustomNavigationFilter( textArea );
 
        DefaultCaret caret = new DefaultCaret()
        {
            public void focusGained(FocusEvent e)
            {
                setVisible(true);
            }
            public void focusLost(FocusEvent e) {}
        };
        caret.setVisible(true);
        caret.setBlinkRate( UIManager.getInt("TextArea.caretBlinkRate") );
        textArea.setCaret( caret );
 
        textArea.addCaretListener( new CaretListener()
        {
            public void caretUpdate(CaretEvent e)
            {
        //        System.out.println("Now at position: " + textArea.getCaretPosition());
            }
        });
 
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
    }
 
    class CustomNavigationFilter extends NavigationFilter
        implements MouseListener
    {
        private JTextComponent component;
 
        private List<Integer> caretStops = new ArrayList<Integer>();
        private boolean isMousePressed = false;
        private int mouseDot = -1;
        private int lastDot = -1;
 
        public CustomNavigationFilter(JTextComponent component)
        {
            this.component = component;
            this.component.setNavigationFilter( this );
            this.component.addMouseListener( this );
        }
 
        /*
         *  Override for normal caret movement
         */
        public void setDot(NavigationFilter.FilterBypass fb, int dot, Position.Bias bias)
        {
            // Moving forwards in the Document
 
            if (dot > lastDot)
            {
                dot = getForwardDot(dot);
                super.setDot(fb, dot, bias);
            }
            else  // Moving backwards in the Document
            {
                dot = getBackwardDot(dot);
                super.setDot(fb, dot, bias);
            }
 
            lastDot = dot;
        }
 
        /*
         *  Override for text selection as the caret is moved
         *
         *  Note: since you have removed the key bindings and probably the MouseListener
         *  from the text area you don't need to override this method or use the
         *  MouseListener.
         */
        public void moveDot(NavigationFilter.FilterBypass fb, int dot, Position.Bias bias)
        {
            //  The mouse dot is used when dragging the mouse to prevent flickering
 
            lastDot = isMousePressed ? mouseDot : lastDot;
 
            //  Moving forwards in the Document
 
            if (dot > lastDot)
            {
                lastDot = dot;
                dot = getForwardDot(dot);
                super.moveDot(fb, dot, bias);
            }
            else  //  Moving backwards in the Document
            {
                lastDot = dot;
                dot = getBackwardDot(dot);
                super.moveDot(fb, dot, bias);
            }
 
            lastDot = dot;
        }
 
        /*
         *  Attempting to move the caret forward in the Document. Skip forward
         *  when we attempt to position the caret at a protected offset.
         */
        private int getForwardDot(int dot)
        {
            for (Integer position : caretStops)
            {
                if (position >= dot)
                    return position;
            }
 
            return component.getDocument().getLength();
        }
 
        /*
         *  Attempting to move the caret forward in the Document. Skip forward
         *  when we attempt to position the caret at a protected offset.
         */
        private int getBackwardDot(int dot)
        {
            for (int i = caretStops.size() - 1; i >= 0; i--)
            {
                Integer position = caretStops.get(i);
 
                if (position <= dot)
                    return position;
            }
 
            return 0;
        }
 
        public void addCaretStop(int position)
        {
            caretStops.add( position );
        }
 
    //  Implement the MouseListener
 
        public void mousePressed( MouseEvent e )
        {
            //  Track the caret position so it is easier to determine whether
            //  we are moving forwards/backwards when dragging the mouse.
 
            mouseDot = component.getCaretPosition();
            isMousePressed = true;
        }
 
        public void mouseReleased( MouseEvent e )
        {
            isMousePressed = false;
        }
 
        public void mouseEntered( MouseEvent e ) {}
        public void mouseExited( MouseEvent e ) {}
        public void mouseClicked( MouseEvent e ) {}
 
    }
 
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.add( new TextComponentNavigation() );
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }
}