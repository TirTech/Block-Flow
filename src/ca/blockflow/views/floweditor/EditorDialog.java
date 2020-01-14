package ca.blockflow.views.floweditor;

import ca.blockflow.blocks.Block;
import ca.blockflow.blocks.BlockTypes;
import ca.blockflow.util.StyleUtils;
import ca.blockflow.views.blockeditor.BlockEditor;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

public class EditorDialog extends Alert {
    
    public EditorDialog(BlockTypes type, Block block) throws IllegalAccessException, InstantiationException {
        super(AlertType.NONE);
        BlockEditor editor = type.getUiClass().newInstance();
        editor.setBackingBlock(block);
        getButtonTypes().addAll(ButtonType.CLOSE, ButtonType.APPLY);
        DialogPane pane = getDialogPane();
        ((Stage) pane.getScene().getWindow()).getIcons().add(StyleUtils.getLogoAsIcon());
        Button btnApply = (Button) pane.lookupButton(ButtonType.APPLY);
        btnApply.addEventFilter(ActionEvent.ACTION, e -> {
            if (! editor.onApply(e)) e.consume();
        });
        pane.setContent(editor);
        final InvalidationListener sizeListener = e -> pane.getScene().getWindow().sizeToScene();
        pane.heightProperty().addListener(sizeListener);
        pane.widthProperty().addListener(sizeListener);
        setTitle("Edit " + type.getBlockName() + " Block");
        pane.requestLayout();
    }
}
