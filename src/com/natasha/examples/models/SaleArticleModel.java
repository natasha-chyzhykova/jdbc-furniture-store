/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.natasha.examples.models;

import static com.natasha.examples.models.SupplyArticleModel.ARTICLE_ID_COLUMN;
import static com.natasha.examples.models.SupplyArticleModel.PRICE_COLUMN;
import static com.natasha.examples.models.SupplyArticleModel.QUANTITY_COLUMN;
import static com.natasha.examples.models.SupplyArticleModel.SUPPLY_ID_COLUMN;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

/**
 *
 * @author Nata
 */
public class SaleArticleModel extends AbstractDbModel {
    public static final String SALE_ID_COLUMN = "id_sale";
    public static final String ARTICLE_ID_COLUMN = "id_article";
    public static final String QUANTITY_COLUMN = "quantity";
    public static final String PRICE_COLUMN = "price";
    
    private SaleModel sale;
    private ArticleModel article;
    private int quantity;
    private float price;

    public SaleArticleModel(SaleModel sale, ArticleModel article, int quantity, float price) {
        this.sale = sale;
        this.article = article;
        this.quantity = quantity;
        this.price = price;
    }

    public SaleModel getSale() {
        return sale;
    }

    public void setSale(SaleModel sale) {
        this.sale = sale;
    }

    public ArticleModel getArticle() {
        return article;
    }

    public void setArticle(ArticleModel article) {
        this.article = article;
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
            String req = String.format(Locale.UK, "INSERT INTO %s (%s, %s, %s, %s) VALUES (%d, %d, %d, %f);", table.getName(), SALE_ID_COLUMN, ARTICLE_ID_COLUMN, QUANTITY_COLUMN, PRICE_COLUMN, sale.getId(), article.getId(), quantity, price);
            try {
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Insert new instance OK");
            } catch(SQLException e) {
                System.out.println("Error insert new row - "+e.getMessage());
                req = String.format(Locale.UK, "UPDATE %s SET %s=%d, %s=%d, %s=%d, %s=%f WHERE (%s=%d AND %s=%d);", table.getName(),
                                                       SALE_ID_COLUMN, sale.getId(), ARTICLE_ID_COLUMN, article.getId(),
                                                       QUANTITY_COLUMN, quantity, PRICE_COLUMN, price, SALE_ID_COLUMN, sale.getId(), ARTICLE_ID_COLUMN, article.getId());
                System.out.println(req);
                stmt.executeUpdate(req);
                System.out.println("Updating row OK");
            }
         
        } finally {
            stmt.close();
        }
    }
}
