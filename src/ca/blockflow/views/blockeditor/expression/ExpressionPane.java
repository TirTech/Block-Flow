package ca.blockflow.views.blockeditor.expression;

import ca.blockflow.logic.Expression;
import ca.blockflow.logic.SupportedTypes;
import ca.blockflow.logic.operations.Operation;
import ca.blockflow.logic.Variable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.*;
import java.util.stream.Collectors;

public class ExpressionPane extends GridPane {
    
    private Collection<SupportedTypes> types;
    private ObservableList<Variable> vars;
    private ChoiceBox<Operation> cbOps;
    private ExpressionOperandPane expLeft;
    private ExpressionOperandPane expRight;
    private HBox expLeftContainer = new HBox();
    private HBox expRightContainer = new HBox();
    
    public ExpressionPane(Collection<SupportedTypes> types, ObservableList<Variable> vars) {
        this.types = types;
        this.vars = vars;
        init();
        
        cbOps.valueProperty().addListener((obs, oldVal, newVal) -> {
            expLeft = new ExpressionOperandPane(new ArrayList<>(newVal.getRestrictedATypesForOp(types)), vars);
            expLeftContainer.getChildren().clear();
            expLeftContainer.getChildren().add(expLeft);
            expRight = new ExpressionOperandPane(new ArrayList<>(newVal.getRestrictedBTypesForOp(types)), vars);
            expRightContainer.getChildren().clear();
            expRightContainer.getChildren().add(expRight);
        });
    }
    
    private void init() {
        cbOps = new ChoiceBox<>(getOPs());
        cbOps.setPrefWidth(150);
        this.setPadding(new Insets(5,5,5,5));
        
        this.addRow(0, expLeftContainer);
        this.addRow(1, cbOps);
        this.addRow(2, expRightContainer);
        this.setHgap(5);
        this.setVgap(5);
    }
    
    public Expression createExpression() {
        Operation op = cbOps.getValue();
        Expression finalExp;
        finalExp = new Expression(expLeft.getExpression(), op, expRight.getExpression());
        return finalExp;
    }
    
    private ObservableList<Operation> getOPs() {
        return FXCollections.observableArrayList(types.stream().flatMap(t -> Operation.getOpsWithReturnType(t).stream()).collect(Collectors.toSet()));
    }
    
    public void loadExpression(Expression expression) {
        if (expression == null) return;
        cbOps.setValue(expression.getOperation());
        expLeft.loadExpression(expression.getExprLeft());
        expRight.loadExpression(expression.getExprRight());
    }
}
