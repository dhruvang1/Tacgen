import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.Hashtable;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class AllControlsAndListeners extends JFrame {

    private Screen screen;
    public javax.swing.JMenuBar jMenuBar;
    public javax.swing.JSlider jZoomSlider;
    public javax.swing.JLabel jPreviewButton;

    private javax.swing.JMenuItem jOpenFileItem;
    private javax.swing.JMenuItem jTutorialHelpItem;
    private JButton jClearSvg;

    /*variable declaration, page wise*/

    //page-0
    private JButton start;

    //page-1
    public javax.swing.JButton jDetectText;
    public javax.swing.JButton jEraser;
    public javax.swing.JButton jSkipPage1;
    public javax.swing.JToolBar jToolbarPage1;
    public Font hindiFont;
    public Font englishFont;
    public Font bengaliFont;

    private JComboBox jComboPage1;

    //page-2
    public javax.swing.JToggleButton jSelectText;
    public javax.swing.JToggleButton jEdit;
    public javax.swing.JToggleButton jModifyLabel;
    public javax.swing.JButton jDeletePage2;
    public javax.swing.JTextField jLabel;
    public javax.swing.JButton jSavePage2;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JButton jGoBackPage2;
    public javax.swing.JButton jNextPage2;

    //page-3
    private javax.swing.JComboBox jComboPage3;
    public javax.swing.JButton jMathButton;
    public javax.swing.JButton jScienceDiagram;
    public javax.swing.JButton jGoBackPage3;
    public javax.swing.JButton jSkipPage3;

    //page-4
    public javax.swing.JToggleButton drawLine;
    public javax.swing.JToggleButton drawCircle;
    public javax.swing.JToggleButton drawArc;
    public javax.swing.JToggleButton drawPolygon;
    public javax.swing.JRadioButton polygonStart;
    public javax.swing.JRadioButton polygonEnd;
    public javax.swing.JToggleButton drawPath;
    public javax.swing.JToggleButton drawRegion;
    public javax.swing.JToggleButton drawBezier;
    public javax.swing.JRadioButton bezierStart;
    public javax.swing.JRadioButton bezierEnd;
    public javax.swing.JToggleButton jEditPage4;
    public javax.swing.JButton jDeleteButton;
    public javax.swing.JButton jGoBackPage4;
    public javax.swing.JButton jNextPage4;

    //maths parameter page 4
    private javax.swing.JLabel angleParameter;
    private javax.swing.JLabel distanceParameter;
    public javax.swing.JSlider duplicateLineDetectionByAngle;
    public javax.swing.JSlider duplicateLineDetectionByDistance;
    public javax.swing.JToolBar jToolbarPage4;
    public javax.swing.JButton jMathGoBackPage4;
    public javax.swing.JButton jMathNextPage4;

    //science parameter page 4
    private javax.swing.JLabel dilationLabel;
    private javax.swing.JLabel erosionLabel;
    private javax.swing.JLabel reduceNodesLabel;
    public javax.swing.JCheckBox dilationCheck;
    public javax.swing.JCheckBox erosionCheck;
    public javax.swing.JSlider dilationSlider;
    public javax.swing.JSlider erosionSlider;
    public javax.swing.JSlider removeNodesSlider;
    public javax.swing.JToolBar jToolbarSciencePage4;
    public javax.swing.JButton jScienceGoBackPage4;
    public javax.swing.JButton jScienceNextPage4;

    //page5
    public javax.swing.JToggleButton jFillColor;
    private javax.swing.JButton jChooseColor;
    public javax.swing.JButton jSelectedColor;
    public javax.swing.JButton jGoBackPage5;
    public javax.swing.JButton jSaveButton;

    /**
     * deselects all polygon start end radio buttons
     */
    public void deselectPolygonRadioButtons() {
        Screen.allControlsAndListeners.polygonStart.setSelected(false);
        Screen.allControlsAndListeners.polygonEnd.setSelected(false);
        Screen.allControlsAndListeners.polygonStart.setEnabled(false);
        Screen.allControlsAndListeners.polygonEnd.setEnabled(false);
    }

    public void deselectBezierRadioButtons() {
        Screen.allControlsAndListeners.bezierStart.setSelected(false);
        Screen.allControlsAndListeners.bezierEnd.setSelected(false);
        Screen.allControlsAndListeners.bezierStart.setEnabled(false);
        Screen.allControlsAndListeners.bezierEnd.setEnabled(false);
    }

    /**
     * clear temp arrays. calls deleteTemp functions for each math object
     */
    public void deleteAllTemp() {
        Screen.linesObject.deleteTemp();
        Screen.circlesObject.deleteTemp();
        Screen.arcObject.deleteTemp();
        Screen.polygonObject.deleteTemp();
        Screen.bezierObject.deleteTemp();
        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
    }

    /**
     * gets the labels for zoom index (0.25 - 2)
     * @param minLabel lowest label is (minlabel+1)/20
     * @param maxLabel max label is (maxlabel+1)/20
     * @param increment gap between two labels is increment/20
     * @return integer-> jlabel mapping
     */
    private Hashtable getLabelTable(int minLabel, int maxLabel, int increment) {
        Hashtable<Integer,JLabel> table = new Hashtable<>();
        for(int j = minLabel; j <= maxLabel; j += increment) {
            String labels = String.format("%.2f", (j+4)/20.0);
            table.put(j, new JLabel(labels));
        }
        return table;
    }

    /**
     * fetches pixels of the point clicked wrt the unzoomed screen
     * @param e mouse coordinates
     * @return mouse coordinates
     * @throws NoninvertibleTransformException
     */
    public Point getOriginalZoomedCoordinate(MouseEvent e) throws NoninvertibleTransformException{
        Point p = e.getPoint();
        Screen.zoomAffineTransform.inverseTransform(p, p);
        return p;
    }

    /**
     * initialize class variables that do not belong to any page
     */
    private void initializeMembers() {
        javax.swing.JMenu jFileMenu = new javax.swing.JMenu("File");
        javax.swing.JMenu jHelpMenu = new javax.swing.JMenu("Help");;

        jMenuBar = new javax.swing.JMenuBar();
        jMenuBar.setPreferredSize(new java.awt.Dimension(56, 30));
        jFileMenu.setBackground(Color.BLUE);
        jFileMenu.setForeground(Color.WHITE);
        jFileMenu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jFileMenu.setIconTextGap(10);
        jFileMenu.setOpaque(true);
        jFileMenu.setPreferredSize(new java.awt.Dimension(50, 25));
        jHelpMenu.setBackground(Color.BLUE);
        jHelpMenu.setForeground(Color.WHITE);
        jHelpMenu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jHelpMenu.setIconTextGap(10);
        jHelpMenu.setOpaque(true);
        jHelpMenu.setPreferredSize(new java.awt.Dimension(60, 25));
        jOpenFileItem = new javax.swing.JMenuItem("Open");
        jOpenFileItem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jOpenFileItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jTutorialHelpItem = new javax.swing.JMenuItem("Tutorial");
        jTutorialHelpItem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTutorialHelpItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        JButton jGenerateSvg = new JButton("SVG");
        jClearSvg = new JButton("CLEAR");
        jPreviewButton = new JLabel(new javax.swing.ImageIcon(Screen.config.get("hover_image")));
        jPreviewButton.setToolTipText("See progress in preview pane");
        //default
        jFileMenu.add(jOpenFileItem);
        jHelpMenu.add(jTutorialHelpItem);
        jMenuBar.add(jFileMenu);
        jMenuBar.add(jHelpMenu);
        
        jZoomSlider = new javax.swing.JSlider();
        jZoomSlider.setMajorTickSpacing(5);
        jZoomSlider.setMaximum(36);
        jZoomSlider.setMinimum(1);
        jZoomSlider.setMinorTickSpacing(1);
        jZoomSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        jZoomSlider.setLabelTable(getLabelTable(1, 36, 5));
        jZoomSlider.setPaintLabels(true);
        jZoomSlider.setPaintTicks(true);
        jZoomSlider.setValue(16);
    }

    /**
     * initialize listeners for {@link #initializeMembers()}
     */
    private void initializeListeners() {
        Screen.mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (!Screen.currentFile.getName().contentEquals("tacgen.jpg")) {
                    String optionButtons[] = {"Yes", "No", "Cancel"};
                    int promptResult = JOptionPane.showOptionDialog(null, "Do you want to save any unsaved work?", "Tactile Tool", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, optionButtons, optionButtons[0]);
                    if (promptResult == JOptionPane.YES_OPTION) {
                        try {
                            Screen.svgGenerateObject.svgFile();
                            String currentFilePath = Screen.currentFile.getAbsolutePath();
                            String currentFileName = String.valueOf(Screen.currentFile.getName());
                            String newFileName = currentFileName.substring(0, currentFileName.lastIndexOf(".")) + "_1" + currentFileName.substring(currentFileName.lastIndexOf("."));
                            int index = currentFilePath.lastIndexOf("\\");
                            String newFilePath = currentFilePath.substring(0, index + 1) + newFileName;
                            File outputFile1 = new File(newFilePath);
                            outputFile1.delete();
                            File textFile = new File(Screen.currentFile.getAbsolutePath() + ".txt");
                            textFile.delete();
                            System.exit(0);
                        } catch (IOException | ScriptException | NoSuchMethodException ex) {
                            Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (promptResult == JOptionPane.NO_OPTION) {
                        String currentFilePath = Screen.currentFile.getAbsolutePath();
                        String currentFileName = String.valueOf(Screen.currentFile.getName());
                        String newFileName = currentFileName.substring(0, currentFileName.lastIndexOf(".")) + "_1" + currentFileName.substring(currentFileName.lastIndexOf("."));
                        int index = currentFilePath.lastIndexOf("\\");
                        String newFilePath = currentFilePath.substring(0, index + 1) + newFileName;
                        File outputFile1 = new File(newFilePath);
                        outputFile1.delete();
                        File textFile = new File(Screen.currentFile.getAbsolutePath() + ".txt");
                        textFile.delete();
                        File svgFile = new File(Screen.currentFile.getAbsolutePath() + ".svg");
                        svgFile.delete();
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
            }
        });
        Screen.previewFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                Screen.allControlsAndListeners.jPreviewButton.setEnabled(true);
            }
        });
        jOpenFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setPreferredSize(new Dimension(800, 600));
                FileFilter imageFilter = new FileNameExtensionFilter(
                        "Image files", ImageIO.getReaderFileSuffixes());
                jFileChooser.addChoosableFileFilter(imageFilter);
                jFileChooser.setAccessory(new ImagePreviewer(jFileChooser));
                jFileChooser.setFileFilter(imageFilter);
                File workingDirectory = new File(System.getProperty("user.dir"));
                jFileChooser.setCurrentDirectory(workingDirectory);
                int returnValue = jFileChooser.showOpenDialog(Screen.mainFrame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        File currentFile = jFileChooser.getSelectedFile();
                        Screen.currentFile = currentFile;
                        Screen.currentFileName = String.valueOf(Screen.currentFile.getName());
                        Screen.bufferedImageScreen = ImageIO.read(currentFile);
                        Screen.initialFrameSetup.screenCopy = new BufferedImage(
                                (int)(Screen.zoomScale * Screen.bufferedImageScreen.getWidth()),
                                (int)(Screen.zoomScale * Screen.bufferedImageScreen.getHeight()),
                                Screen.bufferedImageScreen.getType());
                        Screen.initialFrameSetup.screenLabel = new JLabel(new ImageIcon(Screen.initialFrameSetup.screenCopy));
                        Screen.initialFrameSetup.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                        Screen.initialFrameSetup.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);

                        Screen.mainFrame.validate();
                        Screen.mainFrame.repaint();
                        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                        Screen.mainFrame.setVisible(true);
                        Screen.allObjectReinitializer.refresh();
                        File svgFile = new File(Screen.currentFile.getAbsolutePath() + ".svg");
                        if (svgFile.exists()) {
                            String optionButtons[] = {"Yes", "No"};
                            int promptResult = JOptionPane.showOptionDialog(null, "You already have saved SVG for this file. Do you want to restore it?", "Restore", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, optionButtons, optionButtons[0]);
                            if (promptResult == JOptionPane.YES_OPTION) {
                                Screen.restoreSVG.restore();
                            } else {
                                svgFile.delete();
                            }
                        }
                        Screen.page1AutoText = new Page1AutoText();
                        Screen.mainFrame.validate();
                        Screen.mainFrame.repaint();
                        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                        //Screen.a2.screenLabel.repaint();
                        Screen.mainFrame.setVisible(true);
                    } catch (IOException ex) {
                        Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (XPathExpressionException ex) {
                        Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ScriptException ex) {
                        Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NoSuchMethodException ex) {
                        Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

        jTutorialHelpItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    File f = new File(Screen.config.get("tutorial_help"));
                    Desktop.getDesktop().browse(f.toURI());
                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

//        final MouseEvent m = null;

        jClearSvg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.allObjectReinitializer.refresh();
                    Screen.svgGenerateObject.svgFile();
                    screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                    Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();

                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ScriptException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        jZoomSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = jZoomSlider.getValue();
                Screen.zoomScale = (value+4)/20.0;
                Screen.initialFrameSetup.screenCopy = new BufferedImage(
                        (int)(Screen.zoomScale * Screen.bufferedImageScreen.getWidth()),
                        (int)(Screen.zoomScale * Screen.bufferedImageScreen.getHeight()),
                        (int)(Screen.zoomScale * Screen.bufferedImageScreen.getType()));
                Screen.initialFrameSetup.screenLabel = new JLabel(new ImageIcon(Screen.initialFrameSetup.screenCopy));
                Screen.initialFrameSetup.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                Screen.initialFrameSetup.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
                Screen.imageAreaListeners.add();
                //System.out.println("scale - "+Screen.zoom_scale);
                Screen.mainFrame.validate();
                Screen.mainFrame.repaint();
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
                Screen.mainFrame.setVisible(true);
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
            }
        });
    }

    /**
     * initialize class variables corresponding to Page 0
     */
    private void initializePage0Members() {
        //page-0
        start = new JButton("Start!");
    }

    /**
     * initialize listeners for {@link #initializePage0Members()}
     */
    private void initializePage0Listeners() {
        //page-0
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.page1AutoText = new Page1AutoText();
            }
        });

    }

    /**
     * initialize class variables corresponding to Page 1
     */
    private void initializePage1Members() {
        //page-1
        jDetectText = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("detect_text")));
        jDetectText.setContentAreaFilled(false);
        jDetectText.setBorderPainted(false);
        jEraser = new javax.swing.JButton("Eraser");
        jSkipPage1 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("skip_page1")));
        jSkipPage1.setContentAreaFilled(false);
        jSkipPage1.setBorderPainted(false);
        String[] languages = new String[]{"Language", "English", "Hindi","Bengali"};
        jComboPage1 = new JComboBox(languages);
        jComboPage1.setMaximumSize(new java.awt.Dimension(80, 25));
        jToolbarPage1 = new javax.swing.JToolBar();
        jDetectText.setToolTipText("Automatically detect text and go to next stage");
        jEraser.setToolTipText("Convert pixels to white");
        jSkipPage1.setToolTipText("Go to next stage without text detection");
        jToolbarPage1.setRollover(true);
        jToolbarPage1.add(jComboPage1);
    }

    /**
     * initialize listeners for {@link #initializePage1Members()}
     */
    private void initializePage1Listeners() {
        jComboPage1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lang = (String) jComboPage1.getSelectedItem();
                switch (lang) {
                    case "Hindi":
                        Screen.textExeObject.language = "hin";
                        //Screen.a1.Label.setFont(hin_font);
                        break;
                    case "English":
                        Screen.textExeObject.language = "eng";
                        //Screen.a1.Label.setFont(eng_font);
                        break;
                    case "Bengali":
                        Screen.textExeObject.language = "ben";
                        //Screen.a1.Label.setFont(eng_font);
                        break;
                    case "Language":
                        Screen.textExeObject.language = "eng";
                        //Screen.a1.Label.setFont(eng_font);
                        break;
                    default:
                        Screen.textExeObject.language = "eng";
                        //Screen.a1.Label.setFont(eng_font);
                        break;
                }
            }
        });

        jDetectText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.textExeObject.load();
                    Screen.page2ManualText = new Page2ManualText();
                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ScriptException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jSkipPage1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.page2ManualText = new Page2ManualText();
            }
        });
    }

    /**
     * initialize class variables corresponding to Page 2
     */
    private void initializePage2Members() throws FontFormatException, IOException {
        //page-2
        jSelectText = new javax.swing.JToggleButton("Select Text");
        jEdit = new javax.swing.JToggleButton("Edit");
        jModifyLabel = new javax.swing.JToggleButton("Modify Label");
        jDeletePage2 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("delete_page2")));
        jDeletePage2.setContentAreaFilled(false);
        jDeletePage2.setBorderPainted(false);
        jLabel = new javax.swing.JTextField();
        englishFont = new java.awt.Font("sansserif", 0, 24);
        hindiFont = Font.createFont(Font.PLAIN, new File(Screen.config.get("font_directory_path"))).deriveFont(Font.BOLD, 24f);
        bengaliFont = Font.createFont(Font.PLAIN, new File(Screen.config.get("ben_font_directory_path"))).deriveFont(Font.BOLD, 24f);
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsEnvironment.registerFont(hindiFont);
        graphicsEnvironment.registerFont(bengaliFont);
        //System.out.println(hin_font);
        jSavePage2 = new javax.swing.JButton("save");
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator2.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator2.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator2.setEnabled(false);

        jSeparator1.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator1.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator1.setEnabled(false);
        jGoBackPage2 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("Go_back_page2")));
        jGoBackPage2.setContentAreaFilled(false);
        jGoBackPage2.setBorderPainted(false);
        jNextPage2 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("Next_page2")));
        jNextPage2.setContentAreaFilled(false);
        jNextPage2.setBorderPainted(false);

        jSelectText.setToolTipText("Draw a box around undetected text");
        jModifyLabel.setToolTipText("Modify labels of misdetected texts and undetected text");
        jEdit.setToolTipText("Select text boxes to delete");
        jDeletePage2.setToolTipText("Delete selected text boxes");
        jGoBackPage2.setToolTipText("Revert back to previous stage");
        jNextPage2.setToolTipText("Go to next stage if all texts have been idenfied");
        jLabel.setToolTipText("Modify labels here");
    }

    /**
     * initialize listeners for {@link #initializePage2Members()}
     */
    private void initializePage2Listeners() {
        jSelectText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jSelectText.isSelected()) {
                    jModifyLabel.setSelected(false);
                    jEdit.setSelected(false);
                    jLabel.setText("");
                    jLabel.setEditable(false);
                }
            }
        });

        jModifyLabel.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    jSelectText.setSelected(false);
                    jEdit.setSelected(false);
                    if (Screen.modifyTextObject.selectedRectangle != 10000) {
                        jLabel.setEditable(true);
                        if (Screen.textboxObject.label.get(Screen.modifyTextObject.selectedRectangle).getR().contains("label -")) {
                            jLabel.setText("");
                        } else {
                            jLabel.setText(Screen.textboxObject.label.get(Screen.modifyTextObject.selectedRectangle).getR());
                        }
                    }
                } else {
                    jLabel.setText("");
                    jLabel.setEditable(false);
                }
            }
        });

        jEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jEdit.isSelected()) {
                    jLabel.setText("");
                    jLabel.setEditable(false);
                    jSelectText.setSelected(false);
                    jModifyLabel.setSelected(false);
                }
            }
        });

        jDeletePage2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.textboxObject.deleteIndices();
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
            }
        });

        jLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String th = Screen.textboxObject.label.get(Screen.modifyTextObject.selectedRectangle).getL();
                Pair<String, String> tempPair;
                if (jLabel.getText().length() == 0) {
                    Screen.label_counts++;
                    tempPair = new Pair<>(th, "label - " + Screen.label_counts);
                    Screen.textboxObject.label.set(Screen.modifyTextObject.selectedRectangle, tempPair);
                } else {
                    tempPair = new Pair<>(th, jLabel.getText());
                    Screen.textboxObject.label.set(Screen.modifyTextObject.selectedRectangle, tempPair);
                }
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
            }
        });
        jSavePage2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String th = Screen.textboxObject.label.get(Screen.modifyTextObject.selectedRectangle).getL();
                Pair<String, String> tempPair;
                if (jLabel.getText().length() == 0) {
                    Screen.label_counts++;
                    tempPair = new Pair<>(th, "label - " + Screen.label_counts);
                    Screen.textboxObject.label.set(Screen.modifyTextObject.selectedRectangle, tempPair);
                } else {
                    tempPair = new Pair<>(th, jLabel.getText());
                    Screen.textboxObject.label.set(Screen.modifyTextObject.selectedRectangle, tempPair);
                }
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
            }
        });

        jGoBackPage2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.page2ManualText.goBack();
            }
        });

        jNextPage2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.textExeObject.store();
                } catch (IOException | SAXException | ParserConfigurationException | XPathExpressionException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ScriptException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
                Screen.page3AutoMathScience = new Page3AutoMathScience();
            }
        });
    }

    /**
     * initialize class variables corresponding to Page 3
     */
    private void initializePage3Members() {
        //page-3
        jComboPage3 = new javax.swing.JComboBox();
        jMathButton = new javax.swing.JButton("Maths");
        jScienceDiagram = new javax.swing.JButton("Science");
        jGoBackPage3 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("Go_back_page3")));
        jGoBackPage3.setContentAreaFilled(false);
        jGoBackPage3.setBorderPainted(false);
        jSkipPage3 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("skip_page3")));
        jSkipPage3.setContentAreaFilled(false);
        jSkipPage3.setBorderPainted(false);
        jMathButton.setToolTipText("Detect mathmatical figures in image for maths images");
        jScienceDiagram.setToolTipText("Detect different regions in image for science diagrams");
        jGoBackPage3.setToolTipText("Revert to previous stage");
        jSkipPage3.setToolTipText("Go to next stage");
    }

    /**
     * initialize listeners for {@link #initializePage3Members()}
     */
    private void initializePage3Listeners() {
        jMathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.maths_science_exe.load();
                    Screen.page4MathParameter = new Page4MathParameter();
                    Screen.mainFrame.validate();
                    Screen.mainFrame.repaint();
                    screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                    Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
                    Screen.mainFrame.setVisible(true);
                    screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                    Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jScienceDiagram.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.maths_science_exe.load_science();
//                    Screen.page4ManualMathScience = new Page4ManualMathScience();
                    Screen.page4ScienceParameter = new Page4ScienceParameter();
                    Screen.mainFrame.validate();
                    Screen.mainFrame.repaint();
                    screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                    Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
                    Screen.mainFrame.setVisible(true);
                    screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                    Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jGoBackPage3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.page3AutoMathScience.goBack();
                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jSkipPage3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.page4ManualMathScience = new Page4ManualMathScience();
            }
        });
    }

    /**
     * initialize class variables corresponding to Page 4
     */
    private void initializePage4Members() {
        //page-4
        drawLine = new javax.swing.JToggleButton("Line");
        drawCircle = new javax.swing.JToggleButton("Circle");
        drawArc = new javax.swing.JToggleButton("Arc");
        drawPolygon = new javax.swing.JToggleButton("Polygon");
        polygonStart = new javax.swing.JRadioButton("Start");
        polygonEnd = new javax.swing.JRadioButton("End");
        drawPath = new javax.swing.JToggleButton("Path");
        drawRegion = new javax.swing.JToggleButton("Region");
        drawBezier = new javax.swing.JToggleButton("Bezier Curve");
        bezierStart = new javax.swing.JRadioButton("Start");
        bezierEnd = new javax.swing.JRadioButton("End");
        jEditPage4 = new javax.swing.JToggleButton("Edit");
        jDeleteButton = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("delete_page4")));
        jDeleteButton.setContentAreaFilled(false);
        jDeleteButton.setBorderPainted(false);
        jGoBackPage4 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("Go_back_page4")));
        jGoBackPage4.setContentAreaFilled(false);
        jGoBackPage4.setBorderPainted(false);
        jNextPage4 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("Next_page4")));
        jNextPage4.setContentAreaFilled(false);
        jNextPage4.setBorderPainted(false);

        drawLine.setToolTipText("Select two end points of line");
        drawCircle.setToolTipText("Select any three point of circle");
        drawArc.setToolTipText("Select three points of arc in order");
        drawPolygon.setToolTipText("Select vertices of polygon in order");
        polygonEnd.setToolTipText("Draw a polygon of selected vertices");
        drawPath.setToolTipText("Select points in order to draw a continuous path");
        drawRegion.setToolTipText("Draw a arbit shape");
        drawBezier.setToolTipText("Select points for bezier curve");
        jEditPage4.setToolTipText("Select shapes to delete");
        jDeleteButton.setToolTipText("Delete selected shapes");
        jGoBackPage4.setToolTipText("Revert back to previous stage");
        jNextPage4.setToolTipText("Go to next page if all shapes have been identified");
    }

    /**
     * initialize listeners for {@link #initializePage4Members()}
     */
    private void initializePage4Listeners() {
        
        drawLine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (drawLine.isSelected()) {
                    drawCircle.setSelected(false);
                    drawArc.setSelected(false);
                    drawPolygon.setSelected(false);
                    drawPath.setSelected(false);
                    drawRegion.setSelected(false);
                    drawBezier.setSelected(false);
                    jEditPage4.setSelected(false);
                    deselectPolygonRadioButtons();
                    deselectBezierRadioButtons();
                    deleteAllTemp();
                }
            }
        });

        drawCircle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (drawCircle.isSelected()) {
                    drawLine.setSelected(false);
                    drawArc.setSelected(false);
                    drawPolygon.setSelected(false);
                    drawPath.setSelected(false);
                    drawRegion.setSelected(false);
                    drawBezier.setSelected(false);
                    jEditPage4.setSelected(false);
                    deselectPolygonRadioButtons();
                    deselectBezierRadioButtons();
                    deleteAllTemp();
                }
            }
        });

        drawArc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (drawArc.isSelected()) {
                    drawLine.setSelected(false);
                    drawCircle.setSelected(false);
                    drawPolygon.setSelected(false);
                    drawPath.setSelected(false);
                    drawRegion.setSelected(false);
                    drawBezier.setSelected(false);
                    jEditPage4.setSelected(false);
                    deselectPolygonRadioButtons();
                    deselectBezierRadioButtons();
                    deleteAllTemp();
                }
            }
        });

        drawPolygon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (drawPolygon.isSelected()) {
                    drawLine.setSelected(false);
                    drawCircle.setSelected(false);
                    drawArc.setSelected(false);
                    drawPath.setSelected(false);
                    drawRegion.setSelected(false);
                    drawBezier.setSelected(false);
                    jEditPage4.setSelected(false);
                    deleteAllTemp();
                    polygonStart.setEnabled(true);
                    polygonEnd.setEnabled(false);
                } else {
                    deselectPolygonRadioButtons();
                }
            }
        });

        polygonStart.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    polygonEnd.setSelected(false);
                }
            }
        });

        polygonEnd.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    MouseEvent m = null;
                    Screen.polygonObject.getPolygons(m);
                } catch (NoninvertibleTransformException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        drawBezier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (drawBezier.isSelected()) {
                    drawLine.setSelected(false);
                    drawCircle.setSelected(false);
                    drawArc.setSelected(false);
                    drawPolygon.setSelected(false);
                    drawPath.setSelected(false);
                    drawRegion.setSelected(false);
                    jEditPage4.setSelected(false);
                    deleteAllTemp();
                    bezierStart.setEnabled(true);
                    bezierEnd.setEnabled(false);
                } else {
                    deselectBezierRadioButtons();
                }
            }
        });

        bezierStart.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    bezierEnd.setSelected(false);
                }
            }
        });

        bezierEnd.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    MouseEvent m = null;
                    Screen.bezierObject.getBeziers(m);
                } catch (NoninvertibleTransformException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        drawPath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (drawPath.isSelected()) {
                    drawLine.setSelected(false);
                    drawCircle.setSelected(false);
                    drawArc.setSelected(false);
                    drawPolygon.setSelected(false);
                    drawRegion.setSelected(false);
                    drawBezier.setSelected(false);
                    jEditPage4.setSelected(false);
                    deselectPolygonRadioButtons();
                    deselectBezierRadioButtons();
                    deleteAllTemp();
                }
            }
        });

        drawRegion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (drawRegion.isSelected()) {
                    drawLine.setSelected(false);
                    drawCircle.setSelected(false);
                    drawArc.setSelected(false);
                    drawPath.setSelected(false);
                    drawPolygon.setSelected(false);
                    drawBezier.setSelected(false);
                    jEditPage4.setSelected(false);
                    deselectPolygonRadioButtons();
                    deselectBezierRadioButtons();
                    deleteAllTemp();
                }
            }
        });

        jEditPage4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jEditPage4.isSelected()) {
                    drawLine.setSelected(false);
                    drawCircle.setSelected(false);
                    drawArc.setSelected(false);
                    drawPolygon.setSelected(false);
                    drawPath.setSelected(false);
                    drawRegion.setSelected(false);
                    drawBezier.setSelected(false);
                    deselectPolygonRadioButtons();
                    deselectBezierRadioButtons();
                }
            }
        });

        jDeleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.circlesObject.deleteIndices();
                Screen.linesObject.deleteLineIndices();
                Screen.arcObject.deleteIndices();
                Screen.polygonObject.deleteIndices();
                Screen.regionsObject.deleteIndices();
                Screen.pathsObject.deleteIndices();
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
            }
        });

        jGoBackPage4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.page4ManualMathScience.goBack();
                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jNextPage4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.page5ColorMapping = new Page5ColorMapping();
            }
        });

    }

    /**
     * initialize class variables (math) corresponding to Page 4
     */
    private void initializePage4MathMembers() {
        // maths parameter page 4
        distanceParameter = new javax.swing.JLabel();
        distanceParameter.setMaximumSize(new Dimension(60, 45));
        distanceParameter.setText("<html>Distance<br>parameter</html>");

        angleParameter = new javax.swing.JLabel();
        angleParameter.setMaximumSize(new Dimension(60, 45));
        angleParameter.setText("<html>Angle<br>Parameter</html>");

        duplicateLineDetectionByDistance = new javax.swing.JSlider();
        duplicateLineDetectionByDistance.setMajorTickSpacing(10);
        duplicateLineDetectionByDistance.setMaximum(50);
        duplicateLineDetectionByDistance.setMinimum(10);
        duplicateLineDetectionByDistance.setMinorTickSpacing(5);
        duplicateLineDetectionByDistance.setPaintLabels(true);
        duplicateLineDetectionByDistance.setPaintTicks(true);
        duplicateLineDetectionByDistance.setSnapToTicks(true);
        duplicateLineDetectionByDistance.setValue(10);
        duplicateLineDetectionByDistance.setMaximumSize(new java.awt.Dimension(100, 45));

        duplicateLineDetectionByAngle = new javax.swing.JSlider();
        duplicateLineDetectionByAngle.setMajorTickSpacing(2);
        duplicateLineDetectionByAngle.setMaximum(10);
        duplicateLineDetectionByAngle.setMinorTickSpacing(1);
        duplicateLineDetectionByAngle.setPaintLabels(true);
        duplicateLineDetectionByAngle.setPaintTicks(true);
        duplicateLineDetectionByAngle.setSnapToTicks(true);
        duplicateLineDetectionByAngle.setValue(5);
        duplicateLineDetectionByAngle.setMaximumSize(new java.awt.Dimension(100, 45));

        jToolbarPage4 = new javax.swing.JToolBar();
        jToolbarPage4.add(distanceParameter);
        jToolbarPage4.add(duplicateLineDetectionByDistance);
        jToolbarPage4.add(angleParameter);
        jToolbarPage4.add(duplicateLineDetectionByAngle);

        jMathGoBackPage4 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("Go_back_parameter_page4")));
        jMathGoBackPage4.setContentAreaFilled(false);
        jMathGoBackPage4.setBorderPainted(false);

        jMathNextPage4 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("Next_parameter_page4")));
        jMathNextPage4.setContentAreaFilled(false);
        jMathNextPage4.setBorderPainted(false);

    }

    /**
     * initialize listeners for {@link #initializePage4MathMembers()}
     */
    private void initializePage4MathListeners() {
        // maths parameter page 4
        jMathGoBackPage4.setToolTipText("Revert back to previous stage");
        jMathNextPage4.setToolTipText("Go to next page if you see best detection result");
        duplicateLineDetectionByDistance.setToolTipText("<html>A parameter to remove nearly detected duplicate lines."
                + "<br>Duplicate lines with gap less than this value "
                + "<br>will be removed. Increase this "
                + "<br>if duplicate lines are detected</html>");
        duplicateLineDetectionByAngle.setToolTipText("<html>A parameter to remove nearly detected duplicate lines."
                + "<br>Duplicate lines with angle less than this value"
                + "<br>will be removed. Increase this"
                + "<br>if duplicate lines are detected</html>");

        duplicateLineDetectionByDistance.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (!duplicateLineDetectionByDistance.getValueIsAdjusting()) {
                    try {
                        Screen.maths_science_exe.load();
                        Screen.mainFrame.validate();
                        Screen.mainFrame.repaint();
                        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
                        Screen.mainFrame.setVisible(true);
                        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
                    } catch (IOException ex) {
                        Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        duplicateLineDetectionByDistance.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }

            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                //dup_line_dist_sliderAncestorResized(evt);
            }
        });

        duplicateLineDetectionByAngle.addChangeListener(new ChangeListener() {

        @Override
        public void stateChanged(ChangeEvent e) {
            if (!duplicateLineDetectionByAngle.getValueIsAdjusting()) {
                try {
                    Screen.maths_science_exe.load();
                    Screen.mainFrame.validate();
                    Screen.mainFrame.repaint();
                    screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                    Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
                    Screen.mainFrame.setVisible(true);
                    screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                    Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        });

        jMathGoBackPage4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.page4MathParameter.goBack();
                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jMathNextPage4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.page4ManualMathScience = new Page4ManualMathScience();
                Screen.mainFrame.validate();
                Screen.mainFrame.repaint();
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
                Screen.mainFrame.setVisible(true);
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
            }
        });
    }

    /**
     * initialize class variables (science) corresponding to Page 4
     */
    private void initializePage4ScienceMembers(){
        //science parameters page 4
        dilationLabel = new javax.swing.JLabel();
        dilationLabel.setMaximumSize(new Dimension(60, 45));
        dilationLabel.setText("<html>Dilation<br>parameter</html>");

        erosionLabel = new javax.swing.JLabel();
        erosionLabel.setMaximumSize(new Dimension(60, 45));
        erosionLabel.setText("<html>Erosion<br>Parameter</html>");

        dilationCheck = new javax.swing.JCheckBox();
        dilationCheck.setSelected(false);
        erosionCheck = new javax.swing.JCheckBox();
        erosionCheck.setSelected(false);

        dilationSlider = new javax.swing.JSlider();
        dilationSlider.setMajorTickSpacing(2);
        dilationSlider.setMinimum(3);
        dilationSlider.setMaximum(9);
        dilationSlider.setValue(3);
        dilationSlider.setPaintLabels(true);
        dilationSlider.setPaintTicks(true);
        dilationSlider.setSnapToTicks(true);
        dilationSlider.setEnabled(false);
        dilationSlider.setMaximumSize(new java.awt.Dimension(100, 45));

        erosionSlider = new javax.swing.JSlider();
        erosionSlider.setMajorTickSpacing(2);
        erosionSlider.setMinimum(3);
        erosionSlider.setMaximum(9);
        erosionSlider.setValue(3);
        erosionSlider.setPaintLabels(true);
        erosionSlider.setPaintTicks(true);
        erosionSlider.setSnapToTicks(true);
        erosionSlider.setEnabled(false);
        erosionSlider.setMaximumSize(new java.awt.Dimension(100, 45));

        jToolbarSciencePage4 = new javax.swing.JToolBar();
        jToolbarSciencePage4.add(dilationCheck);
        jToolbarSciencePage4.add(dilationLabel);
        jToolbarSciencePage4.add(dilationSlider);
        jToolbarSciencePage4.add(erosionCheck);
        jToolbarSciencePage4.add(erosionLabel);
        jToolbarSciencePage4.add(erosionSlider);

        jScienceGoBackPage4 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("Go_back_parameter_page4")));
        jScienceGoBackPage4.setContentAreaFilled(false);
        jScienceGoBackPage4.setBorderPainted(false);

        jScienceNextPage4 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("Next_parameter_page4")));
        jScienceNextPage4.setContentAreaFilled(false);
        jScienceNextPage4.setBorderPainted(false);
    }

    /**
     * initialize listeners for {@Link #initializePage4ScienceMembers()}
     */
    private void initializePage4ScienceListeners(){
        jScienceGoBackPage4.setToolTipText("Revert back to previous stage");
        jScienceNextPage4.setToolTipText("Go to next page if you see best detection result");
        dilationSlider.setToolTipText("<html>A parameter to select mask size for dilation</html>");
        duplicateLineDetectionByAngle.setToolTipText("<html>A parameter to select mask size for erosion</html>");


        jScienceGoBackPage4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.page4ScienceParameter.goBack();
                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jScienceNextPage4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.page4ManualMathScience = new Page4ManualMathScience();
                Screen.mainFrame.validate();
                Screen.mainFrame.repaint();
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);  //Screen.a2.screenLabel.repaint();
                Screen.mainFrame.setVisible(true);
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
            }
        });

    }

    /**
     * initialize class variables corresponding to Page 5
     */
    private void initializePage5Members() {
        //page5
        jFillColor = new javax.swing.JToggleButton("Fill Color");
        jChooseColor = new javax.swing.JButton("Choose");
        jSelectedColor = new javax.swing.JButton("");
        jGoBackPage5 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("Go_back_page5")));
        jGoBackPage5.setContentAreaFilled(false);
        jGoBackPage5.setBorderPainted(false);
        jSaveButton = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("Save_page5")));
        jSaveButton.setContentAreaFilled(false);
        jSaveButton.setBorderPainted(false);
        
    }

    /**
     * initialize listeners for {@link #initializePage5Members()}
     */
    private void initializePage5Listeners() {
        // page-5
        jFillColor.setToolTipText("Add colours to shapes");
        jChooseColor.setToolTipText("Choose a colour");
        jGoBackPage5.setToolTipText("Revert to previous stage");
        jSelectedColor.setToolTipText("Current Colour");
        jSelectedColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.colorObject.actionPerformed(e);
            }
        });

        jGoBackPage5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.page5ColorMapping.goBack();
                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jSaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.svgGenerateObject.svgFile();
                    String currentFilePath = Screen.currentFile.getAbsolutePath();
                    String currentFileName = String.valueOf(Screen.currentFile.getName());
                    String newFileName = currentFileName.substring(0, currentFileName.lastIndexOf(".")) + "_1" + currentFileName.substring(currentFileName.lastIndexOf("."));
                    int index = currentFilePath.lastIndexOf("\\");
                    String newFilePath = currentFilePath.substring(0, index + 1) + newFileName;
                    File outputFile = new File(newFilePath);
                    outputFile.delete();
                    File textFile = new File(Screen.currentFile.getAbsolutePath() + ".txt");
                    textFile.delete();
                    File svgFile = new File(Screen.currentFile.getAbsolutePath() + ".svg");
                    if(currentFilePath.indexOf(".")>0)
                    	currentFilePath = currentFilePath.substring(0,currentFilePath.lastIndexOf("."));
                    File tempFile = new File(currentFilePath+".svg");

                    BufferedReader reader = new BufferedReader(new FileReader(svgFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                    String currentLine;
                    while((currentLine = reader.readLine()) != null) {
                        // trim newline when comparing with lineToRemove
                        if(currentLine.length()>5 && "<rect".equals(currentLine.substring(0,5))) continue;
                        writer.write(currentLine + System.getProperty("line.separator"));
                    }
                    writer.close(); 
                    reader.close(); 
                    svgFile.delete();
                    svgFile=tempFile;
                    
//                    svgFile.renameTo(currentFilePath.substring(0, index + 1) +currentFileName+".svg");
                    Desktop.getDesktop().browse(svgFile.toURI());
                    Screen.zoomScale = 1;
                    Screen.allControlsAndListeners.jZoomSlider.setValue(16);
                    Screen.page0OpenImage = new Page0OpenImage();
                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ScriptException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /*Constructor to call all initializing methods described above*/
    public AllControlsAndListeners() throws FontFormatException, IOException {
        screen = new Screen(0);
        initializeMembers();
        initializePage0Members();
        initializePage1Members();
        initializePage2Members();
        initializePage3Members();
        initializePage4Members();
        initializePage4MathMembers();
        initializePage4ScienceMembers();
        initializePage5Members();

        initializeListeners();
        initializePage0Listeners();
        initializePage1Listeners();
        initializePage2Listeners();
        initializePage3Listeners();
        initializePage4Listeners();
        initializePage4MathListeners();
        initializePage4ScienceListeners();
        initializePage5Listeners();
    }
}
