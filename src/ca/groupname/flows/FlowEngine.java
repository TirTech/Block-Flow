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
				// Set state to running
				workingState.setStatus(FlowStatus.RUNNING);
				// ...and keep running till we are done
				// TODO Need to test pause and resume (tested stop)
				while (workingState.getStatus() == FlowStatus.RUNNING){
					workingState.getCurrentBlock().call(workingState);
					updateValue(workingState);
				}
				return workingState;
			}
		};
	}
	
	/**
	 * Sets the starting state for the service
	 * @param state the starting state
	 */
	public void setFlowState(FlowState state) {
		this.flowState.set(state);
	}
	
	/**
	 * Gets the starting state of the service
	 * @return the starting state
	 */
	public FlowState getFlowState() {
		return flowState.get();
	}
	
	/**
	 * Get the starting state property
	 * @return the starting state property
	 */
	public SimpleObjectProperty<FlowState> flowStateProperty() {
		return flowState;
	}
}
