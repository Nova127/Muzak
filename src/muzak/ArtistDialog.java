
package muzak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import muzak.mycomp.TablessTextArea;

public class ArtistDialog extends AbstractPhasedDialog
{
    private ToggleGroup             ui_typeOptions;
    private TextField               ui_nameField;
    private TextField               ui_aliasField;
    private ComboBox<KeyValueCombo> ui_originChoice;
    private ComboBox<String>        ui_foundedChoice;
    private TablessTextArea         ui_commentArea;
    
    public ArtistDialog(final Locale locale, final Configurations config)
    {
        super(locale);
        ResourceBundle res = ResourceBundle.getBundle("bundles.ArtistDialog", locale);
        addPhase(createArtistForm(res));
        
        populateOrigins(locale);
        populateFoundeds(config);
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
    }

    @Override
    protected void rollBack()
    {
    }
    
    private void populateOrigins(final Locale locale)
    {
        ResourceBundle loc = ResourceBundle.getBundle("bundles.ListOfCountries", locale);
        
        ArrayList<KeyValueCombo> values = new ArrayList<>();
        for(String key : loc.keySet())
            values.add(new KeyValueCombo(key, loc.getString(key)));
        
        Collections.sort(values);
        
        ui_originChoice.setItems(FXCollections.observableArrayList(values));
    }
    
    private void populateFoundeds(final Configurations config)
    {
        ArrayList<String> list = new ArrayList<>();
        for(int i = config.getFoundedEndValue(); i >= config.getFoundedStartValue(); --i)
            list.add(Integer.toString(i));
        
        ui_foundedChoice.setItems(FXCollections.observableList(list));
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
        ui_typeOptions = new ToggleGroup();
        artistOption.setToggleGroup(ui_typeOptions);
        bandOption.setToggleGroup(ui_typeOptions);
        otherOption.setToggleGroup(ui_typeOptions);
        
        HBox optionsLayout = new HBox(15.0);
        optionsLayout.getChildren().addAll(bandOption, artistOption, otherOption);
        
        ui_nameField = new TextField();
        ui_aliasField = new TextField();
        ui_originChoice = new ComboBox<>();
        ui_foundedChoice = new ComboBox<>();
        ui_commentArea = new TablessTextArea();
        ui_commentArea.setPrefHeight(80.0);
        ui_commentArea.setWrapText(true);
        
        GridPane pane = new GridPane();
        pane.getStyleClass().setAll("dialog-phase");
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
        pane.add(new Button("Discogs"), 0, 7);

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
}
