package ca.blockflow.views.floweditor;

import ca.blockflow.models.AppModel;
import ca.blockflow.util.StyleUtils;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;

public class FlowView extends ScrollPane {
    
    private BlockView rootView;
    private ContextMenu ctxMenu = new ContextMenu();
    private MenuItem ctxmiDelete = new MenuItem("Delete");
    private MenuItem ctxmiBreakpoint = new MenuItem("Toggle Breakpoint");
    private Object targetedNode;
    
    public FlowView() {
        this.setBorder(StyleUtils.getCurvedBorderGrey(5));
        this.setPadding(new Insets(5));
        this.setFitToWidth(true);
        ctxMenu.getItems().addAll(ctxmiBreakpoint, ctxmiDelete);
        setOnMouseClicked(e -> {
            System.out.println("TARGET: " + e.getTarget());
            boolean targetIsHbox = e.getTarget() instanceof HBox && ((HBox) e.getTarget()).getParent() instanceof BlockView;
            boolean valid = e.getButton() == MouseButton.SECONDARY
                            && (e.getTarget() instanceof BlockView
                                || targetIsHbox)
                            && ! ((targetIsHbox
                                   ? ((HBox) e.getTarget()).getParent()
                                   : e.getTarget()) instanceof FunctionBlockView);
            if (valid) {
                targetedNode = targetIsHbox ? ((HBox) e.getTarget()).getParent() : e.getTarget();
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
    
        ctxmiBreakpoint.setOnAction(e -> {
            if (targetedNode != null) {
                ((BlockView) targetedNode).toggleBreakpoint();
            }
        });
        AppModel.getInstance().rootBlockViewProperty().addListener((obs, oldVal, newVal) -> {
            if (! newVal.equals(this.rootView)) {
                setRootView(newVal);
            }
        });
    }
    
    public void setRootView(BlockView view) {
        this.rootView = view;
        this.setContent(rootView);
    }
}
