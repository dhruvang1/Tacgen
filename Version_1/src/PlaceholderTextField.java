import java.awt.*;
import javax.swing.*;
import javax.swing.text.Document;

public class PlaceholderTextField extends JTextField {

    public static void main(final String[] args) {
        final PlaceholderTextField placeholderTextField = new PlaceholderTextField("");
        placeholderTextField.setColumns(20);
        placeholderTextField.setPlaceholder("All your base are belong to us!");
        final Font font = placeholderTextField.getFont();
        placeholderTextField.setFont(new Font(font.getName(), font.getStyle(), 30));
        JOptionPane.showMessageDialog(null, placeholderTextField);
    }

    private String placeholder;

    public PlaceholderTextField() {}

    public PlaceholderTextField(final Document pDoc,
                                final String pText,
                                final int pColumns){
        super(pDoc, pText, pColumns);
    }

    public PlaceholderTextField(final int pColumns) {
        super(pColumns);
    }

    public PlaceholderTextField(final String pText) {
        super(pText);
    }

    public PlaceholderTextField(final String pText, final int pColumns) {
        super(pText, pColumns);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D graphics2D = (Graphics2D) pG;
        graphics2D.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(getDisabledTextColor());
        graphics2D.drawString(placeholder, getInsets().left, pG.getFontMetrics()
            .getMaxAscent() + getInsets().top);
    }

    private void setPlaceholder(final String s) {
        placeholder = s;
    }

}