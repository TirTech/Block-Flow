package ca.groupname.flows;

import ca.groupname.main.Saveable;

import java.util.ArrayList;

/**
 * UNUSED Container class for holding all blocks in one place for saving to a file
 */
public class Flowchart implements Saveable {
	private ArrayList<Block> blocks = new ArrayList<>();
	
	public Flowchart() {}
}
