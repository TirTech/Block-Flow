package ca.blockflow.views;

import ca.blockflow.blocks.Block;
import ca.blockflow.blocks.DummyBlock;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.flows.FlowEngine;
import ca.blockflow.flows.FlowState;
import ca.blockflow.flows.FlowStatus;
import ca.blockflow.logic.Variable;
import ca.blockflow.util.AppUtils;
import ca.blockflow.views.floweditor.BlockView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

public class BlockChoiceView extends VBox {
    
    public BlockChoiceView(BlockView view) {
        Label lblTest = new Label("DRAG ME!");
        Button link = new Button("Link It!");
        Button run = new Button("Run");
        this.getChildren().addAll(lblTest, link, run);
        lblTest.setOnDragDetected(d -> {
            System.out.println("DRAG STARTED!");
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.put(AppUtils.REF_BLOCK, new DummyBlock());
            db.setContent(content);
            d.consume();
        });
        link.setOnAction(e -> {
            view.link(new Block() {
                @Override
                public Block call(FlowState state) {
                    System.out.println("LINK END BLOCK");
                    state.setStatus(FlowStatus.STOPPED);
                    return null;
                }
            });
        });
        run.setOnAction(e -> {
            FlowEngine engine = new FlowEngine();
            FlowState state = new FlowState();
            state.addVars(new Variable("pos", SupportedTypes.INT, 0));
            engine.setFlowState(state);
            state.setCurrentBlock(view.getBackingBlock());
            engine.start();
        });
    }
}
