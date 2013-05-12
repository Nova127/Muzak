
package muzak;

import java.util.TreeSet;

import muzakModel.DataModelObject;

interface DialogObserver
{
    TreeSet<DataModelObject> getArtists();
    void createArtist(DialogCallback callback);
}
