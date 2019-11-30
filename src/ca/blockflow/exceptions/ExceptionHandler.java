package ca.blockflow.exceptions;

import ca.blockflow.main.Main;
import ca.blockflow.views.ExceptionView;

import java.util.Arrays;

public class ExceptionHandler extends Exception {
    
    private final boolean PRINT_STACK_TRACES = false;
    private final String BORDER = "############################################################";
    private ExceptionView view;
    
    public ExceptionHandler(String message) {
        super(message);
        if (view == null) {
            view = Main.getConsoleView();
        }
        if (PRINT_STACK_TRACES) {
            message += "\n" + BORDER + Arrays.toString(this.getStackTrace()) + BORDER + "\n";
        }
        view.setConsole(message);
    }
    
    public ExceptionHandler(Throwable throwable) {
        super(throwable);
    }
    
    public ExceptionHandler(String message, Throwable throwable) {
        super(message, throwable);
        view.setConsole(message);
    }
    
    public ExceptionView getView() {
        return view;
    }
    
    public void updateConsole(String message) throws ExceptionHandler {
        view.setConsole(message);
    }
}
