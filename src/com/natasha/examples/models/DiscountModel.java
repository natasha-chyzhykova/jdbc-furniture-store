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
public class DiscountModel extends AbstractDbModel {
    private static final String DISCOUNT_ID_COLUMN = "id_discount";
    private static final String CARD_NUMBER_COLUMN = "card_number";
    private static final String DATE_REGISTRATION_COLUMN = "date_registration";
    private static final String GIVEN_NAME_COLUMN = "given_name";
    private static final String MIDDLE_NAME_COLUMN = "middle_name";
    private static final String FAMILY_NAME_ID_COLUMN = "family_name";
    private static final String BIRTHDAY_ID_COLUMN = "birthday";
    private static final String CONTACT_EMAIL_ID_COLUMN = "contact_email";
    
    
    private int id;
    private String card_number;
    private String date_registration;
    private String given_name;
    private String middle_name;
    private String family_name;
    private String birthday;
    private String contact_email;
    
    
    public DiscountModel(String card_number, String date_registration, String given_name, String middle_name, String family_name, String birthday, String contact_email) {
        this.card_number = card_number;
        this.date_registration = date_registration;
        this.given_name = given_name;
        this.middle_name = middle_name;
        this.family_name = family_name;
        this.birthday = birthday;
        this.contact_email = contact_email;
    }

    public int getId() {
        return id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getDate_registration() {
        return date_registration;
    }

    public void setDate_registration(String date_registration) {
        this.date_registration = date_registration;
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

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }
    
    
    
    @Override
    public void crateOrUpdate(Connection conn, DbTable table) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        try {
            System.out.println("-----------------------------------------------------");
            if(id  > 0) {
                String req = String.format("UPDATE %s SET %s='%s', %s='%s', %s='%s', %s='%s',  %s='%s', %s='%s', %s='%s' WHERE (%s=%d);", table.getName(), CARD_NUMBER_COLUMN, card_number, 
                                                                                        DATE_REGISTRATION_COLUMN, date_registration, GIVEN_NAME_COLUMN, given_name, 
                                                                                        MIDDLE_NAME_COLUMN, middle_name, FAMILY_NAME_ID_COLUMN, family_name, 
                                                                                        BIRTHDAY_ID_COLUMN, birthday, CONTACT_EMAIL_ID_COLUMN, contact_email, 
                                                                                        DISCOUNT_ID_COLUMN, id);
                System.out.println(req);
                int nUpd = stmt.executeUpdate(req);
                System.out.println("Number affected rows = "+nUpd);
            } else {
                String req = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');", table.getName(), CARD_NUMBER_COLUMN, DATE_REGISTRATION_COLUMN, GIVEN_NAME_COLUMN, MIDDLE_NAME_COLUMN, FAMILY_NAME_ID_COLUMN, BIRTHDAY_ID_COLUMN, CONTACT_EMAIL_ID_COLUMN, card_number, date_registration, given_name, middle_name, family_name, birthday, contact_email);
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
                                                                                        