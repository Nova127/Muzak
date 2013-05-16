package muzak;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class KeyValueElement
{
    private StringProperty key;
    private StringProperty value;
    
    public KeyValueElement()
    {
        this.key    = new SimpleStringProperty();
        this.value  = new SimpleStringProperty();
    }
    
    public KeyValueElement(String key, String value)
    {
        this.key    = new SimpleStringProperty(key);
        this.value  = new SimpleStringProperty(value);
    }
    
    public StringProperty getKeyProperty()
    {
        return this.key;
    }
    public StringProperty getValueProperty()
    {
        return this.value;
    }
    
    public String getKey()
    {
        return this.key.get();
    }
    public String getValue()
    {
        return this.value.get();
    }
    
    public void setKey(String key)
    {
        this.key.set(key);
    }
    public void setValue(String value)
    {
        this.value.set(value);
    }
    
    @Override
    public String toString()
    {
        return this.value.get();
    }
}
