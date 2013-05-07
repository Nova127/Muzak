
package muzak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import muzak.Configurations.Resources;
import muzak.mycomp.TablessTextArea;

public class ArtistDialog extends AbstractPhasedDialog
{
    /* Phase 1 UI components: */
    private ToggleGroup             ui_typeOptions;
    private TextField               ui_nameField;
    private TextField               ui_aliasField;
    private ComboBox<KeyValueCombo> ui_originChoice;
    private ComboBox<String>        ui_foundedChoice;
    private TablessTextArea         ui_commentArea;
    private Button                  ui_discogsButton;
    /* Phase 2 - Discogs results - UI components: */
    /* Last phase UI components: */
    private Label                   ui_typeValue;
    private Label                   ui_nameValue;
    private Label                   ui_techNameValue;
    private Label                   ui_aliasesValue;
    private Label                   ui_originValue;
    private Label                   ui_foundedValue;
    private Label                   ui_commentValue;
    
    public ArtistDialog(final Configurations config)
    {
        super(config);
        createComponents();
        
        ResourceBundle res = config.getResources(Resources.ARTIST_DIALOG);

        super.addPhase(createArtistForm(res));
        super.addPhase(createArtistSummary(res));
        
        UIUtils.populate(ui_originChoice, config.getResources(Resources.LIST_OF_COUNTRIES));
        UIUtils.populate(ui_foundedChoice, config.getFoundedStartValue(), config.getFoundedEndValue());
        
        setTitle(res.getString("DIALOG_TITLE"));
        
        super.firstPhase();
    }
    
    public String getType()
    {
        return ((RadioButton)ui_typeOptions.getSelectedToggle()).getId();
    }
    
    public String getName()
    {
        return ui_nameField.getText();
    }
    
    public String getAliases()
    {
        return ui_aliasField.getText();
    }
    
    public String getOriginCode()
    {
        return ui_originChoice.getSelectionModel().getSelectedItem().getKey();
    }
    
    public String getFounded()
    {
        return ui_foundedChoice.getSelectionModel().getSelectedItem();
    }
    
    public String getComment()
    {
        return ui_commentArea.getText();
    }
    
    @Override
    protected void proceed()
    {
        if(isLastPhase())
            populateSummary();
    }
    
    @Override
    protected void rollBack()
    {
    }
    
    @Override
    protected Pane createDiscogsResultPane()
    {
        Pane p = new GridPane();
        return p;
    }
    
    private void populateSummary()
    {
        ui_typeValue.setText(getType());
        ui_nameValue.setText(getName());
        ui_techNameValue.setText(getName());
        ui_aliasesValue.setText(getAliases());
        
        KeyValueCombo origin = ui_originChoice.getSelectionModel().getSelectedItem();
        if(origin != null)
            ui_originValue.setText(origin.getValue());
        else
            ui_originValue.setText("");
        
        String founded = getFounded();
        if(founded != null)
            ui_foundedValue.setText(founded);
        else
            ui_foundedValue.setText("");
        
        ui_commentValue.setText(getComment());
    }

    private GridPane createArtistSummary(ResourceBundle res)
    {
        GridPane pane = new GridPane();
        pane.getStyleClass().setAll("glass-pane", "dialog-phase");
        pane.setHgap(10.0);
        pane.setVgap(10.0);
        
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(20.0);
        col0.setHalignment(HPos.RIGHT);
        pane.getColumnConstraints().add(col0);
        
        pane.add(new Label(res.getString("TYPE")), 0, 0);
        pane.add(new Label(res.getString("NAME")), 0, 1);
        pane.add(new Label(res.getString("TECH_NAME")), 0, 2);
        pane.add(new Label(res.getString("ALIASES")), 0, 3);
        pane.add(new Label(res.getString("ORIGIN")), 0, 4);
        pane.add(new Label(res.getString("FOUNDED")), 0, 5);
        pane.add(new Label(res.getString("COMMENT")), 0, 6);
        
        pane.add(ui_typeValue, 1, 0);
        pane.add(ui_nameValue, 1, 1);
        pane.add(ui_techNameValue, 1, 2);
        pane.add(ui_aliasesValue, 1, 3);
        pane.add(ui_originValue, 1, 4);
        pane.add(ui_foundedValue, 1, 5);
        pane.add(ui_commentValue, 1, 6);
        
        return pane;
    }
    
    private GridPane createArtistForm(ResourceBundle res)
    {
        RadioButton artistOption = new RadioButton(res.getString("ARTIST"));
        artistOption.setId("ARTIST");
        RadioButton bandOption = new RadioButton(res.getString("BAND"));
        bandOption.setId("BAND");
        bandOption.setSelected(true);
        RadioButton otherOption = new RadioButton(res.getString("OTHER"));
        otherOption.setId("OTHER");
        
        artistOption.setToggleGroup(ui_typeOptions);
        bandOption.setToggleGroup(ui_typeOptions);
        otherOption.setToggleGroup(ui_typeOptions);
        
        HBox optionsLayout = new HBox(15.0);
        optionsLayout.getChildren().addAll(bandOption, artistOption, otherOption);
        
        HBox discogsLayout = new HBox();
        discogsLayout.getChildren().addAll(UIUtils.getHStretcher(), ui_discogsButton, UIUtils.getHStretcher());
        
        GridPane pane = new GridPane();
        pane.getStyleClass().setAll("glass-pane", "dialog-phase");
        pane.setHgap(10.0);
        pane.setVgap(10.0);
        
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(20.0);
        col0.setHalignment(HPos.RIGHT);
        pane.getColumnConstraints().add(col0);
        
        pane.add(new Label(res.getString("TYPE")), 0, 0);
        pane.add(new Label(res.getString("NAME")), 0, 1);
        pane.add(new Label(res.getString("ALIASES")), 0, 2);
        pane.add(UIUtils.getHStretcher(), 0, 3);
        pane.add(new Label(res.getString("ORIGIN")), 0, 4);
        pane.add(new Label(res.getString("FOUNDED")), 0, 5);
        pane.add(new Label(res.getString("COMMENT")), 0, 6);
        pane.add(discogsLayout, 0, 7);
        
        pane.add(optionsLayout, 1, 0);
        pane.add(ui_nameField, 1, 1);
        pane.add(ui_aliasField, 1, 2);
        pane.add(new Text(res.getString("ALIAS_GUIDE")), 1, 3);
        pane.add(ui_originChoice, 1, 4);
        pane.add(ui_foundedChoice, 1, 5);
        pane.add(ui_commentArea, 1, 6);
        pane.add(UIUtils.getHStretcher(), 1, 7);
        
        return pane;
    }
    
    private void createComponents()
    {
        ui_typeOptions = new ToggleGroup();
        ui_nameField = new TextField();
        ui_aliasField = new TextField();
        ui_originChoice = new ComboBox<>();
        ui_foundedChoice = new ComboBox<>();
        ui_commentArea = new TablessTextArea();
        ui_commentArea.setPrefHeight(80.0);
        ui_commentArea.setWrapText(true);
        ui_discogsButton = getDiscogsButton();
        
        ui_typeValue = new Label();
        ui_nameValue = new Label();
        ui_techNameValue = new Label();
        ui_aliasesValue = new Label();
        ui_originValue = new Label();
        ui_foundedValue = new Label();
        ui_commentValue = new Label();
        ui_commentValue.setWrapText(true);
    }
}
