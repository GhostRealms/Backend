package net.ghostrealms.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by River on 30-Nov-14.
 * Internal Database Management
 */
public class Database {

    private Connection connection = null;

    private String hostname = "localhost";
    private int port = 3306;
    private String username;
    private String password;
    private String database;

    //Assumes that the Host & port are 'localhost' and 3306 respectively
    public Database(String database, String user, String pass) {
        this.database = database;
        username = user;
        password = pass;

        establish();
    }

    public Database(String database, String user, String pass, String hostname, int port) {
        this.database = database;
        username = user;
        password = pass;
        this.hostname = hostname;
        this.port = port;

        establish();
    }

    public Connection getConnection() {
        if(connection != null)
            return connection;
        throw new NullPointerException("Connection is null!");
    }

    private void establish() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database +
            "?username=" + username + "&password=" + password);
        } catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}
