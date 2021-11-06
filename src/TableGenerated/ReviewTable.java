/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableGenerated;

import JdbcConnect.QueryManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONObject;

/**
 *
 * @author 10337
 */
class Review{
    String RID;
    int stars;
    String UID;
    String rdate;
    String BID;
    int funny;
    int cool;
    int useful;
    String rtext;
    
    public Review(String one_line){
        SQLFormat sf = new SQLFormat();
        JSONObject rjson = new JSONObject(one_line);
        RID = rjson.getString("review_id");
        stars = rjson.getInt("stars");
        UID = rjson.getString("user_id");
        BID = rjson.getString("business_id");
        rdate = rjson.getString("date");
        rtext = rjson.getString("text");
        rtext = sf.parseString(rtext.substring(0, java.lang.Math.min(100, rtext.length())));
        JSONObject votes = rjson.getJSONObject("votes");
        funny = votes.getInt("funny");
        cool = votes.getInt("cool");
        useful = votes.getInt("useful");
//        System.out.println(insertReviewSql());
    }
    
    public String insertReviewSql(){
        String res = String.format("INSERT INTO Review VALUES ('%s', %d, '%s', TO_DATE('%s','YYYY-MM-DD'), '%s', %d, '%s')", 
                RID, stars, UID, rdate, BID, funny + cool + useful, rtext);
        return res;
    }
}

public class ReviewTable {
    ArrayList<Review> rl;
    QueryManager qm;
    
    public ReviewTable(){
        qm = new QueryManager("./config/connect_config.json");
        qm.connectToDB();
    }
    
    public void parseFile(String filePath){
        rl = new ArrayList<>();
        int tmp_index = 0;
        try{
            Scanner sc = new Scanner(new File(filePath));
            while(sc.hasNextLine()){
                Review cr = new Review(sc.nextLine());
                rl.add(cr);
                tmp_index += 1;
//                if(tmp_index > 100){break;}
            }
            System.out.println(String.format("Total %d rows in file", rl.size()));
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        } 
    }
    
    public void populateReviews(){
        System.out.println(String.format("Delete %d rows from Review" , qm.updateDB("Delete From Review")));
        
        int insert_row = 0;
        for(int i = 0;i < rl.size();++i){
            Review cu = rl.get(i);
            String cur_sql = cu.insertReviewSql();
            int rows = qm.updateDB(cur_sql);
            if(rows == 0){
                System.out.println(cur_sql);
            }
            insert_row += rows;
            if(i % 200000 == 0){System.out.println("Review number: " + String.valueOf(i));}
        }
        System.out.println(String.format("Insert %d rows", insert_row));
    }
    
    public static void main(String [] Args){
        ReviewTable rt = new ReviewTable();
        rt.parseFile("./data/yelp_review.json");
        rt.populateReviews();
    }
}
