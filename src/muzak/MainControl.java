
package muzak;

import muzakModel.Artist;
import muzakModel.MuzakDataModel;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainControl
{
    private MuzakDataModel m_model;
    private MuzakConfig m_config;
    /* Initialize 'default' locale. */
    //private Locale m_locale = new Locale("fi");
    private Stage mainWindow;
    
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
    
//    public boolean changeLocale(String iso639code)
//    {
//        Locale other = new Locale(iso639code);
//
//        if(this.m_locale.getLanguage().equals(other.getLanguage())) return false;
//        
//        this.m_locale = other;
//        
//        return true;
//    }
    
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
            break;
        case "AddArtistRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
            showArtistDialog();
            break;
        case "AddReleaseRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
            break;
        case "AddTracksRequest":
            System.out.println(((MenuItem)event.getSource()).getId());
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
        
        switch (((Node) event.getSource()).getId()) 
        {
            case "SearchRequest":
                System.out.println("Search requested from Main Window.");
                break;

            case "AddArtistRequest":
                System.out.println("Add Artist requested from Main Window.");
                showArtistDialog();
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
    
    private void showArtistDialog()
    {
        ArtistDialog dialog = new ArtistDialog(m_config);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(mainWindow);
        
        if(dialog.execute())
        {
            String type = dialog.getType();
            String name = dialog.getName();
            String aliasesString = dialog.getAliases();
            String ccode = dialog.getOriginCode();
            String founded = dialog.getFounded();
            String comment = dialog.getComment();
            
            Artist artist = MuzakDataModel.createArtist();
            artist.setType(type);
            artist.setName(name);
            artist.setTechName(name);
            artist.setCountryCode(ccode);
            artist.setFounded(Integer.parseInt(founded));
            artist.setComment(comment);
            String[] aliases = aliasesString.split(";");
            for(int i = 0; i < aliases.length; ++i)
            {
                artist.addAlias(aliases[i].trim());
            }
        }
        else
            System.out.println("Dialog rejected!");
    }
    
}
