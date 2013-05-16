
package muzak.mycomp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.TreeSet;

import muzak.KeyValueCombo;
import muzak.SelectionElement;
import muzak.UIUtils;
import muzakModel.DataModelObject;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;



class RadioCell extends ListCell<SelectionElement>
{
    RadioButton ui_radio = new RadioButton();
    Label       ui_label = new Label();
    
    public RadioCell(ToggleGroup tgroup)
    {
        super();
        ui_radio.setToggleGroup(tgroup);
    }
    
    @Override
    public void updateItem(SelectionElement item, boolean empty)
    {
        super.updateItem(item, empty);
        if(empty)
        {
            setGraphic(null);
        }
        else
        {
            ui_label.setText(item != null ? item.getDisplayKey() : "");
            ui_radio.setUserData(item);
            HBox box = UIUtils.hLayout(8.0, ui_radio, ui_label);
            box.setAlignment(Pos.CENTER_LEFT);
            setGraphic(box);
        }
    }
}

public class MultiSelectionListView extends ListView<SelectionElement>// implements SelectionListView
{
    private boolean     m_multiSelectionMode = true;
    private ToggleGroup m_toggles            = new ToggleGroup();
    private ObservableList<SelectionElement> m_data = FXCollections.observableArrayList();
    
    public MultiSelectionListView(boolean multiSelectionMode)
    {
        m_multiSelectionMode = multiSelectionMode;
        this.setItems(m_data);
        setupCellFactory();
    }
    
    public void switchToMultiMode()
    {
        m_multiSelectionMode = true;
        this.setCellFactory(null);
        
        setupCellFactory();
    }
    
    public void switchToSingleMode()
    {
        m_multiSelectionMode = false;
        this.setCellFactory(null);
        
        setupCellFactory();
    }
    
    public boolean isMultiModeOn()
    {
        return m_multiSelectionMode;
    }
    
    public boolean isSingleModeOn()
    {
        return !m_multiSelectionMode;
    }
    
    public void insertSelectionElements(ResourceBundle res)
    {
        ArrayList<SelectionElement> items = new ArrayList<>();
        for(String key : res.keySet())
            m_data.add(new SelectionElement(key, res.getString(key)));//items.add(new SelectionElement(key, res.getString(key)));
        
        //Collections.sort(items);
        
        //this.setItems(FXCollections.observableArrayList(items));
    }
    
    public void insertSelectionElements(TreeSet<DataModelObject> combos)
    {
        ArrayList<SelectionElement> items = new ArrayList<>();
        for(DataModelObject dmo : combos)
            items.add(new SelectionElement(dmo.getIDString(), dmo.getShortInfoString()));
        
        this.setItems(FXCollections.observableArrayList(items));
    }
    
    public void insertSelectionElements(ArrayList<KeyValueCombo> combos)
    {
        m_data.clear();
        ArrayList<SelectionElement> items = new ArrayList<>();
        for(KeyValueCombo kvc : combos)
            //m_data.add(new SelectionElement(kvc));
            items.add(new SelectionElement(kvc.getKey(), kvc.getValue()));
        
        //m_data = FXCollections.observableArrayList(items);
        this.setItems(FXCollections.observableArrayList(items));
        System.out.println("MSLV/insert");
    }
    
    public ArrayList<KeyValueCombo> getSelected()
    {
        ArrayList<KeyValueCombo> selections = new ArrayList<>();
        
        if(m_multiSelectionMode)
        {
            for(SelectionElement e : this.getItems())
            {
                if(e.getSelectionValue())
                    selections.add(new KeyValueCombo(e.getTechKey(), e.getDisplayKey()));
            }
        }
        else
        {
            RadioButton opt = (RadioButton)m_toggles.getSelectedToggle();
            if(opt != null)
            {
                SelectionElement se = (SelectionElement)opt.getUserData();
                if(se != null)
                {
                    selections.add(new KeyValueCombo(se.getTechKey(), se.getDisplayKey()));
                }
            }
        }
        
        return selections;
    }
    
    public ArrayList<String> getSelectedKeys()
    {
        ArrayList<String> keys = new ArrayList<>();
        
        if(m_multiSelectionMode)
        {
            for(SelectionElement e : this.getItems())
            {
                if(e.getSelectionValue())
                    keys.add(e.getTechKey());
            }
        }
        else
        {
            RadioButton opt = (RadioButton)m_toggles.getSelectedToggle();
            if(opt != null)
            {
                SelectionElement se = (SelectionElement)opt.getUserData();
                if(se != null)
                {
                    keys.add(se.getTechKey());
                }
            }
        }
        
        return keys;
    }
    
    private void setupCellFactory()
    {
        if(m_multiSelectionMode)
        {
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
        else
        {
            this.setCellFactory(new Callback<ListView<SelectionElement>, ListCell<SelectionElement>>()
            {
                @Override
                public RadioCell call(ListView<SelectionElement> arg0)
                {
                    return new RadioCell(m_toggles);
                }
            });
        }
    }
}





















