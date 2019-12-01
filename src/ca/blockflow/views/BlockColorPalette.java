package ca.blockflow.views;

import ca.blockflow.blocks.BlockTypes;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class BlockColorPalette {
    
    private HashMap<BlockTypes, SimpleObjectProperty<Color>> colors = new HashMap<>();
    
    //any blocks added just need to be added with g/setters
    public BlockColorPalette() {
        //default color for binding testing
        for (BlockTypes value : BlockTypes.values()) {
            colors.put(value, new SimpleObjectProperty<>(Color.ROYALBLUE));
        }
    }
    
    public void setColor(BlockTypes type, Color color) {
        colors.get(type).set(color);
    }
    
    public SimpleObjectProperty<Color> getColor(BlockTypes type) {
        //grab block type and return the color of that block
        return colors.get(type);
    }
}
