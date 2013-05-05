package muzak;


import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Muzak extends Application
{
    private MainControl m_controller = new MainControl();
    
    private Scene scene = null;
    
    private TextField               ui_searchField      = null;
    private Button                  ui_searchButton     = null;
    private ComboBox<KeyValuePair>  ui_searchFilter     = null;
    private HBox                    ui_menuBarLayout    = null;
    
    private class KeyValuePair
    {
        private String m_key;
        private String m_value;
        
        KeyValuePair(String key, String value)
        {
            m_key = key; m_value = value; 
        }
        
        public String getKey()   { return m_key; }
        //public String getValue() { return m_value; };
        public String toString() { return m_value; };
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        ResourceBundle res = ResourceBundle.getBundle("bundles.MainWindowTitles", m_controller.getLocale());
        
        scene = new Scene( createMainLayout(res) );
        scene.getStylesheets().add(getClass().getResource("styles/toolbar.css").toExternalForm());
        stage.setScene(scene);
        
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        stage.setX(screen.getWidth() / 8);
        stage.setY(screen.getHeight() / 8);
        stage.setWidth(0.75 * screen.getWidth());
        stage.setHeight(0.75 * screen.getHeight());
        
        m_controller.setMainWindow(stage);
        
        stage.show();
    }

    private MenuBar createMenuBar(ResourceBundle res)
    {
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                m_controller.handleMenuAction(event);
            }
            
        };
        
        Menu menu1 = new Menu(res.getString("FILE"));
        MenuItem exitChoice = new MenuItem(res.getString("EXIT"));
        exitChoice.setId("ExitRequest");
        exitChoice.setOnAction(handler);
        menu1.getItems().add(exitChoice);
        
        Menu menu2 = new Menu(res.getString("DB"));
        MenuItem addArtistChoice    = new MenuItem(res.getString("ADD_ARTIST"));
        addArtistChoice.setId("AddArtistRequest");
        addArtistChoice.setOnAction(handler);
        MenuItem addReleaseChoice   = new MenuItem(res.getString("ADD_RELEASE"));
        addReleaseChoice.setId("AddReleaseRequest");
        addReleaseChoice.setOnAction(handler);
        MenuItem addTracksChoice    = new MenuItem(res.getString("ADD_TRACKS"));
        addTracksChoice.setId("AddTracksRequest");
        addTracksChoice.setOnAction(handler);
        MenuItem modArtistChoice    = new MenuItem(res.getString("MOD_ARTIST"));
        modArtistChoice.setId("ModifyArtistRequest");
        modArtistChoice.setOnAction(handler);
        MenuItem modReleaseChoice   = new MenuItem(res.getString("MOD_RELEASE"));
        modReleaseChoice.setId("ModifyReleaseRequest");
        modReleaseChoice.setOnAction(handler);
        MenuItem modTracksChoice    = new MenuItem(res.getString("MOD_TRACKS"));
        modTracksChoice.setId("ModifyTracksRequest");
        modTracksChoice.setOnAction(handler);
        MenuItem delArtistChoice    = new MenuItem(res.getString("DEL_ARTIST"));
        delArtistChoice.setId("DeleteArtistRequest");
        delArtistChoice.setOnAction(handler);
        MenuItem delReleaseChoice   = new MenuItem(res.getString("DEL_RELEASE"));
        delReleaseChoice.setId("DeleteReleaseRequest");
        delReleaseChoice.setOnAction(handler);
        MenuItem delTracksChoice    = new MenuItem(res.getString("DEL_TRACKS"));
        delTracksChoice.setId("DeleteTracksRequest");
        delTracksChoice.setOnAction(handler);
        menu2.getItems().addAll(addArtistChoice, addReleaseChoice, addTracksChoice, new SeparatorMenuItem(),
                                modArtistChoice, modReleaseChoice, modTracksChoice, new SeparatorMenuItem(),
                                delArtistChoice, delReleaseChoice, delTracksChoice);
        
        Menu menu3 = new Menu(res.getString("HELP"));
        MenuItem aboutChoice = new MenuItem(res.getString("ABOUT"));
        aboutChoice.setId("AboutRequest");
        aboutChoice.setOnAction(handler);
        menu3.getItems().add(aboutChoice);
        
        MenuBar bar = new MenuBar();
        bar.setStyle("-fx-background-color: transparent;");
        bar.getMenus().addAll(menu1, menu2, menu3);
        
        return bar;
    }
    
    private HBox createMenuBarBox(ResourceBundle res)
    {
        Region stretcher = new Region();
        HBox.setHgrow(stretcher, Priority.ALWAYS);
        
        Button lang_fiButton = new Button("FI");
        lang_fiButton.setGraphic(new ImageView(new Image("file:resources/icons/flag_fi.png")));
        lang_fiButton.setStyle("-fx-background-color: transparent;");
        lang_fiButton.setId("LangFIRequest");
        lang_fiButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(m_controller.changeToFinnish())
                {
                    reloadVisibles();
                }
            }
        });
        
        Button lang_enButton = new Button("EN");
        lang_enButton.setGraphic(new ImageView(new Image("file:resources/icons/flag_gb.png")));
        lang_enButton.setStyle("-fx-background-color: transparent;");
        lang_enButton.setId("LangENRequest");
        lang_enButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(m_controller.changeToEnglish())
                {
                    reloadVisibles();
                }
            }
        });
        
        ui_menuBarLayout = new HBox();
        ui_menuBarLayout.setSpacing(10.0);
        ui_menuBarLayout.setStyle("-fx-background-color: -fx-selection-bar;");
        ui_menuBarLayout.getChildren().addAll(createMenuBar(res), stretcher, lang_fiButton, lang_enButton);

        return ui_menuBarLayout;
    }
    
    private HBox createToolBar(ResourceBundle res)
    {
        ui_searchField = new TextField();
        
        ui_searchFilter = new ComboBox<>();
        ui_searchFilter.setItems(FXCollections.observableArrayList(new KeyValuePair("SEEK_NOFILT",     res.getString("SEEK_NOFILT")),
                                                                   new KeyValuePair("SEEK_ARTISTS",    res.getString("SEEK_ARTISTS")),
                                                                   new KeyValuePair("SEEK_RELEASES",   res.getString("SEEK_RELEASES")),
                                                                   new KeyValuePair("SEEK_TRACKS",     res.getString("SEEK_TRACKS"))));
        ui_searchFilter.getSelectionModel().selectFirst();
        
        ui_searchButton = new Button(res.getString("SEARCH"));
        ui_searchButton.setMinWidth(80.0);
        ui_searchButton.setId("SearchRequest");
        ui_searchButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!ui_searchField.getText().isEmpty())
                    m_controller.handleSearchAction(ui_searchField.getText(), ui_searchFilter.getValue().getKey());
            }
        });
        
