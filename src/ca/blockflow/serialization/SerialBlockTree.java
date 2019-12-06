package ca.blockflow.serialization;

import ca.blockflow.blocks.Block;
import ca.blockflow.blocks.BlockTypes;

import java.util.HashMap;
import java.util.List;

public class SerialBlockTree implements Saveable {
    
    private BlockTypes type;
    private Block backingBlock;
    private HashMap<String, List<SerialBlockTree>> subBlocks = new HashMap<>();
    
    public SerialBlockTree(BlockTypes type, Block backingBlock) {
        this.type = type;
        this.backingBlock = backingBlock;
    }
    
    public BlockTypes getType() {
        return type;
    }
    
    public Block getBackingBlock() {
        return backingBlock;
    }
    
    public List<SerialBlockTree> getSubblockTree(String name) {
        return subBlocks.get(name);
    }
    
    public void setSubBlocksTree(String name, List<SerialBlockTree> tree) {
        subBlocks.put(name, tree);
    }
}
