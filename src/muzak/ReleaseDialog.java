
package muzak;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import muzak.Configurations.Resources;
import muzak.mycomp.IntegerSpinnerPane;
import muzak.mycomp.MultiSelectionListView;
import muzak.mycomp.TablessTextArea;
import muzak.mycomp.TracklistTableView;
import muzakModel.Release;
import muzakModel.TrackInfoElement;

public class ReleaseDialog extends AbstractPhasedDialog// implements DialogCallback
{
    
    /* Phase 0 UI components: */
    private TextField               ui_titleField           = new TextField();
    private TextField               ui_altTitleField        = new TextField();
    private TextField               ui_catNumberField       = new TextField();
    private TextField               ui_barCodeField         = new TextField();
    private MultiSelectionListView  ui_typeList             = new MultiSelectionListView(true);
    private MultiSelectionListView  ui_mediaList            = new MultiSelectionListView(true);
    private ToggleGroup             ui_releaseOptions       = new ToggleGroup();
    private CheckBox                ui_extendedOption       = new CheckBox();
    private ComboBox<String>        ui_orgYearChoice        = new ComboBox<>();
    private ComboBox<String>        ui_curYearChoice        = new ComboBox<>();
    private IntegerSpinnerPane      ui_discCount            = new IntegerSpinnerPane();
    private ComboBox<KeyValueCombo> ui_styleChoice          = new ComboBox<>();
    private ComboBox<KeyValueCombo> ui_ratingChoice         = new ComboBox<>();
    private TablessTextArea         ui_commentArea          = new TablessTextArea();
    private Button                  ui_discogsButton        = getDiscogsButton();
    /* Phase 1 UI components: */
    private MultiSelectionListView  ui_artistsChoice     = new MultiSelectionListView(false);
    private Button                  ui_createArtistButton   = new Button();
    /* Phase 2 UI components: */
    private TracklistTableView      ui_tracklistTable       = null;
    /* Phase 3 UI components: */
    private TextArea                ui_performersValue      = new TextArea();
    private Label                   ui_titleValue           = new Label();
    private Label                   ui_techTitleValue       = new Label();
    private Label                   ui_altTitleValue        = new Label();
    private Label                   ui_catNumberValue       = new Label();
    private Label                   ui_barCodeValue         = new Label();
    private Label                   ui_typeValue            = new Label();
    private Label                   ui_mediaValue           = new Label();
    private Label                   ui_releaseValue         = new Label();
    private Label                   ui_yearsValue           = new Label();
    private Label                   ui_discsValue           = new Label();
    private Label                   ui_styleValue           = new Label();
    private Label                   ui_ratingValue          = new Label();
    private TextArea                ui_commentValue         = new TextArea();
    
    public ReleaseDialog(final Configurations config, final DialogObserver observer)
    {
        super(config, observer);
        
        ui_tracklistTable = new TracklistTableView(config);
        
        ResourceBundle res = config.getResources(Resources.RELEASE_DIALOG);

        super.addPhase(createReleaseForm(res));
        super.addPhase(createReleaseArtistForm(res));
        super.addPhase(createReleaseTracklistForm(res));
        super.addPhase(createReleaseSummary(res));
        
        UIUtils.populate(ui_styleChoice, config.getResources(Resources.LIST_OF_STYLES));
        UIUtils.populate(ui_curYearChoice, config.getReleasedStartValue(), config.getReleasedEndValue());
        UIUtils.populate(ui_orgYearChoice, config.getReleasedStartValue(), config.getReleasedEndValue());
        UIUtils.populate(ui_ratingChoice, config.getResources(Resources.LIST_OF_RATINGS));
        ui_typeList.insertSelectionElements(config.getResources(Resources.LIST_OF_RELEASE_TYPES));
        ui_mediaList.insertSelectionElements(config.getResources(Resources.LIST_OF_RELEASE_MEDIA));
        ui_artistsChoice.insertSelectionElements(m_observer.getArtists());
        setTitle(res.getString("DIALOG_TITLE"));
        
        super.prepare();
    }
    
    @Override // from DialogCallback
    public void update()
    {
        if(m_observer != null)
            ui_artistsChoice.insertSelectionElements(m_observer.getArtists());
        
        System.out.println("ARTIST UPDATE");
    }
    
    @Override // from DialogCallback
    public String getQueryTitle()
    {
        return getReleaseTitle();
    }

    @Override // from DialogCallback
    public String getQueryCatNumber()
    {
        return MyUtils.trimToAlphanumeric(getCatalogNumber());
    }

    @Override // from DialogCallback
    public String getQueryBarcode()
    {
        return getBarcode();
    }
    
    @Override // from DialogCallback
    public void injectValues(RecordInfoElement record)
    {
        System.out.println("RELDIALOG/INJECT");
        
        if(record != null)
        {
            Release release = record.getRelease();
            ui_catNumberField.setText(release.getCatalogNumber());
            
            for(TrackInfoElement track : record.getTracklist())
            {
                System.out.println(track);
                ui_tracklistTable.addTableData(track);
            }
            
            //ui_commentArea.setText("Kissat");
        }
    }
    
