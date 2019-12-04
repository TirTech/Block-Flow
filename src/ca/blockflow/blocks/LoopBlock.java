package ca.blockflow.blocks;

import ca.blockflow.exceptions.BlockException;
import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.expressions.Expression;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Variable;

public class LoopBlock extends Block{
    /** The expression that indicates if the for loop should loop again */
    private Expression expression;
    
    /** The next block to be run inside the for loop */
    private Block subBlock;
    
    /**
     * Performs loop actions according to state. All changes should be stored back into state
     * @param state the program state.
     * @return returns the next block to be executed
     * @throws BlockException Throws exception if the evaluated expression is not of type Boolean
     * @throws ExceptionHandler the exception handler for evaluating expressions
     */
    @Override
    public Block call(FlowState state) throws BlockException, ExceptionHandler {
        Block nextBlock = null;
        Variable boolVar = expression.evaluateExpression();
        SupportedTypes t = boolVar.getType();
    
        if (t != SupportedTypes.BOOLEAN) {
            throw new BlockException("Loop Block: expression evaluated to a type other than boolean");
        } else {
            //cast to boolean to get var value as bool
            if (((Variable<Boolean>) boolVar).getValue()) { //  --- Set Loop if condition is true   ---
                setNextLinkedBlock(this);
                nextBlock = subBlock;
            } else {//  --- Continue past loop if condition is false  ---
                nextBlock = null;
                setNextLinkedBlock(null);
            }
        }
    
        return nextBlock;
    }
    
    /**
     * Sets the expression for the LoopBlock
     * @param newExpression The new expression to be set
     */
    public void setExpression(Expression newExpression){this.expression = newExpression;}
    
    /**
     * Sets the next block to be run inside the LoopBlock
     * @param block the block to be executed next
     */
    public void setSubBlock(Block block) {this.subBlock = block;}
    
}
