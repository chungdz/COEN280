/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableGenerated;

/**
 *
 * @author 10337
 */
public class SQLFormat {
    public String parseString(String origin){
        String res = origin.replace("'", "''").replace("&", "'||'&'||'");
        return res;
    }
}
