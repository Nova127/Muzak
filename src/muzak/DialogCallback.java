package muzak;

import javafx.stage.Stage;

public interface DialogCallback
{
    Stage getOwningStage();
    void update();
    void showDiscogsResultsDialog(final Configurations config);
    
    String getQueryTitle();
    String getQueryCatNumber();
    String getQueryBarcode();
}
