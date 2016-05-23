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
public class PhoneDirectorModel extends AbstractDbModel {
    public static final String TELEPHONE_ID_COLUMN = "id_phone";
    public static final String DIRECTOR_ID_COLUMN = "id_director";
        
    private TelephoneModel telephone;
    private DirectorModel director;
        
    public PhoneDirectorModel(TelephoneModel phone, DirectorModel director) {
        if ((phone == null) || (phone.getId() < 1)) throw new IllegalArgumentException("A valid phoneId must be provided");
        if ((director == null) || (director.getId() < 1)) throw new IllegalArgumentException("A valid directorId must be provided");
        this.telephone = phone;
        this.director = director;
    }

    public TelephoneModel getTelephone() {
        return telephone;
    }

    public void setTelephone(TelephoneModel telephone) {
        this.telephone = telephone;
    }

    public DirectorModel getDirector() {
        return director;
    }

    public void setDirector(DirectorModel director) {
        this.director = director;
    }
        
    @Override
    public void crateOrUpdate(Connection conn, DbTable table) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            System.out.println("-----------------------------------------------------");
            String req = String.format(Locale.UK, "INSERT INTO %s (%s, %s) VALUES (%d, %d);", table.getName(), TELEPHONE_ID_COLUMN, DIRECTOR_ID_COLUMN, telephone.getId(), director.getId());
            try {
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Insert new instance OK");
            } catch(SQLException e) {
                System.out.println("Error insert new row - "+e.getMessage());
                req = String.format(Locale.UK, "UPDATE %s SET %s=%d, %s=%d WHERE (%s=%d AND %s=%d);", table.getName(),
                                                       TELEPHONE_ID_COLUMN, telephone.getId(), DIRECTOR_ID_COLUMN, director.getId(),
                                                       TELEPHONE_ID_COLUMN, telephone.getId(), DIRECTOR_ID_COLUMN, director.getId());
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Updating row OK");
            }
         
        } finally {
            stmt.close();
        }
    }
}
