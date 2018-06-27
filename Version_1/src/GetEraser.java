import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;

public class GetEraser {
    private int currentX, currentY;
    private Screen screen = new Screen(0);
    public Rectangle captureRectangle;

    public void eraserActive(MouseEvent e) throws NoninvertibleTransformException{
        Point p = Screen.allControlsAndListeners.getOriginalZoomedCoordinate(e);
        currentX = p.x;
        currentY = p.y;
        int w = currentX - Screen.start.x;
        int h = currentY - Screen.start.y;
        captureRectangle = new Rectangle(Screen.start.x,Screen.start.y,w,h);
        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
    }

    public void eraserDeactivate(MouseEvent e) throws NoninvertibleTransformException {
        captureRectangle = null;
        screen.repaint(Screen.bufferedImageScreen, Screen.initialFrameSetup.screenCopy);
        Screen.initialFrameSetup.jScrollPane1.setViewportView(Screen.initialFrameSetup.screenLabel);
    }

}
