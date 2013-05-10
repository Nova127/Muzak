
package muzak.mycomp;


public interface ViewModDelObserver
{
    public void handleViewRequest(String idString, Object typeObject);
    public void handleModifyRequest(String idString, Object typeObject);
    public void handleDeleteRequest(String idString, Object typeObject);
}
