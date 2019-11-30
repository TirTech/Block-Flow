package ca.blockflow.logic;

import ca.blockflow.exceptions.ExceptionHandler;

import java.util.function.BiFunction;


@FunctionalInterface
public interface BiFunctionUtil<T, U, R> {
    public R apply(T o, U o2) throws ExceptionHandler;
    
}
