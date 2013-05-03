package muzak;

import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;

public class MainControl
{
    /* Initialize 'default' locale. */
    private Locale locale = new Locale("fi");
    
    public MainControl()
    {
        super();
    }
    
    public Locale getLocale()
    {
        return this.locale;
    }
    
    public boolean changeToFinnish()
    {
        return changeLocale("fi");
    }
    
    public boolean changeToEnglish()
    {
        return changeLocale("en");
    }
    
    public boolean changeLocale(String iso639code)
    {
        Locale other = new Locale(iso639code);

        if(this.locale.getLanguage().equals(other.getLanguage())) return false;
        
        this.locale = other;
        
        return true;
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
        
        switch( ((Node)event.getSource()).getId() )
        {
        case "SearchRequest":
            System.out.println("Search requested from Main Window.");
            break;
            
/*        case "LangFIRequest":
            if(changeLocale("fi"))
            {
                System.out.println("Changing language to Finnish.");
            }
            break;
            
        case "LangENRequest":
            if(changeLocale("en"))
            {
                System.out.println("Changing language to English.");
            }
            break;*/
            
        default:
            /* Shouldn't really happen... */
            break;
        }
    }
}
