package ca.blockflow.logic;

import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

/**
 * A container class for variables in a particular closure (i.e scope) such as inside functions. Scopes enable variable shadowing
 */
public class Scope {
    
    /**
     * A map of all variables in this scope
     */
    private SimpleMapProperty<String, Variable> vars = new SimpleMapProperty(FXCollections.observableHashMap());
    
    /**
     * Creates a new blank scope
     */
    public Scope() {}
    
    /**
     * Adds a variable to the scope. If the variable name is already used, the value is not added
     * @param var the variable object to add
     * @return true if the variable was added, false if the name was already taken
     */
    public boolean addVar(Variable var) {
        if (vars.containsKey(var.getName())) {
            return false;
        } else {
            vars.put(var.getName(), var);
            return true;
        }
    }
    
    /**
     * Removes a variable from the scope. If the variable name is not used, the value is not removed
     * @param var the variable object to remove
     * @return true if the variable was removed, false if the variable did not exist
     */
    public boolean removeVar(Variable var) {
        return removeVar(var.getName());
    }
    
    /**
     * Removes a variable from the scope. If the variable name is not used, the value is not removed
     * @param name the variable name to remove
     * @return true if the variable was removed, false if the name did not exist
     */
    public boolean removeVar(String name) {
        if (vars.containsKey(name)) {
            return false;
        } else {
            vars.remove(name);
            return true;
        }
    }
    
    /**
     * Get the variable object for a given variable name
     * @param name the variable name to look for
     * @return the variable object associated with this variable name
     */
    public Variable getVar(String name) {
        return vars.get(name);
    }
}
