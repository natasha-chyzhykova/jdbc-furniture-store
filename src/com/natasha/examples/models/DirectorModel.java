package com.natasha.examples.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DirectorModel extends AbstractDbModel {
    private static final String ID_COLUMN = "id_director";
    private static final String GIVEN_NAME_COLUMN = "given_name";
    private static final String MIDDLE_NAME_COLUMN = "middle_name";
    private static final String FAMITY_NAME_COLUMN = "family_name";
    private static final String BIRTHDAY_COLUMN = "birthday";
    private static final String EMAIL_COLUMN = "contact_email";
    
    private int id;
    private String givenName;
    private String middleName;
    private String familyName;
    private String birthday;
    private String email;
    
    public DirectorModel(String givenName, String middleName, String familyName, String birthday, String email) {
        this.givenName = givenName;
        this.middleName = middleName;
        this.familyName = familyName;
        this.birthday = birthday;
        this.email = email;
    }
    
    public int getId() {
        return id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    @Override
    public void crateOrUpdate(Connection conn, DbTable table) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        try {
            System.out.println("-----------------------------------------------------");
            if(id  > 0) {
                String req = String.format("UPDATE %s SET %s='%s', %s='%s', %s='%s', %s='%s', %s=%d WHERE (%s=%d);", table.getName(), GIVEN_NAME_COLUMN, givenName, MIDDLE_NAME_COLUMN, middleName, FAMITY_NAME_COLUMN, familyName, BIRTHDAY_COLUMN, birthday, EMAIL_COLUMN, email, ID_COLUMN, id);
                System.out.println(req);
                int nUpd = stmt.executeUpdate(req);
                System.out.println("Number affected rows = "+nUpd);
            } else {
                String req = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', '%s', '%s');", table.getName(), GIVEN_NAME_COLUMN, MIDDLE_NAME_COLUMN, FAMITY_NAME_COLUMN, BIRTHDAY_COLUMN, EMAIL_COLUMN, givenName, middleName, familyName, birthday, email);
                System.out.println(req);
                stmt.executeUpdate(req, Statement.RETURN_GENERATED_KEYS);
                rs = stmt.getGeneratedKeys();
                if(rs.first()) {
                    id = rs.getInt(1);
                    System.out.println("Generated 'id_director' = "+id);
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
