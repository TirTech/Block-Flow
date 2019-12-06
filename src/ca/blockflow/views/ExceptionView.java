package ca.blockflow.views;

import ca.blockflow.util.StyleUtils;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ExceptionView extends VBox {
    
    private TextFlow console = new TextFlow();
    private Text pathText;
    private ScrollPane scroll = new ScrollPane();
    private Color ERROR_COLOR = Color.RED;
    private String PATH = "C:\\blockflow\\main$";
    
    public ExceptionView() {
        initConsole();
        scroll.setContent(console);
        this.getChildren().add(scroll);
    }
    
    private void initConsole() {
        this.pathText = new Text(PATH);
    
        pathText.setFont(StyleUtils.consoleFont);
        console.getChildren().add(pathText);
        console.setPrefWidth(500);
        console.setPrefHeight(150);
    
        this.setBorder(StyleUtils.getCurvedBorderGrey(5));
    }
    
    public void logMessage(String text, boolean error) {
        setConsole(text, error ? ERROR_COLOR : Color.BLACK);
    }
    
    public void setConsole(String text, Color color) {
        if (! Platform.isFxApplicationThread()) {
            Platform.runLater(() -> setConsole(text, color));
            return;
        }
        Text newText = StyleUtils.consoleText(text + "\n" + PATH + "\t");
        newText.setFont(StyleUtils.consoleFont);
        newText.setFill(color);
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
