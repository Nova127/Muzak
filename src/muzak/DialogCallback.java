package muzak;

import javafx.stage.Stage;

public interface DialogCallback
{
    Stage getOwningStage();
    void update();
}
