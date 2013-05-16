
package muzakModel;

import muzakModel.ArtistTrackRecord.TrackType;

public class TrackInfoElement
{
    private Track               m_track;
    private ArtistTrackRecord   m_artistTrackRecord;
    private ReleaseTrackRecord    m_albumTrackRecord;
    
    public TrackInfoElement(String title, String ordinal)
    {
        super();
        //m_track             = new Track(title);
        m_artistTrackRecord = new ArtistTrackRecord(TrackType.PROPRIETARY);
        m_albumTrackRecord  = new ReleaseTrackRecord(ordinal);
    }
    public TrackInfoElement(String title, int ordinal)
    {
        super();
        //m_track             = new Track(title);
        m_artistTrackRecord = new ArtistTrackRecord(TrackType.PROPRIETARY);
        m_albumTrackRecord  = new ReleaseTrackRecord(Integer.toString(ordinal));
    }
    public TrackInfoElement(String title, String ordinal, TrackType type)
    {
        super();
        //m_track             = new Track(title);
        m_artistTrackRecord = new ArtistTrackRecord(type);
        m_albumTrackRecord  = new ReleaseTrackRecord(ordinal);
    }
    public TrackInfoElement(String title, int ordinal, TrackType type)
    {
        super();
        //m_track             = new Track(title);
        m_artistTrackRecord = new ArtistTrackRecord(type);
        m_albumTrackRecord  = new ReleaseTrackRecord(Integer.toString(ordinal));
    }
    
    /* Getters for each property. */
    public String getTitle()
    {
        return m_track.getTitle();
    }
    public String getOrdinal()
    {
        return m_albumTrackRecord.getOrdinal();
    }
    public int getLength()
    {
        return m_albumTrackRecord.getLength();
    }
    public String getTypeString()
    {
        return m_artistTrackRecord.getTrackTypeString();
    }
    public int getRating()
    {
        return m_artistTrackRecord.getRating();
    }
    
    /* Getters for each individual element. Package wide visibility. */
    Track getTrack()
    {
        return m_track;
    }
    ArtistTrackRecord getArtistTrackRecord()
    {
        return m_artistTrackRecord;
    }
    ReleaseTrackRecord getAlbumTrackRecord()
    {
        return m_albumTrackRecord;
    }
    
    public boolean isValid()
    {
        return (m_track.isValid() && m_artistTrackRecord.isValid() && m_albumTrackRecord.isValid());
    }
    
    /* Setters for each property. */
    public void setTitle(String title)
    {
        m_track.setTitle(title);
    }
    public void setOrdinal(String ordinal)
    {
        m_albumTrackRecord.setOrdinal(ordinal);
    }
    public void setLength(int length)
    {
        m_albumTrackRecord.setLength(length);
    }
    public void addType(ArtistTrackRecord.TrackType type)
    {
        m_artistTrackRecord.addType(type);
    }
    public void setType(ArtistTrackRecord.TrackType type)
    {
        m_artistTrackRecord.setType(type);
    }
    public void setRating(int rating)
    {
        m_artistTrackRecord.setRating(rating);
    }
    
    /* Setters for each individual element. Package wide visibility. */
    void setTrack(Track track)
    {
        m_track = track;
    }
    void setArtistTrackRecord(ArtistTrackRecord artistTrackRecord)
    {
        m_artistTrackRecord = artistTrackRecord;
    }
    void setAlbumTrackRecord(ReleaseTrackRecord albumTrackRecord)
    {
        m_albumTrackRecord = albumTrackRecord;
    }
}
