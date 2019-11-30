package ca.blockflow.views.floweditor;

import ca.blockflow.util.StyleUtils;
import ca.blockflow.views.FunctionBlockView;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;

public class FlowView extends ScrollPane {
    
    private BlockView rootView;
    private ContextMenu ctxMenu = new ContextMenu();
    private MenuItem ctxmiDelete = new MenuItem("Delete");
    private Object targetedNode;
    
    public FlowView() {
        this.setBorder(StyleUtils.getCurvedBorderGrey(5));
        this.setPadding(new Insets(5));
        this.setFitToWidth(true);
        ctxMenu.getItems().addAll(ctxmiDelete);
        setOnMouseClicked(e -> {
            System.out.println("TARGET: " + e.getTarget());
            if (e.getButton() == MouseButton.SECONDARY && e.getTarget() instanceof BlockView && ! (e.getTarget() instanceof FunctionBlockView)) {
                targetedNode = e.getTarget();
                ctxMenu.show(this, e.getScreenX(), e.getScreenY());
            } else if (ctxMenu.isShowing()) {
                ctxMenu.hide();
            }
        });
        ctxmiDelete.setOnAction(e -> {
            if (targetedNode != null) {
                ((BlockView) targetedNode).delete();
            }
            ctxMenu.hide();
        });
    }
    
    public void setRootView(BlockView view) {
        this.rootView = view;
        this.setContent(rootView);
    }
    
    
}
