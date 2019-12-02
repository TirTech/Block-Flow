package ca.blockflow.main;

import ca.blockflow.flows.FlowEngine;
import ca.blockflow.logic.Variable;
import ca.blockflow.views.BlockColorPalette;
import ca.blockflow.views.floweditor.FunctionBlockView;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppModel {
    
    private static AppModel instance;
    private FlowEngine engine;
    private FunctionBlockView rootBlockView;
    private SimpleListProperty<Variable> variables = new SimpleListProperty<>(FXCollections.observableArrayList());
    private BlockColorPalette colors;
    
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
        return rootBlockView;
    }
    
    public void setRootBlockView(FunctionBlockView rootBlockView) {
        this.rootBlockView = rootBlockView;
    }
    
    public SimpleListProperty<Variable> variablesProperty() {
        return variables;
    }
    
    public ObservableList<Variable> getVariables() {
        return variables.get();
    }
}
