package ca.groupname.flows;

import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * Saves the state of a flowchart execution, including variables, current block, and the state of the flow execution
 */
public class FlowState {
	
	private SimpleObjectProperty<Block> currentBlock = new SimpleObjectProperty<>(null);
	private SimpleObjectProperty<FlowStatus> status = new SimpleObjectProperty<>(FlowStatus.STOPPED);
	private SimpleMapProperty<String, Variable> vars = new SimpleMapProperty<>(FXCollections.observableHashMap());
	public SimpleStringProperty teststring = new SimpleStringProperty("");
	
	public FlowState() {
	}
	
	public Block getCurrentBlock() {
		return currentBlock.get();
	}
	
	public SimpleObjectProperty<Block> currentBlockProperty() {
		return currentBlock;
	}
	
	public void setCurrentBlock(Block currentBlock) {
		this.currentBlock.set(currentBlock);
	}
	
	public FlowStatus getStatus() {
		return status.get();
	}
	
	public SimpleObjectProperty<FlowStatus> statusProperty() {
		return status;
	}
	
	public void setStatus(FlowStatus status) {
		this.status.set(status);
	}
	
	public ObservableMap<String, Variable> getVars() {
		return vars.get();
	}
	
	public SimpleMapProperty<String, Variable> varsProperty() {
		return vars;
	}
	
	public void addVars(Variable... vars) {
		for (Variable var : vars) {
			this.vars.put(var.getName(), var);
		}
	}
	
	public void removeVars(Variable... vars) {
		for (Variable var : vars) {
			this.vars.remove(var.getName());
		}
	}
	
	public Variable getVar(String name) {
		return vars.get(name);
	}
}
