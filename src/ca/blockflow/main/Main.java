package ca.blockflow.main;

import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.testing.TestingCode;
import ca.blockflow.util.StyleUtils;
import ca.blockflow.views.*;
import ca.blockflow.views.floweditor.FlowView;
import ca.blockflow.views.floweditor.FunctionBlockView;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    
    private static ExceptionView bottomView;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void stop() {
        System.out.println("Bye!");
    }
    
    public static ExceptionView getConsoleView() {
        return bottomView;
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
    }
    
    public void start(Stage primaryStage) {
        primaryStage.setTitle("BlockFlow");
        primaryStage.getIcons().add(StyleUtils.getLogoAsIcon());
        doSplash(primaryStage);
    }
    
    /**
     * Sets the first stage of the application. Called by {@link #doSplash} after fading out.
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     */
    private void postInit(Stage primaryStage) {

//        ArrayList<Variable> vars = new ArrayList<>();
//        Variable a = new Variable("a", SupportedTypes.INT, 5);
//        Variable b = new Variable("b", SupportedTypes.INT, -5);
//        Variable c = new Variable("c", SupportedTypes.INT, 2);
//        Variable d = new Variable("d", SupportedTypes.DOUBLE, 5.26);
//        Variable e = new Variable("e", SupportedTypes.STRING, "string");
//        Variable f = new Variable("f", SupportedTypes.BOOLEAN, true);
//        vars.addAll(Arrays.asList(a, b, c, d, e, f));
//
//
//        bottomView = new ExpressionsView(SupportedTypes.DOUBLE, FXCollections.observableArrayList(vars));
        
        AppModel model = AppModel.getInstance();
    
        //Containers
        VBox root = new VBox();
        primaryStage.setScene(new Scene(root, 800, 800));
        BorderPane content = new BorderPane();
    
        //Define the panes
        bottomView = new ExceptionView();
        MenuBar menus = buildMenuBar();
        FlowView flowView = new FlowView();
        BlockMenuView blockMenu = new BlockMenuView();
        VariableView varView = new VariableView();
        FunctionBlockView bv = new FunctionBlockView(null);
        FlowControls controls = new FlowControls();
        VBox rightPane = new VBox(controls, varView);
    
        //Setup layout
        model.setRootBlockView(bv);
        rightPane.setSpacing(5);
        content.setPadding(new Insets(5));
        content.setRight(rightPane);
        content.setLeft(blockMenu);
        content.setBottom(bottomView);
        content.setCenter(flowView);
        root.getChildren().addAll(menus, content);
        flowView.setRootView(bv);
    
        ///////////////////////////////////////////////
        try {
            TestingCode.test();
        } catch (ExceptionHandler exceptionHandler) {
            exceptionHandler.printStackTrace();
        }
        //////////////////////////////////////////////
    }
    
    private MenuBar buildMenuBar() {
        MenuBar menu = new MenuBar();
        Menu mnuHelp = new Menu("Help");
        Menu mnuFile = new Menu("File");
        MenuItem miHelp = new MenuItem("Help");
        MenuItem miAbout = new MenuItem("About");
        MenuItem miColor = new MenuItem("Color Preferences");
        MenuItem miQuit = new MenuItem("Quit");
        mnuFile.getItems().addAll(miColor, miQuit);
        mnuHelp.getItems().addAll(miAbout, miHelp);
        menu.getMenus().addAll(mnuFile, mnuHelp);
        
        miAbout.setOnAction(e -> {
            AboutView about = new AboutView();
            about.show();
        });
        
        miHelp.setOnAction(e -> {
            HelpView help = new HelpView();
            help.show();
        });
    
        miColor.setOnAction(e -> {
            ColorPrefView colorPref = new ColorPrefView();
            colorPref.show();
        });
    
        miQuit.setOnAction(e -> Platform.exit());
    
        return menu;
    }
}
