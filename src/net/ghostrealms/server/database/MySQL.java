package net.ghostrealms.server.database;

import net.ghostrealms.server.BackendClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by River on 30-Nov-14.
 * Establishes the connection with the MySQL Instance of the specified server in the config
 */
public class MySQL {

    private Connection connection = null;

    private String username;
    private String password;
    private String hostname;
    private int port = 3306;

    public MySQL(String user, String pass, String host, int port) {
        username = user;
        password = pass;
        hostname = host;
        this.port = port;
        establish();
    }

    public Connection getConnection() {
        if(connection != null)
            return connection;
        throw new NullPointerException("The connection does not exist");
    }

    public int use(String database) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeUpdate("USE " + database + ";");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int create(String database) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeUpdate("CREATE DATABASE " + database + ";");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public void close() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void establish() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch(ClassNotFoundException ex) {
            ex.printStackTrace();
            //Hopefully disable when the connection cannot be established
            BackendClient.getPlugin(BackendClient.class).getServer().getPluginManager().disablePlugin(new BackendClient());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            //get the connection, but do not connect to a database.
            connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/?username=" + username
                    + "&password=" + password);
        } catch (SQLException ex) {
            ex.printStackTrace();
            //Hopefully disable when the connection cannot be established
            BackendClient.getPlugin(BackendClient.class).getServer().getPluginManager().disablePlugin(new BackendClient());
        }
    }

}
