
package muzak.mycomp;

import muzakModel.DataModelObject;


public interface ViewModDelObserver
{
    public void handleViewRequest(DataModelObject dmo);
    public void handleModifyRequest(DataModelObject dmo);
    public void handleDeleteRequest(DataModelObject dmo);
}
