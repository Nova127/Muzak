package muzak;

import java.util.ArrayList;
import java.util.Iterator;

public class MyUtils
{
    public static String asValueString(ArrayList<KeyValueCombo> combos, String separator)
    {
        String res = "";
        
        if(combos != null && !combos.isEmpty())
        {
            String tmp = "";
            Iterator<KeyValueCombo> i = combos.iterator();
            
            while(i.hasNext())
            {
                tmp = i.next().toString(); // Moves current to next
                res += (i.hasNext()) ? tmp + separator : tmp;
            }
        }
        
        return res;
    }
    
    public static String trimWhitespaces(String input)
    {
        /* Remove leading and trailing whitespaces: */
        input = input.trim();
        
        /* Replace possible whitespace character combinations with single space: */
        return input.replaceAll("\\s+", " ");
    }
    
    public static String trimToAlphanumeric(String input)
    {
        /* Remove all non-alphanumeric characters. */
        return input.replaceAll("[^a-zA-Z0-9]", "");
    }
    
    public static String artistTechName(String input)
    {
        String output = "";
        
        //if(input.isEmpty()) return output;
        
        String[] tokens = input.trim().split("\\s+");
        
        for(int i = 1; i < tokens.length; ++i)
            output += tokens[i] + " ";
        
        return output.trim() + ", " + tokens[0];
    }
    
    public static String trimArticles(String input)
    {
        String tail = "";
        
        String[] tokens = input.trim().split("\\s+");
        
        if(tokens[0].equalsIgnoreCase("the"))
        {
            tail = ", the";
        }
        else if(tokens[0].equalsIgnoreCase("an"))
        {
            tail = ", an";
        }
        else if(tokens[0].equalsIgnoreCase("a"))
        {
            tail = ", a";
        }
        else
            return trimWhitespaces(input);
        
        String output = "";
        
        for(int i = 1; i < tokens.length; ++i)
            output += tokens[i] + " ";
        
        return output.trim() + tail;
    }
}
