/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.natasha.examples;

import com.natasha.examples.models.DbTable;
import com.natasha.examples.models.TelephoneModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Stas
 */
public class NatashaJdbc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            DbHelper.initialize();
        } catch(ClassNotFoundException e) {
            System.out.println("The JDBC driver not found - "+e.getMessage());
            return;
        }
        
        try {
            DbHelper.getInstance().createDbIfNotExists();
        } catch(SQLException e) {
            e.printStackTrace();
            return;
        }
        
        Connection conn = null;
        try {
            conn = DbHelper.getInstance().getConnection();
            
            //----  Создаем нужные таблицы в БД, если их нету или очищаем, если есть  ----
            for (DbTable table : DataHelper.tables) {
                System.out.println("------------------------------------------------");
                boolean exists = table.isTableExists(conn);
                System.out.println(String.format("Check table '%s' exists - %b", table.getName(), exists));
                
                if(exists) {
                    table.clearTable(conn);
                } else {
                    table.createTable(conn);
                }
            }
            
            //----  проверяем БД на наличие ненужных таблиц, удаляем  ----
            ResultSet rs = conn.getMetaData().getTables(null, null, "%", null);
            try {
                if(rs.first()) {
                    do {
                        String tName = rs.getString(3);
                        if(!DataHelper.isValidTable(tName)) {
                            DbHelper.getInstance().dropTableByName(tName);
                        }
                    } while(rs.next());
                }
            } finally {
                rs.close();
            }
            
            
            
            for(TelephoneModel tel : DataHelper.telephones) {
                tel.crateOrUpdate(conn, DataHelper.tables[3]);
            }
            
            DataHelper.telephones[0].setTelephone("sdfsgsgsgsg45433");
            DataHelper.telephones[0].crateOrUpdate(conn, DataHelper.tables[3]);
            
            
            
        } catch(SQLException e) {
            System.out.println("Error creating tables - "+e.getMessage());
        } finally {
            try {
                DbHelper.getInstance().releaseConnection();
            } catch(SQLException e){}
        }
        
        
        
        
        
        
    }
    
}
