/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.natasha.examples;

import com.natasha.examples.models.ArticleModel;
import com.natasha.examples.models.CategoryModel;
import com.natasha.examples.models.CompositeMaterialModel;
import com.natasha.examples.models.CompositeModel;
import com.natasha.examples.models.DbTable;
import com.natasha.examples.models.DirectorModel;
import com.natasha.examples.models.DiscountModel;
import com.natasha.examples.models.EmployeeModel;
import com.natasha.examples.models.ManufacturerModel;
import com.natasha.examples.models.MaterialModel;
import com.natasha.examples.models.PhoneDirectorModel;
import com.natasha.examples.models.PhoneDiscountModel;
import com.natasha.examples.models.PhoneManufacturerModel;
import com.natasha.examples.models.PhoneSupplyierModel;
import com.natasha.examples.models.SaleArticleModel;
import com.natasha.examples.models.SaleModel;
import com.natasha.examples.models.SubcategoryModel;
import com.natasha.examples.models.SupplyArticleModel;
import com.natasha.examples.models.SupplyModel;
import com.natasha.examples.models.SupplyierModel;
import com.natasha.examples.models.TelephoneModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.activation.DataHandler;

/**
 *
 * @author Nata
 */
public class NatashaJdbc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            DbHelper.initialize();
        } catch(ClassNotFoundException e) {
            System.out.println("The JDBC driver not found - "+e.getMessage());
            return;
        }
        
        try {
            DbHelper.getInstance().createDbIfNotExists();
        } catch(SQLException e) {
            e.printStackTrace();
            return;
        }
        
        Connection conn = null;
        try {
            conn = DbHelper.getInstance().getConnection();
            
            //----  Создаем нужные таблицы в БД, если их нету или очищаем, если есть  ----
            for (DbTable table : DataHelper.tables) {
                System.out.println("------------------------------------------------");
                boolean exists = table.isTableExists(conn);
                System.out.println(String.format("Check table '%s' exists - %b", table.getName(), exists));
                
                if(exists) {
                    table.clearTable(conn);
                } else {
                    table.createTable(conn);
                }
            }
            
            //----  проверяем БД на наличие ненужных таблиц, удаляем  ----
            ResultSet rs = conn.getMetaData().getTables(null, null, "%", null);
            try {
                if(rs.first()) {
                    do {
                        String tName = rs.getString(3);
                        if(!DataHelper.isValidTable(tName)) {
                            DbHelper.getInstance().dropTableByName(tName);
                        }
                    } while(rs.next());
                }
            } finally {
                rs.close();
            }
            
            
            MaterialModel[] materials = new MaterialModel[]{
        /*0*/       new MaterialModel("metal"),
        /*1*/       new MaterialModel("tree"),
        /*2*/        new MaterialModel("plastic"),
        /*3*/        new MaterialModel("glass"),
        /*4*/        new MaterialModel("leather_upholstery"),
        /*5*/        new MaterialModel("soft_upholstery"),
            };
            
            CompositeModel[] composites = new CompositeModel[]{
        /*0*/        new CompositeModel("tree-leather_upholstery"),
        /*1*/        new CompositeModel("metall-plastic"),
        /*2*/        new CompositeModel("tree-soft_upholstery"),
        /*3*/        new CompositeModel("metal"),
        /*4*/        new CompositeModel("leather_upholstery-soft_upholstery"),
        /*5*/        new CompositeModel("metal-tree"),
        /*6*/        new CompositeModel("metal-glass"),
        /*7*/        new CompositeModel("metal-tree"),
            };
            
            for(MaterialModel mat : materials) {
                mat.crateOrUpdate(conn, DataHelper.tables[8]);
            }
            
            for(CompositeModel comp : composites) {
                comp.crateOrUpdate(conn, DataHelper.tables[9]);
            }
            
            CompositeMaterialModel[] compMats = new CompositeMaterialModel[]{
                new CompositeMaterialModel(composites[0], materials[1], 10f),
                new CompositeMaterialModel(composites[0], materials[4], 10f),
                new CompositeMaterialModel(composites[1], materials[0], 10f),
                new CompositeMaterialModel(composites[1], materials[2], 10f),
                new CompositeMaterialModel(composites[2], materials[1], 10f),
                new CompositeMaterialModel(composites[2], materials[5], 10f),
                new CompositeMaterialModel(composites[3], materials[0], 10f),
                new CompositeMaterialModel(composites[4], materials[4], 10f),
                new CompositeMaterialModel(composites[4], materials[5], 10f),
                new CompositeMaterialModel(composites[5], materials[0], 10f),
                new CompositeMaterialModel(composites[5], materials[1], 10f),
                new CompositeMaterialModel(composites[6], materials[0], 10f),
                new CompositeMaterialModel(composites[6], materials[3], 10f),
                new CompositeMaterialModel(composites[7], materials[0], 10f),
                new CompositeMaterialModel(composites[7], materials[1], 10f),
            };
            
            for (CompositeMaterialModel cm : compMats) {
                cm.crateOrUpdate(conn, DataHelper.tables[18]);
            }
            
            compMats[3].setPrice(35.78f);
            compMats[3].crateOrUpdate(conn, DataHelper.tables[18]);
            
            DirectorModel[] dir = new DirectorModel[]{
                new DirectorModel("Vladimir",  "Vladimirovich", "Pikalov", "1965-12-10", "Pikalov_Vladimir@ukr.net"),
                new DirectorModel("Olegovych", "Grigory", "Petrenko", "1980-11-04", "Petrenko_rabota@ukr.net"),  
                new DirectorModel("Natalia", "Petrovna", "Belozerova", "1982-05-21", "Belozerova Natalia@mail.ru"),   
                new DirectorModel("Irina", "Nykolaevna", "Gavrish", "1985-12-05", "Gavrish Irina@gmail.com"),  
                new DirectorModel("Nikolay", "Fedorovich", "Basov", "1988-04-03", "Gavrish Irina@gmail.com"),   
            };
            
            TelephoneModel[] phone = new TelephoneModel[]{
                new TelephoneModel("097-952-32-18"),
                new TelephoneModel("067-231-25-37"),
                new TelephoneModel("050-654-65-31"),
                new TelephoneModel("063-687-28-91"),
                new TelephoneModel("050-365-51-02"),
                new TelephoneModel("050-234-82-17"),
                new TelephoneModel("050-031-25-13"),
                new TelephoneModel("067-031-25-25"),
                new TelephoneModel("097-251-25-13"),
                new TelephoneModel("050-655-55-81"),
                new TelephoneModel("063-231-96-99"),
                new TelephoneModel("097-111-46-28"),
                new TelephoneModel("098-125-33-46"),
                new TelephoneModel("096-452-25-13"),
                new TelephoneModel("050-321-26-18"),
            };
            
            for(DirectorModel direc : dir){
                direc.crateOrUpdate(conn, DataHelper.tables[2]);
            }
            
            for(TelephoneModel tele : phone){
                tele.crateOrUpdate(conn, DataHelper.tables[3]);
            }
            
            PhoneDirectorModel[] phdir = new PhoneDirectorModel[]{
                new PhoneDirectorModel(phone[0], dir[0]),
                new PhoneDirectorModel(phone[1], dir[1]),
                new PhoneDirectorModel(phone[2], dir[2]),
            };
            
            for (PhoneDirectorModel ph : phdir) {
                ph.crateOrUpdate(conn, DataHelper.tables[13]);
            }
            
            ManufacturerModel[] manuf = new ManufacturerModel[] {
                new ManufacturerModel("New Style", "Kharkiv, street Sumy,7", "rabota_new_style@ukr.net", "www.newstyle.com", dir[0]),
                new ManufacturerModel("Topfurniture", "Kharkiv, street Novgorod,50", "Top_furniture@ukr.net", "www.top_furniture.com", dir[0]),
                new ManufacturerModel("New_furniture", "Kiev, street Bobrovka.15", "New_furniture@ukr.net", "www.new_furniture.com", dir[1]),
            };
            
            for(ManufacturerModel man : manuf){
                man.crateOrUpdate(conn, DataHelper.tables[0]);
            }
            
            PhoneManufacturerModel[] phman = new PhoneManufacturerModel[]{
                new PhoneManufacturerModel(phone[3], manuf[0]),
                new PhoneManufacturerModel(phone[4], manuf[0]),
                new PhoneManufacturerModel(phone[5], manuf[1]),
                new PhoneManufacturerModel(phone[6], manuf[2]),
            };
            
            for(PhoneManufacturerModel pm : phman){
                pm.crateOrUpdate(conn, DataHelper.tables[14]);
            }
            
            SupplyierModel[] supplyier = new SupplyierModel[]{
                new SupplyierModel("Decoroom", "Kharkiv, prospect Moskovsky.75", "decoroom_rabota@ukr.net", "www.decoroom.com/", dir[2]),
                new SupplyierModel("KUBIS", "Kharkiv, street Mironositskaya, 95", "kubis@ukr.net", "www.KUBIS.com/", dir[2]),
                new SupplyierModel("MEBLISTAR", "Kharkiv, street Zolochevskaya, 1", "meblistar@ukr.net", "www.MEBLISTAR.com/", dir[3]),
                new SupplyierModel("Oakland", "Kiev, street George, 85", "oakland@ukr.net", "www.oakland.com/", dir[4]),
            };
            
            for(SupplyierModel sup : supplyier){
                sup.crateOrUpdate(conn, DataHelper.tables[1]);
            }
            
            PhoneSupplyierModel[] phsup = new PhoneSupplyierModel[]{
                new PhoneSupplyierModel(phone[7], supplyier[0]),
                new PhoneSupplyierModel(phone[7], supplyier[0]),
                new PhoneSupplyierModel(phone[8], supplyier[1]),
                new PhoneSupplyierModel(phone[9], supplyier[2]),
                new PhoneSupplyierModel(phone[10], supplyier[3]),
            };
            
            for(PhoneSupplyierModel ps : phsup){
                ps.crateOrUpdate(conn, DataHelper.tables[15]);
            }
            
            DiscountModel[] discount = new DiscountModel[]{
                new DiscountModel("2323173", "2015-07-01", "Marina", "Yuryevna", "Shevchenko", "1985-10-02", "Shevchenko_Marina@gmail.com"),
                new DiscountModel("4515879", "2013-10-11", "Andrey", "Yurevich", "Malakhov", "1980-12-05", "Malakhov@gmail.com"),  
                new DiscountModel("1657318", "2010-01-12", "Andrey", "Vladimirovich", "Petrov", "1979-07-02", "Petrov_Andrey@ukr.net"),  
                new DiscountModel("7957123", "2012-09-02", "Nikolay", "Fedorovich", "Sidorov", "1975-01-12", "Sidorov_Nikolay@ukr.net"),  
            };
            
            for(DiscountModel dis : discount){
                dis.crateOrUpdate(conn, DataHelper.tables[10]);
            }
            
            PhoneDiscountModel[] phdis = new PhoneDiscountModel[]{
                new PhoneDiscountModel(phone[11], discount[0]),
                new PhoneDiscountModel(phone[12], discount[1]),
                new PhoneDiscountModel(phone[13], discount[2]),
                new PhoneDiscountModel(phone[14], discount[3]),
            };
            
            for(PhoneDiscountModel pd : phdis){
                pd.crateOrUpdate(conn, DataHelper.tables[16]);
            }
            
            SupplyModel[] supM = new SupplyModel[]{
                new SupplyModel(supplyier[0], "2015-10-01"),
                new SupplyModel(supplyier[1], "2016-05-12"),
                new SupplyModel(supplyier[2], "2014-03-05"),
                new SupplyModel(supplyier[3], "2015-12-01"),
            };
            
            for(SupplyModel sM : supM){
                sM.crateOrUpdate(conn, DataHelper.tables[5]);
            }
            
            CategoryModel[] cat = new CategoryModel[]{
                new CategoryModel("soft furniture", "soft furniture for relax"),
                new CategoryModel("cabinet furniture", "cabinet furniture for office"),
                new CategoryModel("street furniture", "street furniture for beautification"),
            };
            
            for(CategoryModel cM : cat){
                cM.crateOrUpdate(conn, DataHelper.tables[6]);
            }
            
            SubcategoryModel[] sub = new SubcategoryModel[]{
                new SubcategoryModel(cat[0], 1, "sofa", "sofa for guests"),
                new SubcategoryModel(cat[0], 2, "bed sofa", "sofa for sleep"),
                new SubcategoryModel(cat[0], 3, "pouf", "pouf for hallway"),
                new SubcategoryModel(cat[1], 1, "table", "computer table"),
                new SubcategoryModel(cat[1], 2, "chair", "Office chair"),
                new SubcategoryModel(cat[2], 1, "bench", "decorative bench"),
                new SubcategoryModel(cat[2], 2, "bower", "bower for garden"),
                new SubcategoryModel(cat[2], 3, "chair", "chair for the garden"),
            };
            
            for(SubcategoryModel sM : sub){
                sM.crateOrUpdate(conn, DataHelper.tables[7]);
            }
            
            EmployeeModel[] emp = new EmployeeModel[]{
                new EmployeeModel("2011-01-08", "Irina", "Petrovna", "Akmetova", "1981-12-11"),
                new EmployeeModel("2012-11-02", "Petro", "Romanovich", "Ivanov", "1979-05-08"),
                new EmployeeModel("2014-07-10", "Stanislav", "Vladimirovich", "Petrov", "1981-12-11"),
            };
            
            for(EmployeeModel eM : emp){
                eM.crateOrUpdate(conn, DataHelper.tables[11]);
            }
            
            SaleModel[] sale = new SaleModel[]{
                new SaleModel(emp[0], discount[0], "2015-08-06"),
                new SaleModel(emp[1], discount[1], "2014-12-10"),
                new SaleModel(emp[2], discount[2], "2015-10-05"),
                new SaleModel(emp[2], discount[3], "2015-08-03"),
            };
            
            for(SaleModel sM : sale){
                sM.crateOrUpdate(conn, DataHelper.tables[12]);
            }
            
            ArticleModel[] art = new ArticleModel[]{
                new ArticleModel(sub[0], composites[0], manuf[0], 1),
                new ArticleModel(sub[1], composites[2], manuf[0], 2),
                new ArticleModel(sub[2], composites[4], manuf[0], 3),
                new ArticleModel(sub[3], composites[6], manuf[0], 4),
                new ArticleModel(sub[4], composites[7], manuf[1], 5),
                new ArticleModel(sub[5], composites[5], manuf[1], 6),
                new ArticleModel(sub[6], composites[3], manuf[2], 7),
                new ArticleModel(sub[7], composites[1], manuf[2], 8),
            };
            
            for(ArticleModel aM : art){
                aM.crateOrUpdate(conn, DataHelper.tables[4]);
            }
            
            SaleArticleModel[] saleart = new SaleArticleModel[]{
                new SaleArticleModel(sale[0], art[0], 3, 10f),
                new SaleArticleModel(sale[0], art[2], 4, 15f),
                new SaleArticleModel(sale[0], art[4], 6, 20f),
                new SaleArticleModel(sale[1], art[3], 10, 12f),
                new SaleArticleModel(sale[1], art[4], 8, 20f),
                new SaleArticleModel(sale[1], art[1], 6, 7f),
                new SaleArticleModel(sale[2], art[5], 5, 25f),
                new SaleArticleModel(sale[3], art[6], 11, 14f),
                new SaleArticleModel(sale[3], art[7], 9, 12f),
                new SaleArticleModel(sale[3], art[0], 9, 10f),
            };
            
            for(SaleArticleModel SAM : saleart){
                SAM.crateOrUpdate(conn, DataHelper.tables[19]);
            }
            
            SupplyArticleModel[] supart = new SupplyArticleModel[]{
                new SupplyArticleModel(supM[0], art[0], 20, 8f),
                new SupplyArticleModel(supM[0], art[1], 22, 10f),
                new SupplyArticleModel(supM[0], art[2], 30, 7f),
                new SupplyArticleModel(supM[1], art[1], 32, 8f),
                new SupplyArticleModel(supM[1], art[2], 33, 9f),
                new SupplyArticleModel(supM[1], art[3], 30, 7f),
                new SupplyArticleModel(supM[2], art[4], 25, 10f),
                new SupplyArticleModel(supM[2], art[5], 26, 11f),
                new SupplyArticleModel(supM[2], art[0], 15, 10f),
                new SupplyArticleModel(supM[3], art[6], 18, 11f),
                new SupplyArticleModel(supM[3], art[7], 19, 12f),
                new SupplyArticleModel(supM[3], art[1], 22, 13f),
            };
            
            for(SupplyArticleModel supartM : supart){
                supartM.crateOrUpdate(conn, DataHelper.tables[17]);
            }
            
        } catch(SQLException e) {
            System.out.println("SQL request error - "+e.getMessage());
        } finally {
            try {
                DbHelper.getInstance().releaseConnection();
            } catch(SQLException e){}
        }
        
        
        
        
        
        
    }
    
}
