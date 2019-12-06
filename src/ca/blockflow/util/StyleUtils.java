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
    
    public static Border getCurvedBorderBold(int radius, Paint color) {
        return getCurvedBorder(radius, color, BorderStroke.THICK);
    }
    
    private static Border getCurvedBorder(int radius, Paint color, BorderWidths stroke) {
        return new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, new CornerRadii(radius), stroke));
    }
    
    public static Border getCurvedBorder(int radius, Paint color) {
        return getCurvedBorder(radius, color, BorderStroke.THIN);
    }
    
    public static Background solidBackground(Paint color, int radius) {
        return new Background(new BackgroundFill(color, radius > 0 ? new CornerRadii(radius) : CornerRadii.EMPTY, null));
    }
    
    public static ImageView getLogo(boolean withBackground, double fitHeight) {
        return getImage(withBackground ? "logo_with_bg.png" : "logo_no_bg.png", fitHeight);
    }
    
    public static Image getLogoAsIcon() {
        return getImage("logo_icon_64x.png", 5).getImage();
    }
    
    public static Text consoleText(String s) {
        Text text = new Text(s);
        text.setFont(StyleUtils.consoleFont);
        return text;
    }
    
    /**
     * Gets the Image at the given path
     * @param path the path of the image file
     * @param fitHeight the desired size of the image
     * @return returns the Image at the path location as an ImageView
     */
    public static ImageView getImage(String path, double fitHeight) {
        ImageView view = new ImageView(new Image(path));
        view.setPreserveRatio(true);
        view.setFitHeight(fitHeight);
        return view;
    }
}
