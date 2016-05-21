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
 * @author stanislav.perchenko
 */
public class SupplyModel extends AbstractDbModel {
    private static final String ID_COLUMN = "id_supply";
    private static final String SUPPLYER_COLUMN = "id_supplyier";
    private static final String DATE_COLUMN = "date";
    
    private int id;
    private SupplyerModel supplyer;
    private String date;
    
    public SupplyModel(SupplyerModel supplyer, String date) {
        if(supplyer != null && supplyer.getId() > 0) {
            this.supplyer = supplyer;
        } else {
            throw new IllegalArgumentException("Valid supplyer instance must be provided");
        }
        this.date = date;
    }
    
    public int getId() {
        return id;
    }

    public SupplyerModel getSupplyer() {
        return supplyer;
    }

    public void setSupplyer(SupplyerModel supplyer) {
        if(supplyer != null && supplyer.getId() > 0) {
            this.supplyer = supplyer;
        } else {
            throw new IllegalArgumentException("Valid supplyer instance must be provided");
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
    @Override
    public void crateOrUpdate(Connection conn, DbTable table) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        try {
            System.out.println("-----------------------------------------------------");
            if(id  > 0) {
                String req = String.format("UPDATE %s SET %s=%d, %s='%s' WHERE (%s=%d);", table.getName(), SUPPLYER_COLUMN, supplyer.getId(), DATE_COLUMN, date, ID_COLUMN, id);
                System.out.println(req);
                int nUpd = stmt.executeUpdate(req);
                System.out.println("Number affected rows = "+nUpd);
            } else {
                String req = String.format("INSERT INTO %s (%s, %s) VALUES ('%s', %d);", table.getName(), SUPPLYER_COLUMN, , EMAIL_COLUMN, WEBSITE_COLUMN, DIRECTOR_ID_COLUMN, name, address, email, website, director.getId());
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
