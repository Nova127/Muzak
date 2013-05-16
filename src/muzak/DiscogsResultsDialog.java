
package muzak;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Callback;
import javafx.util.StringConverter;
import muzak.Configurations.Resources;
import muzak.mycomp.MultiSelectionListView;

public class DiscogsResultsDialog extends AbstractPhasedDialog
{
    private Label                   ui_waiting          = new Label();
    //private MultiSelectionListView  ui_resultsChoice    = new MultiSelectionListView(false);
    private ListView<KeyValueElement>  ui_resultsChoice    = new ListView<>();
    private ObservableList<KeyValueElement> m_results = FXCollections.observableArrayList();
    
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
        ui_resultsChoice.setItems(m_results);
        super.prepare();
    }
    
    public String getUserSelection()
    {
        String choice = "";
        
        /* Should be only 0 or 1 (expected) selection. */
//        ArrayList<String> selection = ui_resultsChoice.getSelectedKeys();
//        if(!selection.isEmpty())
//            choice = selection.get(0);
        KeyValueElement selected = ui_resultsChoice.getSelectionModel().getSelectedItem();
        if(selected != null)
            choice = selected.getKey();
        
        
        return choice;
    }
    
    @Override
    public void update()
    {
        m_results.clear();
        
        if(m_observer != null)
            m_results.addAll(m_observer.getDiscogsResults());
            
        System.out.println("DISCOGS UPDATE: " + m_results.size());
    }
    
    @Override
    public void update(ArrayList<KeyValueElement> data)
    {
        System.out.println("UPDATE WITH DATA");
        
        
        //ArrayList<KeyValueCombo> res = m_observer.getDiscogsResults();
        
        if(data != null)
        {
            /* Don't know why, but control seems to exit this method after calling below function. */
            //ui_resultsChoice.insertSelectionElements(res);
            //m_results = FXCollections.observableArrayList();
//            int i = 0;
//            for(KeyValueCombo kvc : data)
//            {
//                m_results.add(new KeyValueElement(kvc.getKey(), kvc.getValue()));
//                System.out.println("Add, index = " + ++i);
//            }
//            m_results.clear();
//            m_results.setAll(data);
            
            //ui_resultsChoice.setItems(m_results);
        }
        
        ui_waiting.setVisible(false);
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

        ui_resultsChoice.setCellFactory(new Callback<ListView<KeyValueElement>, ListCell<KeyValueElement>>() {
            
            @Override
            public ListCell<KeyValueElement> call(ListView<KeyValueElement> arg0) {
                // TODO Auto-generated method stub
                return new ListCell<KeyValueElement>() {
                    
                    @Override
                    public void updateItem(KeyValueElement item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if(empty)
                        {
                            setGraphic(null);
                        }
                        else
                        {
                            setGraphic(new Label(item.getValue()));
                        }
                    }
                };
            }
        });
        
        VBox box = UIUtils.vLayout(10.0, UIUtils.hLayoutCentered(ui_waiting), ui_resultsChoice);
        box.getStyleClass().addAll("glass-pane", "dialog-phase");
        
        return box;
    }
}
