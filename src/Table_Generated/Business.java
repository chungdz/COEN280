/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Table_Generated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author 10337
 */
public class Business{
    String bID;
    String name;
    String Bstate;
    String city;
    double stars;
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
        stars = bjson.getDouble("stars");
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
        String res = String.format("INSERT INTO Business VALUES ('%s', '%s', '%s', '%s', %f)", 
        bID, name, city, Bstate, stars);
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
