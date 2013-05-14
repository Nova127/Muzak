package muzak;

import muzakModel.DataModelObject;
import javafx.stage.Stage;

public interface DialogCallback
{
    Stage getOwningStage();
    AbstractPhasedDialog getReference();
    void injectValues(DataModelObject dmo);
    void update();
    //void showDiscogsResultsDialog(final Configurations config);
    
    String getQueryTitle();
    String getQueryCatNumber();
    String getQueryBarcode();
}
