
package muzak;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import muzak.Configurations.Resources;
import muzak.mycomp.TracklistTableView;

public class TracksDialog extends AbstractPhasedDialog
{
    private TextField                   ui_ordinalField     = new TextField();
    private TextField                   ui_titleField       = new TextField();
    private TextField                   ui_lengthField      = new TextField();
    private CheckBox                    ui_coverOption      = new CheckBox();
    private ComboBox<KeyValueCombo>     ui_ratingChoice     = new ComboBox<>();
    private TracklistTableView          ui_tableView;
    private Button                      ui_discogs          = getDiscogsButton();
    
    public TracksDialog(final Configurations config, final DialogObserver observer)
    {
        super(config, observer);
        
        ui_tableView = new TracklistTableView(config);
        
        ResourceBundle res = config.getResources(Resources.TRACKS_DIALOG);
        
        addPhase(createTracksForm(res));
        
        UIUtils.populate(ui_ratingChoice, config.getResources(Resources.LIST_OF_RATINGS));
        
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        setWidth(0.5 * screen.getWidth());
        setHeight(0.5 * screen.getHeight());
        
        setTitle(res.getString("DIALOG_TITLE"));
        
        super.prepare();
    }
    
    public ArrayList<TrackInfoElement> getData()
    {
        return ui_tableView.getTableData();
    }
    
    @Override
    protected void proceed()
    {
    }

    @Override
    protected void rollBack()
    {
    }
    
    private TrackInfoElement makeTrackInfo()
    {
        TrackInfoElement tie = new TrackInfoElement();
        
        String ordinal = MyUtils.trimWhitespaces(ui_ordinalField.getText());
        if(ordinal.isEmpty())
            return null;
        
        tie.setOrdinal(ordinal);
        tie.setTitle(MyUtils.trimWhitespaces(ui_titleField.getText()));
        tie.setLength(MyUtils.trimWhitespaces(ui_lengthField.getText()));
        tie.setCover(ui_coverOption.isSelected());
        
        KeyValueCombo kvc = ui_ratingChoice.getSelectionModel().getSelectedItem();
        if(kvc != null)
            tie.setRating(kvc.getValue());
        
        return tie;
    }
    
    private Pane createTracksForm(ResourceBundle res)
    {
        ui_ordinalField.setPromptText("#");
        UIUtils.setFixedWidth(ui_ordinalField, 80.0);
        
        ui_titleField.setPromptText(res.getString("TITLE"));
        ui_titleField.setMinWidth(80.0);
        HBox.setHgrow(ui_titleField, Priority.ALWAYS);
        
        ui_coverOption.setText(res.getString("COVER"));
        
        ui_lengthField.setPromptText(res.getString("LENGTH"));
        UIUtils.setFixedWidth(ui_lengthField, 80.0);
        
        final Button addButton = new Button(res.getString("ADD"));
        addButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t)
            {
                TrackInfoElement tie = makeTrackInfo();
                ui_tableView.addTableData(tie);
                
                
                ui_ordinalField.clear();
                ui_titleField.clear();
                ui_lengthField.clear();
                ui_coverOption.setSelected(false);
                ui_ratingChoice.getSelectionModel().clearSelection();
            }
        });
        
        
        HBox hbox = UIUtils.hLayout(10.0,
                                    ui_ordinalField,
                                    ui_titleField,
                                    ui_coverOption,
                                    ui_lengthField,
                                    ui_ratingChoice,
                                    UIUtils.getHStretcher(),
                                    addButton);
        
        VBox box = UIUtils.vLayout(10.0, ui_tableView, hbox, ui_discogs);
        box.getStyleClass().addAll("glass-pane", "dialog-phase");
        
        return box;
    }
}
