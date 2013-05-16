package muzak;

import java.util.ArrayList;

import javafx.stage.Stage;

public interface DialogCallback
{
    Stage getOwningStage();
    AbstractPhasedDialog getReference();
    void injectValues(RecordInfoElement record);
    void update();
    void update(ArrayList<KeyValueElement> data);
    //void showDiscogsResultsDialog(final Configurations config);
    
    String getQueryTitle();
    String getQueryCatNumber();
    String getQueryBarcode();
}
