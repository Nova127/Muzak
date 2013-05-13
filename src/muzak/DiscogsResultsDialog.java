
package muzak;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import muzak.Configurations.Resources;
import muzak.mycomp.MultiSelectionListView;

public class DiscogsResultsDialog extends AbstractPhasedDialog
{
    private Label                   ui_waiting          = new Label();
    private MultiSelectionListView  ui_resultsChoice    = new MultiSelectionListView(false);
    
    public DiscogsResultsDialog(final Configurations config, final DialogObserver observer)
    {
        super(config, observer);
        
        ResourceBundle res = config.getResources(Resources.DISCOGS_RESULTS_DIALOG);
        
        super.addPhase(createInitialView(res));
        //super.addPhase(UIUtils.vLayout(0, ui_resultsChoice));
        
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        setWidth(0.5 * screen.getWidth());
        setHeight(0.5 * screen.getHeight());
        
        setTitle(res.getString("DIALOG_TITLE"));
        
        super.prepare();
    }
    
    @Override
    public void update()
    {
        ui_waiting.setVisible(false);
        
        ArrayList<KeyValueCombo> res = m_observer.getDiscogsResults();
        
        if(res != null)
            /* Don't know why, but control seems to exit this method after calling below function. */
            ui_resultsChoice.insertSelectionElements(res);
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
    
    @Override
    protected void proceed()
    {
    }
    
    @Override
    protected void rollBack()
    {
    }
    
    private Pane createInitialView(ResourceBundle res)
    {
        ui_waiting.setText(res.getString("WAITING"));

        VBox box = UIUtils.vLayout(10.0, UIUtils.hLayoutCentered(ui_waiting), ui_resultsChoice);
        //ui_phase = UIUtils.vLayoutCenterCenter(ui_waiting);
        box.getStyleClass().addAll("glass-pane", "dialog-phase");
        
        return box;
    }
}
