

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class LoginRequired {

    LoginRequired() {
        JFrame jFrame = new JFrame("Login Required");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        jFrame.setSize(400, 300);
        jFrame.setResizable(false);
        jFrame.setLocationByPlatform(true);
        jFrame.setVisible(true);

        showLogin(jFrame);
    }

    private void showLogin(JFrame frame) {
        JPanel jPanel = new JPanel(new BorderLayout(5,5));
        JPanel labelsPanel = new JPanel(new GridLayout(0,1,2,2));
        labelsPanel.add(new JLabel("User Name", SwingConstants.RIGHT));
        labelsPanel.add(new JLabel("Password", SwingConstants.RIGHT));
        jPanel.add(labelsPanel, BorderLayout.WEST);

        JPanel controlsPanel = new JPanel(new GridLayout(0,1,2,2));
        JTextField username = new JTextField("Joe Blogs");
        controlsPanel.add(username);
        JPasswordField password = new JPasswordField();
        password.addAncestorListener(new RequestFocusListener(false));
        controlsPanel.add(password);
        jPanel.add(controlsPanel, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(
            frame, jPanel, "Log In", JOptionPane.YES_NO_CANCEL_OPTION);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new LoginRequired();
            }
        });
    }

}

/**
 *  Convenience class to request focus on a component.
 *
 *  When the component is added to a realized Window then component will
 *  request focus immediately, since the ancestorAdded event is fired
 *  immediately.
 *
 *  When the component is added to a non realized Window, then the focus
 *  request will be made once the window is realized, since the
 *  ancestorAdded event will not be fired until then.
 *
 *  Using the default constructor will cause the listener to be removed
 *  from the component once the AncestorEvent is generated. A second constructor
 *  allows you to specify a boolean value of false to prevent the
 *  AncestorListener from being removed when the event is generated. This will
 *  allow you to reuse the listener each time the event is generated.
 */
class RequestFocusListener implements AncestorListener
{
    private boolean removeListener;

    /*
     *  Convenience constructor. The listener is only used once and then it is
     *  removed from the component.
     */
    public RequestFocusListener()
    {
        this(true);
    }

    /*
     *  Constructor that controls whether this listen can be used once or
     *  multiple times.
     *
     *  @param removeListener when true this listener is only invoked once
     *                        otherwise it can be invoked multiple times.
     */
    public RequestFocusListener(boolean removeListener)
    {
        this.removeListener = removeListener;
    }

    @Override
    public void ancestorAdded(AncestorEvent e)
    {
        JComponent component = e.getComponent();
        component.requestFocusInWindow();

        if (removeListener)
            component.removeAncestorListener( this );
    }

    @Override
    public void ancestorMoved(AncestorEvent e) {}

    @Override
    public void ancestorRemoved(AncestorEvent e) {}
}