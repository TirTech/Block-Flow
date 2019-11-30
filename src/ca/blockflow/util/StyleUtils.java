package ca.blockflow.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StyleUtils {
    
    public static final Font consoleFont = new Font("Times", 18);
    
    public static Border getCurvedBorderGrey(int radius) {
        return getCurvedBorder(radius, Color.DARKGRAY);
    }
    
    public static Border getCurvedBorder(int radius, Paint color) {
        return new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, new CornerRadii(radius), BorderStroke.THIN));
    }
    
    public static Background solidBackground(Paint color) {
        return new Background(new BackgroundFill(color, null, null));
    }
    
    public static ImageView getLogo(boolean withBackground, double fitHeight) {
        ImageView view = new ImageView(new Image(withBackground ? "logo_with_bg.png" : "logo_no_bg.png"));
        view.setPreserveRatio(true);
        view.setFitHeight(fitHeight);
        return view;
    }
    
    public static Text consoleText(String s) {
        Text text = new Text(s);
        text.setFont(StyleUtils.consoleFont);
        return text;
    }
}
