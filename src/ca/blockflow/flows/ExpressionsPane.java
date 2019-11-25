package ca.blockflow.flows;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class ExpressionsPane extends VBox {

    TextField blockDataPoint;
    final BigInteger ZERO_INT = new BigInteger("0");
    final BigInteger ONE_INT = new BigInteger("1");
    final BigDecimal ZERO_DEC = new BigDecimal("0");
    final BigDecimal ONE_DEC = new BigDecimal("1");

    public ExpressionsPane() {
        // blockDataPoint will be defined based on the type of the data point in the block
        blockDataPoint = new TextField();
        blockDataPoint.setText(ZERO_INT.toString());
        this.getChildren().addAll(intInput(blockDataPoint), intArithmeticOp());
    }

    public void stringExpression() {

    }

    private HBox intArithmeticOp() {
        HBox operation = new HBox();
        BigInteger n = new BigInteger(blockDataPoint.getText());
        ArrayList<String> ops = new ArrayList<>(Arrays.asList("+", "-", "*", "/", "**", "%", "!"));
        if (n.compareTo(ZERO_INT) == 0) {
            ops.remove("/");
        }
        operation.getChildren().addAll(genOperations(ops), intInput());
        return operation;
    }

    private ComboBox genOperations(ArrayList<String> ops) {
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll(ops);
        return new ComboBox(options);
    }

    private HBox intInput(TextField... textFields) {
        HBox hBox = new HBox();
        TextField textField;
        if (textFields.length == 0) {
            textField = new TextField();
        }
        else {
            textField = blockDataPoint;
        }
        textField.setMinSize(50, 40);
        textField.setMaxSize(100,40);
        textField.setFont(new Font("Times", 25));
        textField.setDisable(true);
        ToggleButton increment = new ToggleButton("UP");
        ToggleButton decrement = new ToggleButton("DOWN");
        ToggleGroup toggleGroup = new ToggleGroup();
        Image up = new Image("resources/up_arrow.png");
        Image down = new Image("resources/down_arrow.png");
        ImageView upArrow = new ImageView(up);
        ImageView downArrow = new ImageView(down);
        increment.setGraphic(upArrow);
        decrement.setGraphic(downArrow);
        upArrow.setFitWidth(15);
        upArrow.setFitHeight(8);
        downArrow.setFitWidth(15);
        downArrow.setFitHeight(8);

        increment.setOnMouseClicked(event -> {
            BigInteger n = new BigInteger(textField.getText());
            textField.setText(n.add(ONE_INT).toString());
            System.out.println("increment clicked: " + n + ", textfield: " + textField);
//            this.layoutChildren();
        });
        decrement.setOnMouseClicked(event -> {
            BigInteger n = new BigInteger(textField.getText());
            textField.setText(n.subtract(ONE_INT).toString());
            System.out.println("decrement clicked: " + n + ", textfield: " + textField);
            this.layoutChildren();
        });

        toggleGroup.getToggles().addAll(increment, decrement);
        increment.setMinSize(30, 20);
        decrement.setMinSize(30, 20);
        increment.setMaxSize(30, 20);
        decrement.setMaxSize(30, 20);
        textField.setText("0"); // needs to be adjusted for editing purposes
        VBox vBox = new VBox();
        vBox.getChildren().addAll(increment, decrement);
        hBox.getChildren().addAll(textField, vBox);
        return hBox;
    }

    private boolean validateNumberRange(BigInteger... args) {
        int argsL = args.length;
        BigInteger x, start, end;
        switch(argsL) {
            case 1  :   return true;
            case 2  :   x = args[0];
                        end = args[1];
                        return x.compareTo(end) < 0;
            case 3  :   x = args[0];
                        start = args[1];
                        end = args[2];
                        return x.compareTo(start) >= 0 && x.compareTo(end) < 0;
            default :   return false;
        }
    }
}
