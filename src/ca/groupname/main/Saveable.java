package ca.groupname.main;

import java.io.*;

/**
 * Interface that allows a class to be saveable to a file. Wraps Serializable and exposes methods directly on objects
 */
public interface Saveable extends Serializable {
    
    /**
     * Load a class from a file and cast it to the given type
     * @param clazz    the class to cast to
     * @param fileName the file to load from
     * @param <T>      the type of the loaded Object
     * @return the loaded object as type <code>T extends Saveable</code>
     */
    static <T extends Saveable> T load(Class<T> clazz, String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream(fileName + ".flow");
            ObjectInputStream objOut = new ObjectInputStream(fileIn);
            return clazz.cast(objOut.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Save this object to a file with the given name. Will not throw errors.
     * @param fileName the name of the file
     */
    default void save(String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName + ".flow");
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
