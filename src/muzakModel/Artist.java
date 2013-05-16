package muzakModel;

import java.io.Serializable;
import java.util.*;

public class Artist extends AbstractDataModelObject implements Serializable
{
    private static final long serialVersionUID = -6135493892808869762L;
    
    public enum ArtistType { BAND, ARTIST, OTHER };
    
    /* Technical name - name to be used in f.eg. ordering. */
    private String          m_techName;
    /* Name - name of the artist/band. */
    private String          m_name;
    private TreeSet<String> m_aliases;
    private ArtistType      m_type;
    private String          m_countryCode; // ISO-3166 double character country code
    private int             m_founded;
    private String          m_comment;
    
    Artist(long id)
    {
        super(id);
        m_techName      = "";
        m_name          = "";
        m_type          = ArtistType.BAND;
        m_countryCode   = "";
        m_founded       = -1;
        m_comment       = "";
    }

    @Override
    public boolean equalSignatures(DataModelObject dmo)
    {
        if(this == dmo) return true;
        
        if(dmo == null || !(dmo instanceof Artist)) return false;

        Artist other = (Artist)dmo;
        
        /* If IDs are equal, objects should be same. */
        if(m_ID == other.m_ID) return true;
        
        if(m_techName == null)
        {
            if(other.m_techName != null)
                return false;
        }
        else if(!m_techName.equals(other.m_techName))
            return false;
        
        if(m_founded != other.m_founded) return false;
        
        if(m_countryCode == null)
        {
            if(other.m_countryCode != null)
                return false;
        }
        else if(!m_countryCode.equals(other.m_countryCode))
            return false;
        
        return true;
    }
    
    @Override
    public boolean isValid()
    {
        if(m_techName == null || m_name == null) return false;
        
        return (!m_techName.isEmpty() && !m_name.isEmpty());
    }
    
    @Override
    public int alphaCompareTo(DataModelObject dmo)
    {
        Artist other = (Artist)dmo;
        
        int res = m_techName.compareTo(other.m_techName);
        
        if(res == 0)
        {
            res = m_countryCode.compareTo(other.m_countryCode);
            
            if(res == 0)
            {
                res = Integer.compare(m_founded, other.m_founded);
            }
        }
        
        return res;
    }
    
    @Override
    public int chronoCompareTo(DataModelObject dmo)
    {
        Artist other = (Artist)dmo;
        
        int res = Integer.compare(m_founded, other.m_founded);
        
        if(res == 0)
        {
            res = m_techName.compareTo(other.m_techName);
            
            if(res == 0)
            {
                res = m_countryCode.compareTo(other.m_countryCode);
            }
        }
        
        return res;
    }
    
    @Override
    public int naturalCompareTo(DataModelObject dmo)
    {
        Artist other = (Artist)dmo;
        
        int res = m_techName.compareTo(other.m_techName);
        
        if(res == 0)
        {
            res = Integer.compare(m_founded, other.m_founded);

            if(res == 0)
            {
                res = m_countryCode.compareTo(other.m_countryCode);
            }
        }
        
        return res;
    }
    
    @Override
    public boolean equalsTitle(String title)
    {
        return (m_name.contentEquals(title) || m_techName.contentEquals(title));
    }
    
    @Override
    public boolean containsTitle(String title)
    {
        return (m_name.contains(title) || m_techName.contains(title));
    }
    
    
    /* ******* *
     * GETTERS *
     * ******* */
    public String getTechName()
    {
        return m_techName;
    }
    
    public String getName()
    {
        return m_name;
    }
    
    public ArrayList<String> getAliases()
    {
        if(m_aliases == null || m_aliases.isEmpty()) return new ArrayList<>();
        
        return new ArrayList<String>(Arrays.asList((String[])m_aliases.toArray()));
    }
    
    public String getAliasesString()
    {
        return MuzakModelUtils.getSetAsString(m_aliases);
    }
    
    public ArtistType getType()
    {
        return m_type;
    }
    
    public String getTypeString()
    {
        return m_type.toString();
    }
    
    public String getCountryCode()
    {
        return m_countryCode;
    }
    
    public int getFounded()
    {
        return m_founded;
    }
    
    public String getComment()
    {
        return m_comment;
    }
    
    
    /* ******* *
     * SETTERS *
     * ******* */
    public void setTechName(String techName)
    {
        m_techName = techName;
    }
    
    public void setName(String name)
    {
        m_name = name;
    }
    
    /*
     * Adds an alias to the list of aliases. TreeSet doesn't allow null or duplicate values.
     * Returns true if object changed as a result of the call, false otherwise.
     */
    public boolean addAlias(String alias)
    {
        if(m_aliases == null)
            m_aliases = new TreeSet<>();
        
        return m_aliases.add(alias);
    }
    
    /*
     * Adds aliases to the list of aliases. TreeSet doesn't allow null or duplicate values.
     * Returns true if object changed as a result of the call, false otherwise.
     */
    public boolean addAlias(List<String> aliases)
    {
        boolean changed = false;
        
        for(String a : aliases)
            if(addAlias(a))
                changed = true;
        
        return changed;
    }
    
    /*
     * Removes an alias from the list of aliases.
     * Returns true if object changed as a result of the call, false otherwise.
     */
    public boolean removeAlias(String alias)
    {
        if(m_aliases == null) return false;
        
        boolean ret = m_aliases.remove(alias);
        
        if(m_aliases.size() == 0)
            m_aliases = null;
        
        return ret;
    }
    
    public void clearAliases()
    {
        m_aliases = null;
    }
    
    public void setType(ArtistType type)
    {
        m_type = type;
    }
    
    public void setType(String type)
    {
        m_type = ArtistType.valueOf(ArtistType.class, type);
    }
    
    public void setCountryCode(String cc)
    {
        m_countryCode = cc;
    }
    
    public void setFounded(int founded)
    {
        m_founded = founded;
    }
    
    public void setComment(String comment)
    {
        m_comment = comment;
    }
    
    @Override
    public String getShortInfoString()
    {
        String signature = "";
        
        if(!m_countryCode.isEmpty())
            signature = m_countryCode;
        
        if(m_founded >= 0)
            signature += "/" + m_founded;
        
        if(!signature.isEmpty())
            signature = "[" + signature + "]";
        
        return m_name + " " + signature;
    }
    
    @Override
    public String getListString()
    {
        String signature = "";
        
        if(!m_countryCode.isEmpty())
            signature = m_countryCode;
        
        if(m_founded >= 0)
            signature += "/" + m_founded;
        
        if(!signature.isEmpty())
            signature = "[" + signature + "]";
        
        return m_techName + " " + signature;
    }
    
    @Override
    public String toString()
    {
        return "\"" + m_name + "\" [" + m_countryCode + ", " + m_founded + "]";
    }
}
