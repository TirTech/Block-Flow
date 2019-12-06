package ca.blockflow.blocks;

import ca.blockflow.util.StyleUtils;
import ca.blockflow.views.blockeditor.*;
import javafx.scene.image.ImageView;


public enum BlockTypes {
    FUNCTION("Function", "function_icon.png", FunctionBlock.class, BlankEditor.class),
    IF("If", "if_icon.png", IfBlock.class, IfBlockEditor.class),
    ASSIGNMENT("Assignment", "variable_icon.png", AssignmentBlock.class, AssignmentBlockEditor.class),
    OUTPUTBLOCK("Output Block", "output_icon.png", OutputBlock.class, OutputBlockEditor.class),
    WHILELOOP("While Loop", "while_loop_icon.png", WhileLoopBlock.class, WhileBlockEditor.class),
    FORLOOP("For Loop", "for_loop_icon.png", ForLoopBlock.class, ForLoopBlockEditor.class);
    
    private final String blockName;
    private final String iconPath;
    private final Class<? extends Block> blockClass;
    private final Class<? extends BlockEditor> uiClass;
    
    /**
     * @param blockName The string name of the block type
     * @param iconPath the path of the image icon
     * @param blockClass the block's class
     * @param uiClass the class for this blocks editor
     * */
    BlockTypes(String blockName, String iconPath, Class<? extends Block> blockClass, Class<? extends BlockEditor> uiClass) {
        this.blockName = blockName;
        this.iconPath = iconPath;
        this.blockClass = blockClass;
        this.uiClass = uiClass;
    }
    
    /**
     * Gets the class that this type represents
     * @return the backing class
     */
    public Class<? extends Block> getBlockClass() {
        return blockClass;
    }
    
    /**
     * @return returns the string name of the block's type
     */
    public String getBlockName(){return blockName;}
    
    /**
     * @param fitHeight The size of the icon to return
     * @return returns the icon of the block as an ImageView
     */
    public ImageView getIcon(int fitHeight) {return StyleUtils.getImage("icons/" + this.iconPath, fitHeight);}
    
    /**
     * @return returns a drag sized icon image
     */
    public ImageView getDragIcon() {return StyleUtils.getImage("icons/" + "drag_" + this.iconPath, 15);}
    
    /**
     * @return returns the string path of the icon image
     */
    public String getIconPath() {return "icons/" + iconPath;}
    
    /**
     * Gets the class that is used to edit this block
     * @return class instantiatable for an editor
     */
    public Class<? extends BlockEditor> getUiClass() {
        return uiClass;
    }
}

