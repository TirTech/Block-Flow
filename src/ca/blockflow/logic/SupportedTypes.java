package ca.blockflow.logic;

import java.util.Arrays;
import java.util.List;

public enum SupportedTypes {
    STRING(String.class),
    INT(Integer.class),
    DOUBLE(Double.class),
    BOOLEAN(Boolean.class);
    
    private final Class clazz;

    SupportedTypes(Class clazz) {
        this.clazz = clazz;
    }
    
    public static List<SupportedTypes> valuesAsList() {
        return Arrays.asList(values());
    }
    
    public Class getTypeClass() {
        return this.clazz;
    }

    public static SupportedTypes determineType(Class clazz) {
        for (SupportedTypes t : SupportedTypes.values()) {
            if (clazz.equals(t.clazz)) {
                return t;
            }
        }
        return null;
    }
}
