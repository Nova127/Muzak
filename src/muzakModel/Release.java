package muzakModel;

import java.io.Serializable;
import java.util.*;

public class Release extends AbstractDataModelObject implements Serializable
{
    private static final long serialVersionUID = -5160254038412714587L;
    
    /* Technical title - title to be used in f.eg. ordering. */
    private String              m_techTitle;
    /* Title - title of the release (will be shown to user as such). */
    private String              m_title;
    private String              m_altTitle;
    private String              m_catalogNumber;
    private String              m_barCode;
    private TreeSet<String>     m_type;
    private boolean             m_original;   // T if original release, F if reissue
    private boolean             m_extended;   // T if edition is extended, F if not
    private int                 m_orgYear;    // Original Year of Release
    private int                 m_curYear;    // This release's Year of Release
    private TreeSet<String>     m_media;
    private int                 m_discs;
    private String              m_styleKey;
    private int                 m_rating;
    private String              m_comment;
    private String              m_artworkUri; // URI to Discogs
    private String              m_artwork;    // Name of local Artwork file
    
    Release(long id)
    {
        super(id);
        m_techTitle     = "";
        m_title         = "";
        m_altTitle      = "";
        m_catalogNumber = "";
        m_barCode       = "";
        m_type          = null;
        m_original      = true;
        m_extended      = false;
        m_orgYear       = -1;
        m_curYear       = -1;
        m_media         = null;
        m_discs         = -1;
        m_styleKey      = ""; // Undefined
        m_rating        = -1;
        m_comment       = "";
        m_artworkUri    = "";
        m_artwork       = "";
    }

    @Override
    public boolean equalSignatures(DataModelObject dmo)
    {
        if(this == dmo) return true;
        
        if(dmo == null || !(dmo instanceof Release)) return false;
        
        Release other = (Release)dmo;
        
        /* If IDs are equal, objects should be same. */
        if(m_ID == other.m_ID) return true;
        
        if(m_barCode == null)
        {
            if(other.m_barCode != null)
                return false;
        }
        else if(!m_barCode.equals(other.m_barCode))
            return false;
        
        if(m_catalogNumber == null)
        {
            if(other.m_catalogNumber != null)
                return false;
        }
        else if(!m_catalogNumber.equals(other.m_catalogNumber))
            return false;
        
        if(m_techTitle == null)
        {
            if(other.m_techTitle != null)
                return false;
        }
        else if(!m_techTitle.equals(other.m_techTitle))
            return false;
        
        return true;
    }

    @Override
    public int alphaCompareTo(DataModelObject dmo)
    {
        Release other = (Release)dmo;
        
        int res = m_techTitle.compareTo(other.m_techTitle);
        
        if(res == 0)
        {
            res = m_altTitle.compareTo(other.m_altTitle);
            
            if(res == 0)
            {
                res = m_catalogNumber.compareTo(other.m_catalogNumber);
            }
        }
        
        return res;
    }
    
    @Override
    public int chronoCompareTo(DataModelObject dmo)
    {
        return naturalCompareTo(dmo);
    }
    
    @Override
    public int naturalCompareTo(DataModelObject dmo)
    {
        Release other = (Release)dmo;
        
        int res = Integer.compare(m_orgYear, other.m_orgYear);
        
        if(res == 0)
        {
            res = Integer.compare(m_curYear, other.m_curYear);
            
            if(res == 0)
            {
                res = m_techTitle.compareTo(other.m_techTitle);
                
                if(res == 0)
                {
                    res = m_catalogNumber.compareTo(other.m_catalogNumber);
                }
            }
        }
        
        return res;
    }
    
    @Override
    public boolean isValid()
    {
        if(m_techTitle == null || m_title == null) return false;
        
        return (!m_techTitle.isEmpty() && !m_title.isEmpty());
    }

    @Override
    public boolean containsTitle(String title)
    {
        return (m_title.contains(title) || m_altTitle.contains(title) || m_techTitle.contains(title));
    }

    @Override
    public boolean equalsTitle(String title)
    {
        return (m_title.contentEquals(title) || m_altTitle.contentEquals(title) || m_techTitle.contentEquals(title));
    }
    
    
    /* ******* *
     * GETTERS *
     * ******* */
    public String getTechTitle()
    {
        return m_techTitle;
    }
    
    public String getTitle()
    {
        return m_title;
    }
    
    public String getAltTitle()
    {
        return m_altTitle;
    }
    
    public String getCatalogNumber()
    {
        return m_catalogNumber;
    }
    
    public String getBarCode()
    {
        return m_barCode;
    }
    
    public ArrayList<String> getReleaseTypeKeys()
    {
        if(m_type == null || m_type.isEmpty()) return new ArrayList<>();
        
        return new ArrayList<>(Arrays.asList((String[])m_type.toArray()));
    }
    
    public boolean isOriginalRelease()
    {
        return m_original;
    }
    
