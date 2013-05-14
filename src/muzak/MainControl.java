
package muzak;

import discogs.Discogs;
import discogs.DiscogsWorker;
import java.io.IOException;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import muzak.mycomp.ViewModDelObserver;
import muzakModel.Artist;
import muzakModel.DataModelObject;
import muzakModel.MuzakDataModel;
import muzakModel.MuzakDataModel.Order;
import muzakModel.MuzakDataModel.Tables;
import muzakModel.Release;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import muzakModel.NotUniqueSignatureException;
import org.json.simple.parser.ParseException;

public class MainControl implements DialogObserver, ViewModDelObserver
{
    private MuzakDataModel m_model;
    private MuzakConfig m_config;
    /* Initialize 'default' locale. */
    //private Locale m_locale = new Locale("fi");
    private Stage mainWindow;
    private Muzak muzak;
    private DiscogsWorker m_discogs;
    
    public MainControl()
    {
        super();
        m_model = new MuzakDataModel();
        m_config = new MuzakConfig();
    }
    
    public void setMainWindow(final Stage win)
    {
        mainWindow = win;
    }
    
    public void setMuzak(Muzak m)
    {
        muzak = m;
    }
    
    public Stage getMainWindow()
    {
        return mainWindow;
    }
    
    public Configurations getConfigurations()
    {
        return m_config;
    }
    
    public boolean changeToFinnish()
    {
        return m_config.changeLangToFI();
    }
    
    public boolean changeToEnglish()
    {
        return m_config.changeLangToEN();
    }
    
    public void quit()
    {
        System.out.println("Quitting...");
        Platform.exit();
    }
    
    @Override
    public void handleViewRequest(DataModelObject dmo)
    {
        System.out.println("View Request from ID: " + dmo.getID() + " of " + dmo.getClass().getSimpleName());
    }

    @Override
    public void handleModifyRequest(DataModelObject dmo)
    {
        System.out.println("Modify Request from ID: " + dmo.getID() + " of " + dmo.getClass().getSimpleName());
    }

    @Override
    public void handleDeleteRequest(DataModelObject dmo)
    {
        System.out.println("Delete Request from ID: " + dmo.getID() + " of " + dmo.getClass().getSimpleName());
    }
    
    @Override
    public TreeSet<DataModelObject> getArtists()
    {
        @SuppressWarnings("unchecked")
        TreeSet<DataModelObject> set = (TreeSet<DataModelObject>)m_model.selectAll(Tables.ARTISTS, Order.ALPHABETICAL, false);
        
        return set;
    }
    
    @Override
    public ArrayList<KeyValueCombo> getDiscogsResults()
    {
        if(m_discogs == null)
            return new ArrayList<>();
            
        return m_discogs.getReleases();
    }
    
    @Override
    public void createArtist(DialogCallback callback)
    {
        showArtistDialog(callback.getOwningStage());
        callback.update();
    }
    
    @Override
    public void discogsRequest(DialogCallback callback)
    {
        m_discogs = new DiscogsWorker();
        m_discogs.searchReleases(callback.getQueryTitle(), callback.getQueryCatNumber(), callback.getQueryBarcode());
        //System.out.println("MainControl / Discogs request");
        showDiscogsResultsDialog(callback, m_discogs);
    }
    
    public void handleSearchAction(String searchString, String filter)
    {
        System.out.println("Search " + searchString + " from " + filter);
    }
    
    public void handleMenuAction(ActionEvent event)
    {
        if(!(event.getSource() instanceof MenuItem)) return;
        
        switch( ((MenuItem)event.getSource()).getId() )
        {
        case "ExitRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
            quit();
            break;
        case "AddArtistRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
            showArtistDialog(mainWindow);
            break;
        case "AddReleaseRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
            showReleaseDialog();
            break;
        case "AddTracksRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
            showTracksDialog();
            break;
        case "ModifyArtistRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
            break;
        case "ModifyReleaseRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
            break;
        case "ModifyTracksRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
            break;
        case "DeleteArtistRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
            break;
        case "DeleteReleaseRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
            break;
        case "DeleteTracksRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
            break;
        case "AboutRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
            break;
        default:
            /* Shouldn't really happen... */
            break;
        }
    }
    
