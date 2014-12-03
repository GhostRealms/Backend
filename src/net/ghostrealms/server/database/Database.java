package net.ghostrealms.server.database;

import java.sql.*;

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
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database +
            "?username=" + username + "&password=" + password);
        } catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prepares an SQL Statement to be sent
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    private PreparedStatement prepareSqlStatement(String sql, Object[] params) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement(sql);

        int counter = 1;

        for (Object param : params) {
            if (param instanceof Integer) {
                stmt.setInt(counter++, (Integer) param);
            } else if (param instanceof Short) {
                stmt.setShort(counter++, (Short) param);
            } else if (param instanceof Long) {
                stmt.setLong(counter++, (Long) param);
            } else if (param instanceof Double) {
                stmt.setDouble(counter++, (Double) param);
            } else if (param instanceof String) {
                stmt.setString(counter++, (String) param);
            } else if (param == null) {
                stmt.setNull(counter++, Types.NULL);
            } else if (param instanceof Object) {
                stmt.setObject(counter++, param);
            } else {
                System.out.printf("Database -> Unsupported data type %s", param.getClass().getSimpleName());
            }
        }

        return stmt;
    }


    public void write(String statement, Object... params) throws SQLException {
        PreparedStatement stmt = this.prepareSqlStatement(statement, params);
        stmt.executeUpdate();
    }


}
