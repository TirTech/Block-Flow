package ca.blockflow.expressions;

import ca.blockflow.exceptions.ExpressionException;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Operation;
import ca.blockflow.logic.Variable;

public class Expression {

    private Expression operandA;
    private Operation operation;
    private Expression operandB;
    private Variable value;
    
    private FlowState flowState;

    public Expression() {
//        flowState.getCurrentBlock();
//        System.out.println(operation.toString());
//        this.operation = (a, b) -> {return operation(a, b)};
    }
    
    public void setFlowState(FlowState flowState) {
        this.flowState = flowState;
    }
    
    public Expression simpleAssignExpression(Variable v) {
        Expression e = new Expression();
        e.setValue(v);
        e.setOperation(Operation.NO_OP);
        initExpression(e, Operation.NO_OP, null);
        return e;
    }
    
    public boolean initExpression(Expression a, Operation o, Expression b) {
        try {
            if (a == null && value == null) {
                throw new ExpressionException();
            }
            else if (value != null){
                ///////////////////////////////////
            }
            else if (o == null) {
                o = Operation.NO_OP;
            }
            this.operandA = a;
            this.operation = o;
            this.operandB = b;
            return true;
        }
        catch (ExpressionException e) {
            e.creationException("Unable to create an expression for:\n\tA\t-\t<" +
                                a + ">\n\tOP\t-\t<" +
                                o + ">\n\tB\t-\t<" +
                                b + ">");
            return false;
        }
    }

    public Variable evaluateExpression() {
        try {
            if (operation == Operation.NO_OP) {
                if (value != null) {
                    return value;
                } else {
                    throw new ExpressionException();
                }
            }
            else if (value != null && operandB == null) {
                return value;
            }
            else {
                return operation.perform(operandA, operandB);
            }
        }
        catch (ExpressionException e) {
            e.unassignedExpression("Tried to evaluate expression: " + operation + "(" + operandA + ", " + operandB + ")");
            return null;
        }
    }
    
    public void setAttrs(Expression opA, Operation op, Expression opB) {
        this.operandA = opA;
        this.operation = op;
        this.operandB = opB;
    }
    
    public void setExpAttrs(Expression e, Expression opA, Operation op, Expression opB) {
        e.operandA = opA;
        e.operation = op;
        e.operandB = opB;
    }
    
    public void setOperandA(Expression operandA) {
        this.operandA = operandA;
    }
    
    public void setOperation(Operation operation) {
        this.operation = operation;
    }
    
    public void setOperandB(Expression operandB) {
        this.operandB = operandB;
    }
    
    public Variable getValue() {
        return value;
    }
    
    public Variable setValue(Variable v) {
        return value = v;
    }
    
    public String toString() {
        Object o = ((value == null)? null : value.getValue());
        return "< " + operation + "(" + operandA + ", " + operandB + ") => " + o + " />";
    }
}
