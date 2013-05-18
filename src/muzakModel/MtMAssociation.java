package muzakModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

class MtMAssociation<Key, AssocObj> implements Serializable
{
    private static final long serialVersionUID = 5246551967487521315L;

    /* Left-to-Right Associations. */
    private HashMap<Key, HashMap<Key, AssocObj>> m_LtoR;
    
    /* Right-to-Left Associations. */
    private HashMap<Key, HashMap<Key, AssocObj>> m_RtoL;
    
    public MtMAssociation()
    {
        m_LtoR = new HashMap<Key, HashMap<Key, AssocObj>>();
        m_RtoL = new HashMap<Key, HashMap<Key, AssocObj>>();
    }
    
    /**
     * Associates obj between keys. Prior association is overwritten.
     * 
     * @param left - Left key
     * @param right - Right key
     * @param obj - Association object
     */
    public void associate(Key left, Key right, AssocObj obj)
    {
        if(left == null || right == null /*|| obj == null*/) return;
        
        HashMap<Key, AssocObj> tmp = null;
        
        
        /* Add Left-to-Right association. */
        tmp = m_LtoR.get(left);

        if(tmp == null) // No association present.
            tmp = new HashMap<>();
        
        tmp.put(right, obj); // Replaces existent mapping.
        
        m_LtoR.put(left, tmp); // Left-to-Right association done.
        
        
        /* Add Right-to-Left association. */
        tmp = m_RtoL.get(right);
        
        if(tmp == null) // No association present.
            tmp = new HashMap<>();
            
        tmp.put(left, obj); // Replaces existent mapping.
        
        m_RtoL.put(right, tmp); // Right-to-Left association done.
    }
    
    public void associate(Key left, ArrayList<Key> rights, ArrayList<AssocObj> objs)
    {
        if(left == null || rights == null || objs == null) return;
        
        if(rights.size() != objs.size()) return;
        
        for(int i = 0; i < rights.size(); ++i)
            associate(left, rights.get(i), objs.get(i));
    }
    
    public Set<Key> getLeftKeys()
    {
        return m_LtoR.keySet();
    }
    
    public Set<Key> getRightKeys()
    {
        return m_RtoL.keySet();
    }
    
    public Set<Key> getLeftKeys(Key right)
    {
        HashMap<Key, AssocObj> left = m_RtoL.get(right);
        
        if(left != null)
            return left.keySet();
        
        return null;
    }
    
    public Set<Key> getRightKeys(Key left)
    {
        HashMap<Key, AssocObj> right = m_LtoR.get(left);
        
        if(right != null)
            return right.keySet();
        
        return null;
    }
    
    public TreeSet<Key> getDistinctLeftKeys(Set<Key> rights)
    {
        TreeSet<Key> lefts = new TreeSet<>();
        HashMap<Key, AssocObj> tmp = null;
        
        for(Key r : rights)
        {
            if((tmp = m_RtoL.get(r)) != null)
            {
                for(Key left : tmp.keySet()) lefts.add(left);
            }
        }
        
        return lefts;
    }
    
    public TreeSet<Key> getDistinctRightKeys(Set<Key> lefts)
    {
        TreeSet<Key> rights = new TreeSet<>();
        HashMap<Key, AssocObj> tmp = null;
        
        for(Key l : lefts)
        {
            if((tmp = m_LtoR.get(l)) != null)
            {
                for(Key right : tmp.keySet()) rights.add(right);
            }
        }
        
        return rights;
    }
    
    public HashMap<Key, AssocObj> getRightAssociations(Key left)
    {
        return m_LtoR.get(left);
    }
    
    public HashMap<Key, AssocObj> getLeftAssociations(Key right)
    {
        return m_RtoL.get(right);
    }
    
    public AssocObj getAssociation(Key left, Key right)
    {
        HashMap<Key, AssocObj> rmap = getRightAssociations(left);
        
        if(rmap == null) return null;
        
        return rmap.get(right);
    }
    
    /**
     * Dissociates an association. Empty associations are removed
     * in order to prevent hanging associations.
     * 
     * @param left - Left key
     * @param right - Right key
     */
    public void dissociate(Key left, Key right)
    {
        HashMap<Key, AssocObj> rmap = m_LtoR.get(left);
        HashMap<Key, AssocObj> lmap = m_RtoL.get(right);
        
        if(rmap != null)
        {
            rmap.remove(right);
            
            if(rmap.isEmpty()) // Remove empty association.
                m_LtoR.remove(left);
        }
        
        if(lmap != null)
        {
            lmap.remove(left);
            
            if(lmap.isEmpty()) // Remove empty association.
                m_RtoL.remove(right);
        }
    }
    
    /* Removes null associations. Seeks a list of LtoR-associations by left key. Whenever AssocObj equals null,
     * that association is removed bidirectionally.
     * 
     * Returns a list of keys which associated null AssocObjs.
     */
    public void removeNullAssociation(Key left)
    {
        HashMap<Key, AssocObj> rmap = m_LtoR.get(left);
        
        if(rmap != null)
        {
            ArrayList<Key> rkeys = new ArrayList<>();
            
            for(Map.Entry<Key, AssocObj> entry : rmap.entrySet())
            {
                if(entry.getValue() == null)
                {
                    rkeys.add(entry.getKey());
                }
            }
            
            for(Key rk : rkeys)
                dissociate(left, rk);
        }
    }
    
    public void outputLtoRAssocs()
    {
        for(Key lkey : m_LtoR.keySet())
        {
            System.out.println("Left key: " + lkey);
            HashMap<Key, AssocObj> tmp = m_LtoR.get(lkey);
            for(Key rkey : tmp.keySet())
            {
                System.out.println("\tRight key: " + rkey + "\tvalue: " + tmp.get(rkey));
            }
        }
    }
    
    public void outputRtoLAssocs()
    {
        for(Key rkey : m_RtoL.keySet())
        {
            System.out.println("Right key: " + rkey);
            HashMap<Key, AssocObj> tmp = m_RtoL.get(rkey);
            for(Key lkey : tmp.keySet())
            {
                System.out.println("\tLeft key: " + lkey + "\tvalue: " + tmp.get(lkey));
            }
        }
    }
    
    public void outputAssociations()
    {
        outputLtoRAssocs();
        System.out.println();
        outputRtoLAssocs();
    }
}























