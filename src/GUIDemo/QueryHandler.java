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
    public QueryManager qm;
    SQLFormat sf;
    
    public QueryHandler(){
        qm = new QueryManager("./config/connect_config.json");
        qm.connectToDB();
        sf = new SQLFormat();
    }
    
    public Vector<Vector> queryUser(hw3 frameInfo){
        ArrayList<String> conditions = new ArrayList();
        String DateFrom, reviewCount, friends, stars, voteCount, bu;
        
        DateFrom = frameInfo.jxfU2.getText();
        if(!DateFrom.equals("")){
            conditions.add(String.format("Yelp_Since > To_date('%s', 'YYYY-MM-DD')", DateFrom));
        }
        reviewCount = frameInfo.jxfU3.getText();
        if(!reviewCount.equals("")){
            int s = Integer.parseInt(reviewCount);
            String op = frameInfo.u1.getSelectedItem().toString();
            conditions.add(String.format("REVIEW_COUNT %s %d", op, s));
        }
        friends = frameInfo.jxfU4.getText();
        if(!reviewCount.equals("")){
            int s = Integer.parseInt(friends);
            String op = frameInfo.u2.getSelectedItem().toString();
            conditions.add(String.format("FRIENDS_NUM %s %d", op, s));
        }
        stars = frameInfo.jxfU5.getText();
        if(!stars.equals("")){
            double s = Double.parseDouble(stars);
            String op = frameInfo.u3.getSelectedItem().toString();
            conditions.add(String.format("AVERAGE_STARS %s %f", op, s));
        }
        voteCount = frameInfo.jxfU6.getText();
        if(!voteCount.equals("")){
            int v = Integer.parseInt(voteCount);
            String op = frameInfo.u4.getSelectedItem().toString();
            conditions.add(String.format("VOTES %s %d", op, v));
        }
        
        String sql = "SELECT * FROM Users WHERE ";
        String final_q;
        if(conditions.size() > 0){
            bu = frameInfo.comboBoxUser.getSelectedItem().toString();
            final_q = sql + String.join(" " + bu + " ", conditions);
        }
        else{
            final_q = sql + "ROWNUM <= 100";
        }
        ResultSet queryRes = qm.fetchQuery(final_q);
        Vector<Vector> qv = qm.queryVector(queryRes);
        System.out.println(final_q);
        return qv;
    }
    
    public Vector<Vector> queryReview(hw3 frameInfo){
        ArrayList<String> conditions = new ArrayList();
        String DateFrom, DateTo, stars, voteCount;
        DateFrom = frameInfo.jxf1.getText();
        if(!DateFrom.equals("")){
            conditions.add(String.format("R.Publishdate >= To_date('%s', 'YYYY-MM-DD')", DateFrom));
        }
        DateTo = frameInfo.jxf2.getText();
        if(!DateTo.equals("")){
            conditions.add(String.format("R.Publishdate <= To_date('%s', 'YYYY-MM-DD')", DateTo));
        }
        stars = frameInfo.jxf3.getText();
        if(!stars.equals("")){
            int s = Integer.parseInt(stars);
            String op = frameInfo.r1.getSelectedItem().toString();
            conditions.add(String.format("R.STARS %s %d", op, s));
        }
        voteCount = frameInfo.jxf4.getText();
        if(!voteCount.equals("")){
            int v = Integer.parseInt(voteCount);
            String op = frameInfo.r2.getSelectedItem().toString();
            conditions.add(String.format("R.VOTES %s %d", op, v));
        }
        
        String BID = frameInfo.jt1.getValueAt(frameInfo.jt1.getSelectedRow(), 0).toString();
        String sql = String.format("SELECT R.STARS, R.PUBLISHDATE, R.VOTES, R.RTEXT, U.UNAME as Author FROM REVIEW R, Users U "
                + "WHERE R.AUTHOR=U.USERID AND BusinessID='%s'", BID);
        String final_q = sql;
        if(conditions.size() > 0){
            final_q = sql + " AND " + String.join(" AND ", conditions);
        }
        ResultSet queryRes = qm.fetchQuery(final_q);
        Vector<Vector> qv = qm.queryVector(queryRes);
        System.out.println(final_q);
        return qv;
    }
    
    public Vector<Vector> queryBusiness(hw3 frameInfo){
        
        String BID_query;
        if(frameInfo.business_bool.equals("AND")){
            String B1_query = constructSqlANDForB1(frameInfo);
            String B2_query = constructSqlANDForB2(frameInfo);
            if(frameInfo.attributes.size() < 1){
                BID_query = "(" + B1_query + ") INTERSECT (" + B2_query + ")";
            }
            else{
                String B3_query = constructSqlANDForAtt(frameInfo);
                BID_query = "(" + B1_query + ") INTERSECT (" + B2_query + ") INTERSECT (" + B3_query + ")";
            }
        }
        else{
            String B1_query = constructSqlORForB1(frameInfo);
            String B2_query = constructSqlORForB2(frameInfo);
            if(frameInfo.attributes.size() < 1){
                BID_query = "(" + B1_query + ") INTERSECT (" + B2_query + ")";
            }
            else{
                String B3_query = constructSqlORForAtt(frameInfo);
                BID_query = "(" + B1_query + ") INTERSECT (" + B2_query + ") INTERSECT (" + B3_query + ")";
            }
        }
        String final_q = "SELECT * FROM Business WHERE BusinessID IN(" + BID_query + ")";
        ResultSet queryRes = qm.fetchQuery(final_q);
        Vector<Vector> qv = qm.queryVector(queryRes);
        System.out.println(final_q);
        return qv;
    }
    
    public ArrayList<String> queryForSecondBusiness(hw3 frameInfo){
        ArrayList<String> res = new ArrayList<>();
        
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
    
    public ArrayList<String> queryForFirstBusiness(hw3 frameInfo){
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
    
    private String constructSqlANDForB1(hw3 frameInfo){
        ArrayList<String> res_list = new ArrayList<>();
        for(String cate: frameInfo.category_selected){
            String res = String.format("SELECT DISTINCT BusinessID FROM Business_Categories" +
                                            " WHERE CNAME='%s'", sf.parseString(cate));
            res_list.add(res);
        }
        return String.join(" INTERSECT ", res_list);
    }
    
    private String constructSqlORForB1(hw3 frameInfo){
        ArrayList<String> res_list = new ArrayList<>();
        for(String cate: frameInfo.category_selected){
            String res = String.format("CNAME='%s'", sf.parseString(cate));
            res_list.add(res);
        }
        String head = "SELECT DISTINCT BusinessID FROM Business_Categories WHERE ";
        String tail = String.join(" or ", res_list);
        return head + tail;
    }
    
    private String constructSqlANDForB2(hw3 frameInfo){
        ArrayList<String> res_list = new ArrayList<>();
        for(String subcate: frameInfo.subcates){
            String res = String.format("SELECT DISTINCT BusinessID FROM Business_Subcategories" +
                                            " WHERE SubCNAME='%s'", sf.parseString(subcate));
            res_list.add(res);
        }
        return String.join(" INTERSECT ", res_list);
    }
    
    private String constructSqlORForB2(hw3 frameInfo){
        ArrayList<String> res_list = new ArrayList<>();
        for(String subcate: frameInfo.subcates){
            String res = String.format("SubCNAME='%s'", sf.parseString(subcate));
            res_list.add(res);
        }
        String head = "SELECT DISTINCT BusinessID FROM Business_Subcategories WHERE ";
        String tail = String.join(" or ", res_list);
        return head + tail;
    }
    
    private String constructSqlANDForAtt(hw3 frameInfo){
        ArrayList<String> res_list = new ArrayList<>();
        for(String attr: frameInfo.attributes){
            String res = String.format("SELECT DISTINCT BusinessID FROM Business_Att" +
                                            " WHERE ANAME='%s'", sf.parseString(attr));
            res_list.add(res);
        }
        return String.join(" INTERSECT ", res_list);
    }
    
    private String constructSqlORForAtt(hw3 frameInfo){
        ArrayList<String> res_list = new ArrayList<>();
        for(String attr: frameInfo.attributes){
            String res = String.format("ANAME='%s'", sf.parseString(attr));
            res_list.add(res);
        }
        String head = "SELECT DISTINCT BusinessID FROM Business_Att WHERE ";
        String tail = String.join(" or ", res_list);
        return head + tail;
    }
}
