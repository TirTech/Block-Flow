package ca.blockflow.util;

import javafx.util.StringConverter;

public class ClassStringConverter extends StringConverter<Class> {
    @Override
    public String toString(Class object) {
        return object.getSimpleName();
    }
    
    @Override
    public Class fromString(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException e) {
            AppUtils.logError(e.getMessage());
            return Object.class;
        }
    }
}
