package src.ca.groupname.flows;

import src.ca.groupname.main.Saveable;

/**
 * Base class for all blocks. Subclasses must implement the <code>call({@link FlowState} state)</code> method.
 */
public abstract class Block implements Saveable {
	
	public Block() {}
	
	/**
	 *  <code>call</code> performs actions for this block according to state. All changes should be stored back into state
	 * @param state the program state.
	 */
	public abstract void call(FlowState state);
}
