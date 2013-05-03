package muzak;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class AbstractPhasedDialog extends Stage
{
    private ArrayList<Pane> m_phases;
    private int             m_phaseIndex;
    private boolean         m_accepted;
    
    /* Base Dialog controls - visibility has to be protected! */
    protected Button  backButton;
    protected Button  nextButton;
    protected Button  okButton;
    protected Button  cancelButton;
    
    public AbstractPhasedDialog(final Locale locale)
    {
        m_phases        = new ArrayList<>();
        m_phaseIndex    = 0;
        m_accepted      = false;
        
        ResourceBundle res = ResourceBundle.getBundle("bundles.AbstractPhasedDialog", locale);
        
        setScene(new Scene(createBaseLayout(res)));
        
        createEventHandlers();
    }
    
    /* This should be used instead of direct call of showAndWait()
     * from the executing thread. Returns true if dialog was accepted
     * and false otherwise. */
    public boolean execute()
    {
        super.showAndWait();
        
        return m_accepted;
    }
    
    /* This abstract method is called whenever user proceeds to next phase
     * of the dialog by pressing "next" button. */
    protected abstract void proceed();
    
    /* This abstract method is called whenever user rolls back to previous phase
     * of the dialog by pressing "back" button. */
    protected abstract void rollBack();
    
    /* Instantiates phases defined by resources and shows current phase. */
//    protected void loadPhases(ArrayList<String> resources, Locale locale, ArrayList<BuilderFactory> builders)
//    {
//        for(int i = 0; i < resources.size(); ++i)
//            m_phases.add(createPhase(resources.get(i), locale, builders.get(i)));
//        
//        setContentPane(m_phases.get(m_phaseIndex));
//        permitNavigation();
//    }
    
    /* Overloaded convenience method. */
//    protected void loadPhases(ArrayList<String> resources, Locale locale)
//    {
//        /* Create a list of null builders. */
//        ArrayList<BuilderFactory> dummies = new ArrayList<>(resources.size());
//        for(int i = 0; i < resources.size(); ++i)
//            dummies.add(null);
//        
//        loadPhases(resources, locale, dummies);
//    }
    
    protected void addPhase(Pane p)
    {
        m_phases.add(p);
        setContentPane(m_phases.get(m_phaseIndex));
        permitNavigation();
    }
    
//    private Pane createPhase(String resource, Locale locale, BuilderFactory builder)
//    {
//        Pane pane = null;
//        
//        FXMLLoader loader = new FXMLLoader();
//        loader.setResources(ResourceBundle.getBundle("bundles.Bundle", locale));
//        loader.setLocation(getClass().getResource(resource));
//        if(builder != null)
//            loader.setBuilderFactory(builder);
//        loader.setController(this);
//        
//        try {
//            pane = (Pane)loader.load();
//        }
//        catch(IOException e) {
//            throw new RuntimeException(e);
//        }
//        
//        return pane;
//    }
    
    private void setContentPane(Pane contents)
    {
        BorderPane p = (BorderPane)this.getScene().getRoot();
        p.setCenter(contents);
    }
    
    private void navigate(ActionEvent ae)
    {
        String id = ((Node)ae.getSource()).getId();
        System.out.println("Dialog: " + id);
        
        switch(id)
        {
            case "backButton":
                if(decPhase())
                {
                    permitNavigation();
                    rollBack();
                    setContentPane(m_phases.get(m_phaseIndex));
                }
                break;
                
            case "nextButton":
                if(incPhase())
                {
                    permitNavigation();
                    proceed();
                    setContentPane(m_phases.get(m_phaseIndex));
                }
                break;
                
            default:
                /* Shouldn't really happen... :P */
                break;
        }
    }
    
    private void permitNavigation()
    {
        /* Only one phase, so nowhere to navigate to. */
        if(m_phases.size() == 1)
        {
            backButton.setVisible(false);
            nextButton.setVisible(false);
        }
        else
        {
            /* Current phase is the first phase. */
            if(m_phaseIndex == 0)
            {
                backButton.setDisable(true);
                nextButton.setDisable(false);
            }
            /* Current phase is the last phase. */
            else if(m_phaseIndex == m_phases.size()-1)
            {
                backButton.setDisable(false);
                nextButton.setDisable(true);
            }
            /* Current phase is an intermediate phase. */
            else
            {
                backButton.setDisable(false);
                nextButton.setDisable(false);
            }
        }
    }
    
    private boolean incPhase()
    {
        if(m_phaseIndex < m_phases.size()-1)
        {
            ++m_phaseIndex;
            return true;
        }
        
        return false;
    }
    
    private boolean decPhase()
    {
        if(m_phaseIndex > 0)
        {
            --m_phaseIndex;
            return true;
        }
        
        return false;
    }
    
    private void createEventHandlers()
    {
        okButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent t)
            {
                System.out.println("Dialog accepted");
                m_accepted = true;
                close();
            }
        });
        
        /* Reject dialog on pressing Cancel or hitting Esc. */
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent t)
            {
                System.out.println("Dialog rejected");
                m_accepted = false;
                close();
            }
        });
        
        EventHandler<ActionEvent> nav = new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent t)
            {
                navigate(t);
            }
        };
        
        backButton.setOnAction(nav);
        nextButton.setOnAction(nav);
        
        /* If user closes the dialog via X on upper right corner. */
        setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override public void handle(WindowEvent t)
            {
                System.out.println("Dialog rejected via X");
                m_accepted = false;
                close();
            }
        });
    }
    private BorderPane createBaseLayout(ResourceBundle res)
    {
        HBox buttonsLayout = new HBox(5.0);
        buttonsLayout.setPadding(new Insets(5.0));
        
        backButton = new Button(res.getString("BACK"));
        nextButton = new Button(res.getString("NEXT"));
        okButton = new Button(res.getString("OK"));
        cancelButton = new Button(res.getString("CANCEL"));
        cancelButton.setCancelButton(true);
        
        Region stretcher = new Region();
        HBox.setHgrow(stretcher, Priority.ALWAYS);
        
        buttonsLayout.getChildren().addAll(backButton, nextButton, stretcher, okButton, cancelButton);
        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(5.0));
        root.setBottom(buttonsLayout);
        
        return root;
    }
}
