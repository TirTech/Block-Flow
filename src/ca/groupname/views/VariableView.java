package ca.groupname.views;

import ca.groupname.Logic.Variable;
import ca.groupname.expressions.SupportedTypes;
import ca.groupname.util.StyleUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class VariableView extends VBox {
    
    private TableView<Variable> table = new TableView();
    private NewVariableView newVarPane = new NewVariableView();
    private Button printVars = new Button("Print vars");
    private Button addVar = new Button("Add variable");
    private TableColumn<Variable, String> colValue = new TableColumn<>("Value");
    private TableColumn<Variable, String> colType = new TableColumn<>("Type");
    private TableColumn<Variable, String> colName = new TableColumn<>("Name");
    private SimpleListProperty<Variable> vars = new SimpleListProperty<>(FXCollections.observableArrayList());
    
    public VariableView(ObservableList<Variable> variableList) {
        vars.set(variableList);
        
        // Cell Value Factories and formatting
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        colType.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getType().toString()));
        
        // Actions and Events
        addVar.setOnAction(e -> {
            this.getChildren().add(this.getChildren().indexOf(addVar) + 1, newVarPane);
            addVar.setDisable(true);
        });
        
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
        table.getColumns().addAll(colName, colValue, colType);
        table.itemsProperty().bind(this.vars);
        table.setPlaceholder(new Label("No variables. \nClick \"Add Variable\" above."));
        getChildren().addAll(printVars, addVar, table);
        
        // Formatting
        setPadding(new Insets(10));
        setBorder(StyleUtils.getCurvedBorderGrey(5));
        this.setSpacing(5);
        
        // DEBUG --------
        printVars.setOnAction(e -> {
            for (Variable v : vars.get()) {
                System.out.printf("-----\nName: %s\nType: %s\nValue: %s\n-----", v.getName(), v.getType(), v.getValue());
                if (v.getType() == SupportedTypes.INT) {
                    System.out.println((Integer) v.getValue() + 5);
                } else if (v.getType() == SupportedTypes.DOUBLE) {
                    System.out.println((Double) v.getValue() + 5.1);
                }
            }
        });
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
