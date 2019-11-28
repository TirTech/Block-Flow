package ca.blockflow.exceptions;

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
    
    public void creationException(String errorMessage) {
        System.out.println(BORDER);
        System.out.println("\tExpression creation failed.\n" + errorMessage + ".");
        if (PRINT_STACK_TRACES) {
            this.printStackTrace();
        }
        System.out.println(BORDER);
    }
    
    public void unassignedExpression(String errorMessage) {
        System.out.println(BORDER);
        System.out.println("\tExpression cannot be evaluated.\n" + errorMessage + ".");
        if (PRINT_STACK_TRACES) {
            this.printStackTrace();
        }
        System.out.println(BORDER);
    }
    
    public void comparisonException(String errorMessage) {
        System.out.println(BORDER);
        System.out.println("\tComparison values invalid.\n" + errorMessage + ".");
        if (PRINT_STACK_TRACES) {
            this.printStackTrace();
        }
        System.out.println(BORDER);
    }
}
