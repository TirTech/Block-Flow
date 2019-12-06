package ca.blockflow.models;

import ca.blockflow.blocks.Block;
import ca.blockflow.blocks.BlockTypes;
import ca.blockflow.views.floweditor.SubblockContainer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BlockViewModel {
    
    private Block backingBlock;
    private BlockTypes type;
    private ArrayList<SubblockContainer> containers = new ArrayList<>();
    private SimpleObjectProperty<Color> blockColor = new SimpleObjectProperty<>(Color.DODGERBLUE);
    private SimpleBooleanProperty breakpoint = new SimpleBooleanProperty(false);
    
    public BlockViewModel() {
    
    }
    
    public boolean isBreakpoint() {
        return breakpoint.get();
    }
    
    public void setBreakpoint(boolean breakpoint) {
        this.breakpoint.set(breakpoint);
    }
    
    public SimpleBooleanProperty breakpointProperty() {
        return breakpoint;
    }
    
    public Block getBackingBlock() {
        return backingBlock;
    }
    
    public void setBackingBlock(Block backingBlock) {
        this.backingBlock = backingBlock;
    }
    
    public BlockTypes getType() {
        return type;
    }
    
    public void setType(BlockTypes type) {
        this.type = type;
    }
    
    public ArrayList<SubblockContainer> getContainers() {
        return containers;
    }
    
    public void setContainers(ArrayList<SubblockContainer> containers) {
        this.containers = containers;
    }
    
    public Color getBlockColor() {
        return blockColor.get();
    }
    
    public void setBlockColor(Color blockColor) {
        this.blockColor.set(blockColor);
    }
    
    public SimpleObjectProperty<Color> blockColorProperty() {
        return blockColor;
    }
}
