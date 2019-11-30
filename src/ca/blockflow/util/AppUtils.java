package ca.blockflow.util;

import javafx.scene.input.DataFormat;

import javafx.scene.text.Text;

public class AppUtils {
    
    public static DataFormat REF_BLOCK = new DataFormat("ref/block");
    
    public static String getResource(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("help_text.txt").getPath();
    }
}
