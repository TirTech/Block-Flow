package ca.blockflow.exceptions;

public class BlockException extends Exception{
    
    /**
     * The constructor for BlockException
     */
    public BlockException() {
    }
    
    /**
     * BlockException passes the message to the parent Exception class
     * @param message the error to be displayed
     */
    public BlockException(String message) {
        super(message);
    }
    
    /**
     * BlockException passes the message to the parent Exception class
     * @param message the error to be displayed
     * @param cause   the thrown exception which causes BlockException to be thrown
     */
    public BlockException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * BlockException passes the message to the parent Exception class
     * @param cause the thrown exception which causes BlockException to be thrown
     */
    public BlockException(Throwable cause) {
        super(cause);
    }
}
