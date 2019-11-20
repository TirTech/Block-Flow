package ca.groupname.flows;

import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service that performs the execution of a flowchart from some state. This service prevents UI unresponsiveness.
 */
public class FlowEngine extends Service<FlowState> {
    /**
     * The state to start from
     */
    private SimpleObjectProperty<FlowState> flowState = new SimpleObjectProperty<>(null);
    
    @Override
    protected Task<FlowState> createTask() {
        // State to work off of
        final FlowState workingState = flowState.get();
        return new Task<FlowState>() {
            @Override
            protected FlowState call() throws Exception {
                // You can't run a stopped flow
                if (workingState.getStatus() == FlowStatus.STOPPED) {
                    throw new InvalidFlowStateException("Flow state was STOPPED. Flows cannot be executed in the stop state.");
                }
                // Keep running till state changes
                while (workingState.getStatus().canExecuteFrom()) {
                    // Call the block to do work. It will advance the flow though state's currentBlock
                    workingState.getCurrentBlock().call(workingState);
                    // Keeps the tasks resultant value up to date
                    updateValue(workingState);
                    if (workingState.getStatus() == FlowStatus.STEPPING) {
                        workingState.setStatus(FlowStatus.PAUSED);
                    }
                }
                return workingState;
            }
        };
    }
    
    /**
     * Gets the starting state of the service
     * @return the starting state
     */
    public FlowState getFlowState() {
        return flowState.get();
    }
    
    /**
     * Sets the starting state for the service.
     * @param state the starting state
     */
    public void setFlowState(FlowState state) {
        this.flowState.set(state);
    }
    
    /**
     * Get the starting state property
     * @return the starting state property
     */
    public SimpleObjectProperty<FlowState> flowStateProperty() {
        return flowState;
    }
    
    public void play() throws MissingFlowStateException, InvalidFlowStateException {
        if (getFlowState() == null) {
            throw new MissingFlowStateException();
        } else if (getFlowState().getStatus() == FlowStatus.STOPPED) {
            throw new InvalidFlowStateException();
        }
        getFlowState().setStatus(FlowStatus.RUNNING);
        restart();
    }
    
    @Override
    public void restart() {
        if (getFlowState() != null) {
            super.restart();
        }
    }
    
    public void pause() throws MissingFlowStateException {
        if (getFlowState() == null) {
            throw new MissingFlowStateException();
        }
        getFlowState().setStatus(FlowStatus.PAUSED);
    }
    
    public void step() throws MissingFlowStateException {
        if (getFlowState() == null) {
            throw new MissingFlowStateException();
        } else if (getFlowState().getStatus() == FlowStatus.PAUSED) {
            getFlowState().setStatus(FlowStatus.STEPPING);
            restart();
        }
    }
}
