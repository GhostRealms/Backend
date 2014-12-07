package net.ghostrealms.server.database;

import net.ghostrealms.server.BackendClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by River on 07-Dec-14.
 * Database Manager
 */
public class DatabaseManager {

    private Connection connection = null;

    public DatabaseManager(BackendClient backendClient) {

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new NullPointerException("could not find the driver");
        }
    }

    public static Connection getConnection(String db_name) {
        try {
            return DriverManager.getConnection("jdbc:h2:~/" + db_name);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Could not load the db");
        }
    }

}
