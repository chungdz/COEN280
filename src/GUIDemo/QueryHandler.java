/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIDemo;
import JdbcConnect.QueryManager;
import TableGenerated.SQLFormat;
import java.sql.*;
import java.sql.SQLException;
import java.util.*;
/**
 *
 * @author 10337
 */
public class QueryHandler {
    QueryManager qm;
    SQLFormat sf;
    
    public QueryHandler(){
        qm = new QueryManager("./config/connect_config.json");
        qm.connectToDB();
        sf = new SQLFormat();
    }
    
    public ArrayList<String> queryForSecondBusiness(Demo frameInfo){
        ArrayList<String> res = new ArrayList<>();
        if(frameInfo.category_selected.size() < 1){
            return res;
        }
        
        String BID_query, BID2_query;
        if(frameInfo.business_bool.equals("AND")){
            BID_query = constructSqlANDForB1(frameInfo);
            BID2_query = constructSqlANDForB2(frameInfo);
        }
        else{
            BID_query = constructSqlORForB1(frameInfo);
            BID2_query = constructSqlORForB2(frameInfo);
        }
        String final_q = "SELECT DISTINCT AName FROM Business_Att " +
        "WHERE BusinessID IN((" + BID_query + ") INTERSECT (" + BID2_query + "))";
        ResultSet queryRes = qm.fetchQuery(final_q);
        System.out.println(final_q);
        if(queryRes != null){
            try{
                while(queryRes.next()){
                    res.add(queryRes.getString(1));
                }
            } catch (SQLException E){
                System.out.println("SQLException:" + E.getMessage());
                System.out.println("SQLState:" + E.getSQLState());
                System.out.println("VendorError:" + E.getErrorCode());
            }
        }
        return res;
    }
    
    public ArrayList<String> queryForFirstBusiness(Demo frameInfo){
        ArrayList<String> res = new ArrayList<>();
        if(frameInfo.category_selected.size() < 1){
            return res;
        }
        
        String BID_query;
        if(frameInfo.business_bool.equals("AND")){
            BID_query = constructSqlANDForB1(frameInfo);
        }
        else{
            BID_query = constructSqlORForB1(frameInfo);
        }
        String final_q = "SELECT DISTINCT SubcName FROM Business_Subcategories " +
                                "WHERE BusinessID IN(" + BID_query + ")";
        ResultSet queryRes = qm.fetchQuery(final_q);
        System.out.println(final_q);
        if(queryRes != null){
            try{
                while(queryRes.next()){
                    res.add(queryRes.getString(1));
                }
            } catch (SQLException E){
                System.out.println("SQLException:" + E.getMessage());
                System.out.println("SQLState:" + E.getSQLState());
                System.out.println("VendorError:" + E.getErrorCode());
            }
        }
        return res;
    }
    
    private String constructSqlANDForB1(Demo frameInfo){
        ArrayList<String> res_list = new ArrayList<>();
        for(String cate: frameInfo.category_selected){
            String res = String.format("SELECT DISTINCT BusinessID FROM Business_Categories" +
                                            " WHERE CNAME='%s'", sf.parseString(cate));
            res_list.add(res);
        }
        return String.join(" INTERSECT ", res_list);
    }
    
    private String constructSqlORForB1(Demo frameInfo){
        ArrayList<String> res_list = new ArrayList<>();
        for(String cate: frameInfo.category_selected){
            String res = String.format("CNAME='%s'", sf.parseString(cate));
            res_list.add(res);
        }
        String head = "SELECT DISTINCT BusinessID FROM Business_Categories WHERE ";
        String tail = String.join(" or ", res_list);
        return head + tail;
    }
    
    private String constructSqlANDForB2(Demo frameInfo){
        ArrayList<String> res_list = new ArrayList<>();
        for(String subcate: frameInfo.subcates){
            String res = String.format("SELECT DISTINCT BusinessID FROM Business_Subcategories" +
                                            " WHERE SubCNAME='%s'", sf.parseString(subcate));
            res_list.add(res);
        }
        return String.join(" INTERSECT ", res_list);
    }
    
    private String constructSqlORForB2(Demo frameInfo){
        ArrayList<String> res_list = new ArrayList<>();
        for(String subcate: frameInfo.subcates){
            String res = String.format("SubCNAME='%s'", sf.parseString(subcate));
            res_list.add(res);
        }
        String head = "SELECT DISTINCT BusinessID FROM Business_Subcategories WHERE ";
        String tail = String.join(" or ", res_list);
        return head + tail;
    }
}
