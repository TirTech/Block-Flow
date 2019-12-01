package ca.blockflow.views;

import javafx.scene.paint.Color;

public class BlockColorPalette {
    
    private Color forBlockColor;
    private Color ifBlockColor;
    private Color asnBlockColor;
    private Color funcBlockColor;
    
    //any blocks added just need to be added with g/setters
    public BlockColorPalette() {}
    
    //getters
    
    public Color getForBlockColor() {
        return this.forBlockColor;
    }
    
    public void setForBlockColor(Color color) {
        forBlockColor = color;
    }
    
    public Color getIfBlockColor() {
        return this.ifBlockColor;
    }
    
    public void setIfBlockColor(Color color) {
        ifBlockColor = color;
    }
    
    
    //setters
    
    public Color getFuncBlockColor() {
        return this.funcBlockColor;
    }
    
    public void setFuncBlockColor(Color color) {
        funcBlockColor = color;
    }
    
    public Color getAsnBlockColor() {
        return this.asnBlockColor;
    }
    
    public void setAsnBlockColor(Color color) {
        asnBlockColor = color;
    }
    
    
}
