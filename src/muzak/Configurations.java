package muzak;

import java.util.Locale;

public interface Configurations
{
    /* Starting value for year of foundation list. */
    public int getFoundedStartValue();
    /* Ending value for year of foundation list. */
    public int getFoundedEndValue();
    
    public void setLangEN();
    public void setLangFI();
    
    public Locale getCurrentLocale();
}
