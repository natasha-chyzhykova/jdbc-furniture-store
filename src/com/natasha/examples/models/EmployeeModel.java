/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.natasha.examples.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Nata
 */

public class EmployeeModel extends AbstractDbModel{
    private static final String EMPLOYEE_ID_COLUMN = "id_employee";
    private static final String DATA_HIRED_COLUMN = "date_hired";
    private static final String GIVEN_NAME_COLUMN = "given_name";
    private static final String MIDDLE_NAME_COLUMN = "middle_name";
    private static final String FAMILY_NAME_ID_COLUMN = "family_name";
    private static final String BIRTHDAY_ID_COLUMN = "birthday";
       
    
    private int id;
    private String date_hired;
    private String given_name;
    private String middle_name;
    private String family_name;
    private String birthday;
   
    
    public EmployeeModel(String date_hired, String given_name, String middle_name, String family_name, String birthday) {
        this.date_hired = date_hired;
        this.given_name = given_name;
        this.middle_name = middle_name;
        this.family_name = family_name;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public String getDate_hired() {
        return date_hired;
    }

    public void setDate_hired(String date_hired) {
        this.date_hired = date_hired;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    
    
    @Override
    public void crateOrUpdate(Connection conn, DbTable table) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        try {
            System.out.println("-----------------------------------------------------");
            if(id  > 0) {
                String req = String.format("UPDATE %s SET %s='%s', %s='%s', %s='%s', %s='%s',  %s='%s' WHERE (%s=%d);", table.getName(), DATA_HIRED_COLUMN, date_hired, 
                                                                                        GIVEN_NAME_COLUMN, given_name, MIDDLE_NAME_COLUMN, middle_name, 
                                                                                        FAMILY_NAME_ID_COLUMN, family_name, BIRTHDAY_ID_COLUMN, birthday, 
                                                                                        EMPLOYEE_ID_COLUMN, id);
                System.out.println(req);
                int nUpd = stmt.executeUpdate(req);
                System.out.println("Number affected rows = "+nUpd);
            } else {
                String req = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', '%s', '%s');", table.getName(), DATA_HIRED_COLUMN, GIVEN_NAME_COLUMN, MIDDLE_NAME_COLUMN, FAMILY_NAME_ID_COLUMN, BIRTHDAY_ID_COLUMN, date_hired, given_name, middle_name, family_name, birthday);
                System.out.println(req);
                stmt.executeUpdate(req, Statement.RETURN_GENERATED_KEYS);
                rs = stmt.getGeneratedKeys();
                if(rs.first()) {
                    id = rs.getInt(1);
                    System.out.println("Generated 'id_supplyier' = "+id);
                } else {
                    throw new SQLException("DB did not return generated primary key");
                }
            }
        } finally {
            if (rs != null) rs.close();
            stmt.close();
        }
    }
}

