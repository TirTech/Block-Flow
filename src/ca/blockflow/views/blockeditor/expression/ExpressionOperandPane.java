package ca.blockflow.views.blockeditor.expression;

import ca.blockflow.logic.Expression;
import ca.blockflow.logic.SupportedTypes;
import ca.blockflow.logic.Variable;
import ca.blockflow.models.AppModel;
import ca.blockflow.util.StyleUtils;
import ca.blockflow.views.ValueForm;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.Collections;

public class ExpressionOperandPane extends GridPane {
    
    private ToggleGroup typeGroup = new ToggleGroup();
    private RadioButton rbExpression = new RadioButton("Expression");
    private RadioButton rbValue = new RadioButton("Value");
    private RadioButton rbVar = new RadioButton("Variable");
    
    private ExpressionPane exprPane = null;
    private ChoiceBox<Variable> variableChoiceBox;
    private ValueForm valForm = null;
    private VBox exprViewContainer = new VBox();
    
    private ObservableList<Variable> vars;
    private Collection<SupportedTypes> types;
    
    private ChangeListener toggleChangeListeneer = (obs, oldVal, newVal) -> {
        if (newVal == rbExpression) {
            exprPane = new ExpressionPane(types, vars);
            exprViewContainer.getChildren().clear();
            exprViewContainer.getChildren().add(exprPane);
            toggleOn(exprViewContainer);
        } else if(newVal == rbValue) {
            toggleOn(valForm);
        } else {
            toggleOn(variableChoiceBox);
        }
    };
    
    public ExpressionOperandPane(SupportedTypes type, ObservableList<Variable> vars) {
        this(Collections.singleton(type),vars);
    }
    
    public ExpressionOperandPane(Collection<SupportedTypes> types, ObservableList<Variable> vars) {
        this.vars = vars;
        this.types = types;
        init();
        
        typeGroup.selectedToggleProperty().addListener(toggleChangeListeneer);
    }
    
    private void init() {
        setBorder(StyleUtils.getCurvedBorderGrey(5));
        setPadding(new Insets(5,5,5,5));
        setVgap(5);
        rbExpression.setToggleGroup(typeGroup);
        rbValue.setToggleGroup(typeGroup);
        rbVar.setToggleGroup(typeGroup);
        
        variableChoiceBox = new ChoiceBox<>(vars);
        HBox typeControls = new HBox(rbExpression, rbValue, rbVar);
        typeControls.setSpacing(5);
        valForm = new ValueForm(types);
        
        addRow(0, typeControls);
        addRow(1, new HBox(valForm, exprViewContainer, variableChoiceBox));
        
        typeGroup.selectToggle(rbValue);
        toggleOn(valForm);
    }
    
    public Expression getExpression() {
        Toggle selected = typeGroup.getSelectedToggle();
        Expression newExpr = null;
        if (selected == rbExpression) {
            newExpr = exprPane == null ? null : exprPane.createExpression();
        } else if(selected == rbValue) {
            newExpr = new Expression(valForm.getValue(), valForm.getSelectedType());
        } else {
            newExpr = new Expression(variableChoiceBox.getValue());
        }
        return newExpr;
    }
    
    private void toggleOn(Node node) {
        toggleNode(exprViewContainer, false);
        toggleNode(valForm, false);
        toggleNode(variableChoiceBox, false);
        toggleNode(node, true);
    }
    
    /**
     * Enables or disables the visibility and drawing of a node
     * @param node  the node to toggle
     * @param state whether to show the node
     */
    private void toggleNode(Node node, boolean state) {
        node.setVisible(state);
        node.setManaged(state);
    }
    
    public void loadExpression(Expression expression) {
        Variable val = expression.getValue();
        // Need to prevent toggle events while setting up
        typeGroup.selectedToggleProperty().removeListener(toggleChangeListeneer);
        if (val != null) {
           if (val.getName() == null || val.getName().trim().isEmpty()) {
               typeGroup.selectToggle(rbValue);
               valForm.setup(val);
               toggleOn(valForm);
           } else {
               typeGroup.selectToggle(rbVar);
               variableChoiceBox.setValue(AppModel.getInstance().findVar(val.getName()));
               toggleOn(variableChoiceBox);
           }
        } else {
            typeGroup.selectToggle(rbExpression);
            exprPane = new ExpressionPane(types, vars);
            exprPane.loadExpression(expression);
            exprViewContainer.getChildren().clear();
            exprViewContainer.getChildren().add(exprPane);
            toggleOn(exprViewContainer);
        }
        typeGroup.selectedToggleProperty().addListener(toggleChangeListeneer);
    }
}
