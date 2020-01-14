package ca.blockflow.logic;

import ca.blockflow.exceptions.ExpressionException;
import ca.blockflow.exceptions.OperationException;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.operations.Operation;
import ca.blockflow.serialization.Saveable;

public class Expression implements Saveable {
    
    private static final long serialVersionUID = 1L;
    private Expression exprLeft;
    private Operation operation;
    private Expression exprRight;
    private Variable value;
    
    public Expression(Expression exprLeft, Operation operation, Expression exprRight) {
        this.exprLeft = exprLeft;
        this.operation = operation;
        this.exprRight = exprRight;
    }
    
    public Expression(Variable value) {
        this.value = value;
    }
    
    public Expression(Object value, SupportedTypes type) {
        this.value = new Variable("", type, value);
    }
    
    public Expression getExprLeft() {
        return exprLeft;
    }
    
    public void setExprLeft(Expression exprLeft) {
        this.exprLeft = exprLeft;
    }
    
    public Operation getOperation() {
        return operation;
    }
    
    public void setOperation(Operation operation) {
        this.operation = operation;
    }
    
    public Expression getExprRight() {
        return exprRight;
    }
    
    public void setExprRight(Expression exprRight) {
        this.exprRight = exprRight;
    }
    
    public Variable getValue() {
        return value;
    }
    
    public void setValue(Variable value) {
        this.value = value;
    }
    
    public Variable evaluate(FlowState state) throws ExpressionException {
        if (value != null)
            return (value.getName() == null || value.getName().trim().isEmpty()) ? value : state.getVar(value.getName());
        try {
            return operation.perform(exprLeft != null ? exprLeft.evaluate(state) : null,
                                     exprRight != null ? exprRight.evaluate(state) : null);
        } catch (OperationException e) {
            throw new ExpressionException("Error evaluating expression: Operation Failed",e);
        }
    }
}
