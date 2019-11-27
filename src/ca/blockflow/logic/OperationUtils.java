package ca.blockflow.logic;

import ca.blockflow.exceptions.ExpressionException;
import ca.blockflow.expressions.Expression;

import java.math.BigDecimal;
import java.math.BigInteger;

/*
 * TO DO :
 *
 *   -   remove try catches to allow for a flowstate to be the global error handler.
 *
 *
 * */

public class OperationUtils {
    
    public OperationUtils() {}
    
    /**
     * Returns concatenation of two objects
     * @param l String object left.
     * @param r String object left.
     * @return left and right strings concatenated
     */
    static String concat(Object l, Object r) {
        return l.toString() + r.toString();
    }
    
    static double doubleFact(double n) {
        if (n <= 1) {
            return Math.max(1, n);
        }
        else {
            return n * doubleFact(n - 1);
        }
    }
    
    static int mod(int l, int r) {
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
    
    static int intFact(int n) {
        if (n <= 1) {
            return Math.max(1, n);
        }
        else {
            return n * intFact(n - 1);
        }
    }
    
    static int intDivision(int l, int r) {
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
    
    static double doubleDivision(double l, double r) {
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
    
    static boolean greaterThan(Object lVal, Object rVal) {
        boolean lIsInt = validInt(lVal);
        boolean rIsInt = validInt(rVal);
        boolean lIsDouble = validDouble(lVal);
        boolean rIsDouble = validDouble(rVal);
        boolean lIsString = validString(lVal);
        boolean rIsString = validString(rVal);
        boolean lIsBoolean = validBoolean(lVal);
        boolean rIsBoolean = validBoolean(rVal);
    
        try {
            if (lIsInt && rIsInt) {
                return intValue(lVal) > intValue(rVal);
            } else if (lIsDouble && rIsDouble) {
                return doubleValue(lVal) > doubleValue(rVal);
            } else if (lIsInt && rIsDouble) {
                return doubleValue(lVal) > doubleValue(rVal);
            } else if (rIsInt && lIsDouble) {
                return doubleValue(lVal) > doubleValue(rVal);
            } else if (lIsString || rIsString) {
                String lStringVal = lVal.toString();
                String rStringVal = rVal.toString();
                if (lIsBoolean) {
                    lStringVal = Integer.toString(booleanToInt((boolean) lVal));
                }
                if (rIsBoolean) {
                    rStringVal = Integer.toString(booleanToInt((boolean) rVal));
                }
                return lStringVal.compareTo(rStringVal) > 0;
            } else if (lIsBoolean || rIsBoolean) {
                boolean[] landr = boolValues(lVal, rVal);
                int lIntVal = booleanToInt(landr[0]);
                int rIntVal = booleanToInt(landr[1]);
                return lIntVal > rIntVal;
            } else {
                // throw error? HARD CODE?
                throw new ExpressionException();
            }
        }
        catch(ExpressionException e) {
            e.comparisonException("Trying to compare: " + lVal + " < " + rVal);
            return false;
        }
    }
    
    static boolean lessThan(Object lVal, Object rVal) {
        boolean lIsInt = validInt(lVal);
        boolean rIsInt = validInt(rVal);
        boolean lIsDouble = validDouble(lVal);
        boolean rIsDouble = validDouble(rVal);
        boolean lIsString = validString(lVal);
        boolean rIsString = validString(rVal);
        boolean lIsBoolean = validBoolean(lVal);
        boolean rIsBoolean = validBoolean(rVal);
        
        try {
            if (lIsInt && rIsInt) {
                return intValue(lVal) < intValue(rVal);
            } else if (lIsDouble && rIsDouble) {
                return doubleValue(lVal) < doubleValue(rVal);
            } else if (lIsInt && rIsDouble) {
                return doubleValue(lVal) < doubleValue(rVal);
            } else if (rIsInt && lIsDouble) {
                return doubleValue(lVal) < doubleValue(rVal);
            } else if (lIsString || rIsString) {
                String lStringVal = convertToString(lVal);
                String rStringVal = convertToString(rVal);
                if (lIsBoolean) {
                    lStringVal = Integer.toString(booleanToInt((boolean) lVal));
                }
                if (rIsBoolean) {
                    rStringVal = Integer.toString(booleanToInt((boolean) rVal));
                }
                return lStringVal.compareTo(rStringVal) < 0;
            } else if (lIsBoolean || rIsBoolean) {
                boolean[] landr = boolValues(lVal, rVal);
                int lIntVal = booleanToInt(landr[0]);
                int rIntVal = booleanToInt(landr[1]);
                return lIntVal < rIntVal;
            } else {
                // throw error? HARD CODE
                throw  new ExpressionException();
            }
        }
        catch (ExpressionException e) {
            e.comparisonException("Trying to compare: " + lVal + " < " + rVal);
            return false;
        }
    }
    
    private static boolean[] boolValues(Object lVal, Object rVal) throws ExpressionException{
        boolean lIsInt = validInt(lVal);
        boolean rIsInt = validInt(rVal);
        boolean lIsDouble = validDouble(lVal);
        boolean rIsDouble = validDouble(rVal);
        boolean lIsBoolean = validBoolean(lVal);
        boolean rIsBoolean = validBoolean(rVal);
        boolean lBooleanVal;
        boolean rBooleanVal;
        if (lIsBoolean) {
            lBooleanVal = (boolean)lVal;
        }
        else {
            if (lIsInt || lIsDouble) {
                lBooleanVal = intToBoolean((int)lVal);
            }
            else {
                // throw error? HARD CODE?
                lBooleanVal = false;
            }
        }
        if (rIsBoolean) {
            rBooleanVal = (boolean)rVal;
        }
        else {
            if (rIsInt || rIsDouble) {
                rBooleanVal = intToBoolean((int)rVal);
            }
            else {
                // throw error? HARD CODE?
                rBooleanVal = false;
            }
        }
        return new boolean[] {lBooleanVal, rBooleanVal};
    }
    
    static boolean equals(Object lVal, Object rVal) {
        return lVal.equals(rVal);
    }
    
    static String indexSearch(Object lst, int index) {
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
    
    public static boolean intToBoolean(int val) {
        if (val > 0) {
            return true;
        }
        return false;
    }
    
    public static boolean doubleToBoolean(double val) {
        if (val > 0) {
            return true;
        }
        return false;
    }
    
    public static int booleanToInt(boolean val) {
        if (val) {
            return 1;
        }
        return 0;
    }
    
    public static String convertToString(Object o) {
        if (validInt(o)) {
            return Integer.toString((Integer) o);
        }
        else if (validDouble(o)) {
            return Double.toString((Double) o);
        }
        else {
            return o.toString();
        }
    }
    
//    static Object genericVal(Object o) {
//        return o;
//    }
    
    static boolean booleanVal(Object o) {
        if (o != null) {
            if (validBoolean(o)) {
                return (boolean) o;
            }
            else {
                if (validInt(o) || validDouble(o)) {
                    return new BigDecimal(o.toString()).doubleValue() > 0;
                }
                else return o.toString().length() > 0;
            }
        }
        return false;
    }
    
    static int intValue(Object o) {
        if (o != null) {
            String n = o.toString();
            if (validInt(o) || validDouble(o)) {
                return new BigDecimal(o.toString()).intValue();
            }
            else if (n.length() > 0) {
                if (checkInt(n)) {
                    return Integer.parseInt(n);
                }
            }
            else if (validBoolean(o)) {
                return ((booleanVal(o))? 1 : 0);
            }
        }
        return 0;
    }
    
    static double doubleValue(Object o) {
        if (o != null) {
            String n = o.toString();
            if (validInt(o) || validDouble(o)) {
                return new BigDecimal(n).doubleValue();
            }
            else if (n.length() > 0) {
                if (checkDouble(n)) {
                    return new BigDecimal(n).doubleValue();
                }
            }
            else if (validBoolean(o)) {
                return ((booleanVal(o))? 1 : 0);
            }
        }
        return 0;
    }
    
    static Object genericVal(Expression e) {
        return Operation.getExpValue(e).getValue();
    }
    
    static boolean booleanVal(Expression e) {
        return (boolean) Operation.getExpValue(e).getValue();
    }
    
    static int intValue(Expression e) {
        return (int) Operation.getExpValue(e).getValue();
    }
    
    static boolean checkInt(String text) {
        try {
            BigInteger i = new BigInteger(text);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    static boolean checkDouble(String text) {
        try {
            BigDecimal i = new BigDecimal(text);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    static double doubleValue(Expression e) {
        Object o = Operation.getExpValue(e).getValue();
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
            return false;
        }
    }
    
    public static boolean validDouble(Object o) {
        try {
            double i = (Double) o;
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public static boolean validBoolean(Object o) {
        try {
            boolean b = (boolean)o;
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public static boolean validString(Object o) {
        return o != null;
    }
}
