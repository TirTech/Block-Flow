package ca.blockflow.blocks;

import ca.blockflow.exceptions.BlockException;
import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.expressions.Expression;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Variable;


public class AssignmentBlock extends Block {
    
    /**
     * The expression given to assignment block from the BlockView
     */
    private Expression expression;
    
    /**
     * The variable that will be assigned a new value
     */
    private Variable input;
    
    /**
     * The next block to be executed. Default value is not set (to be set by the BlockView)
     */
    private Block nextBlock;
    
    /**
     * AssignmentBlock constructor for initialization
     */
    public AssignmentBlock(){
        //constructor
    }
    
    @Override
    public Block call(FlowState state) throws BlockException, ExceptionHandler {
        //get list of variables from state and present in dropdown for assignment
        //ok so state.getVar() returns single item
    
        // The variable that is to be assigned a value
        Variable var;
    
        //the value var will be set to once the expression is evaluated
        Variable expVar;
    
        var = state.getVar(input.getName());
    
        //evaluates the given expression to a value and sets it to the expression variable value
        expVar = expression.evaluateExpression();
        
        //type check intended/desired vs given
        if(var.getType() == expVar.getType()){
            var.setValue(expVar.getValue());
        }
        else{
    
            throw new BlockException("Assignment block error - type mismatch");
        }//end else
        //the next block will be decided by flowstate (return null)
        return null;
    }
    
    /**
     * Gets the input (variable) that in going to be assigned a value.
     * @return input
     */
    public Variable getInput(){
        return this.input;
    }
    
    /**
     * Sets the input (variable) of the assignment block.
     * @param input the variable to be assigned a new value
     */
    public void setInput(Variable input){
        this.input = input;
    }
    
    /**
     * Sets the expression that will become the new value of 'input'
     * @param exp the expression to be evaluated at runtime
     */
    public void setExpression(Expression exp){
        this.expression = exp;
    }
    
    /**
     * Gets the next block to be executed.
     * @return nextBlock the next block to be executed
     */
    public Block getNextBlock() {
        return this.nextBlock;
    }
    
    /**
     * Sets the next block to be executed after this block
     * @param b the block being set to the next block to be executed
     */
    public void setNextBlock(Block b) {
        this.nextBlock = b;
    }
    
    public Expression getExpression() {
        return expression;
    }
}
