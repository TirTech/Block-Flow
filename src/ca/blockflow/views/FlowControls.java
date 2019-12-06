package ca.blockflow.views;

import ca.blockflow.controllers.FlowControlsController;
import ca.blockflow.util.StyleUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class FlowControls extends HBox {
    
    private Button btnPlay = new Button();
    private Button btnPause = new Button();
    private Button btnStep = new Button();
    private Button btnStop = new Button();
    
    public FlowControls() {
        super(5);
        double BUTTON_SIZE = 25;
        btnPlay.setGraphic(StyleUtils.getImage("controls/play.png", BUTTON_SIZE));
        btnPause.setGraphic(StyleUtils.getImage("controls/pause.png", BUTTON_SIZE));
        btnStep.setGraphic(StyleUtils.getImage("controls/step-forward.png", BUTTON_SIZE));
        btnStop.setGraphic(StyleUtils.getImage("controls/stop.png", BUTTON_SIZE));
        getChildren().addAll(btnPlay, btnPause, btnStep, btnStop);
        setBorder(StyleUtils.getCurvedBorderGrey(5));
        setPadding(new Insets(5));
        FlowControlsController controller = new FlowControlsController(this);
        controller.setHandlers();
    }
    
    public Button getBtnPlay() {
        return btnPlay;
    }
    
    public Button getBtnPause() {
        return btnPause;
    }
    
    public Button getBtnStep() {
        return btnStep;
    }
    
    public Button getBtnStop() {
        return btnStop;
    }
}
