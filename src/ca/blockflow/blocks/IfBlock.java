package ca.blockflow.blocks;

import ca.blockflow.exceptions.BlockException;
import ca.blockflow.expressions.Expression;
import ca.blockflow.flows.FlowState;

public class IfBlock {
    
    /**
     * The expression given to assignment block from the BlockView.
     * This expression is evaluated to be either true or false and
     * the corresponding block is executed
     */
    private Expression expression;
    
    /**
     * The next block to be executed. Default value is not set (to be set by the BlockView)
     */
    private Block nextBlock;
    
    
    public IfBlock() {
        //empty constructor }
        
        
    }
    
    public void call(FlowState state) throws BlockException {
        //needs to get one or two states from the flowstate to compare
        
        
    }
    
    
}//end ifblock class
