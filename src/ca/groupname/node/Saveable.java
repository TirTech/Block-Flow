package ca.groupname.node;

import java.io.*;

public interface Saveable extends Serializable {
	
	default void save(String fileName) {
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName + ".flow");
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
}
