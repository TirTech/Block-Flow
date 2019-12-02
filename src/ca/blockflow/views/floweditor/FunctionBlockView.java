package ca.blockflow.views.floweditor;

import ca.blockflow.blocks.BlockTypes;

public class FunctionBlockView extends BlockView {
    public FunctionBlockView(SubblockContainer parent) {
        super(parent, BlockTypes.FUNCTION);
    }
}
