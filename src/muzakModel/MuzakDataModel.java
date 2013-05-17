
package muzakModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class MuzakDataModel
{
    public enum Tables { ARTISTS, RELEASES, TRACKS, MUSICIANS };
    public enum Order { BY_ID, NATURAL, ALPHABETICAL, CHRONOLOGICAL };
    
    /* Tables: */
    private ArtistTable     m_artists;
    private TrackTable      m_tracks;
    private ReleaseTable    m_releases;
    private MusicianTable   m_musicians;
    
    /* Association tables: */
    private MtMAssociation<Long, Boolean>            m_artaLink;
    private MtMAssociation<Long, ReleaseTrackRecord> m_retaLink;
    private MtMAssociation<Long, String>             m_remuLink;
    
    public MuzakDataModel()
    {
        m_artists   = new ArtistTable();
        m_tracks    = new TrackTable();
        m_releases  = new ReleaseTable();
        m_musicians = new MusicianTable();
        
        m_artaLink  = new MtMAssociation<>();
        m_retaLink  = new MtMAssociation<>();
        m_remuLink  = new MtMAssociation<>();
    }
    
    
    /* ******************************** *
     * FACTORIES FOR DATA MODEL OBJECTS *
     * ******************************** */
    public static Artist createArtist()
    {
        return new Artist(generateUniqueID());
    }
    public static Release createRelease()
    {
        return new Release(generateUniqueID());
    }
    public static Track createTrack()
    {
        return new Track(generateUniqueID());
    }
    public static Track createTrack(String title)
    {
        return new Track(generateUniqueID(), title);
    }
    public static Musician createMusician()
    {
        return new Musician(generateUniqueID());
    }
    public static ReleaseTrackRecord createAlbumTrackRecord()
    {
        return new ReleaseTrackRecord();
    }
    
    
    /* ************************** *
     * METHODS FOR SELECTING DATA *
     * ************************** */
    public TreeSet<? extends DataModelObject> selectByTitle(String title, boolean exactMatch, Tables source)
    {
        TreeSet<? extends DataModelObject> set = null;
        
        switch(source)
        {
        case ARTISTS:
            set = m_artists.selectByTitle(title, exactMatch);
            break;
            
        case RELEASES:
            set = m_releases.selectByTitle(title, exactMatch);
            break;
            
        case TRACKS:
            set = m_tracks.selectByTitle(title, exactMatch);
            break;
            
        case MUSICIANS:
            set = m_musicians.selectByTitle(title, exactMatch);
            break;
            
        default: /* Definitely shouldn't happen... :P */
            break;
        }
        
        return set;
    }
    
    public TreeSet<? extends DataModelObject> selectAll(Tables source, Order order, boolean descending)
    {
        TreeSet<? extends DataModelObject> set = null;
        
        switch(source)
        {
        case ARTISTS:
            set = m_artists.selectAll(decideComparator(order, descending));
            break;
            
        case RELEASES:
            set = m_releases.selectAll(decideComparator(order, descending));
            break;
            
        case TRACKS:
            set = m_tracks.selectAll(decideComparator(order, descending));
            break;
            
        case MUSICIANS:
            set = m_musicians.selectAll(decideComparator(order, descending));
            break;
            
        default: /* Definitely shouldn't happen... :P */
            break;
        }
        
        return set;
    }
    
    public ArrayList<Release> selectReleasesByArtist(Artist artist)
    {
        ArrayList<Release> releases = new ArrayList<>();
        
        /* First select TIDs by Artist: */
        Set<Long> tids = m_artaLink.getRightKeys(artist.getID());
        
        if(tids != null && !tids.isEmpty())
        {
            /* Then select RIDs by TIDs: */
            TreeSet<Long> rids = (TreeSet<Long>)m_retaLink.getDistinctLeftKeys(tids);
            
            if(rids != null && !rids.isEmpty())
            {
                /* Finally select Releases by RIDs: */
                for(Long rid : rids)
                {
                    releases.add(m_releases.get(rid));
                }
            }
        }
        
        return releases;
    }
    
    public ArrayList<Release> selectReleasesByArtist(long aid)
    {
        Artist artist = m_artists.selectByID(aid);
        
        return selectReleasesByArtist(artist);
    }
    
    /* ************************** *
     * METHODS FOR INSERTING DATA *
     * ************************** */
    public void insert(DataModelObject dmo) throws IllegalArgumentException, NotUniqueSignatureException
    {
        if(dmo instanceof Artist)
        {
            m_artists.insert((Artist)dmo);
        }
        else if(dmo instanceof Release)
        {
            m_releases.insert((Release)dmo);
        }
        else if(dmo instanceof Track)
        {
            m_tracks.insert((Track)dmo);
        }
        else if(dmo instanceof Musician)
        {
            m_musicians.insert((Musician)dmo);
        }
        else
            throw new IllegalArgumentException("Illegal argument class of type " + dmo.getClass().getSimpleName());
    }
    
    public void     insertTracklist(TreeSet<TrackInfoElement> tracklist, Artist artist, Release release)
           throws   IllegalArgumentException, NotUniqueSignatureException
    {
        /* Throws IAE, if check fails and thus prevents associations. */
        checkExistence(artist, release);
        
        putTracklist(tracklist, artist.getID(), release.getID());
        
/*        long aid = artist.getID(), rid = release.getID();
        Track track = null;
        
        for(TrackInfoElement tie : tracklist)
        {
            track = new Track(generateUniqueID(), tie.getTitle());
            
            m_tracks.insert(track);
            
            m_artaLink.associate(aid, track.getID(), tie.getCover());
            m_retaLink.associate(rid, track.getID(), tie.getReleaseTrackRecord());
        }*/
    }
    
    public void     insertTracklist(TreeSet<TrackInfoElement> tracklist, long aid, long rid)
           throws   IllegalArgumentException, NotUniqueSignatureException
    {
        if(m_artists.containsKey(aid) && m_releases.containsKey(rid))
        {
            putTracklist(tracklist, aid, rid);
        }
        else
            throw new IllegalArgumentException("Inexistent Artist or Release ID.");
    }
    
    private void     putTracklist(TreeSet<TrackInfoElement> tracklist, long aid, long rid)
            throws   IllegalArgumentException, NotUniqueSignatureException
    {
        Track track = null;
        
        for(TrackInfoElement tie : tracklist)
        {
            track = new Track(generateUniqueID(), tie.getTitle());
            
            m_tracks.insert(track);
            
            /* Associate artist and track. */
            m_artaLink.associate(aid, track.getID(), tie.getCover());
            /* Associate release and track. */
            m_retaLink.associate(rid, track.getID(), tie.getReleaseTrackRecord());
        }
    }
    
    public void associateArtistAndTrack(boolean cover, Artist artist, Track track) throws IllegalArgumentException
    {
        /* Throws IAE, if check fails and thus prevents associations. */
        checkExistence(artist, track);
        
        m_artaLink.associate(artist.getID(), track.getID(), cover);
    }
    
    public void associateReleaseAndTrack(ReleaseTrackRecord record, Release release, Track track) throws IllegalArgumentException
    {
        if(!record.isValid()) throw new IllegalArgumentException("Invalid album-track record!");

        /* Throws IAE, if check fails and thus prevents associations. */
        checkExistence(release, track);
        
        m_retaLink.associate(release.getID(), track.getID(), record);
    }
    
    public void associateArtistAndRelease(Artist artist, Release release) throws IllegalArgumentException
    {
        Long tid = generateUniqueID();
        
        /* Throws IAE, if check fails and thus prevents associations. */
        checkExistence(artist, release);
        
        m_artaLink.associate(artist.getID(), tid, null);
        m_retaLink.associate(release.getID(), tid, null);
    }
    
    public void associateArtistAndRelease(long aid, Release release) throws IllegalArgumentException
    {
        Artist artist = m_artists.selectByID(aid);
        
        associateArtistAndRelease(artist, release);
    }
    
    
    /* ******* *
     * PRIVATE *
     * ******* */
    private void checkExistence(DataModelObject... objects) throws IllegalArgumentException
    {
        for(DataModelObject dmo : objects)
        {
            if(dmo instanceof Artist)
            {
                if(!m_artists.containsKey(dmo.getID()))
                    throw new IllegalArgumentException("Inexistent Artist ID.");
            }
            else if(dmo instanceof Release)
            {
                if(!m_releases.containsKey(dmo.getID()))
                    throw new IllegalArgumentException("Inexistent Release ID.");
            }
            else if(dmo instanceof Track)
            {
                if(!m_tracks.containsKey(dmo.getID()))
                    throw new IllegalArgumentException("Inexistent Track ID.");
            }
            else if(dmo instanceof Musician)
            {
                if(!m_musicians.containsKey(dmo.getID()))
                    throw new IllegalArgumentException("Inexistent Musician ID.");
            }
            else
                throw new IllegalArgumentException("Illegal argument class of type " + dmo.getClass().getSimpleName());
        }
    }
    
    private Comparator<DataModelObject> decideComparator(Order order, boolean descending)
    {
        Comparator<DataModelObject> comp = null;
        
        switch(order)
        {
        case NATURAL:
            comp = AbstractDataModelObject.getNaturalComparator();
            break;
            
        case ALPHABETICAL:
            comp = AbstractDataModelObject.getAlphabeticalComparator(descending);
            break;
            
        case CHRONOLOGICAL:
            comp = AbstractDataModelObject.getChronologicalComparator(descending);
            break;
            
        case BY_ID: /* DMOs are ordered by ID by default. */
        default:
            break;
        }
        
        return comp;
    }
    
//    public<E extends Enum<E>> ArrayList<String> outputEnum(Class<E> enumClass)
//    {
//        return MuzakModelUtils.getEnumValues(enumClass);
//    }
    public void outputArtists()
    {
        m_artists.output();
    }
    public void outputReleases()
    {
        m_releases.output();
    }
    public void outputTracks()
    {
        m_tracks.output();
    }
    public void outputMusicians()
    {
        m_musicians.output();
    }

    public void outputBTAssociations()
    {
        m_artaLink.outputAssociations();
    }
    public void outputATAssociations()
    {
        m_retaLink.outputAssociations();
    }
    public void outputAMAssociations()
    {
        m_remuLink.outputAssociations();
    }
    
    private static long generateUniqueID()
    {
        long start = System.currentTimeMillis();
        
        /* Wait for one millisecond. */
        while((System.currentTimeMillis() - start) < 1L);
        
        return System.currentTimeMillis();
    }
}
