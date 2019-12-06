package ca.blockflow.logic;

import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.expressions.Expression;
import ca.blockflow.expressions.SupportedTypes;

import java.util.ArrayList;
import java.util.Arrays;

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
    INT_POW((l,r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " mod " + rVal), INT, (int) Math.pow(lVal, rVal));
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
    
    LOGICAL_NOT((l, r) -> {
        boolean lVal = booleanVal(l);
        return new Variable<>(("!" + lVal), BOOLEAN, ! lVal);
    }),
    LOGICAL_AND((l, r) -> {
        boolean lVal = booleanVal(l);
        boolean rVal = booleanVal(r);
        return new Variable<>((lVal + " LOGICAL_AND " + rVal), BOOLEAN, Boolean.logicalAnd(lVal, rVal));
    }),
    LOGICAL_OR((l, r) -> {
        boolean lVal = booleanVal(l);
        boolean rVal = booleanVal(r);
        return new Variable<>((lVal + " LOGICAL_OR " + rVal), BOOLEAN, Boolean.logicalOr(lVal, rVal));
    }),
    LOGICAL_XOR((l, r) -> {
        boolean lVal = booleanVal(l);
        boolean rVal = booleanVal(r);
        return new Variable<>((lVal + " LOGICAL_XOR " + rVal), BOOLEAN, Boolean.logicalXor(lVal, rVal));
    }),
    LOGICAL_NOR((l, r) -> {
        boolean lVal = booleanVal(l);
        boolean rVal = booleanVal(r);
        return new Variable<>((lVal + " LOGICAL_NOR " + rVal), BOOLEAN, !Boolean.logicalOr(lVal, rVal));
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
    
    public static ArrayList<Operation> getIntOps() {
        return new ArrayList<>(
                Arrays.asList(NO_OP,
                              INT_PLUS,
                              INT_SUB,
                              INT_MULT,
                              INT_DIV,
                              INT_MOD,
                              INT_FACT,
                              INT_POW));
    }
    
    public static ArrayList<Operation> getDoubleOps() {
        return new ArrayList<>(
                Arrays.asList(NO_OP,
                              DOUBLE_POW,
                              DOUBLE_PLUS,
                              DOUBLE_SUB,
                              DOUBLE_MULT,
                              DOUBLE_DIV,
                              DOUBLE_FACT));
    }
    
    public static ArrayList<Operation> getBooleanOps() {
        return new ArrayList<>(
                Arrays.asList(NO_OP,
                              EQUALS,
                              LOGICAL_NOT,
                              LOGICAL_AND,
                              LOGICAL_OR,
                              LOGICAL_NOR,
                              LOGICAL_XOR,
                              GREATER_THAN,
                              LESS_THAN,
                              GREATER_THAN_OR_EQUAL,
                              LESS_THAN_OR_EQUAL));
    }
    
    public static ArrayList<Operation> getStringOps() {
        return new ArrayList<>(
                Arrays.asList(NO_OP,
                              CONCAT,
                              INDEX_SEARCH));
    }
    
    public static ArrayList<SupportedTypes> getSupportedTypes(Operation op) {
        ArrayList<SupportedTypes> types = new ArrayList<>();
//        for (Operation op : ops) {
        String opName = op.toString();
        if (opName.contains("CONCAT") || opName.contains("STRING") || opName.contains("INDEX")) {
            types.addAll(Arrays.asList(INT, DOUBLE, BOOLEAN, STRING));
        }
        else if (opName.contains("INT")) {
            types.add(INT);
        }
        else if (opName.contains("DOUBLE")) {
            types.addAll(Arrays.asList(INT, DOUBLE));
        }
        else if (opName.contains("BOOLEAN")) {
            types.addAll(Arrays.asList(INT, DOUBLE, BOOLEAN));
        }
        else if (opName.contains("LOGICAL")) {
            types.add(BOOLEAN);
        }
        else if (opName.contains("THAN") || opName.contains("EQUALS")) {
            types.addAll(Arrays.asList(INT, DOUBLE, BOOLEAN, STRING));
        }
        return types;
    }
}
