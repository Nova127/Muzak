package muzakModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

class MuzakModelUtils
{
    /* Returns a string representation of the elements of a set. All elements except the last
     * are separated by separator provided with function call. */
    public static<E> String getSetAsString(Set<E> set, String separator)
    {
        String str = "";
        
        if(set != null && !set.isEmpty())
        {
            String tmp = "";
            Iterator<E> i = set.iterator();
            
            while(i.hasNext())
            {
                tmp = i.next().toString(); // Moves current to next
                str += (i.hasNext()) ? tmp + separator : tmp;
            }
        }
        
        return str;
    }
    
    public static<E> String getSetAsString(Set<E> set)
    {
        return MuzakModelUtils.getSetAsString(set, ", ");
    }
    
    /* Returns enum values as a list of strings. */
    public static<E extends Enum<E>> ArrayList<String> getEnumValues(Class<E> enumClass)
    {
        ArrayList<String> values = new ArrayList<>();
        
        for(Enum<E> item : enumClass.getEnumConstants())
        {
            values.add(item.toString());
        }
        
        return values;
    }
}
