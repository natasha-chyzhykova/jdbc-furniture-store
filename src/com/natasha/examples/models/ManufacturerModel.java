package com.natasha.examples.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ManufacturerModel extends AbstractDbModel {
    private static final String ID_COLUMN = "id_manufacturer";
    private static final String NAME_COLUMN = "name";
    private static final String ADDRESS_COLUMN = "address";
    private static final String EMAIL_COLUMN = "contact_email";
    private static final String WEBSITE_COLUMN = "website";
    private static final String DIRECTOR_ID_COLUMN = "id_director";
    
    private int id;
    private String name;
    private String address;
    private String email;
    private String website;
    private DirectorModel director;
    
    private ManufacturerModel(int id, String name, String address, String email, String website, DirectorModel director) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.website = website;
        if(director != null && director.getId() > 0) {
            this.director = director;
        } else {
            throw new IllegalArgumentException("Valid director instance must be provided");
        }
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public DirectorModel getDirector() {
        return director;
    }

    public void setDirector(DirectorModel director) {
        if(director != null && director.getId() > 0) {
            this.director = director;
        } else {
            throw new IllegalArgumentException("Valid director instance must be provided");
        }
    }

    
    @Override
    public void crateOrUpdate(Connection conn, DbTable table) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        try {
            System.out.println("-----------------------------------------------------");
            if(id  > 0) {
                String req = String.format("UPDATE %s SET %s='%s', %s='%s', %s='%s', %s='%s', %s=%d WHERE (%s=%d);", table.getName(), NAME_COLUMN, name, ADDRESS_COLUMN, address, EMAIL_COLUMN, email, WEBSITE_COLUMN, website, DIRECTOR_ID_COLUMN, director.getId(), ID_COLUMN, id);
                System.out.println(req);
                int nUpd = stmt.executeUpdate(req);
                System.out.println("Number affected rows = "+nUpd);
            } else {
                String req = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', '%s', %d);", table.getName(), NAME_COLUMN, ADDRESS_COLUMN, EMAIL_COLUMN, WEBSITE_COLUMN, DIRECTOR_ID_COLUMN, name, address, email, website, director.getId());
                System.out.println(req);
                stmt.executeUpdate(req, Statement.RETURN_GENERATED_KEYS);
                rs = stmt.getGeneratedKeys();
                if(rs.first()) {
                    id = rs.getInt(1);
                    System.out.println("Generated 'id_manufacturer' = "+id);
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
