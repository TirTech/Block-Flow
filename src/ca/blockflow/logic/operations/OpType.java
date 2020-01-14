package ca.blockflow.logic.operations;

import ca.blockflow.logic.SupportedTypes;

public class OpType {
    SupportedTypes typeA;
    SupportedTypes typeB;
    SupportedTypes returnType;
    
    public OpType(SupportedTypes typeA, SupportedTypes typeB, SupportedTypes returnType) {
        this.typeA = typeA;
        this.typeB = typeB;
        this.returnType = returnType;
    }
    
    public static OpType typeWithMod(SupportedTypes type, SupportedTypes modType) {
        return new OpType(type, modType, type);
    }
    
    public static OpType simpleType(SupportedTypes type) {
        return new OpType(type, type, type);
    }
    
    public SupportedTypes getTypeA() {
        return typeA;
    }
    
    public SupportedTypes getTypeB() {
        return typeB;
    }
    
    public SupportedTypes getReturnType() {
        return returnType;
    }
}
