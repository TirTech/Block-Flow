package ca.blockflow.views.blockeditor;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class BlankEditor extends BlockEditor {
    
    
    public BlankEditor() {
        getChildren().add(new Label("This block has no editable functionality"));
    }
    
    @Override
    public boolean onApply(ActionEvent event) {
        return true;
    }
    
    @Override
    public void initUI() {
    
    }
}
