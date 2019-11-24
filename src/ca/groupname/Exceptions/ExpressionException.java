package ca.groupname.Exceptions;

public class ExpressionException extends Throwable {
    public ExpressionException() {
    }

    public void divisionByZeroException() {
        System.out.println("Division by zero.");
    }
}
