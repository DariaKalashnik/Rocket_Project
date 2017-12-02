package com.rocket.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBWorker {
    private static final String URL = "jdbc:mysql://localhost:3306/rocket_schema?useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true\"+\n"
            +"\"&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "myGoraHuge19";
    private Connection connection;

    // Establish connection with the Database
    public DBWorker() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Connection with DB is failed");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
