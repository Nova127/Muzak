package muzak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import muzak.Configurations.Resources;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class AbstractPhasedDialog extends Stage
{
    private ArrayList<Pane> m_phases;
    private int             m_phaseIndex;
    private boolean         m_accepted;
    
    /* Base Dialog controls. */
    protected Button    ui_backButton;
    protected Button    ui_nextButton;
    protected Button    ui_okButton;
    protected Button    ui_cancelButton;
    
    public AbstractPhasedDialog(final Configurations config)
    {
        m_phases         = new ArrayList<>();
        m_phaseIndex     = 0;
        m_accepted       = false;
        
        ResourceBundle res = config.getResources(Resources.ABSTRACT_PHASED_DIALOG);
        
        Scene scene = new Scene(createBaseLayout(res));
        scene.getStylesheets().add(getClass().getResource("styles/main.css").toExternalForm());
        setScene(scene);
        
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
    
    protected abstract Pane createDiscogsResultPane();
    
    protected boolean isLastPhase()
    {
        return m_phaseIndex == m_phases.size()-1;
    }
    
    protected int getCurrentPhase()
    {
        return m_phaseIndex + 1;
    }
    
    protected Button getDiscogsButton()
    {
        Button b = new Button("Discogs");
        b.setId("DiscogsButton");
        b.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent ae)
            {
                navigate(ae);
            }
            
        });
        
        return b;
    }
    
    protected void addPhase(Pane phase)
    {
        m_phases.add(phase);
    }
    
    /* Set initial phase and permit dialog navigation. */
    protected void prepare()
    {
        setContentPane(m_phases.get(m_phaseIndex));
        permitNavigation();
    }
    
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
            case "BackButton":
                if(decPhase())
                {
                    rollBack();
                    permitNavigation();
                    setContentPane(m_phases.get(m_phaseIndex));
                }
                break;
                
            case "NextButton":
                if(incPhase())
                {
                    proceed();
                    permitNavigation();
                    setContentPane(m_phases.get(m_phaseIndex));
                }
                break;
                
            case "DiscogsButton":
                System.out.println("Discogs request!");
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
            ui_backButton.setVisible(false);
            ui_nextButton.setVisible(false);
        }
        else
        {
            /* Current phase is the first phase. */
            if(m_phaseIndex == 0)
            {
                ui_backButton.setDisable(true);
                ui_nextButton.setDisable(false);
            }
            /* Current phase is the last phase. */
            else if(m_phaseIndex == m_phases.size()-1)
            {
                ui_backButton.setDisable(false);
                ui_nextButton.setDisable(true);
            }
            /* Current phase is an intermediate phase. */
            else
            {
                ui_backButton.setDisable(false);
                ui_nextButton.setDisable(false);
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
        ui_okButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent t)
            {
                m_accepted = true;
                close();
            }
        });
        
        /* Reject dialog on pressing Cancel or hitting Esc. */
        ui_cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent t)
            {
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
        
        ui_backButton.setOnAction(nav);
        ui_nextButton.setOnAction(nav);
        
        /* If user closes the dialog via X on upper right corner. */
        setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override public void handle(WindowEvent t)
            {
                m_accepted = false;
                close();
            }
        });
    }
    
    private BorderPane createBaseLayout(ResourceBundle res)
    {
        HBox buttonsLayout = new HBox(5.0);
        buttonsLayout.setPadding(new Insets(10.0, 5.0, 5.0, 5.0));
        
        ui_backButton = new Button(res.getString("BACK"));
        ui_backButton.setId("BackButton");
        
        ui_nextButton = new Button(res.getString("NEXT"));
        ui_nextButton.setId("NextButton");
        
        ui_okButton = new Button(res.getString("OK"));
        
        ui_cancelButton = new Button(res.getString("CANCEL"));
        ui_cancelButton.setCancelButton(true);
        
        buttonsLayout.getChildren().addAll(ui_backButton, ui_nextButton, UIUtils.getHStretcher(), ui_okButton, ui_cancelButton);
        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(5.0));
        root.setBottom(buttonsLayout);
        
        return root;
    }
}
