package ca.groupname;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javafx.application.Platform;

/**
 * Singleton management class for persistent data. Contains database connections, properties, and other data to be accessed by classes
 */
public class DataManager {
	
	private Properties appProperties = null;
	private Connection databaseConn = null;
	
	private static DataManager instance = null;
	
	private DataManager() {
		appProperties = new Properties();
		try {
			appProperties.load(getClass().getResourceAsStream("app.properties"));
			System.out.println("Loaded Properties: " + appProperties.toString());
			String dbFile = getClass().getResource(appProperties.getProperty("databaseURL")).toString();
			databaseConn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
			System.out.println("Properties and connection to database loaded");
		} catch (Exception e) {
			//If something goes wrong, bail!
			e.printStackTrace();
			Platform.exit();
		}
	}
	
	/**
	 * Gets the instance of this class, or creates it.
	 * @return the instance of the data manager class
	 */
	public static DataManager getInstance() {
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}

	public Properties getAppProperties() {
		return appProperties;
	}

	public Connection getDatabaseConnection() {
		return databaseConn;
	}
}
