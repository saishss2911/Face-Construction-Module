package com.example.trial1;

import java.sql.*;

public class SqliteConnection {
    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\91883\\IdeaProjects\\trial1\\src\\main\\feature.db");
            return conn;
        } catch (Exception e) {
            return null;
        }
    }

}
