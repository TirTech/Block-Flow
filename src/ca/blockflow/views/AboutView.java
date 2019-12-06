package ca.blockflow.views;

import ca.blockflow.util.StyleUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AboutView extends Alert {
    
    public AboutView() {
        super(AlertType.NONE);
        ((Stage) getDialogPane().getScene().getWindow()).getIcons().add(StyleUtils.getLogoAsIcon());
        String ABOUT_CONTENT_TEXT = "BlockFlow was built by:\n" +
                                    "Avery Briggs\n" +
                                    "Shawn Norrie\n" +
                                    "Adam Marciszewski\n" +
                                    "Brandon Franklin\n";
        setContentText(ABOUT_CONTENT_TEXT);
        setGraphic(StyleUtils.getLogo(false, 75));
        getButtonTypes().add(ButtonType.CLOSE);
        setHeaderText("BlockFlow");
        setResizable(false);
        setTitle("About");
    }
}
