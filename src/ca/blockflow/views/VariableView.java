package ca.blockflow.views;

import ca.blockflow.controllers.VariableViewController;
import ca.blockflow.logic.Variable;
import ca.blockflow.util.StyleUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
    private Button btnLoadVars = new Button("Load Variables");
    private Button btnSaveVars = new Button("Save Variables");
    private TableColumn<Variable, String> colValue = new TableColumn<>("Value");
    private TableColumn<Variable, String> colType = new TableColumn<>("Type");
    private TableColumn<Variable, String> colName = new TableColumn<>("Name");
    private VariableViewController controller = new VariableViewController(this);
    
    private HBox varControls = new HBox(addVar, removeVar);
    
    public VariableView() {
        // Cell Value Factories and formatting
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        colType.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getType().toString()));
    
        // Setup view layout
        table.setEditable(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table.getColumns().addAll(colName, colValue, colType);
        table.setPlaceholder(new Label("No variables. \nClick \"Add Variable\" above."));
    
        removeVar.setDisable(true);
        varControls.setSpacing(5);
        HBox buttonBox = new HBox(btnLoadVars, btnSaveVars);
        buttonBox.setSpacing(5);
        getChildren().addAll(varControls, table, buttonBox);
        
        // Formatting
        setPadding(new Insets(10));
        setBorder(StyleUtils.getCurvedBorderGrey(5));
        this.setSpacing(5);
        controller.setHandlers();
    }
    
    /**
     * Get the value of a particular row
     * @param e the cell edit event
     * @return the object from this row
     */
    private Object getRowValue(TableColumn.CellEditEvent e) {
        return e.getTableView().getItems().get(e.getTablePosition().getRow());
    }
    
    public Button getBtnLoadVars() {
        return btnLoadVars;
    }
    
    public void setBtnLoadVars(Button btnLoadVars) {
        this.btnLoadVars = btnLoadVars;
    }
    
    public Button getBtnSaveVars() {
        return btnSaveVars;
    }
    
    public void setBtnSaveVars(Button btnSaveVars) {
        this.btnSaveVars = btnSaveVars;
    }
    
    public TableView<Variable> getTable() {
        return table;
    }
    
    public NewVariableView getNewVarPane() {
        return newVarPane;
    }
    
    public Button getPrintVars() {
        return printVars;
    }
    
    public Button getAddVar() {
        return addVar;
    }
    
    public Button getRemoveVar() {
        return removeVar;
    }
    
    public TableColumn<Variable, String> getColValue() {
        return colValue;
    }
    
    public TableColumn<Variable, String> getColType() {
        return colType;
    }
    
    public TableColumn<Variable, String> getColName() {
        return colName;
    }
    
    public HBox getVarControls() {
        return varControls;
    }
}
