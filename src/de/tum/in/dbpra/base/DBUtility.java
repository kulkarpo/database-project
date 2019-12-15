package de.tum.in.dbpra.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.tum.in.dbpra.config.Database;

public class DBUtility {
    /**
     * @return an Instance of java.sql.Connection, created according to the configuration found in config.DBUtility
     * @throws ClassNotFoundException In case the JDBC-Driver could not be found.
     * @throws SQLException           In case the Connection could not be established.
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        //DriverManager.register(new org.postgresql.Driver());
        return DriverManager.getConnection(
                "jdbc:postgresql://"
                        + Database.SERVER
                        + (!Database.PORT.equals("") ? "" : ":" + Database.PORT)
                        + "/" + Database.DATABASE,
                Database.USER,
                Database.PASSWORD);
    }
}
