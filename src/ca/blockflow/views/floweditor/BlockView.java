package ca.blockflow.views.floweditor;

import ca.blockflow.blocks.Block;
import ca.blockflow.blocks.BlockTypes;
import ca.blockflow.main.AppModel;
import ca.blockflow.util.AppUtils;
import ca.blockflow.util.StyleUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
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
    private SimpleObjectProperty<Color> blockColor = new SimpleObjectProperty<>(Color.DODGERBLUE);
    private Block backingBlock;
    private ArrayList<SubblockContainer> containers = new ArrayList<>();
    private SubblockContainer parent;
    private BlockTypes type;
    
    public BlockView(SubblockContainer parent, BlockTypes type) {
        super();
        try {
            this.parent = parent;
            this.backingBlock = type.getBlockClass().newInstance();
            this.nameLabel.setText(type.getBlockName());
            this.type = type;
            initView();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    private void initView() {
        this.blockColor.bind(AppModel.getInstance().getColors().getColor(type));
        blockColor.addListener((obs, oldVal, newVal) -> {
            this.setBackground(StyleUtils.solidBackground(newVal, 5));
            this.setBorder(StyleUtils.getCurvedBorder(5, newVal.darker()));
        });
        this.setBorder(StyleUtils.getCurvedBorder(5, blockColor.get().darker()));
        this.setBackground(StyleUtils.solidBackground(blockColor.get(), 5));
        this.setSpacing(5);
        this.setPadding(new Insets(5));
        this.getChildren().addAll(nameLabel);
        String[] subblocks = Stream.concat(
                Arrays.stream(backingBlock.getLoopingSubblockNames()),
                Arrays.stream(backingBlock.getSubblockNames()))
                                   .toArray(String[]::new);
        for (String sub : subblocks) {
            SubblockContainer container = new SubblockContainer(sub, blockColor, this);
            this.containers.add(container);
            this.getChildren().add(container);
        }
        this.setOnDragDetected(e -> {
            System.out.println("DRAG ON VIEW STARTED!");
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.put(AppUtils.REF_BLOCK_VIEW, AppUtils.addToRefBoard(this));
            db.setContent(content);
            e.consume();
        });
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
