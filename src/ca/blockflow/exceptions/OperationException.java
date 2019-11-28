package ca.blockflow.exceptions;

public class OperationException extends Exception {
    
    private final boolean PRINT_STACK_TRACES = false;
    private final String BORDER = "############################################################";
    
    public OperationException() {
    
    }
    
}
