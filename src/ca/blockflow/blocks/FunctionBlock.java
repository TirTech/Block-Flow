package ca.blockflow.blocks;

import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Variable;

public class FunctionBlock extends Block {
    
    private static final long serialVersionUID = 1L;
    private Block bodyBlock;
    
    @Override
    public Block call(FlowState state) {
        String EXECUTED_VAR_NAME = "FUNCTION_ENTERED";
        if (state.getVar(EXECUTED_VAR_NAME) == null) {
            state.addVars(new Variable(EXECUTED_VAR_NAME, SupportedTypes.BOOLEAN, true));
            return bodyBlock;
        }
        return null;
    }
    
    @Override
    public String[] getLoopingSubblockNames() {
        return new String[]{"Body"};
    }
    
    @Override
    public void setSubblock(String name, Block block) {
        if ("Body".equals(name)) {
            this.bodyBlock = block;
        }
    }
}
