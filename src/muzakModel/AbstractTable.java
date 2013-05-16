package muzakModel;

import java.util.*;

abstract class AbstractTable<DMO extends DataModelObject> extends Hashtable<Long, DMO>
{
    private static final long serialVersionUID = 3236155070724699007L;
    
    /* Returns true if there isn't data model object with equal signature present. */
    boolean uniqueSignature(DMO dmo)
    {
        for(Map.Entry<Long, DMO> entry : this.entrySet())
        {
            if(entry.getValue().equalSignatures(dmo))
                return false;
        }
        
        return true;
    }
    
    /* Insert data model object. Throws IAE, if object is invalid, and NUSE, if object with same signature already exists. */
    void insert(DMO dmo) throws IllegalArgumentException, NotUniqueSignatureException
    {
        if(!dmo.isValid()) throw new IllegalArgumentException("Invalid " + dmo.getClass().getSimpleName() + " object.");
        
        if(!this.uniqueSignature(dmo))
            throw new NotUniqueSignatureException(dmo.getClass().getSimpleName() + " object with same signature already exists.");
        
        this.put(dmo.getID(), dmo);
    }
    
    /* If data model object is present, overwrites object. Otherwise, object is inserted. Throws IAE, if object is invalid. */
    void replace(DMO dmo) throws IllegalArgumentException
    {
        if(!dmo.isValid()) throw new IllegalArgumentException("Invalid " + dmo.getClass().getSimpleName() + " object.");
        
        this.put(dmo.getID(), dmo);
    }
    
    /* Deletes data model object. If object is not present, method does nothing. */
    void delete(DMO dmo)
    {
        this.remove(dmo.getID());
    }
    
    DMO selectByID(long id) throws NullPointerException
    {
        return this.get(id);
    }
    
    TreeSet<DMO> selectByTitle(String title, boolean exactMatch)
    {
        TreeSet<DMO> selected = new TreeSet<>(AbstractDataModelObject.getAlphabeticalComparator(false));
        
        if(exactMatch)
        {
            for(DMO dmo : this.values())
            {
                if(dmo.equalsTitle(title))
                    selected.add(dmo);
            }
        }
        else
        {
            for(DMO dmo : this.values())
            {
                if(dmo.containsTitle(title))
                    selected.add(dmo);
            }
        }
        
        return selected;
    }
    
    TreeSet<DMO> selectAll(Comparator<DataModelObject> comparator)
    {
        TreeSet<DMO> set = null;

        if(comparator == null)
            set = new TreeSet<>();
        else
            set = new TreeSet<>(comparator);

        set.addAll(this.values());

        return set;
    }
    
    int entryCount()
    {
        return this.size();
    }
    
    void output()
    {
        String cl = this.getClass().getName();
        
        for(Long id : this.keySet())
            System.out.println(cl + " ID: " + id + " " + this.get(id));
        
        System.out.println();
    }
    
/*    protected Long generateUniqueID()
    {
        Long start = System.currentTimeMillis();
        
         Wait for one millisecond. 
        while((System.currentTimeMillis() - start) < 1L);
        
        return System.currentTimeMillis();
    }
    
    protected ArrayList<Long> generateMultipleIDs(int cnt) // Move to Tracks?
    {
        ArrayList<Long> keys = new ArrayList<>(cnt);
        
        for(int i = 0; i < cnt; ++i)
            keys.add(generateUniqueID());
        
        return keys;
    }*/
}
