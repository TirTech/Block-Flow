package ca.groupname.logic;

import ca.groupname.exceptions.ExpressionException;
import ca.groupname.expressions.Expression;
import ca.groupname.expressions.SupportedTypes;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.math.BigInteger;
import java.util.function.BiFunction;

/*
* TO DO :
*
*   -   remove try catches to allow for a flowstate to be the global error handler.
*
*
* */

public enum Operation {

    INT_PLUS((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " + " + rVal), SupportedTypes.INT, (lVal + rVal));
    }),
    INT_SUB((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " - " + rVal), SupportedTypes.INT, (lVal - rVal));
    }),
    INT_MULT((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " * " + rVal), SupportedTypes.INT, (lVal * rVal));
    }),
    INT_DIV((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " / " + rVal), SupportedTypes.INT, intDivision(lVal, rVal));
    }),
    INT_FACT((l, r) -> {
        int lVal = intValue(l);
        return new Variable<>((lVal + "!"), SupportedTypes.INT, intFact(lVal));
    }),
    INT_MOD((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " mod " + rVal), SupportedTypes.INT, mod(lVal, rVal));
    }),

    DOUBLE_POW((l, r) -> {
        int lVal = intValue(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + " ^ " + rVal), SupportedTypes.DOUBLE, Math.pow(lVal, rVal));
    }),
    DOUBLE_PLUS((l, r) -> {
        double lVal = doubleValue(l);
        double rVal = doubleValue(r);
        return new Variable<>((lVal + " + " + rVal), SupportedTypes.DOUBLE, (lVal + rVal));
    }),
    DOUBLE_SUB((l, r) -> {
        double lVal = doubleValue(l);
        double rVal = doubleValue(r);
        return new Variable<>((lVal + " - " + rVal), SupportedTypes.DOUBLE, (lVal - rVal));
    }),
    DOUBLE_MULT((l, r) -> {
        double lVal = doubleValue(l);
        double rVal = doubleValue(r);
        return new Variable<>((lVal + " * " + rVal), SupportedTypes.DOUBLE, (lVal * rVal));
    }),
    DOUBLE_DIV((l, r) -> {
        double lVal = doubleValue(l);
        double rVal = doubleValue(r);
        return new Variable<>((lVal + " / " + rVal), SupportedTypes.DOUBLE, doubleDivision(lVal, rVal));
    }),
    DOUBLE_FACT((l, r) -> {
        double lVal = doubleValue(l);
        return new Variable<>((lVal + "!"), SupportedTypes.DOUBLE, doubleFact(lVal));
    }),
    
    NOT((l, r) -> {
        boolean lVal = booleanVal(l);
        return new Variable<>(("!" + lVal), boolean.class, !lVal);
    }),
    
    AND((l, r) -> {
        boolean lVal = booleanVal(l);
        boolean rVal = booleanVal(r);
        return new Variable<>((lVal + " && " + rVal), boolean.class, Boolean.logicalAnd(lVal, rVal));
    }),
    
    OR((l, r) -> {
        boolean lVal = booleanVal(l);
        boolean rVal = booleanVal(r);
        return new Variable<>((lVal + " && " + rVal), boolean.class, Boolean.logicalOr(lVal, rVal));
    }),
    
    XOR((l, r) -> {
        boolean lVal = booleanVal(l);
        boolean rVal = booleanVal(r);
        return new Variable<>((lVal + " && " + rVal), boolean.class, Boolean.logicalXor(lVal, rVal));
    }),

    CONCAT((l, r) -> {
        Object lVal = genericVal(l);
        Object rVal = genericVal(r);
        return new Variable<>((lVal + " + " + rVal), String.class, concat(lVal, rVal));
    }),
    INDEX_SEARCH((l, r) -> {
        Object lVal = genericVal(l);
        int rVal = intValue(r);
        return new Variable<>((lVal + "[" + rVal + "]"), String.class, indexSearch(lVal, rVal));
    });
    
    private static String indexSearch(Object lst, int index) {
        String word = (String) lst;
        try {
            if (index >= 0 && index < word.length()) {
                return word.charAt(index) + "";
            } else {
                throw new ExpressionException();
            }
        } catch (ExpressionException e) {
            e.indexOutOfRangeException("ATTEMPTING get element " + index + ", from a string of size " + word.length());
            return "";
        }
    }
    
    /**
     * Concat returns concatenation of strings, int, and doubles
     * @param l
     * @param r
     * @return
     */
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
    
    private static int mod(int l, int r) {
        try {
            if (r < 0) {
                throw new ExpressionException();
            }
            else {
                if (l >= 0) {
                    return l % r;
                }
                else {
                    BigInteger i = new BigInteger(Integer.toString(l));
                    BigInteger j = new BigInteger(Integer.toString(l));
                    return i.modInverse(j).intValue();
                }
            }
        } catch (ExpressionException e) {
            e.modByZeroException("ATTEMPTING to mod: " + l + " % " + r);
            return 0;
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
            e.divisionByZeroException("ATTEMPTING to divide: " + l + " / " + r);
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
            e.divisionByZeroException("ATTEMPTING to divide: " + l + " / " + r);
            return 0;
        }
    }

    private BiFunction<Expression,Expression,Variable> funct;

    Operation(BiFunction<Expression, Expression, Variable> funct) {
        this.funct = funct;
    }

    public Variable perform(Expression l, Expression r) {
        return this.funct.apply(l,r);
    }

    private static Variable getExpValue(Expression exp) { return exp.evaluateExpression(); }

    private static Object genericVal(Expression e) {
        return getExpValue(e).getValue();
    }
    
    private static boolean booleanVal(Expression e) {
        return (boolean)getExpValue(e).getValue();
    }

    private static int intValue(Expression e) {
        return (int)getExpValue(e).getValue();
    }

    private static double doubleValue(Expression e) {
        Object o = getExpValue(e).getValue();
        if (validInt(o)) {
            return new Double(o.toString());
        }
        else {
            return (double) o;
        }
    }
    
    public static boolean validInt(Object o) {
        try {
            int i = (Integer) o;
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