    public void handleButtonAction(ActionEvent event)
    {
        if(!(event.getSource() instanceof Node)) return;
        
        switch(((Node)event.getSource()).getId())
        {
            case "SearchRequest":
                System.out.println("Search requested from Main Window.");
                break;

            case "AddArtistRequest":
                System.out.println("Add Artist requested from Main Window.");
                break;

            case "AddReleaseRequest":
                System.out.println("Add Release requested from Main Window.");
                break;

            case "AddTracksRequest":
                System.out.println("Add Tracks requested from Main Window.");
                break;

            default:
            /* Shouldn't really happen... */
            break;
        } 
    }
    
    private void showDiscogsResultsDialog(DialogCallback owner, DiscogsWorker worker)
    {
        DiscogsResultsDialog dialog = new DiscogsResultsDialog(m_config, this);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(owner.getOwningStage());
        
        worker.setOnFinished(dialog);
        worker.start();
        
        if(dialog.execute())
        {
            String user = dialog.getUserSelection();
            
            if(!user.isEmpty())
            {
                DiscogsWorker dw = new DiscogsWorker();
                dw.setRequestMode();
                dw.requestRelease(user);
                dw.setOnFinished(owner);
                dw.start();
                
                System.out.println("VALINTA " + dialog.getUserSelection());
            }
        }
        else
        {
        }
        
        /* Interrupt Discogs search if still ongoing. */
        if(worker.getState() != State.TERMINATED)
            worker.interrupt();
    }
    
    private void showTracksDialog()
    {
        TracksDialog dialog = new TracksDialog(m_config, this);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(mainWindow);
        
        if(dialog.execute())
        {
            for(TrackInfoElement tie : dialog.getData())
                System.out.println(tie);
        }
        else
        {
            
        }
    }
    
    private void showReleaseDialog()
    {
        ReleaseDialog dialog = new ReleaseDialog(m_config, this);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(mainWindow);
        
        if(dialog.execute())
        {
            Release release = MuzakDataModel.createRelease();
            release.setTitle(dialog.getReleaseTitle());
            release.setTechTitle(dialog.getTechTitle());
            release.setAltTitle(dialog.getAlternateTitle());
            release.setCatalogNumber(dialog.getCatalogNumber());
            release.setBarCode(dialog.getBarcode());
            release.addType(dialog.getTypeKeys());
            release.addMedia(dialog.getMediaKeys());
            release.setOriginalRelease(dialog.getIsOriginal());
            release.setExtendedEdition(dialog.getIsExtended());
            release.setCurYear(dialog.getCurrentYear());
            release.setOrgYear(dialog.getOriginalYear());
            release.setDiscs(dialog.getDiscCount());
            release.setStyleKey(dialog.getStyleKey());
            release.setRating(dialog.getRating());
            release.setComment(dialog.getComment());
            
            // TODO: Better exception handling would be in order.
            try
            {
                m_model.insert(release);
            }
            catch(IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch(NotUniqueSignatureException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            
        }
        
        dialog.output();
    }
    
    private void showArtistDialog(Stage owner)
    {
        ArtistDialog dialog = new ArtistDialog(m_config, this);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(owner);
        
        if(dialog.execute())
        {
            Artist artist = MuzakDataModel.createArtist();
            artist.setType(dialog.getType());
            artist.setName(dialog.getName());
            artist.setTechName(dialog.getTechName());
            artist.addAlias(dialog.getAliases());
            artist.setCountryCode(dialog.getOriginCode());
            artist.setFounded(dialog.getFounded());
            artist.setComment(dialog.getComment());
            
            // TODO: Better exception handling would be in order. 
            try
            {
                m_model.insert(artist);
                //muzak.addContent(UIUtils.getListInfoElement(artist, this));
            }
            catch(IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch(NotUniqueSignatureException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            
        }
    }
}
