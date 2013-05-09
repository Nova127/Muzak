
package muzak;

import muzak.mycomp.ViewModDelTools;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class SimpleInfoDisplayElement extends BorderPane
{
    public Label mainLabel;
    public Label subLabel;
    
    public SimpleInfoDisplayElement()
    {
        super();
        
        createDisplay();
    }
    
    private void createDisplay()
    {
        mainLabel = new Label("MAIN LABEL");
        subLabel = new Label("SUB LABEL");
        
        HBox buttons = new ViewModDelTools();
        
        VBox layout = new VBox();
        layout.getChildren().addAll(mainLabel, subLabel, UIUtils.hLayout(0, UIUtils.getHStretcher(), buttons));
        
        this.setCenter(layout);
    }
}
