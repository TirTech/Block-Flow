package ca.blockflow.views;

import ca.blockflow.util.StyleUtils;
import javafx.scene.control.TextArea;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ExceptionView extends VBox {
    
    private TextArea console;
    private String path;
    
    public ExceptionView() {
        this.path = "C:\\blockflow\\main$";
        this.console = new TextArea(path);
        this.console.setFont(new Font("Times", 18));
//        this.setWidth(500);
//        this.setHeight(80);
        this.setBorder(StyleUtils.getCurvedBorderGrey(5));
        this.getChildren().add(console);
//        this.getChildren().addAll());
    }
    
    public void setConsole(String text) {
        this.console.setText(path + "\t" + text);
    }
    
    public String getConsoleText() {
        return this.console.getText();
    }
}
