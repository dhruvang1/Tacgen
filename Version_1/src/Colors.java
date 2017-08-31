import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Colors extends JPanel
                               implements ActionListener,
                                          ChangeListener {
//	JColorChooser tcc;

    public Colors() {}

    public void actionPerformed(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(
                                       Colors.this,
                                       "Choose Region Color",
                                       Screen.allControlsAndListeners.jSelectedColor.getBackground());
        if (newColor != null) {
                Screen.allControlsAndListeners.jSelectedColor.setBackground(newColor);
                Screen.currentColor = newColor;
        }
    }

    public void stateChanged(ChangeEvent e) {
//        Color newColor = tcc.getColor();
//        Screen.a1.selected_color.setBackground(newColor);
        
    }

}