
package muzak;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import muzak.Configurations.Resources;
import muzak.mycomp.TablessTextArea;

public class ArtistDialog extends AbstractPhasedDialog
{
    /* Phase 1 UI components: */
    private ToggleGroup             ui_typeOptions          = new ToggleGroup();
    private TextField               ui_nameField            = new TextField();
    private Button                  ui_autoNameButton       = new Button();
    private TextField               ui_techNameField        = new TextField();
    private TextField               ui_aliasField           = new TextField();
    private ComboBox<KeyValueCombo> ui_originChoice         = new ComboBox<>();
    private ComboBox<String>        ui_foundedChoice        = new ComboBox<>();
    private TablessTextArea         ui_commentArea          = new TablessTextArea();
    /* Last phase UI components: */
    private Label                   ui_typeValue            = new Label();
    private Label                   ui_nameValue            = new Label();
    private Label                   ui_techNameValue        = new Label();
    private Label                   ui_aliasesValue         = new Label();
    private Label                   ui_originValue          = new Label();
    private Label                   ui_foundedValue         = new Label();
    private Label                   ui_commentValue         = new Label();
    
    public ArtistDialog(final Configurations config, final DialogObserver observer)
    {
        super(config, observer);
        
        ResourceBundle res = config.getResources(Resources.ARTIST_DIALOG);

        super.addPhase(createArtistForm(res));
        super.addPhase(createArtistSummary(res));
        
        UIUtils.populate(ui_originChoice, config.getResources(Resources.LIST_OF_COUNTRIES));
        UIUtils.populate(ui_foundedChoice, config.getFoundedStartValue(), config.getFoundedEndValue());
        
        setTitle(res.getString("DIALOG_TITLE"));
        
        super.prepare();
    }
    
    public String getType()
    {
        RadioButton opt = (RadioButton)ui_typeOptions.getSelectedToggle();
        
        return (opt != null ? opt.getId() : "OTHER");
    }
    
    public String getName()
    {
        return MyUtils.trimWhitespaces(ui_nameField.getText());
    }
    
    public String getTechName()
    {
        String name = getName();
        
        if(name.isEmpty())
        {
            return "";
        }
        else
        {
            String tech = ui_techNameField.getText();
            
            if(tech.isEmpty())
                return makeTechName();
            else
                return MyUtils.trimWhitespaces(tech);
        }
    }
    
    public ArrayList<String> getAliases()
    {
        ArrayList<String> aliases = new ArrayList<>();
        
        if(ui_aliasField.getText().trim().isEmpty())
            return aliases;
        
        for(String s : ui_aliasField.getText().split(";"))
            aliases.add(MyUtils.trimWhitespaces(s));
        
        return aliases;
    }
    
    public String getOriginCode()
    {
        KeyValueCombo kvc = ui_originChoice.getSelectionModel().getSelectedItem();
        
        return (kvc != null ? kvc.getKey() : "");
    }
    
    public int getFounded()
    {
        String val = ui_foundedChoice.getSelectionModel().getSelectedItem();

        return (val != null ? Integer.parseInt(val) : -1);
    }
    
    public String getComment()
    {
        return MyUtils.trimWhitespaces(ui_commentArea.getText());
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
    
    private String makeTechName()
    {
        String tech = "";
        String name = ui_nameField.getText();
        
        String type = getType();
        
        if(type.equals("BAND"))
        {
            tech = MyUtils.trimArticles(name);
        }
        else if(type.equals("ARTIST"))
        {
            tech = MyUtils.artistTechName(name);
        }
        else
            tech = MyUtils.trimWhitespaces(name);
        
        return tech;
    }
    
    private void populateSummary()
    {
        RadioButton opt = (RadioButton)ui_typeOptions.getSelectedToggle();
        
        ui_typeValue.setText(opt != null ? opt.getText() : "");
        
        ui_nameValue.setText(getName());
        ui_techNameValue.setText(getTechName());
        
        ui_aliasesValue.setText(ui_aliasField.getText());
        
        KeyValueCombo origin = ui_originChoice.getSelectionModel().getSelectedItem();
        if(origin != null)
            ui_originValue.setText(origin.getValue());
        else
            ui_originValue.setText("");
        
        String founded = ui_foundedChoice.getSelectionModel().getSelectedItem();
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
        HBox.setHgrow(ui_techNameField, Priority.ALWAYS);
        ui_autoNameButton.setText(res.getString("AUTO_TECH_NAME"));
        ui_autoNameButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent arg0)
            {
                ui_techNameField.setText(makeTechName());
            }
        });
        
        ui_commentArea.setPrefHeight(80.0);
        ui_commentArea.setWrapText(true);
        
        ui_commentValue.setWrapText(true);
        
        RadioButton bopt = new RadioButton(res.getString("BAND"));
        bopt.setId("BAND");
        bopt.setSelected(true);
        RadioButton aopt = new RadioButton(res.getString("ARTIST"));
        aopt.setId("ARTIST");
        RadioButton oopt = new RadioButton(res.getString("OTHER"));
        oopt.setId("OTHER");
        
        aopt.setToggleGroup(ui_typeOptions);
        bopt.setToggleGroup(ui_typeOptions);
        oopt.setToggleGroup(ui_typeOptions);
        
        
        /* Layout Phase 1 Form. */
        GridPane pane = new GridPane();
        pane.getStyleClass().setAll("glass-pane", "dialog-phase");
        pane.setHgap(10.0);
        pane.setVgap(10.0);
        
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(20.0);
        col0.setHalignment(HPos.RIGHT);
        pane.getColumnConstraints().add(col0);
        
        pane.add(new Label(res.getString("TYPE")), 0, 0);
        pane.add(new Label(res.getString("NAME") + " " + res.getString("MANDATORY")), 0, 1);
        pane.add(new Label(res.getString("TECH_NAME")), 0, 2);
        pane.add(new Label(res.getString("ALIASES")), 0, 3);
        pane.add(UIUtils.getHStretcher(), 0, 4);
        pane.add(new Label(res.getString("ORIGIN")), 0, 5);
        pane.add(new Label(res.getString("FOUNDED")), 0, 6);
        pane.add(new Label(res.getString("COMMENT")), 0, 7);
        
        pane.add(UIUtils.hLayout(15.0, bopt, aopt, oopt), 1, 0);
        pane.add(ui_nameField, 1, 1);
        pane.add(UIUtils.hLayout(10.0, ui_techNameField, ui_autoNameButton), 1, 2);
        pane.add(ui_aliasField, 1, 3);
        pane.add(new Text(res.getString("ALIAS_GUIDE")), 1, 4);
        pane.add(ui_originChoice, 1, 5);
        pane.add(ui_foundedChoice, 1, 6);
        pane.add(ui_commentArea, 1, 7);
        
        return pane;
    }
}
