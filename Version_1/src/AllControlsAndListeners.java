
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
import java.io.File;
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

    int r = 0;
    Screen screen;
    javax.swing.JMenuBar jMenuBar;
    javax.swing.JMenu jFileMenu;
    javax.swing.JMenuItem jOpenFileItem;
    javax.swing.JMenu jHelpMenu;
    javax.swing.JMenuItem jTutorialHelpItem;
    javax.swing.JSlider jZoomSlider;
    javax.swing.JLabel jPreviewButton;
    JButton jGenerateSvg;
    JButton jClearSvg;

    //page-0
    JButton start;

    //page-1
    javax.swing.JButton jDetectText;
    javax.swing.JButton jSkipPage1;
    String[] languages;
    JComboBox jComboPage1;
    javax.swing.JToolBar jToolbarPage1;
    Font hindiFont;
    Font englishFont;
    Font bengaliFont;

    //page-2
    javax.swing.JToggleButton jSelectText;
    javax.swing.JToggleButton jEdit;
    javax.swing.JToggleButton jModifyLabel;
    javax.swing.JButton jDeletePage2;
    javax.swing.JTextField jLabel;
    javax.swing.JButton jSavePage2;
    javax.swing.JSeparator jSeparator1;
    javax.swing.JSeparator jSeparator2;
    javax.swing.JButton jGoBackPage2;
    javax.swing.JButton jNextPage2;

    //page-3
    javax.swing.JComboBox jComboPage3;
    javax.swing.JButton jMathButton;
    javax.swing.JButton jScienceDiagram;
    javax.swing.JButton jGoBackPage3;
    javax.swing.JButton jSkipPage3;

    //page-4
    javax.swing.JToggleButton drawLine;
    javax.swing.JToggleButton drawCircle;
    javax.swing.JToggleButton drawArc;
    javax.swing.JToggleButton drawPolygon;
    javax.swing.JRadioButton polygonStart;
    javax.swing.JRadioButton polygonEnd;
    javax.swing.JToggleButton drawPath;
    javax.swing.JToggleButton drawRegion;
    javax.swing.JToggleButton jEditPage4;
    javax.swing.JButton jDeleteButton;
    javax.swing.JButton jGoBackPage4;
    javax.swing.JButton jNextPage4;

    //maths parameter page 4
    javax.swing.JLabel angleParameter;
    javax.swing.JLabel distanceParameter;
    javax.swing.JSlider duplicateLineDetectionByAngle;
    javax.swing.JSlider duplicateLineDetectionByDistance;
    javax.swing.JToolBar jToolbarPage4;
    javax.swing.JButton jMathGoBackPage4;
    javax.swing.JButton jMathNextPage4;

    //page5
    javax.swing.JToggleButton jFillColor;
    javax.swing.JButton jChooseColor;
    javax.swing.JButton jSelectedColor;
    javax.swing.JButton jGoBackPage5;
    javax.swing.JButton jSaveButton;

    public void deselectRadioButtons() {
        Screen.a1.polygonStart.setSelected(false);
        Screen.a1.polygonEnd.setSelected(false);
        Screen.a1.polygonStart.setEnabled(false);
        Screen.a1.polygonEnd.setEnabled(false);
    }

    public void deleteAllTemp() {
        Screen.line_object.deleteTemp();
        Screen.circle_object.deleteTemp();
        Screen.arc_object.deleteTemp();
        Screen.polygon_object.deleteTemp();
        screen.repaint(Screen.screen, Screen.a2.screenCopy);
        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);
    }
    
    public Hashtable getLabelTable(int minLabel, int maxLabel, int increment) {
        Hashtable<Integer,JLabel> table = new Hashtable<Integer,JLabel>();
        for(int j = minLabel; j <= maxLabel; j += increment) {
            String labels = String.format("%.2f", (j+4)/20.0);
            table.put(Integer.valueOf(j), new JLabel(labels));
        }
        return table;
    }
    public Point getOriginalZoomedCoordinate(MouseEvent e) throws NoninvertibleTransformException{
        Point p = e.getPoint();
        Screen.zoom_affine_transform.inverseTransform(p, p);
        return p;
    }
    
