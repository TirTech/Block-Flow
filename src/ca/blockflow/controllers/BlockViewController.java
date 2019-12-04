package ca.blockflow.controllers;

import ca.blockflow.models.BlockViewModel;
import ca.blockflow.serialization.SerialBlockTree;
import ca.blockflow.util.AppUtils;
import ca.blockflow.views.floweditor.BlockView;
import ca.blockflow.views.floweditor.EditorDialog;
import ca.blockflow.views.floweditor.SubblockContainer;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.util.Arrays;
import java.util.stream.Stream;

public class BlockViewController {
    
    private BlockView view;
    private BlockViewModel model;
    
    public BlockViewController(BlockView view, BlockViewModel model) {
        this.view = view;
        this.model = model;
    }
    
    public void setHandlers() {
        String[] subblocks = Stream.concat(
                Arrays.stream(model.getBackingBlock().getLoopingSubblockNames()),
                Arrays.stream(model.getBackingBlock().getSubblockNames()))
                                   .toArray(String[]::new);
        for (String sub : subblocks) {
            SubblockContainer container = new SubblockContainer(sub, model.blockColorProperty(), view);
            model.getContainers().add(container);
            view.getChildren().add(container);
        }
        view.setOnDragDetected(e -> {
            System.out.println("DRAG ON VIEW STARTED!");
            Dragboard db = view.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.put(AppUtils.REF_BLOCK_VIEW, AppUtils.addToRefBoard(view));
            db.setContent(content);
            e.consume();
        });
        view.setOnMouseClicked(e -> {
            if (e.getClickCount() >= 2) {
                EditorDialog dialog = null;
                try {
                    dialog = new EditorDialog(model.getType(), model.getBackingBlock());
                    dialog.show();
                } catch (IllegalAccessException | InstantiationException e1) {
                    AppUtils.logError(e1.getMessage());
                }
                e.consume();
            }
        });
    }
    
    public SerialBlockTree serializeTree() {
        SerialBlockTree tree = new SerialBlockTree(model.getType(), model.getBackingBlock());
        for (SubblockContainer container : model.getContainers()) {
            container.serializeTree(tree);
        }
        return tree;
    }
    
    public void constructTree(SerialBlockTree tree) {
        for (SubblockContainer container : model.getContainers()) {
            container.constructTree(tree);
        }
    }
}
