/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIDemo;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import Table_Generated.Business;
import java.awt.*;
/**
 *
 * @author 10337
 */
public class Demo extends JFrame {
    ArrayList<JCheckBox> jb_list1;
    HashSet<String> category_selected;
    String business_bool = "AND";
    
    private void print_list_1(){
        String res = "";
        for(String s: category_selected){
            res += s + ", ";
        }
        System.out.println("category selected changes: " + res);
    }
    
    private void add_button_first(JPanel jp){
        jb_list1 = new ArrayList<>();
        category_selected = new HashSet<>();
        Color color = jp.getBackground();
        for(String cate: Business.all_cate){
            JCheckBox jCheckBox_cur = new JCheckBox(cate);
            jCheckBox_cur.setVisible(true);
            jCheckBox_cur.setBackground(color);
            jCheckBox_cur.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    AbstractButton abstractButton = (AbstractButton) evt.getSource();
                    String cur_text = abstractButton.getText();
                    boolean selected = abstractButton.getModel().isSelected();
                    if(selected){
                        category_selected.add(cur_text);
                    }
                    else{
                        category_selected.remove(cur_text);
                    }
                    print_list_1();
                }
            });
            jb_list1.add(jCheckBox_cur);
            jp.add(jCheckBox_cur);
//            System.out.println("add " + cate);
        }
    }
    
    private JPanel firstjp(){
        JPanel jp1 = new JPanel();
        jp1.setBounds(0, 0, 350, 420);
        jp1.setVisible(true);
        jp1.setLayout(new GridLayout(0, 2));
        jp1.setBackground(new java.awt.Color(204, 204, 255));
        jp1.setBorder(new TitledBorder(new EtchedBorder(), "Categories"));
        add_button_first(jp1);
        return jp1;
    }
    
    private JComboBox<String> firstJcb(){
        String labels[] = {"AND", "OR"};
        JComboBox<String> comboBox = new JComboBox<String>(labels);
        comboBox.setEditable(true);
        comboBox.setBounds(0, 421, 350, 25);
        comboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> cb = (JComboBox<String>) e.getSource();
                business_bool = cb.getSelectedItem().toString();
                System.out.println("Business select state changes to " + business_bool);
            }
        });
        return comboBox;
    }
    
    public Demo() {
        Container container = this.getContentPane();
        // 设置流布局管理器，2是右对齐，后两个参数分别为组件间的水平间隔和垂直间隔
        this.setLayout(null);
//        JScrollPane scrollPane = new JScrollPane(jp1);
//        scrollPane.setBounds(0, 0, 350, 420);
//        jp1.setPreferredSize(new Dimension(350, 420));
//        jp1.revalidate();
        container.add(firstjp());
        container.add(firstJcb());

        this.setSize(2000, 1000);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
         new Demo();
    }
}
