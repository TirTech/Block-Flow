package ca.blockflow.views;

import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.expressions.Expression;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.logic.Operation;
import ca.blockflow.logic.Variable;
import ca.blockflow.util.StyleUtils;
import ca.blockflow.util.VoidFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ExpressionsView extends GridPane {
    
    private GridPane view;
    private SupportedTypes type;
    private ObservableList vars;
    private Label typeLabel;
    private ObservableList<Operation> opBoptions;
    private ChoiceBox<Operation> opBOptionsView;
    
    private TextField txtNameA;
    private TextField txtNameB;
    private TextField txtValueA;
    private Spinner spinIntA;
    private Spinner spinDoubleA;
    private TextField txtValueB;
    private Spinner spinIntB;
    private Spinner spinDoubleB;
    private Button btnSubmit;
    private Button btnCancel;
    private Consumer<Variable> onSubmit;
    private VoidFunction onCancel;
    private CheckBox boolValA;
    private CheckBox boolValB;
    private CheckBox aUsingVars;
    private CheckBox bUsingVars;
    private ObservableList<Variable> aVariables;
    private ChoiceBox<Variable> aVariableChoiceBox;
    private ObservableList<Variable> bVariables;
    private ChoiceBox<Variable> bVariableChoiceBox;
    
    private Expression expression;
    
    public ExpressionsView(SupportedTypes type, ObservableList<Variable> vars) {
        this.type = type;
        this.vars = vars;
        this.typeLabel = new Label("TYPE:\t" + type.toString());
        init();
        
        aUsingVars.setOnAction(event -> {
            boolean checked = aUsingVars.isSelected();
            System.out.println("aischecked: " + checked);
            getAppropriateFieldA(type);
        });
        
        bUsingVars.setOnAction(event -> {
            boolean checked = bUsingVars.isSelected();
            System.out.println("bischecked: " + checked);
            getAppropriateFieldB(type);
        });
        
        opBOptionsView.valueProperty().addListener(e -> {
            Operation selection = opBOptionsView.getValue();
            getOperandBInputField(selection);
//            switch (selection) {
//
//            }
            btnSubmit.setDisable(false);
            System.out.println("selection: " + selection);
        });
    
        // Fix bad input in spinner fields
        spinIntA.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && ! newVal.matches("-?[0-9]*")) {
                spinIntA.getEditor().setText(oldVal);
            }
        });
    
        spinDoubleA.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && ! newVal.matches("-?[0-9]*\\.?[0-9]*")) {
                spinDoubleA.getEditor().setText(oldVal);
            }
        });
    
        // Button logic
        txtNameA.textProperty().addListener((obs, oldVal, newVal) -> btnSubmit.setDisable(newVal == null || newVal.trim().isEmpty()));
        txtNameB.textProperty().addListener((obs, oldVal, newVal) -> btnSubmit.setDisable(newVal == null || newVal.trim().isEmpty()));
    
        btnCancel.setOnAction(e -> {
            if (this.onCancel != null) {
                this.onCancel.apply();
            }
        });
    
        btnSubmit.setOnAction(e -> {
            SupportedTypes selection = SupportedTypes.highestPromotion(Operation.getSupportedTypes(opBOptionsView.getValue()));
            Object valueA = null;
            Object valueB = null;
            if (aUsingVars.isSelected()) {
                valueA = aVariableChoiceBox.getValue().getValue();
            }
            else {
                switch (selection) {
                    case STRING:
                        valueA = txtNameA.getText();
                        break;
                    case INT:
                        valueA = spinIntA.getValue();
                        break;
                    case DOUBLE:
                        valueA = spinDoubleA.getValue();
                        break;
                    case BOOLEAN:
                        valueA = boolValA.isSelected();
                        break;
                }
            }
            if (bUsingVars.isSelected()) {
                valueB = bVariableChoiceBox.getValue().getValue();
            }
            else {
                switch (selection) {
                    case STRING:
                        valueB = txtNameB.getText();
                        break;
                    case INT:
                        valueB = spinIntB.getValue();
                        break;
                    case DOUBLE:
                        valueB = spinDoubleB.getValue();
                        break;
                    case BOOLEAN:
                        valueB = boolValB.isSelected();
                        break;
                }
            }
            System.out.println("Value A: " + valueA);
            System.out.println("Value B: " + valueB);
            Variable newVarA = new Variable<>(txtNameA.getText(), selection, valueA);
            Variable newVarB = new Variable<>(txtNameB.getText(), selection, valueB);
            System.out.println("newVarA: " + newVarA);
            System.out.println("newVarB: " + newVarB);
            
            Expression expA = new Expression();
            expA.setValue(newVarA);
            
            Operation op = opBOptionsView.getValue();
            
            Expression expB = new Expression();
            expB.setValue(newVarB);
            
            Expression finalExp = new Expression();
            finalExp.setAttrs(expA, op, expB);
            this.expression = finalExp;
            
            System.out.println("expA: " + expA + "\nop: " + op + "\nexpB: " + expB + "\nfinalExp: " + finalExp);
            try {
                System.out.println("finalExp.value(): " + finalExp.evaluateExpression());
            } catch (ExceptionHandler exceptionHandler) {
                exceptionHandler.printStackTrace();
            }
//            exp.setAttrs(,,);
            if (onSubmit != null) {
                onSubmit.accept(newVarB);
            }
        });
    }
    
    private void init() {
        this.view = new GridPane();
        this.opBoptions = getOPs();
        this.opBOptionsView = new ChoiceBox<>(opBoptions);
        this.aUsingVars = new CheckBox();
        this.bUsingVars = new CheckBox();
        
        this.aVariableChoiceBox = new ChoiceBox<>(vars);
        this.bVariableChoiceBox = new ChoiceBox<>(vars);
        
        this.txtNameA = new TextField("A:\t");
        this.txtValueA = new TextField();
        this.spinIntA = new Spinner<Integer>(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        this.spinDoubleA = new Spinner<Double>(Double.MIN_VALUE, Double.MAX_VALUE, 0.0);
        this.boolValA = new CheckBox();
        
        this.txtNameB = new TextField("B:\t");
        this.txtValueB = new TextField();
        this.spinIntB = new Spinner<Integer>(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        this.spinDoubleB = new Spinner<Double>(Double.MIN_VALUE, Double.MAX_VALUE, 0.0);
        this.boolValB = new CheckBox();
        
        this.btnSubmit = new Button("Create");
        this.btnCancel = new Button("Cancel");
        
        this.setBorder(StyleUtils.getCurvedBorderGrey(5));
        this.setPadding(new Insets(5,5,5,5));

//        opBOptionsView.setItems((ObservableList<String>) opBoptions);
        
        this.addRow(1, typeLabel);
        this.addRow(2, opBOptionsView);
        this.addRow(3, new HBox(new HBox(new Label("Use vars for A:\t"), aUsingVars),
                                new HBox(new Label("Use vars for B:\t"), bUsingVars)));
        this.addRow(4, new Label("A:\t"), txtNameA, spinIntA, spinDoubleA, boolValA, aVariableChoiceBox);
        this.addRow(5, new Label("B:\t"),txtNameB, spinIntB, spinDoubleB, boolValB, bVariableChoiceBox);
        this.addRow(6, btnSubmit, btnCancel);
        this.setHgap(5);
        this.setVgap(5);
        spinIntA.setEditable(true);
        spinDoubleA.setEditable(true);
        btnSubmit.setDisable(true);
        spinIntB.setEditable(true);
        spinDoubleB.setEditable(true);
        
        opBOptionsView.setPrefWidth(150);
        typeLabel.setTextAlignment(TextAlignment.CENTER);
    
        toggleAllAFalse();
        toggleAllBFalse();
    }
    
    private void toggleAllAFalse() {
        toggleNode(txtNameA, false);
        toggleNode(spinIntA, false);
        toggleNode(spinDoubleA, false);
        toggleNode(boolValA, false);
        toggleNode(aVariableChoiceBox, false);
    }
    
    
    private void toggleAllBFalse() {
        toggleNode(txtNameB, false);
        toggleNode(spinIntB, false);
        toggleNode(spinDoubleB, false);
        toggleNode(boolValB, false);
        toggleNode(bVariableChoiceBox, false);
    }
    
    private void getOperandBInputField(Operation op) {
        ArrayList<SupportedTypes> types = Operation.getSupportedTypes(op);
        SupportedTypes greatestType = SupportedTypes.highestPromotion(types);
        getAppropriateFieldA(greatestType);
        getAppropriateFieldB(greatestType);
    }
    
    private void getAppropriateFieldA(SupportedTypes greatestType) {
        if (aUsingVars.isSelected()) {
            toggleAllAFalse();
            toggleNode(aVariableChoiceBox, true);
        }
        else {
            toggleNode(aVariableChoiceBox, false);
            switch (greatestType) {
                case STRING:
                    toggleNode(txtValueA, true);
                    toggleNode(spinIntA, false);
                    toggleNode(spinDoubleA, false);
                    toggleNode(boolValA, false);
                    return;
                case INT:
                    toggleNode(txtValueA, false);
                    toggleNode(spinIntA, true);
                    toggleNode(spinDoubleA, false);
                    toggleNode(boolValA, false);
                    return;
                case DOUBLE:
                    toggleNode(txtValueA, false);
                    toggleNode(spinIntA, false);
                    toggleNode(spinDoubleA, true);
                    toggleNode(boolValA, false);
                    return;
                case BOOLEAN:
                    toggleNode(txtValueA, false);
                    toggleNode(spinIntA, false);
                    toggleNode(spinDoubleA, false);
                    toggleNode(boolValA, true);
                    return;
                default:
                    System.err.println("WTF");
                    return;
            }
        }
    }
    
    private void getAppropriateFieldB(SupportedTypes greatestType) {
        if (bUsingVars.isSelected()) {
            toggleAllBFalse();
            toggleNode(bVariableChoiceBox, true);
        }
        else {
            toggleNode(bVariableChoiceBox, false);
            switch (greatestType) {
                case STRING:
                    toggleNode(txtValueB, true);
                    toggleNode(spinIntB, false);
                    toggleNode(spinDoubleB, false);
                    toggleNode(boolValB, false);
                    return;
                case INT:
                    toggleNode(txtValueB, false);
                    toggleNode(spinIntB, true);
                    toggleNode(spinDoubleB, false);
                    toggleNode(boolValB, false);
                    return;
                case DOUBLE:
                    toggleNode(txtValueB, false);
                    toggleNode(spinIntB, false);
                    toggleNode(spinDoubleB, true);
                    toggleNode(boolValB, false);
                    return;
                case BOOLEAN:
                    toggleNode(txtValueB, false);
                    toggleNode(spinIntB, false);
                    toggleNode(spinDoubleB, false);
                    toggleNode(boolValB, true);
                    return;
                default:
                    System.err.println("WTF");
                    return;
            }
        }
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
    
    /**
     * Set the callback for the submission of this form
     * @param onSubmit the function to call
     */
    public void setOnSubmit(Consumer<Variable> onSubmit) {
        this.onSubmit = onSubmit;
    }
    
    /**
     * Set the callback for the cancellation of this form
     * @param onCancel the function to call
     */
    public void setOnCancel(VoidFunction onCancel) {
        this.onCancel = onCancel;
    }
    
    public Expression getExpression() {
        return expression;
    }
    
//    public ArrayList<String> getIntOps() {
//        ArrayList<Operation> stringOps = Operation.getIntOps();
//        return (ArrayList<String>) stringOps.stream().map(Operation::toString).
//                collect(Collectors.toList());
//    }
//
//    public ArrayList<String> getDoubleOps() {
//        ArrayList<Operation> stringOps = Operation.getDoubleOps();
//        return (ArrayList<String>) stringOps.stream().map(Operation::toString).
//                collect(Collectors.toList());
//    }
//
//    public ArrayList<String> getBooleanOps() {
//        ArrayList<Operation> stringOps = Operation.getBooleanOps();
//        return (ArrayList<String>) stringOps.stream().map(Operation::toString).
//                collect(Collectors.toList());
//    }
//
//    public ArrayList<String> getStringOps() {
//        ArrayList<Operation> stringOps = Operation.getStringOps();
//        return (ArrayList<String>) stringOps.stream().map(Operation::toString).
//                collect(Collectors.toList());
//    }
}
