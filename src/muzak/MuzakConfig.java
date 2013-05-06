package muzak;

import java.util.Calendar;
import java.util.Locale;

class MuzakConfig implements Configurations
{
    /* Default values: */
    private static final int DEF_FOUNDED_START_VALUE = 1900;
    private static final int DEF_FOUNDED_END_VALUE = 0;
    
    private int m_foundedStartValue;
    private int m_foundedEndValue;
    
    public MuzakConfig()
    {
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

    public void setFoundedStartValue(int value)
    {
        m_foundedStartValue = value;
    }
    
    public void setFoundedEndValue(int value)
    {
        m_foundedEndValue = value;
    }

    @Override
    public void setLangEN() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setLangFI() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Locale getCurrentLocale() {
        // TODO Auto-generated method stub
        return null;
    }
}
