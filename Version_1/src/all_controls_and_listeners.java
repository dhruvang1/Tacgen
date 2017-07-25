
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

public final class all_controls_and_listeners extends JFrame {

    int r = 0;
    SCR t1;
    javax.swing.JMenuBar menu_bar;
    javax.swing.JMenu file_menu;
    javax.swing.JMenuItem open_file;
    javax.swing.JMenu help_menu;
    javax.swing.JMenuItem tutorial_help;
    javax.swing.JSlider zoom_slider;
    javax.swing.JLabel preview_button;
    JButton generate_svg;
    JButton clear_svg;

    //page-0
    JButton Start;

    //page-1
    javax.swing.JButton detect_text;
    javax.swing.JButton skip_page1;
    String[] languages;
    JComboBox combo_page1;
    javax.swing.JToolBar ToolBar_page1;
    Font hin_font;
    Font eng_font;
    Font ben_font;

    //page-2
    javax.swing.JToggleButton Select_Text;
    javax.swing.JToggleButton Edit;
    javax.swing.JToggleButton Modify_Label;
    javax.swing.JButton delete_page2;
    javax.swing.JTextField Label;
    javax.swing.JButton save_page2;
    javax.swing.JSeparator Separator1;
    javax.swing.JSeparator Separator2;
    javax.swing.JButton Go_back_page2;
    javax.swing.JButton Next_page2;

    //page-3
    javax.swing.JComboBox combo_page3;
    javax.swing.JButton Math_diagram;
    javax.swing.JButton Science_diagram;
    javax.swing.JButton Go_back_page3;
    javax.swing.JButton skip_page3;

    //page-4
    javax.swing.JToggleButton Line;
    javax.swing.JToggleButton Circle;
    javax.swing.JToggleButton Arc;
    javax.swing.JToggleButton Polygon;
    javax.swing.JRadioButton polygon_start;
    javax.swing.JRadioButton polygon_end;
    javax.swing.JToggleButton Path;
    javax.swing.JToggleButton Region;
    javax.swing.JToggleButton Edit_page4;
    javax.swing.JButton delete_page4;
    javax.swing.JButton Go_back_page4;
    javax.swing.JButton Next_page4;

    //maths parameter page 4
    javax.swing.JLabel angle_p_math_p;
    javax.swing.JLabel dist_p_math_p;
    javax.swing.JSlider dup_line_angle_slider;
    javax.swing.JSlider dup_line_dist_slider;
    javax.swing.JToolBar ToolBar_page4;
    javax.swing.JButton Go_back_parameter_page4;
    javax.swing.JButton Next_parameter_page4;

    //page5
    javax.swing.JToggleButton Fill_color;
    javax.swing.JButton Choose_color;
    javax.swing.JButton Selected_color;
    javax.swing.JButton Go_back_page5;
    javax.swing.JButton Save;

    public void deselect_rb1_rb2() {
        SCR.a1.polygon_start.setSelected(false);
        SCR.a1.polygon_end.setSelected(false);
        SCR.a1.polygon_start.setEnabled(false);
        SCR.a1.polygon_end.setEnabled(false);
    }

