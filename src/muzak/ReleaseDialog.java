
package muzak;

import java.util.ResourceBundle;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import muzak.mycomp.MultiSelectionListView;
import muzak.mycomp.TablessTextArea;

public class ReleaseDialog extends AbstractPhasedDialog
{
    private TextField               ui_titleField;
    private TextField               ui_altTitleField;
    private TextField               ui_catNumberField;
    private TextField               ui_barCodeField;
    private MultiSelectionListView  ui_typeList;
    private MultiSelectionListView  ui_mediaList;
    private ToggleGroup             ui_releaseOptions;
    private CheckBox                ui_extendedOption;
    private ComboBox<String>        ui_orgYearChoice;
    private ComboBox<String>        ui_curYearChoice;
    private ComboBox<KeyValueCombo> ui_styleChoice;
    private TablessTextArea         ui_commentArea;
    
    public ReleaseDialog(final Configurations config)
    {
        super(config);
        createComponents();
        
        ResourceBundle res = config.getResources(Configurations.Resources.RELEASE_DIALOG);

        super.addPhase(createReleaseForm(res));
        
        super.firstPhase();
    }

    @Override
    protected void proceed() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void rollBack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Pane createDiscogsResultPane() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private GridPane createReleaseSummary()
    {
        GridPane pane = new GridPane();
        return pane;
    }
    
    private GridPane createReleaseForm(ResourceBundle res)
    {
        GridPane listSelectionsLayout = new GridPane();
        listSelectionsLayout.setHgap(10.0);
        listSelectionsLayout.setVgap(10.0);
        
        listSelectionsLayout.getColumnConstraints().addAll(UIUtils.getColumnConstraints(2));
        
        listSelectionsLayout.add(new Label(res.getString("TYPE")), 0, 0);
        listSelectionsLayout.add(new Label(res.getString("MEDIA")), 1, 0);
        listSelectionsLayout.add(ui_typeList, 0, 1);
        listSelectionsLayout.add(ui_mediaList, 1, 1);
        
        GridPane yearLayout = new GridPane();
        yearLayout.getColumnConstraints().addAll(UIUtils.getColumnConstraints(4));
        
        yearLayout.add(new Label(res.getString("ORIGINAL")), 0, 0);
        yearLayout.add(ui_orgYearChoice, 1, 0);
        yearLayout.add(new Label(res.getString("CURRENT")), 2, 0);
        yearLayout.add(ui_curYearChoice, 3, 0);
        
        RadioButton orgOpt = new RadioButton(res.getString("ORIGINAL"));
        orgOpt.setId("ORIGINAL");
        orgOpt.setSelected(true);
        RadioButton reiOpt = new RadioButton(res.getString("REISSUE"));
        reiOpt.setId("REISSUE");
        
        orgOpt.setToggleGroup(ui_releaseOptions);
        reiOpt.setToggleGroup(ui_releaseOptions);
        
        ui_extendedOption.setText(res.getString("EXTENDED"));
        
        HBox releaseLayout = new HBox(15.0);
        releaseLayout.getChildren().addAll(orgOpt, reiOpt, UIUtils.getHStretcher(), ui_extendedOption);
        
        GridPane pane = new GridPane();
        pane.getStyleClass().setAll("glass-pane", "dialog-phase");
        pane.setHgap(10.0);
        pane.setVgap(10.0);
        
        pane.add(new Label(res.getString("TITLE")), 0, 0);
        pane.add(new Label(res.getString("ALT_TITLE")), 0, 1);
        pane.add(new Label(res.getString("CATALOG_NUMBER")), 0, 2);
        pane.add(new Label(res.getString("BARCODE")), 0, 3);
        pane.add(new Label(res.getString("RELEASE")), 0, 5);
        pane.add(new Label(res.getString("STYLE")), 0, 7);
        pane.add(new Label(res.getString("COMMENT")), 0, 8);
        
        pane.add(ui_titleField, 1, 0);
        pane.add(ui_altTitleField, 1, 1);
        pane.add(ui_catNumberField, 1, 2);
        pane.add(ui_barCodeField, 1, 3);
        pane.add(listSelectionsLayout, 1, 4);
        pane.add(releaseLayout, 1, 5);
        pane.add(yearLayout, 1, 6);
        pane.add(ui_styleChoice, 1, 7);
        pane.add(ui_commentArea, 1, 8);
        
        return pane;
    }
    
    private void createComponents()
    {
        ui_titleField = new TextField();
        ui_altTitleField = new TextField();
        ui_catNumberField = new TextField();
        ui_barCodeField = new TextField();
        
        ui_typeList = new MultiSelectionListView();
        ui_typeList.setPrefHeight(80.0);
        
        ui_mediaList = new MultiSelectionListView();
        ui_mediaList.setPrefHeight(80.0);
        
        ui_releaseOptions = new ToggleGroup();
        ui_extendedOption = new CheckBox();
        
        ui_orgYearChoice = new ComboBox<>();
        ui_curYearChoice = new ComboBox<>();
        
        ui_styleChoice = new ComboBox<>();
        
        ui_commentArea = new TablessTextArea();
        ui_commentArea.setPrefHeight(80.0);
    }
}
