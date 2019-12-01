package ca.blockflow.controllers;

import ca.blockflow.logic.Variable;
import ca.blockflow.main.AppModel;
import ca.blockflow.views.VariableView;
import javafx.beans.property.SimpleListProperty;

public class VariableViewController {
    
    private SimpleListProperty<Variable> model = AppModel.getInstance().variablesProperty();
    private VariableView view;
    
    public VariableViewController(VariableView view) {
        this.view = view;
    }
    
    public void setHandlers() {
        // Actions and Events
        view.getAddVar().setOnAction(e -> {
            view.getChildren().add(view.getChildren().indexOf(view.getVarControls()) + 1, view.getNewVarPane());
            view.getAddVar().setDisable(true);
        });
        
        view.getRemoveVar().setOnAction(e -> {
            Variable selection = view.getTable().getSelectionModel().getSelectedItem();
            if (selection != null) {
                model.remove(selection);
            }
        });
        
        view.getTable().getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> view.getRemoveVar().setDisable(newVal == null));
        
        view.getNewVarPane().setOnSubmit((newVar) -> {
            model.add(newVar);
            view.getChildren().remove(view.getNewVarPane());
            view.getAddVar().setDisable(false);
        });
        
        view.getNewVarPane().setOnCancel(() -> {
            view.getChildren().remove(view.getNewVarPane());
            view.getAddVar().setDisable(false);
        });
        
        view.getTable().itemsProperty().bind(model);
    }
}
