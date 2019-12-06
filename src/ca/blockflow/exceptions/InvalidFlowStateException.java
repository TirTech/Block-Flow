package ca.blockflow.exceptions;

public class InvalidFlowStateException extends ExceptionHandler {
    public InvalidFlowStateException() {
        super("Flow state was STOPPED. Flows cannot be executed in the stop state.");
    }
    
//    public InvalidFlowStateException(String message) {
//        super(message);
//    }
//
//    public InvalidFlowStateException(String message, Throwable cause) {
//        super(message, cause);
//    }
}
