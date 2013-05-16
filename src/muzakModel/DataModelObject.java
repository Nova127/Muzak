
package muzakModel;

public interface DataModelObject extends Comparable<DataModelObject>
{
    boolean isValid();
    
    boolean equalSignatures(DataModelObject dmo);
    
    boolean containsTitle(String title);
    boolean equalsTitle(String title);
    
    int alphaCompareTo(DataModelObject dmo);
    int chronoCompareTo(DataModelObject dmo);
    int naturalCompareTo(DataModelObject dmo);
    
    long getID();
    String getIDString();
    
    String getDiscogsResourceUri();
    void setDiscogsResourceUri(String uri);
    
    String getShortInfoString();
    String getListString();
    String toString();
}
