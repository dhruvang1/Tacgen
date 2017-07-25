
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class circle_edit {
    public static void main(String[] args){
        ArrayList<Integer> line=new ArrayList<Integer>();
        line.add(3);
        line.add(2);
        line.add(1);
        line.remove((Integer)1);
        for(int y=0;y<line.size();y++){
            System.out.println(line.get(y));
        }
                
    }
    
}
