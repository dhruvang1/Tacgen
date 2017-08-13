
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.NoninvertibleTransformException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Image_Area_Listeners {

    int r = 0;
    Screen t1 = new Screen(r);

    public Image_Area_Listeners() {
        add();
    }

    public void add() {

        Screen.a2.screenLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                try {
                    //System.out.println("mouse moved");
                    Screen.start = Screen.a1.getOriginalZoomedCoordinate(me);
                    t1.repaint(Screen.screen, Screen.a2.screenCopy);
                    Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);
                } catch (NoninvertibleTransformException ex) {
                    Logger.getLogger(Image_Area_Listeners.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            @Override
            public void mouseDragged(MouseEvent me) {
                try {
                    if (Screen.a1.drawRegion.isSelected() && Screen.a1.drawRegion.isDisplayable()) {

                        Screen.region_object.getRegion(me);//

                    } else if (Screen.a1.drawPath.isSelected() && Screen.a1.drawPath.isDisplayable()) {
                        Screen.a16.getRegion(me);//

                    } else if (Screen.a1.jGoBackPage2.isDisplayable()) {
                        if (Screen.a9.selected_rect != 10000) {
                            Screen.textbox_object.translateText(me);//

                        } else {
                            Screen.textbox_object.selectText(me);//

                        }
                    } else if (Screen.a1.jGoBackPage3.isDisplayable()) {
                        if (Screen.a9.selected_rect != 10000) {
                            Screen.textbox_object.translateText(me);//

                        }
                    }
                } catch (NoninvertibleTransformException ex) {
                    Logger.getLogger(Image_Area_Listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Screen.a2.screenLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                System.out.println("mouse clicked");
                try {
                    Screen.start = Screen.a1.getOriginalZoomedCoordinate(e);
                    if (Screen.a1.drawLine.isSelected() && Screen.a1.drawLine.isDisplayable()) {
                        System.out.println("lines size : " + Screen.line_object.lines.size());
                        Screen.line_object.getLine(e); //
                    } else if (Screen.a1.drawCircle.isSelected() && Screen.a1.drawCircle.isDisplayable()) {
                        Screen.circle_object.getCircle(e); //
                    } else if (Screen.a1.drawArc.isSelected() && Screen.a1.drawArc.isDisplayable()) {
                        Screen.arc_object.getCircle(e); //
                    } else if (Screen.a1.drawPolygon.isSelected() && Screen.a1.drawPolygon.isDisplayable()) {
                        Screen.polygon_object.getPolygons(e); //
                    } else if (Screen.a1.drawRegion.isSelected() && Screen.a1.drawRegion.isDisplayable()) {

                    } else if (Screen.a1.drawPath.isSelected() && Screen.a1.drawPath.isDisplayable()) {

                    } //                if(Screen.a1.Edit.isSelected() && Screen.a1.Edit.isDisplayable()){
                    //                    Screen.textbox_object.addIndices(e);
                    //                }
                    else if (Screen.a1.jGoBackPage4.isDisplayable()) {
                        try {
                            Screen.line_object.addIndices(e);//
                            Screen.circle_object.addIndices(e);//
                            Screen.region_object.addIndices(e);//
                            Screen.polygon_object.addIndices(e);//
                            Screen.arc_object.addIndices(e);//
                            Screen.a16.addIndices(e);//
                        } catch (NoninvertibleTransformException ex) {
                            Logger.getLogger(Image_Area_Listeners.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    if (Screen.a1.jGoBackPage5.isDisplayable()) {
                        Screen.region_object.addColorIndices(e);
                        Screen.circle_object.addColorIndices(e);
                        Screen.line_object.addColorIndices(e);
                        Screen.polygon_object.addColorIndices(e);
                        Screen.a16.addColorIndices(e);
                        Screen.arc_object.addColorIndices(e);
                    }
                    if (Screen.a1.jGoBackPage3.isDisplayable()) {
                        Screen.a9.modify_selected_rect(e);
                        if (Screen.a9.selected_rect != 10000) {
                            Screen.rectangle_translate_start = Screen.textbox_object.rectangleArray.get(Screen.a9.selected_rect).getLocation();
                        }
                    }
                    if (Screen.a1.jGoBackPage2.isDisplayable()) {
                        Screen.a9.modify_selected_rect(e);
                        if (Screen.a9.selected_rect != 10000) {
                            Screen.rectangle_translate_start = Screen.textbox_object.rectangleArray.get(Screen.a9.selected_rect).getLocation();
                            Screen.a1.jLabel.setEditable(true);
                            Screen.a1.jSavePage2.setEnabled(true);
                            Screen.a1.jDeletePage2.setEnabled(true);
                            Screen.a1.jLabel.setEnabled(true);
                            Screen.a1.jLabel.requestFocus(true);
                            String language = Screen.textbox_object.languageArray.get(Screen.a9.selected_rect);
                            System.out.println("current box's language = " + language);
                            switch (language) {
                                case "hin":
                                    System.out.println("hindi font set");
                                    Screen.a1.jLabel.setFont(Screen.a1.hindiFont);
                                    break;
                                case "eng":
                                    System.out.println("english font set");
                                    Screen.a1.jLabel.setFont(Screen.a1.englishFont);
                                    break;
                                case "ben":
                                    System.out.println("bengali font set");
                                    Screen.a1.jLabel.setFont(Screen.a1.bengaliFont);
                                    break;
                                default:
                                    break;
                            }
                            if (Screen.textbox_object.label.get(Screen.a9.selected_rect).getR().contains("label -")) {
                                Screen.a1.jLabel.setText("");
                            } else {
                                String font_name = Screen.a1.jLabel.getFont().getFontName();
                                String text_to_be_set = Screen.textbox_object.label.get(Screen.a9.selected_rect).getR();
                                System.out.println("current font : " + font_name + " , current text : " + text_to_be_set);
                                Screen.a1.jLabel.setText(text_to_be_set);
                            }
                        }
                    }
                } catch (NoninvertibleTransformException ex) {
                    System.out.println("error");
                    Logger.getLogger(Image_Area_Listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
                t1.repaint(Screen.screen, Screen.a2.screenCopy);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);
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
                if (Screen.a1.jGoBackPage2.isDisplayable()) {
                    if (Screen.a9.selected_rect != 10000) {
                        Screen.a9.selected_rect = 10000;
                    } else {
                        try {
                            Screen.textbox_object.updateCaptureRectangle(me); //
                        } catch (NoninvertibleTransformException ex) {
                            Logger.getLogger(Image_Area_Listeners.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (Screen.a1.jGoBackPage3.isDisplayable()) {
                    if (Screen.a9.selected_rect != 10000) {
                        Screen.a9.selected_rect = 10000;
                    }
                }
                if (Screen.a1.drawRegion.isSelected() && Screen.a1.drawRegion.isDisplayable()) {
                    Screen.region_object.updateRegions();
                }
                if (Screen.a1.drawPath.isSelected() && Screen.a1.drawPath.isDisplayable()) {
                    Screen.a16.updateRegions();
                }
                t1.repaint(Screen.screen, Screen.a2.screenCopy);
                Screen.a2.jScrollPane1.setViewportView(Screen.a2.screenLabel);
            }
        });

        Screen.a1.jPreviewButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Screen.a1.jPreviewButton.isEnabled()) {
                    Screen.a1.jPreviewButton.setEnabled(false);
                    Screen.preview_frame.setBounds(0, 0, (Screen.main_frame.getWidth() * 3) / 4, Screen.main_frame.getHeight());
                    Screen.preview_frame.validate();
                    Screen.preview_frame.repaint();
                    t1.repaint(Screen.white, Screen.a2.whitecopy);
                    Screen.a2.preview_ScrollPane.setViewportView(Screen.a2.preview_Label);
                    Screen.preview_frame.setVisible(true);
                    Screen.preview_frame.validate();
                    Screen.preview_frame.repaint();
                    t1.repaint(Screen.white, Screen.a2.whitecopy);
                    Screen.a2.preview_ScrollPane.setViewportView(Screen.a2.preview_Label);
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
                if (!Screen.preview_frame.isVisible()) {
//                    Screen.a2.preview_frame.setBounds(0,0,(Screen.main_frame.getWidth()*3)/4,Screen.main_frame.getHeight());
//                    Screen.a2.preview_frame.validate();
//                    Screen.a2.preview_frame.repaint();
//                    t1.repaint(Screen.white, Screen.a2.whitecopy);
//                    Screen.a2.preview_ScrollPane.setViewportView(Screen.a2.preview_Label);
//                    Screen.a2.preview_frame.setVisible(true);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //Screen.a2.preview_frame.setVisible(false);
            }
        });
    }

}
