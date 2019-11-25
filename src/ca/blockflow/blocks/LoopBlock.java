package ca.blockflow.blocks;

import ca.blockflow.exceptions.BlockException;
import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.expressions.Expression;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Variable;

public class LoopBlock extends Block{
    private Expression expression;
    private Block subBlock;
    
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
    
    public void setExpression(Expression newExpression){this.expression = newExpression;}
    
    public void setSubBlock(Block block) {this.subBlock = block;}
    
}
