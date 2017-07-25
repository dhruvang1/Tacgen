
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

public class get_circles {

    boolean fpc = false;
    boolean spc = false;
    Point s1, s2, s3;
    ArrayList<Pair<Float, Float>> Circles = new ArrayList<Pair<Float, Float>>();
    ArrayList<Pair<Float, Float>> Centers = new ArrayList<Pair<Float, Float>>();
    ArrayList<Integer> circle_indices = new ArrayList<Integer>();
    ArrayList<Float> Radius = new ArrayList<Float>();
    ArrayList<Integer> fill_or_not = new ArrayList<Integer>();
    ArrayList<Color> color_array = new ArrayList<Color>();

    Pair<Float, Float> A, B, C;
    float radius;
    int j = 0;
    int r = 0;
    SCR t1 = new SCR(r);

    public get_circles() {
        // TODO Auto-generated constructor stub
    }

    public void get_circle(MouseEvent e) throws NoninvertibleTransformException {
        float x = (float) 0.0;
        float y = (float) 0.0;
        Pair<Float, Float> temp1 = new Pair<Float, Float>(x, y);
        Pair<Float, Float> temp2 = new Pair<Float, Float>(x, y);
        Pair<Float, Float> temp3 = new Pair<Float, Float>(x, y);
        Pair<Float, Float> center = new Pair<Float, Float>(x, y);

        if (!fpc) {
            s1 = SCR.a1.get_original_point_coordinate(e);
            fpc = true;
            temp1.setL((float) s1.x);
            temp1.setR((float) s1.y);
            s2 = null;
            s3 = null;
            //	System.out.println("Started :- "+ s1+" , "+s2+","+s3);
            Circles.add(temp1);
            A = temp1;

        } else if (!spc) {
            s2 = SCR.a1.get_original_point_coordinate(e);
            if (SCR.line_object.calc_distance(s2.x, s2.y, s1.x, s1.y) > 5) {
                temp2.setL((float) s2.x);
                temp2.setR((float) s2.y);
                Circles.add(temp2);
                spc = true;
                B = temp2;
            }
        } else {
            s3 = SCR.a1.get_original_point_coordinate(e);
            if ((SCR.line_object.calc_distance(s2.x, s2.y, s3.x, s3.y) > 5) && (SCR.line_object.calc_distance(s3.x, s3.y, s1.x, s1.y) > 5)
                    && !SCR.line_object.collinear(s1.x, s1.y, s2.x, s2.y, s3.x, s3.y)) {
                temp3.setL((float) s3.x);
                temp3.setR((float) s3.y);
                Circles.add(temp3);
                fpc = spc = false;
                C = temp3;
                circle a = new circle();
                int[] b = a.calc((int) (float) A.getL(), (int) (float) A.getR(), (int) (float) B.getL(), (int) (float) B.getR(), (int) (float) C.getL(), (int) (float) C.getR());
                center.setL((float) (int) b[0]);
                center.setR((float) (int) b[1]);
                Centers.add(center);
                radius = a.get_radius(center, C);
                Radius.add(radius);
                color_array.add(SCR.current_color);
                fill_or_not.add(0);
                //System.out.println(radius);
                Circles.clear();
            }
            t1.repaint(SCR.screen, SCR.a2.screenCopy);
            SCR.a2.jScrollPane1.setViewportView(SCR.a2.screenLabel);  //SCR.a2.screenLabel.repaint();
        }

    }

    public void delete_temp() {
        fpc = false;
        spc = false;
        Circles.clear();
    }

    public void add_indices(MouseEvent e) throws NoninvertibleTransformException {
        Point p = SCR.a1.get_original_point_coordinate(e);
        for (int i = 0; i < Centers.size(); i++) {
            float dist = (float) (Math.pow((p.x - Centers.get(i).getL()), 2) + Math.pow((p.y - Centers.get(i).getR()), 2));
            if (dist < Math.pow(Radius.get(i), 2)) {
                if (circle_indices.contains(i)) {
                    circle_indices.remove((Integer) i);
                } else {
                    circle_indices.add(i);
                }
            }
        }
    }

    public void add_color_indices(MouseEvent e) throws NoninvertibleTransformException {
        Point p = SCR.a1.get_original_point_coordinate(e);
        for (int i = 0; i < Centers.size(); i++) {
            float dist = (float) (Math.pow((p.x - Centers.get(i).getL()), 2) + Math.pow((p.y - Centers.get(i).getR()), 2));
            if (dist < Math.pow(Radius.get(i), 2)) {
                if (fill_or_not.get(i) == 1 && color_array.get(i) != SCR.current_color) {
                    color_array.set(i, SCR.current_color);
                    fill_or_not.set(i, 1);
                } else {
                    color_array.set(i, SCR.current_color);
                    fill_or_not.set(i, (fill_or_not.get(i) + 1) % 2);
                }
            }
            fill_or_not.set(i, 0);  // to remove fill
        }
    }

    public void delete_indices() {
//           ArrayList<Pair<Float,Float>> temp_Circles=new ArrayList<Pair<Float,Float>>();
//           ArrayList<Pair<Float,Float>> temp_Centers=new ArrayList<Pair<Float,Float>>();
//           ArrayList<Float> temp_Radius=new ArrayList<Float>();
        circle_indices.sort(null);
        for (int i = circle_indices.size() - 1; i >= 0; i--) {
//                temp_Circles.add(Circles.get(3*circle_indices.get(i)));
//                temp_Circles.add(Circles.get(3*circle_indices.get(i)+1));
//                temp_Circles.add(Circles.get(3*circle_indices.get(i)+2));
            color_array.remove((int) circle_indices.get(i));

            fill_or_not.remove((int) circle_indices.get(i));
            Centers.remove((int) circle_indices.get(i));
            Radius.remove((int) circle_indices.get(i));
        }
//            Circles.removeAll(temp_Circles);
//            Centers.removeAll(temp_Centers);
//            Radius.removeAll(temp_Radius);           
        circle_indices.clear();
    }
}
