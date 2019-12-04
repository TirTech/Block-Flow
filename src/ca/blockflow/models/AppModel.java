package ca.blockflow.models;

import ca.blockflow.flows.FlowEngine;
import ca.blockflow.logic.Variable;
import ca.blockflow.views.BlockColorPalette;
import ca.blockflow.views.ExceptionView;
import ca.blockflow.views.floweditor.FunctionBlockView;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class AppModel {
    
    private static AppModel instance;
    private FlowEngine engine;
    private SimpleObjectProperty<FunctionBlockView> rootBlockView = new SimpleObjectProperty<>();
    private SimpleListProperty<Variable> variables = new SimpleListProperty<>(FXCollections.observableArrayList());
    private BlockColorPalette colors;
    private ExceptionView console;
    
    private AppModel() {
    
    }
    
    public BlockColorPalette getColors() {
        if (colors == null) colors = new BlockColorPalette();
        return colors;
    }
    
    public void setColors(BlockColorPalette colors) {
        this.colors = colors;
    }
    
    public static AppModel getInstance() {
        if (instance == null) instance = new AppModel();
        return instance;
    }
    
    public FlowEngine getEngine() {
        if (engine == null) engine = new FlowEngine();
        return engine;
    }
    
    public FunctionBlockView getRootBlockView() {
        return rootBlockView.get();
    }
    
    public void setRootBlockView(FunctionBlockView rootBlockView) {
        this.rootBlockView.set(rootBlockView);
    }
    
    public SimpleListProperty<Variable> variablesProperty() {
        return variables;
    }
    
    public ObservableList<Variable> getVariables() {
        return variables.get();
    }
    
    public void setVariables(List<Variable> variables) {
        this.variables.set(FXCollections.observableArrayList(variables));
    }
    
    public ExceptionView getConsole() {
        if (console == null) console = new ExceptionView();
        return console;
    }
    
    public SimpleObjectProperty<FunctionBlockView> rootBlockViewProperty() {
        return rootBlockView;
    }
}
