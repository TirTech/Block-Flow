package ca.blockflow.util;

public class AppUtils {
    
    public static String getResource(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("help_text.txt").getPath();
    }
}
