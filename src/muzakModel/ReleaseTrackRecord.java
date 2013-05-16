package muzakModel;

import java.io.Serializable;

public class ReleaseTrackRecord implements Serializable
{
    private static final long serialVersionUID = 4959212489879639183L;
    
    private String m_ordinal;
    private int    m_length; // in seconds
    
    ReleaseTrackRecord()
    {
        super();
        m_ordinal = "";
        m_length  = 0;
    }
    
    ReleaseTrackRecord(String ordinal)
    {
    	super();
    	m_ordinal = ordinal;
    	m_length  = 0;
    }
    
    ReleaseTrackRecord(String ordinal, int length)
    {
    	super();
        m_ordinal = ordinal;
        m_length  = length;
    }
    
    ReleaseTrackRecord(String ordinal, String length)
    {
        super();
        m_ordinal = ordinal;
        setLength(length);
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
    
    public String getLengthString()
    {
        return toLengthString(m_length);
    }
    
    public static String toLengthString(int length)
    {
        if(length <= 0) return "";

        int m = length % 60;
        int s = length - 60*m;

        String len  = (m > 0 ? m + ":" : "0");
               len += (s > 9 ? s : "0" + s);
        
        return len;
    }
    
    public void setOrdinal(String ordinal)
    {
        m_ordinal = ordinal;
    }
    
    public void setLength(int length)
    {
        m_length = length;
    }
    
    public void setLength(String length)
    {
        m_length = parseLength(length);
    }
    
    public static int parseLength(String length)
    {
        /* Split string on non-numerics. */
        String[] bits = length.split("\\D+");
        
        if(bits.length == 0 || bits.length > 3)
        {
            /* User has clearly misunderstood the meaning of track length... */
            return 0;
        }
        
        int value = 0;
        
        try
        {
            for(int i = bits.length-1, k = 0; i >= 0; --i, ++k)
            {
                value += Integer.parseInt(bits[i]) * k*60;
            }
        }
        catch(NumberFormatException e)
        {
            value = 0;
        }
        
        return value;
    }
    
    public String toString()
    {
        return m_ordinal + ", " + m_length + "s";
    }
}
