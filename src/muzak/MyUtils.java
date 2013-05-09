package muzak;

public class MyUtils
{
    public static String trimWhitespaces(String input)
    {
        /* Remove leading and trailing whitespaces: */
        input = input.trim();
        
        /* Replace possible whitespace character combinations with single space: */
        return input.replaceAll("\\s+", " ");
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
