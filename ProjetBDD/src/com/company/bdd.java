package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class bdd {

	static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver"; //le nom du driver 
    static final String DB_URL = "jdbc: mariadb:localhost:medecin"; 
    //  Database credentials
    static final String USER = "root"; 
    static final String PASS = "projetjava"; 
    
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public Connection getConn() {
        return conn;
    }
    public ResultSet getResultSet() {
        return resultSet;
    }
    
    public void connection() throws Exception{
        try{
            Class.forName(JDBC_DRIVER);
            // Setup the connection with the DB
            conn = DriverManager
                    .getConnection(DB_URL, USER, PASS);

            // Statements allow to issue SQL queries to the database
            stmt = conn.createStatement();
        }catch (Exception e) {
            throw e;
        }
    }
}
