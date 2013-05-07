package muzak;

import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

class MuzakConfig implements Configurations
{
    private Locale m_locale;
    
    /* Default values: */
    private static final int DEF_FOUNDED_START_VALUE = 1900;
    private static final int DEF_FOUNDED_END_VALUE = 0;
    private static final int DEF_RELEASED_START_VALUE = 1950;
    private static final int DEF_RELEASED_END_VALUE = 0;
    
    private int m_foundedStartValue;
    private int m_foundedEndValue;
    private int m_releasedStartValue;
    private int m_releasedEndValue;
    
    public MuzakConfig()
    {
        m_locale = Locale.getDefault();
        
        m_foundedStartValue = DEF_FOUNDED_START_VALUE;
        m_foundedEndValue = DEF_FOUNDED_END_VALUE;
        m_releasedStartValue = DEF_RELEASED_START_VALUE;
        m_releasedEndValue = DEF_RELEASED_END_VALUE;
    }
    
    @Override
    public int getFoundedStartValue()
    {
        return m_foundedStartValue;
    }

    @Override
    public int getFoundedEndValue()
    {
        if(m_foundedStartValue > m_foundedEndValue)
            return getCurrentYear();
        
        return m_foundedEndValue;
    }

    @Override
    public int getReleasedStartValue()
    {
        return m_releasedStartValue;
    }
    
    @Override
    public int getReleasedEndValue()
    {
        if(m_releasedStartValue > m_releasedEndValue)
            return getCurrentYear();
        
        return m_releasedEndValue;
    }
    
    @Override
    public ResourceBundle getResources(Resources resource)
    {
        String baseName = "bundles.";
        
        switch(resource)
        {
        case MAIN_WINDOW:
            baseName += "MainWindowTitles";
            break;

        case ABSTRACT_PHASED_DIALOG:
            baseName += "AbstractPhasedDialog";
            break;
            
        case ARTIST_DIALOG:
            baseName += "ArtistDialog";
            break;
            
        case RELEASE_DIALOG:
            baseName += "ReleaseDialog";
            break;
            
        case LIST_OF_COUNTRIES:
            baseName += "ListOfCountries";
            break;
            
        case LIST_OF_STYLES:
            baseName += "ListOfStyles";
            break;
            
        case LIST_OF_RELEASE_TYPES:
            baseName += "ListOfReleaseTypes";
            break;
            
        case LIST_OF_RELEASE_MEDIA:
            baseName += "ListOfReleaseMedia";
            break;
            
        default:
            /* Shouldn't happen... :P */
            break;
        }
        
        return ResourceBundle.getBundle(baseName, m_locale);
    }
    
    public void setFoundedStartValue(int value)
    {
        m_foundedStartValue = value;
    }
    
    public void setFoundedEndValue(int value)
    {
        m_foundedEndValue = value;
    }
    
    @Override
    public boolean changeLangToEN()
    {
        return changeLocale("en");
    }

    @Override
    public boolean changeLangToFI()
    {
        return changeLocale("fi");
    }

    @Override
    public Locale getCurrentLocale()
    {
        return m_locale;
    }
    
    private int getCurrentYear()
    {
        Calendar c = Calendar.getInstance();
        
        return c.get(Calendar.YEAR);
    }
    
    private boolean changeLocale(String iso639code)
    {
        Locale locale = new Locale(iso639code);

        if(m_locale.getLanguage().equals(locale.getLanguage())) return false;
        
        m_locale = locale;
        
        return true;
    }
}
