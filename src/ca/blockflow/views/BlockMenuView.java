package ca.blockflow.views;

import ca.blockflow.blocks.BlockTypes;
import ca.blockflow.blocks.DummyBlock;
import ca.blockflow.util.AppUtils;
import ca.blockflow.util.StyleUtils;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class BlockMenuView extends VBox {
    private VBox basicBlocksTab = new VBox(new Text("Basic Blocks"));
    
    //  --- HBoxs containing selection data ---
    private HBox cVarData = new HBox(
            BlockTypes.VARIABLE.getIcon(25),
            new Text(BlockTypes.VARIABLE.getBlockName())
    );
    private HBox cFuncData = new HBox(
            BlockTypes.FUNCTION.getIcon(25),
            new Text(BlockTypes.FUNCTION.getBlockName())
    );
    private HBox cLoopData = new HBox(
            BlockTypes.LOOP.getIcon(25),
            new Text(BlockTypes.LOOP.getBlockName())
    );
    
    //  --- Selection Background Object Shapes    ---
    private Rectangle varOptionRec = createRect(Color.BLUE);
    private Rectangle funcOptionRec = createRect(Color.RED);
    private Rectangle loopOptionRec = createRect(Color.GREEN);
    
    //  --- Selection Object Pane   ---
    private Pane createVarBlock = new Pane(varOptionRec, cVarData);
    private Pane createFunctionBlock = new Pane(funcOptionRec, cFuncData);
    private Pane createLoopBlock = new Pane(loopOptionRec, cLoopData);
    
    private VBox optionList = new VBox(createVarBlock, createFunctionBlock, createLoopBlock);
    
    /*
     * Creates a new rectangle object
     * @color   The fill color of the rectangle
     * @return  Returns the newly created rectangle object
     */
    private Rectangle createRect(Color fillColor){
        Rectangle rectangle = new Rectangle();
    
        //Setting the properties of the rectangle
        rectangle.setWidth(100);
        rectangle.setHeight(35);
        rectangle.strokeWidthProperty().set(1);
    
        //Setting the height and width of the arc
        rectangle.setArcWidth(10.0);
        rectangle.setArcHeight(10.0);
        
        //Setting the Colors of the rectangle
        rectangle.setFill(fillColor);
        rectangle.setStroke(Color.BLACK);
        
        return rectangle;
    }
    
    /**
     * Creates a drag event
     * @param d the event being consumed
     * @param blockType the block type of the block being dragged and added
     */
    private void createDrag(Event d, BlockTypes blockType){
        System.out.println("DRAG STARTED!");
        Dragboard db = this.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.put(AppUtils.REF_BLOCK, new DummyBlock());
        db.setContent(content);
        db.setDragView(blockType.getDragIcon().getImage());
        d.consume();
    }
    
    /**
     * Creates all the Selection Listeners
     */
    private void setSelectionListeners(){
        //  --- Create Variable Block Listeners ---
        createVarBlock.setOnMousePressed(e -> {
            varOptionRec.strokeWidthProperty().set(3);
        });
        createVarBlock.setOnDragDetected(d -> {
            createDrag(d, BlockTypes.VARIABLE);
        });
        createVarBlock.setOnMouseReleased(e -> {
            varOptionRec.strokeWidthProperty().set(1);
        });
        createVarBlock.setOnDragOver(d ->{
            varOptionRec.strokeWidthProperty().set(1);
        });
        
    
        //  --- Create Function Block Listeners ---
        createFunctionBlock.setOnMousePressed(e -> {
            funcOptionRec.strokeWidthProperty().set(3);
        });
        createFunctionBlock.setOnDragDetected(d -> {
            createDrag(d, BlockTypes.FUNCTION);
        });
        createFunctionBlock.setOnMouseReleased(e -> {
            funcOptionRec.strokeWidthProperty().set(1);
        });
        createFunctionBlock.setOnDragOver(d -> {
            funcOptionRec.strokeWidthProperty().set(1);
        });
        
    
        //  --- Create Loop Block Listeners ---
        createLoopBlock.setOnMousePressed(e -> {
            loopOptionRec.strokeWidthProperty().set(3);
        });
        createLoopBlock.setOnDragDetected(d -> {
            createDrag(d, BlockTypes.LOOP);
        });
        createLoopBlock.setOnMouseReleased(e -> {
            loopOptionRec.strokeWidthProperty().set(1);
        });
        createLoopBlock.setOnDragOver(d -> {
            loopOptionRec.strokeWidthProperty().set(1);
        });
    }
    
    public BlockMenuView(){
        //  --- Setting the Create Variable Block   ---
        cVarData.setSpacing(10);
        cVarData.setPadding(new Insets(5));
        cVarData.setAlignment(Pos.CENTER);
        createVarBlock.setPrefSize(80, 30);
        createVarBlock.setPadding(new Insets(10));
        
    
        //  --- Setting the Create Function Block   ---
        cFuncData.setSpacing(10);
        cFuncData.setPadding(new Insets(5));
        cFuncData.setAlignment(Pos.CENTER);
        createFunctionBlock.setPrefSize(80, 30);
        createFunctionBlock.setPadding(new Insets(10));
        
    
        //  --- Setting the Create Loop Block   ---
        cLoopData.setSpacing(10);
        cLoopData.setPadding(new Insets(5));
        cLoopData.setAlignment(Pos.CENTER);
        createLoopBlock.setPrefSize(80, 30);
        createLoopBlock.setPadding(new Insets(10));
    
        setSelectionListeners();
    
        //  --- Menu Title  ---
        basicBlocksTab.setAlignment(Pos.TOP_CENTER);
        VBox spacer = new VBox();
        spacer.setFillWidth(true);
        spacer.setBorder(StyleUtils.getCurvedBorderGrey(0));
        basicBlocksTab.getChildren().add(spacer);
        
        //  --- Option List Setup   ---
        optionList.setSpacing(10);
        optionList.setPadding(new Insets(5,5,5,5));
        optionList.setAlignment(Pos.TOP_CENTER);
    
        //  --- Block Menu Tab Setup    ---
        this.setBorder(StyleUtils.getCurvedBorderGrey(5));
        this.setPrefSize(112, 300);
        this.setMaxSize(150,500);
        this.setAlignment(Pos.TOP_CENTER);
        
        this.getChildren().addAll(basicBlocksTab, optionList);
    }
}
