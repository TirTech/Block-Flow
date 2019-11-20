package ca.groupname.flows;

public class MissingFlowStateException extends Exception {
    public MissingFlowStateException() {
        super("No state was set");
    }
    
    public MissingFlowStateException(String message) {
        super(message);
    }
    
    public MissingFlowStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
