package ca.blockflow.logic;

import ca.blockflow.exceptions.ExceptionHandler;


@FunctionalInterface
public interface BiFunctionUtil<T, U, R> {
    R apply(T o, U o2) throws ExceptionHandler;
    
}
