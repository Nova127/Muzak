
package muzak;

import javafx.scene.layout.*;
import javafx.scene.control.*;

public abstract class AbstractInfoDisplayElement extends BorderPane
{
    protected Button viewButton;
    protected Button modifyButton;
    protected Button deleteButton;
    
    public AbstractInfoDisplayElement()
    {
        super();
        
        setupUI();
    }
    
    protected final void setContentPane(Pane contents)
    {
        BorderPane p = (BorderPane)this.getScene().getRoot();
        p.setCenter(contents);
    }
    
    private void setupUI()
    {
        viewButton = new Button("View");
        modifyButton = new Button("Modify");
        deleteButton = new Button("Delete");
        
        Region stretcher = new Region();
        HBox.setHgrow(stretcher, Priority.ALWAYS);
        
        HBox buttonsLayout = new HBox(5.0);
        buttonsLayout.getChildren().addAll(stretcher, viewButton, modifyButton, deleteButton);
        
        this.setBottom(buttonsLayout);
        
        this.setStyle("-fx-border-style: solid;" +
                      "-fx-border-color: black;" + 
                      "-fx-background-color: transparent;");
    }
}
