
package muzakModel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class TrackInfoElement
{
    private final SimpleStringProperty ordinal;
    private final SimpleStringProperty title;
    private final SimpleStringProperty length;
    private final SimpleBooleanProperty cover;
    
    public TrackInfoElement()
    {
        this.ordinal    = new SimpleStringProperty();
        this.title      = new SimpleStringProperty();
        this.length     = new SimpleStringProperty();
        this.cover      = new SimpleBooleanProperty();
    }
    
    public TrackInfoElement(String ordinal, String title, String length, boolean cover)
    {
        this.ordinal    = new SimpleStringProperty(ordinal);
        this.title      = new SimpleStringProperty(title);
        this.length     = new SimpleStringProperty(length);
        this.cover      = new SimpleBooleanProperty(cover);
    }
    
    public TrackInfoElement(Track track, ReleaseTrackRecord rtrecord, boolean cover)
    {
        this.ordinal    = new SimpleStringProperty(rtrecord.getOrdinal());
        this.title      = new SimpleStringProperty(track.getTitle());
        this.length     = new SimpleStringProperty(rtrecord.getLengthString());
        this.cover      = new SimpleBooleanProperty(cover);
    }
    
    public String getOrdinal()
    {
        return ordinal.get();
    }
    
    public String getTitle()
    {
        return title.get();
    }
    
    public String getLength()
    {
        return length.get();
    }
    
    public boolean getCover()
    {
        return cover.get();
    }
    
    public SimpleStringProperty getOrdinalProperty()
    {
        return ordinal;
    }

    public SimpleStringProperty getTitleProperty()
    {
        return title;
    }

    public SimpleStringProperty getLengthProperty()
    {
        return length;
    }

    public SimpleBooleanProperty getCoverProperty()
    {
        return cover;
    }
    
    public ReleaseTrackRecord getReleaseTrackRecord()
    {
        return new ReleaseTrackRecord(this.ordinal.get(), this.length.get());
    }
    
/*    public Track getTrack() // TODO: Consider removing this, might lead to unexpected errors...
    {
        return MuzakDataModel.createTrack(this.title.get());
    }*/
    
    public void setOrdinal(String ordinal)
    {
        this.ordinal.set(ordinal);
    }
    
    public void setTitle(String title)
    {
        this.title.set(title);
    }
    
    public void setLength(String length)
    {
        this.length.set(length);
    }
    
    public void setCover(boolean cover)
    {
        this.cover.set(cover);
    }
    
    @Override
    public String toString()
    {
        return getOrdinal() + " \"" + getTitle() + "\" " + getLength() + " " + (getCover() ? "cover" : "");
    }
}
























