/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muzak;

import java.util.Locale;

/**
 *
 * @author Harri
 */
public class ArtistDialog extends AbstractPhasedDialog
{
    public ArtistDialog(final Locale locale)
    {
        super(locale);
    }

    @Override
    protected void proceed() 
    {
    }

    @Override
    protected void rollBack() 
    {
    }
    
}
