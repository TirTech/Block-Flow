package ca.blockflow.testing;

import ca.blockflow.blocks.Block;
import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.exceptions.InvalidFlowStateException;
import ca.blockflow.exceptions.MissingFlowStateException;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.flows.FlowEngine;
import ca.blockflow.flows.FlowState;
import ca.blockflow.flows.FlowStatus;
import ca.blockflow.logic.Variable;

/**
 * Do whatever with this class. I just use it for testing to keep the rest of the code clean
 */
public class TestingCode {
    public static void test() throws ExceptionHandler {
        //testFlowEngineStepping();
        //testFlowEnginePausing();
        //testScoping();
        //testFlowEngineBasic();
        //testFlowEnginePauseMethod();
        //testBreakpointing();
        ExpressionTesting expTest = new ExpressionTesting();
        expTest.test();
    }
    
    /**
     * Tests if the program pauses when a breakpoint is hit
     */
    private static void testBreakpointing() {
        Block newBlock = makeInfiniteBlock();
        newBlock.setBreakpoint(true);
        FlowEngine engine = new FlowEngine();
        FlowState state = new FlowState();
        state.setCurrentBlock(newBlock);
        engine.setFlowState(state);
        System.out.println("Starting...");
        engine.setOnSucceeded(e -> {
            System.out.println("Paused...");
            try {
                engine.play();
            } catch (MissingFlowStateException | InvalidFlowStateException e1) {
                e1.printStackTrace();
            }
        });
        try {
            engine.play();
        } catch (MissingFlowStateException e) {
            e.printStackTrace();
        } catch (InvalidFlowStateException e) {
            e.printStackTrace();
        }
    }
    
    private static Block makeInfiniteBlock() {
        return new Block() {
            @Override
            public void call(FlowState state) {
                Variable<Integer> varX = state.getVar("x");
                if (varX == null) {
                    System.out.println("Creating x...");
                    state.addVars(new Variable("x", SupportedTypes.INT, 0));
                } else {
                    System.out.println("[" + varX.getValue() + "] Running block...");
                    varX.setValue(varX.getValue() + 1);
                }
            }
        };
    }
    
    /**
     * Test pausing using the Pause method
     */
    private static void testFlowEnginePauseMethod() {
        Block newBlock = makeInfiniteBlock();
        FlowEngine engine = new FlowEngine();
        FlowState state = new FlowState();
        state.setCurrentBlock(newBlock);
        engine.setFlowState(state);
        System.out.println("Starting...");
        engine.start();
        try {
            Thread.sleep(100);
            System.out.println("Pausing...");
            engine.pause();
            System.out.println("Did it pause?");
        } catch (MissingFlowStateException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Testing out variable scoping
     */
    private static void testScoping() {
        FlowState testState = new FlowState();
        testState.addVars(new Variable("X", SupportedTypes.INT, 5));
        testState.addVars(new Variable("Y", SupportedTypes.INT, 6));
        testState.enterScope();
        testState.addVars(new Variable("X", SupportedTypes.INT, 7));
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
        Block testBlock = new Block() {
            @Override
            public void call(FlowState state) {
                state.getVar("teststring").setValue(state.getVar("teststring").getValue() + "\n> " + System.currentTimeMillis());
                if (((String) state.getVar("teststring").getValue()).chars().filter(ch -> ch == '>').count() > 500) {
                    state.setStatus(FlowStatus.STOPPED);
                }
            
            }
        };
        state.setCurrentBlock(testBlock);
        engine.setFlowState(state);
        Variable var = new Variable("teststring", SupportedTypes.STRING, "START");
        state.addVars(var);
        var.valueProperty().addListener((obs, oldVal, newVal) -> System.out.println(newVal));
        engine.start();
    }
    
    /**
     * Test flowchart stepping
     */
    private static void testFlowEngineStepping() {
        Block newBlock = new Block() {
            @Override
            public void call(FlowState state) {
                Variable<Integer> varX = state.getVar("x");
                if (varX == null) {
                    System.out.println("Creating x...");
                    state.addVars(new Variable("x", SupportedTypes.INT, 0));
                } else {
                    System.out.println("[" + varX.getValue() + "] Running block...");
                    varX.setValue(varX.getValue() + 1);
                    if (varX.getValue() == 5) {
                        System.out.println("Internal Pause Condition!");
                        state.setStatus(FlowStatus.PAUSED);
                    }
                }
            }
        };
        FlowEngine engine = new FlowEngine();
        FlowState state = new FlowState();
        state.setCurrentBlock(newBlock);
        engine.setFlowState(state);
        engine.setOnSucceeded(e -> {
            System.out.println("Worker #1 stop");
            System.out.println(engine.getState());
            Variable<Integer> varx = state.getVar("x");
            if (varx == null) {
                System.out.println("X is missing...???");
                return;
            }
            System.out.println("X = " + varx.getValue());
            engine.setOnSucceeded(null);
            engine.setOnSucceeded(w -> {
                System.out.println("Worker stop #2");
                if (varx == null) {
                    System.out.println("X is missing...???");
                    return;
                }
                System.out.println("X = " + varx.getValue());
            });
            try {
                engine.step();
            } catch (Exception x) {
                x.printStackTrace();
            }
        });
        try {
            engine.play();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
    
    /**
     * Test flowchart pausing
     */
    private static void testFlowEnginePausing() {
        Block newBlock = new Block() {
            @Override
            public void call(FlowState state) {
                Variable<Integer> varX = state.getVar("x");
                if (varX == null) {
                    System.out.println("Creating x...");
                    state.addVars(new Variable("x", SupportedTypes.INT, 0));
                } else {
                    System.out.println("[" + varX.getValue() + "] Running block...");
                    varX.setValue(varX.getValue() + 1);
                    if (varX.getValue() == 5) {
                        System.out.println("Internal Pause Condition!");
                        state.setStatus(FlowStatus.PAUSED);
                    } else if (varX.getValue() >= 10) {
                        System.out.println("Done!");
                        state.setStatus(FlowStatus.STOPPED);
                    }
                }
            }
        };
        FlowEngine engine = new FlowEngine();
        FlowState state = new FlowState();
        state.setCurrentBlock(newBlock);
        engine.setFlowState(state);
        engine.setOnSucceeded(e -> {
            System.out.println("Worker stopped");
            System.out.println(engine.getState());
            Variable<Integer> varx = state.getVar("x");
            if (varx == null) {
                System.out.println("X is missing...???");
                return;
            }
            System.out.println("X = " + varx.getValue());
            engine.setOnSucceeded(null);
            engine.setOnSucceeded(w -> {
                System.out.println("Worker stop #2");
                if (varx == null) {
                    System.out.println("X is missing...???");
                    return;
                }
                System.out.println("X = " + varx.getValue());
                //try to start a stopped flow
                engine.setOnFailed(t -> {
                    System.out.println("Got an error from the task. This shouldn't have gotten here... >> " + engine.getException().toString());
                });
                try {
                    engine.play();
                } catch (Exception x) {
                    System.out.println("This should be a InvalidFlowStateException >>");
                    x.printStackTrace();
                }
            });
            try {
                engine.play();
            } catch (Exception x) {
                x.printStackTrace();
            }
        });
        try {
            engine.play();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
