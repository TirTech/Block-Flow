package ca.blockflow.main;

import ca.blockflow.testing.TestingCode;
import ca.blockflow.util.StyleUtils;
import ca.blockflow.views.AboutView;
import ca.blockflow.views.ColorPrefView;
import ca.blockflow.views.HelpView;
import ca.blockflow.views.VariableView;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void stop() {
        System.out.println("Bye!");
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
        splashPane.getChildren().add(StyleUtils.getLogo(true, 500));
        Scene splashScene = new Scene(splashPane);
        
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
        VBox root = new VBox();
        BorderPane content = new BorderPane();
        //FlowView flowView = new FlowView();
        Label nodeView = new Label("nodeView");
        Label bottomView = new Label("bottomView");
        VariableView varView = new VariableView(FXCollections.observableArrayList());
        MenuBar menus = buildMenuBar();
        content.setPadding(new Insets(5));
        content.setRight(varView);
        content.setLeft(nodeView);
        content.setBottom(bottomView);
        //content.setCenter(flowView);
        root.getChildren().addAll(menus, content);
        primaryStage.setScene(new Scene(root, 500, 500));
        //flowView.setRootView(new BlockView());
    }
    
    private MenuBar buildMenuBar() {
        MenuBar menu = new MenuBar();
        Menu mnuHelp = new Menu("Help");
        MenuItem miHelp = new MenuItem("Help");
        MenuItem miAbout = new MenuItem("About");
        MenuItem miPref = new MenuItem("Color Preferences");
        mnuHelp.getItems().addAll(miAbout, miHelp, miPref);
        menu.getMenus().addAll(mnuHelp);
        
        miAbout.setOnAction(e -> {
            AboutView about = new AboutView();
            about.show();
        });
        
        miHelp.setOnAction(e -> {
            HelpView help = new HelpView();
            help.show();
        });
        miPref.setOnAction(e -> {
            ColorPrefView colorPref = new ColorPrefView();
            colorPref.show();
        });
        
        return menu;
    }
}
