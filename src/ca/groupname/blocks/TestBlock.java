package ca.groupname.blocks;

import ca.groupname.flows.Block;
import ca.groupname.flows.FlowState;
import ca.groupname.flows.FlowStatus;

/**
 * An example block. Must implement call()
 */
public class TestBlock extends Block {
    
    @Override
    public void call(FlowState state) {
        state.getVar("teststring").setValue(state.getVar("teststring").getValue() + "\n> " + System.currentTimeMillis());
        if (((String) state.getVar("teststring").getValue()).chars().filter(ch -> ch == '>').count() > 500) {
            state.setStatus(FlowStatus.STOPPED);
        }
        
    }
}
