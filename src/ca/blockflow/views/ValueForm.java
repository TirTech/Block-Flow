package ca.blockflow.views;

import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.logic.Variable;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Collection;
import java.util.List;

public class ValueForm extends GridPane {
    
    private ChoiceBox<SupportedTypes> cbTypes = new ChoiceBox<>();
    private TextField txtValue = new TextField();
    private Spinner spinInt = new Spinner<Integer>(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
    private Spinner spinDouble = new Spinner<Double>(Double.MIN_VALUE, Double.MAX_VALUE, 0.0);
    private CheckBox boolVal = new CheckBox();
    private Label cbTypesLabel = new Label("Type: ");
    
    public ValueForm(List<SupportedTypes> types) {
        this.addRow(0, cbTypesLabel, cbTypes);
        this.addRow(1, new Label("Value: "), new HBox(txtValue, spinInt, spinDouble, boolVal));
        this.setVgap(5);
        this.setHgap(5);
        spinInt.setEditable(true);
        spinDouble.setEditable(true);
        
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
        
        cbTypes.valueProperty().addListener(e -> {
            SupportedTypes selection = cbTypes.getValue();
            toggleAllFalse();
            if (selection == null) return;
            switch (selection) {
                case STRING:
                    toggleNode(txtValue, true);
                    break;
                case INT:
                    toggleNode(spinInt, true);
                    break;
                case DOUBLE:
                    toggleNode(spinDouble, true);
                    break;
                case BOOLEAN:
                    toggleNode(boolVal, true);
                    break;
            }
        });
    
        setTypes(types);
        cbTypes.setValue(cbTypes.getItems().get(0));
    }
    
    private void toggleAllFalse() {
        toggleNode(txtValue, false);
        toggleNode(spinInt, false);
        toggleNode(spinDouble, false);
        toggleNode(boolVal, false);
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
    
    public Object getValue() {
        SupportedTypes selection = getSelectedType();
        Object value = null;
        if (selection == null) return null;
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
        return value;
    }
    
    public SupportedTypes getSelectedType() {
        return cbTypes.getValue();
    }
    
    public void setTypes(Collection<SupportedTypes> types) {
        cbTypes.setItems(FXCollections.observableArrayList(types));
        if (types.size() != 0) {
            cbTypes.setValue(cbTypes.getItems().get(0));
            if (types.size() == 1) {
                toggleNode(cbTypes, false);
                toggleNode(cbTypesLabel, false);
            } else {
                toggleNode(cbTypes, true);
                toggleNode(cbTypesLabel, true);
            }
            toggleNode(this, true);
        } else {
            toggleNode(this, false);
        }
    }
    
    public void setup(Variable value) {
        this.cbTypes.setValue(value.getType());
        switch (value.getType()) {
            case STRING:
                txtValue.setText((String) value.getValue());
                break;
            case INT:
                Integer valInt = (Integer) value.getValue();
                spinInt.getValueFactory().setValue(valInt);
                break;
            case DOUBLE:
                Double valDbl = (Double) value.getValue();
                spinDouble.getValueFactory().setValue(valDbl);
                break;
            case BOOLEAN:
                boolVal.setSelected((Boolean) value.getValue());
                break;
        }
    }
}
