package ca.blockflow.views.blockeditor;

import ca.blockflow.blocks.Block;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

public abstract class BlockEditor<T extends Block> extends VBox {
    protected T backingBlock;
    
    /**
     * Receives events upon the Apply button of the editor being pressed. This method should return true when the dialog should close,
     * and false to keep the dialog open.
     * @param event the ActionEvent from the button
     * @return true to close the dialog, false to keep it open
     */
    public abstract boolean onApply(ActionEvent event);
    
    public void setBackingBlock(T block) {
        backingBlock = block;
        initUI();
        requestLayout();
    }
    
    /**
     * Called when the backing block is set after the UI is constructed. Override this to load the UI
     */
    public abstract void initUI();
}
