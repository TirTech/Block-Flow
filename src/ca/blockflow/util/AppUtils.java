package ca.blockflow.util;

import javafx.scene.text.Text;

public class AppUtils {
    
    public static String getResource(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("help_text.txt").getPath();
    }
}
