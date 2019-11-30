package ca.blockflow.views;

import ca.blockflow.util.StyleUtils;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ExceptionView extends VBox {
    
    private TextFlow console;
    private Text pathText;
    private Font consoleFont;
    
    private Color ERROR_COLOR;
    private String PATH;
    
    public ExceptionView() {
        initConsole();
        this.getChildren().add(console);
    }
    
    private void initConsole() {
        this.ERROR_COLOR = Color.RED;
        this.PATH = "C:\\blockflow\\main$";
        this.console = new TextFlow();
        this.consoleFont = new Font("Times", 18);
        this.pathText = new Text(PATH);
    
        pathText.setFont(consoleFont);
        console.getChildren().add(pathText);
        console.setPrefWidth(500);
        console.setPrefHeight(150);
    
        this.setBorder(StyleUtils.getCurvedBorderGrey(5));
    }
    
    public void setConsole(String text) {
        Text newText = new Text("\n" + PATH + "\t" + text + "\n");
        newText.setFont(consoleFont);
        if (!text.equals("")) {
            newText.setFill(ERROR_COLOR);
        }
        System.out.println("setting console to: " + newText);
        this.console.getChildren().add(newText);
    }
    
    public String getConsoleText() {
        return this.console.getAccessibleText();
    }
    
    public ExceptionView getInstance() {
        return this;
    }
}
