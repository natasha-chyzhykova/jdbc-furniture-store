/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.natasha.examples;

import com.natasha.examples.models.DbTable;
import com.natasha.examples.models.TelephoneModel;

/**
 *
 * @author Stas
 */
public class DataHelper {
    
    public static DbTable[] tables = new DbTable[] {
        new DbTable("manufacturer", "id_manufacturer INT NOT NULL AUTO_INCREMENT, name VARCHAR(25) NOT NULL, address VARCHAR(25) NOT NULL, contact_email VARCHAR(25) NULL, website VARCHAR(255) NULL,  id_director INT UNSIGNED NOT NULL", "id_manufacturer"),
        new DbTable("supplyier", "id_supplyier INT NOT NULL AUTO_INCREMENT, name VARCHAR(25) NOT NULL, address VARCHAR(25) NOT NULL, contact_email VARCHAR(25) NULL, website VARCHAR(255) NULL, id_director INT UNSIGNED NOT NULL", "id_supplyier"),
        new DbTable("director", "id_director INT NOT NULL AUTO_INCREMENT, full_name VARCHAR(45) NOT NULL, contact_email VARCHAR(25) NULL", "id_director"),
        new DbTable("telephone", "id_telephone INT NOT NULL AUTO_INCREMENT, phone VARCHAR(45) NOT NULL", "id_telephone"),
        
        new DbTable("telephone_director", "id_telephone INT NOT NULL, id_director INT NOT NULL", "id_telephone, id_director"),
        new DbTable("telephone_manufacturer", "id_telephone INT NOT NULL, id_manufacturer INT NOT NULL", "id_telephone, id_manufacturer"),
        new DbTable("telephone_supplyer", "id_telephone INT NOT NULL, id_supplyier INT NOT NULL", "id_telephone, id_supplyier"),
        
        new DbTable("article", "id_article INT NOT NULL AUTO_INCREMENT, id_subcategories furniture INT NOT NULL, id_composite INT NOT NULL, id_manufacturer INT NOT NULL, manufacturer_catalog_number INT UNSIGNED", "id_article"),
        //new DbTable(),
    };
    
    
    public static TelephoneModel[] telephones = new TelephoneModel[]{
        new TelephoneModel("+38-050-663-7552"),
        new TelephoneModel("+38-067-303-5054"),
        new TelephoneModel("+38-050-343-3884"),
    };
    
    
    
}
