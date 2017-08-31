import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

/**
 * Class to select file choosing dialog
 */
public class FileChooserTest
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
         {
            public void run()
            {
               ImageViewerFrame imageViewerFrame = new ImageViewerFrame();
               imageViewerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               imageViewerFrame.setVisible(true);
//               imageViewerFrame.setExtendedState(imageViewerFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            }
         });
   }
}
 
/**
 * A frame that has a menu for loading an image and a display area for the loaded image.
 */
class ImageViewerFrame extends JFrame
{
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 400;

    private JLabel label;
    private JFileChooser chooser;

   public ImageViewerFrame()
   {
      setTitle("FileChooserTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
 
      // set up menu bar
      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      menuBar.setPreferredSize(new Dimension(100, 50));
      JMenu menu = new JMenu("File");
      
      
      menuBar.add(menu);
      
      JMenuItem openItem = new JMenuItem("Open");
      menu.add(openItem);
      openItem.addActionListener(new FileOpenListener());
 
      JMenuItem exitItem = new JMenuItem("Exit");
      menu.add(exitItem);
      exitItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               System.exit(0);
            }
         });
 
      // use a label to display the images
      label = new JLabel();
      add(label);
 
      // set up file chooser
      File root = new File (".");
      FileSystemView fileSystemView = new SingleRootFileSystemView(root);
      chooser = new JFileChooser(fileSystemView);
 
      // accept all image files ending with .jpg, .jpeg, .gif
      /*
      final ExtensionFileFilter fileNameExtensionFilter = new ExtensionFileFilter();
      fileNameExtensionFilter.addExtension("jpg");
      fileNameExtensionFilter.addExtension("jpeg");
      fileNameExtensionFilter.addExtension("gif");
      fileNameExtensionFilter.setDescription("Image files");
      */
      FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "gif");
      chooser.setFileFilter(fileNameExtensionFilter);
 
      chooser.setAccessory(new ImagePreviewer(chooser));
 
      chooser.setFileView(new FileIconView(fileNameExtensionFilter, new ImageIcon("palette.gif")));
   }
 
   /**
    * This is the listener for the File->Open menu item.
    */
   private class FileOpenListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         chooser.setCurrentDirectory(new File("."));
 
         // show file chooser dialog
         int result = chooser.showOpenDialog(ImageViewerFrame.this);
 
         // if image file accepted, set it as icon of the label
         if (result == JFileChooser.APPROVE_OPTION)
         {
            String name = chooser.getSelectedFile().getPath();
            label.setIcon(new ImageIcon(name));
         }
      }
   }
}
 
/**
 * A file view that displays an icon for all files that match a file filter.
 */
class FileIconView extends FileView
{
   /**
    * Constructs a FileIconView.
    * @param aFilter a file filter--all files that this filter accepts will be shown with the icon.
    * @param anIcon--the icon shown with all accepted files.
    */
   public FileIconView(FileFilter aFilter, Icon anIcon)
   {
      filter = aFilter;
      icon = anIcon;
   }
 
   public Icon getIcon(File f)
   {
      if (!f.isDirectory() && filter.accept(f)) return icon;
      else return null;
   }
 
   private FileFilter filter;
   private Icon icon;
}
 
/**
 * A file chooser accessory that previews images.
 */
class ImagePreviewer extends JLabel
{
   /**
    * Constructs an ImagePreviewer.
    * @param chooser the file chooser whose property changes trigger an image change in this
    * previewer
    */
   public ImagePreviewer(JFileChooser chooser)
   {
      setPreferredSize(new Dimension(400, 400));
      setBorder(BorderFactory.createEtchedBorder());
 
      chooser.addPropertyChangeListener(new PropertyChangeListener()
         {
            public void propertyChange(PropertyChangeEvent event)
            {
               if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(event.getPropertyName()))
               {
                  // the user has selected a new file
                  File file = (File) event.getNewValue();
                  if (file == null)
                  {
                     setIcon(null);
                     return;
                  }
 
                  // read the image into an imageIcon
                  ImageIcon imageIcon = new ImageIcon(file.getPath());
 
                  // if the imageIcon is too large to fit, scale it
                  if (imageIcon.getIconWidth() > getWidth()) imageIcon = new ImageIcon(imageIcon.getImage()
                        .getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT));
 
                  setIcon(imageIcon);
               }
            }
         });
   }
}
 
/**
 *  A FileSystemView class that limits the file selections to a single root.
 *
 *  When used with the JFileChooser component the user will only be able to
 *  traverse the directories contained within the specified root fill.
 *
 *  The "Look In" combo box will only display the specified root.
 *
 *  The "Up One Level" button will be disable when at the root.
 *
 */
class SingleRootFileSystemView extends FileSystemView
{
    private File root;
    private File[] roots = new File[1];
 
    public SingleRootFileSystemView(File root)
    {
        super();
        this.root = root;
        roots[0] = root;
    }
 
    @Override
    public File createNewFolder(File containingDir)
    {
        File folder = new File(containingDir, "New Folder");
        folder.mkdir();
        return folder;
    }
 
    @Override
    public File getDefaultDirectory()
    {
        return root;
    }
 
    @Override
    public File getHomeDirectory()
    {
        return root;
    }
 
    @Override
    public File[] getRoots()
    {
        return roots;
    }
}