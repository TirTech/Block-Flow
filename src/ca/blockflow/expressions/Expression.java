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

    public Expression() {
//        flowState.getCurrentBlock();
//        System.out.println(operation.toString());
//        this.operation = (a, b) -> {return operation(a, b)};
    }
    
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
    
    public void setValue(Variable v) {
        value = v;
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
    
    public Variable evaluateExpression() throws ExceptionHandler {
        if (operation == Operation.NO_OP) {
            if (value != null) {
                String name = value.getName();
                FlowState flowState = AppModel.getInstance().getEngine().getFlowState();
                Variable v = flowState != null ? flowState.getVar(name) : null;
                if (v != null) {
                    return v != null ? v : value;
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
            o = value.getName() == "" ? value.getValue() : AppModel.getInstance().getEngine().getFlowState().getVar(value.getName()).getValue();
        }
        return "< " + operation + "(" + operandA + ", " + operandB + ") => " + o + " />";
    }
}
