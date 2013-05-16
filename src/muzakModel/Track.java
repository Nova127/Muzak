package muzakModel;

import java.io.Serializable;

public class Track extends AbstractDataModelObject implements Serializable
{
    private static final long serialVersionUID = -1286237697228255525L;
    
    private String  m_title;
    
    Track(long id)
    {
        super(id);
        m_title = "";
    }
    
    Track(long id, String title)
    {
        super(id);
        m_title = title;
    }
    
    @Override
    public boolean equalSignatures(DataModelObject dmo)
    {
        if(this == dmo) return true;
        
         return (dmo == null || !(dmo instanceof Track));
    }
    
    @Override
    public int alphaCompareTo(DataModelObject dmo)
    {
        return naturalCompareTo(dmo);
    }
    
    @Override
    public int chronoCompareTo(DataModelObject dmo)
    {
        /* Chronological order is not applicable to Tracks. */
        return naturalCompareTo(dmo);
    }
    
    @Override
    public int naturalCompareTo(DataModelObject dmo)
    {
        return m_title.compareTo(((Track)dmo).m_title);
    }
    
    @Override
    public boolean isValid()
    {
        return (m_title != null);
    }

    @Override
    public boolean equalsTitle(String title)
    {
        return (m_title.contentEquals(title));
    }
    
    @Override
    public boolean containsTitle(String title)
    {
        return (m_title.contains(title));
    }
    
    public String getTitle()
    {
        return m_title;
    }
    
    public void setTitle(String title)
    {
        m_title = title;
    }
    
    @Override
    public String getShortInfoString()
    {
        return m_title;
    }
    
    @Override
    public String toString()
    {
        return "\"" + m_title + "\"";
    }
}
