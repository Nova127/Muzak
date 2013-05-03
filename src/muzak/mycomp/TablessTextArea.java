
package muzak.mycomp;

import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.SkinBase;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.*;


public class TablessTextArea extends TextArea
{
    public TablessTextArea()
    {
        super();
        
        initTabBehaviour();
    }
    
    private void initTabBehaviour()
    {
        /* Following code taken from Stack Overflow - author: amru
         * http://stackoverflow.com/questions/12860478/tab-key-navigation-in-javafx-textarea */
        addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                if(event.getCode() == KeyCode.TAB)
                {
                    SkinBase skin = (SkinBase)getSkin();
                    if(skin.getBehavior() instanceof TextAreaBehavior)
                    {
                        TextAreaBehavior behavior = (TextAreaBehavior)skin.getBehavior();

                        if(event.isControlDown())
                            behavior.callAction("InsertTab");
                        else
                            behavior.callAction("TraverseNext");

                        event.consume();
                    }
                }
            }
        });
    }
}
