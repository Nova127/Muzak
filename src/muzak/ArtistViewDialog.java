package muzak;

import java.util.TreeSet;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import muzak.mycomp.TablessTextArea;
import muzakModel.Artist;
import muzakModel.Release;

public class ArtistViewDialog extends AbstractPhasedDialog
{
    private Artist m_artist;
    private TreeSet<Release> m_releases;
    private TabPane ui_tpane = new TabPane();
    private TablessTextArea ui_commentArea = new TablessTextArea();
    private Configurations m_config;

    public ArtistViewDialog(Artist artist, final Configurations config, final DialogObserver observer)
    {
        super(config, observer);
        m_artist = artist;
        m_config = config;
        addPhase(createArtistView(config));
        super.prepare();
    }
    
    public void setReleases(TreeSet<Release> releases)
    {
        m_releases = releases;
        Tab rtab = new Tab("RELEASE");
        rtab.setClosable(false);
        ScrollPane spane = new ScrollPane();
        spane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spane.setFitToWidth(true);
        VBox box = new VBox();
        box.setPadding(new Insets(10.0));
        for(Release rel : releases)
            box.getChildren().add(UIUtils.getReleaseCard(rel, m_config, new TablessTextArea()));
        
        spane.setContent(box);
        rtab.setContent(spane);
        ui_tpane.getTabs().add(rtab);
    }
    

    @Override
    protected void proceed()
    {
        
    }

    @Override
    protected void rollBack()
    {
        
    }
    
    private Pane createArtistView(Configurations config)
    {
        VBox card = UIUtils.getArtistCard(m_artist, config, ui_commentArea);
        
        ui_tpane = new TabPane();
        ui_tpane.getStyleClass().addAll("tab-pane");
//        Tab tabr = new Tab("Releases");
//        
//        Tab taba = new Tab("Appears on");
//        Tab tabt = new Tab("Tracklist");
//        Tab tabm = new Tab("Members");
//        tpane.getTabs().addAll(tabr, taba, tabt, tabm);
        
        BorderPane box = new BorderPane();
        box.setStyle("-fx-background-color: transparent;");
        box.setTop(card);
        box.setCenter(ui_tpane);
        
        return box;
    }
    
}
