package ca.blockflow.blocks;

import ca.blockflow.exceptions.BlockException;
import ca.blockflow.expressions.Expression;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Variable;

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
        //empty constructor
    }
    
    public void call(FlowState state) throws BlockException {
        //needs to get expression from view to evaluate
        Variable boolVar = expression.evaluateExpression();
        SupportedTypes t = boolVar.getType();
        //if(t.determineType(t.getTypeClass()) == true){
        if (t != SupportedTypes.BOOLEAN) {
            throw new BlockException("If Block: expression evaluated to a type other than boolean");
        } else {
            //cast to boolean to get var value as bool
            if (((Variable<Boolean>) boolVar).getValue()) {
                //select if path condition is true
                //state.setCurrentBlock();
            } else {
                //select path if condition is false
            }
        }
    
    
    }//end call
    
    
}//end ifblock class
