package com.example.demo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoDataBase {
    private Connection conn = null;
    public synchronized Connection getConnetion() throws ClassNotFoundException, SQLException{
        if (conn == null){
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/trabaFinal", "postgres", "postgres");
        }
        return conn;
    }
}
