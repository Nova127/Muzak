package muzak;

import java.util.ArrayList;

import muzakModel.Artist;
import muzakModel.Musician;
import muzakModel.MuzakDataModel;
import muzakModel.Release;

public class RecordInfoElement
{
    private Artist                      m_artist;
    private Release                     m_release;
    private ArrayList<TrackInfoElement> m_tracks;
    private ArrayList<Musician>         m_musicians;
    
    public RecordInfoElement()
    {
        m_artist = MuzakDataModel.createArtist();
        m_release = MuzakDataModel.createRelease();
        m_tracks = new ArrayList<>();
        m_musicians = new ArrayList<>();
    }
    
    public Artist getArtist()
    {
        return m_artist;
    }
    
    public Release getRelease()
    {
        return m_release;
    }
    
    public ArrayList<TrackInfoElement> getTracklist()
    {
        return m_tracks;
    }
    
    public void setArtist(Artist artist)
    {
        m_artist = artist;
    }
    
    public void setRelease(Release release)
    {
        m_release = release;
    }
    
    public void setTracks(ArrayList<TrackInfoElement> tracklist)
    {
        m_tracks = tracklist;
    }
}

