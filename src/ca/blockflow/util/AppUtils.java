package ca.blockflow.util;

import javafx.scene.input.DataFormat;

public class AppUtils {
    
    public static final DataFormat REF_BLOCK_VIEW = new DataFormat("ref/block_view");
    public static final DataFormat REF_BLOCK_TYPE = new DataFormat("ref/block_type");
    
    public static String getResource(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("help_text.txt").getPath();
    }
}
