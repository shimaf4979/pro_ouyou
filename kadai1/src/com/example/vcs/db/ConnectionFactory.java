package com.example.vcs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:sqlite:versioncontrol.db";
    private ConnectionFactory() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}