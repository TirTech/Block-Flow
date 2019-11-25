package ca.blockflow.blocks;

import ca.blockflow.exceptions.BlockException;
import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.expressions.Expression;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Variable;

public abstract class ForBlock extends Block {
    
    private Expression expression;
    private Block subBlock;
    
    /**
     * The variable that will be increase
     */
    private Variable indexVar;
    private Variable conditionVar;
    
    @Override
    public Block call(FlowState state) throws BlockException, ExceptionHandler {
        Block nextBlock = null;
        Variable boolVar = expression.evaluateExpression();
        SupportedTypes t = boolVar.getType();
        
        if (t != SupportedTypes.BOOLEAN) {
            throw new BlockException("Loop Block: expression evaluated to a type other than boolean");
        } else {
            Variable index = state.getVar(indexVar.getName());
            Variable condition = state.getVar(conditionVar.getName());
            
            SupportedTypes indexT = index.getType();
            SupportedTypes condT = condition.getType();
            
            if(indexT != SupportedTypes.INT || indexT != SupportedTypes.DOUBLE
                || condT != SupportedTypes.INT || condT != SupportedTypes.DOUBLE){
                throw new BlockException("Loop Block: expression evaluated to a type other than boolean");
            }
            
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
     * Gets the index (variable)
     * @return Returns the index variable of the loop
     */
    public Variable getIndexVar(){
        return this.indexVar;
    }
    
    /**
     * Sets the index (variable) of the ForLoopBlock.
     * @param newIndex the variable to be assigned a new value
     */
    public void setIndexVar(Variable newIndex){
        this.indexVar = newIndex;
    }
    
    /**
     * Gets the condition (variable)
     * @return Returns the condition variable of the loop
     */
    public Variable getConditionVar(){
        return this.conditionVar;
    }
    
    /**
     * Sets the condition (variable) of the ForLoopBlock.
     * @param newCondition the variable to be assigned a new value
     */
    public void setConditionVar(Variable newCondition){
        this.conditionVar = newCondition;
    }
    
    /**
     * Sets the expression for this loop block
     * @param newExpression The new expression to be set
     */
    public void setExpression(Expression newExpression){this.expression = newExpression;}
    
    /**
     * Sets the next block to be run inside the for loop block
     * @param block the block to be executed next
     */
    public void setSubBlock(Block block) {this.subBlock = block;}
}
