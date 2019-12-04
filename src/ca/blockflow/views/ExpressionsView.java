package ca.blockflow.views;

import ca.blockflow.expressions.Expression;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.logic.Operation;
import ca.blockflow.logic.Variable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Collections;

public class ExpressionsView extends GridPane {
    
    private SupportedTypes type;
    private ObservableList<Variable> vars;
    private ChoiceBox<Operation> opBOptionsView;
    
    private ValueForm formA = new ValueForm(SupportedTypes.valuesAsList());
    private ValueForm formB = new ValueForm(SupportedTypes.valuesAsList());
    private CheckBox aUsingVars = new CheckBox("Use vars for A");
    private CheckBox bUsingVars = new CheckBox("Use vars for B");
    private ChoiceBox<Variable> aVariableChoiceBox;
    private ChoiceBox<Variable> bVariableChoiceBox;
    
    public ExpressionsView(SupportedTypes type, ObservableList<Variable> vars) {
        this.type = type;
        this.vars = vars;
        init();
    
        aUsingVars.setOnAction(event -> getAppropriateFieldA());
        bUsingVars.setOnAction(event -> getAppropriateFieldB());
        
        opBOptionsView.valueProperty().addListener(e -> {
            ArrayList<SupportedTypes> types = Operation.getSupportedTypes(opBOptionsView.getValue());
            formA.setTypes(types);
            formB.setTypes(types);
            if (opBOptionsView.getValue() == Operation.NO_OP) {
                formA.setTypes(Collections.singleton(type));
            }
            getAppropriateFieldA();
            getAppropriateFieldB();
        });
    
        toggleNode(aVariableChoiceBox, false);
        toggleNode(bVariableChoiceBox, false);
    }
    
    private void init() {
        this.opBOptionsView = new ChoiceBox<>(getOPs());
        this.aVariableChoiceBox = new ChoiceBox<>(vars);
        this.bVariableChoiceBox = new ChoiceBox<>(vars);
        Label typeLabel = new Label("TYPE:\t" + type.toString());
        
        this.setPadding(new Insets(5,5,5,5));
    
        HBox aInputs = new HBox(new Label("A:\t"), formA, aVariableChoiceBox);
        HBox bInputs = new HBox(new Label("B:\t"), formB, bVariableChoiceBox);
        HBox useVarControls = new HBox(aUsingVars, bUsingVars);
        
        this.addRow(1, typeLabel);
        this.addRow(2, opBOptionsView);
        this.addRow(3, useVarControls);
        this.addRow(4, aInputs);
        this.addRow(5, bInputs);
        this.setHgap(5);
        this.setVgap(5);
    
        opBOptionsView.setPrefWidth(150);
        useVarControls.setSpacing(5);
        typeLabel.setTextAlignment(TextAlignment.CENTER);
    }
    
    private void getAppropriateFieldA() {
        toggleNode(formA, ! aUsingVars.isSelected());
        toggleNode(aVariableChoiceBox, aUsingVars.isSelected());
    }
    
    private void getAppropriateFieldB() {
        toggleNode(formB, ! bUsingVars.isSelected());
        toggleNode(bVariableChoiceBox, bUsingVars.isSelected());
    }
    
    public Expression createExpression() {
        SupportedTypes selection;
        if (opBOptionsView.getValue() == Operation.NO_OP) {
            selection = formA.getSelectedType();
        } else {
            selection = SupportedTypes.highestPromotion(Operation.getSupportedTypes(opBOptionsView.getValue()));
        }
        Object valueA = formA.getValue();
        Object valueB = formB.getValue();
    
        Variable newVarA;
        if (aUsingVars.isSelected()) {
            newVarA = aVariableChoiceBox.getValue();
        } else {
            newVarA = new Variable<>("", selection, valueA);
        }
    
        Variable newVarB;
        if (bUsingVars.isSelected()) {
            newVarB = bVariableChoiceBox.getValue();
        } else {
            newVarB = new Variable<>("", selection, valueB);
        }
        
        Operation op = opBOptionsView.getValue();
        
        Expression finalExp;
        if (op == Operation.NO_OP) {
            finalExp = new Expression(newVarA);
        } else {
            finalExp = new Expression(new Expression(newVarA), op, new Expression(newVarB));
        }
        return finalExp;
    }
    
    private ObservableList<Operation> getOPs() {
        ArrayList<Operation> ops = new ArrayList<>();
        if (this.type.equals(SupportedTypes.INT)) {
            ops.addAll(Operation.getIntOps());
        }
        else if (this.type.equals(SupportedTypes.DOUBLE)) {
            ops.addAll(Operation.getDoubleOps());
        }
        else if (this.type.equals(SupportedTypes.BOOLEAN)) {
            ops.addAll(Operation.getBooleanOps());
        }
        else if (this.type.equals(SupportedTypes.STRING)) {
            ops.addAll(Operation.getStringOps());
        }
    
        return FXCollections.observableArrayList(ops);
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
}
