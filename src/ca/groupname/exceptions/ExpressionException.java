package ca.groupname.exceptions;

public class ExpressionException extends Throwable {
    public ExpressionException() {
    }

    public void divisionByZeroException(String errorMessage) {
        System.out.println("\tDivision by zero.\n" + errorMessage);
    }
    
    public void indexOutOfRangeException(String errorMessage) {
        System.out.println("\tIndex out of range.\n" + errorMessage);
    }
}
