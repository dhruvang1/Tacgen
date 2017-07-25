
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.NoninvertibleTransformException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Image_Area_Listeners {

    int r = 0;
    SCR t1 = new SCR(r);

    public Image_Area_Listeners() {
        add();
    }

    public void add() {

        SCR.a2.screenLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                try {
                    //System.out.println("mouse moved");
                    SCR.start = SCR.a1.get_original_point_coordinate(me);
                    t1.repaint(SCR.screen, SCR.a2.screenCopy);
                    SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);
                } catch (NoninvertibleTransformException ex) {
                    Logger.getLogger(Image_Area_Listeners.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            @Override
            public void mouseDragged(MouseEvent me) {
                try {
                    if (SCR.a1.Region.isSelected() && SCR.a1.Region.isDisplayable()) {

                        SCR.region_object.get_region(me);//

                    } else if (SCR.a1.Path.isSelected() && SCR.a1.Path.isDisplayable()) {
                        SCR.a16.get_region(me);//

                    } else if (SCR.a1.Go_back_page2.isDisplayable()) {
                        if (SCR.a9.selected_rect != 10000) {
                            SCR.textbox_object.translate_text(me);//

                        } else {
                            SCR.textbox_object.select_text(me);//

                        }
                    } else if (SCR.a1.Go_back_page3.isDisplayable()) {
                        if (SCR.a9.selected_rect != 10000) {
                            SCR.textbox_object.translate_text(me);//

                        }
                    }
                } catch (NoninvertibleTransformException ex) {
                    Logger.getLogger(Image_Area_Listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        SCR.a2.screenLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                System.out.println("mouse clicked");
                try {
                    SCR.start = SCR.a1.get_original_point_coordinate(e);
                    if (SCR.a1.Line.isSelected() && SCR.a1.Line.isDisplayable()) {
                        System.out.println("lines size : " + SCR.line_object.Lines.size());
                        SCR.line_object.get_line(e); //
                    } else if (SCR.a1.Circle.isSelected() && SCR.a1.Circle.isDisplayable()) {
                        SCR.circle_object.get_circle(e); //
                    } else if (SCR.a1.Arc.isSelected() && SCR.a1.Arc.isDisplayable()) {
                        SCR.arc_object.get_circle(e); //
                    } else if (SCR.a1.Polygon.isSelected() && SCR.a1.Polygon.isDisplayable()) {
                        SCR.polygon_object.get_polygons(e); //
                    } else if (SCR.a1.Region.isSelected() && SCR.a1.Region.isDisplayable()) {

                    } else if (SCR.a1.Path.isSelected() && SCR.a1.Path.isDisplayable()) {

                    } //                if(SCR.a1.Edit.isSelected() && SCR.a1.Edit.isDisplayable()){
                    //                    SCR.textbox_object.add_indices(e);
                    //                }
                    else if (SCR.a1.Go_back_page4.isDisplayable()) {
                        try {
                            SCR.line_object.add_indices(e);//
                            SCR.circle_object.add_indices(e);//
                            SCR.region_object.add_indices(e);//
                            SCR.polygon_object.add_indices(e);//
                            SCR.arc_object.add_indices(e);//
                            SCR.a16.add_indices(e);//
                        } catch (NoninvertibleTransformException ex) {
                            Logger.getLogger(Image_Area_Listeners.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    if (SCR.a1.Go_back_page5.isDisplayable()) {
                        SCR.region_object.add_color_indices(e);
                        SCR.circle_object.add_color_indices(e);
                        SCR.line_object.add_color_indices(e);
                        SCR.polygon_object.add_color_indices(e);
                        SCR.a16.add_color_indices(e);
                        SCR.arc_object.add_color_indices(e);
                    }
                    if (SCR.a1.Go_back_page3.isDisplayable()) {
                        SCR.a9.modify_selected_rect(e);
                        if (SCR.a9.selected_rect != 10000) {
                            SCR.rectangle_translate_start = SCR.textbox_object.Rect_array.get(SCR.a9.selected_rect).getLocation();
                        }
                    }
                    if (SCR.a1.Go_back_page2.isDisplayable()) {
                        SCR.a9.modify_selected_rect(e);
                        if (SCR.a9.selected_rect != 10000) {
                            SCR.rectangle_translate_start = SCR.textbox_object.Rect_array.get(SCR.a9.selected_rect).getLocation();
                            SCR.a1.Label.setEditable(true);
                            SCR.a1.save_page2.setEnabled(true);
                            SCR.a1.delete_page2.setEnabled(true);
                            SCR.a1.Label.setEnabled(true);
                            SCR.a1.Label.requestFocus(true);
                            String language = SCR.textbox_object.Language_array.get(SCR.a9.selected_rect);
                            System.out.println("current box's language = " + language);
                            switch (language) {
                                case "hin":
                                    System.out.println("hindi font set");
                                    SCR.a1.Label.setFont(SCR.a1.hin_font);
                                    break;
                                case "eng":
                                    System.out.println("english font set");
                                    SCR.a1.Label.setFont(SCR.a1.eng_font);
                                    break;
                                case "ben":
                                    System.out.println("bengali font set");
                                    SCR.a1.Label.setFont(SCR.a1.ben_font);
                                    break;
                                default:
                                    break;
                            }
                            if (SCR.textbox_object.label.get(SCR.a9.selected_rect).getR().contains("label -")) {
                                SCR.a1.Label.setText("");
                            } else {
                                String font_name = SCR.a1.Label.getFont().getFontName();
                                String text_to_be_set = SCR.textbox_object.label.get(SCR.a9.selected_rect).getR();
                                System.out.println("current font : " + font_name + " , current text : " + text_to_be_set);
                                SCR.a1.Label.setText(text_to_be_set);
                            }
                        }
                    }
                } catch (NoninvertibleTransformException ex) {
                    System.out.println("error");
                    Logger.getLogger(Image_Area_Listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
                t1.repaint(SCR.screen, SCR.a2.screenCopy);
                SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (SCR.a1.Go_back_page2.isDisplayable()) {
                    if (SCR.a9.selected_rect != 10000) {
                        SCR.a9.selected_rect = 10000;
                    } else {
                        try {
                            SCR.textbox_object.update_capture_rect(me); //
                        } catch (NoninvertibleTransformException ex) {
                            Logger.getLogger(Image_Area_Listeners.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (SCR.a1.Go_back_page3.isDisplayable()) {
                    if (SCR.a9.selected_rect != 10000) {
                        SCR.a9.selected_rect = 10000;
                    }
                }
                if (SCR.a1.Region.isSelected() && SCR.a1.Region.isDisplayable()) {
                    SCR.region_object.update_regions();
                }
                if (SCR.a1.Path.isSelected() && SCR.a1.Path.isDisplayable()) {
                    SCR.a16.update_regions();
                }
                t1.repaint(SCR.screen, SCR.a2.screenCopy);
                SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);
            }
        });

        SCR.a1.preview_button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SCR.a1.preview_button.isEnabled()) {
                    SCR.a1.preview_button.setEnabled(false);
                    SCR.preview_frame.setBounds(0, 0, (SCR.main_frame.getWidth() * 3) / 4, SCR.main_frame.getHeight());
                    SCR.preview_frame.validate();
                    SCR.preview_frame.repaint();
                    t1.repaint(SCR.white, SCR.a2.whitecopy);
                    SCR.a2.preview_ScrollPane.setViewportView(SCR.a2.preview_Label);
                    SCR.preview_frame.setVisible(true);
                    SCR.preview_frame.validate();
                    SCR.preview_frame.repaint();
                    t1.repaint(SCR.white, SCR.a2.whitecopy);
                    SCR.a2.preview_ScrollPane.setViewportView(SCR.a2.preview_Label);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!SCR.preview_frame.isVisible()) {
//                    SCR.a2.preview_frame.setBounds(0,0,(SCR.main_frame.getWidth()*3)/4,SCR.main_frame.getHeight());
//                    SCR.a2.preview_frame.validate();
//                    SCR.a2.preview_frame.repaint();
//                    t1.repaint(SCR.white, SCR.a2.whitecopy);
//                    SCR.a2.preview_ScrollPane.setViewportView(SCR.a2.preview_Label);
//                    SCR.a2.preview_frame.setVisible(true);                    
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //SCR.a2.preview_frame.setVisible(false);
            }
        });
    }

}
