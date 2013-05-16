package muzakModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

public class Musician extends AbstractDataModelObject implements Serializable
{
    private static final long serialVersionUID = -1192794361572815963L;
    
    private ArrayList<String>   m_forenames;
    private String              m_surname;
    private TreeSet<String>     m_pseudonyms;
    
    Musician(long id)
    {
        super(id);
        m_surname   = "";
    }

    @Override
    public boolean equalSignatures(DataModelObject dmo)
    {
        if(this == dmo) return true;
        
        if(dmo == null || !(dmo instanceof Musician)) return false;

        Musician other = (Musician)dmo;
        
        /* If IDs are equal, objects should be same. */
        if(m_ID == other.m_ID) return true;
        
        if(m_surname == null)
        {
            if(other.m_surname != null)
                return false;
        }
        else if(!m_surname.equals(other.m_surname))
            return false;
        
        if(m_forenames == null)
        {
            if(other.m_forenames != null)
                return false;
        }
        else if(!m_forenames.equals(other.m_forenames))
            return false;
        
        return true;
    }
    
    @Override
    public int alphaCompareTo(DataModelObject dmo)
    {
        return naturalCompareTo(dmo);
    }
    
    @Override
    public int chronoCompareTo(DataModelObject dmo)
    {
        /* Chronological order is not applicable to Musicians. */
        return naturalCompareTo(dmo);
    }
    
    @Override
    public int naturalCompareTo(DataModelObject dmo)
    {
        Musician other = (Musician)dmo;
        
        int res = m_surname.compareTo(other.m_surname);
        
        if(res == 0)
        {
            res = getForenameString().compareTo(other.getForenameString());
        }
        
        return res;
    }
    
    @Override
    public boolean isValid()
    {
        if(m_surname != null && !m_surname.isEmpty()) // Surname OK?
        {
            if(m_forenames != null && !m_forenames.isEmpty())
            {
                for(String name : m_forenames) // Forenames OK?
                {
                    if(name == null || name.isEmpty()) return false;
                }
                
                /* Surname and all forenames are OK... */
                return true;
            }
        }
        
        return false;
    }

    @Override
    public boolean equalsTitle(String title)
    {
        if(!m_surname.contentEquals(title)) // Check if surname is a match
        {
            /* Assume validity, i.e. forenames should be present. */
            for(String name : m_forenames)
            {
                if(name.contentEquals(title)) return true;
            }
            
            /* Finally, check pseudonyms. */
            if(m_pseudonyms != null && !m_pseudonyms.isEmpty())
            {
                return m_pseudonyms.contains(title);
            }
            
            /* Everything checked, but no match... */
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean containsTitle(String title)
    {
        if(!m_surname.contains(title)) // Check if surname is a match
        {
            /* Assume validity, i.e. forenames should be present. */
            for(String name : m_forenames)
            {
                if(name.contains(title)) return true;
            }
            
            /* Finally, check pseudonyms. */
            if(m_pseudonyms != null && !m_pseudonyms.isEmpty())
            {
                Iterator<String> i = m_pseudonyms.iterator();
                
                while(i.hasNext())
                {
                    if(i.next().contains(title)) return true;
                }
            }
            
            /* Everything checked, but no match... */
            return false;
        }
        
        return true;
    }
    
    /* ******* *
     * GETTERS *
     * ******* */
    public ArrayList<String> getForenames()
    {
        if(m_forenames == null) return new ArrayList<>();
        
        return m_forenames;
    }
    
    public String getForenameString()
    {
        String str = "";
        
        if(m_forenames != null)
        {
            for(int i = 0; i < m_forenames.size(); ++i)
                str += m_forenames.get(i) + " ";
            
            str = str.trim();
        }
        
        return str;
    }
    
    public String getSurname()
    {
        return m_surname;
    }
    
    public String getName()
    {
        return getForenameString() + " " + m_surname;
    }
    
    public String getListName()
    {
        return m_surname + ", " + getForenameString();
    }
    
    /*
     * If pseudonyms are set, return pseudonyms as alphabetically sorted string list.
     * Otherwise, empty string list is returned.
     */
    public ArrayList<String> getPseudonyms()
    {
        if(m_pseudonyms == null || m_pseudonyms.isEmpty()) return new ArrayList<String>();
        
        return new ArrayList<>(Arrays.asList((String[])m_pseudonyms.toArray()));
    }
    
    public String getPseudonymString()
    {
        return MuzakModelUtils.getSetAsString(m_pseudonyms, ", "); // Returns "" on null or empty.
    }
    
    /* ******* *
     * SETTERS *
     * ******* */
    public void setForenames(String[] forenames)
    {
        if(forenames.length == 0) return;

        m_forenames = new ArrayList<>(Arrays.asList(forenames));
    }
    
    public void setSurname(String surname)
    {
        m_surname = surname;
    }
    
    /*
     * Adds a pseudonym to the list of pseudonyms. TreeSet doesn't allow null or duplicate values.
     * Returns true if object changed as a result of the call, false otherwise.
     */
    public boolean addPseudonym(String pseudonym)
    {
        if(m_pseudonyms == null)
            m_pseudonyms = new TreeSet<>();
        
        return m_pseudonyms.add(pseudonym);
    }
    
    /*
     * Removes a pseudonym from the list of pseudonyms.
     * Returns true if object changed as a result of the call, false otherwise.
     */
    public boolean removePseudonym(String pseudonym)
    {
        if(m_pseudonyms == null) return false;
        
        boolean ret = m_pseudonyms.remove(pseudonym);
        
        if(m_pseudonyms.size() == 0)
            m_pseudonyms = null;
        
        return ret;
    }
    
    @Override
    public String getShortInfoString()
    {
        return getName();
    }
    
    @Override
    public String getListString()
    {
        return getListName();
    }
    
    @Override
    public String toString()
    {
        return getName();
    }
}
