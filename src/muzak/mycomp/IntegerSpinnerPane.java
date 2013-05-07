
package muzak;

import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class IntegerSpinnerPane extends GridPane
{
    /* The next three properties will be seen in FXML as attributes with
     * exactly the same name. There must be getters and setters implemented
     * for them or loading of the xml-file will fail at run time. */
    protected int minValue;
    protected int defValue;
    protected int maxValue;
    
    protected Button incButton;
    protected Button decButton;
    protected TextField valueField;
    
    public IntegerSpinnerPane()
    {
        super();
        minValue = Integer.MIN_VALUE;
        defValue = 0;
        maxValue = Integer.MAX_VALUE;
        
        setupUi();
        init();
    }
    
    public int getMinValue()
    {
        return minValue;
    }
    
    public int getDefValue()
    {
        return defValue;
    }
    
    public int getMaxValue()
    {
        return maxValue;
    }
    
    public int getCurrentValue()
    {
        int value = 0;
        
        try {
            value = Integer.parseInt(valueField.getText());
        }
        catch(NumberFormatException nfe){;}
        
        return value;
    }
    
    public void setMinValue(int min)
    {
        minValue = min;
    }
    
    public void setDefValue(int def)
    {
        setValue(def);
    }
    
    public void setMaxValue(int max)
    {
        maxValue = max;
    }
    
    public void setRange(int min, int max)
    {
        if(min > max)
        {
            int tmp = min;
            min = max;
            max = tmp;
        }
        
        minValue = min;
        maxValue = max;
    }
    
    public void setValue(int value)
    {
        valueField.setText(Integer.toString(value));
    }
    
    public void setValues(int min, int init, int max)
    {
        setRange(min, max);
        setValue(init);
    }
    
    public void setPrefFieldWidth(double w)
    {
        valueField.setPrefWidth(w);
    }
    
    protected void incValue()
    {
        int current = getCurrentValue();
        
        setValue(++current);
    }
    
    protected void decValue()
    {
        int current = getCurrentValue();
        
        setValue(--current);
    }
    
    protected void chechRange()
    {
        int value = getCurrentValue();
        
        if(value < minValue || value > maxValue)
        {
            valueField.setStyle("-fx-background-color: rgba(255,0,0,0.5);" +
                                "-fx-border-color:     rgb(211,211,211);");
        }
        else
            valueField.setStyle("-fx-background-color: white;" +
                                "-fx-border-color:     rgb(211,211,211);");
    }
    
    private void init()
    {
        valueField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                if(!"-0123456789".contains(event.getCharacter()))
                    event.consume();
            }
        });
        
        valueField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue)
            {
                System.out.println("CHANGE: \"" + oldValue + "\" to \"" + newValue + "\"");
                if(!newValue.matches("^-?\\d*$"))
                {
                    valueField.setText(oldValue);
                }
                
                chechRange();
            }
        });
        
        incButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t)
            {
                incValue();
            }
        });
        
        decButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t)
            {
                decValue();
            }
        });
    }
    
    private void setupUi()
    {
        incButton = new Button();
        incButton.setGraphic(new ImageView(new Image("file:icons/addIcon.png")));
        incButton.setStyle("-fx-background-radius: 0 5 5 0");
        incButton.setMinSize(25, 20);
        incButton.setMaxSize(25, 20);
        incButton.setFocusTraversable(false);
        
        decButton = new Button();
        decButton.setGraphic(new ImageView(new Image("file:icons/subIcon.png")));
        decButton.setStyle("-fx-background-radius: 5 0 0 5");
        decButton.setMinSize(25, 20);
        decButton.setMaxSize(25, 20);
        decButton.setFocusTraversable(false);
        
        valueField = new TextField();
        valueField.setStyle("-fx-background-radius: 0");
        valueField.setMinHeight(20);
        valueField.setMaxHeight(20);
        valueField.setMinWidth(50);
        valueField.setAlignment(Pos.CENTER);
        
        add(decButton,   0,  0);
        add(valueField,  1,  0);
        add(incButton,   2,  0);
    }
}
