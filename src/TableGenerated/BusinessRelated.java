/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableGenerated;
import JdbcConnect.QueryManager;
import java.util.*;
import java.io.*;
/**
 *
 * @author 10337
 *
 */

public class BusinessRelated {
    ArrayList<Business> bl;
    QueryManager qm;
    
    public BusinessRelated(){
        qm = new QueryManager("./config/connect_config.json");
        qm.connectToDB();
    }
    
    public void parseFile(String filePath){
        bl = new ArrayList<>();
        int tmp_index = 0;
        try{
            Scanner sc = new Scanner(new File(filePath));
            while(sc.hasNextLine()){
                Business cb = new Business(sc.nextLine());
                bl.add(cb);
                tmp_index += 1;
//                if(tmp_index > 100){break;}
            }
            System.out.println(String.format("Total %d rows in file", bl.size()));
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        } 
    }
    
    public void populateBusiness(){
        System.out.println(String.format("Delete %d rows from Business" , qm.updateDB("Delete From Business")));
        
        int insert_row = 0;
        for(int i = 0;i < bl.size();++i){
            Business cb = bl.get(i);
            String cur_sql = cb.insertBusinessSQL();
            int rows = qm.updateDB(cur_sql);
            if(rows == 0){
                System.out.println(cur_sql);
            }
            insert_row += rows;
            if(i % 10000 == 0){System.out.println("Business number: " + String.valueOf(i));}
        }
        System.out.println(String.format("Insert %d rows", insert_row));
    }
    
    public void populateCategory(){
        System.out.println(String.format("Delete %d rows from Business_Categories" , 
                qm.updateDB("Delete From Business_Categories")));
        
        int insert_row = 0;
        for(int i = 0;i < bl.size();++i){
            Business cb = bl.get(i);
            ArrayList<String> cate_list = cb.insertCategorySQL();
            for(String cur_sql: cate_list){
                int rows = qm.updateDB(cur_sql);
                if(rows == 0){
                    System.out.println(cur_sql);
                }
                insert_row += rows;
            }
            if(i % 10000 == 0){System.out.println("Category number: " + String.valueOf(i));}
        }
        System.out.println(String.format("Insert %d rows", insert_row));
    }
    
    public void populateSubcategory(){
        System.out.println(String.format("Delete %d rows from Business_Subcategories" , 
                qm.updateDB("Delete From Business_Subcategories")));
        
        int insert_row = 0;
        for(int i = 0;i < bl.size();++i){
            Business cb = bl.get(i);
            ArrayList<String> cate_list = cb.insertSubcategorySQL();
            for(String cur_sql: cate_list){
                int rows = qm.updateDB(cur_sql);
                if(rows == 0){
                    System.out.println(cur_sql);
                }
                insert_row += rows;
            }
            if(i % 10000 == 0){System.out.println("Subcategory number: " + String.valueOf(i));}
        }
        System.out.println(String.format("Insert %d rows", insert_row));
    }
    
    public void populateAttributes(){
        System.out.println(String.format("Delete %d rows from Business_Att" , 
                qm.updateDB("Delete From Business_Att")));
        
        int insert_row = 0;
        for(int i = 0;i < bl.size();++i){
            Business cb = bl.get(i);
            ArrayList<String> cate_list = cb.insertAttributesSQL();
            for(String cur_sql: cate_list){
                int rows = qm.updateDB(cur_sql);
                if(rows == 0){
                    System.out.println(cur_sql);
                }
                insert_row += rows;
            }
            if(i % 4000 == 0){System.out.println("Attributes number: " + String.valueOf(i));}
        }
        System.out.println(String.format("Insert %d rows", insert_row));
    }
    
    public static void main(String [] Args){
        BusinessRelated br = new BusinessRelated();
        System.out.println(Business.cate_set.size());
        br.parseFile("./data/yelp_business.json");
        br.populateBusiness();
        br.populateCategory();
        br.populateSubcategory();
        br.populateAttributes();
//        String mjson = "{\"cc\": true, \"dd\": \"sss\", \"ee\": {\"tt\": ff}}";
//        JSONObject bjson = new JSONObject(mjson);
//        Iterator<String> it =bjson.keys();
//        while (it.hasNext()) {
//            String key = it.next();
//            String type = bjson.get(key).getClass().getSimpleName();
//            System.out.println(type);
//        }
    }
}
