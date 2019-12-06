package ca.blockflow.views;

import ca.blockflow.util.AppUtils;
import ca.blockflow.util.StyleUtils;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HelpView extends Dialog {
    
    private final Font HEADER_FONT = Font.font(Font.getDefault().getName(), FontWeight.BOLD, 18);
    
    public HelpView() {
        super();
        setTitle("Help");
        DialogPane pane = getDialogPane();
        ((Stage) pane.getScene().getWindow()).getIcons().add(StyleUtils.getLogoAsIcon());
        pane.setMaxWidth(600);
        pane.setMaxHeight(600);
        pane.getButtonTypes().add(ButtonType.CLOSE);
        
        //Init texts
        ScrollPane spane = new ScrollPane();
        TextFlow texts = new TextFlow();
        spane.setContent(texts);
        spane.setFitToWidth(true);
        spane.setFitToHeight(true);
        try {
            texts.getChildren().addAll(loadHelpText());
        } catch (IOException e) {
            AppUtils.logError(e.getMessage());
        }
        
        //Finalize
        pane.setContent(spane);
    }
    
    private ArrayList<Text> loadHelpText() throws IOException {
        List<String> lines = Files.readAllLines(new File(AppUtils.getResource("help_text.txt")).toPath());
        ArrayList<Text> texts = lines.stream().map(l -> {
            Text txt = new Text();
            String text = l;
            if (l.startsWith("#")) {
                text = text.replaceFirst("#", "");
                txt.setFont(HEADER_FONT);
            }
            txt.setText(text + "\n");
            return txt;
        }).collect(Collectors.toCollection(ArrayList::new));
        return texts;
    }
}
