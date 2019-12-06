package ca.blockflow.views;

import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.logic.Variable;
import ca.blockflow.models.AppModel;
import ca.blockflow.util.VoidFunction;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

/**
 * A view for creating new variable objects
 */
public class NewVariableView extends VBox {
    
    private TextField txtName = new TextField();
    private Button btnSubmit = new Button("Create");
    private Button btnCancel = new Button("Cancel");
    private ValueForm form = new ValueForm(SupportedTypes.valuesAsList());
    private Consumer<Variable> onSubmit;
    private VoidFunction onCancel;
    
    public NewVariableView() {
        
        // View init
        HBox rowName = new HBox(new Label("Name: "), txtName);
        HBox rowButtons = new HBox(btnSubmit, btnCancel);
        rowName.setSpacing(5);
        rowButtons.setSpacing(5);
    
        getChildren().addAll(rowName, form, rowButtons);
        setSpacing(5);
        btnSubmit.setDisable(true);
        
        // Button logic
        txtName.textProperty().addListener((obs, oldVal, newVal) -> {
            if (! newVal.matches("\\w*")) {
                txtName.setText(oldVal);
                newVal = oldVal;
            }
            btnSubmit.setDisable(newVal == null || newVal.trim().isEmpty() || varExists(newVal));
        });
        
        btnCancel.setOnAction(e -> {
            if (this.onCancel != null) {
                this.onCancel.apply();
            }
        });
        
        btnSubmit.setOnAction(e -> {
            Variable newVar = new Variable<>(txtName.getText(), form.getSelectedType(), form.getValue());
            txtName.clear();
            if (onSubmit != null) {
                onSubmit.accept(newVar);
            }
        });
    }
    
    private boolean varExists(String newVal) {
        for (Variable var : AppModel.getInstance().getVariables()) {
            if (var.getName().equals(newVal)) return true;
        }
        return false;
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
