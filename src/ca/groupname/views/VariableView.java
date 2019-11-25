package ca.groupname.views;

import ca.groupname.Logic.Variable;
import ca.groupname.util.StyleUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VariableView extends VBox {
    
    private TableView<Variable> table = new TableView();
    private NewVariableView newVarPane = new NewVariableView();
    private Button printVars = new Button("Print vars");
    private Button addVar = new Button("Add variable");
    private Button removeVar = new Button("Remove Variable");
    private TableColumn<Variable, String> colValue = new TableColumn<>("Value");
    private TableColumn<Variable, String> colType = new TableColumn<>("Type");
    private TableColumn<Variable, String> colName = new TableColumn<>("Name");
    private SimpleListProperty<Variable> vars = new SimpleListProperty<>(FXCollections.observableArrayList());
    private HBox varControls = new HBox(addVar, removeVar);
    
    public VariableView(ObservableList<Variable> variableList) {
        vars.set(variableList);
        
        // Cell Value Factories and formatting
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        colType.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getType().toString()));
        
        // Actions and Events
        addVar.setOnAction(e -> {
            this.getChildren().add(this.getChildren().indexOf(varControls) + 1, newVarPane);
            addVar.setDisable(true);
        });
    
        removeVar.setOnAction(e -> {
            Variable selection = table.getSelectionModel().getSelectedItem();
            if (selection != null) {
                vars.remove(selection);
            }
        });
    
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> removeVar.setDisable(newVal == null));
        
        newVarPane.setOnSubmit((newVar) -> {
            vars.add(newVar);
            this.getChildren().remove(newVarPane);
            addVar.setDisable(false);
        });
        
        newVarPane.setOnCancel(() -> {
            this.getChildren().remove(newVarPane);
            addVar.setDisable(false);
        });
    
        // Setup view layout
        table.setEditable(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table.getColumns().addAll(colName, colValue, colType);
        table.itemsProperty().bind(this.vars);
        table.setPlaceholder(new Label("No variables. \nClick \"Add Variable\" above."));
    
        removeVar.setDisable(true);
        varControls.setSpacing(5);
        getChildren().addAll(varControls, table);
        
        // Formatting
        setPadding(new Insets(10));
        setBorder(StyleUtils.getCurvedBorderGrey(5));
        this.setSpacing(5);
    }
    
    /**
     * Get the value of a particular row
     * @param e the cell edit event
     * @return the object from this row
     */
    private Object getRowValue(TableColumn.CellEditEvent e) {
        return e.getTableView().getItems().get(e.getTablePosition().getRow());
    }
}
