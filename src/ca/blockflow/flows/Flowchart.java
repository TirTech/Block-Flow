package ca.blockflow.flows;

import ca.blockflow.blocks.Block;
import ca.blockflow.serialization.Saveable;

import java.util.ArrayList;

/**
 * UNUSED Container class for holding all blocks in one place for saving to a file
 */
public class Flowchart implements Saveable {
    private ArrayList<Block> blocks = new ArrayList<>();
    
    public Flowchart() {}
}
