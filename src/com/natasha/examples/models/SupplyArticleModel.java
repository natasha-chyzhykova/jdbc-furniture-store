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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class SupplyArticleModel extends AbstractDbModel {
    public static final String SUPPLY_ID_COLUMN = "id_supply";
    public static final String ARTICLE_ID_COLUMN = "id_article";
    public static final String QUANTITY_COLUMN = "quantity";
    public static final String PRICE_COLUMN = "price";
    
    private SupplyModel supply;
    private ArticleModel article;
    private int quantity;
    private float price;

    public SupplyArticleModel(SupplyModel supply, ArticleModel article, int quantity, float price) {
        if(supply != null && supply.getId() > 0) {
            this.supply = supply;
        } else {
            throw new IllegalArgumentException("Valid director instance must be provided");
        }
        if(article != null && article.getId() > 0) {
            this.article = article;
        } else {
            throw new IllegalArgumentException("Valid director instance must be provided");
        }
        this.quantity = quantity;
        this.price = price;
    }

    public SupplyModel getSupply() {
        return supply;
    }

    
    public ArticleModel getArticle() {
        return article;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
            String req = String.format(Locale.UK, "INSERT INTO %s (%s, %s, %s, %s) VALUES (%d, %d, %d, %f);", table.getName(), SUPPLY_ID_COLUMN, ARTICLE_ID_COLUMN, QUANTITY_COLUMN, PRICE_COLUMN, supply.getId(), article.getId(), quantity, price);
            try {
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Insert new instance OK");
            } catch(SQLException e) {
                System.out.println("Error insert new row - "+e.getMessage());
                req = String.format(Locale.UK, "UPDATE %s SET %s=%d, %s=%d, %s=%d, %s=%f WHERE (%s=%d AND %s=%d);", table.getName(),
                                                       SUPPLY_ID_COLUMN, supply.getId(), ARTICLE_ID_COLUMN, article.getId(),
                                                       QUANTITY_COLUMN, quantity, PRICE_COLUMN, price, SUPPLY_ID_COLUMN, supply.getId(), ARTICLE_ID_COLUMN, article.getId());
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Updating row OK");
            }
         
        } finally {
            stmt.close();
        }
    }
}

