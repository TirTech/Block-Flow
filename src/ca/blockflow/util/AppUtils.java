package ca.blockflow.util;

import ca.blockflow.models.AppModel;
import ca.blockflow.serialization.Saveable;
import ca.blockflow.serialization.SerialBlockTree;
import ca.blockflow.views.floweditor.BlockView;
import ca.blockflow.views.floweditor.FunctionBlockView;
import javafx.scene.input.DataFormat;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class AppUtils {
    
    private static final Level LOGGING_LEVEL = Level.ALL;
    public static final DataFormat REF_BLOCK_VIEW = new DataFormat("ref/block_view");
    public static final DataFormat REF_BLOCK_TYPE = new DataFormat("ref/block_type");
    private static HashMap<String, Object> refboard = new HashMap<>();
    
    public static String getResource(String filename) {
        URL res = Thread.currentThread().getContextClassLoader().getResource(filename);
        return res != null ? res.getPath() : null;
    }
    
    public static String addToRefBoard(Object obj) {
        String key = obj.hashCode() + "";
        refboard.put(key, obj);
        return key;
    }
    
    public static Object getFromRefBoard(String key) {
        Object obj = refboard.get(key);
        refboard.remove(key);
        return obj;
    }
    
    public static boolean fileExists(String name) {
        return Files.exists(new File(name).toPath());
    }
    
    public static void saveAppVariables(String filename) {
        Saveable.save(new ArrayList<>(AppModel.getInstance().getVariables()), filename);
    }
    
    public static void loadAppVariables(String filename) {
        AppModel.getInstance().setVariables(Saveable.load(ArrayList.class, filename));
    }
    
    public static void logMessage(Throwable ex) {
        logMessage(ex.getMessage());
        if (LOGGING_LEVEL.intValue() <= Level.INFO.intValue()) {
            System.out.println("This was the exception that was logged \n");
            ex.printStackTrace();
        }
    }
    
    public static void logMessage(String message) {
        AppModel.getInstance().getConsole().logMessage(message, false);
        if (LOGGING_LEVEL.intValue() <= Level.INFO.intValue()) {
            System.out.println("This was the message that was logged: \n" + message);
        }
    }
    
    public static void logError(Throwable ex) {
        logError(ex.getMessage());
        if (LOGGING_LEVEL.intValue() <= Level.WARNING.intValue()) {
            System.out.println("This was the exception that was thrown \n");
            ex.printStackTrace();
        }
    }
    
    public static void logError(String message) {
        AppModel.getInstance().getConsole().logMessage(message, true);
        if (LOGGING_LEVEL.intValue() <= Level.WARNING.intValue()) {
            System.out.println("This was the error message that was logged: \n" + message);
        }
    }
    
    public static BlockView loadBlockView(String filename) {
        SerialBlockTree tree = Saveable.load(SerialBlockTree.class, filename);
        return new FunctionBlockView(tree, null);
    }
    
    public static void saveBlockView(String filename) {
        SerialBlockTree tree = AppModel.getInstance().getRootBlockView().serializeTree();
        tree.save(filename);
    }
}
