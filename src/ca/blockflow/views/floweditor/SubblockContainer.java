package ca.blockflow.views.floweditor;

import ca.blockflow.blocks.Block;
import ca.blockflow.controllers.SubblockContainerController;
import ca.blockflow.serialization.SerialBlockTree;
import ca.blockflow.util.StyleUtils;
import javafx.beans.property.SimpleObjectProperty;
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
    private SimpleObjectProperty<Color> blockColor = new SimpleObjectProperty<>(Color.DODGERBLUE);
    
    private SubblockContainerController controller = new SubblockContainerController(this);
    
    public SubblockContainer(String name, SimpleObjectProperty<Color> blockColor, BlockView parentBlock) {
        this.name = name;
        this.parentBlock = parentBlock;
        this.blockColor.bind(blockColor);
        initView();
    }
    
    private void initView() {
        Label lbl = new Label(name);
        blockColor.addListener((obs, oldVal, newVal) -> {
            setBorder(StyleUtils.getCurvedBorder(5, newVal.darker()));
            setBackground(StyleUtils.solidBackground(newVal.brighter(), 5));
        });
        setBorder(StyleUtils.getCurvedBorder(5, blockColor.get().darker()));
        setBackground(StyleUtils.solidBackground(blockColor.get().brighter(), 5));
        setPadding(new Insets(5));
        getChildren().addAll(lbl);
        
        setSpacing(2);
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
        return blockColor.get();
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
    
    public void serializeTree(SerialBlockTree tree) {
        controller.serializeTree(tree);
    }
    
    public void constructTree(SerialBlockTree tree) {
        controller.constructTree(tree);
    }
}
