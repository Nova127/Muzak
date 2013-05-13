package discogs;

import java.io.IOException;
import java.util.ArrayList;
import muzak.DialogCallback;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class DiscogsWorker implements Discogs
{
    private static final String urlBase = "http://api.discogs.com/";
    private static final String relStub = "release_title=";
    private static final String catStub = "catno=";
    private static final String barStub = "barcode=";
    private String m_query = "";
    private DialogCallback m_callback;
    private ArrayList<String> m_resultSet;
    
    public DiscogsWorker()
    {
        
    }
    
    @Override
    public void setOnFinished(DialogCallback callback)
    {
        m_callback = callback;
        
    }
    
    @Override
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
//        try 
//        {
//            request(query);
//        }
//        catch (Exception e)
//        {
//            
//        }
    }
    
    @Override
    public ArrayList<String> getReleases()
    {
       return m_resultSet;
    }
    
    public ArrayList<String> getReleases(JSONObject json)
    {
        ArrayList<String> results = new ArrayList<>();
        
        if(json==null)
            return results;
        
        JSONArray res = (JSONArray)json.get("results");
        
        StringBuilder builder = new StringBuilder();
        
        for(int i=0; i < res.size(); ++i)
        {
            Object id = ((JSONObject)res.get(i)).get("id");
            Object title = ((JSONObject)res.get(i)).get("title");
            Object barcode = ((JSONObject)res.get(i)).get("barcode");
            Object style = ((JSONObject)res.get(i)).get("style");
            Object country = ((JSONObject)res.get(i)).get("country");
            Object catno = ((JSONObject)res.get(i)).get("catno");
            
            builder.append(id);
            builder.append(" ");
            builder.append(title);
            builder.append(" ");
            builder.append(barcode);
            builder.append(" ");
            builder.append(style);
            builder.append(" ");
            builder.append(country);
            builder.append(" ");
            builder.append(catno);

            results.add(builder.toString());
            builder.delete(0, builder.length()-1);
            System.out.println(id + " " + title + " " + country + " " + style + " " + catno + " " + barcode);
        }
        
        return results;
    }
    
    public void request() throws IOException, ParseException
    {
        JSONReader reader = new JSONReader();
        
        JSONObject json = reader.readJsonFromUrl(m_query);
        
        m_resultSet = getReleases(json);
        m_callback.update();
    }

    
}
