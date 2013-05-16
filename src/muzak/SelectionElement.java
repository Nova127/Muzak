package muzak;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SelectionElement implements Comparable<SelectionElement>
{
    private final SimpleStringProperty    techKey;
    private final SimpleStringProperty    displayKey;
    private final SimpleBooleanProperty   selectionValue;
    
    public SelectionElement(String techKey, String displayKey)
    {
        this.techKey        = new SimpleStringProperty(techKey);
        this.displayKey     = new SimpleStringProperty(displayKey);
        this.selectionValue = new SimpleBooleanProperty(false);
    }
    
    public SelectionElement(KeyValueCombo kvcombo)
    {
        this.techKey        = new SimpleStringProperty(kvcombo.getKey());
        this.displayKey     = new SimpleStringProperty(kvcombo.getValue());
        this.selectionValue = new SimpleBooleanProperty(false);
    }
    
    public SimpleStringProperty getTechKeyProperty()
    {
        return this.techKey;
    }
    public SimpleStringProperty getDisplayKeyProperty()
    {
        return this.displayKey;
    }
    public SimpleBooleanProperty getSelectionValueProperty()
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
    
    public void setTechKey(String tech)
    {
        this.techKey.set(tech);
    }
    public void setDisplayKey(String display)
    {
        this.displayKey.set(display);
    }
    public void setSelectionValue(boolean selected)
    {
        this.selectionValue.set(selected);
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