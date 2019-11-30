package ca.blockflow.exceptions;

public class MissingFlowStateException extends ExceptionHandler {
    public MissingFlowStateException() {
        super("No state was set");
    }
    
//    public MissingFlowStateException(String message) {
//        super(message);
//    }
//
//    public MissingFlowStateException(String message, Throwable cause) {
//        super(message, cause);
//    }
}
