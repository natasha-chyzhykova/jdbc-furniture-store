/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.natasha.examples;

import com.sun.xml.internal.ws.util.exception.JAXWSExceptionBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Stas
 */
public class DbHelper {
    public static final String DB_NAME = "furniture_store";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/";
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
             
    static final String USER = "root";
    static final String PASS = "qwerty";
     
     
    private static DbHelper instance;
     
    public static void initialize() throws ClassNotFoundException {
        if(instance == null) {
            instance = new DbHelper();
        }
    }
     
    public static DbHelper getInstance() {
        if (instance == null) {
            throw new IllegalStateException("The initialize() must be called first");
        }
        return instance;
    }
     
     
     
     
    private DbHelper() throws ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
    }
     
     
    private Connection connection;
    private int numOpened;
     
    
    public Connection getConnection() throws SQLException {
        if(connection == null) {
            connection = DriverManager.getConnection(DB_URL+DB_NAME, USER, PASS);
            numOpened = 1;
        } else {
            numOpened ++;
        }
        return connection;
    }
    
    public void releaseConnection() throws SQLException {
        if(numOpened > 1) {
            numOpened --;
        } else if (numOpened == 1) {
            connection.close();
            numOpened = 0;
        }
    }
    
    
    
    
    
     
    public void createDbIfNotExists() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Check for the "+DB_NAME+" database exists...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            ResultSet rs = conn.getMetaData().getCatalogs();
            boolean exists = false;
            if(rs.first()) {
                do {
                    String dbName = rs.getString(1);
                    if(DB_NAME.equals(dbName)) {
                        exists = true;
                        break;
                    }
                } while(rs.next());
            }
             
            if(!exists) {
                System.out.println("The "+DB_NAME+" database does not exists -> create one!");
                stmt = conn.createStatement();
                
                stmt.execute("CREATE DATABASE "+DB_NAME);
                System.out.println("The "+DB_NAME+" database has been successfully created");
            } else {
                System.out.println("The "+DB_NAME+" database exists.");
            }
             
        } finally {
            if(stmt!=null) stmt.close();
            if(conn != null) conn.close();
        }
    }
    
    
    
    
    
    public static int executeDbUpdate(Statement stmt, String request) throws SQLException {
        System.out.println("'"+request+"'");
        return stmt.executeUpdate(request, Statement.RETURN_GENERATED_KEYS);
    }
    
    
}
