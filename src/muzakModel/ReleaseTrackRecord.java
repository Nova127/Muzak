package muzakModel;

public class ReleaseTrackRecord
{
    private String m_ordinal;
    private int    m_length; // in seconds
    
    ReleaseTrackRecord()
    {
        super();
        m_ordinal = "";
        m_length  = -1;
    }
    ReleaseTrackRecord(String ordinal)
    {
    	super();
    	m_ordinal = ordinal;
    	m_length  = -1;
    }
    ReleaseTrackRecord(String ordinal, int length)
    {
    	super();
        m_ordinal = ordinal;
        m_length  = length;
    }
    
    public boolean isValid()
    {
        return (m_ordinal != null && !m_ordinal.isEmpty());
    }
    
    public String getOrdinal()
    {
        return m_ordinal;
    }
    public int getLength()
    {
        return m_length;
    }
    
    public void setOrdinal(String ordinal)
    {
        m_ordinal = ordinal;
    }
    public void setLength(int length)
    {
        m_length = length;
    }
    
    public String toString()
    {
        return m_ordinal + ", " + m_length + "s";
    }
}
