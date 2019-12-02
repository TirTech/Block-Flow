package ca.blockflow.util;

import javafx.scene.input.DataFormat;

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
}
