package ca.blockflow.logic.operations;

import ca.blockflow.exceptions.OperationException;
import ca.blockflow.logic.SupportedTypes;
import ca.blockflow.logic.VariableBiFunction;
import ca.blockflow.logic.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public enum Operation {
    NO_OP ((l,r)->null),
    PLUS (OpFunctions::PLUS,
          OpType.simpleType(SupportedTypes.INT),
          OpType.simpleType(SupportedTypes.STRING),
          OpType.simpleType(SupportedTypes.DOUBLE)),
    MINUS (OpFunctions::MINUS,
           OpType.simpleType(SupportedTypes.INT),
           OpType.simpleType(SupportedTypes.DOUBLE)),
    MULTIPLY (OpFunctions::MULTIPLY,
              OpType.simpleType(SupportedTypes.INT),
              OpType.simpleType(SupportedTypes.DOUBLE),
              OpType.typeWithMod(SupportedTypes.STRING,SupportedTypes.INT),
              new OpType(SupportedTypes.DOUBLE,SupportedTypes.DOUBLE,SupportedTypes.STRING)),
    DIVIDE (OpFunctions::DIVIDE,
            OpType.simpleType(SupportedTypes.INT),
            OpType.simpleType(SupportedTypes.DOUBLE));
    
    OpType[] types;
    VariableBiFunction func;
    Operation(VariableBiFunction func, OpType... types) {
        this.func = func;
        this.types = types;
    }
    
    public Variable perform(Variable varLeft, Variable varRight) throws OperationException {
        return this.func.apply(varLeft,varRight);
    }
    
    public static ArrayList<Operation> getOpsWithReturnType(SupportedTypes type) {
        return Arrays.stream(Operation.values()).filter(op -> Arrays.stream(op.types).anyMatch(t -> t.returnType == type)).collect(Collectors.toCollection(ArrayList::new));
    }
    
    public Set<SupportedTypes> getRestrictedATypesForOp(Collection<SupportedTypes> returnTypes) {
        return Arrays.stream(this.getTypes()).filter(t -> returnTypes.contains(t.returnType)).map(t -> t.getTypeA()).collect(Collectors.toSet());
    }
    
    public Set<SupportedTypes> getRestrictedBTypesForOp(Collection<SupportedTypes> returnTypes) {
        return Arrays.stream(this.getTypes()).filter(t -> returnTypes.contains(t.returnType)).map(t -> t.getTypeB()).collect(Collectors.toSet());
    }
    
    public OpType[] getTypes() {
        return types;
    }
    
}
