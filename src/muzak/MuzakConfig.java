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
    
    private int m_foundedStartValue;
    private int m_foundedEndValue;
    
    public MuzakConfig()
    {
        m_locale = Locale.getDefault();
        
        m_foundedStartValue = DEF_FOUNDED_START_VALUE;
        m_foundedEndValue = DEF_FOUNDED_END_VALUE;
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
        {
            Calendar c = Calendar.getInstance();
            
            return c.get(Calendar.YEAR);
        }
        
        return m_foundedEndValue;
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
            
        default:
            /* Shouldn't happen:P */
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
    
    private boolean changeLocale(String iso639code)
    {
        Locale locale = new Locale(iso639code);

        if(m_locale.getLanguage().equals(locale.getLanguage())) return false;
        
        m_locale = locale;
        
        return true;
    }
}