    public String getReleaseTitle()
    {
        return MyUtils.trimWhitespaces(ui_titleField.getText());
    }
    
    public String getTechTitle()
    {
        return MyUtils.trimArticles(getReleaseTitle());
    }
    
    public String getAlternateTitle()
    {
        return MyUtils.trimWhitespaces(ui_altTitleField.getText());
    }
    
    public String getCatalogNumber()
    {
        return MyUtils.trimWhitespaces(ui_catNumberField.getText());
    }
    
    public String getBarcode()
    {
        return MyUtils.trimToAlphanumeric(ui_barCodeField.getText());
    }
    
    public ArrayList<String> getTypeKeys()
    {
        return ui_typeList.getSelectedKeys();
    }
    
    public ArrayList<String> getMediaKeys()
    {
        return ui_mediaList.getSelectedKeys();
    }
    
    public boolean getIsOriginal()
    {
        RadioButton opt = (RadioButton)ui_releaseOptions.getSelectedToggle();
        
        return (opt != null ? opt.getId().equals("ORIGINAL") : true);
    }
    
    public boolean getIsExtended()
    {
        return ui_extendedOption.selectedProperty().get();
    }
    
    public int getOriginalYear()
    {
        String val = ui_orgYearChoice.getSelectionModel().getSelectedItem();
        
        return (val != null ? Integer.parseInt(val) : -1);
    }
    
    public int getCurrentYear()
    {
        String val = ui_curYearChoice.getSelectionModel().getSelectedItem();
        
        return (val != null ? Integer.parseInt(val) : getIsOriginal() ? getOriginalYear() : -1);
    }
    
    public int getDiscCount()
    {
        return ui_discCount.getCurrentValue();
    }
    
    public String getStyleKey()
    {
        KeyValueCombo kvc = ui_styleChoice.getSelectionModel().getSelectedItem();
        
        return (kvc != null ? kvc.getKey() : "");
    }
    
    public int getRating()
    {
        KeyValueCombo kvc = ui_ratingChoice.getSelectionModel().getSelectedItem();
        
        return (kvc != null ? Integer.parseInt(kvc.getKey()) : 0);
    }
    
    public String getComment()
    {
        return MyUtils.trimWhitespaces(ui_commentArea.getText());
    }
    
    public ArrayList<KeyValueCombo> getArtists()
    {
        return ui_artistsChoice.getSelected();
    }
    
    @Override
    protected void proceed()
    {
        if(getCurrentPhase() == 1) /* Phase 1 - Select performers for the release. */
        {
            boolean singleSelect = true;
            /* Checking types could be better, but this'll do... */ 
            for(String type : getTypeKeys())
            {
                if(type.contains("SPLIT") || type.contains("VAR_ARTIST") || type.contains("COLLABORATION"))
                    singleSelect = false;
            }
            
            if(singleSelect)
            {
                if(!ui_artistsChoice.isSingleModeOn())
                {
                    ui_artistsChoice.switchToSingleMode();
                    ui_artistsChoice.insertSelectionElements(m_observer.getArtists());
                }
            }
            else
            {
                if(!ui_artistsChoice.isMultiModeOn())
                {
                    ui_artistsChoice.switchToMultiMode();
                    ui_artistsChoice.insertSelectionElements(m_observer.getArtists());
                }
            }
        }
        
        if(isLastPhase())
            populateSummary();
    }

    @Override
    protected void rollBack()
    {
    }
    
    private void relayNewArtistRequest()
    {
        m_observer.createArtist(this);
    }
    
    private void populateSummary()
    {
        String contents = "";
        for(KeyValueCombo kvc : getArtists())
        {
            contents += kvc.getValue() + "\n";
        }
        ui_performersValue.setText(contents.trim());
        
        ui_titleValue.setText(getReleaseTitle());
        ui_techTitleValue.setText(getTechTitle());
        ui_altTitleValue.setText(getAlternateTitle());
        ui_catNumberValue.setText(getCatalogNumber());
        ui_barCodeValue.setText(getBarcode());
        
        ui_typeValue.setText(MyUtils.asValueString(ui_typeList.getSelected(), " + "));
        ui_mediaValue.setText(MyUtils.asValueString(ui_mediaList.getSelected(), " + "));
        
        String tmp = "";
        tmp += ((RadioButton)ui_releaseOptions.getSelectedToggle()).getText();
        if(ui_extendedOption.selectedProperty().get())
            tmp += ((tmp.isEmpty()) ? "" : ", ") + ui_extendedOption.getText();
        
        ui_releaseValue.setText(tmp);
        
        int year = getCurrentYear();
        tmp = (year > 0 ? Integer.toString(year) : "-") + "/";
        year = getOriginalYear();
        tmp += (year > 0 ? Integer.toString(year) : "-");
        
        ui_yearsValue.setText(tmp);
        
        ui_discsValue.setText(Integer.toString(getDiscCount()));
        
        KeyValueCombo kvc = ui_styleChoice.getSelectionModel().getSelectedItem();
        ui_styleValue.setText(kvc != null ? kvc.getValue() : "");
        
        KeyValueCombo rating = ui_ratingChoice.getSelectionModel().getSelectedItem();
        ui_ratingValue.setText(rating != null ? rating.getValue() : "");
        
        ui_commentValue.setText(getComment());
    }
    
