
package muzak;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class SimpleInfoDisplayElement extends AbstractInfoDisplayElement
{
    public Label mainLabel;
    public Label subLabel;
    
    public SimpleInfoDisplayElement()
    {
        super();
        
        setupUI();
    }
    
    private void setupUI()
    {
        mainLabel = new Label("MAIN LABEL");
        subLabel = new Label("SUB LABEL");
        
        VBox layout = new VBox();
        layout.getChildren().addAll(mainLabel, subLabel);
        
        this.setCenter(layout);
    }
}
