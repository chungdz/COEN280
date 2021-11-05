/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JdbcConnect;
import java.sql.*;
import com.google.gson.*;
import java.io.*;
import org.apache.commons.io.FileUtils;
/**
 *
 * @author 10337
 */

class Cconfig{
    String connectionDriver;
    String connectHeader;
    String connectionPort;
    String connectionHost;
    String username;
    String password;
    String dbName;
}

public class QueryManager {
    
    Cconfig ccfg;
    Connection C;
    Statement s;
    
    public QueryManager(String configPath){
        File f = new File(configPath);
        Gson gson = new Gson();
        try{
            String content= FileUtils.readFileToString(f, "UTF-8");
            ccfg = gson.fromJson(content, Cconfig.class);
        } catch(IOException E){
            E.printStackTrace();
        }
    }
    
    public void connectToDB(){
        try{
            Class.forName (ccfg.connectionDriver);
        } catch (Exception E){
            System.err.println("Unable to load driver");
            E.printStackTrace();
        }
        
        try{
            C = DriverManager.getConnection(
               ccfg.connectHeader + ccfg.connectionHost + ":" + 
               ccfg.connectionPort + ":" + ccfg.dbName,
               ccfg.username, ccfg.password);
            s = C.createStatement();
//            C.setAutoCommit(true);
        } catch (SQLException E){
            System.out.println("SQLException:" + E.getMessage());
            System.out.println("SQLState:" + E.getSQLState());
            System.out.println("VendorError:" + E.getErrorCode());
        }
    }
    
    public void PrintDBMeta(){
        try{
            DatabaseMetaData dbMetaData = C.getMetaData();
            String productName = dbMetaData.getDatabaseProductName();
            System.out.println(productName);
            String productVersion =dbMetaData.getDatabaseProductVersion();
            System.out.println(productVersion);
        } catch (SQLException E){
            System.out.println("SQLException:" + E.getMessage());
            System.out.println("SQLState:" + E.getSQLState());
            System.out.println("VendorError:" + E.getErrorCode());
        }
    }
    
    public ResultSet fetchQuery(String query){
        ResultSet res = null;
        try{
            s.executeQuery(query);
            res = s.getResultSet();
        } catch (SQLException E){
            System.out.println("SQLException:" + E.getMessage());
            System.out.println("SQLState:" + E.getSQLState());
            System.out.println("VendorError:" + E.getErrorCode());
        }
        return res;
    }
    
    public int updateDB(String query){
        int res = 0;
        try{
            res = s.executeUpdate(query);
//            C.commit();
        } catch (SQLException E){
            System.out.println("SQLException:" + E.getMessage());
            System.out.println("SQLState:" + E.getSQLState());
            System.out.println("VendorError:" + E.getErrorCode());
        }
        return res;
    }
    
    public void clostConnection(){
        try{
            C.close();
        } catch (SQLException E){
            System.out.println("SQLException:" + E.getMessage());
            System.out.println("SQLState:" + E.getSQLState());
            System.out.println("VendorError:" + E.getErrorCode());
        }
    }
    
    public void ShowQuery(ResultSet res){
        if(res == null){
            System.out.println("No Result");
            return;
        }
        
        try{
            ResultSetMetaData resultsMetaData =res.getMetaData();
            int columnCount =resultsMetaData.getColumnCount();
            /* print header*/
            for(int i = 1; i < columnCount + 1;++i){
                System.out.print(resultsMetaData.getColumnName(i) + " ");
            }
            System.out.println();
            /* print content*/
            while(res.next()){
                for(int i = 1; i < columnCount + 1;++i){
                    System.out.print(res.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (SQLException E){
            System.out.println("SQLException:" + E.getMessage());
            System.out.println("SQLState:" + E.getSQLState());
            System.out.println("VendorError:" + E.getErrorCode());
        }
    }
    
    public static void main(String [] Args){
        try{
            Class.forName ("oracle.jdbc.driver.OracleDriver");
        } catch (Exception E){
            System.err.println("Unable to load driver");
            E.printStackTrace();
        }
        try{
            Connection C = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", 
                "oradb2", "oracle123"); // 
            DatabaseMetaData dbMetaData = C.getMetaData();
            String productName = dbMetaData.getDatabaseProductName();
            System.out.println(productName);
            String productVersion =dbMetaData.getDatabaseProductVersion();
            System.out.println(productVersion);
            Statement s = C.createStatement();
            s.executeQuery("select * from users");
            ResultSet res = s.getResultSet();
            if(res != null){
                while(res.next()){
                    String col1 = res.getString(1);
                    String col2 = res.getString(2);
                    System.out.println(col1 + " " + col2);
                }
            }
            C.close();
        } catch (SQLException E){
            System.out.println("SQLException:" + E.getMessage());
            System.out.println("SQLState:" + E.getSQLState());
            System.out.println("VendorError:" + E.getErrorCode());
        }
    }
}
