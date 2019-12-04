package ca.blockflow.main;

import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.models.AppModel;
import ca.blockflow.serialization.Saveable;
import ca.blockflow.testing.TestingCode;
import ca.blockflow.util.AppUtils;
import ca.blockflow.util.StyleUtils;
import ca.blockflow.views.*;
import ca.blockflow.views.floweditor.BlockView;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Main extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void stop() {
        System.out.println("Bye!");
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
        if (AppUtils.fileExists("ColorPallet.flow")) {
            System.out.println("Loading Colors...");
            AppModel.getInstance().setColors(Saveable.load(BlockColorPalette.class, "ColorPallet.flow"));
        } else {
            System.out.println("No color preferences.");
        }
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
        AppModel model = AppModel.getInstance();
    
        //Containers
        BorderPane content = new BorderPane();
        primaryStage.setScene(new Scene(content, 800, 800));
    
        //Define the panes
        MenuBar menus = buildMenuBar(primaryStage);
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
        content.setBottom(AppModel.getInstance().getConsole());
        content.setCenter(flowView);
        content.setTop(menus);
        flowView.setRootView(bv);
    
        ///////////////////////////////////////////////
        try {
            TestingCode.test();
        } catch (ExceptionHandler exceptionHandler) {
            exceptionHandler.printStackTrace();
        }
        //////////////////////////////////////////////
    }
    
    private MenuBar buildMenuBar(Stage primaryStage) {
        MenuBar menu = new MenuBar();
        Menu mnuHelp = new Menu("Help");
        Menu mnuFile = new Menu("File");
        Menu mnuDebug = new Menu("Debug");
        MenuItem miLoad = new MenuItem("Load Block");
        MenuItem miSave = new MenuItem("Save Block");
        MenuItem miHelp = new MenuItem("Help");
        MenuItem miAbout = new MenuItem("About");
        MenuItem miColors = new MenuItem("Color Preferences");
        MenuItem miQuit = new MenuItem("Quit");
        MenuItem miTestSerial = new MenuItem("Serialize Test");
    
        mnuDebug.getItems().addAll(miTestSerial);
        mnuFile.getItems().addAll(miColors, miSave, miLoad, miQuit);
        mnuHelp.getItems().addAll(miAbout, miHelp);
        menu.getMenus().addAll(mnuFile, mnuHelp, mnuDebug);
        
        miSave.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Save Flow");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BlockFlow Flow", "*.bflw"));
            File file = chooser.showSaveDialog(primaryStage.getOwner());
            AppUtils.saveBlockView(file.getAbsolutePath());
        });
        
        miLoad.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Load Flow");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BlockFlow Flow", "*.bflw"));
            File file = chooser.showOpenDialog(primaryStage.getOwner());
            FunctionBlockView view = (FunctionBlockView) AppUtils.loadBlockView(file.getAbsolutePath());
            AppModel.getInstance().setRootBlockView(view);
        });
        
        miTestSerial.setOnAction(e -> {
            System.out.println("Saving Object...");
            AppUtils.saveBlockView("TEST_SERIAL.bflw");
            System.out.println("Reloading Object...");
            BlockView view = AppUtils.loadBlockView("TEST_SERIAL.bflw");
            System.out.println("Breakpoint");
        });
        
        miAbout.setOnAction(e -> {
            AboutView about = new AboutView();
            about.show();
        });
        
        miHelp.setOnAction(e -> {
            HelpView help = new HelpView();
            help.show();
        });
    
        miColors.setOnAction(e -> {
            ColorPrefView colorPref = new ColorPrefView();
            colorPref.show();
        });
    
        miQuit.setOnAction(e -> Platform.exit());
    
        return menu;
    }
}
