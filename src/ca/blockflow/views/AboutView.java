package ca.blockflow.views;

import ca.blockflow.util.StyleUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AboutView extends Alert {
    
    private final String ABOUT_CONTENT_TEXT = "BlockFlow was built by:\n" +
                                              "Avery Briggs\n" +
                                              "Shawn Norrie\n" +
                                              "Adam Marciszewski\n" +
                                              "Brandon Franklin\n";
    
    public AboutView() {
        super(AlertType.NONE);
        setContentText(ABOUT_CONTENT_TEXT);
        setGraphic(StyleUtils.getLogo(false, 75));
        getButtonTypes().add(ButtonType.CLOSE);
        setHeaderText("BlockFlow");
        setResizable(false);
        setTitle("About");
    }
}
