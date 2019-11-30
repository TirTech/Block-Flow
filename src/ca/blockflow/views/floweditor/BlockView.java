package ca.blockflow.views.floweditor;

import ca.blockflow.blocks.Block;
import ca.blockflow.util.StyleUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BlockView extends VBox {
    
    private static int UUID_GLOBAL = 0;
    private final int UUID = ++ UUID_GLOBAL;
    private Label nameLabel = new Label("New Block" + ">" + UUID);
    private Color blockColor = Color.DODGERBLUE;
    private Block backingBlock;
    private ArrayList<SubblockContainer> containers = new ArrayList<>();
    private SubblockContainer parent;
    
    public BlockView(SubblockContainer parent, Block backingBlock) {
        super();
        this.parent = parent;
        this.backingBlock = backingBlock;
        initView();
    }
    
    private void initView() {
        this.setBorder(StyleUtils.getCurvedBorder(5, blockColor.darker()));
        this.setBackground(StyleUtils.solidBackground(blockColor, 5));
        this.setSpacing(5);
        this.setPadding(new Insets(5));
        this.getChildren().addAll(nameLabel);
        String[] subblocks = Stream.concat(
                Arrays.stream(backingBlock.getLoopingSubblockNames()),
                Arrays.stream(backingBlock.getSubblockNames()))
                                   .toArray(String[]::new);
        for (String sub : subblocks) {
            SubblockContainer container = new SubblockContainer(sub, new Color(Math.random(), Math.random(), Math.random(), 1.0), this);
            this.containers.add(container);
            this.getChildren().add(container);
        }
    }
    
    public void delete() {
        parent.deleteChild(this);
    }
    
    public String getLabel() {
        return this.nameLabel.getText();
    }
    
    public void setLabel(String label) {
        this.nameLabel.setText(label);
    }
    
    /**
     * Returns the block used as the model for this view
     * @return the block for this view
     */
    public Block getBackingBlock() {
        return backingBlock;
    }
    
    /**
     * Link this block to another block. Recursively performs linking on nested blocks
     * @param finalBlock the block to link to
     */
    public void link(Block finalBlock) {
        //Link each container to finalBlock
        System.out.println("Linking " + this);
        List<String> loopingBlocks = Arrays.asList(this.backingBlock.getLoopingSubblockNames());
        backingBlock.setNextLinkedBlock(finalBlock);
        containers.forEach(c -> {
            if (loopingBlocks.contains(c.getName())) {
                c.link(this.getBackingBlock());
            } else {
                c.link(finalBlock);
            }
        });
    }
    
    public String toString() {
        return this.getClass().getSimpleName() + "@" + UUID;
    }
    
}
