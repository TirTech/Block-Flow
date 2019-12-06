package ca.blockflow.views.floweditor;

import ca.blockflow.blocks.BlockTypes;
import ca.blockflow.serialization.SerialBlockTree;

public class FunctionBlockView extends BlockView {
    public FunctionBlockView(SubblockContainer parent) {
        super(parent, BlockTypes.FUNCTION);
    }
    
    public FunctionBlockView(SerialBlockTree tree, SubblockContainer parent) {
        super(tree, parent);
    }
}
