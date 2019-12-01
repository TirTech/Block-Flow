package ca.blockflow.controllers;

import ca.blockflow.exceptions.InvalidFlowStateException;
import ca.blockflow.exceptions.MissingFlowStateException;
import ca.blockflow.flows.FlowState;
import ca.blockflow.main.AppModel;
import ca.blockflow.views.FlowControls;
import ca.blockflow.views.FunctionBlockView;

public class FlowControlsController {
    
    private FlowControls view;
    private AppModel model = AppModel.getInstance();
    
    public FlowControlsController(FlowControls view) {
        this.view = view;
    }
    
    public void setHandlers() {
        view.getBtnPause().setOnAction(e -> {
            try {
                model.getEngine().pause();
            } catch (MissingFlowStateException e1) {
                e1.printStackTrace();
            }
        });
        view.getBtnStop().setOnAction(e -> {
            try {
                model.getEngine().stop();
            } catch (MissingFlowStateException e1) {
                e1.printStackTrace();
            }
        });
        view.getBtnStep().setOnAction(e -> {
            try {
                model.getEngine().step();
            } catch (MissingFlowStateException e1) {
                e1.printStackTrace();
            }
        });
        view.getBtnPlay().setOnAction(e -> {
            try {
                doPlay();
            } catch (InvalidFlowStateException e1) {
                e1.printStackTrace();
            } catch (MissingFlowStateException e1) {
                e1.printStackTrace();
            }
        });
    }
    
    private void doPlay() throws InvalidFlowStateException, MissingFlowStateException {
        /*
            1) Get the FlowView and its first Block
            2) Execute the linker to get everything set up
            3) Create a new FlowState so that we can set up the FlowEngine
            4) Load the Variables into the FlowState
            5) Load the first Block of the FlowView into the FlowState
            6) Set the FlowEngine's FlowState so that it is ready to run
            7) Execute!
         */
        FunctionBlockView bvRoot = model.getRootBlockView();
        bvRoot.link(null);
        FlowState state = new FlowState();
        state.addVars(model.getVariables());
        state.setCurrentBlock(bvRoot.getBackingBlock());
        model.getEngine().setFlowState(state);
        model.getEngine().setOnFailed(e -> {
            e.getSource().getException().printStackTrace();
        });
        model.getEngine().play();
    }
}
