package ca.groupname;

import java.util.function.Consumer;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{
	
	public void start(Stage primaryStage) {
		VBox root = new VBox();
		Label placeholder = new Label("This is a blank placeholder");
		root.getChildren().add(placeholder);
		doSplash(primaryStage, new Scene(root, 500, 500));	
	}
	
	private void doSplash(Stage primaryStage, Scene nextScene) {
		
		//Build splash scene
		VBox splashPane = new VBox();
		Image image = new Image(getClass().getResourceAsStream("logo.png"));
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
		
		// Do the transitions and register callbacks
		fadeIn.setOnFinished( e -> fadeOut.play());
		fadeOut.setOnFinished(e -> primaryStage.setScene(nextScene));
		fadeIn.play();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
