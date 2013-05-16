package muzak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.TreeSet;

import muzak.mycomp.ViewModDelObserver;
import muzak.mycomp.ViewModDelTools;
import muzakModel.DataModelObject;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import muzak.Configurations.Resources;
import muzak.mycomp.TablessTextArea;
import muzakModel.Artist;
import muzakModel.Release;

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
    
    public static VBox getArtistCard(Artist artist, Configurations config, TablessTextArea commentArea)
    {
        Text name = new Text(artist.getName());
        name.getStyleClass().addAll("main-title");
        
        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(15.0);
        
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setHalignment(HPos.RIGHT);
        pane.getColumnConstraints().addAll(col0);
        
        ResourceBundle res = config.getResources(Resources.ARTIST_DIALOG);
        pane.add(new Label(res.getString("ORIGIN")), 0, 0);
        pane.add(new Label(res.getString("FOUNDED")), 0, 1);
        pane.add(new Label(res.getString("ALIASES")), 0, 2);
        pane.add(new Label(res.getString("COMMENT")), 0, 3);
        
        pane.add(new Text(config.mapCodeToCountry(artist.getCountryCode())), 1, 0);
        pane.add(new Text(Integer.toString(artist.getFounded())), 1, 1);
        pane.add(new Text(artist.getAliasesString()), 1, 2);
        commentArea.setPrefHeight(80.0);
        pane.add(commentArea, 1, 3);
        
        VBox box = vLayout(10.0, name, pane);
        box.getStyleClass().addAll("glass-pane", "simple-display-entry");
        
        return box;
    }
    
    public static VBox getReleaseCard(Release release, Configurations config, TablessTextArea commentArea)
    {
        ResourceBundle res = config.getResources(Resources.RELEASE_DIALOG);
        
        String relinfo = (release.isOriginalRelease() ? res.getString("ORIGINAL") : res.getString("REISSUE"));
        relinfo += (release.getCurYear() > 0 ? ", " + Integer.toString(release.getCurYear()) : "");
        relinfo += (release.isExtendedEdition() ? ", " + res.getString("EXTENDED") : "");
        
        Text title = new Text(release.getTitle());
        title.getStyleClass().addAll("main-title");
        
        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(15.0);
        
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setHalignment(HPos.RIGHT);
        pane.getColumnConstraints().addAll(col0); 
        
        pane.add(title, 0, 0, 2, 1);
        pane.add(new Label(res.getString("ALT_TITLE")), 0, 1);
        pane.add(new Label(res.getString("CATALOG_NUMBER")), 0, 2);
        pane.add(new Label(res.getString("BARCODE")), 0, 3);
        pane.add(new Label(res.getString("RELEASE")), 0, 4);
        pane.add(new Label(res.getString("STYLE")), 0, 5);
        
        pane.add(new Text(release.getAltTitle()), 1, 1);
        pane.add(new Text(release.getCatalogNumber()), 1, 2);
        pane.add(new Text(release.getBarCode()), 1, 3);
        pane.add(new Text(relinfo), 1, 4);
        pane.add(new Text(config.mapKeyToStyle(release.getStyleKey())), 1, 5);
        
        ImageView iv = new ImageView(new Image("file:resources/noimage.jpg"));
        
        commentArea.setPrefHeight(80.0);
        
        VBox box = vLayout(10.0, hLayout(10.0, iv, pane), new Label(res.getString("COMMENT")), commentArea);
        box.getStyleClass().addAll("glass-pane", "simple-display-entry");
        
        return box;
    }
    
    public static void setFixedSize(Control component, double value)
    {
        component.setMinSize(value, value);
        component.setMaxSize(component.getMinWidth(), component.getMinHeight());
    }
    
    public static void setFixedHeight(Control component, double value)
    {
        component.setMinHeight(value);
        component.setMaxHeight(component.getMinHeight());
    }
    
    public static void setFixedWidth(Control component, double value)
    {
        component.setMinWidth(value);
        component.setMaxWidth(component.getMinWidth());
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
    
    public static void populate(ComboBox<KeyValueElement> cbox, TreeSet<DataModelObject> dmos)
    {
        ArrayList<KeyValueElement> values = new ArrayList<>();
        for(DataModelObject dmo : dmos)
            values.add(new KeyValueElement(dmo.getIDString(), dmo.getListString()));
        
        cbox.setItems(FXCollections.observableArrayList(values));
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
        box.setAlignment(Pos.CENTER_LEFT);
        
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
