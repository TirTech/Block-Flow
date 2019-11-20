package ca.groupname.main;

import ca.groupname.blocks.TestBlock;
import ca.groupname.flows.FlowEngine;
import ca.groupname.flows.FlowState;
import ca.groupname.flows.Variable;

/**
 * Do whatever with this class. I just use it for testing to keep the rest of the code clean
 */
public class TestingCode {
    public static void test() {
        testScoping();
        testFlowEngineBasic();
    }
    
    /**
     * Testing out variable scoping
     */
    private static void testScoping() {
        FlowState testState = new FlowState();
        testState.addVars(new Variable("X", Integer.class, 5));
        testState.addVars(new Variable("Y", Integer.class, 6));
        testState.enterScope();
        testState.addVars(new Variable("X", Integer.class, 7));
        System.out.println("X = " + testState.getVar("X").getValue());
        if (testState.getVar("Y") != null) {
            System.out.println("WHAT?? Var Y has value " + testState.getVar("Y").getValue());
        } else {
            System.out.println("Phew!");
        }
        testState.exitScope();
        System.out.println("X = " + testState.getVar("X").getValue());
        System.out.println("Y = " + testState.getVar("Y").getValue());
        
    }
    
    /**
     * Basic FlowEngine test (start with no UI freezing)
     */
    private static void testFlowEngineBasic() {
        FlowEngine engine = new FlowEngine();
        FlowState state = new FlowState();
        state.setCurrentBlock(new TestBlock());
        engine.setFlowState(state);
        Variable var = new Variable("teststring", String.class, "START");
        state.addVars(var);
        var.valueProperty().addListener((obs, oldVal, newVal) -> System.out.println(newVal));
        engine.start();
    }
}
