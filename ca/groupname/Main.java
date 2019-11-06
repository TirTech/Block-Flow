package ca.groupname;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{
	
	public void start(Stage primaryStage) {
		doSplash(primaryStage);
	}
	
	/**
	 * Performs initialization of the application. Called by {@link doSplash} before fading out.
	 * @param primaryStage the primary stage for this application, onto which the application scene can be set.
	 */
	private void preInit(Stage primaryStage) {
		Connection db = DataManager.getInstance().getDatabaseConnection();
		try {
			ResultSet rs = db.getMetaData().getTables(null, null, "%", null);
			while (rs.next()) {
				System.out.println(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the first stage of the application. Called by {@link doSplash} after fading out.
	 * @param primaryStage the primary stage for this application, onto which the application scene can be set.
	 */
	private void postInit(Stage primaryStage) {
		VBox root = new VBox();
		Label placeholder = new Label("This is a blank placeholder");
		root.getChildren().add(placeholder);	
		primaryStage.setScene(new Scene(root, 500, 500));
	}
	
	/**	
	 * Displays the splash screen image inside the stage by fading in and out. Calls {@link preInit} after fading in and {@link postInit} after fading out.
	 * @param primaryStage the primary stage for this application, onto which the application scene can be set.
	 */
	private void doSplash(Stage primaryStage) {
		
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
		
		// Register callbacks and start transitions
		fadeIn.setOnFinished( e -> {
			preInit(primaryStage); 
			fadeOut.play();
		});
		fadeOut.setOnFinished(e -> postInit(primaryStage));
		fadeIn.play();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
