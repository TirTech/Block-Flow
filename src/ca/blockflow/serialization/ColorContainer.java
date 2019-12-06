package ca.blockflow.serialization;

import javafx.scene.paint.Color;

public class ColorContainer implements Saveable {
    double red;
    double green;
    double blue;
    double opacity;
    
    public ColorContainer(Color colour) {
        this.red = colour.getRed();
        this.blue = colour.getBlue();
        this.green = colour.getGreen();
        this.opacity = colour.getOpacity();
    }
    
    public Color getColor() {
        return new Color(red, green, blue, opacity);
    }
}
