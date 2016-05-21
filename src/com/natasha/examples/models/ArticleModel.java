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
 * @author Stas
 */
public class ArticleModel {
    private static final String ID_ARTICLE_COLUMN = "id_article";
    private static final String ID_SUBCATEGORIES_FURNITURE_COLUMN = "id_subcategories furniture";
    private static final String ID_COMPOSITE_COLUMN = "id_composite";
    private static final String ID_MANUFACTURER_COLUMN = "id_manufacturer";
    private static final String MANUFACTURER_CATALOG_NUMBER_COLUMN = "manufacturer_catalog_number";
    
    
    private int id;
    private int id_subcategories_furniture;
    private int id_composite;
    private int id_manufacturer;
    private int manufacturer_catalog_number;
    
    public ArticleModel(int id_subcategories_furniture, int id_composite, int id_manufacturer, int manufacturer_catalog_number) {
        this.id_subcategories_furniture = id_subcategories_furniture;
        this.id_composite = id_composite;
        this.id_manufacturer = id_manufacturer;
        this.manufacturer_catalog_number = manufacturer_catalog_number;
    }

    public int getArticle() {
        return id_subcategories_furniture;
    }

    public void setTelephone(String telephone) {
        this.id_subcategories_furniture = id_subcategories_furniture;
    }
    
    public void crateOrUpdate(Connection conn, DbTable table) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        try {
            System.out.println("-----------------------------------------------------");
            if(id  > 0) {
                String req = String.format("UPDATE %s SET %s='%s' WHERE %s=%d;", table.getName(), TELEPHONE_COLUMN, telephone, ID_COLUMN, id);
                System.out.println(req); 
                int nUpd = stmt.executeUpdate(req);
                System.out.println("Number affected rows = "+nUpd);
            } else {
                String req = String.format("INSERT INTO %s (%s) VALUES ('%s');", table.getName(), TELEPHONE_COLUMN, telephone);
                System.out.println(req);
                stmt.executeUpdate(req, Statement.RETURN_GENERATED_KEYS);
                rs = stmt.getGeneratedKeys();
                if(rs.first()) { 
                    id = rs.getInt(1);
                    System.out.println("Generated 'telephone_id' = "+id);
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
