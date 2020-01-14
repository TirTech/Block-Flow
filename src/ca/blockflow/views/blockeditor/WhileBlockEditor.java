package ca.blockflow.views.blockeditor;

import ca.blockflow.blocks.WhileLoopBlock;
import ca.blockflow.logic.SupportedTypes;
import ca.blockflow.models.AppModel;
import ca.blockflow.views.blockeditor.expression.ExpressionOperandPane;
import javafx.event.ActionEvent;

public class WhileBlockEditor extends BlockEditor<WhileLoopBlock> {
    
    private ExpressionOperandPane expView = new ExpressionOperandPane(SupportedTypes.BOOLEAN, AppModel.getInstance().getVariables());
    
    public WhileBlockEditor() {
        getChildren().addAll(expView);
    }
    
    @Override
    public boolean onApply(ActionEvent event) {
        backingBlock.setExpression(expView.getExpression());
        return true;
    }
    
    @Override
    public void initUI() {
        if (backingBlock.getExpression() != null) {
            expView.loadExpression(backingBlock.getExpression());
        }
    }
}
