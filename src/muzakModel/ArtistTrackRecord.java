package muzakModel;

import java.io.Serializable;
import java.util.EnumSet;

public class ArtistTrackRecord implements Serializable
{
    private static final long serialVersionUID = 331899392424957921L;
    
    public enum TrackType
    {
        PROPRIETARY, COVER, COLLABORATION, REMIX
    };
    
    private EnumSet<TrackType> m_type;
    private int                m_rating;
    
    ArtistTrackRecord()
    {
    	super();
        m_type   = EnumSet.noneOf(TrackType.class);
        m_rating = -1;
    }
    ArtistTrackRecord(TrackType type)
    {
    	super();
    	m_type	 = EnumSet.of(type);
    	m_rating = -1;
    }
    ArtistTrackRecord(TrackType type, int rating)
    {
    	super();
        m_type   = EnumSet.of(type);
        m_rating = rating;
    }
    
    public boolean isOfType(TrackType type)
    {
        return m_type.contains(type);
    }
    public boolean isProprietary()
    {
        return isOfType(TrackType.PROPRIETARY);
    }
    public String getTrackTypeString()
    {
        return MuzakModelUtils.getSetAsString(m_type, ", ");
    }
    public int getRating()
    {
        return m_rating;
    }
    
    public boolean isValid()
    {
        if(!m_type.isEmpty())
        {
            if(isProprietary())
            {
                return (m_type.size() == 1);
            }
            
            return true;
        }
        
        return false;
    }
    
    /*
     * Adds a new track type. If new or old type is PROPRIETARY types will be overwritten.
     */
    public void addType(TrackType type)
    {
        /* PROPRIETARY is mutually exclusive with everything else, hence... */
        if(type == TrackType.PROPRIETARY || this.isProprietary())
            setType(type);
        else
            m_type.add(type);
    }
    /*
     * Sets track type to a single new type. Old types will be overwritten.
     */
    public void setType(TrackType type)
    {
        m_type.clear();
        m_type.add(type);
    }
    /*
     * Sets track type to new types. If new types contains PROPRIETARY, it will take
     * precedence. This prevents absurd type information.
     */
    public void setType(EnumSet<TrackType> types)
    {
        if(types.contains(TrackType.PROPRIETARY))
            setType(TrackType.PROPRIETARY);
        else
            m_type = types;
    }
    public void setRating(int rating)
    {
        m_rating = rating;
    }
    
    public String toString()
    {
        return m_type.toString() + " " + m_rating;
    }
}
