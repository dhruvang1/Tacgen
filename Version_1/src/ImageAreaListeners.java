import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.NoninvertibleTransformException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to setup image area listeners
 */
public class ImageAreaListeners {

    private Screen screen = new Screen(0);

    public ImageAreaListeners() {
        add();
    }

    public void add() {
        Screen.initialFrameSetup.screenLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                try {
                    Screen.start = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(me);
                    screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                    Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
                } catch (NoninvertibleTransformException ex) {
                    Logger.getLogger(ImageAreaListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                try {
                    if (Screen.allControlsAndListeners.drawRegion.isSelected() && Screen.allControlsAndListeners.drawRegion.isDisplayable()) {
                        Screen.regionsObject.getRegion(me);
                    } else if (Screen.allControlsAndListeners.drawPath.isSelected() && Screen.allControlsAndListeners.drawPath.isDisplayable()) {
                        Screen.pathsObject.getPath(me);
                    } else if (Screen.allControlsAndListeners.jGoBackPage2.isDisplayable()) {
                        if (Screen.modifyTextObject.selectedRectangle != 10000) {
                            Screen.textboxObject.translateText(me);//
                        } else {
                            Screen.textboxObject.selectText(me);//
                        }
                    } else if (Screen.allControlsAndListeners.jGoBackPage3.isDisplayable()) {
                        if (Screen.modifyTextObject.selectedRectangle != 10000) {
                            Screen.textboxObject.translateText(me);
                        }
                    } else if (Screen.allControlsAndListeners.jSkipPage1.isDisplayable()) {
                        Screen.eraserObject.eraserActive(me);
                    }
                } catch (NoninvertibleTransformException ex) {
                    Logger.getLogger(ImageAreaListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Screen.initialFrameSetup.screenLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("mouse clicked");
                try {
                    Screen.start = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
                    if (Screen.allControlsAndListeners.drawLine.isSelected() && Screen.allControlsAndListeners.drawLine.isDisplayable()) {
                        System.out.println("lines size : " + Screen.linesObject.lines.size());
                        Screen.linesObject.getLine(e); //
                    } else if (Screen.allControlsAndListeners.drawCircle.isSelected() && Screen.allControlsAndListeners.drawCircle.isDisplayable()) {
                        Screen.circlesObject.getCircle(e); //
                    } else if (Screen.allControlsAndListeners.drawArc.isSelected() && Screen.allControlsAndListeners.drawArc.isDisplayable()) {
                        Screen.arcObject.getCircle(e); //
                    } else if (Screen.allControlsAndListeners.drawPolygon.isSelected() && Screen.allControlsAndListeners.drawPolygon.isDisplayable()) {
                        Screen.polygonObject.getPolygons(e); //
                    }
                    else if (Screen.allControlsAndListeners.jGoBackPage4.isDisplayable()) {
                        try {
                            Screen.linesObject.addIndices(e);
                            Screen.circlesObject.addIndices(e);
                            Screen.regionsObject.addIndices(e);
                            Screen.polygonObject.addIndices(e);
                            Screen.arcObject.addIndices(e);
                            Screen.pathsObject.addIndices(e);
                        } catch (NoninvertibleTransformException ex) {
                            Logger.getLogger(ImageAreaListeners.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    if (Screen.allControlsAndListeners.jGoBackPage5.isDisplayable()) {
                        Screen.regionsObject.addColorIndices(e);
                        Screen.circlesObject.addColorIndices(e);
                        Screen.linesObject.addColorIndices(e);
                        Screen.polygonObject.addColorIndices(e);
                        Screen.pathsObject.addColorIndices(e);
                        Screen.arcObject.addColorIndices(e);
                    }
                    if (Screen.allControlsAndListeners.jGoBackPage3.isDisplayable()) {
                        Screen.modifyTextObject.modifySelectedRectangle(e);
                        if (Screen.modifyTextObject.selectedRectangle != 10000) {
                            Screen.rectangleTranslateStart = Screen.textboxObject.rectangleArray.get(Screen.modifyTextObject.selectedRectangle).getLocation();
                        }
                    }
                    if (Screen.allControlsAndListeners.jGoBackPage2.isDisplayable()) {
                        Screen.modifyTextObject.modifySelectedRectangle(e);
                        if (Screen.modifyTextObject.selectedRectangle != 10000) {
                            Screen.rectangleTranslateStart = Screen.textboxObject.rectangleArray.get(Screen.modifyTextObject.selectedRectangle).getLocation();
                            Screen.allControlsAndListeners.jLabel.setEditable(true);
                            Screen.allControlsAndListeners.jSavePage2.setEnabled(true);
                            Screen.allControlsAndListeners.jDeletePage2.setEnabled(true);
                            Screen.allControlsAndListeners.jLabel.setEnabled(true);
                            Screen.allControlsAndListeners.jLabel.requestFocus(true);
                            String language = Screen.textboxObject.languageArray.get(Screen.modifyTextObject.selectedRectangle);
                            System.out.println("current box's language = " + language);
                            switch (language) {
                                case "hin":
                                    System.out.println("hindi font set");
                                    Screen.allControlsAndListeners.jLabel.setFont(Screen.allControlsAndListeners.hindiFont);
                                    break;
                                case "eng":
                                    System.out.println("english font set");
                                    Screen.allControlsAndListeners.jLabel.setFont(Screen.allControlsAndListeners.englishFont);
                                    break;
                                case "ben":
                                    System.out.println("bengali font set");
                                    Screen.allControlsAndListeners.jLabel.setFont(Screen.allControlsAndListeners.bengaliFont);
                                    break;
                                default:
                                    break;
                            }
                            if (Screen.textboxObject.label.get(Screen.modifyTextObject.selectedRectangle).getR().contains("label -")) {
                                Screen.allControlsAndListeners.jLabel.setText("");
                            } else {
                                String font_name = Screen.allControlsAndListeners.jLabel.getFont().getFontName();
                                String text_to_be_set = Screen.textboxObject.label.get(Screen.modifyTextObject.selectedRectangle).getR();
                                System.out.println("current font : " + font_name + " , current text : " + text_to_be_set);
                                Screen.allControlsAndListeners.jLabel.setText(text_to_be_set);
                            }
                        }
                    }
                } catch (NoninvertibleTransformException ex) {
                    System.out.println("error");
                    Logger.getLogger(ImageAreaListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {}

            @Override
            public void mouseExited(MouseEvent arg0) {}

            @Override
            public void mousePressed(MouseEvent arg0) {}

            @Override
            public void mouseReleased(MouseEvent me) {
                if (Screen.allControlsAndListeners.jGoBackPage2.isDisplayable()) {
                    if (Screen.modifyTextObject.selectedRectangle != 10000) {
                        Screen.modifyTextObject.selectedRectangle = 10000;
                    } else {
                        try {
                            Screen.textboxObject.updateCaptureRectangle(me); //
                        } catch (NoninvertibleTransformException ex) {
                            Logger.getLogger(ImageAreaListeners.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (Screen.allControlsAndListeners.jGoBackPage3.isDisplayable()) {
                    if (Screen.modifyTextObject.selectedRectangle != 10000) {
                        Screen.modifyTextObject.selectedRectangle = 10000;
                    }
                } else if (Screen.allControlsAndListeners.jSkipPage1.isDisplayable()){
                    try{
                        Screen.eraserObject.eraserDeactivate(me);
                    } catch (NoninvertibleTransformException ex) {
                        Logger.getLogger(ImageAreaListeners.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (Screen.allControlsAndListeners.drawRegion.isSelected() && Screen.allControlsAndListeners.drawRegion.isDisplayable()) {
                    Screen.regionsObject.updateRegions();
                }
                if (Screen.allControlsAndListeners.drawPath.isSelected() && Screen.allControlsAndListeners.drawPath.isDisplayable()) {
                    Screen.pathsObject.updateRegions();
                }
                screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
                Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
            }
        });

        Screen.allControlsAndListeners.jPreviewButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Screen.allControlsAndListeners.jPreviewButton.isEnabled()) {
                    Screen.allControlsAndListeners.jPreviewButton.setEnabled(false);
                    Screen.previewFrame.setBounds(0, 0, (Screen.mainFrame.getWidth() * 3) / 4, Screen.mainFrame.getHeight());
                    Screen.previewFrame.validate();
                    Screen.previewFrame.repaint();
                    screen.repaint(Screen.bufferedImageWhite, Screen.initialFrameSetup.whitecopy);
                    Screen.initialFrameSetup.previewScrollPane.setViewportView(Screen.initialFrameSetup.previewLabel);
                    Screen.previewFrame.setVisible(true);
                    Screen.previewFrame.validate();
                    Screen.previewFrame.repaint();
                    screen.repaint(Screen.bufferedImageWhite, Screen.initialFrameSetup.whitecopy);
                    Screen.initialFrameSetup.previewScrollPane.setViewportView(Screen.initialFrameSetup.previewLabel);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

}
