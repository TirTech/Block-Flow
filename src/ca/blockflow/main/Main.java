package ca.blockflow.main;

import ca.blockflow.testing.TestingCode;
import ca.blockflow.views.VariableView;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void stop() {
    }
    
    public void start(Stage primaryStage) {
        doSplash(primaryStage);
    }
    
    /**
     * Displays the splash screen image inside the stage by fading in and out. Calls {@link #preInit} after fading in and {@link #postInit} after fading out.
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     */
    private void doSplash(Stage primaryStage) {
        
        //Build splash scene
        VBox splashPane = new VBox();
        Image image = new Image("logo.png");
        ImageView imgView = new ImageView(image);
        splashPane.getChildren().add(imgView);
        Scene splashScene = new Scene(splashPane, 500, 500);
        
        //Set up stage
        primaryStage.setScene(splashScene);
        primaryStage.show();
        
        //Set up transition animations
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3.0), splashPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3.0), splashPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
        
        // Register callbacks and start transitions
        fadeIn.setOnFinished(e -> {
            preInit(primaryStage);
            fadeOut.play();
        });
        fadeOut.setOnFinished(e -> postInit(primaryStage));
        fadeIn.play();
    }
    
    /**
     * Performs initialization of the application. Called by {@link #doSplash} before fading out.
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     */
    private void preInit(Stage primaryStage) {
        // Do stuff
        TestingCode.test();
    }
    
    /**
     * Sets the first stage of the application. Called by {@link #doSplash} after fading out.
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     */
    private void postInit(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Label graphView = new Label("graphView");
        Label nodeView = new Label("nodeView");
        Label bottomView = new Label("bottomView");
        VariableView varView = new VariableView(FXCollections.observableArrayList());
        root.setRight(varView);
        root.setLeft(nodeView);
        root.setBottom(bottomView);
        root.setCenter(graphView);
        root.setPadding(new Insets(5));
        primaryStage.setScene(new Scene(root, 500, 500));
    
    }
}
