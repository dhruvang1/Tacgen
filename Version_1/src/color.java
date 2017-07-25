import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class color extends JPanel
                               implements ActionListener,
                                          ChangeListener {
	JColorChooser tcc;
	
    public color() {
        
    }

    public void actionPerformed(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(
                                       color.this,
                                       "Choose Region Color",
                                       SCR.a1.Selected_color.getBackground());
        if (newColor != null) {
                SCR.a1.Selected_color.setBackground(newColor);
                SCR.current_color = newColor;
        }
    }

    public void stateChanged(ChangeEvent e) {
//        Color newColor = tcc.getColor();
//        SCR.a1.selected_color.setBackground(newColor);
        
    }

}