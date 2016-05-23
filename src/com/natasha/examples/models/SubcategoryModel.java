/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.natasha.examples.models;

import com.sun.xml.internal.ws.util.exception.JAXWSExceptionBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Nata
 */
public class SubcategoryModel extends AbstractDbModel {
    private static final String CATEGORY_ID_COLUMN = "id_category";
    private static final String SUBCATEGORY_ID_COLUMN = "id_subcategory";
    private static final String NAME_COLUMN = "name";
    private static final String DESCRIPTION_COLUMN = "description";
    
    private CategoryModel category;
    private int subcategoryId;
    private String name;
    private String description;
    
    public SubcategoryModel(CategoryModel category, int subcategoryId, String name, String description) {
        if(category == null || category.getId() < 1) throw new IllegalArgumentException("A valid category must be provided");
        if(subcategoryId < 1) throw new IllegalArgumentException("A valid subcategory ID must be provided");
        this.category = category;
        this.subcategoryId = subcategoryId;
        this.name = name;
        this.description = description;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        if(category == null || category.getId() < 0) throw new IllegalArgumentException("A valid category must be provided");
        this.category = category;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        if(subcategoryId < 1) throw new IllegalArgumentException("A valid subcategory ID must be provided");
        this.subcategoryId = subcategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public void crateOrUpdate(Connection conn, DbTable table) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        try {
            System.out.println("-----------------------------------------------------");
            String req = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%d, %d, '%s', '%s');", table.getName(), CATEGORY_ID_COLUMN, SUBCATEGORY_ID_COLUMN, NAME_COLUMN, DESCRIPTION_COLUMN, category.getId(), subcategoryId, name, description);
            try {
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Inserted OK");
            } catch(SQLException e) {
                System.out.println("Error insert - "+e.getMessage());
                req = String.format("UPDATE %s SET %s='%s', %s='%s' WHERE (%s=%d AND %s=%d);", table.getName(), NAME_COLUMN, name, DESCRIPTION_COLUMN, description, CATEGORY_ID_COLUMN, category.getId(), SUBCATEGORY_ID_COLUMN, subcategoryId);
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Updated OK");
            }
        } finally {
            if (rs != null) rs.close();
            stmt.close();
        }
    }
}
