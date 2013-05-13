
package muzak;

import java.util.ArrayList;
import java.util.TreeSet;

import muzakModel.DataModelObject;

interface DialogObserver
{
    TreeSet<DataModelObject> getArtists();
    ArrayList<KeyValueCombo> getDiscogsResults();
    void createArtist(DialogCallback callback);
    
    void discogsRequest(DialogCallback callback);
}
