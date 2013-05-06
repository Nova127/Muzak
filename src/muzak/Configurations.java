package muzak;

import java.util.Locale;
import java.util.ResourceBundle;

public interface Configurations
{
    public enum Resources
    {
        MAIN_WINDOW,
        ABSTRACT_PHASED_DIALOG,
        ARTIST_DIALOG,
        LIST_OF_COUNTRIES;
    }
    
    /* Starting value for year of foundation list. */
    public int getFoundedStartValue();
    /* Ending value for year of foundation list. */
    public int getFoundedEndValue();
    
    public ResourceBundle getResources(Resources resource);
    
    public boolean changeLangToEN();
    public boolean changeLangToFI();
    
    public Locale getCurrentLocale();
}
