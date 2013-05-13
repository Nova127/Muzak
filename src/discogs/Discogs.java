package discogs;

import java.io.IOException;
import java.util.ArrayList;
import muzak.DialogCallback;
import muzak.KeyValueCombo;

import org.json.simple.parser.ParseException;

public interface Discogs
{
    public void setOnFinished(DialogCallback callback);
    public void searchReleases(String t, String c, String b);
    
    public ArrayList<KeyValueCombo> getReleases();
    
    public void request() throws IOException, ParseException;
}
