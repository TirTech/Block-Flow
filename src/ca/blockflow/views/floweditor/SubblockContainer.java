package ca.blockflow.views.floweditor;

import ca.blockflow.blocks.Block;
import ca.blockflow.controllers.SubblockContainerController;
import ca.blockflow.util.StyleUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SubblockContainer extends VBox {
    
    private static int UUID_GLOBAL = 0;
    private final int UUID = ++ UUID_GLOBAL;
    private String name;
    private ArrayList<BlockView> subblocks = new ArrayList<>();
    private BlockView parentBlock;
    private Color blockColor;
    
    private SubblockContainerController controller = new SubblockContainerController(this);
    
    public SubblockContainer(String name, Color blockColor, BlockView parentBlock) {
        this.name = name;
        this.parentBlock = parentBlock;
        this.blockColor = blockColor;
        initView();
    }
    
    private void initView() {
        Label lbl = new Label(name + ">" + UUID);
        setBorder(StyleUtils.getCurvedBorder(5, blockColor.darker()));
        setBackground(StyleUtils.solidBackground(blockColor, 5));
        setPadding(new Insets(5));
        getChildren().addAll(lbl);
        controller.setHandlers();
    }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<BlockView> getSubblocks() {
        return subblocks;
    }
    
    public BlockView getParentBlock() {
        return parentBlock;
    }
    
    public Color getBlockColor() {
        return blockColor;
    }
    
    public void deleteChild(BlockView blockView) {
        controller.deleteChild(blockView);
    }
    
    /**
     * Link this container of blocks to another block. Recursively links nested block chain,
     * with the last block being linked to the provided block
     * @param finalBlock the block to link to
     */
    public void link(Block finalBlock) {
        //Link each sublist of blocks to each other then the last one to finalBlock
        controller.link(finalBlock);
    }
    
    public String toString() {
        return this.getClass().getSimpleName() + "@" + UUID;
    }
}
