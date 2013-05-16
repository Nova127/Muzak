package discogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import muzak.DialogCallback;
import muzak.KeyValueCombo;
import muzak.KeyValueElement;
import muzak.RecordInfoElement;
import muzak.TrackInfoElement;
import muzakModel.Artist;
import muzakModel.Release;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class DiscogsWorker extends Thread
{
    public enum Mode{ INQUIRE, REQUEST };
    
    private static final String urlBase = "http://api.discogs.com/";
    private static final String relStub = "release_title=";
    private static final String catStub = "catno=";
    private static final String barStub = "barcode=";
    private String m_query = "";
    private DialogCallback m_callback;
    private static ArrayList<KeyValueElement> m_resultSet;
    private Mode m_mode;
    
    public DiscogsWorker()
    {
        m_mode = Mode.INQUIRE;
    }
    
    public void setOnFinished(DialogCallback callback)
    {
        m_callback = callback;
        
    }
    
    public void setInquireMode()
    {
        m_mode = Mode.INQUIRE;
    }
    
    public void setRequestMode()
    {
        m_mode = Mode.REQUEST;
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
    
    public void requestRelease(String resourceUri)
    {
        m_query = resourceUri;
    }
    
    public static ArrayList<KeyValueElement> getReleases()
    {
       return m_resultSet;
    }
    
    private ArrayList<KeyValueElement> parseSearchReleaseResponse(JSONObject job)
    {
        ArrayList<KeyValueElement> results = new ArrayList<>();
        
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

            results.add(new KeyValueElement(res_url, result));
            //System.out.println(result + res_url);
        }
        
        return results;
    }
    
    private RecordInfoElement parseRIE(JSONObject job)
    {
        RecordInfoElement rie = new RecordInfoElement();
        
        Artist artist = rie.getArtist();
        Release release = rie.getRelease();
        ArrayList<TrackInfoElement> tracklist = new ArrayList<>();
        
        System.out.println(job.get("status"));
        
//        ArrayList<JSONObject> labels = jsonArrayToObjects(job.get("labels"));
//        String catno = "";
//        for(JSONObject j : labels)
//            catno += j.get("catno");
//        
//        release.setCatalogNumber(catno);
//        
//        ArrayList<JSONObject> tlist = jsonArrayToObjects(job.get("tracklist"));
//        System.out.println("Tracklist length: " + tlist.size());
        
        Object tmp = null;
        String value = "";
        
        ArrayList<String> catno = subobjects(job.get("labels"), "catno");
        value = toCommaSeparatedString(catno);
        
        release.setCatalogNumber(value);
        
        tmp = job.get("year");
        
        if(tmp != null)
        {
            try
            {
                int year = Integer.parseInt(tmp.toString());
                release.setCurYear(year);
            }
            catch(NumberFormatException e)
            {
                
            }
        }
        
        ArrayList<String[]> tl = subobjects(job.get("tracklist"), "position", "title", "duration");
        for(String[] t : tl)
        {
            tracklist.add(new TrackInfoElement(t[0], t[1], t[2], false, "0"));
        }
        
        
        rie.setRelease(release);
        rie.setTracks(tracklist);
        tracklist = null;
//        for(TrackInfoElement t : rie.getTracklist())
//            System.out.println(t);
        
        return rie;
    }
    
    private String toCommaSeparatedString(ArrayList<String> values)
    {
        String css = "";
        int L = values.size();

        for(int i = 0; i < L; ++i)
        {
            css += (i == L-1 ? values.get(i) : values.get(i) + ", ");
        }
        
        return css;
    }
    
    private String extractCatNumbers(ArrayList<String[]> catnos)
    {
        String catno = "";
        
        for(int i = 0; i < catnos.size(); ++i)
        {
            catno += (i == catnos.size()-1 ? catnos.get(i)[0] : catnos.get(i)[0] + ", ");
        }
        
        return catno;
    }
    
    @Override
    public void run()
    {
        try
        {
            if(m_mode == Mode.INQUIRE)
                inquire();
            else if(m_mode == Mode.REQUEST)
                request();
        }
        catch(Exception e)
        {}
    }
    
    public void inquire() throws IOException, ParseException
    {
        //JSONReader reader = new JSONReader();
        
        JSONObject job = JSONReader.readJsonFromUrl(m_query);
        m_resultSet = parseSearchReleaseResponse(job);
        //KeyValueElement kve = new KeyValueElement("asd", "qwer");
        //m_resultSet.add(kve);
        
        m_callback.update();
        System.out.println("INQUIRE EXIT");
    }
    
    public void request() throws IOException, ParseException
    {
        //JSONReader reader = new JSONReader();
        
        JSONObject job = JSONReader.readJsonFromUrl(m_query);
        //System.out.println(job);
        //m_resultSet = parseSearchReleaseResponse(job);
        
        System.out.println(m_callback != null ? m_callback.getClass().getName() : "null callback");
        RecordInfoElement rie = parseRIE(job);
        m_callback.injectValues(rie);
    }
    
    private ArrayList<String> subobjects(Object jsonArray, String subkey)
    {
        ArrayList<String> values = new ArrayList<>();
        
        Object tmp = null;
        for(JSONObject job : jsonArrayToObjects(jsonArray))
        {
            tmp = job.get(subkey);
            if(tmp != null)
                values.add(tmp.toString());
        }
        
        return values;
    }
    
    private ArrayList<String[]> subobjects(Object jsonArray, String... subkeys)
    {
        ArrayList<String[]> values = new ArrayList<>();
        
        Object tmp = null;
        for(JSONObject job : jsonArrayToObjects(jsonArray))
        {
            String[] entry = new String[subkeys.length];
            for(int i = 0; i < subkeys.length; ++i)
            {
                tmp = job.get(subkeys[i]);
                entry[i] = (tmp != null ? tmp.toString() : "");
            }
            values.add(entry);
        }
        
        return values;
    }
    
    private ArrayList<JSONObject> jsonArrayToObjects(Object job)
    {
        ArrayList<JSONObject> list = new ArrayList<>();
        
        try
        {
            JSONArray jar = (JSONArray)job;
            for(int i = 0; i < jar.size(); ++i)
                list.add((JSONObject)jar.get(i));
        }
        catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        
        return list;
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
            e.printStackTrace();
        }
        
        //System.out.println(res);
        
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
