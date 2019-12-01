package ca.blockflow.views;

import ca.blockflow.blocks.BlockTypes;
import ca.blockflow.views.floweditor.BlockView;
import ca.blockflow.views.floweditor.SubblockContainer;

public class FunctionBlockView extends BlockView {
    public FunctionBlockView(SubblockContainer parent) {
        super(parent, BlockTypes.FUNCTION);
    }
}
