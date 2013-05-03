/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muzak;

import java.util.Locale;
import java.util.ResourceBundle;
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

/**
 *
 * @author Harri
 */
public class ArtistDialog extends AbstractPhasedDialog
{
    private ToggleGroup typeOptions;
    private TextField nameTextField;
    private TextField aliasesTextField;
    private ComboBox<String> originComboBox;
    private ComboBox<String> foundedComboBox;
    private TablessTextArea commentTextArea;
    
    public ArtistDialog(final Locale locale)
    {
        super(locale);
        ResourceBundle res = ResourceBundle.getBundle("bundles.ArtistDialog", locale);
        addPhase(createArtistForm(res));
    }

    @Override
    protected void proceed() 
    {
    }

    @Override
    protected void rollBack() 
    {
    }
    
    private GridPane createArtistForm(ResourceBundle res)
    {
        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(10.0);
        pane.setPadding(new Insets(10.0));
        
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(20.0);
        col0.setHalignment(HPos.RIGHT);
        
        pane.getColumnConstraints().add(col0);
        
        Region stretcher = new Region();
        HBox.setHgrow(stretcher, Priority.ALWAYS);
        
        pane.add(new Label(res.getString("TYPE")), 0, 0);
        pane.add(new Label(res.getString("NAME")), 0, 1);
        pane.add(new Label(res.getString("ALIASES")), 0, 2);
        pane.add(stretcher, 0, 3);
        pane.add(new Label(res.getString("ORIGIN")), 0, 4);
        pane.add(new Label(res.getString("FOUNDED")), 0, 5);
        pane.add(new Label(res.getString("COMMENT")), 0, 6);
        pane.add(new Button("Discogs"), 0, 7);
        
        HBox optionsLayout = new HBox(15.0);
        RadioButton artistOption = new RadioButton(res.getString("ARTIST"));
        RadioButton bandOption = new RadioButton(res.getString("BAND"));
        bandOption.setSelected(true);
        RadioButton otherOption = new RadioButton(res.getString("OTHER"));
        typeOptions = new ToggleGroup();
        artistOption.setToggleGroup(typeOptions);
        bandOption.setToggleGroup(typeOptions);
        otherOption.setToggleGroup(typeOptions);
        optionsLayout.getChildren().addAll(bandOption, artistOption, otherOption);
        
        nameTextField = new TextField();
        aliasesTextField = new TextField();
        originComboBox = new ComboBox<>();
        foundedComboBox = new ComboBox<>();
        commentTextArea = new TablessTextArea();
        commentTextArea.setPrefHeight(80.0);
        commentTextArea.setWrapText(true);
        
        Region stretcher2 = new Region();
        HBox.setHgrow(stretcher2, Priority.ALWAYS);
        
        pane.add(optionsLayout, 1, 0);
        pane.add(nameTextField, 1, 1);
        pane.add(aliasesTextField, 1, 2);
        pane.add(new Text(res.getString("ALIAS_GUIDE")), 1, 3);
        pane.add(originComboBox, 1, 4);
        pane.add(foundedComboBox, 1, 5);
        pane.add(commentTextArea, 1, 6);
        pane.add(stretcher2, 1, 7);
        
        return pane;
    }
}
