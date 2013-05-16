
package muzak.mycomp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import muzak.Configurations;
import muzak.Configurations.Resources;
import muzakModel.TrackInfoElement;

public class TracklistTableView extends TableView<TrackInfoElement>
{
    private ObservableList<TrackInfoElement> m_tracklist = FXCollections.observableArrayList();
    
    public TracklistTableView(final Configurations config)
    {
        super();
        
        setupColumns(config.getResources(Resources.TRACKS_DIALOG), config.getResources(Resources.LIST_OF_RATINGS));

        setItems(m_tracklist);
    }
    
    public void addTableData(TrackInfoElement datum)
    {
        m_tracklist.add(datum);
    }
    
    public ArrayList<TrackInfoElement> getTableData()
    {
        ArrayList<TrackInfoElement> data = new ArrayList<>();
        
        for(TrackInfoElement tie : m_tracklist)
            data.add(tie);
        
        return data;
    }
    
    private void setupColumns(ResourceBundle res, ResourceBundle rvals)
    {
        /* Setup Track Ordinal column. */
        TableColumn<TrackInfoElement, String> ocol = new TableColumn<>("#");
        ocol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TrackInfoElement,String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<TrackInfoElement, String> arg0)
            {
                return arg0.getValue().getOrdinalProperty();
            }
        });
        ocol.setCellFactory(new Callback<TableColumn<TrackInfoElement,String>, TableCell<TrackInfoElement,String>>()
        {
            @Override
            public TableCell<TrackInfoElement, String> call(TableColumn<TrackInfoElement, String> arg0)
            {
                return new TextFieldTableCell<>(new StringConverter<String>()
                {
                    @Override
                    public String fromString(String arg0)
                    { 
                        return arg0;
                    }

                    @Override
                    public String toString(String arg0)
                    {
                        return arg0;
                    }
                });
            }
        });
        
        /* Setup Track Title column. */
        TableColumn<TrackInfoElement, String> tcol = new TableColumn<>(res.getString("TITLE"));
        tcol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TrackInfoElement,String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<TrackInfoElement, String> arg0)
            {
                return arg0.getValue().getTitleProperty();
            }
        });
        tcol.setCellFactory(new Callback<TableColumn<TrackInfoElement,String>, TableCell<TrackInfoElement,String>>()
        {
            @Override
            public TableCell<TrackInfoElement, String> call(TableColumn<TrackInfoElement, String> arg0)
            {
                return new TextFieldTableCell<>(new StringConverter<String>()
                {
                    @Override
                    public String fromString(String arg0)
                    { 
                        return arg0;
                    }

                    @Override
                    public String toString(String arg0)
                    {
                        return arg0;
                    }
                });
            }
        });
        
        /* Setup Track Length column. */
        TableColumn<TrackInfoElement, String> lcol = new TableColumn<>(res.getString("LENGTH"));
        lcol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TrackInfoElement,String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<TrackInfoElement, String> arg0)
            {
                return arg0.getValue().getLengthProperty();
            }
        });
        lcol.setCellFactory(new Callback<TableColumn<TrackInfoElement,String>, TableCell<TrackInfoElement,String>>()
        {
            @Override
            public TableCell<TrackInfoElement, String> call(TableColumn<TrackInfoElement, String> arg0)
            {
                return new TextFieldTableCell<>(new StringConverter<String>()
                {
                    @Override
                    public String fromString(String arg0)
                    { 
                        return arg0;
                    }

                    @Override
                    public String toString(String arg0)
                    {
                        return arg0;
                    }
                });
            }
        });
        
        /* Setup Check as Cover option column. */
        TableColumn<TrackInfoElement, Boolean> ccol = new TableColumn<>(res.getString("COVER"));
        ccol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TrackInfoElement, Boolean>, ObservableValue<Boolean>>()
        {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TrackInfoElement, Boolean> arg0)
            {
                return arg0.getValue().getCoverProperty();
            }
        });
        ccol.setCellFactory(new Callback<TableColumn<TrackInfoElement, Boolean>, TableCell<TrackInfoElement, Boolean>>()
        {
            @Override
            public TableCell<TrackInfoElement, Boolean> call(TableColumn<TrackInfoElement, Boolean> arg0)
            {
                return new CheckBoxTableCell<>();
            }
        });
        
        /* Setup Track Rating column. */
        final ArrayList<String> ratings = new ArrayList<>();
        for(String key : rvals.keySet())
            ratings.add(rvals.getString(key));
        Collections.sort(ratings);
        
        TableColumn<TrackInfoElement, String> rcol = new TableColumn<>(res.getString("RATING"));
        rcol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TrackInfoElement,String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<TrackInfoElement, String> arg0)
            {
                return arg0.getValue().getRatingProperty();
            }
            
        });
        rcol.setCellFactory(new Callback<TableColumn<TrackInfoElement,String>, TableCell<TrackInfoElement,String>>()
        {
            @Override
            public TableCell<TrackInfoElement, String> call(TableColumn<TrackInfoElement, String> arg0)
            {
                return new ComboBoxTableCell<>(FXCollections.observableArrayList(ratings));
            }
        });
        
        
        
        ocol.setMinWidth(50.0);
        tcol.setMinWidth(150.0);
        tcol.setPrefWidth(200.0);
        lcol.setMinWidth(80.0);
        ccol.setMinWidth(50.0);
        rcol.setMinWidth(100.0);
        
        getColumns().add(ocol);
        getColumns().add(tcol);
        getColumns().add(lcol);
        getColumns().add(ccol);
        getColumns().add(rcol);
        
        setEditable(true);
    }
}
