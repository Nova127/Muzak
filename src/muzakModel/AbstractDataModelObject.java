
package muzakModel;

import java.io.Serializable;
import java.util.Comparator;

abstract class AbstractDataModelObject implements DataModelObject, Serializable
{
    private static final long serialVersionUID = -4446314369874451164L;
    
    protected long      m_ID;
    protected String    m_dru; // Discogs Resource URI
    
    AbstractDataModelObject(long id)
    {
        m_ID = id;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        
        if(obj == null || !(obj instanceof AbstractDataModelObject)) return false;
        
        return (m_ID == ((AbstractDataModelObject)obj).m_ID);
    }
    
    @Override
    public int compareTo(DataModelObject other)
    {
        return Long.compare(m_ID, other.getID());
    }
    
    public static Comparator<DataModelObject> getAlphabeticalComparator(final boolean descending)
    {
        return new Comparator<DataModelObject>()
        {
            @Override
            public int compare(DataModelObject dmo1, DataModelObject dmo2)
            {
                return (descending ? -1 : 1) * dmo1.alphaCompareTo(dmo2);
            }
        };
    }
    
    public static Comparator<DataModelObject> getChronologicalComparator(final boolean descending)
    {
        return new Comparator<DataModelObject>()
        {
            @Override
            public int compare(DataModelObject dmo1, DataModelObject dmo2)
            {
                return (descending ? -1 : 1) * dmo1.chronoCompareTo(dmo2);
            }
        };
    }
    
    public static Comparator<DataModelObject> getNaturalComparator()
    {
        return new Comparator<DataModelObject>()
        {
            @Override
            public int compare(DataModelObject dmo1, DataModelObject dmo2)
            {
                return dmo1.naturalCompareTo(dmo2);
            }
        };
    }
    
    public long getID()
    {
        return m_ID;
    }
    
    @Override
    public String getIDString()
    {
        return Long.toString(m_ID);
    }
    
    public void setID(long id)
    {
        m_ID = id;
    }
    
    @Override
    public String getDiscogsResourceUri()
    {
        return m_dru;
    }
    
    @Override
    public void setDiscogsResourceUri(String uri)
    {
        m_dru = uri;
    }
}


















