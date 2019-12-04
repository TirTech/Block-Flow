package ca.blockflow.views.blockeditor;

import ca.blockflow.blocks.AssignmentBlock;
import ca.blockflow.logic.Variable;
import ca.blockflow.models.AppModel;
import ca.blockflow.views.ExpressionsView;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class AssignmentBlockEditor extends BlockEditor<AssignmentBlock> {
    
    private ChoiceBox<Variable> cbVars = new ChoiceBox<>(AppModel.getInstance().getVariables());
    private ExpressionsView view;
    
    public AssignmentBlockEditor() {
        cbVars.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal == null || (newVal.getType() != oldVal.getType())) {
                if (view != null) getChildren().remove(view);
                view = new ExpressionsView(newVal.getType(), AppModel.getInstance().getVariables());
                getChildren().add(view);
            }
        });
        getChildren().addAll(new Label("Set: "), cbVars, new Label("To:"));
    }
    
    @Override
    public boolean onApply(ActionEvent event) {
        if (view == null) return false;
        backingBlock.setExpression(view.createExpression());
        backingBlock.setInput(cbVars.getValue());
        return true;
    }
    
    @Override
    public void initUI() {
    
    }
}
