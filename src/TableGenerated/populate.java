/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableGenerated;

/**
 *
 * @author 10337
 */
public class populate {
    public static void main(String [] Args){
        UsersTable ut = new UsersTable();
        ut.parseFile("./data/yelp_user.json");
        ut.populateUsers();
        
        BusinessRelated br = new BusinessRelated();
        System.out.println(String.format("Category size: %d", Business.cate_set.size()));
        br.parseFile("./data/yelp_business.json");
        br.populateBusiness();
        br.populateCategory();
        br.populateSubcategory();
        br.populateAttributes();
        
        ReviewTable rt = new ReviewTable();
        rt.parseFile("./data/yelp_review.json");
        rt.populateReviews();
        
        Checkin ck = new Checkin();
        ck.parseFile("./data/yelp_checkin.json");
        ck.populateCheckin();
    }
}
