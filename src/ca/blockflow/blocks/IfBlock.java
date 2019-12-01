package ca.blockflow.blocks;

import ca.blockflow.exceptions.BlockException;
import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.expressions.Expression;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Variable;

public class IfBlock extends Block {
    
    /**
     * The expression given to assignment block from the BlockView.
     * This expression is evaluated to be either true or false and
     * the corresponding block is executed
     */
    private Expression expression;
    
    /**
     * The next block to be executed. Default value is not set (to be set by the BlockView)
     */
    private Block trueBlock;
    private Block falseBlock;
    
    
    public IfBlock() {
        //empty constructor
    }
    
    public Block call(FlowState state) throws BlockException, ExceptionHandler {
        //needs to get expression from view to evaluate
        Block subBlock = null;
        Variable boolVar = expression.evaluateExpression();
        SupportedTypes t = boolVar.getType();
        
        if (t != SupportedTypes.BOOLEAN) {
            throw new BlockException("If Block: expression evaluated to a type other than boolean");
        } else {
            //cast to boolean to get var value as bool
            if (((Variable<Boolean>) boolVar).getValue()) {
                //select if path condition is true
                //state.setCurrentBlock();
                subBlock = trueBlock;
            } else {
                subBlock = falseBlock;
                //select path if condition is false
            }
        }
        
        return subBlock;
    }//end call
    
    @Override
    public String[] getSubblockNames() {
        return new String[]{"True", "False"};
    }
    
    @Override
    public void setSubblock(String name, Block block) {
        switch (name) {
            case "True":
                this.trueBlock = block;
                break;
            case "False":
                this.falseBlock = block;
                break;
        }
    }
    
}//end ifblock class
