package ca.blockflow.expressions;

import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.exceptions.ExpressionException;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Operation;
import ca.blockflow.logic.Variable;
import ca.blockflow.models.AppModel;
import ca.blockflow.serialization.Saveable;

public class Expression implements Saveable {
    
    private static final long serialVersionUID = 1L;
    private Expression operandA;
    private Operation operation;
    private Expression operandB;
    private Variable value;
    
    private Expression() {}
    
    public Expression(Expression opA, Operation op, Expression opB) {
        setAttrs(opA, op, opB);
    }
    
    public Expression(Variable v) {
        this.setValue(v);
        this.setOperation(Operation.NO_OP);
        this.setOperandA(null);
        this.setOperandB(null);
    }
    
    public Expression simpleAssignExpression(Variable v) throws ExceptionHandler {
        Expression e = new Expression();
        e.setValue(v);
        initExpression(e, Operation.NO_OP, null);
        return e;
    }
    
    public Operation getOperation() {
        return operation;
    }
    
    private void setOperation(Operation operation) {
        this.operation = operation;
    }
    
    public Expression getOperandA() {
        return operandA;
    }
    
    private void setOperandA(Expression operandA) {
        this.operandA = operandA;
    }
    
    public Expression getOperandB() {
        return operandB;
    }
    
    private void setOperandB(Expression operandB) {
        this.operandB = operandB;
    }
    
    private void initExpression(Expression a, Operation o, Expression b) throws ExceptionHandler {
        if (a == null && value == null) {
            ExpressionException.creationException("Unable to create an expression for:\n\tA\t-\t<" +
                                                  null + ">\n\tOP\t-\t<" +
                                                  o + ">\n\tB\t-\t<" +
                                                  b + ">");
        } else if (value == null && o == null) {
            o = Operation.NO_OP;
        }
        this.operandA = a;
        this.operation = o;
        this.operandB = b;
    }
    
    private void setValue(Variable v) {
        value = v;
    }
    
    private void setAttrs(Expression opA, Operation op, Expression opB) {
        this.operandA = opA;
        this.operation = op;
        this.operandB = opB;
    }
    
    public Variable getValue() {
        return value;
    }
    
    public Variable evaluateExpression() throws ExceptionHandler {
        if (operation == Operation.NO_OP) {
            if (value != null) {
                String name = value.getName();
                FlowState flowState = AppModel.getInstance().getEngine().getFlowState();
                Variable v = flowState != null ? flowState.getVar(name) : null;
                if (v != null) {
                    return v;
                } else {
                    return value;
                }
            } else {
                ExpressionException.unassignedExpression("Tried to evaluate expression: " + operation + "(" + operandA + ", " + operandB + ")");
                return null;
            }
        } else if (value != null && operandB == null) {
            return value;
        } else {
            return operation.perform(operandA, operandB);
        }
    }
    
    public String toString() {
        Object o = null;
        if (value != null) {
            o = value.getName().equals("") ? value.getValue() : AppModel.getInstance().getEngine().getFlowState().getVar(value.getName()).getValue();
        }
        return "< " + operation + "(" + operandA + ", " + operandB + ") => " + o + " />";
    }
}
