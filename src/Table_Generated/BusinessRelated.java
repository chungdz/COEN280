/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Table_Generated;
import JdbcConnect.QueryManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import java.util.*;
import java.io.*;
/**
 *
 * @author 10337
 *
 */

class Business{
    String bID;
    String name;
    String Bstate;
    String city;
    ArrayList<String> categories;
    ArrayList<String> subcate;
    ArrayList<String> att;
    SQLFormat sf;
    
    public static final String[] all_cate = new String[] {"Active Life", "Arts & Entertainment", "Automotive", "Car Rental", "Cafes", "Beauty & Spas", "Convenience Stores", "Dentists", "Doctors", "Drugstores", "Department Stores", "Education", "Event Planning & Services", "Flowers & Gifts", "Food", "Health & Medical", "Home Services", "Home & Garden", "Hospitals", "Hotels & Travel", "Hardware Stores", "Grocery", "Medical Centers", "Nurseries & Gardening", "Nightlife", "Restaurants", "Shopping", "Transportation"};
    public static final Set<String> cate_set = new HashSet<>(Arrays.asList(all_cate));
    
    public Business(String one_line){
        sf = new SQLFormat();
        JSONObject bjson = new JSONObject(one_line);
        bID = bjson.getString("business_id");
        name = sf.parseString(bjson.getString("name"));
        Bstate = bjson.getString("state");
        city = bjson.getString("city");
        categories = new ArrayList<>();
        subcate = new ArrayList<>();
        att = new ArrayList<>();
        
        JSONArray cArray = bjson.getJSONArray("categories");
        JSONObject ajson = bjson.getJSONObject("attributes");
        for(int i = 0;i < cArray.length();++i){
            String curc = cArray.getString(i);
            if(cate_set.contains(curc)){
                categories.add(sf.parseString(curc));
            }
            else{
                subcate.add(sf.parseString(curc));
            }
        }
        Iterator<String> it =ajson.keys();
        while (it.hasNext()) {
            String key = it.next();
            String type = ajson.get(key).getClass().getSimpleName();
            if(type.equals("Boolean")){
                boolean value = ajson.getBoolean(key);
                if(value){
                    att.add(sf.parseString(key));
                }
            }
            else if(type.equals("String")){
                att.add(sf.parseString(key + " is " + ajson.getString(key)));
            }
            else if(type.equals("Integer")){
                att.add(sf.parseString(key + " is " + String.valueOf(ajson.getInt(key))));
            }
            else if(type.equals("JSONObject")){
                JSONObject tmp_json = ajson.getJSONObject(key);
                Iterator<String> it2 = tmp_json.keys();
                while (it2.hasNext()){
                    String subkey = it2.next();
                    boolean subvalue = tmp_json.getBoolean(subkey);
                    if(subvalue){
                        att.add(sf.parseString(key + " " + subkey));
                    }
                }
            }
            else{
                System.out.println("Unseened type: " + type);
            }
        }
//        print_sqls();
    }
    
    public String insertBusinessSQL(){
        String res = String.format("INSERT INTO Business VALUES ('%s', '%s', '%s', '%s')", 
        bID, name, city, Bstate);
        return res;
    }
    
    public ArrayList<String> insertCategorySQL(){
        ArrayList<String> res_list = new ArrayList<>();
        for(int i = 0;i < categories.size();++i){
            String res = String.format("INSERT INTO Business_Categories VALUES ('%s', '%s')", bID, categories.get(i));
            res_list.add(res);
        }
        return res_list;
    }
    
    public ArrayList<String> insertSubcategorySQL(){
        ArrayList<String> res_list = new ArrayList<>();
        for(int i = 0;i < subcate.size();++i){
            String res = String.format("INSERT INTO Business_Subcategories VALUES ('%s', '%s')", bID, subcate.get(i));
            res_list.add(res);
        }
        return res_list;
    }
    
    public ArrayList<String> insertAttributesSQL(){
        ArrayList<String> res_list = new ArrayList<>();
        for(int i = 0;i < att.size();++i){
            String res = String.format("INSERT INTO Business_Att VALUES ('%s', '%s')", bID, att.get(i));
            res_list.add(res);
        }
        return res_list;
    }
    
    public void print_sqls(){
        System.out.println(insertBusinessSQL());
        for(String s: insertCategorySQL()){
            System.out.println(s);
        }
        for(String s: insertSubcategorySQL()){
            System.out.println(s);
        }
        for(String s: insertAttributesSQL()){
            System.out.println(s);
        }
        System.out.println();
    }
}

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
