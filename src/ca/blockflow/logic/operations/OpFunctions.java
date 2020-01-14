package ca.blockflow.logic.operations;

import ca.blockflow.exceptions.OperationException;
import ca.blockflow.logic.SupportedTypes;
import ca.blockflow.logic.Variable;

public class OpFunctions {
    public static Variable asVar(Object val, SupportedTypes type) {
        return new Variable("", type, val);
    }
    
    static Variable PLUS(Variable varLeft, Variable varRight) throws OperationException {
        Variable val = null;
        switch (varLeft.getType()) {
            case INT:
                val = asVar(asInt(varLeft) + asInt(varRight), SupportedTypes.INT);
                break;
            case STRING:
                val = asVar(asStr(varLeft) + asStr(varRight), SupportedTypes.STRING);
                break;
            case DOUBLE:
                val = asVar(asDbl(varLeft) + asDbl(varRight), SupportedTypes.DOUBLE);
                break;
            case BOOLEAN:
                throw invalidType(varLeft,varRight);
        }
        return val;
    }
    
    static Variable MINUS(Variable varLeft, Variable varRight) throws OperationException {
        Variable val = null;
        switch (varLeft.getType()) {
            case INT:
                val = asVar(asInt(varLeft) - asInt(varRight), SupportedTypes.INT);
                break;
            case DOUBLE:
                val = asVar(asDbl(varLeft) - asDbl(varRight), SupportedTypes.DOUBLE);
                break;
            case STRING:
            case BOOLEAN:
                throw invalidType(varLeft,varRight);
        }
        return val;
    }
    
    static Variable MULTIPLY(Variable varLeft, Variable varRight) throws OperationException {
        Variable val = null;
        switch (varLeft.getType()) {
            case INT:
                val = asVar(asInt(varLeft) * asInt(varRight), SupportedTypes.INT);
                break;
            case STRING:
                if (varRight.getType() != SupportedTypes.INT)
                    throw invalidType(varLeft,varRight);
                StringBuilder newString = new StringBuilder();
                for (int i = 0; i < asInt(varRight); i++)
                    newString.append(asStr(varLeft));
                val = asVar(newString.toString(), SupportedTypes.STRING);
                break;
            case DOUBLE:
                val = asVar(asDbl(varLeft) * asDbl(varRight), SupportedTypes.DOUBLE);
                break;
            case BOOLEAN:
                throw invalidType(varLeft,varRight);
        }
        return val;
    }
    
    static Variable DIVIDE(Variable varLeft, Variable varRight) throws OperationException {
        Variable val = null;
        switch (varLeft.getType()) {
            case INT:
                val = asVar(asInt(varLeft) / asInt(varRight), SupportedTypes.INT);
                break;
            case DOUBLE:
                val = asVar(asDbl(varLeft) / asDbl(varRight), SupportedTypes.DOUBLE);
                break;
            case STRING:
            case BOOLEAN:
                throw invalidType(varLeft,varRight);
        }
        return val;
    }
    
    private static Integer asInt(Variable var) {
        return (Integer)var.getValue();
    }
    
    private static Double asDbl(Variable var) {
        return (Double)var.getValue();
    }
    
    private static Boolean asBool(Variable var) {
        return (Boolean)var.getValue();
    }
    
    private static String asStr(Variable var) {
        return (String)var.getValue();
    }
    
    private static OperationException invalidType(Variable varLeft, Variable varRight) {
        return new OperationException(String.format("Unsupported type: (%s,%s)", varLeft.getType(), varRight.getType()));
    }
}
