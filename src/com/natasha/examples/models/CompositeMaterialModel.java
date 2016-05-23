/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.natasha.examples.models;

import static com.natasha.examples.models.CompositeModel.DESCRIPTION_COLUMN;
import static com.natasha.examples.models.CompositeModel.ID_COLUMN;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

/**
 *
 * @author Nata
 */
public class CompositeMaterialModel extends AbstractDbModel {
    public static final String MATERIAL_ID_COLUMN = "id_material";
    public static final String COMPOSITE_ID_COLUMN = "id_composite";
    public static final String PRICE_COLUMN = "price";
    
    private MaterialModel material;
    private CompositeModel composite;
    private float price;
    
    public CompositeMaterialModel(CompositeModel composite, MaterialModel material, float price) {
        if ((material == null) || (material.getId() < 1)) throw new IllegalArgumentException("A valid materialId must be provided");
        if ((composite == null) || (composite.getId() < 1)) throw new IllegalArgumentException("A valid compositeId must be provided");
        this.material = material;
        this.composite = composite;
        this.price = price;
    }

    public MaterialModel getMaterial() {
        return material;
    }

    public void setMaterial(MaterialModel material) {
        if ((material == null) || (material.getId() < 1)) throw new IllegalArgumentException("A valid materialId must be provided");
        this.material = material;
    }

    public CompositeModel getComposite() {
        return composite;
    }

    public void setComposite(CompositeModel composite) {
        if ((composite == null) || (composite.getId() < 1)) throw new IllegalArgumentException("A valid compositeId must be provided");
        this.composite = composite;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    

    
    
    @Override
    public void crateOrUpdate(Connection conn, DbTable table) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            System.out.println("-----------------------------------------------------");
            String req = String.format(Locale.UK, "INSERT INTO %s (%s, %s, %s) VALUES (%d, %d, %f);", table.getName(), MATERIAL_ID_COLUMN, COMPOSITE_ID_COLUMN, PRICE_COLUMN, material.getId(), composite.getId(), price);
            try {
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Insert new instance OK");
            } catch(SQLException e) {
                System.out.println("Error insert new row - "+e.getMessage());
                req = String.format(Locale.UK, "UPDATE %s SET %s=%f WHERE (%s=%d AND %s=%d);", table.getName(),
                                                       PRICE_COLUMN, price,
                                                       MATERIAL_ID_COLUMN, material.getId(), COMPOSITE_ID_COLUMN, composite.getId());
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Updating row OK");
            }
            
            
        } finally {
            stmt.close();
        }
    }
    
    
    
}
