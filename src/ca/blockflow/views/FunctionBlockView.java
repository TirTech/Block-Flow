package ca.blockflow.views;

import ca.blockflow.blocks.Block;
import ca.blockflow.views.floweditor.BlockView;
import ca.blockflow.views.floweditor.SubblockContainer;

public class FunctionBlockView extends BlockView {
    public FunctionBlockView(SubblockContainer parent, Block backingBlock) {
        super(parent, backingBlock);
    }
}
