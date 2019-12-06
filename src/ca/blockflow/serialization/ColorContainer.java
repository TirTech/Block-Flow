package ca.blockflow.serialization;

import javafx.scene.paint.Color;

public class ColorContainer implements Saveable {
    
    private static final long serialVersionUID = 1L;
    private double red;
    private double green;
    private double blue;
    private double opacity;
    
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
