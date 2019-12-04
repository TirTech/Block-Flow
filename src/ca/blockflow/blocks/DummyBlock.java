package ca.blockflow.blocks;

import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Variable;

public class DummyBlock extends Block {
    private static final long serialVersionUID = 1L;
    private static int UUID_GLOBAL = 0;
    private final int UUID = ++ UUID_GLOBAL;
    private Block a;
    private Block b;
    
    @Override
    public Block call(FlowState state) {
        Variable<Integer> var = state.getVar("pos");
        System.out.println("I am " + UUID + ". Val is " + var.getValue());
        Block choice = null;
        if (a != null && (var.getValue() % 5 != 0 || var.getValue() <= 10)) {
            System.out.println("Using Branch A");
            choice = a;
        } else if (b != null) {
            System.out.println("Using Branch B");
            choice = b;
        } else {
            System.out.println("Using No Branch");
        }
        var.setValue((var.getValue()) + 1);
        return choice;
    }
    
    @Override
    public String[] getSubblockNames() {
        return new String[]{"B"};
    }
    
    public String[] getLoopingSubblockNames() {
        return new String[]{"A"};
    }
    
    @Override
    public void setSubblock(String name, Block block) {
        switch (name) {
            case "A":
                this.a = block;
                break;
            case "B":
                this.b = block;
                break;
        }
        System.out.println("Set block " + name);
    }
}
