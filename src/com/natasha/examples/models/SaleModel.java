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

public class SaleModel extends AbstractDbModel{
    private static final String SALE_ID_COLUMN = "id_sale";
    private static final String EMPLOYEE_ID_COLUMN = "id_employee";
    private static final String DISCOUNT_ID_COLUMN = "id_discount";
    private static final String DATA_COLUMN = "date";
    
    private int id;
    private EmployeeModel employee;
    private DiscountModel discount;
    private String date;

    public SaleModel(EmployeeModel employee, DiscountModel discount, String date) {
        if(employee != null && employee.getId() > 0) {
            this.employee = employee;
        } else {
            throw new IllegalArgumentException("Valid director instance must be provided");
        }
        if(discount != null && discount.getId() > 0) {
            this.discount = discount;
        } else {
            throw new IllegalArgumentException("Valid director instance must be provided");
        }
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        if(employee != null && employee.getId() > 0) {
            this.employee = employee;
        } else {
            throw new IllegalArgumentException("Valid director instance must be provided");
        }
    }

    public DiscountModel getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountModel discount) {
        if(discount != null && discount.getId() > 0) {
            this.discount = discount;
        } else {
            throw new IllegalArgumentException("Valid director instance must be provided");
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public void crateOrUpdate(Connection conn, DbTable table) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        try {
            System.out.println("-----------------------------------------------------");
            if(id  > 0) {
                String req = String.format("UPDATE %s SET %s='%s', %s='%s', %s='%s' WHERE (%s=%d);", table.getName(), EMPLOYEE_ID_COLUMN, employee.getId(), DISCOUNT_ID_COLUMN, discount.getId(), DATA_COLUMN, date, SALE_ID_COLUMN, id);
                System.out.println(req);
                int nUpd = stmt.executeUpdate(req);
                System.out.println("Number affected rows = "+nUpd);
            } else {
                String req = String.format("INSERT INTO %s (%s, %s, %s) VALUES (%d, %d, '%s');", table.getName(), EMPLOYEE_ID_COLUMN, DISCOUNT_ID_COLUMN, DATA_COLUMN, employee.getId(), discount.getId(), date);
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

