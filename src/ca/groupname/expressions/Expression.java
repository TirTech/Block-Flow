package ca.groupname.expressions;

import ca.groupname.logic.Operation;
import ca.groupname.logic.Variable;
import ca.groupname.flows.FlowState;

public class Expression {

    private Expression operandA;
    private Operation operation;
    private Expression operandB;
    private Variable value;
    private Variable valueA;
    private Variable valueB;
    
    FlowState flowState;

    public Expression() {
//        flowState.getCurrentBlock();
//        System.out.println(operation.toString());
//        this.operation = (a, b) -> {return operation(a, b)};
    }
    
    public void setFlowState(FlowState flowState) {
        this.flowState = flowState;
    }

    public Variable evaluateExpression() {
        if (value != null) {
            return value;
        }
        else {
            if (this.operation == null) {
                // operation is null, set value to operandA's value
//                if (this.operandA.getValue().isSupportedType()) {
//                    this.value =
//                    return value;
//                }
                return value;
            }
            else {
                this.value = operation.perform(operandA, operandB);
                return value;
            }
        }
//        Class clazz = exp.getClass();
//        if (clazz == Expression.class) {
//            evaluateExpression(exp);
//        }
//        else if (clazz == Variable.class) {
//            return (Variable) exp;
//        }
    }
    
    // Testing method
    public void setAttrs(Expression opA, Operation op, Expression opB) {
        this.operandA = opA;
        this.operation = op;
        this.operandB = opB;
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
}
