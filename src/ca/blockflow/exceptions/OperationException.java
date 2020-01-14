package ca.blockflow.exceptions;

public class OperationException extends Exception {
    public OperationException(String message) {
        super(message);
    }
    
    public OperationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public OperationException(Throwable cause) {
        super(cause);
    }
}
