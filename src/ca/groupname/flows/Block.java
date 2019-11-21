package ca.groupname.flows;

import ca.groupname.main.Saveable;

/**
 * Base class for all blocks. Subclasses must implement the <code>call({@link FlowState} state)</code> method.
 */
public abstract class Block implements Saveable {
    
    private boolean breakpoint;
    
    public Block() {}
    
    /**
     * Gets whether the block has a breakpoint
     * @return whether the block has a breakpoint
     */
    public boolean getBreakpoint() {
        return breakpoint;
    }
    
    /**
     * Set whether the block has a breakpoint
     * @param breakpoint whether to break on this block
     */
    public void setBreakpoint(boolean breakpoint) {
        this.breakpoint = breakpoint;
    }
    
    /**
     * <code>call</code> performs actions for this block according to state. All changes should be stored back into state
     * @param state the program state.
     */
    public abstract void call(FlowState state);
}
