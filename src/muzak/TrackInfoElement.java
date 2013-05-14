
package muzak;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class TrackInfoElement
{
    private final SimpleStringProperty ordinal;
    private final SimpleStringProperty title;
    private final SimpleStringProperty length;
    private final SimpleBooleanProperty cover;
    private final SimpleStringProperty rating;
    
    public TrackInfoElement()
    {
        this.ordinal    = new SimpleStringProperty();
        this.title      = new SimpleStringProperty();
        this.length     = new SimpleStringProperty();
        this.cover      = new SimpleBooleanProperty();
        this.rating     = new SimpleStringProperty();
    }
    
    public TrackInfoElement(String ordinal, String title, String length, boolean cover, String rating)
    {
        this.ordinal    = new SimpleStringProperty(ordinal);
        this.title      = new SimpleStringProperty(title);
        this.length     = new SimpleStringProperty(length);
        this.cover      = new SimpleBooleanProperty(cover);
        this.rating     = new SimpleStringProperty(rating);
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
    
    public String getRating()
    {
        return rating.get();
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

    public SimpleStringProperty getRatingProperty()
    {
        return rating;
    }
    
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
    
    public void setRating(String rating)
    {
        this.rating.set(rating);
    }
    
    @Override
    public String toString()
    {
        return getOrdinal() + " " + getTitle() + " " + getLength() + " " + getCover() + " " + getRating();
    }
}
























