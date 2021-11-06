/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableGenerated;

import JdbcConnect.QueryManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import org.json.JSONObject;

/**
 *
 * @author 10337
 */
class Check {
    String type;
    String BID;
    String info;
    
    public Check(String one_line){
        JSONObject cjson = new JSONObject(one_line);
        type = cjson.getString("type");
        BID = cjson.getString("business_id");
        info = "";
        JSONObject ijson = cjson.getJSONObject("checkin_info");
        Iterator<String> it = ijson.keys();
        while (it.hasNext()){
            String key = it.next();
            int value = ijson.getInt(key);
            info += key + "-" +  String.valueOf(value) + " ";
        }
    }
    
    public String insertCheckinSQL(){
        String res = String.format("INSERT INTO Checkin VALUES ('%s', '%s', '%s')", 
        type, BID, info);
        return res;
    }
}

public class Checkin {
    ArrayList<Check> cl;
    QueryManager qm;
    
    public Checkin(){
        qm = new QueryManager("./config/connect_config.json");
        qm.connectToDB();
    }
    
    public void parseFile(String filePath){
        cl = new ArrayList<>();
        int tmp_index = 0;
        try{
            Scanner sc = new Scanner(new File(filePath));
            while(sc.hasNextLine()){
                Check cb = new Check(sc.nextLine());
                cl.add(cb);
                tmp_index += 1;
//                if(tmp_index > 100){break;}
            }
            System.out.println(String.format("Total %d rows in file", cl.size()));
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        } 
    }
    
    public void populateCheckin(){
        System.out.println(String.format("Delete %d rows from Checkin" , qm.updateDB("Delete From Checkin")));
        
        int insert_row = 0;
        for(int i = 0;i < cl.size();++i){
            Check cc = cl.get(i);
            String cur_sql = cc.insertCheckinSQL();
            int rows = qm.updateDB(cur_sql);
            if(rows == 0){
                System.out.println(cur_sql);
            }
            insert_row += rows;
            if(i % 10000 == 0){System.out.println("Checkin number: " + String.valueOf(i));}
        }
        System.out.println(String.format("Insert %d rows", insert_row));
    }
    
    public static void main(String [] Args){
        Checkin ck = new Checkin();
        ck.parseFile("./data/yelp_checkin.json");
        ck.populateCheckin();
    }
}
