package ca.blockflow.views;

import ca.blockflow.blocks.BlockTypes;
import ca.blockflow.serialization.ColorContainer;
import ca.blockflow.serialization.Saveable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class BlockColorPalette implements Saveable {
    
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
    
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        HashMap<BlockTypes, ColorContainer> hm = (HashMap<BlockTypes, ColorContainer>) inStream.readObject();
        colors = new HashMap<>();
        hm.forEach((k, v) -> colors.put(k, new SimpleObjectProperty<>(v.getColor())));
    }
    
    private void writeObject(ObjectOutputStream outStream) throws IOException {
        HashMap<BlockTypes, ColorContainer> hm = new HashMap<>();
        colors.forEach((k, v) -> {
            hm.put(k, new ColorContainer(v.getValue()));
        });
        outStream.writeObject(hm);
    }
}
