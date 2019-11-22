package ca.groupname.flows;

public class InvalidFlowStateException extends Exception {
    public InvalidFlowStateException() {
        super("Flow state was STOPPED. Flows cannot be executed in the stop state.");
    }
    
    public InvalidFlowStateException(String message) {
        super(message);
    }
    
    public InvalidFlowStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
