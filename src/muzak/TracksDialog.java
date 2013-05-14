
package muzak;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import muzak.Configurations.Resources;

public class TracksDialog extends AbstractPhasedDialog
{
    private TextField ui_ordinalField = new TextField();
    private TextField ui_titleField = new TextField();
    private TextField ui_lengthField = new TextField();
    private CheckBox ui_coverOption = new CheckBox();
    private ComboBox<String> ui_ratingChoice = new ComboBox<>();
    private TableView ui_table = new TableView();
    
    private ObservableList<TrackInfoElement> m_data = FXCollections.observableArrayList();
    
    public TracksDialog(final Configurations config, final DialogObserver observer)
    {
        super(config, observer);
        
        ResourceBundle res = config.getResources(Resources.TRACKS_DIALOG);
        
        addPhase(createTracksForm(res));
        
        UIUtils.populate(ui_ratingChoice, config.getMinRatingValue(), config.getMaxRatingValue());
        
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        setWidth(0.5 * screen.getWidth());
        setHeight(0.5 * screen.getHeight());
        
        setTitle(res.getString("DIALOG_TITLE"));
        
        ui_table.setItems(m_data);
        
        super.prepare();
    }

    @Override
    protected void proceed() {
        
    }

    @Override
    protected void rollBack() {
        
    }
    
    private void setupStringTableColumn(TableColumn column, String propertyName)
    {
        column.setCellValueFactory(new PropertyValueFactory<TrackInfoElement, String>(propertyName));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(new EventHandler<CellEditEvent<TrackInfoElement, String>>() {

            @Override
            public void handle(CellEditEvent<TrackInfoElement, String> t) 
            {
                TrackInfoElement tie = (TrackInfoElement)t.getTableView().getItems().get(t.getTablePosition().getRow());
                tie.setOrdinal(t.getNewValue());
            }
        });
    }
    
    private Pane createTracksForm(ResourceBundle res)
    {
        ui_ordinalField.setPromptText("#");
        UIUtils.setFixedWidth(ui_ordinalField, 40.0);
        ui_titleField.setPromptText(res.getString("TITLE"));
        ui_coverOption.setText(res.getString("COVER"));
        ui_lengthField.setPromptText(res.getString("LENGTH"));
        UIUtils.setFixedWidth(ui_lengthField, 80.0);
        final Button addButton = new Button(res.getString("ADD"));
        addButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t)
            {
                System.out.println("Kappaleen lisäys tauluun");
                m_data.add(new TrackInfoElement(
                        ui_ordinalField.getText(),
                        ui_titleField.getText(),
                        ui_lengthField.getText(),
                        ui_coverOption.isSelected(),
                        Integer.parseInt(ui_ratingChoice.getSelectionModel().getSelectedItem())));
                
                ui_ordinalField.clear();
                ui_titleField.clear();
                ui_lengthField.clear();
                ui_coverOption.setSelected(false);
                ui_ratingChoice.getSelectionModel().clearSelection();
            }
        });
        
//        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
//firstNameCol.setOnEditCommit(
//    new EventHandler<CellEditEvent<Person, String>>() {
//        @Override
//        public void handle(CellEditEvent<Person, String> t) {
//            ((Person) t.getTableView().getItems().get(
//                t.getTablePosition().getRow())
//                ).setFirstName(t.getNewValue());
//        }
//    }
//);
        
        TableColumn ocol = new TableColumn("#");
        setupStringTableColumn(ocol, "ordinal");
//        ocol.setCellValueFactory(new PropertyValueFactory<TrackInfoElement, String>("ordinal"));
//        ocol.setCellFactory(TextFieldTableCell.forTableColumn());
//        ocol.setOnEditCommit(new EventHandler<CellEditEvent<TrackInfoElement, String>>() {
//
//            @Override
//            public void handle(CellEditEvent<TrackInfoElement, String> t) 
//            {
//                TrackInfoElement tie = (TrackInfoElement)t.getTableView().getItems().get(t.getTablePosition().getRow());
//                tie.setOrdinal(t.getNewValue());
//            }
//        });
        
        TableColumn tcol = new TableColumn(res.getString("TITLE"));
        setupStringTableColumn(tcol, "title");
//        tcol.setCellValueFactory(new PropertyValueFactory<TrackInfoElement, String>("title"));
//        tcol.setCellFactory(TextFieldTableCell.forTableColumn());
//        tcol.setOnEditCommit(new EventHandler<CellEditEvent<TrackInfoElement, String>>() {
//
//            @Override
//            public void handle(CellEditEvent<TrackInfoElement, String> t) 
//            {
//                TrackInfoElement tie = (TrackInfoElement)t.getTableView().getItems().get(t.getTablePosition().getRow());
//                tie.setOrdinal(t.getNewValue());
//            }
//        });
        
        TableColumn ccol = new TableColumn(res.getString("COVER"));
        ccol.setCellValueFactory(new PropertyValueFactory<TrackInfoElement, Boolean>("cover"));
        TableColumn lcol = new TableColumn(res.getString("LENGTH"));
        setupStringTableColumn(lcol, "length");
        //lcol.setCellValueFactory(new PropertyValueFactory<TrackInfoElement, String>("length"));
        TableColumn rcol = new TableColumn(res.getString("RATING"));
        rcol.setCellValueFactory(new PropertyValueFactory<TrackInfoElement, Integer>("rating"));
        
        ui_table.setEditable(true);
        ui_table.getColumns().addAll(ocol, tcol, ccol, lcol, rcol);
        
        
        HBox addLayout = UIUtils.hLayout(10.0, ui_ordinalField, ui_titleField, ui_coverOption, ui_lengthField, ui_ratingChoice, UIUtils.getHStretcher(), addButton);
        
        VBox box = UIUtils.vLayout(10.0, ui_table, addLayout);
        box.getStyleClass().addAll("glass-pane", "dialog-phase");
        
        return box;
    }
    
}
