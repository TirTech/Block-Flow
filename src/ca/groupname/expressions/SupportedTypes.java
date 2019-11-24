package ca.groupname.expressions;

public enum SupportedTypes {
    STRING(String.class),
    INT(Integer.class),
    DOUBLE(Double.class);

    private final Class clazz;

    SupportedTypes(Class clazz) {
        this.clazz = clazz;
    }

    public Class getTypeClass() {
        return this.clazz;
    }

    public SupportedTypes determineType(Class clazz) {
        for (SupportedTypes t : SupportedTypes.values()) {
            if (clazz == t.clazz) {
                return t;
            }
        }
        return null;
    }
}
