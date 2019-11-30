package ca.blockflow.logic;

import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.expressions.Expression;

import java.util.function.BiFunction;

import static ca.blockflow.expressions.SupportedTypes.*;
import static ca.blockflow.logic.OperationUtils.*;

public enum Operation {

    NO_OP((l, r) -> {
        Object lVal = genericVal(l);
        return new Variable<>(lVal.toString(), determineType(lVal.getClass()), lVal);
    }),
    
    INT_PLUS((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " + " + rVal), INT, (lVal + rVal));
    }),
    INT_SUB((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " - " + rVal), INT, (lVal - rVal));
    }),
    INT_MULT((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " * " + rVal), INT, (lVal * rVal));
    }),
    INT_DIV((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " / " + rVal), INT, intDivision(lVal, rVal));
    }),
    INT_FACT((l, r) -> {
        int lVal = intValue(l);
        return new Variable<>((lVal + "!"), INT, intFact(lVal));
    }),
    INT_MOD((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " mod " + rVal), INT, mod(lVal, rVal));
    }),

    DOUBLE_POW((l, r) -> {
        double lVal = doubleValue(l);
        double rVal = doubleValue(r);
        return new Variable<>((lVal + " ^ " + rVal), DOUBLE, Math.pow(lVal, rVal));
    }),
    DOUBLE_PLUS((l, r) -> {
        double lVal = doubleValue(l);
        double rVal = doubleValue(r);
        return new Variable<>((lVal + " + " + rVal), DOUBLE, (lVal + rVal));
    }),
    DOUBLE_SUB((l, r) -> {
        double lVal = doubleValue(l);
        double rVal = doubleValue(r);
        return new Variable<>((lVal + " - " + rVal), DOUBLE, (lVal - rVal));
    }),
    DOUBLE_MULT((l, r) -> {
        double lVal = doubleValue(l);
        double rVal = doubleValue(r);
        return new Variable<>((lVal + " * " + rVal), DOUBLE, (lVal * rVal));
    }),
    DOUBLE_DIV((l, r) -> {
        double lVal = doubleValue(l);
        double rVal = doubleValue(r);
        return new Variable<>((lVal + " / " + rVal), DOUBLE, doubleDivision(lVal, rVal));
    }),
    DOUBLE_FACT((l, r) -> {
        double lVal = doubleValue(l);
        return new Variable<>((lVal + "!"), DOUBLE, doubleFact(lVal));
    }),
    
    NOT((l, r) -> {
        boolean lVal = booleanVal(l);
        return new Variable<>(("!" + lVal), BOOLEAN, ! lVal);
    }),
    
    AND((l, r) -> {
        boolean lVal = booleanVal(l);
        boolean rVal = booleanVal(r);
        return new Variable<>((lVal + " AND " + rVal), BOOLEAN, Boolean.logicalAnd(lVal, rVal));
    }),
    
    OR((l, r) -> {
        boolean lVal = booleanVal(l);
        boolean rVal = booleanVal(r);
        return new Variable<>((lVal + " OR " + rVal), BOOLEAN, Boolean.logicalOr(lVal, rVal));
    }),
    
    XOR((l, r) -> {
        boolean lVal = booleanVal(l);
        boolean rVal = booleanVal(r);
        return new Variable<>((lVal + " XOR " + rVal), BOOLEAN, Boolean.logicalXor(lVal, rVal));
    }),
    
    NOR((l, r) -> {
        boolean lVal = booleanVal(l);
        boolean rVal = booleanVal(r);
        return new Variable<>((lVal + " NOR " + rVal), BOOLEAN, !Boolean.logicalOr(lVal, rVal));
    }),

    CONCAT((l, r) -> {
        Object lVal = genericVal(l);
        Object rVal = genericVal(r);
        return new Variable<>((lVal + " + " + rVal), STRING, concat(lVal, rVal));
    }),
    INDEX_SEARCH((l, r) -> {
        Object lVal = genericVal(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + "[" + rVal + "]"), STRING, indexSearch(lVal, rVal));
    }),
    
    EQUALS((l, r) -> {
        Object lVal = genericVal(l);
        Object rVal = genericVal(r);
        return new Variable<>((lVal + " == " + rVal), BOOLEAN, OperationUtils.equals(lVal, rVal));
    }),
    GREATER_THAN((l, r) -> {
        Object lVal = genericVal(l);
        Object rVal = genericVal(r);
        return new Variable<>((lVal + " > " + rVal), BOOLEAN, greaterThan(lVal, rVal));
    }),
    LESS_THAN((l, r) -> {
        Object lVal = genericVal(l);
        Object rVal = genericVal(r);
        return new Variable<>((lVal + " < " + rVal), BOOLEAN, lessThan(lVal, rVal));
    }),
    GREATER_THAN_OR_EQUAL((l, r) -> {
        Object lVal = genericVal(l);
        Object rVal = genericVal(r);
        return new Variable<>((lVal + " >= " + rVal), BOOLEAN,
                              OperationUtils.equals(lVal, rVal) || greaterThan(lVal, rVal));
    }),
    LESS_THAN_OR_EQUAL((l, r) -> {
        Object lVal = genericVal(l);
        Object rVal = genericVal(r);
        return new Variable<>((lVal + " <= " + rVal), BOOLEAN,
                              OperationUtils.equals(lVal, rVal) || lessThan(lVal, rVal));
    });
    
    private BiFunctionUtil<Expression,Expression,Variable> func;

    Operation(BiFunctionUtil<Expression, Expression, Variable> func) {
        this.func = func;
    }

    public Variable perform(Expression l, Expression r) throws ExceptionHandler {
        return this.func.apply(l, r);
    }

    public static Variable getExpValue(Expression exp) throws ExceptionHandler { return exp.evaluateExpression(); }
    
}
