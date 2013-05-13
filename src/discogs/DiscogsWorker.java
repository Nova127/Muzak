package discogs;

import java.io.IOException;
import java.util.ArrayList;

import muzak.DialogCallback;
import muzak.KeyValueCombo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class DiscogsWorker extends Thread
{
    private static final String urlBase = "http://api.discogs.com/";
    private static final String relStub = "release_title=";
    private static final String catStub = "catno=";
    private static final String barStub = "barcode=";
    private String m_query = "";
    private DialogCallback m_callback;
    private ArrayList<KeyValueCombo> m_resultSet;
    
    public DiscogsWorker()
    {
        
    }
    
    public void setOnFinished(DialogCallback callback)
    {
        m_callback = callback;
        
    }
    
    public void searchReleases(String t, String c, String b)
    {
        String query = "";
        
        if(!t.isEmpty())
            query += relStub + t.replaceAll("\\s+", "+");
        if(!c.isEmpty())
            query += (query.isEmpty() ? "" : "&") + catStub + c;
        if(!b.isEmpty())
            query += (query.isEmpty() ? "" : "&") + barStub + b;
        
        query = urlBase + "database/search?" + query;
        
        System.out.println(query);
        m_query = query;
    }
    
    public ArrayList<KeyValueCombo> getReleases()
    {
       return m_resultSet;
    }
    
    private ArrayList<KeyValueCombo> parseSearchReleaseResponse(JSONObject job)
    {
        ArrayList<KeyValueCombo> results = new ArrayList<>();
        
        if(job == null) return results;

        String res_url = "", result = "";
        JSONArray res = (JSONArray)job.get("results");
        
        for(int i=0; i < res.size(); ++i)
        {
            res_url = "" + ((JSONObject)res.get(i)).get("resource_url");
            
            result  = "TITLE:\t "   + ((JSONObject)res.get(i)).get("title") + "\n";
            result += "STYLE:\t "   + jsonArrayToString(((JSONObject)res.get(i)).get("style")) + "\n";
            result += "FORMAT:\t "  + jsonArrayToString(((JSONObject)res.get(i)).get("format")) + "\n";
            result += "LABEL:\t "   + jsonArrayToString(((JSONObject)res.get(i)).get("label")) + "\n";
            result += "CAT#:\t "    + ((JSONObject)res.get(i)).get("catno") + "\n";
            result += "BARCODE: "   + jsonArrayToString(((JSONObject)res.get(i)).get("barcode")) + "\n";

            results.add(new KeyValueCombo(res_url, result));
            System.out.println(result + res_url);
        }
        
        return results;
    }
    
    @Override
    public void run()
    {
        try
        {
            request();
        }
        catch(Exception e)
        {}
    }
    
    public void request() throws IOException, ParseException
    {
        //JSONReader reader = new JSONReader();
        
        JSONObject job = JSONReader.readJsonFromUrl(m_query);
        m_resultSet = parseSearchReleaseResponse(job);
        
        m_callback.update();
    }
    
    private String jsonArrayToString(Object job)
    {
        if(job == null) return "null";
        
        String res = "";
        try
        {
            JSONArray jar = (JSONArray)job;
            for(int i = 0; i < jar.size(); ++i)
                res += (i < jar.size()-1 ? jar.get(i) + ", " : jar.get(i));
        }
        catch(ClassCastException e)
        {
            res = "java.lang.ClassCastException";
        }
        
        return res;
/*        System.out.println(job);
        String jar = (String)job;
        jar = jar.replace("^\\[", "");
        System.out.println(jar);
        String res = "";
        String tmp = "";
        
        List<String> bits = Arrays.asList(jar.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
        Iterator<String> i = bits.iterator();
        
        while(i.hasNext())
        {
            tmp = i.next().trim().replace("^\\\"|\\\"$", "");
            res += (i.hasNext() ? tmp + ", " : tmp);
        }
        
        return res;*/
    }
}
