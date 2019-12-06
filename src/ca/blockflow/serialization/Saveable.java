package ca.blockflow.serialization;

import ca.blockflow.util.AppUtils;

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
    static <T extends Serializable> T load(Class<T> clazz, String fileName) {
        FileInputStream fileIn = null;
        ObjectInputStream objOut = null;
        try {
            fileIn = new FileInputStream(fileName);
            objOut = new ObjectInputStream(fileIn);
            T obj = clazz.cast(objOut.readObject());
            fileIn.close();
            objOut.close();
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            AppUtils.logError(e.getMessage());
            return null;
        } finally {
            try {
                if (fileIn != null) {
                    fileIn.close();
                }
                if (objOut != null) {
                    objOut.close();
                }
            } catch (IOException e) {
                AppUtils.logError(e.getMessage());
            }
        }
    }
    
    default void save(String fileName) {
        save(this, fileName);
    }
    
    /**
     * Save the given object to a file with the given name. Will not throw errors.
     * @param fileName the name of the file
     */
    static void save(Serializable obj, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(obj);
            fileOut.close();
            objOut.close();
        } catch (IOException e) {
            AppUtils.logError(e.getMessage());
        }
    }
}
