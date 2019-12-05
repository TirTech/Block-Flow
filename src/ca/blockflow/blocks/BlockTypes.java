package ca.blockflow.blocks;

import ca.blockflow.util.StyleUtils;
import javafx.scene.image.ImageView;


public enum BlockTypes {
    FUNCTION("Function", "function_icon.png", FunctionBlock.class),
    IF("If", "if_icon.png", IfBlock.class),
    ASSIGNMENT("Assignment", "variable_icon.png", AssignmentBlock.class),
    WHILELOOP("While Loop", "while_loop_icon.png", WhileLoopBlock.class),
    FORLOOP("For Loop", "for_loop_icon.png", ForLoopBlock.class);
    
    private final String blockName;
    private final String iconPath;
    private final Class<? extends Block> blockClass;
    
    /**
     * @param blockName The string name of the block type
     * @param iconPath the path of the image icon
     * @param blockClass the block's class
     * */
    BlockTypes(String blockName, String iconPath, Class<? extends Block> blockClass) {
        this.blockName = blockName;
        this.iconPath = iconPath;
        this.blockClass = blockClass;
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
}