/*        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                controller.handleButtonAction(event);
            }
            
        };
        
        Button addArtist = new Button();
        addArtist.setGraphic(new ImageView(new Image("file:resources/icons/vinyl_red.png")));
        //addArtist.setStyle("-fx-background-color: transparent; -fx-border-style: solid; -fx-border-color: black;");
        addArtist.setId("AddArtistRequest");
        addArtist.setOnAction(handler);
        addArtist.setMinSize(27, 27);
        addArtist.setMaxSize(addArtist.getMinWidth(), addArtist.getMinHeight());
        
        Button addRelease = new Button();
        addRelease.setGraphic(new ImageView(new Image("file:resources/icons/addRelease64x64.png")));
        addRelease.setStyle("-fx-background-color: transparent; -fx-border-style: solid; -fx-border-color: black;");
        addRelease.setId("AddReleaseRequest");
        addRelease.setOnAction(handler);
        //addRelease.setMinSize(27, 27);
        addRelease.setMaxSize(addRelease.getMinWidth(), addRelease.getMinHeight());
        
        Button addTracks = new Button();
        addTracks.setGraphic(new ImageView(new Image("file:resources/icons/vinyl_green.png")));
        addTracks.setStyle("-fx-background-color: transparent; -fx-border-style: solid; -fx-border-color: black;");
        addTracks.setId("AddTracksRequest");
        addTracks.setOnAction(handler);
        addTracks.setMinSize(27, 27);
        addTracks.setMaxSize(addTracks.getMinWidth(), addTracks.getMinHeight());
        */
        Region stretcher = new Region();
        HBox.setHgrow(stretcher, Priority.ALWAYS);
        
        HBox toolsLayout = new HBox();
        toolsLayout.getStyleClass().setAll("main-window-toolbar");
        toolsLayout.getChildren().addAll(ui_searchField, ui_searchFilter, ui_searchButton, stretcher);
        
        return toolsLayout;
    }
    
/*    private StackPane createToolButton(Image img)
    {
        StackPane base = new StackPane();
        ImageView iv1 = new ImageView(new Image("file:resources/icons/vinyl-icon_48.png"));
        iv1.setCache(true);
        
        ImageView iv2 = new ImageView(new Image("file:resources/icons/plus2_256.png"));
        iv2.setFitWidth(24.0);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);
        
        base.getChildren().addAll(iv1, iv2);
        base.setAlignment(Pos.BOTTOM_RIGHT);
        
        base.setOnMouseEntered(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent arg0)
            {
                scene.setCursor(Cursor.HAND);
            }
        });
        
        base.setOnMouseExited(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent arg0)
            {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        return base;
    }*/
    
    private BorderPane createMainLayout(ResourceBundle res)
    {
        BorderPane root = new BorderPane();
        
        VBox topLayout = new VBox();
        topLayout.getChildren().addAll(createMenuBarBox(res), createToolBar(res));
        root.setTop(topLayout);
        
        ScrollPane centerPiece = new ScrollPane();
        centerPiece.setHbarPolicy(ScrollBarPolicy.NEVER);
        centerPiece.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        centerPiece.setFitToWidth(true);
        VBox contentPane = new VBox();
        contentPane.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        contentPane.setSpacing(5.0);
        centerPiece.setContent(contentPane);
        root.setCenter(centerPiece);
        
        HBox bottomLayout = new HBox();
        bottomLayout.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        Text status = new Text("This is status bar.");
        bottomLayout.getChildren().add(status);
        root.setBottom(bottomLayout);
        root.setId("background");
        
        return root;
    }
    
    private void reloadVisibles()
    {
        System.out.println("Reloading Visibles...");
        
        ResourceBundle res = ResourceBundle.getBundle("bundles.MainWindowTitles", m_controller.getLocale());
        
        int selected = ui_searchFilter.getSelectionModel().getSelectedIndex();
        
        ui_searchFilter.setItems(FXCollections.observableArrayList(new KeyValuePair("SEEK_NOFILT",     res.getString("SEEK_NOFILT")),
                                                                   new KeyValuePair("SEEK_ARTISTS",    res.getString("SEEK_ARTISTS")),
                                                                   new KeyValuePair("SEEK_RELEASES",   res.getString("SEEK_RELEASES")),
                                                                   new KeyValuePair("SEEK_TRACKS",     res.getString("SEEK_TRACKS"))));
        ui_searchFilter.getSelectionModel().select(selected);
        
        ui_searchButton.setText(res.getString("SEARCH"));
        
        ui_menuBarLayout.getChildren().remove(0);
        ui_menuBarLayout.getChildren().add(0, createMenuBar(res));
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
