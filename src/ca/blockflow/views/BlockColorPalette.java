package ca.blockflow.views;

import ca.blockflow.blocks.Block;
import javafx.scene.paint.Color;

public class BlockColorPalette {
    
    private Color forBlockColor;
    private Color ifBlockColor;
    private Color asnBlockColor;
    private Color funcBlockColor;
    
    //any blocks added just need to be added with g/setters
    public BlockColorPalette() {
        //default color for binding testing
        forBlockColor = Color.GRAY;
        ifBlockColor = Color.GRAY;
        asnBlockColor = Color.GRAY;
        funcBlockColor = Color.GRAY;
    }
    
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
    
    public Color getColorForBlockType(Block block) {
        //grab block type and return the color of that block
        Color color = null;
        
        
        return color;
    }
}
