
package muzak;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import muzak.mycomp.MultiSelectionListView;

public class DiscogsResultsDialog extends AbstractPhasedDialog
{
    private MultiSelectionListView ui_resultsChoice = new MultiSelectionListView(false);
    
    public DiscogsResultsDialog(final Configurations config, final DialogObserver observer)
    {
        super(config, observer);
        super.addPhase(createResultsForm());
        super.prepare();
    }
    
    @Override
    public void update()
    {
        System.out.println("DRD / update");
        ArrayList<String> res = m_observer.getDiscogsResults();
        if(res!=null)
            ui_resultsChoice.insertSelectionElements(res);
        
    }

    @Override
    protected void proceed()
    {
        
    }

    @Override
    protected void rollBack()
    {
        
    }

    @Override
    public void showDiscogsResultsDialog(final Configurations config)
    {
        
    }
    
    private Pane createResultsForm()
    {
        VBox box = new VBox();
        box.getStyleClass().addAll("glass-pane", "dialog-phase");
        box.getChildren().addAll(ui_resultsChoice);
        
        return box;
    }

    @Override
    public String getQueryTitle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getQueryCatNumber() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getQueryBarcode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
