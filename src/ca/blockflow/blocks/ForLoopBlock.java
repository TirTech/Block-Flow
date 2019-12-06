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
    private Variable increments;
    /** The index of the for loop */
    private Variable indexVar;
    
    
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
        Variable indexedVar = state.getVar(indexVar.getName());
        
        if (t != SupportedTypes.BOOLEAN) {
            throw new BlockException("Loop Block: expression evaluated to a type other than boolean");
        } else {
    
            //    --- Check if previously executed in order to increment index    ---
            if (state.getVar(hashCode()+"") == null) state.addVars(new Variable(hashCode()+"", SupportedTypes.BOOLEAN, true));
            else {
                Number indexVal = (Number) indexedVar.getValue();
                Number incVal = (Number) increments.getValue();
                indexedVar.setValue(indexVal.doubleValue() + incVal.doubleValue());
            }
    
            //  --- Set Loop if condition is true   ---
            if (((Variable<Boolean>) boolVar).getValue()) nextBlock = subBlock;
            //  --- Continue past loop if condition is false  ---
            else state.removeVars(hashCode()+"");
        }
        
        return nextBlock;
    }
    
    /**
     * Gets the index (variable)
     * @return Returns the index variable of the loop
     */
    public Variable<Double> getIndexVar(){ return this.indexVar; }
    
    /**
     * Sets the index variable of the ForLoopBlock
     * @param newIndex the new double index variable
     */
    public void setIndexVar(Variable newIndex) { this.indexVar = newIndex; }
    
    /**
     * Gets the increments amount of the For Loop
     * @return Returns the increments Variable of the ForLoopBlock
     */
    public Variable<Double> getIncrements(){ return this.increments; }
    
    /**
     * Sets a new increments value for the ForLoopBlock
     * @param newIncrements The new increments for the ForLoopBlock
     */
    public void setIncrements(Variable<Double> newIncrements){ this.increments = newIncrements; }
    
    
    
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
    
    @Override
    public String[] getLoopingSubblockNames() {
        return new String[]{"Body"};
    }
    
    /**
     * Sets the next block to be run within the ForLoopBlock
     * @param block the block to be executed next
     */
    @Override
    public void setSubblock(String name, Block block) {
        if ("Body".equals(name)) {
            this.subBlock = block;
        }
    }
}
