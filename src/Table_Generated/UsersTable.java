/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Table_Generated;
import JdbcConnect.QueryManager;
import Table_Generated.SQLFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import java.util.*;
import java.io.*;
/**
 *
 * @author 10337
 */
class Users{
    String userID;
    String yelpSince;
    String UName;
    int funny;
    int cool;
    int useful;
    int friends;
    int reviewCount;
    double averageStars;
    SQLFormat sf;
    
    public Users(String one_line){
        sf = new SQLFormat();
        JSONObject ujson = new JSONObject(one_line);
        userID = ujson.getString("user_id");
        yelpSince = ujson.getString("yelping_since") + "-01";
        UName = sf.parseString(ujson.getString("name"));
        JSONObject votes = ujson.getJSONObject("votes");
        funny = votes.getInt("funny");
        cool = votes.getInt("cool");
        useful = votes.getInt("useful");
        JSONArray fArray = ujson.getJSONArray("friends");
        friends = fArray.length();
        reviewCount = ujson.getInt("review_count");
        averageStars = ujson.getDouble("average_stars");
//        printContent();
    }
    
    public void printContent(){
        String res = String.format("UserID: %s, since: %s, friends: %d, name: %s, votes: %s", 
                userID, yelpSince, friends, UName, funny + cool + useful);
        System.out.println(res);
    }
    
    public String insertUserSql(){
        String res = String.format("INSERT INTO USERS VALUES ('%s', TO_DATE('%s','YYYY-MM-DD'), "
                + "'%s', %d, %d, %d, %d, %d, %f, %d)", 
                userID, yelpSince, UName, funny, cool, useful, funny + cool + useful, reviewCount, averageStars, friends);
        return res;
    }
}

public class UsersTable {
    public ArrayList<Users> ul;
    QueryManager qm;
    
    public UsersTable(){
        qm = new QueryManager("./config/connect_config.json");
        qm.connectToDB();
    }
    
    public void parseFile(String filePath){
        ul = new ArrayList<Users>();
        int tmp_index = 0;
        try{
            Scanner sc = new Scanner(new File(filePath));
            while(sc.hasNextLine()){
                Users cu = new Users(sc.nextLine());
                ul.add(cu);
                tmp_index += 1;
//                if(tmp_index > 10){break;}
            }
            System.out.println(String.format("Total %d rows in file", ul.size()));
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
        
    }
    
    public void populateUsers(){
        System.out.println(String.format("Delete %d rows" , qm.updateDB("Delete From Users")));
        
        int insert_row = 0;
        for(int i = 0;i < ul.size();++i){
            Users cu = ul.get(i);
            String cur_sql = cu.insertUserSql();
            int rows = qm.updateDB(cur_sql);
            if(rows == 0){
                System.out.println(cur_sql);
            }
            insert_row += rows;
            if(i % 10000 == 0){System.out.println("User number: " + String.valueOf(i));}
        }
        System.out.println(String.format("Insert %d rows", insert_row));
    }
    
    public static void main(String [] Args){
        UsersTable ut = new UsersTable();
        ut.parseFile("./data/yelp_user.json");
        ut.populateUsers();
    }
}
