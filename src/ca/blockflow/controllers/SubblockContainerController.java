package ca.blockflow.controllers;

import ca.blockflow.blocks.Block;
import ca.blockflow.blocks.BlockTypes;
import ca.blockflow.serialization.SerialBlockTree;
import ca.blockflow.util.AppUtils;
import ca.blockflow.util.StyleUtils;
import ca.blockflow.views.floweditor.BlockView;
import ca.blockflow.views.floweditor.SubblockContainer;
import javafx.geometry.Point2D;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SubblockContainerController {
    private SubblockContainer view;
    
    public SubblockContainerController(SubblockContainer view) {
        this.view = view;
    }
    
    /**
     * Set up event handlers for the view
     */
    public void setHandlers() {
        view.setOnDragOver(d -> {
            if (d.getDragboard().hasContent(AppUtils.REF_BLOCK_TYPE) || d.getDragboard().hasContent(AppUtils.REF_BLOCK_VIEW)) {
                d.acceptTransferModes(TransferMode.ANY);
            }
            d.consume();
        });
        view.setOnDragDropped(e -> {
            BlockTypes type = (BlockTypes) e.getDragboard().getContent(AppUtils.REF_BLOCK_TYPE);
            String movedViewKey = (String) e.getDragboard().getContent(AppUtils.REF_BLOCK_VIEW);
            if (type != null) {
                System.out.println("OOO A BLOCK DRAG!");
                BlockView newBlock = new BlockView(view, type);
                view.getSubblocks().add(newBlock);
                view.getChildren().add(newBlock);
                e.setDropCompleted(true);
                e.consume(); //Required to prevent duplicates up the event tree
            } else if (movedViewKey != null) {
                System.out.println("A BlockView was dropped here");
                BlockView movedView = (BlockView) AppUtils.getFromRefBoard(movedViewKey);
                movedView.delete();
                Point2D mousePos = new Point2D(e.getX(), e.getY());
                int closestPos;
                ArrayList<BlockView> blocks = view.getSubblocks();
                if (blocks.size() == 0) {
                    closestPos = 0;
                } else {
                    double totalDistance = blocks.get(0).getLayoutY();
                    for (closestPos = 0; closestPos < blocks.size(); closestPos++) {
                        if (totalDistance + blocks.get(closestPos).getHeight() > mousePos.getY()) {
                            break;
                        } else {
                            totalDistance += blocks.get(closestPos).getHeight();
                        }
                    }
                }
                view.getSubblocks().add(closestPos, movedView);
                view.getChildren().add(closestPos + 1, movedView);
                e.setDropCompleted(true);
                e.consume(); //Required to prevent duplicates up the event tree
            }
        });
        view.setOnDragEntered(e -> {
            if (e.getDragboard().hasContent(AppUtils.REF_BLOCK_TYPE) || e.getDragboard().hasContent(AppUtils.REF_BLOCK_VIEW)) {
                view.setBorder(StyleUtils.getCurvedBorderBold(5, Color.BLACK));
            }
        });
        view.setOnDragExited(e -> view.setBorder(StyleUtils.getCurvedBorder(5, ((Color) view.getBackground().getFills().get(0).getFill()).darker())));
    }
    
    /**
     * Link this container of blocks to another block. Recursively links nested block chain,
     * with the last block being linked to the provided block
     * @param finalBlock the block to link to
     */
    public void link(Block finalBlock) {
        ArrayList<BlockView> subblocks = view.getSubblocks();
        BlockView parentBlock = view.getParentBlock();
        if (subblocks.size() > 0) {
            parentBlock.getBackingBlock().setSubblock(view.getName(), subblocks.get(0).getBackingBlock());
            for (int index = 0; index < subblocks.size() - 1; index++) {
                System.out.println("Linking Subblock Container " + view + " => " + index);
                subblocks.get(index).link(subblocks.get(index + 1).getBackingBlock());
            }
            System.out.println("Linking Subblock Container " + view + " => " + (subblocks.size() - 1));
            subblocks.get(subblocks.size() - 1).link(finalBlock);
        }
    }
    
    public void deleteChild(BlockView blockView) {
        ArrayList<BlockView> subblocks = view.getSubblocks();
        subblocks.remove(blockView);
        view.getChildren().remove(blockView);
    }
    
    public void serializeTree(SerialBlockTree tree) {
        ArrayList<SerialBlockTree> trees = new ArrayList<>();
        for (BlockView blocks : view.getSubblocks()) {
            trees.add(blocks.serializeTree());
        }
        tree.setSubBlocksTree(view.getName(), trees);
    }
    
    public void constructTree(SerialBlockTree tree) {
        List<SerialBlockTree> myTree = tree.getSubblockTree(view.getName());
        if (myTree != null && myTree.size() > 0) {
            for (SerialBlockTree subtree : myTree) {
                BlockView newView = new BlockView(subtree, view);
                view.getSubblocks().add(newView);
                view.getChildren().add(newView);
            }
        }
    }
}
