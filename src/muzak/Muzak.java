package muzak;


import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Muzak extends Application
{
    private MainControl controller = new MainControl();
    Button searchButton = null;
    ComboBox<String> searchFilter = null;
    HBox menuBarLayout = null;
    @Override
    public void start(Stage stage) throws Exception
    {
        ResourceBundle res = ResourceBundle.getBundle("bundles.MainWindowTitles", controller.getLocale());
        
        Scene scene = new Scene( createMainLayout(res) );
        scene.getStylesheets().add(getClass().getResource("styles/toolbar.css").toExternalForm());
        stage.setScene(scene);
        
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        stage.setX(screen.getWidth() / 8);
        stage.setY(screen.getHeight() / 8);
        stage.setWidth(0.75 * screen.getWidth());
        stage.setHeight(0.75 * screen.getHeight());
        
        controller.setMainWindow(stage);
        
        stage.show();
    }

    private MenuBar createMenuBar(ResourceBundle res)
    {
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                controller.handleMenuAction(event);
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
                if(controller.changeToFinnish())
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
                if(controller.changeToEnglish())
                {
                    reloadVisibles();
                }
            }
        });
        
        menuBarLayout = new HBox();
        menuBarLayout.setSpacing(10.0);
        menuBarLayout.setStyle("-fx-background-color: -fx-selection-bar;");
        menuBarLayout.getChildren().addAll(createMenuBar(res), stretcher, lang_fiButton, lang_enButton);

        return menuBarLayout;
    }
    
    private HBox createToolBar(ResourceBundle res)
    {
        HBox toolsLayout = new HBox();
        toolsLayout.setPadding(new Insets(10.0));
        toolsLayout.setSpacing(10.0);
        toolsLayout.setStyle("-fx-border-style: solid; -fx-border-color: black;");
        toolsLayout.setAlignment(Pos.CENTER_LEFT);
        toolsLayout.getStyleClass().setAll("segmented-button-bar");
        
        TextField searchTextField = new TextField();
        
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll(res.getString("SEEK_EVERYTHING"), res.getString("SEEK_ARTISTS"), res.getString("SEEK_RELEASES"), res.getString("SEEK_TRACKS"));
        
        searchFilter = new ComboBox<>();
        searchFilter.setItems(options);
        
        searchButton = new Button(res.getString("SEARCH"));
        searchButton.setId("SearchRequest");
        searchButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                controller.handleButtonAction(event);
            }
        });
        
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>()
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
        addRelease.setGraphic(new ImageView(new Image("file:resources/icons/vinyl_blue.png")));
        addRelease.setStyle("-fx-background-color: transparent; -fx-border-style: solid; -fx-border-color: black;");
        addRelease.setId("AddReleaseRequest");
        addRelease.setOnAction(handler);
        addRelease.setMinSize(27, 27);
        addRelease.setMaxSize(addRelease.getMinWidth(), addRelease.getMinHeight());
        
        Button addTracks = new Button();
        addTracks.setGraphic(new ImageView(new Image("file:resources/icons/vinyl_green.png")));
        addTracks.setStyle("-fx-background-color: transparent; -fx-border-style: solid; -fx-border-color: black;");
        addTracks.setId("AddTracksRequest");
        addTracks.setOnAction(handler);
        addTracks.setMinSize(27, 27);
        addTracks.setMaxSize(addTracks.getMinWidth(), addTracks.getMinHeight());
        
        Region stretcher = new Region();
        HBox.setHgrow(stretcher, Priority.ALWAYS);
        toolsLayout.getChildren().addAll(searchTextField, searchFilter, searchButton, stretcher, addArtist, addRelease, addTracks);
        
        return toolsLayout;
    }
    
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
        
        ResourceBundle res = ResourceBundle.getBundle("bundles.MainWindowTitles", controller.getLocale());
        
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll(res.getString("SEEK_EVERYTHING"), res.getString("SEEK_ARTISTS"), res.getString("SEEK_RELEASES"), res.getString("SEEK_TRACKS"));
        
        searchFilter.setItems(options);
        
        searchButton.setText(res.getString("SEARCH"));
        
        menuBarLayout.getChildren().remove(0);
        menuBarLayout.getChildren().add(0, createMenuBar(res));
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
