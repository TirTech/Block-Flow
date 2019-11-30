package ca.blockflow.views;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

public class ColorPrefView extends Dialog {
    
    public ColorPrefView() {
        super();
        setTitle("Color Picker");
        //create color picker
        DialogPane pane = getDialogPane();
        pane.setMaxSize(300, 300);
        pane.getButtonTypes().add(ButtonType.CLOSE);
        
        ColorPicker cPicker = new ColorPicker();
        
    }//end constructor
    
    
}
