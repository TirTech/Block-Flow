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
	SimpleObjectProperty<FlowState> flowState = new SimpleObjectProperty<>(null);
    
    @Override
    protected Task<FlowState> createTask() {
        // State to work off of
        final FlowState workingState = flowState.get();
        return new Task<FlowState>() {
            @Override
            protected FlowState call() throws Exception {
                // You can't run a stopped flow
                if (workingState.getStatus() == FlowStatus.STOPPED) {
                    throw new InvalidFlowStateException();
                }
    
                // Keep running till state changes
                while (workingState.getStatus().canExecuteFrom()) {
                    // If we haven't hit a breakpoint prior and this block has one, breakpoint and pause
                    if (! workingState.getBreakpointed() && workingState.getCurrentBlock().getBreakpoint()) {
                        workingState.setBreakpointed(true);
                        workingState.setStatus(FlowStatus.PAUSED);
                    } else {
                        // Clear the breakpointed flag
                        workingState.setBreakpointed(false);
                        // Call the block to do work. It will advance the flow though state's currentBlock
                        workingState.getCurrentBlock().call(workingState);
                    }
                    // Keeps the tasks resultant value up to date
                    updateValue(workingState);
                    // If we stepped, then pause right away
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
    
    /**
     * Execute the current flow
     * @throws MissingFlowStateException
     * @throws InvalidFlowStateException
     */
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
    
    /**
     * Pause the current flow. The flow may advance several blocks before the pause takes effect
     * @throws MissingFlowStateException
     */
    public void pause() throws MissingFlowStateException {
        if (getFlowState() == null) {
            throw new MissingFlowStateException();
        }
        getFlowState().setStatus(FlowStatus.PAUSED);
    }
    
    /**
     * Step through the current flow. The flow will advance a single block before pausing again
     * @throws MissingFlowStateException
     */
    public void step() throws MissingFlowStateException {
        if (getFlowState() == null) {
            throw new MissingFlowStateException();
        } else if (getFlowState().getStatus() == FlowStatus.PAUSED) {
            getFlowState().setStatus(FlowStatus.STEPPING);
            restart();
        }
    }
}
