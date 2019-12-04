package ca.blockflow.blocks;

import ca.blockflow.exceptions.BlockException;
import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.expressions.Expression;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Variable;

public class ForLoopBlock extends Block {
    /** The expression that indicates if the for loop should loop again */
    private Expression expression;
    
    /** The next block to be run inside the for loop */
    private Block subBlock;
    
    /** The index increments of the index */
    private Variable<Double> increments;
    /** The index of the for loop */
    private Variable<Double> indexVar;
    /** The condition for which the index can't pass */
    private Variable<Double> conditionVar;
    
    
    /**
     * Performs for loop actions according to state. All changes should be stored back into state
     * @param state the program state.
     * @return the next block to be executed
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
            
            if (state.getVar(hashCode()+"") == null) {//    --- Check if previously executed in order to increment index    ---
                state.addVars(new Variable(hashCode()+"", SupportedTypes.BOOLEAN, true));
                indexVar.setValue(indexVar.getValue() + increments.getValue());
            }
            
            if (((Variable<Boolean>) boolVar).getValue()) { //  --- Set Loop if condition is true   ---
                setNextLinkedBlock(this);
                nextBlock = subBlock;
            } else {//  --- Continue past loop if condition is false  ---
                nextBlock = null;
                setNextLinkedBlock(null);
                state.removeVars(hashCode()+"");
            }
        }
        
        return nextBlock;
    }
    
    /**
     * Gets the index (variable)
     * @return Returns the index variable of the loop
     */
    public Variable<Double> getIndexVar(){ return this.indexVar; }
    
    /**
     * Sets the index variable of the ForLoopBlock.
     * Throws BlockException when newIndex is not Double
     * @param newIndex the new double index variable
     * @throws BlockException Throws exception when the newIndex Variable is not of type double
     */
    public void setIndexVar(Variable<Double> newIndex) throws BlockException {
        if(newIndex.getType() == SupportedTypes.DOUBLE) this.indexVar = newIndex;
        else throw new BlockException("ForLoopBlock: Index Variable not set to a Double");
    }
    
    /**
     * Gets the condition variable
     * @return Returns the condition variable of the loop
     */
    public Variable<Double> getConditionVar(){ return this.conditionVar; }
    
    /**
     * Sets the condition variable of the ForLoopBlock.
     * @param newCondition the new condition of the ForLoopBlock
     * @throws BlockException Throws exception if newCondition is not a Double
     */
    public void setConditionVar(Variable<Double> newCondition) throws BlockException {
        if(newCondition.getType() == SupportedTypes.DOUBLE) this.conditionVar = newCondition;
        else throw new BlockException("ForLoopBlock: Condition Variable not set to a Double");
    }
    
    
    /**
     * Gets the increments amount of the For Loop
     * @return Returns the increments Variable of the ForLoopBlock
     */
    public Variable<Double> getIncrements(){ return this.increments; }
    
    /**
     * Sets a new increments value for the ForLoopBlock
     * @param newIncrements The new increments for the ForLoopBlock
     * @throws BlockException Throws exception when the newIncrements is not a Double
     */
    public void setIncrements(Variable<Double> newIncrements) throws BlockException {
        if(newIncrements.getType() == SupportedTypes.DOUBLE) this.increments = newIncrements;
        else throw new BlockException("ForLoopBlock: Increments Variable not set to a Double");
    }
    
    
    /**
     * Gets the expression of the ForLoopBlock
     * @return Returns the expression of the ForLoopBlock
     */
    public Expression getExpression(){ return this.expression; }
    
    /**
     * Sets the expression for the ForLoopBlock
     * @param newExpression The new expression to be set
     */
    public void setExpression(Expression newExpression){ this.expression = newExpression; }
    
    
    /**
     * Sets the next block to be run within the ForLoopBlock
     * @param block the block to be executed next
     */
    public void setSubBlock(Block block) {this.subBlock = block;}
}
