package ca.blockflow.views;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ColorPrefView extends Dialog {
    
    public ColorPrefView() {
        super();
        setTitle("Color Picker");
        //create color picker
        DialogPane pane = getDialogPane();
        GridPane gridPane = new GridPane();
        pane.setMaxWidth(600);
        pane.setMaxHeight(600);
        pane.getButtonTypes().add(ButtonType.CLOSE);
        Label cPickerLabel = new Label("Pick a block and a Color.");
        ColorPicker cPicker = new ColorPicker(Color.BLUE);
    
        cPicker.getStyleClass().add("button");
        Button selectColor = new Button("Set");
    
    
        pane.getChildren().addAll(cPickerLabel, cPicker);
        //help with block dropdown selection
        
    }//end constructor
    
    
}
