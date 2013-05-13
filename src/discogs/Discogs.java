package discogs;

import java.io.IOException;
import java.util.ArrayList;
import muzak.DialogCallback;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public interface Discogs
{
    public void setOnFinished(DialogCallback callback);
    public void searchReleases(String t, String c, String b);
    
    public ArrayList<String> getReleases();
    
    public void request() throws IOException, ParseException;
}
