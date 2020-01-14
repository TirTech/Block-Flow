package ca.blockflow.views.blockeditor.expression;

import ca.blockflow.logic.SupportedTypes;
import ca.blockflow.logic.Variable;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.Collections;

public class ExpressionView extends VBox {
    
    private ExpressionOperandPane exprOpPane;
    
    public ExpressionView(SupportedTypes type, ObservableList<Variable> vars) {
        this(Collections.singleton(type), vars);
    }
    
    public ExpressionView(Collection<SupportedTypes> types, ObservableList<Variable> vars) {
        this.exprOpPane = new ExpressionOperandPane(types, vars);
    }
    
    public void validate() {
    
    }
}