    public void delete_all_temp() {
        SCR.line_object.delete_temp();
        SCR.circle_object.delete_temp();
        SCR.arc_object.delete_temp();
        SCR.polygon_object.delete_temp();
        t1.repaint(SCR.screen, SCR.a2.screenCopy);
        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);
    }
    
    public Hashtable getLabelTable(int min, int max, int inc) {
        Hashtable<Integer,JLabel> table = new Hashtable<Integer,JLabel>();
        for(int j = min; j <= max; j += inc) {
            String s = String.format("%.2f", (j+4)/20.0);
            table.put(Integer.valueOf(j), new JLabel(s));
        }
        return table;
    }
    public Point get_original_point_coordinate(MouseEvent e) throws NoninvertibleTransformException{
        Point p = e.getPoint();
        SCR.zoom_affine_transform.inverseTransform(p, p);
        return p;
    }
    
    public Point get_zoomed_point_coordinate(Point p) throws NoninvertibleTransformException{
        SCR.zoom_affine_transform.transform(p, p);
        return p;
    }
    
    public void default_elements() {
        menu_bar = new javax.swing.JMenuBar();
        menu_bar.setPreferredSize(new java.awt.Dimension(56, 30));
        file_menu = new javax.swing.JMenu("File");
        file_menu.setBackground(Color.BLUE);
        file_menu.setForeground(Color.WHITE);
        file_menu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        file_menu.setIconTextGap(10);
        file_menu.setOpaque(true);
        file_menu.setPreferredSize(new java.awt.Dimension(50, 25));
        help_menu = new javax.swing.JMenu("Help");
        help_menu.setBackground(Color.BLUE);
        help_menu.setForeground(Color.WHITE);
        help_menu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        help_menu.setIconTextGap(10);
        help_menu.setOpaque(true);
        help_menu.setPreferredSize(new java.awt.Dimension(60, 25));
        open_file = new javax.swing.JMenuItem("Open");
        open_file.setFont(new java.awt.Font("Segoe UI", 0, 14));
        open_file.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        tutorial_help = new javax.swing.JMenuItem("Tutorial");
        tutorial_help.setFont(new java.awt.Font("Segoe UI", 0, 14));
        tutorial_help.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        generate_svg = new JButton("SVG");
        clear_svg = new JButton("CLEAR");
        preview_button = new JLabel(new javax.swing.ImageIcon(SCR.config.get("hover_image")));
        preview_button.setToolTipText("See progress in preview pane");
        //default
        file_menu.add(open_file);
        help_menu.add(tutorial_help);
        menu_bar.add(file_menu);
        menu_bar.add(help_menu);
        
        zoom_slider = new javax.swing.JSlider();
        zoom_slider.setMajorTickSpacing(5);
        zoom_slider.setMaximum(36);
        zoom_slider.setMinimum(1);
        zoom_slider.setMinorTickSpacing(1);
        zoom_slider.setOrientation(javax.swing.JSlider.VERTICAL);
        zoom_slider.setLabelTable(getLabelTable(1, 36, 5));
        zoom_slider.setPaintLabels(true);
        zoom_slider.setPaintTicks(true);
        zoom_slider.setValue(16);
    }

    public void default_listeners() {
        SCR.main_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (!SCR.current_file.getName().contentEquals("tacgen.jpg")) {
                    String ObjButtons[] = {"Yes", "No", "Cancel"};
                    int PromptResult = JOptionPane.showOptionDialog(null, "Do you want to save any unsaved work?", "Tactile Tool", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[0]);
                    if (PromptResult == JOptionPane.YES_OPTION) {
                        try {
                            SCR.a13.svg_file();
                            String h1 = SCR.current_file.getAbsolutePath();
                            String h2 = String.valueOf(SCR.current_file.getName());
                            String ty = h2.substring(0, h2.lastIndexOf(".")) + "_1" + h2.substring(h2.lastIndexOf("."));
                            String h4 = h1;
                            int index = h4.lastIndexOf("\\");
                            h4 = h4.substring(0, index + 1) + ty;
                            File outputfile1 = new File(h4);
                            outputfile1.delete();
                            File textfile = new File(SCR.current_file.getAbsolutePath() + ".txt");
                            textfile.delete();
                            System.exit(0);
                        } catch (IOException ex) {
                            Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ScriptException ex) {
                            Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchMethodException ex) {
                            Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (PromptResult == JOptionPane.NO_OPTION) {
                        String h1 = SCR.current_file.getAbsolutePath();
                        String h2 = String.valueOf(SCR.current_file.getName());
                        String ty = h2.substring(0, h2.lastIndexOf(".")) + "_1" + h2.substring(h2.lastIndexOf("."));
                        String h4 = h1;
                        int index = h4.lastIndexOf("\\");
                        h4 = h4.substring(0, index + 1) + ty;
                        File outputfile1 = new File(h4);
                        outputfile1.delete();
                        File textfile = new File(SCR.current_file.getAbsolutePath() + ".txt");
                        textfile.delete();
                        File svgfile = new File(SCR.current_file.getAbsolutePath() + ".html");
                        svgfile.delete();
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
            }
        });
        SCR.preview_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                SCR.a1.preview_button.setEnabled(true);
            }
        });
        open_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setPreferredSize(new Dimension(800, 600));
                FileFilter imageFilter = new FileNameExtensionFilter(
                        "Image files", ImageIO.getReaderFileSuffixes());
                fc.addChoosableFileFilter(imageFilter);
                fc.setAccessory(new ImagePreviewer(fc));
                fc.setFileFilter(imageFilter);
                File workingdirectory = new File(System.getProperty("user.dir"));
                fc.setCurrentDirectory(workingdirectory);
                int returnVal = fc.showOpenDialog(SCR.main_frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file1 = fc.getSelectedFile();
                        SCR.current_file = file1;
                        SCR.current_file_name = String.valueOf(SCR.current_file.getName());
                        SCR.screen = ImageIO.read(file1);
                        SCR.a2.screenCopy = new BufferedImage(
                                (int)(SCR.zoom_scale*SCR.screen.getWidth()),
                                (int)(SCR.zoom_scale*SCR.screen.getHeight()),
                                SCR.screen.getType());
                        SCR.a2.screenLabel = new JLabel(new ImageIcon(SCR.a2.screenCopy));
                        SCR.a2.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                        SCR.a2.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);

                        SCR.main_frame.validate();
                        SCR.main_frame.repaint();
                        t1.repaint(SCR.screen, SCR.a2.screenCopy);
                        SCR.main_frame.setVisible(true);
                        SCR.refresh_all.refresh();
                        File svgfile = new File(SCR.current_file.getAbsolutePath() + ".html");
                        if (svgfile.exists()) {
                            String ObjButtons[] = {"Yes", "No"};
                            int PromptResult = JOptionPane.showOptionDialog(null, "You already have saved SVG for this file. Do you want to restore it?", "Restore", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[0]);
                            if (PromptResult == JOptionPane.YES_OPTION) {
                                SCR.restore_svg.restore();
                            } else {
                                svgfile.delete();
                            }
                        }
                        SCR.a20 = new page1_auto_text();
                        SCR.main_frame.validate();
                        SCR.main_frame.repaint();
                        t1.repaint(SCR.screen, SCR.a2.screenCopy);
                        //SCR.a2.screenLabel.repaint();
                        SCR.main_frame.setVisible(true);
                    } catch (IOException ex) {
                        Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (XPathExpressionException ex) {
                        Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ScriptException ex) {
                        Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NoSuchMethodException ex) {
                        Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        tutorial_help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    File f = new File(SCR.config.get("tutorial_help"));
                    Desktop.getDesktop().browse(f.toURI());
                } catch (IOException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        final MouseEvent m = null;

        clear_svg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SCR.refresh_all.refresh();
                    SCR.a13.svg_file();
                    t1.repaint(SCR.screen, SCR.a2.screenCopy);
                    SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();

                } catch (IOException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ScriptException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        zoom_slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = zoom_slider.getValue();
                SCR.zoom_scale = (value+4)/20.0;
                SCR.a2.screenCopy = new BufferedImage(
                        (int)(SCR.zoom_scale*SCR.screen.getWidth()),
                        (int)(SCR.zoom_scale*SCR.screen.getHeight()),
                        (int)(SCR.zoom_scale*SCR.screen.getType()));
                SCR.a2.screenLabel = new JLabel(new ImageIcon(SCR.a2.screenCopy));
                SCR.a2.screenLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                SCR.a2.screenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);
                SCR.image_area_listeners.add();
                //System.out.println("scale - "+SCR.zoom_scale);
                SCR.main_frame.validate();
                SCR.main_frame.repaint();
                t1.repaint(SCR.screen, SCR.a2.screenCopy);
                SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
                SCR.main_frame.setVisible(true);
                t1.repaint(SCR.screen, SCR.a2.screenCopy);
                SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
            }
        });
    }

    public void page_0_elements() {
        //page-0
        Start = new JButton("Start!");
    }

    public void page_0_listeners() {
        //page-0
        Start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SCR.a20 = new page1_auto_text();
            }
        });

    }

    public void page_1_elements() {
        //page-1
        detect_text = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("detect_text")));
        detect_text.setContentAreaFilled(false);
        detect_text.setBorderPainted(false);
        skip_page1 = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("skip_page1")));
        skip_page1.setContentAreaFilled(false);
        skip_page1.setBorderPainted(false);
        languages = new String[]{"Language", "English", "Hindi","Bengali"};
        combo_page1 = new JComboBox(languages);
        combo_page1.setMaximumSize(new java.awt.Dimension(80, 25));
        ToolBar_page1 = new javax.swing.JToolBar();
        detect_text.setToolTipText("Automatically detect text and go to next stage");
        skip_page1.setToolTipText("Go to next stage without text detection");
        ToolBar_page1.setRollover(true);
        ToolBar_page1.add(combo_page1);

    }

    public void page_1_listeners() {
        combo_page1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lang = (String) combo_page1.getSelectedItem();
                switch (lang) {
                    case "Hindi":
                        SCR.text_exe.language = "hin";
                        //SCR.a1.Label.setFont(hin_font);
                        break;
                    case "English":
                        SCR.text_exe.language = "eng";
                        //SCR.a1.Label.setFont(eng_font);
                        break;
                    case "Bengali":
                        SCR.text_exe.language = "ben";
                        //SCR.a1.Label.setFont(eng_font);
                        break;
                    case "Language":
                        SCR.text_exe.language = "eng";
                        //SCR.a1.Label.setFont(eng_font);
                        break;
                    default:
                        SCR.text_exe.language = "eng";
                        //SCR.a1.Label.setFont(eng_font);
                        break;
                }
            }
        });

        detect_text.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SCR.text_exe.load();
                    SCR.a21 = new page2_manual_text();
                } catch (IOException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ScriptException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        skip_page1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SCR.a21 = new page2_manual_text();
            }
        });
    }

    public void page_2_elements() throws FontFormatException, IOException {
        //page-2
        Select_Text = new javax.swing.JToggleButton("Select Text");
        Edit = new javax.swing.JToggleButton("Edit");
        Modify_Label = new javax.swing.JToggleButton("Modify Label");
        delete_page2 = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("delete_page2")));
        delete_page2.setContentAreaFilled(false);
        delete_page2.setBorderPainted(false);
        Label = new javax.swing.JTextField();
        eng_font = new java.awt.Font("sansserif", 0, 24);
        hin_font = Font.createFont(Font.PLAIN, new File(SCR.config.get("font_directory_path"))).deriveFont(Font.BOLD, 24f);
        ben_font = Font.createFont(Font.PLAIN, new File(SCR.config.get("ben_font_directory_path"))).deriveFont(Font.BOLD, 24f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(hin_font);
        ge.registerFont(ben_font);
        //System.out.println(hin_font);
        save_page2 = new javax.swing.JButton("save");
        Separator1 = new javax.swing.JSeparator();
        Separator2 = new javax.swing.JSeparator();
        Separator2.setBackground(new java.awt.Color(240, 240, 240));
        Separator2.setForeground(new java.awt.Color(240, 240, 240));
        Separator2.setEnabled(false);

        Separator1.setBackground(new java.awt.Color(240, 240, 240));
        Separator1.setForeground(new java.awt.Color(240, 240, 240));
        Separator1.setEnabled(false);
        Go_back_page2 = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("Go_back_page2")));
        Go_back_page2.setContentAreaFilled(false);
        Go_back_page2.setBorderPainted(false);
        Next_page2 = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("Next_page2")));
        Next_page2.setContentAreaFilled(false);
        Next_page2.setBorderPainted(false);

        Select_Text.setToolTipText("Draw a box around undetected text");
        Modify_Label.setToolTipText("Modify labels of misdetected texts and undetected text");
        Edit.setToolTipText("Select text boxes to delete");
        delete_page2.setToolTipText("Delete selected text boxes");
        Go_back_page2.setToolTipText("Revert back to previous stage");
        Next_page2.setToolTipText("Go to next stage if all texts have been idenfied");
        Label.setToolTipText("Modify labels here");

    }

    public void page_2_listeners() {
        Select_Text.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Select_Text.isSelected()) {
                    Modify_Label.setSelected(false);
                    Edit.setSelected(false);
                    Label.setText("");
                    Label.setEditable(false);
                }
            }
        });

        Modify_Label.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    Select_Text.setSelected(false);
                    Edit.setSelected(false);
                    if (SCR.a9.selected_rect != 10000) {
                        Label.setEditable(true);
                        if (SCR.textbox_object.label.get(SCR.a9.selected_rect).getR().contains("label -")) {
                            Label.setText("");
                        } else {
                            Label.setText(SCR.textbox_object.label.get(SCR.a9.selected_rect).getR());
                        }
                    }
                } else {
                    Label.setText("");
                    Label.setEditable(false);
                }
            }
        });

        Edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Edit.isSelected()) {
                    Label.setText("");
                    Label.setEditable(false);
                    Select_Text.setSelected(false);
                    Modify_Label.setSelected(false);
                }
            }
        });

        delete_page2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SCR.textbox_object.delete_indices();
                t1.repaint(SCR.screen, SCR.a2.screenCopy);
                SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
            }
        });

        Label.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String th = SCR.textbox_object.label.get(SCR.a9.selected_rect).getL();
                if (Label.getText().length() == 0) {
                    SCR.label_counts++;
                    Pair<String, String> temp_pair = new Pair<String, String>(th, "label - " + SCR.label_counts);
                    SCR.textbox_object.label.set(SCR.a9.selected_rect, temp_pair);
                } else {
                    Pair<String, String> temp_pair = new Pair<String, String>(th, Label.getText());
                    SCR.textbox_object.label.set(SCR.a9.selected_rect, temp_pair);
                }
                t1.repaint(SCR.screen, SCR.a2.screenCopy);
                SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
            }
        });
        save_page2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String th = SCR.textbox_object.label.get(SCR.a9.selected_rect).getL();
                
                if (Label.getText().length() == 0) {
                    SCR.label_counts++;
                    Pair<String, String> temp_pair = new Pair<String, String>(th, "label - " + SCR.label_counts);
                    SCR.textbox_object.label.set(SCR.a9.selected_rect, temp_pair);
                } else {
                    Pair<String, String> temp_pair = new Pair<String, String>(th, Label.getText());
                    SCR.textbox_object.label.set(SCR.a9.selected_rect, temp_pair);
                }
                t1.repaint(SCR.screen, SCR.a2.screenCopy);
                SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
            }
        });

        Go_back_page2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SCR.a21.go_back();
            }
        });

        Next_page2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SCR.text_exe.store();
                } catch (IOException | SAXException | ParserConfigurationException | XPathExpressionException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ScriptException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
                SCR.a22 = new page3_auto_maths_science();
            }
        });
    }

    public void page_3_elements() {
        //page-3
        combo_page3 = new javax.swing.JComboBox();
        Math_diagram = new javax.swing.JButton("Maths");
        Science_diagram = new javax.swing.JButton("Science");
        Go_back_page3 = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("Go_back_page3")));
        Go_back_page3.setContentAreaFilled(false);
        Go_back_page3.setBorderPainted(false);
        skip_page3 = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("skip_page3")));
        skip_page3.setContentAreaFilled(false);
        skip_page3.setBorderPainted(false);
        Math_diagram.setToolTipText("Detect mathmatical figures in image for maths images");
        Science_diagram.setToolTipText("Detect different regions in image for science diagrams");
        Go_back_page3.setToolTipText("Revert to previous stage");
        skip_page3.setToolTipText("Go to next stage");
    }

    public void page_3_listeners() {
        Math_diagram.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SCR.maths_science_exe.load();
                    SCR.a23_maths = new page4_maths_parameter();
                    SCR.main_frame.validate();
                    SCR.main_frame.repaint();
                    t1.repaint(SCR.screen, SCR.a2.screenCopy);
                    SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
                    SCR.main_frame.setVisible(true);
                    t1.repaint(SCR.screen, SCR.a2.screenCopy);
                    SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Science_diagram.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SCR.maths_science_exe.load_science();
                    SCR.a23 = new page4_manual_maths_science();
                    SCR.main_frame.validate();
                    SCR.main_frame.repaint();
                    t1.repaint(SCR.screen, SCR.a2.screenCopy);
                    SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
                    SCR.main_frame.setVisible(true);
                    t1.repaint(SCR.screen, SCR.a2.screenCopy);
                    SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Go_back_page3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SCR.a22.go_back();
                } catch (IOException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        skip_page3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SCR.a23 = new page4_manual_maths_science();
            }
        });
    }

    public void page_4_elements() {
        //page-4
        Line = new javax.swing.JToggleButton("Line");
        Circle = new javax.swing.JToggleButton("Circle");
        Arc = new javax.swing.JToggleButton("Arc");
        Polygon = new javax.swing.JToggleButton("Polygon");
        polygon_start = new javax.swing.JRadioButton("Start");
        polygon_end = new javax.swing.JRadioButton("End");
        Path = new javax.swing.JToggleButton("Path");
        Region = new javax.swing.JToggleButton("Region");
        Edit_page4 = new javax.swing.JToggleButton("Edit");
        delete_page4 = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("delete_page4")));
        delete_page4.setContentAreaFilled(false);
        delete_page4.setBorderPainted(false);
        Go_back_page4 = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("Go_back_page4")));
        Go_back_page4.setContentAreaFilled(false);
        Go_back_page4.setBorderPainted(false);
        Next_page4 = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("Next_page4")));
        Next_page4.setContentAreaFilled(false);
        Next_page4.setBorderPainted(false);

        Line.setToolTipText("Select two end points of line");
        Circle.setToolTipText("Select any three point of circle");
        Arc.setToolTipText("Select three points of arc in order");
        Polygon.setToolTipText("Select vertices of polygon in order");
        polygon_end.setToolTipText("Draw a polygon of selected vertices");
        Path.setToolTipText("Select points in order to draw a continuous path");
        Region.setToolTipText("Draw a arbit shape");
        Edit_page4.setToolTipText("Select shapes to delete");
        delete_page4.setToolTipText("Delete selected shapes");
        Go_back_page4.setToolTipText("Revert back to previous stage");
        Next_page4.setToolTipText("Go to next page if all shapes have been identified");
    }

    public void page_4_listeners() {
        
        Line.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Line.isSelected()) {
                    Circle.setSelected(false);
                    Arc.setSelected(false);
                    Polygon.setSelected(false);
                    Path.setSelected(false);
                    Region.setSelected(false);
                    Edit_page4.setSelected(false);
                    deselect_rb1_rb2();
                    delete_all_temp();
                }
            }
        });

        Circle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Circle.isSelected()) {
                    Line.setSelected(false);
                    Arc.setSelected(false);
                    Polygon.setSelected(false);
                    Path.setSelected(false);
                    Region.setSelected(false);
                    Edit_page4.setSelected(false);
                    deselect_rb1_rb2();
                    delete_all_temp();
                }
            }
        });

        Arc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Arc.isSelected()) {
                    Line.setSelected(false);
                    Circle.setSelected(false);
                    Polygon.setSelected(false);
                    Path.setSelected(false);
                    Region.setSelected(false);
                    Edit_page4.setSelected(false);
                    deselect_rb1_rb2();
                    delete_all_temp();
                }
            }
        });

        Polygon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Polygon.isSelected()) {
                    Line.setSelected(false);
                    Circle.setSelected(false);
                    Arc.setSelected(false);
                    Path.setSelected(false);
                    Region.setSelected(false);
                    Edit_page4.setSelected(false);
                    delete_all_temp();
                    polygon_start.setEnabled(true);
                    polygon_end.setEnabled(false);
                } else {
                    deselect_rb1_rb2();
                }
            }
        });

        polygon_start.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    polygon_end.setSelected(false);
                }
            }
        });

        polygon_end.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    MouseEvent m = null;
                    SCR.polygon_object.get_polygons(m);
                } catch (NoninvertibleTransformException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Path.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Path.isSelected()) {
                    Line.setSelected(false);
                    Circle.setSelected(false);
                    Arc.setSelected(false);
                    Polygon.setSelected(false);
                    Region.setSelected(false);
                    Edit_page4.setSelected(false);
                    deselect_rb1_rb2();
                    delete_all_temp();
                }
            }
        });

        Region.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Region.isSelected()) {
                    Line.setSelected(false);
                    Circle.setSelected(false);
                    Arc.setSelected(false);
                    Path.setSelected(false);
                    Polygon.setSelected(false);
                    Edit_page4.setSelected(false);
                    deselect_rb1_rb2();
                    delete_all_temp();
                }
            }
        });

        Edit_page4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Edit_page4.isSelected()) {
                    Line.setSelected(false);
                    Circle.setSelected(false);
                    Arc.setSelected(false);
                    Polygon.setSelected(false);
                    Path.setSelected(false);
                    Region.setSelected(false);
                    deselect_rb1_rb2();
                }
            }
        });

        delete_page4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SCR.circle_object.delete_indices();
                SCR.line_object.delete_line_indices();
                SCR.arc_object.delete_indices();
                SCR.polygon_object.delete_indices();
                SCR.region_object.delete_indices();
                SCR.a16.delete_indices();
                t1.repaint(SCR.screen, SCR.a2.screenCopy);
                SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
            }
        });

        Go_back_page4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SCR.a23.go_back();
                } catch (IOException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Next_page4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SCR.a24 = new page5_color_mapping();
            }
        });

    }

    public void page_4_maths_param_elements() {
        // maths parameter page 4
        dist_p_math_p = new javax.swing.JLabel();
        dist_p_math_p.setMaximumSize(new Dimension(60, 45));
        dist_p_math_p.setText("<html>Distance<br>parameter</html>");

        angle_p_math_p = new javax.swing.JLabel();
        angle_p_math_p.setMaximumSize(new Dimension(60, 45));
        angle_p_math_p.setText("<html>Angle<br>Parameter</html>");

        dup_line_dist_slider = new javax.swing.JSlider();
        dup_line_dist_slider.setMajorTickSpacing(10);
        dup_line_dist_slider.setMaximum(50);
        dup_line_dist_slider.setMinimum(10);
        dup_line_dist_slider.setMinorTickSpacing(5);
        dup_line_dist_slider.setPaintLabels(true);
        dup_line_dist_slider.setPaintTicks(true);
        dup_line_dist_slider.setSnapToTicks(true);
        dup_line_dist_slider.setValue(10);
        dup_line_dist_slider.setMaximumSize(new java.awt.Dimension(100, 45));

        dup_line_angle_slider = new javax.swing.JSlider();
        dup_line_angle_slider.setMajorTickSpacing(2);
        dup_line_angle_slider.setMaximum(10);
        dup_line_angle_slider.setMinorTickSpacing(1);
        dup_line_angle_slider.setPaintLabels(true);
        dup_line_angle_slider.setPaintTicks(true);
        dup_line_angle_slider.setSnapToTicks(true);
        dup_line_angle_slider.setValue(5);
        dup_line_angle_slider.setMaximumSize(new java.awt.Dimension(100, 45));

        ToolBar_page4 = new javax.swing.JToolBar();
        ToolBar_page4.add(dist_p_math_p);
        ToolBar_page4.add(dup_line_dist_slider);
        ToolBar_page4.add(angle_p_math_p);
        ToolBar_page4.add(dup_line_angle_slider);

        Go_back_parameter_page4 = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("Go_back_parameter_page4")));
        Go_back_parameter_page4.setContentAreaFilled(false);
        Go_back_parameter_page4.setBorderPainted(false);

        Next_parameter_page4 = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("Next_parameter_page4")));
        Next_parameter_page4.setContentAreaFilled(false);
        Next_parameter_page4.setBorderPainted(false);

    }

    public void page_4_maths_param_listeners() {
        // maths parameter page 4
        Go_back_parameter_page4.setToolTipText("Revert back to previous stage");
        Next_parameter_page4.setToolTipText("Go to next page if you see best detection result");
        dup_line_dist_slider.setToolTipText("<html>A parameter to remove nearly detected duplicate lines."
                + "<br>Duplicate lines with gap less than this value "
                + "<br>will be removed. Increase this "
                + "<br>if duplicate lines are detected</html>");
        dup_line_angle_slider.setToolTipText("<html>A parameter to remove nearly detected duplicate lines."
                + "<br>Duplicate lines with angle less than this value"
                + "<br>will be removed. Increase this"
                + "<br>if duplicate lines are detected</html>");

        dup_line_dist_slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (!dup_line_dist_slider.getValueIsAdjusting()) {
                    try {
                        SCR.maths_science_exe.load();
                        SCR.main_frame.validate();
                        SCR.main_frame.repaint();
                        t1.repaint(SCR.screen, SCR.a2.screenCopy);
                        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
                        SCR.main_frame.setVisible(true);
                        t1.repaint(SCR.screen, SCR.a2.screenCopy);
                        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
                    } catch (IOException ex) {
                        Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        dup_line_dist_slider.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }

            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                //dup_line_dist_sliderAncestorResized(evt);
            }
        });

        dup_line_angle_slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (!dup_line_angle_slider.getValueIsAdjusting()) {
                    try {
                        SCR.maths_science_exe.load();
                        SCR.main_frame.validate();
                        SCR.main_frame.repaint();
                        t1.repaint(SCR.screen, SCR.a2.screenCopy);
                        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
                        SCR.main_frame.setVisible(true);
                        t1.repaint(SCR.screen, SCR.a2.screenCopy);
                        SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
                    } catch (IOException ex) {
                        Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        Go_back_parameter_page4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SCR.a23_maths.go_back();
                } catch (IOException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Next_parameter_page4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SCR.a23 = new page4_manual_maths_science();
                SCR.main_frame.validate();
                SCR.main_frame.repaint();
                t1.repaint(SCR.screen, SCR.a2.screenCopy);
                SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
                SCR.main_frame.setVisible(true);
                t1.repaint(SCR.screen, SCR.a2.screenCopy);
                SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);
            }
        });
    }

    public void page_5_elements() {
        //page5
        Fill_color = new javax.swing.JToggleButton("Fill Color");
        Choose_color = new javax.swing.JButton("Choose");
        Selected_color = new javax.swing.JButton("");
        Go_back_page5 = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("Go_back_page5")));
        Go_back_page5.setContentAreaFilled(false);
        Go_back_page5.setBorderPainted(false);
        Save = new javax.swing.JButton(new javax.swing.ImageIcon(SCR.config.get("Save_page5")));
        Save.setContentAreaFilled(false);
        Save.setBorderPainted(false);
        
    }

    public void page_5_listeners() {
        // page-5
        Fill_color.setToolTipText("Add colours to shapes");
        Choose_color.setToolTipText("Choose a colour");
        Go_back_page5.setToolTipText("Revert to previous stage");
        Selected_color.setToolTipText("Current Colour");
        Selected_color.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SCR.color_obj.actionPerformed(e);
            }
        });

        Go_back_page5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SCR.a24.go_back();
                } catch (IOException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SCR.a13.svg_file();
                    String h1 = t1.current_file.getAbsolutePath();
                    String h2 = String.valueOf(t1.current_file.getName());
                    String ty = h2.substring(0, h2.lastIndexOf(".")) + "_1" + h2.substring(h2.lastIndexOf("."));
                    String h4 = h1;
                    int index = h4.lastIndexOf("\\");
                    h4 = h4.substring(0, index + 1) + ty;
                    File outputfile1 = new File(h4);
                    outputfile1.delete();
                    File textfile = new File(SCR.current_file.getAbsolutePath() + ".txt");
                    textfile.delete();
                    File f = new File(SCR.current_file.getAbsolutePath() + ".html");
                    Desktop.getDesktop().browse(f.toURI());
                    SCR.zoom_scale = 1;
                    SCR.a1.zoom_slider.setValue(16);
                    SCR.page_0 = new page0_open_image();
                } catch (IOException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ScriptException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(all_controls_and_listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public all_controls_and_listeners() throws FontFormatException, IOException {
        t1 = new SCR(r);
        default_elements();
        page_0_elements();
        page_1_elements();
        page_2_elements();
        page_3_elements();
        page_4_elements();
        page_4_maths_param_elements();
        page_5_elements();

        default_listeners();
        page_0_listeners();
        page_1_listeners();
        page_2_listeners();
        page_3_listeners();
        page_4_listeners();
        page_4_maths_param_listeners();
        page_5_listeners();
    }
}
