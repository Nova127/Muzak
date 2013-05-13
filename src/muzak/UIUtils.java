package muzak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import muzak.mycomp.ViewModDelObserver;
import muzak.mycomp.ViewModDelTools;
import muzakModel.DataModelObject;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UIUtils
{
    public static Pane getListInfoElement(DataModelObject dmo, ViewModDelObserver observer)
    {
        Text text = new Text(dmo.getShortInfoString());
        text.getStyleClass().addAll("main-title");
        
        ViewModDelTools tools = new ViewModDelTools(observer);
        tools.setUserData(dmo);
        
        HBox box = hLayout(10.0, text, getHStretcher(), tools);
        box.getStyleClass().addAll("glass-pane", "simple-display-entry");
        
        return box;
    }
    
    public static void setFixedSize(Control component, double value)
    {
        component.setMinSize(value, value);
        component.setMaxSize(component.getMinWidth(), component.getMinHeight());
    }
    
    public static void populate(ComboBox<KeyValueCombo> cbox, ResourceBundle res)
    {
        ArrayList<KeyValueCombo> values = new ArrayList<>();
        for(String key : res.keySet())
            values.add(new KeyValueCombo(key, res.getString(key)));
        
        Collections.sort(values);
        
        cbox.setItems(FXCollections.observableArrayList(values));
    }
    
    public static void populate(ComboBox<String> cbox, int begin, int end)
    {
        ArrayList<String> list = new ArrayList<>();
        for(int i = end; i >= begin; --i)
            list.add(Integer.toString(i));
        
        cbox.setItems(FXCollections.observableList(list));
    }
    
    public static Region getHStretcher()
    {
        Region stretcher = new Region();
        HBox.setHgrow(stretcher, Priority.ALWAYS);
        
        return stretcher;
    }
    
    public static Region getVStretcher()
    {
        Region stretcher = new Region();
        VBox.setVgrow(stretcher, Priority.ALWAYS);
        
        return stretcher;
    }
    
    public static HBox hLayout(double spacing, Node... nodes)
    {
        HBox box = new HBox(spacing);
        box.getChildren().addAll(nodes);
        
        return box;
    }
    
    public static HBox hLayoutRight(Node node)
    {
        return hLayout(0, getHStretcher(), node);
    }
    
    public static HBox hLayoutLeft(Node node)
    {
        return hLayout(0, node, getHStretcher());
    }
    
    public static HBox hLayoutCentered(Node node)
    {
        return hLayout(0, getHStretcher(), node, getHStretcher());
    }
    
    public static VBox vLayout(double spacing, Node... nodes)
    {
        VBox box = new VBox(spacing);
        box.getChildren().addAll(nodes);
        
        return box;
    }
    
    public static VBox vLayoutCentered(Node node)
    {
        return vLayout(0, getVStretcher(), node, getVStretcher());
    }
    
    public static VBox vLayoutCenterCenter(Node node)
    {
        return vLayoutCentered(hLayoutCentered(node));
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
