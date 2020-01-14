package ca.blockflow.blocks;

import ca.blockflow.exceptions.BlockException;
import ca.blockflow.exceptions.ExpressionException;
import ca.blockflow.logic.Expression;
import ca.blockflow.logic.SupportedTypes;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Variable;

public class WhileLoopBlock extends Block{
    
    private static final long serialVersionUID = 1L;
    /** The expression that indicates if the for loop should loop again */
    private Expression expression;
    
    /** The next block to be run inside the for loop */
    private Block subBlock;
    
    /**
     * Performs loop actions according to state. All changes should be stored back into state
     * @param state the program state.
     * @return returns the next block to be executed
     * @throws BlockException Throws exception if the evaluated expression is not of type Boolean
     */
    @Override
    public Block call(FlowState state) throws BlockException, ExpressionException {
        Block nextBlock = null;
        Variable boolVar = expression.evaluate(state);
        SupportedTypes t = boolVar.getType();
    
        if (t != SupportedTypes.BOOLEAN) {
            throw new BlockException("Loop Block: expression evaluated to a type other than boolean");
        } else {
            //  --- Set Loop if condition is true   ---
            if (((Variable<Boolean>) boolVar).getValue()) nextBlock = subBlock;
        }
    
        return nextBlock;
    }
    
    /**
     * Sets the expression for the LoopBlock
     * @param newExpression The new expression to be set
     */
    public void setExpression(Expression newExpression){ this.expression = newExpression; }
    
    @Override
    public String[] getLoopingSubblockNames() { return new String[]{"Body"};}
    
    /**
     * Sets the next block to be run inside the LoopBlock
     * @param block the block to be executed next
     */
    @Override
    public void setSubblock(String name, Block block) {
        if ("Body".equals(name)) {
            this.subBlock = block;
        }
    }
    
    public Expression getExpression() {
        return expression;
    }
}
