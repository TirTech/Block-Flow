package ca.groupname.flows;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Saves the state of a flowchart execution, including variables, current block, and the state of the flow execution
 */
public class FlowState {
    
    /**
     * The current execution block. This is set to a different block during their execution
     */
    private SimpleObjectProperty<Block> currentBlock = new SimpleObjectProperty<>(null);
    /**
     * The state of this flow's execution. Flow state is changed by flowchart events like pausing, breakpointing, and starting execution
     */
    private SimpleObjectProperty<FlowStatus> status = new SimpleObjectProperty<>(FlowStatus.STOPPED);
    /**
     * Holds the variable scopes. Scopes contain variable definitions and are "stacked" to provide shadowing
     */
    private SimpleListProperty<Scope> scopes = new SimpleListProperty<>(FXCollections.observableArrayList());
    
    /**
     * Creates a new FlowState with a single Scope
     */
    public FlowState() {
        enterScope();
        setStatus(FlowStatus.READY);
    }
    
    /**
     * Creates a new {@link Scope} on the scope stack. All new variables will be defined in this new scope.
     * Variables defined in previous scopes will not be accessible until this scope is removed using {@link #exitScope()}
     * @return the new {@link Scope}
     */
    public Scope enterScope() {
        Scope newScope = new Scope();
        scopes.add(newScope);
        return newScope;
    }
    
    /**
     * Gets the currently executing block
     * @return the block the state is currently executing on
     */
    public Block getCurrentBlock() {
        return currentBlock.get();
    }
    
    /**
     * Sets the currently executing block. This block will be executed by the flow.
     * @param currentBlock the block to set to
     */
    public void setCurrentBlock(Block currentBlock) {
        this.currentBlock.set(currentBlock);
    }
    
    /**
     * Gets the current block as a property
     * @return the current block's backing property
     */
    public SimpleObjectProperty<Block> currentBlockProperty() {
        return currentBlock;
    }
    
    /**
     * Gets the state of the flow
     * @return the flow's execution state
     */
    public FlowStatus getStatus() {
        return status.get();
    }
    
    /**
     * Set the state of the flow. Used to pause, play, step, and stop flow execution
     * @param status the status to set to
     */
    public void setStatus(FlowStatus status) {
        this.status.set(status);
    }
    
    /**
     * Gets the state of the flow as a property
     * @return the backing property of the flow state
     */
    public SimpleObjectProperty<FlowStatus> statusProperty() {
        return status;
    }
    
    /**
     * Gets all available Scopes
     * @return all Scopes for this state
     */
    public ObservableList<Scope> getScopes() {
        return scopes.get();
    }
    
    /**
     * Gets all available Scopes as a property
     * @return the backing property for Scopes
     */
    public SimpleListProperty<Scope> scopesProperty() {
        return scopes;
    }
    
    /**
     * Adds the variables to the current scope. If the variable already exists it will not be changed
     * @param vars the variables to add
     */
    public void addVars(Variable... vars) {
        Scope scope = getCurrentScope();
        for (Variable var : vars) {
            scope.addVar(var);
        }
    }
    
    /**
     * Gets the current Scope
     * @return the current scope
     */
    public Scope getCurrentScope() {
        return scopes.get(scopes.size() - 1);
    }
    
    /**
     * Removes the variables from the current scope. If the variable does not already exist it will not be removed
     * @param vars the variables to remove
     */
    public void removeVars(Variable... vars) {
        Scope scope = getCurrentScope();
        for (Variable var : vars) {
            scope.removeVar(var);
        }
    }
    
    /**
     * Removes the variables from the current scope. If the variable does not already exist it will not be removed
     * @param vars the variable names to remove
     */
    public void removeVars(String... vars) {
        Scope scope = getCurrentScope();
        for (String var : vars) {
            scope.removeVar(var);
        }
    }
    
    /**
     * Get the variable with the given name in the current scope. Returns <code>null</code> if the variable does not exist
     * @param name the variable name to look for
     * @return the variable with name <code>name</code> or <code>null</code> if it does not exist
     */
    public Variable getVar(String name) {
        return getCurrentScope().getVar(name);
    }
    
    /**
     * Removes the current {@link Scope} from the scope stack. Variables created in this scope will no longer be available, and variables
     * in the next lowest scope will be used.
     * @return the {@link Scope} that was removed
     */
    public Scope exitScope() {
        if (scopes.size() <= 1) return null;
        Scope scope = getCurrentScope();
        scopes.remove(scope);
        return scope;
    }
}
