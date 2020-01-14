package ca.blockflow.logic;

import ca.blockflow.exceptions.OperationException;

@FunctionalInterface
public interface VariableBiFunction {
    Variable apply(Variable o, Variable o2) throws OperationException;
}
