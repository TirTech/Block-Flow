package ca.blockflow.views;

import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.logic.Variable;
import ca.blockflow.util.VoidFunction;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

/**
 * A view for creating new variable objects
 */
public class NewVariableView extends GridPane {
    
    private TextField txtName = new TextField();
    private ChoiceBox<SupportedTypes> cbTypes = new ChoiceBox<>(FXCollections.observableArrayList(SupportedTypes.values()));
    private TextField txtValue = new TextField();
    private Spinner spinInt = new Spinner<Integer>(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
    private Spinner spinDouble = new Spinner<Double>(Double.MIN_VALUE, Double.MAX_VALUE, 0.0);
    private Button btnSubmit = new Button("Create");
    private Button btnCancel = new Button("Cancel");
    private Consumer<Variable> onSubmit;
    private VoidFunction onCancel;
    private CheckBox boolVal = new CheckBox();
    
    public NewVariableView() {
        
        // View init
        this.addRow(0, new Label("Name: "), txtName);
        this.addRow(1, new Label("Type: "), cbTypes);
        this.addRow(2, new Label("Value: "), new HBox(txtValue, spinInt, spinDouble, boolVal));
        this.addRow(3, btnSubmit, btnCancel);
        this.setHgap(5);
        this.setVgap(5);
        spinInt.setEditable(true);
        spinDouble.setEditable(true);
        btnSubmit.setDisable(true);
        
        // Fix bad input in spinner fields
        spinInt.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && ! newVal.matches("-?[0-9]*")) {
                spinInt.getEditor().setText(oldVal);
            }
        });
        
        spinDouble.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && ! newVal.matches("-?[0-9]*\\.?[0-9]*")) {
                spinDouble.getEditor().setText(oldVal);
            }
        });
        
        // Button logic
        txtName.textProperty().addListener((obs, oldVal, newVal) -> btnSubmit.setDisable(newVal == null || newVal.trim().isEmpty()));
        
        btnCancel.setOnAction(e -> {
            if (this.onCancel != null) {
                this.onCancel.apply();
            }
        });
        
        btnSubmit.setOnAction(e -> {
            SupportedTypes selection = cbTypes.getValue();
            Object value = null;
            switch (selection) {
                case STRING:
                    value = txtValue.getText();
                    break;
                case INT:
                    value = spinInt.getValue();
                    break;
                case DOUBLE:
                    value = spinDouble.getValue();
                    break;
                case BOOLEAN:
                    value = boolVal.isSelected();
                    break;
            }
            Variable newVar = new Variable<>(txtName.getText(), selection, value);
            if (onSubmit != null) {
                onSubmit.accept(newVar);
            }
        });
        
        cbTypes.valueProperty().addListener(e -> {
            SupportedTypes selection = cbTypes.getValue();
            switch (selection) {
                case STRING:
                    toggleNode(txtValue, true);
                    toggleNode(spinInt, false);
                    toggleNode(spinDouble, false);
                    toggleNode(boolVal, false);
                    break;
                case INT:
                    toggleNode(txtValue, false);
                    toggleNode(spinInt, true);
                    toggleNode(spinDouble, false);
                    toggleNode(boolVal, false);
                    break;
                case DOUBLE:
                    toggleNode(txtValue, false);
                    toggleNode(spinInt, false);
                    toggleNode(spinDouble, true);
                    toggleNode(boolVal, false);
                    break;
                case BOOLEAN:
                    toggleNode(txtValue, false);
                    toggleNode(spinInt, false);
                    toggleNode(spinDouble, false);
                    toggleNode(boolVal, true);
                    break;
            }
        });
        
        //Keep these at the bottom to trigger the UI change events
        cbTypes.setValue(SupportedTypes.STRING);
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
}
