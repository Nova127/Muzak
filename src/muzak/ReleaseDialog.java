
package muzak;

import java.util.ResourceBundle;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import muzak.Configurations.Resources;
import muzak.mycomp.IntegerSpinnerPane;
import muzak.mycomp.MultiSelectionListView;
import muzak.mycomp.TablessTextArea;

public class ReleaseDialog extends AbstractPhasedDialog
{
    private TextField               ui_titleField           = new TextField();
    private TextField               ui_altTitleField        = new TextField();
    private TextField               ui_catNumberField       = new TextField();
    private TextField               ui_barCodeField         = new TextField();
    private MultiSelectionListView  ui_typeList             = new MultiSelectionListView();
    private MultiSelectionListView  ui_mediaList            = new MultiSelectionListView();
    private ToggleGroup             ui_releaseOptions       = new ToggleGroup();
    private CheckBox                ui_extendedOption       = new CheckBox();
    private ComboBox<String>        ui_orgYearChoice        = new ComboBox<>();
    private ComboBox<String>        ui_curYearChoice        = new ComboBox<>();
    private IntegerSpinnerPane      ui_discCount            = new IntegerSpinnerPane();
    private ComboBox<KeyValueCombo> ui_styleChoice          = new ComboBox<>();
    private ComboBox<KeyValueCombo> ui_ratingChoice         = new ComboBox<>();
    private TablessTextArea         ui_commentArea          = new TablessTextArea();
    private Button                  ui_discogsButton        = getDiscogsButton();
    
    public ReleaseDialog(final Configurations config)
    {
        super(config);
        
        ResourceBundle res = config.getResources(Resources.RELEASE_DIALOG);

        super.addPhase(createReleaseForm(res));
        
        UIUtils.populate(ui_styleChoice, config.getResources(Resources.LIST_OF_STYLES));
        UIUtils.populate(ui_curYearChoice, config.getReleasedStartValue(), config.getReleasedEndValue());
        UIUtils.populate(ui_orgYearChoice, config.getReleasedStartValue(), config.getReleasedEndValue());
        ui_typeList.insertSelectionElements(config.getResources(Resources.LIST_OF_RELEASE_TYPES));
        ui_mediaList.insertSelectionElements(config.getResources(Resources.LIST_OF_RELEASE_MEDIA));
        
        super.firstPhase();
    }
    
    @Override
    protected void proceed()
    {
    }

    @Override
    protected void rollBack()
    {
    }
    
    @Override
    protected Pane createDiscogsResultPane()
    {
        return null;
    }
    
    private GridPane createReleaseSummary()
    {
        GridPane pane = new GridPane();
        return pane;
    }
    
    private GridPane createReleaseForm(ResourceBundle res)
    {
        ui_typeList.setPrefHeight(80.0);
        ui_mediaList.setPrefHeight(80.0);
        
        ui_extendedOption.setText(res.getString("EXTENDED"));
        
        ui_discCount.setMaxWidth(50.0);
        ui_discCount.setMinValue(0);
        ui_discCount.setDefValue(1);

        ui_commentArea.setPrefHeight(80.0);
        
        RadioButton orgOpt = new RadioButton(res.getString("ORIGINAL"));
        orgOpt.setId("ORIGINAL");
        orgOpt.setSelected(true);
        RadioButton reiOpt = new RadioButton(res.getString("REISSUE"));
        reiOpt.setId("REISSUE");
        
        orgOpt.setToggleGroup(ui_releaseOptions);
        reiOpt.setToggleGroup(ui_releaseOptions);
        
        GridPane pane = new GridPane();
        pane.getStyleClass().setAll("glass-pane", "dialog-phase");
        pane.setHgap(10.0);
        pane.setVgap(15.0);
        
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setHalignment(HPos.RIGHT);
        pane.getColumnConstraints().addAll(col0);
        
        pane.add(new Label(res.getString("TITLE")), 0, 0);
        pane.add(new Label(res.getString("ALT_TITLE")), 0, 1);
        pane.add(new Label(res.getString("CATALOG_NUMBER")), 0, 2);
        pane.add(new Label(res.getString("BARCODE")), 0, 3);
        pane.add(new Label(res.getString("RELEASE")), 0, 5);
        pane.add(new Label(res.getString("YEAR_OF_RELEASE")), 0, 6);
        pane.add(new Label(res.getString("CURRENT")), 1, 6);
        pane.add(new Label(res.getString("ORIGINAL")), 3, 6);
        pane.add(new Label(res.getString("DISCS")), 0, 7);
        pane.add(new Label(res.getString("STYLE")), 0, 8);
        pane.add(new Label(res.getString("RATING")), 0, 9);
        pane.add(new Label(res.getString("COMMENT")), 0, 10);
        pane.add(UIUtils.hLayout(0, UIUtils.getHStretcher(), ui_discogsButton, UIUtils.getHStretcher()), 0, 11);
        
        pane.add(ui_titleField, 1, 0, 4, 1);
        pane.add(ui_altTitleField, 1, 1, 4, 1);
        pane.add(ui_catNumberField, 1, 2, 4, 1);
        pane.add(ui_barCodeField, 1, 3, 4, 1);
        pane.add(UIUtils.vLayout(10.0, new Label(res.getString("TYPE")), ui_typeList), 1, 4, 2, 1);
        pane.add(UIUtils.vLayout(10.0, new Label(res.getString("MEDIA")), ui_mediaList), 3, 4, 2, 1);
        pane.add(UIUtils.hLayout(15.0, orgOpt, reiOpt, UIUtils.getHStretcher(), ui_extendedOption), 1, 5, 4, 1);
        pane.add(ui_curYearChoice, 2, 6);
        pane.add(ui_orgYearChoice, 4, 6);
        pane.add(ui_discCount, 1, 7, 4, 1);
        pane.add(ui_styleChoice, 1, 8, 4, 1);
        pane.add(ui_ratingChoice, 1, 9, 4, 1);
        pane.add(ui_commentArea, 1, 10, 4, 1);
        
        return pane;
    }
}
