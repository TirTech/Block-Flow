package ca.blockflow.blocks;

import ca.blockflow.exceptions.BlockException;
import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.flows.FlowState;
import ca.blockflow.main.Saveable;

/**
 * Base class for all blocks. Subclasses must implement the <code>call({@link FlowState} state)</code> method.
 */
public abstract class Block implements Saveable {
    
    private boolean breakpoint;
    private Block nextLinkedBlock;
    
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
     * Called from the FlowEngine. Wraps {@link #call(FlowState)} and performs pre and post operations
     * @param state the program FlowState
     */
    public void callBlock(FlowState state) throws BlockException, ExceptionHandler {
        Block nextBlock = call(state);
        state.setCurrentBlock(nextBlock != null ? nextBlock : nextLinkedBlock);
    }
    
    /**
     * <code>call</code> performs actions for this block according to state. All changes should be stored back into state
     * @param state the program state.
     */
    public abstract Block call(FlowState state)throws BlockException, ExceptionHandler;
    
    /**
     * Returns the names of all subblocks that this block will have access to
     * @return subblock names
     */
    public String[] getSubblockNames() {
        return new String[]{};
    }
    
    /**
     * Returns the names of all subblocks that this block will have access to that will need to call this block
     * (looping blocks)
     * @return looping subblock names
     */
    public String[] getLoopingSubblockNames() {
        return new String[]{};
    }
    
    /**
     * Accepts named blocks (in accordance with <code>setSubblockNames()</code>) allowing blocks to handle "branches"
     * @param name  the blocks name
     * @param block the block from the ui for the given name
     */
    public void setSubblock(String name, Block block) {
        //Do nothing
    }
    
    public void setNextLinkedBlock(Block nextLinkedBlock) {
        this.nextLinkedBlock = nextLinkedBlock;
    }
}
