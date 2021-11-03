/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JdbcConnect;

import java.sql.*;
import JdbcConnect.QueryManager;

/**
 *
 * @author 10337
 */
public class RunManager {
    public static void main(String [] Args){
        QueryManager qm = new QueryManager("./src/JdbcConnect/connect_config.json");
        qm.connectToDB();
        qm.PrintDBMeta();
        ResultSet res = qm.fetchQuery("select * from users where ROWNUM <= 5");
        try{
            if(res != null){
                while(res.next()){
                    String col1 = res.getString(1);
                    String col2 = res.getString(2);
                    System.out.println(col1 + " " + col2);
                }
            }
        } catch (SQLException E){
            System.out.println("SQLException:" + E.getMessage());
            System.out.println("SQLState:" + E.getSQLState());
            System.out.println("VendorError:" + E.getErrorCode());
        }
        
        ResultSet res2 = qm.fetchQuery("select * from Business where ROWNUM <= 5");
        try{
            if(res2 != null){
                while(res2.next()){
                    String col1 = res2.getString(1);
                    String col2 = res2.getString(2);
                    System.out.println(col1 + " " + col2);
                }
            }
        } catch (SQLException E){
            System.out.println("SQLException:" + E.getMessage());
            System.out.println("SQLState:" + E.getSQLState());
            System.out.println("VendorError:" + E.getErrorCode());
        }
    }
}
