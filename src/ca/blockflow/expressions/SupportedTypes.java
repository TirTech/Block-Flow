package ca.blockflow.expressions;

import java.util.ArrayList;
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
            if (clazz == t.clazz) {
                return t;
            }
        }
        return null;
    }
    
    public static SupportedTypes highestPromotion(ArrayList<SupportedTypes> types) {
        SupportedTypes highestType = null;
        if (types.contains(STRING)) {
            highestType = STRING;
        }
        else if (types.contains(DOUBLE)) {
            highestType = DOUBLE;
        }
        else if (types.contains(INT)) {
            highestType = INT;
        }
        else if (types.contains(BOOLEAN)) {
            highestType = BOOLEAN;
        }
        return highestType;
    }
}
