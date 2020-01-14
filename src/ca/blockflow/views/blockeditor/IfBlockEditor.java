package ca.blockflow.views.blockeditor;

import ca.blockflow.blocks.IfBlock;
import ca.blockflow.logic.SupportedTypes;
import ca.blockflow.models.AppModel;
import ca.blockflow.views.blockeditor.expression.ExpressionOperandPane;
import javafx.event.ActionEvent;

public class IfBlockEditor extends BlockEditor<IfBlock> {
    
    private ExpressionOperandPane expView = new ExpressionOperandPane(SupportedTypes.BOOLEAN, AppModel.getInstance().getVariables());
    
    public IfBlockEditor() {
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
