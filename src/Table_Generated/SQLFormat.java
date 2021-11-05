/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Table_Generated;

/**
 *
 * @author 10337
 */
public class SQLFormat {
    public String parseString(String origin){
        String res = origin.replace("'", "''");
        return res;
    }
}
