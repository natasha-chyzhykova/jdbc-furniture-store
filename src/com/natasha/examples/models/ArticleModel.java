/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.natasha.examples.models;

/**
 *
 * @author Nata
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArticleModel extends AbstractDbModel {
    private static final String ARTICLE_ID_COLUMN = "id_article";
    private static final String CATEGORY_ID_COLUMN = "id_category";
    private static final String SUBCATEGORY_ID_COLUMN = "id_subcategory";
    public static final String COMPOSITE_ID_COLUMN = "id_composite";
    private static final String MANUFACTURER_ID_COLUMN = "id_manufacturer";
    private static final String MANUFACTURER_CATALOG_NUMBER_COLUMN = "manufacturer_catalog_number";
    
    private int id;
    private SubcategoryModel subcategory;
    private CompositeModel composite;
    private ManufacturerModel manufacturer;
    private int manufacturer_catalog_number;

    public ArticleModel(SubcategoryModel subcategory, CompositeModel composite, ManufacturerModel manufacturer, int manufacturer_catalog_number) {
        this.subcategory = subcategory;
        this.composite = composite;
        this.manufacturer = manufacturer;
        this.manufacturer_catalog_number = manufacturer_catalog_number;
    }

    public int getId() {
        return id;
    }

    public SubcategoryModel getSubcategory() {
        return subcategory;
    }
    
    public CategoryModel getCategory() {
        return subcategory.getCategory();
    }

    public void setSubcategory(SubcategoryModel subcategory) {
        if (subcategory == null) throw new IllegalArgumentException("A valid materialId must be provided");
        this.subcategory = subcategory;
    }

    public CompositeModel getComposite() {
        return composite;
    }

    public void setComposite(CompositeModel composite) {
        this.composite = composite;
    }

    public ManufacturerModel getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerModel manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getManufacturer_catalog_number() {
        return manufacturer_catalog_number;
    }

    public void setManufacturer_catalog_number(int manufacturer_catalog_number) {
        this.manufacturer_catalog_number = manufacturer_catalog_number;
    }
    
    @Override
    public void crateOrUpdate(Connection conn, DbTable table) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        try {
            System.out.println("-----------------------------------------------------");
            if(id  > 0) {
                String req = String.format("UPDATE %s SET %s='%s', %s='%s', %s='%s', %s='%s', %s='%s' WHERE (%s=%d);", table.getName(), CATEGORY_ID_COLUMN, this.subcategory.getCategory().getId(), 
                                                                                                    SUBCATEGORY_ID_COLUMN, subcategory.getSubcategoryId(), COMPOSITE_ID_COLUMN, composite.getId(),
                                                                                                    MANUFACTURER_ID_COLUMN, manufacturer.getId(), MANUFACTURER_CATALOG_NUMBER_COLUMN, manufacturer_catalog_number, ARTICLE_ID_COLUMN, id);
                System.out.println(req);
                int nUpd = stmt.executeUpdate(req);
                System.out.println("Number affected rows = "+nUpd);
            } else {
                String req = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (%d, %d, %d, %d, %d);", table.getName(), CATEGORY_ID_COLUMN, SUBCATEGORY_ID_COLUMN, COMPOSITE_ID_COLUMN,
                                                                                            MANUFACTURER_ID_COLUMN, MANUFACTURER_CATALOG_NUMBER_COLUMN, subcategory.getCategory().getId(), 
                                                                                            subcategory.getSubcategoryId(), composite.getId(), manufacturer.getId(), manufacturer_catalog_number);
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
