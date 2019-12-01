package ca.blockflow.blocks;

import ca.blockflow.util.StyleUtils;
import javafx.scene.image.ImageView;


public enum BlockTypes {
    FUNCTION("Function", "function_icon.png", "FunctionBlock"),
    IF("If", "if_icon.png", "IfBlock"),
    LOOP("Loop", "loop_icon.png", "LoopBlock"),
    VARIABLE("Variable", "variable_icon.png", "AssignmentBlock");
    
    private final String blockName;
    private final String iconPath;
    private final String blockType;
    /**
     * @param blockName The string name of the block type
     * @param iconPath the path of the image icon
     * @param blockType the block type name
     */
    BlockTypes(String blockName, String iconPath, String blockType) {
        this.blockName = blockName;
        this.iconPath = iconPath;
        this.blockType = blockType;
    }
    
    /**
     * @return returns the string name of the block's type
     */
    public String getBlockName(){return blockName;}
    
    /**
     * @return returns the block type used as a string
     */
    public String getBlockType(){return  blockType;}
    
    /**
     * @param fitHeight The size of the icon to return
     * @return returns the icon of the block as an ImageView
     */
    public ImageView getIcon(int fitHeight){return StyleUtils.getImage(this.iconPath, fitHeight);}
    
    /**
     * @return returns a drag sized icon image
     */
    public ImageView getDragIcon(){return StyleUtils.getImage( "drag_" + this.iconPath, 15);}
    
    /**
     * @return returns the string path of the icon image
     */
    public String getIconPath(){return iconPath;}
}