//    public Point get_zoomed_point_coordinate(Point p) throws NoninvertibleTransformException{
//        Screen.zoom_affine_transform.transform(p, p);
//        return p;
//    }
//
    public void intializeMembers() {
        jMenuBar = new javax.swing.JMenuBar();
        jMenuBar.setPreferredSize(new java.awt.Dimension(56, 30));
        jFileMenu = new javax.swing.JMenu("File");
        jFileMenu.setBackground(Color.BLUE);
        jFileMenu.setForeground(Color.WHITE);
        jFileMenu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jFileMenu.setIconTextGap(10);
        jFileMenu.setOpaque(true);
        jFileMenu.setPreferredSize(new java.awt.Dimension(50, 25));
        jHelpMenu = new javax.swing.JMenu("Help");
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
        jGenerateSvg = new JButton("SVG");
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

    public void initializeListeners() {
        Screen.main_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (!Screen.current_file.getName().contentEquals("tacgen.jpg")) {
                    String optionButtons[] = {"Yes", "No", "Cancel"};
                    int promptResult = JOptionPane.showOptionDialog(null, "Do you want to save any unsaved work?", "Tactile Tool", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, optionButtons, optionButtons[0]);
                    if (promptResult == JOptionPane.YES_OPTION) {
                        try {
                            Screen.a13.svg_file();
                            String currentFilePath = Screen.current_file.getAbsolutePath();
                            String currentFileName = String.valueOf(Screen.current_file.getName());
                            String newFileName = currentFileName.substring(0, currentFileName.lastIndexOf(".")) + "_1" + currentFileName.substring(currentFileName.lastIndexOf("."));
                            int index = currentFilePath.lastIndexOf("\\");
                            String newFilePath = currentFilePath.substring(0, index + 1) + newFileName;
                            File outputFile1 = new File(newFilePath);
                            outputFile1.delete();
                            File textFile = new File(Screen.current_file.getAbsolutePath() + ".txt");
                            textFile.delete();
                            System.exit(0);
                        } catch (IOException | ScriptException | NoSuchMethodException ex) {
                            Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (promptResult == JOptionPane.NO_OPTION) {
                        String currentFilePath = Screen.current_file.getAbsolutePath();
                        String currentFileName = String.valueOf(Screen.current_file.getName());
                        String newFileName = currentFileName.substring(0, currentFileName.lastIndexOf(".")) + "_1" + currentFileName.substring(currentFileName.lastIndexOf("."));
                        int index = currentFilePath.lastIndexOf("\\");
                        String newFilePath = currentFilePath.substring(0, index + 1) + newFileName;
                        File outputFile1 = new File(newFilePath);
                        outputFile1.delete();
                        File textFile = new File(Screen.current_file.getAbsolutePath() + ".txt");
                        textFile.delete();
                        File svgFile = new File(Screen.current_file.getAbsolutePath() + ".html");
                        svgFile.delete();
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
            }
        });
        Screen.preview_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                Screen.a1.jPreviewButton.setEnabled(true);
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
                int returnValue = jFileChooser.showOpenDialog(Screen.main_frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        File currentFile = jFileChooser.getSelectedFile();
                        Screen.current_file = currentFile;
                        Screen.current_file_name = String.valueOf(Screen.current_file.getName());
                        Screen.screen = ImageIO.read(currentFile);
                        Screen.a2.screenCopy = new BufferedImage(
                                (int)(Screen.zoom_scale* Screen.screen.getWidth()),
                                (int)(Screen.zoom_scale* Screen.screen.getHeight()),
                                Screen.screen.getType());
                        Screen.a2.screenLabel = new JLabel(new ImageIcon(Screen.a2.screenCopy));
                        Screen.a2.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                        Screen.a2.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);

                        Screen.main_frame.validate();
                        Screen.main_frame.repaint();
                        screen.repaint(Screen.screen, Screen.a2.screenCopy);
                        Screen.main_frame.setVisible(true);
                        Screen.refresh_all.refresh();
                        File svgFile = new File(Screen.current_file.getAbsolutePath() + ".html");
                        if (svgFile.exists()) {
                            String optionButtons[] = {"Yes", "No"};
                            int promptResult = JOptionPane.showOptionDialog(null, "You already have saved SVG for this file. Do you want to restore it?", "Restore", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, optionButtons, optionButtons[0]);
                            if (promptResult == JOptionPane.YES_OPTION) {
                                Screen.restore_svg.restore();
                            } else {
                                svgFile.delete();
                            }
                        }
                        Screen.a20 = new page1_auto_text();
                        Screen.main_frame.validate();
                        Screen.main_frame.repaint();
                        screen.repaint(Screen.screen, Screen.a2.screenCopy);
                        //Screen.a2.screenLabel.repaint();
                        Screen.main_frame.setVisible(true);
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
                    Screen.refresh_all.refresh();
                    Screen.a13.svg_file();
                    screen.repaint(Screen.screen, Screen.a2.screenCopy);
                    Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();

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
                Screen.zoom_scale = (value+4)/20.0;
                Screen.a2.screenCopy = new BufferedImage(
                        (int)(Screen.zoom_scale* Screen.screen.getWidth()),
                        (int)(Screen.zoom_scale* Screen.screen.getHeight()),
                        (int)(Screen.zoom_scale* Screen.screen.getType()));
                Screen.a2.screenLabel = new JLabel(new ImageIcon(Screen.a2.screenCopy));
                Screen.a2.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                Screen.a2.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);
                Screen.image_area_listeners.add();
                //System.out.println("scale - "+Screen.zoom_scale);
                Screen.main_frame.validate();
                Screen.main_frame.repaint();
                screen.repaint(Screen.screen, Screen.a2.screenCopy);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
                Screen.main_frame.setVisible(true);
                screen.repaint(Screen.screen, Screen.a2.screenCopy);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
            }
        });
    }

    public void initializePage0Members() {
        //page-0
        start = new JButton("Start!");
    }

    public void initializePage0Listeners() {
        //page-0
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.a20 = new page1_auto_text();
            }
        });

    }

    public void initializePage1Members() {
        //page-1
        jDetectText = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("detect_text")));
        jDetectText.setContentAreaFilled(false);
        jDetectText.setBorderPainted(false);
        jSkipPage1 = new javax.swing.JButton(new javax.swing.ImageIcon(Screen.config.get("skip_page1")));
        jSkipPage1.setContentAreaFilled(false);
        jSkipPage1.setBorderPainted(false);
        languages = new String[]{"Language", "English", "Hindi","Bengali"};
        jComboPage1 = new JComboBox(languages);
        jComboPage1.setMaximumSize(new java.awt.Dimension(80, 25));
        jToolbarPage1 = new javax.swing.JToolBar();
        jDetectText.setToolTipText("Automatically detect text and go to next stage");
        jSkipPage1.setToolTipText("Go to next stage without text detection");
        jToolbarPage1.setRollover(true);
        jToolbarPage1.add(jComboPage1);

    }

    public void initializePage1Listeners() {
        jComboPage1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lang = (String) jComboPage1.getSelectedItem();
                switch (lang) {
                    case "Hindi":
                        Screen.text_exe.language = "hin";
                        //Screen.a1.Label.setFont(hin_font);
                        break;
                    case "English":
                        Screen.text_exe.language = "eng";
                        //Screen.a1.Label.setFont(eng_font);
                        break;
                    case "Bengali":
                        Screen.text_exe.language = "ben";
                        //Screen.a1.Label.setFont(eng_font);
                        break;
                    case "Language":
                        Screen.text_exe.language = "eng";
                        //Screen.a1.Label.setFont(eng_font);
                        break;
                    default:
                        Screen.text_exe.language = "eng";
                        //Screen.a1.Label.setFont(eng_font);
                        break;
                }
            }
        });

        jDetectText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.text_exe.load();
                    Screen.a21 = new page2_manual_text();
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
                Screen.a21 = new page2_manual_text();
            }
        });
    }

    public void initializePage2Members() throws FontFormatException, IOException {
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

    public void initializePage2Listeners() {
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
                    if (Screen.a9.selected_rect != 10000) {
                        jLabel.setEditable(true);
                        if (Screen.textbox_object.label.get(Screen.a9.selected_rect).getR().contains("label -")) {
                            jLabel.setText("");
                        } else {
                            jLabel.setText(Screen.textbox_object.label.get(Screen.a9.selected_rect).getR());
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
                Screen.textbox_object.deleteIndices();
                screen.repaint(Screen.screen, Screen.a2.screenCopy);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
            }
        });

        jLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String th = Screen.textbox_object.label.get(Screen.a9.selected_rect).getL();
                Pair<String, String> tempPair;
                if (jLabel.getText().length() == 0) {
                    Screen.label_counts++;
                    tempPair = new Pair<>(th, "label - " + Screen.label_counts);
                    Screen.textbox_object.label.set(Screen.a9.selected_rect, tempPair);
                } else {
                    tempPair = new Pair<>(th, jLabel.getText());
                    Screen.textbox_object.label.set(Screen.a9.selected_rect, tempPair);
                }
                screen.repaint(Screen.screen, Screen.a2.screenCopy);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
            }
        });
        jSavePage2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String th = Screen.textbox_object.label.get(Screen.a9.selected_rect).getL();
                Pair<String, String> tempPair;
                if (jLabel.getText().length() == 0) {
                    Screen.label_counts++;
                    tempPair = new Pair<>(th, "label - " + Screen.label_counts);
                    Screen.textbox_object.label.set(Screen.a9.selected_rect, tempPair);
                } else {
                    tempPair = new Pair<>(th, jLabel.getText());
                    Screen.textbox_object.label.set(Screen.a9.selected_rect, tempPair);
                }
                screen.repaint(Screen.screen, Screen.a2.screenCopy);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
            }
        });

        jGoBackPage2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.a21.go_back();
            }
        });

        jNextPage2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.text_exe.store();
                } catch (IOException | SAXException | ParserConfigurationException | XPathExpressionException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ScriptException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
                Screen.a22 = new page3_auto_maths_science();
            }
        });
    }

    public void initializePage3Members() {
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

    public void initializePage3Listeners() {
        jMathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.maths_science_exe.load();
                    Screen.a23_maths = new page4_maths_parameter();
                    Screen.main_frame.validate();
                    Screen.main_frame.repaint();
                    screen.repaint(Screen.screen, Screen.a2.screenCopy);
                    Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
                    Screen.main_frame.setVisible(true);
                    screen.repaint(Screen.screen, Screen.a2.screenCopy);
                    Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jScienceDiagram.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.maths_science_exe.load_science();
                    Screen.a23 = new page4_manual_maths_science();
                    Screen.main_frame.validate();
                    Screen.main_frame.repaint();
                    screen.repaint(Screen.screen, Screen.a2.screenCopy);
                    Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
                    Screen.main_frame.setVisible(true);
                    screen.repaint(Screen.screen, Screen.a2.screenCopy);
                    Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jGoBackPage3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.a22.go_back();
                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jSkipPage3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.a23 = new page4_manual_maths_science();
            }
        });
    }

    public void initializePage4Members() {
        //page-4
        drawLine = new javax.swing.JToggleButton("Line");
        drawCircle = new javax.swing.JToggleButton("Circle");
        drawArc = new javax.swing.JToggleButton("Arc");
        drawPolygon = new javax.swing.JToggleButton("Polygon");
        polygonStart = new javax.swing.JRadioButton("Start");
        polygonEnd = new javax.swing.JRadioButton("End");
        drawPath = new javax.swing.JToggleButton("Path");
        drawRegion = new javax.swing.JToggleButton("Region");
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
        jEditPage4.setToolTipText("Select shapes to delete");
        jDeleteButton.setToolTipText("Delete selected shapes");
        jGoBackPage4.setToolTipText("Revert back to previous stage");
        jNextPage4.setToolTipText("Go to next page if all shapes have been identified");
    }

    public void initializePage4Listeners() {
        
        drawLine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (drawLine.isSelected()) {
                    drawCircle.setSelected(false);
                    drawArc.setSelected(false);
                    drawPolygon.setSelected(false);
                    drawPath.setSelected(false);
                    drawRegion.setSelected(false);
                    jEditPage4.setSelected(false);
                    deselectRadioButtons();
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
                    jEditPage4.setSelected(false);
                    deselectRadioButtons();
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
                    jEditPage4.setSelected(false);
                    deselectRadioButtons();
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
                    jEditPage4.setSelected(false);
                    deleteAllTemp();
                    polygonStart.setEnabled(true);
                    polygonEnd.setEnabled(false);
                } else {
                    deselectRadioButtons();
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
                    Screen.polygon_object.getPolygons(m);
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
                    jEditPage4.setSelected(false);
                    deselectRadioButtons();
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
                    jEditPage4.setSelected(false);
                    deselectRadioButtons();
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
                    deselectRadioButtons();
                }
            }
        });

        jDeleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.circle_object.deleteIndices();
                Screen.line_object.deleteLineIndices();
                Screen.arc_object.deleteIndices();
                Screen.polygon_object.deleteIndices();
                Screen.region_object.deleteIndices();
                Screen.a16.deleteIndices();
                screen.repaint(Screen.screen, Screen.a2.screenCopy);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
            }
        });

        jGoBackPage4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.a23.go_back();
                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jNextPage4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.a24 = new page5_color_mapping();
            }
        });

    }

    public void initializePage4MathMembers() {
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

    public void initializePage4MathListeners() {
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
                        Screen.main_frame.validate();
                        Screen.main_frame.repaint();
                        screen.repaint(Screen.screen, Screen.a2.screenCopy);
                        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
                        Screen.main_frame.setVisible(true);
                        screen.repaint(Screen.screen, Screen.a2.screenCopy);
                        Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
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
                    Screen.main_frame.validate();
                    Screen.main_frame.repaint();
                    screen.repaint(Screen.screen, Screen.a2.screenCopy);
                    Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
                    Screen.main_frame.setVisible(true);
                    screen.repaint(Screen.screen, Screen.a2.screenCopy);
                    Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
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
                    Screen.a23_maths.go_back();
                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jMathNextPage4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.a23 = new page4_manual_maths_science();
                Screen.main_frame.validate();
                Screen.main_frame.repaint();
                screen.repaint(Screen.screen, Screen.a2.screenCopy);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);  //Screen.a2.screenLabel.repaint();
                Screen.main_frame.setVisible(true);
                screen.repaint(Screen.screen, Screen.a2.screenCopy);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);
            }
        });
    }

    public void initializePage5Members() {
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

    public void initializePage5Listeners() {
        // page-5
        jFillColor.setToolTipText("Add colours to shapes");
        jChooseColor.setToolTipText("Choose a colour");
        jGoBackPage5.setToolTipText("Revert to previous stage");
        jSelectedColor.setToolTipText("Current Colour");
        jSelectedColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.color_obj.actionPerformed(e);
            }
        });

        jGoBackPage5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.a24.go_back();
                } catch (IOException ex) {
                    Logger.getLogger(AllControlsAndListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jSaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Screen.a13.svg_file();
                    String currentFilePath = screen.current_file.getAbsolutePath();
                    String currentFileName = String.valueOf(screen.current_file.getName());
                    String newFileName = currentFileName.substring(0, currentFileName.lastIndexOf(".")) + "_1" + currentFileName.substring(currentFileName.lastIndexOf("."));
                    int index = currentFilePath.lastIndexOf("\\");
                    String newFilePath = currentFilePath.substring(0, index + 1) + newFileName;
                    File ouputFile = new File(newFilePath);
                    ouputFile.delete();
                    File textFile = new File(Screen.current_file.getAbsolutePath() + ".txt");
                    textFile.delete();
                    File htmlFile = new File(Screen.current_file.getAbsolutePath() + ".html");
                    Desktop.getDesktop().browse(htmlFile.toURI());
                    Screen.zoom_scale = 1;
                    Screen.a1.jZoomSlider.setValue(16);
                    Screen.page_0 = new page0_open_image();
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

    public AllControlsAndListeners() throws FontFormatException, IOException {
        screen = new Screen(r);
        intializeMembers();
        initializePage0Members();
        initializePage1Members();
        initializePage2Members();
        initializePage3Members();
        initializePage4Members();
        initializePage4MathMembers();
        initializePage5Members();

        initializeListeners();
        initializePage0Listeners();
        initializePage1Listeners();
        initializePage2Listeners();
        initializePage3Listeners();
        initializePage4Listeners();
        initializePage4MathListeners();
        initializePage5Listeners();
    }
}
