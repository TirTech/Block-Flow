package ca.groupname.expressions_testing;

/*
*
* Testing environment for expressions class
*
* */

import ca.groupname.expressions.Expression;
import ca.groupname.logic.Operation;
import ca.groupname.logic.Variable;

import java.util.function.BiFunction;

public class ExpressionTesting {
    
    Variable x = new Variable<>("x", Integer.class, 25);
    Variable y = new Variable<>("y", Integer.class, -25);
    Expression xExp = new Expression();
//    BiFunction<Expression, Operation, Expression> addFunc = new BiFunction<Expression, Operation, Expression>(exp1, add, exp1);
    
    Operation add = new Operation(new BiFunction<Expression, Expression, Variable>());
}
