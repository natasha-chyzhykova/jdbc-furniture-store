/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.natasha.examples;

import com.natasha.examples.models.DbTable;
import com.natasha.examples.models.TelephoneModel;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Nata
 */
public class DataHelper {
    
    public static DbTable[] tables = new DbTable[] {
        //----  Таблицы корневых сущностей  ----
/*0*/        new DbTable("manufacturer", "id_manufacturer INT NOT NULL AUTO_INCREMENT, name VARCHAR(25) NOT NULL, address VARCHAR(35) NOT NULL, contact_email VARCHAR(25) NULL, website VARCHAR(255) NULL,  id_director INT UNSIGNED NOT NULL", "id_manufacturer"),
/*1*/        new DbTable("supplyier", "id_supplyier INT NOT NULL AUTO_INCREMENT, name VARCHAR(25) NOT NULL, address VARCHAR(35) NOT NULL, contact_email VARCHAR(25) NULL, website VARCHAR(255) NULL, id_director INT UNSIGNED NOT NULL", "id_supplyier"),
/*2*/        new DbTable("director", "id_director INT NOT NULL AUTO_INCREMENT, given_name VARCHAR(45) NOT NULL, middle_name VARCHAR(45) NULL, family_name VARCHAR(45) NOT NULL, birthday DATE NOT NULL, contact_email VARCHAR(35) NULL", "id_director"),
/*3*/        new DbTable("telephone", "id_telephone INT NOT NULL AUTO_INCREMENT, phone VARCHAR(45) NOT NULL", "id_telephone"),
/*4*/     new DbTable("article", "id_article INT NOT NULL AUTO_INCREMENT, id_category INT UNSIGNED NOT NULL, id_subcategory INT UNSIGNED NOT NULL, id_composite INT UNSIGNED NOT NULL, id_manufacturer INT UNSIGNED NOT NULL, manufacturer_catalog_number INT UNSIGNED NULL", "id_article"),
/*5*/        new DbTable("supply", "id_supply INT NOT NULL AUTO_INCREMENT, id_supplyier INT NOT NULL, date DATE NOT NULL", "id_supply"),
/*6*/        new DbTable("category", "id_category INT UNSIGNED NOT NULL AUTO_INCREMENT, name VARCHAR(45) NOT NULL, description VARCHAR(255) NULL", "id_category"),
/*7*/        new DbTable("subcategory", "id_category INT UNSIGNED NOT NULL, id_subcategory INT UNSIGNED NOT NULL, name VARCHAR(45) NOT NULL, description VARCHAR(255) NULL", "id_category, id_subcategory"),
/*8*/        new DbTable("material", "id_material INT NOT NULL AUTO_INCREMENT, name VARCHAR(45) NOT NULL", "id_material"),
/*9*/        new DbTable("composite", "id_composite INT NOT NULL AUTO_INCREMENT, description VARCHAR(255)", "id_composite"),
/*10*/       new DbTable("discount", "id_discount INT NOT NULL AUTO_INCREMENT, card_number INT(11) NOT NULL, date_registration DATE NOT NULL, given_name VARCHAR(45) NOT NULL, middle_name VARCHAR(45) NULL, family_name VARCHAR(45) NOT NULL, birthday DATE NULL, contact_email VARCHAR(35) NULL", "id_discount"),
/*11*/       new DbTable("employee", "id_employee INT NOT NULL AUTO_INCREMENT, date_hired DATE NOT NULL, given_name VARCHAR(45) NOT NULL, middle_name VARCHAR(45) NULL, family_name VARCHAR(45) NOT NULL, birthday DATE NOT NULL", "id_employee"),
/*12*/       new DbTable("sale", "id_sale INT NOT NULL AUTO_INCREMENT, id_employee INT UNSIGNED NOT NULL, id_discount INT UNSIGNED NOT NULL, date DATE NOT NULL", "id_sale"),
        
        /***********  Таблицы ассоциации  **************************************/
        
        //----  Привязка телефонов к корневым сущностям  ----
/*13*/        new DbTable("phone_director", "id_phone INT NOT NULL, id_director INT NOT NULL", "id_phone, id_director"),
/*14*/        new DbTable("phone_manufacturer", "id_phone INT NOT NULL, id_manufacturer INT NOT NULL", "id_phone, id_manufacturer"),
/*15*/        new DbTable("phone_supplyer", "id_phone INT NOT NULL, id_supplyier INT NOT NULL", "id_phone, id_supplyier"),
/*16*/        new DbTable("phone_discount", "id_phone INT NOT NULL, id_discount INT NOT NULL", "id_phone, id_discount"),
        
        //----  Связка поставок с артикулами ----
/*17*/        new DbTable("supply_article", "id_supply INT NOT NULL, id_article INT NOT NULL, quantity INT(6) UNSIGNED NOT NULL, price FLOAT UNSIGNED NOT NULL", "id_supply, id_article"),
        
        //----  Связка композиции материалов с материалами  ----
/*18*/        new DbTable("composite_material", "id_material INT NOT NULL, id_composite INT NOT NULL, price FLOAT UNSIGNED", "id_material, id_composite"),
        
        //----  Связка продаж с набором артикулов  ----
/*19*/       new DbTable("sale_article", "id_sale INT NOT NULL, id_article INT NOT NULL, quantity INT(3) UNSIGNED NOT NULL, price FLOAT UNSIGNED NOT NULL", "id_sale, id_article"),
        
        
        
    };
    
    /**********  Хэш набор имен таблиц для быстрого поиска  *******************/
    private static Set<String> tableNames = new HashSet<String>();
    static {
        for(DbTable table : tables) {
            tableNames.add(table.getName());
        }
    }
    
    public static boolean isValidTable(String tableName) {
        return tableNames.contains(tableName);
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    
    public static TelephoneModel[] telephones = new TelephoneModel[]{
        new TelephoneModel("+38-050-663-7552"),
        new TelephoneModel("+38-067-303-5054"),
        new TelephoneModel("+38-050-343-3884"),
    };
    
    
    
}
