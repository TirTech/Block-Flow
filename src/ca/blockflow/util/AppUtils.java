package ca.blockflow.util;

import ca.blockflow.models.AppModel;
import ca.blockflow.serialization.Saveable;
import ca.blockflow.serialization.SerialBlockTree;
import ca.blockflow.views.floweditor.BlockView;
import ca.blockflow.views.floweditor.FunctionBlockView;
import javafx.scene.input.DataFormat;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class AppUtils {
    
    public static final DataFormat REF_BLOCK_VIEW = new DataFormat("ref/block_view");
    public static final DataFormat REF_BLOCK_TYPE = new DataFormat("ref/block_type");
    private static HashMap<String, Object> refboard = new HashMap<>();
    
    public static String getResource(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("help_text.txt").getPath();
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
    
    public static void logMessage(String message) {
        AppModel.getInstance().getConsole().logMessage(message, false);
    }
    
    public static void logError(String message) {
        AppModel.getInstance().getConsole().logMessage(message, true);
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