    private GridPane createReleaseSummary(ResourceBundle res)
    {
        ui_performersValue.setPrefHeight(80.0);
        ui_performersValue.setEditable(false);
        
        ui_commentValue.setPrefHeight(80.0);
        ui_commentValue.setWrapText(true);
        ui_commentValue.setEditable(false);
        
        GridPane pane = new GridPane();
        pane.getStyleClass().setAll("glass-pane", "dialog-phase");
        pane.setHgap(10.0);
        pane.setVgap(15.0);
        
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setHalignment(HPos.RIGHT);
        pane.getColumnConstraints().addAll(col0);
        
        pane.add(new Label(res.getString("PERFORMER")), 0, 0);
        pane.add(new Label(res.getString("TITLE")), 0, 1);
        pane.add(new Label(res.getString("TECH_TITLE")), 0, 2);
        pane.add(new Label(res.getString("ALT_TITLE")), 0, 3);
        pane.add(new Label(res.getString("CATALOG_NUMBER")), 0, 4);
        pane.add(new Label(res.getString("BARCODE")), 0, 5);
        pane.add(new Label(res.getString("TYPE")), 0, 6);
        pane.add(new Label(res.getString("MEDIA")), 0, 7);
        pane.add(new Label(res.getString("RELEASE")), 0, 8);
        pane.add(new Label(res.getString("YEAR_OF_RELEASE")), 0, 9);
        pane.add(new Label(res.getString("CURRENT") + "/" + res.getString("ORIGINAL")), 1, 9);
        pane.add(new Label(res.getString("DISCS")), 0, 10);
        pane.add(new Label(res.getString("STYLE")), 0, 11);
        pane.add(new Label(res.getString("RATING")), 0, 12);
        pane.add(new Label(res.getString("COMMENT")), 0, 13);
        
        pane.add(ui_performersValue, 1, 0, 2, 1);
        pane.add(ui_titleValue, 1, 1, 2, 1);
        pane.add(ui_techTitleValue, 1, 2, 2, 1);
        pane.add(ui_altTitleValue, 1, 3, 2, 1);
        pane.add(ui_catNumberValue, 1, 4, 2, 1);
        pane.add(ui_barCodeValue, 1, 5, 2, 1);
        pane.add(ui_typeValue, 1, 6, 2, 1);
        pane.add(ui_mediaValue, 1, 7, 2, 1);
        pane.add(ui_releaseValue, 1, 8, 2, 1);
        pane.add(ui_yearsValue, 2, 9);
        pane.add(ui_discsValue, 1, 10, 2, 1);
        pane.add(ui_styleValue, 1, 11, 2, 1);
        pane.add(ui_ratingValue, 1, 12, 2, 1);
        pane.add(ui_commentValue, 1, 13, 2, 1);
        
        return pane;
    }
    
    private VBox createReleaseTracklistForm(ResourceBundle res)
    {
        VBox box = UIUtils.vLayout(15.0, new Label(res.getString("TRACKLIST")), ui_tracklistTable);
        box.getStyleClass().setAll("glass-pane", "dialog-phase");
        
        return box;
    }
    
    private VBox createReleaseArtistForm(ResourceBundle res)
    {
        ui_createArtistButton.setText(res.getString("CREATE_ARTIST"));
        ui_createArtistButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent arg0)
            {
                relayNewArtistRequest();
            }
        });
        
        VBox box = UIUtils.vLayout(15.0, new Label(res.getString("PERFORMER_GUIDE")), ui_artistsChoice, UIUtils.getVStretcher(), ui_createArtistButton);
        box.getStyleClass().setAll("glass-pane", "dialog-phase");
        
        return box;
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
        
        RadioButton oopt = new RadioButton(res.getString("ORIGINAL"));
        oopt.setId("ORIGINAL");
        oopt.setSelected(true);
        RadioButton ropt = new RadioButton(res.getString("REISSUE"));
        ropt.setId("REISSUE");
        
        oopt.setToggleGroup(ui_releaseOptions);
        ropt.setToggleGroup(ui_releaseOptions);
        
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
        pane.add(UIUtils.vLayout(10.0, new Label(res.getString("TYPE_GUIDE")), ui_typeList), 1, 4, 2, 1);
        pane.add(UIUtils.vLayout(10.0, new Label(res.getString("MEDIA_GUIDE")), ui_mediaList), 3, 4, 2, 1);
        pane.add(UIUtils.hLayout(15.0, oopt, ropt, UIUtils.getHStretcher(), ui_extendedOption), 1, 5, 4, 1);
        pane.add(ui_curYearChoice, 2, 6);
        pane.add(ui_orgYearChoice, 4, 6);
        pane.add(ui_discCount, 1, 7, 4, 1);
        pane.add(ui_styleChoice, 1, 8, 4, 1);
        pane.add(ui_ratingChoice, 1, 9, 4, 1);
        pane.add(ui_commentArea, 1, 10, 4, 1);
        
        return pane;
    }

    
}
