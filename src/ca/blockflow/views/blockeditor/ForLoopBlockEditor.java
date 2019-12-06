package ca.blockflow.views.blockeditor;

import ca.blockflow.blocks.ForLoopBlock;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.logic.Variable;
import ca.blockflow.models.AppModel;
import ca.blockflow.views.ExpressionsView;
import ca.blockflow.views.ValueForm;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Collections;

public class ForLoopBlockEditor extends BlockEditor<ForLoopBlock> {
    
    private ExpressionsView expView = new ExpressionsView(SupportedTypes.BOOLEAN, AppModel.getInstance().getVariables());
    private ValueForm formIncrement = new ValueForm(Collections.singletonList(SupportedTypes.DOUBLE));
    private ChoiceBox<Variable> indexVars = new ChoiceBox<>();
    
    public ForLoopBlockEditor() {
        indexVars.setItems(AppModel.getInstance().getVariables().filtered(v -> v.getType() == SupportedTypes.DOUBLE || v.getType() == SupportedTypes.INT));
        GridPane content = new GridPane();
        content.addRow(0, new Label("Index Variable: "), indexVars);
        content.addRow(1, new Label("Increment: "), formIncrement);
        content.addRow(2, new Label("Loop Condition: "), expView);
        content.setPadding(new Insets(5));
        getChildren().addAll(content);
    }
    
    @Override
    public boolean onApply(ActionEvent event) {
        backingBlock.setExpression(expView.createExpression());
        backingBlock.setIncrements(new Variable<>("", SupportedTypes.DOUBLE, (Double) formIncrement.getValue()));
        backingBlock.setIndexVar(indexVars.getValue());
        return true;
    }
    
    @Override
    public void initUI() {
        if (backingBlock.getExpression() != null) {
            expView.loadExpression(backingBlock.getExpression());
        }
        if (backingBlock.getIndexVar() != null
            && AppModel.getInstance().findVar(backingBlock.getIndexVar().getName()) != null) {
            indexVars.setValue(AppModel.getInstance().findVar(backingBlock.getIndexVar().getName()));
        }
        if (backingBlock.getIncrements() != null) {
            formIncrement.setup(backingBlock.getIncrements());
        }
    }
}
