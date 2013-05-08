
package muzak.mycomp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import muzak.KeyValueCombo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;

class SelectionElement implements Comparable<SelectionElement>
{
    private final StringProperty    techKey;
    private final StringProperty    displayKey;
    private final BooleanProperty   selectionValue;
    
    public SelectionElement(String techKey, String displayKey)
    {
        this.techKey        = new SimpleStringProperty(techKey);
        this.displayKey     = new SimpleStringProperty(displayKey);
        this.selectionValue = new SimpleBooleanProperty(false);
    }
    
    public BooleanProperty getSelectionValueProperty()
    {
        return this.selectionValue;
    }
    
    public String getTechKey()
    {
        return this.techKey.get();
    }
    public String getDisplayKey()
    {
        return this.displayKey.get();
    }
    public Boolean getSelectionValue()
    {
        return this.selectionValue.get();
    }
    
    @Override
    public String toString()
    {
        return displayKey.get();
    }

    @Override
    public int compareTo(SelectionElement other)
    {
        return techKey.get().compareTo(other.techKey.get());
    }
}

public class MultiSelectionListView extends ListView<SelectionElement>
{
    //ObservableList<SelectionElement> m_elems = null;
    
    public MultiSelectionListView()
    {
/*        m_elems = FXCollections.observableArrayList(elems);
        
        this.setItems(m_elems);*/
        
        Callback<SelectionElement, ObservableValue<Boolean>> cb = new Callback<SelectionElement, ObservableValue<Boolean>>()
        {
            @Override
            public ObservableValue<Boolean> call(SelectionElement elem)
            {
                return elem.getSelectionValueProperty();
            }
        };
        
        this.setCellFactory(CheckBoxListCell.forListView(cb));
    }
    
    public void insertSelectionElements(ResourceBundle res)
    {
        ArrayList<SelectionElement> items = new ArrayList<>();
        for(String key : res.keySet())
            items.add(new SelectionElement(key, res.getString(key)));
        
        Collections.sort(items);
        
        this.setItems(FXCollections.observableArrayList(items));
    }
    
    public ArrayList<KeyValueCombo> getSelected()
    {
        ArrayList<KeyValueCombo> selections = new ArrayList<>();
        
        for(SelectionElement e : this.getItems())
        {
            if(e.getSelectionValue())
                selections.add(new KeyValueCombo(e.getTechKey(), e.getDisplayKey()));
        }
        
        return selections;
    }
}





