    public boolean isExtendedEdition()
    {
        return m_extended;
    }
    
    public int getOrgYear()
    {
        return m_orgYear;
    }
    
    public String getOrgYearString()
    {
        if(m_orgYear < 0) return "";
        
        return Integer.toString(m_orgYear);
    }
    
    public int getCurYear()
    {
        return m_curYear;
    }
    
    public String getCurYearString()
    {
        if(m_curYear < 0) return "";
        
        return Integer.toString(m_curYear);
    }
    
    public ArrayList<String> getReleaseMediaKeys()
    {
        if(m_media == null || m_media.isEmpty()) return new ArrayList<>();
        
        return new ArrayList<>(Arrays.asList((String[])m_media.toArray()));
    }
    
    public int getDiscCount()
    {
        return m_discs;
    }
    
    public String getStyleKey()
    {
        return m_styleKey;
    }
    
    public int getRating()
    {
        return m_rating;
    }
    
    public String getComment()
    {
        return m_comment;
    }
    
    public String getArtworkUri()
    {
        return m_artworkUri;
    }
    
    public String getArtwork()
    {
        return m_artwork;
    }
    
    /* ******* *
     * SETTERS *
     * ******* */
    public void setTechTitle(String techTitle)
    {
        m_techTitle = techTitle;
    }
    
    public void setTitle(String title)
    {
        m_title = title;
    }
    
    public void setAltTitle(String altTitle)
    {
        m_altTitle = altTitle;
    }
    
    public void setCatalogNumber(String catalogNumber)
    {
        m_catalogNumber = catalogNumber;
    }
    
    public void setBarCode(String barCode)
    {
        m_barCode = barCode;
    }
    
    /*
     * Methods for adding Type/Media key.
     * TreeSet doesn't allow null or duplicate values.
     * Returns true if object changed as a result of the call, false otherwise.
     */
    public boolean addType(String typeKey)
    {
        if(m_type == null)
            m_type = new TreeSet<>();
        
        return m_type.add(typeKey);
    }
    
    public boolean addType(List<String> typeKeys)
    {
        boolean changed = false;
        
        for(String key : typeKeys)
            if(addType(key))
                changed = true;
        
        return changed;
    }
    
    public boolean addMedia(String mediaKey)
    {
        if(m_media == null)
            m_media = new TreeSet<>();
        
        return m_media.add(mediaKey);
    }
    
    public boolean addMedia(List<String> mediaKeys)
    {
        boolean changed = false;
        
        for(String key : mediaKeys)
            if(addMedia(key))
                changed = true;
        
        return changed;
    }
    
    /*
     * Methods for removing Type/Media key.
     * Returns true if object changed as a result of the call, false otherwise.
     * Returns false if attempting removal with invalid key.
     */
    public boolean removeType(String typeKey)
    {
        if(m_type == null) return false;
        
        boolean ret = m_type.remove(typeKey);
        
        if(m_type.size() == 0)
            m_type = null;
        
        return ret;
    }
    
    public void clearType()
    {
        m_type = null;
    }
    
    public boolean removeMedia(String mediaKey)
    {
        if(m_media == null) return false;
        
        boolean ret = m_media.remove(mediaKey);
        
        if(m_media.size() == 0)
            m_media = null;
        
        return ret;
    }
    
    public void clearMedia()
    {
        m_media = null;
    }
    
    public void setOriginalRelease(boolean isOriginal)
    {
        m_original = isOriginal;
    }
    
    public void setExtendedEdition(boolean isExtended)
    {
        m_extended = isExtended;
    }
    
    public void setOrgYear(int orgYear)
    {
        m_orgYear = orgYear;
    }
    
    public void setCurYear(int curYear)
    {
        m_curYear = curYear;
    }
    
    public void setDiscs(int discs)
    {
        m_discs = discs;
    }
    
    public void setStyleKey(String key)
    {
        m_styleKey = key;
    }
    
    public void setRating(int rating)
    {
        m_rating = rating;
    }
    
    public void setComment(String comment)
    {
        m_comment = comment;
    }
    
    public void setArtworkUri(String uri)
    {
        m_artworkUri = uri;
    }
    
    public void setArtwork(String artwork)
    {
        m_artwork = artwork;
    }
    
    @Override
    public String getShortInfoString()
    {
        String signature = "";
        
        if(m_curYear >= 0)
            signature = Integer.toString(m_curYear);
        
        if(!m_catalogNumber.isEmpty())
            signature += "/" + m_catalogNumber;
        
        if(!signature.isEmpty())
            signature = "[" + signature + "]";
        
        return m_title + " " + signature;
    }
    
    @Override
    public String toString()
    {
        return "\"" + m_title + "\" [" + m_type + ", " + m_media + ", " + (m_original ? "Original" : "Reissue") + ", " + m_curYear + "]";
    }
}
