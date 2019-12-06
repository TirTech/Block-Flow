package ca.blockflow.views;

import ca.blockflow.util.StyleUtils;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ExceptionView extends VBox {
    
    private TextFlow console = new TextFlow();
    private Text pathText;
    private Color ERROR_COLOR = Color.RED;
    
    public ExceptionView() {
        initConsole();
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(console);
        Button btnClear = new Button("Clear Console");
        this.getChildren().addAll(scroll, btnClear);
        this.setBorder(StyleUtils.getCurvedBorderGrey(5));
        btnClear.setOnAction(e -> {
            console.getChildren().clear();
            console.getChildren().add(pathText);
        });
    }
    
    private void initConsole() {
        this.pathText = new Text("This is the console. Logs will appear here \n");
    
        pathText.setFont(StyleUtils.consoleFont);
        console.getChildren().add(pathText);
        console.setPrefWidth(500);
        console.setPrefHeight(150);
        console.setPadding(new Insets(5));
    }
    
    public void logMessage(String text, boolean error) {
        setConsole(text, error ? ERROR_COLOR : Color.BLACK);
    }
    
    private void setConsole(String text, Color color) {
        if (! Platform.isFxApplicationThread()) {
            Platform.runLater(() -> setConsole(text, color));
            return;
        }
        String PATH = "C:\\blockflow\\main$";
        Text newText = StyleUtils.consoleText(PATH + "\t" + text + "\n");
        newText.setFont(StyleUtils.consoleFont);
        newText.setFill(color);
        this.console.getChildren().add(newText);
    }
    
    public String getConsoleText() {
        return this.console.getAccessibleText();
    }
    
    public ExceptionView getInstance() {
        return this;
    }
}
