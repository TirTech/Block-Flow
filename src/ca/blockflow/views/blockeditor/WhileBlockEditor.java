package ca.blockflow.views.blockeditor;

import ca.blockflow.blocks.WhileLoopBlock;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.models.AppModel;
import ca.blockflow.views.ExpressionsView;
import javafx.event.ActionEvent;

public class WhileBlockEditor extends BlockEditor<WhileLoopBlock> {
    
    private ExpressionsView expView = new ExpressionsView(SupportedTypes.BOOLEAN, AppModel.getInstance().getVariables());
    
    public WhileBlockEditor() {
        getChildren().addAll(expView);
    }
    
    @Override
    public boolean onApply(ActionEvent event) {
        backingBlock.setExpression(expView.createExpression());
        return true;
    }
    
    @Override
    public void initUI() {}
}
