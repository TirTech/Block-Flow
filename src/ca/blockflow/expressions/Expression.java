package ca.blockflow.expressions;

import ca.blockflow.exceptions.ExceptionHandler;
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
    
    public Expression simpleAssignExpression(Variable v) throws ExceptionHandler {
        Expression e = new Expression();
        e.setValue(v);
//        e.setOperation(Operation.NO_OP);
        initExpression(e, Operation.NO_OP, null);
        return e;
    }
    
    public boolean initExpression(Expression a, Operation o, Expression b) throws ExceptionHandler {
        if (a == null && value == null) {
            ExpressionException.creationException("Unable to create an expression for:\n\tA\t-\t<" +
                                                  a + ">\n\tOP\t-\t<" +
                                                  o + ">\n\tB\t-\t<" +
                                                  b + ">");
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

    public Variable evaluateExpression() throws ExceptionHandler {
        if (operation == Operation.NO_OP) {
            if (value != null) {
                String name  = value.getName();
                Variable v = this.flowState.getVar(name);
                if (v != null) {
                    return v != null ? v : value;
                }
                else {
                    return value;
                }
            } else {
                ExpressionException.unassignedExpression("Tried to evaluate expression: " + operation + "(" + operandA + ", " + operandB + ")");
                return null;
            }
        }
        else if (value != null && operandB == null) {
            return value;
        }
        else {
            return operation.perform(operandA, operandB);
        }
    }
    
    public void setAttrs(Expression opA, Operation op, Expression opB) {
        this.operandA = opA;
        this.operation = op;
        this.operandB = opB;
    }
    
//    public void setExpAttrs(Expression e, Expression opA, Operation op, Expression opB) {
//        e.operandA = opA;
//        e.operation = op;
//        e.operandB = opB;
//    }
    
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
