package ca.blockflow.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class StyleUtils {
    
    public static Border getCurvedBorderGrey(int radius) {
        return getCurvedBorder(radius, Color.DARKGRAY);
    }
    
    public static Border getCurvedBorderBold(int radius, Paint color) {
        return getCurvedBorder(radius, color, BorderStroke.THICK);
    }
    
    public static Border getCurvedBorder(int radius, Paint color, BorderWidths stroke) {
        return new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, new CornerRadii(radius), stroke));
    }
    
    public static Border getCurvedBorder(int radius, Paint color) {
        return getCurvedBorder(radius, color, BorderStroke.THIN);
    }
    
    public static Background solidBackground(Paint color, int radius) {
        return new Background(new BackgroundFill(color, radius > 0 ? new CornerRadii(radius) : CornerRadii.EMPTY, null));
    }
    
    public static ImageView getLogo(boolean withBackground, double fitHeight) {
        ImageView view = new ImageView(new Image(withBackground ? "logo_with_bg.png" : "logo_no_bg.png"));
        view.setPreserveRatio(true);
        view.setFitHeight(fitHeight);
        return view;
    }
}
