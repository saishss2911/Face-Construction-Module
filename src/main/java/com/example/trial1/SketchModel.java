package com.example.trial1;

import java.sql.*;

public class SketchModel {
    Connection connection;

    public SketchModel() {
        connection = SqliteConnection.Connector();
        if (connection == null) ;
    }

    public boolean dbConnected() {
        try {
            return !connection.isClosed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
