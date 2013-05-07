package muzak;

import java.util.ArrayList;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class UIUtils
{
    public static Region getHStretcher()
    {
        Region stretcher = new Region();
        HBox.setHgrow(stretcher, Priority.ALWAYS);
        
        return stretcher;
    }
    
    public static ColumnConstraints getColumnConstraint(double relativeWidth)
    {
        ColumnConstraints c = new ColumnConstraints();
        c.setPercentWidth(relativeWidth);
        
        return c;
    }
    
    public static ArrayList<ColumnConstraints> getColumnConstraints(int cnt)
    {
        double w = 100.0 / cnt;
        ColumnConstraints c;
        ArrayList<ColumnConstraints> constraints = new ArrayList<>();
        for(int i = 0; i < cnt; ++i)
        {
            c = new ColumnConstraints();
            c.setPercentWidth(w);
            
            constraints.add(c);
        }
        
        return constraints;
    }
}
