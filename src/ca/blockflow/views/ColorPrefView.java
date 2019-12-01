package ca.blockflow.views;

import ca.blockflow.blocks.BlockTypes;
import ca.blockflow.main.AppModel;
import ca.blockflow.util.StyleUtils;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

public class ColorPrefView extends Dialog {
    
    public ColorPrefView() {
        super();
        setTitle("Color Picker");
        //create color picker
    
        ComboBox<BlockTypes> blockOptions = new ComboBox<>(FXCollections.observableArrayList(Arrays.asList(BlockTypes.values())));
    
        Rectangle rect = new Rectangle(50, 15);
        rect.setFill(Color.WHITE);
        ColorPicker cPicker = new ColorPicker(Color.BLUE);
        BlockColorPalette colorPalette = AppModel.getInstance().getColors();
    
        Label cPickerLabel = new Label("Pick a block and a Color.");
        cPicker.getStyleClass().add("button");
        Button setColorBtn = new Button("Set Color");
        GridPane previewPane = new GridPane();
    
        setColorBtn.setOnAction(e -> {
            rect.setFill(cPicker.getValue());
            colorPalette.setColor(blockOptions.getValue(), cPicker.getValue());
        });
    
        blockOptions.valueProperty().addListener((obs, old, newVal) -> {
            //set color of picker based on selected block
            cPicker.setValue(colorPalette.getColor(blockOptions.getValue()).get());
            rect.setFill(cPicker.getValue());
        });
        /*
        These panes are a mess.
        You have a root grid pane(1 col, 2 rows)
        Root grid pane has label in first row,
        second grid pane in second row,
        second grid pane contains color info
        and preview pane showing old number in bottom right of grid.
        */
        
        DialogPane pane = getDialogPane();
        pane.setMinSize(400, 200);
        //pane.setMaxSize(400,200);
        pane.getButtonTypes().add(ButtonType.CLOSE);
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
    
        gridPane.setMinSize(400, 200);
        gridPane.setMaxSize(400, 200);
        gridPane.getColumnConstraints().add(new ColumnConstraints(150)); // column 0 is 100 wide
        gridPane.getColumnConstraints().add(new ColumnConstraints(250));
    
        Label previewLabel = new Label("Old Color: ");
        previewPane.setPadding(new Insets(10, 10, 10, 10));
        previewPane.setBorder(StyleUtils.getCurvedBorderGrey(5));
        previewPane.addRow(0, previewLabel, rect);
    
        gridPane.addRow(0, blockOptions, setColorBtn);
        gridPane.addRow(1, previewPane, cPicker);
    
        //fixing layout
        GridPane rootGPane = new GridPane();
        rootGPane.setMinSize(400, 200);
        rootGPane.setMaxSize(400, 200);
        rootGPane.getRowConstraints().add(new RowConstraints(30));
        rootGPane.getColumnConstraints().add(new ColumnConstraints(400));
        rootGPane.addRow(0, cPickerLabel);
        rootGPane.addRow(1, gridPane);
    
        pane.setContent(rootGPane);
        //help with block dropdown selection
    
        blockOptions.getSelectionModel().selectFirst();
    }//end constructor
    
    
}
