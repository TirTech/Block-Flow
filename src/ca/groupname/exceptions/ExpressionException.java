package ca.groupname.exceptions;

public class ExpressionException extends Exception {
    
    private final boolean PRINT_STACK_TRACES = false;
    private final String BORDER = "############################################################";
    
    public ExpressionException() {
    }

    public void divisionByZeroException(String errorMessage) {
        System.out.println(BORDER);
        System.out.println("\tDivision by zero.\n" + errorMessage + ".");
        if (PRINT_STACK_TRACES) {
            this.printStackTrace();
        }
        System.out.println(BORDER);
    }
    
    public void indexOutOfRangeException(String errorMessage) {
        System.out.println(BORDER);
        System.out.println("\tIndex out of range.\n" + errorMessage + ".");
        if (PRINT_STACK_TRACES) {
            this.printStackTrace();
        }
        System.out.println(BORDER);
    }
    
    public void modByZeroException(String errorMessage) {
        System.out.println(BORDER);
        System.out.println("\tModulus invalid.\n" + errorMessage + ".");
        if (PRINT_STACK_TRACES) {
            this.printStackTrace();
        }
        System.out.println(BORDER);
    }
}
