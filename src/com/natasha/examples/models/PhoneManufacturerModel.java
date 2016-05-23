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
public class PhoneManufacturerModel extends AbstractDbModel {
    public static final String TELEPHONE_ID_COLUMN = "id_phone";
    public static final String MANUFACTURER_ID_COLUMN = "id_manufacturer";
        
    private TelephoneModel telephone;
    private ManufacturerModel manufacturer;
        
    public PhoneManufacturerModel(TelephoneModel phone, ManufacturerModel manufacturer) {
        if ((phone == null) || (phone.getId() < 1)) throw new IllegalArgumentException("A valid phoneId must be provided");
        if ((manufacturer == null) || (manufacturer.getId() < 1)) throw new IllegalArgumentException("A valid manufacturerId must be provided");
        this.telephone = phone;
        this.manufacturer = manufacturer;
    }

    public TelephoneModel getTelephone() {
        return telephone;
    }

    public void setTelephone(TelephoneModel telephone) {
        this.telephone = telephone;
    }

    public ManufacturerModel getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerModel manufacturer) {
        this.manufacturer = manufacturer;
    }
        
    @Override
    public void crateOrUpdate(Connection conn, DbTable table) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            System.out.println("-----------------------------------------------------");
            String req = String.format(Locale.UK, "INSERT INTO %s (%s, %s) VALUES (%d, %d);", table.getName(), TELEPHONE_ID_COLUMN, MANUFACTURER_ID_COLUMN, telephone.getId(), manufacturer.getId());
            try {
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Insert new instance OK");
            } catch(SQLException e) {
                System.out.println("Error insert new row - "+e.getMessage());
                req = String.format(Locale.UK, "UPDATE %s SET %s=%d, %s=%d WHERE (%s=%d AND %s=%d);", table.getName(),
                                                       TELEPHONE_ID_COLUMN, telephone.getId(), MANUFACTURER_ID_COLUMN, manufacturer.getId(),
                                                       TELEPHONE_ID_COLUMN, telephone.getId(), MANUFACTURER_ID_COLUMN, manufacturer.getId());
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Updating row OK");
            }
         
        } finally {
            stmt.close();
        }
    }
}
