package ca.blockflow.blocks;

import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Variable;
import ca.blockflow.util.AppUtils;

import java.util.ArrayList;

public class OutputBlock extends Block {
    private static final long serialVersionUID = 1L;
    private String message;
    private ArrayList<String> names;
    
    @Override
    public Block call(FlowState state) {
        String output = message;
        for (String name : names) {
            Variable var = state.getVar(name);
            output = output.replaceAll("%(" + name + ")%", var.getValue().toString());
        }
        AppUtils.logMessage(output);
        return null;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public ArrayList<String> getNames() {
        return names;
    }
    
    public void setNames(ArrayList<String> names) {
        this.names = names;
    }
}
