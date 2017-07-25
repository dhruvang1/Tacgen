
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;

public class text {
   private Frame mainFrame;
   private Label headerLabel;
   private Label statusLabel;
   private Panel controlPanel;
   private TextField tf;

   public text(){
      prepareGUI();
   }
   
private static String getLabelText(AttributedString attributedLabel) {
    AttributedCharacterIterator.Attribute[] attributes= new AttributedCharacterIterator.Attribute[1];
    attributes[0] = TextAttribute.FONT;
    CharacterIterator iterator = attributedLabel.getIterator(attributes);
    int length = iterator.getEndIndex();
    StringBuilder builder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
        builder.append(iterator.setIndex(i));
    }

    return builder.toString();
}

   public static void main(String[] args){
       Font font = new Font("LucidaSans", Font.PLAIN, 14);
       AttributedString atString= new AttributedString("Example text string");
       atString.addAttribute(TextAttribute.FONT, font);
       System.out.println(getLabelText(atString));
   }

   private void prepareGUI(){
      mainFrame = new Frame("Java AWT Examples");
      mainFrame.setSize(400,400);
      mainFrame.setLayout(new GridLayout(3, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
   
      headerLabel = new Label();
      headerLabel.setAlignment(Label.CENTER);
      statusLabel = new Label();        
      statusLabel.setAlignment(Label.CENTER);
      statusLabel.setSize(350,100);

      controlPanel = new Panel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }

   public void showTextListenerDemo(final TextField tf){
      headerLabel.setText("Listener in action: TextListener");      

//      tf  = new TextField(10);

      tf.addTextListener(new CustomTextListener());
      Button okButton = new Button("OK");
      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            statusLabel.setText("Entered text: " 
            + tf.getText()); 
            
         }
      });

      controlPanel.add(tf);
      controlPanel.add(okButton);    
      mainFrame.setVisible(true);
//      return tf.getText();
   }

   class CustomTextListener implements TextListener {
      public void textValueChanged(TextEvent e) {
         statusLabel.setText("Entered text: " + tf.getText());               
      }
   }
}