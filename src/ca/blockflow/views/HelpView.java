package ca.blockflow.views;

import ca.blockflow.util.AppUtils;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
        pane.getButtonTypes().add(ButtonType.CLOSE);
        
        //Init texts
        TextFlow texts = new TextFlow();
        texts.setMaxWidth(600);
        try {
            texts.getChildren().addAll(loadHelpText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Finalize
        pane.setContent(new VBox(texts));
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
