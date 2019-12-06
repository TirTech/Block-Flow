package ca.blockflow.exceptions;

public class ExpressionException extends Exception{
    
    private static final String NEW_LINE_SPACE = "\n\t\t\t\t\t";
    
    public ExpressionException() {
    }
    
    public static void divisionByZeroException(String errorMessage) throws ExceptionHandler {
        errorMessage = "\tDivision by zero." + NEW_LINE_SPACE + errorMessage + ".";
        throw new ExceptionHandler(errorMessage);
    }
    
    public static void indexOutOfRangeException(String errorMessage) throws ExceptionHandler{
        errorMessage = "\tIndex out of range." + NEW_LINE_SPACE + errorMessage + ".";
        throw new ExceptionHandler(errorMessage);
    }
    
    public static void modByZeroException(String errorMessage) throws ExceptionHandler{
        errorMessage = "\tModulus invalid." + NEW_LINE_SPACE + errorMessage + ".";
        throw new ExceptionHandler(errorMessage);
    }
    
    public static void creationException(String errorMessage) throws ExceptionHandler {
        errorMessage = "\tExpression creation failed." + NEW_LINE_SPACE + errorMessage + ".";
        throw new ExceptionHandler(errorMessage);
    }
    
    public static void unassignedExpression(String errorMessage) throws ExceptionHandler {
        errorMessage = "\tExpression cannot be evaluated." + NEW_LINE_SPACE + errorMessage + ".";
        throw new ExceptionHandler(errorMessage);
    }
    
    public static void comparisonException(String errorMessage) throws ExceptionHandler {
        errorMessage = "\tComparison values invalid." + NEW_LINE_SPACE + errorMessage + ".";
        throw new ExceptionHandler(errorMessage);
    }
}
