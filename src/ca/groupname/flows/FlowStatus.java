package ca.groupname.flows;

/**
 * Represents the possible states of a flowchart execution
 */
public enum FlowStatus {
    READY(true),
    RUNNING(true),
    PAUSED(false),
    STEPPING(true),
    STOPPED(false);
    
    private final boolean canExecuteFrom;
    
    FlowStatus(boolean canExecuteFrom) {
        this.canExecuteFrom = canExecuteFrom;
    }
    
    public boolean canExecuteFrom() {
        return canExecuteFrom;
    }
}
