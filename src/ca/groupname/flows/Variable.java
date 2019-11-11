package ca.groupname.flows;

import javafx.beans.property.SimpleObjectProperty;

/**
 * Stored information about a variable and its type
 * @param <T> the type of the variable's value
 */
public class Variable<T> {
	
	/**
	 * The name of the variable. Should not be blank
	 */
	private String name;
	/**
	 * The type of the variable extending Object
	 */
	private Class<T> clazz;
	/**
	 * The variable value
	 */
	private SimpleObjectProperty<T> value = new SimpleObjectProperty<>();
	
	public Variable(String name, Class<T> clazz, T initalValue) {
		this.clazz = clazz;
		this.name = name;
		this.value.set(initalValue);
	}
	
	public Variable(String name, Class<T> clazz) {
		this.clazz = clazz;
		this.name = name;
	}
	
	public T getValue() {
		return value.get();
	}
	
	public void setValue(T value) {
		this.value.set(value);
	}
	
	public SimpleObjectProperty<T> valueProperty() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public Class<T> getType() {
		return clazz;
	}
}
