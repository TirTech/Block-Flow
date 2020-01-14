package ca.blockflow.blocks;

import ca.blockflow.exceptions.BlockException;
import ca.blockflow.exceptions.ExpressionException;
import ca.blockflow.logic.Expression;
import ca.blockflow.logic.SupportedTypes;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Variable;

public class IfBlock extends Block {
    private static final long serialVersionUID = 1L;
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
    
    public Block call(FlowState state) throws BlockException, ExpressionException {
        //needs to get expression from view to evaluate
        Block subBlock;
        Variable boolVar = expression.evaluate(state);
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
    
    public Expression getExpression() {
        return expression;
    }
    
    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}//end ifblock class
