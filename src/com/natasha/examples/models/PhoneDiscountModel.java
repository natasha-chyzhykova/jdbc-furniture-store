/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.natasha.examples.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

/**
 *
 * @author Nata
 */
public class PhoneDiscountModel extends AbstractDbModel {
    public static final String TELEPHONE_ID_COLUMN = "id_phone";
    public static final String DISCOUNT_ID_COLUMN = "id_discount";
        
    private TelephoneModel telephone;
    private DiscountModel discount;

    public PhoneDiscountModel(TelephoneModel telephone, DiscountModel discount) {
        if ((telephone == null) || (telephone.getId() < 1)) throw new IllegalArgumentException("A valid phoneId must be provided");
        if ((discount == null) || (discount.getId() < 1)) throw new IllegalArgumentException("A valid discount must be provided");
        this.telephone = telephone;
        this.discount = discount;
    }

    public TelephoneModel getTelephone() {
        return telephone;
    }

    public void setTelephone(TelephoneModel telephone) {
        this.telephone = telephone;
    }

    public DiscountModel getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountModel discount) {
        this.discount = discount;
    }
    
    @Override
    public void crateOrUpdate(Connection conn, DbTable table) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            System.out.println("-----------------------------------------------------");
            String req = String.format(Locale.UK, "INSERT INTO %s (%s, %s) VALUES (%d, %d);", table.getName(), TELEPHONE_ID_COLUMN, DISCOUNT_ID_COLUMN, telephone.getId(), discount.getId());
            try {
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Insert new instance OK");
            } catch(SQLException e) {
                System.out.println("Error insert new row - "+e.getMessage());
                req = String.format(Locale.UK, "UPDATE %s SET %s=%d, %s=%d WHERE (%s=%d AND %s=%d);", table.getName(),
                                                       TELEPHONE_ID_COLUMN, telephone.getId(), DISCOUNT_ID_COLUMN, discount.getId(),
                                                       TELEPHONE_ID_COLUMN, telephone.getId(), DISCOUNT_ID_COLUMN, discount.getId());
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Updating row OK");
            }
         
        } finally {
            stmt.close();
        }
    }
    
}
