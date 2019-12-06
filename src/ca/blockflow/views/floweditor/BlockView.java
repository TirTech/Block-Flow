package ca.blockflow.views.floweditor;

import ca.blockflow.blocks.Block;
import ca.blockflow.blocks.BlockTypes;
import ca.blockflow.controllers.BlockViewController;
import ca.blockflow.models.AppModel;
import ca.blockflow.models.BlockViewModel;
import ca.blockflow.serialization.SerialBlockTree;
import ca.blockflow.util.AppUtils;
import ca.blockflow.util.StyleUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class BlockView extends VBox {
    
    private static int UUID_GLOBAL = 0;
    private final int UUID = ++ UUID_GLOBAL;
    private Label nameLabel = new Label("New Block" + ">" + UUID);
    private ImageView breakpoint = StyleUtils.getImage("icons/breakpoint.png", 15);
    private SubblockContainer parent;
    private BlockViewModel model = new BlockViewModel();
    private BlockViewController controller = new BlockViewController(this, model);
    
    public BlockView(SubblockContainer parent, BlockTypes type) {
        super();
        try {
            this.parent = parent;
            model.setBackingBlock(type.getBlockClass().newInstance());
            model.setType(type);
            initView();
        } catch (InstantiationException | IllegalAccessException e) {
            AppUtils.logError(e.getMessage());
        }
    }
    
    public BlockView(SerialBlockTree tree, SubblockContainer parent) {
        this.parent = parent;
        model.setBackingBlock(tree.getBackingBlock());
        model.setType(tree.getType());
        model.setBreakpoint(model.getBackingBlock().getBreakpoint());
        initView();
        controller.constructTree(tree);
    }
    
    private void initView() {
        this.nameLabel.setText(model.getType().getBlockName());
        SimpleObjectProperty<Color> color = AppModel.getInstance().getColors().getColor(model.getType());
        this.setEffect(new DropShadow(5, 2.5, 2.5, color.get().darker().darker()));
        model.blockColorProperty().bind(color);
        model.blockColorProperty().addListener((obs, oldVal, newVal) -> {
            this.setBackground(StyleUtils.solidBackground(newVal, 5));
            this.setBorder(StyleUtils.getCurvedBorder(5, newVal.darker()));
        });
        this.setBorder(StyleUtils.getCurvedBorder(5, model.getBlockColor().darker()));
        this.setBackground(StyleUtils.solidBackground(model.getBlockColor(), 5));
        this.setSpacing(5);
        this.setPadding(new Insets(5));
        HBox labelView = new HBox(model.getType().getIcon(15), nameLabel, breakpoint);
        labelView.setSpacing(5);
        labelView.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(labelView);
        controller.setHandlers();
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
     * Link this block to another block. Recursively performs linking on nested blocks
     * @param finalBlock the block to link to
     */
    public void link(Block finalBlock) {
        //Link each container to finalBlock
        System.out.println("Linking " + this);
        List<String> loopingBlocks = Arrays.asList(model.getBackingBlock().getLoopingSubblockNames());
        model.getBackingBlock().setNextLinkedBlock(finalBlock);
        model.getContainers().forEach(c -> {
            if (loopingBlocks.contains(c.getName())) {
                c.link(this.getBackingBlock());
            } else {
                c.link(finalBlock);
            }
        });
    }
    
    /**
     * Returns the block used as the model for this view
     * @return the block for this view
     */
    public Block getBackingBlock() {
        return model.getBackingBlock();
    }
    
    public String toString() {
        return this.getClass().getSimpleName() + "@" + UUID;
    }
    
    public SerialBlockTree serializeTree() {
        return controller.serializeTree();
    }
    
    public ImageView getBreakpointIcon() {
        return breakpoint;
    }
    
    public void toggleBreakpoint() {
        controller.toggleBreakpoint();
    }
}
