package ca.blockflow.exceptions;

import ca.blockflow.models.AppModel;

public class ExceptionHandler extends Exception {
    
    public ExceptionHandler(String message) {
        super(message);
        AppModel.getInstance().getConsole().logMessage(message, true);
    }
    
    public ExceptionHandler(Throwable throwable) {
        super(throwable);
    }
    
    public ExceptionHandler(String message, Throwable throwable) {
        super(message, throwable);
        AppModel.getInstance().getConsole().logMessage(message, true);
    }
}
