package ca.groupname.Logic;

import ca.groupname.Exceptions.ExpressionException;
import ca.groupname.expressions.Expression;

import java.math.BigInteger;
import java.util.function.BiFunction;

import static sun.util.calendar.CalendarUtils.mod;

public enum Operation {

    INT_PLUS((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " + " + rVal), int.class, (lVal + rVal));
    }),
    INT_SUB((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " - " + rVal) , int.class, (lVal - rVal));
    }),
    INT_MULT((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " * " + rVal), int.class, (lVal * rVal));
    }),
    INT_DIV((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " / " + rVal), int.class, intDivision(lVal, rVal));
    }),
    INT_FACT((l, r) -> {
        int lVal = intValue(l);
        return new Variable<>((lVal + "!"), int.class, intFact(lVal));
    }),
    INT_MOD((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " mod " + rVal), int.class, mod(lVal, rVal));
    }),

    DOUBLE_POW((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " ^ " + rVal), double.class, Math.pow(lVal, rVal));
    }),
    DOUBLE_PLUS((l, r) -> {
        double lVal = doubleValue(l);
        double rVal = doubleValue(r);
        return new Variable<>((lVal + " + " + rVal), double.class, (lVal + rVal));
    }),
    DOUBLE_SUB((l, r) -> {
        double lVal = doubleValue(l);
        double rVal = doubleValue(r);
        return new Variable<>((lVal + " - " + rVal) , double.class, (lVal - rVal));
    }),
    DOUBLE_MULT((l, r) -> {
        double lVal = doubleValue(l);
        double rVal = doubleValue(r);
        return new Variable<>((lVal + " * " + rVal), double.class, (lVal * rVal));
    }),
    DOUBLE_DIV((l, r) -> {
        double lVal = doubleValue(l);
        double rVal = doubleValue(r);
        return new Variable<>((lVal + " / " + rVal), double.class, doubleDivision(lVal, rVal));
    }),
    DOUBLE_FACT((l, r) -> {
        double lVal = doubleValue(l);
        return new Variable<>((lVal + "!"), double.class, doubleFact(lVal));
    }),

    CONCAT((l, r) -> {
        Object lVal = genericVal(l);
        Object rVal = genericVal(r);
        return new Variable<>((lVal + " + " + rVal), String.class, concat(lVal, rVal));
    });

    private static String concat(Object l, Object r) {
        return l.toString() + r.toString();
    }

    private static double doubleFact(double l) {
        if (l <= 0) {
            return 0.0;
        }
        else {
            return l * doubleFact(l - 1);
        }
    }

    private static int intFact(int l) {
        if (l <= 0) {
            return 0;
        }
        else {
            return l * intFact(l - 1);
        }
    }

    private static int intDivision(int l, int r) {
        try {
            if (r == 0) {
                throw new ExpressionException();
            }
            return l / r;
        } catch (ExpressionException e) {
            e.divisionByZeroException();
            e.printStackTrace();
            return 0;
        }
    }

    private static double doubleDivision(double l, double r) {
        try {
            if (r == 0.0) {
                throw new ExpressionException();
            }
            return l / r;
        } catch (ExpressionException e) {
            e.divisionByZeroException();
            e.printStackTrace();
            return 0;
        }
    }

    private final BiFunction<Expression,Expression,Variable> funct;

    Operation(BiFunction<Expression, Expression, Variable> funct) {
        this.funct = funct;
    }

    public Variable perform(Expression l, Expression r) {
        return this.funct.apply(l,r);
    }

    private static Variable getExpValue(Expression exp) {
        return exp.evaluateExpression().getValue();
    }

    private static Object genericVal(Expression e) {
        return getExpValue(e).getValue();
    }

    private static int intValue(Expression e) {
        return (int)getExpValue(e).getValue();
    }

    private static double doubleValue(Expression e) {
        return (double)getExpValue(e).getValue();
    }

    public void validateOperation() {
        try {

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
