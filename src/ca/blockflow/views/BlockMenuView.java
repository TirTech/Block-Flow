package ca.blockflow.views;

import ca.blockflow.blocks.BlockTypes;
import ca.blockflow.main.AppModel;
import ca.blockflow.util.AppUtils;
import ca.blockflow.util.StyleUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class BlockMenuView extends VBox {
    
    public BlockMenuView() {
        //  --- Menu Title  ---
        VBox basicBlocksTab = new VBox(new Text("Basic Blocks"));
        basicBlocksTab.setAlignment(Pos.TOP_CENTER);
        VBox spacer = new VBox();
        spacer.setFillWidth(true);
        spacer.setBorder(StyleUtils.getCurvedBorderGrey(0));
        basicBlocksTab.getChildren().add(spacer);
        
        //  --- Option List Setup   ---
        VBox optionList = new VBox(setupBlock(BlockTypes.ASSIGNMENT), setupBlock(BlockTypes.FUNCTION), setupBlock(BlockTypes.IF));
        optionList.setSpacing(10);
        optionList.setPadding(new Insets(5));
        optionList.setAlignment(Pos.TOP_CENTER);
        
        //  --- Block Menu Tab Setup    ---
        this.setBorder(StyleUtils.getCurvedBorderGrey(5));
        this.setAlignment(Pos.TOP_CENTER);
        
        this.getChildren().addAll(basicBlocksTab, optionList);
    }
    
    /**
     * Set up a block on the UI
     * @param type the type backing this block
     * @return
     */
    private Pane setupBlock(BlockTypes type) {
        HBox root = new HBox(type.getIcon(25), new Text(type.getBlockName()));
        root.setSpacing(10);
        root.setPadding(new Insets(5));
        //root.setAlignment(Pos.CENTER);
        root.setBorder(StyleUtils.getCurvedBorder(5, Color.BLACK));
        SimpleObjectProperty<Color> color = AppModel.getInstance().getColors().getColor(type);
        color.addListener((obs, oldVal, newVal) -> {
            root.setBackground(StyleUtils.solidBackground(newVal, 5));
        });
        root.setBackground(StyleUtils.solidBackground(color.get(), 5));
        root.setPrefSize(80, 30);
        root.setPadding(new Insets(10));
        //root.setOnMousePressed(e -> root.setBorder(StyleUtils.getCurvedBorder(5, Color.BLACK, BorderStroke.THICK)));
        root.setOnDragDetected(d -> createDrag(d, type));
        //root.setOnMouseReleased(e -> root.setBorder(StyleUtils.getCurvedBorder(5, Color.BLACK)));
        //root.setOnDragDone(e -> root.setBorder(StyleUtils.getCurvedBorder(5, Color.BLACK)));
        return root;
    }
    
    /**
     * Creates a drag event
     * @param d         the event being consumed
     * @param blockType the block type of the block being dragged and added
     */
    private void createDrag(MouseEvent d, BlockTypes blockType) {
        System.out.println("DRAG STARTED!");
        Dragboard db = this.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.put(AppUtils.REF_BLOCK, blockType);
        db.setContent(content);
        db.setDragView(blockType.getDragIcon().getImage());
        d.consume();
    }
    
    /**
     * Creates a new rectangle object
     * @return Returns the newly created rectangle object
     * @color The fill color of the rectangle
     */
    private Rectangle createRect(Color fillColor) {
        Rectangle rectangle = new Rectangle();
        
        //Setting the properties of the rectangle
        rectangle.setWidth(100);
        rectangle.setHeight(35);
        
        //Setting the height and width of the arc
        rectangle.setArcWidth(10.0);
        rectangle.setArcHeight(10.0);
        
        //Setting the Colors of the rectangle
        rectangle.setFill(fillColor);
        rectangle.setStrokeWidth(1);
        rectangle.setStroke(Color.BLACK);
        
        return rectangle;
    }
}
