package ca.blockflow.logic;

import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.serialization.Saveable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Stored information about a variable and its type
 * @param <T> the type of the variable's value
 */
public class Variable<T> implements Saveable {
    private static final long serialVersionUID = 1L;
 
	/**
	 * The type defined by the application's supported types
	 * */
	private SupportedTypes type;

    /**
     * The name of the variable. Should not be blank
     */
    private SimpleStringProperty name = new SimpleStringProperty();

    /**
     * The variable value
     */
    private SimpleObjectProperty<T> value = new SimpleObjectProperty<>();
    
    /**
     * Creates a new variable with the given name, class (type), and default value
     * @param name        the name of the variable
     * @param initalValue the initial value of the variable
     */
    public Variable(String name, SupportedTypes type, T initalValue) {
        this.name.set(name);
        this.value.set(initalValue);
        this.type = type;
    }
    
    /**
     * Gets the value of the variable
     * @return the variable value
     */
    public T getValue() {
        return value.get();
    }
    
    /**
     * Sets the value of the variable
     * @param value the value to set to
     */
    public void setValue(T value) {
        this.value.set(value);
    }
    
    /**
     * Gets the variable's value as a property
     * @return the backing property of the variable's value
     */
    public SimpleObjectProperty<T> valueProperty() {
        return value;
    }
    
    /**
     * Gets the name of the variable
     * @return the variables name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Filters a given collection, returning a list of all variable items in the collection who's type matches (or is a subclass) of the type
     * of this variable. i.e. if this variable is of type Number, then this will return all collection variables of type Number, Integer, Double, etc.
     * , but not Strings (does not extend Number).
     * @param lst the list to filter
     * @return a filtered list of variables of type <code>Class<? extends T></code>
     */
    public ArrayList<Variable> getAllSimilarTyped(Collection<? extends Variable> lst) {
        return lst.stream().filter(v -> type == v.getType()).collect(Collectors.toCollection(ArrayList::new));
    }
    
    /**
     * Gets the class of the variables value as defined at creation.
     * @return the variables type
     */
    public SupportedTypes getType() {
        return type;
    }
    
    public boolean isSupportedType() {
        return this.type != null;
    }
    
    public String toString() {
        return name.get();
    }
    
    public String toDetailedString() {
        return "\"" + name + "\", of type: " + type + ".\tvalue:\t{" + value.get() + "}";
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(name.get());
        out.writeObject(type);
        out.writeObject(value.get());
    }
    
    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
        this.name = new SimpleStringProperty((String) in.readObject());
        this.type = (SupportedTypes) in.readObject();
        this.value = new SimpleObjectProperty(in.readObject());
    }
}
