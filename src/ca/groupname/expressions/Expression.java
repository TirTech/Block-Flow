package ca.groupname.expressions;

import ca.groupname.Logic.Operation;
import ca.groupname.Logic.Variable;
import ca.groupname.flows.FlowState;

public class Expression {

    Expression operandA;
    Operation operation;
    Expression operandB;
    Variable left;
    Variable right;

    public Expression(FlowState flowState) {
//        flowState.getCurrentBlock();
        System.out.println(operation.toString());
//        this.operation = (a, b) -> {return operation(a, b)};
    }

    public Variable evaluateExpression() {
//        Class clazz = exp.getClass();
//        if (clazz == Expression.class) {
//            evaluateExpression(exp);
//        }
//        else if (clazz == Variable.class) {
//            return (Variable) exp;
//        }
        return null;
    }
}
