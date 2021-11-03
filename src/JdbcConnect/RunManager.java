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
        System.out.println();
        ResultSet res = qm.fetchQuery("select * from users where ROWNUM <= 5");
        qm.ShowQuery(res);
        System.out.println();
        res = qm.fetchQuery("select * from Business where ROWNUM <= 5");
        qm.ShowQuery(res);
        System.out.println();
        int rows = qm.updateDB("Drop Table Test1");
        System.out.println(rows);
        rows = qm.updateDB("Create Table Test1 (UserID varchar(10))");
        System.out.println(rows);
        qm.clostConnection();
    }
}
