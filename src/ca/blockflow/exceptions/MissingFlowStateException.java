package ca.blockflow.exceptions;

public class MissingFlowStateException extends Exception {
    public MissingFlowStateException() {
        super("No state was set");
    }
}
