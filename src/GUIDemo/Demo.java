/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIDemo;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import TableGenerated.Business;
import java.awt.*;
import java.sql.ResultSet;
import java.awt.font.*;
/**
 *
 * @author 10337
 */
public class Demo extends JFrame {
    ArrayList<JCheckBox> jb_list1;
    HashSet<String> category_selected, subcates, attributes;
    String business_bool = "AND", user_bool = "AND", table_state="NULL";
    QueryHandler qh;
    
    Demo overallFrame;
    JPanel jp1, jp2, jp3, jp4, jp5;
    JComboBox<String> comboBoxBusiness, comboBoxUser, r1, r2, u1, u2, u3, u4;
    JScrollPane scrollPane1, scrollPane2, scrollPane3;
    JTable jt1;
    JButton jb1, jb2;
    ArrayList<JDialog> jdf_list;
    ArrayList<JTable> jt_list;
    ArrayList<JScrollPane> jsp_list;
    JTextField jxf1, jxf2, jxf3, jxf4;
    JTextField jxfU1, jxfU2, jxfU3, jxfU4, jxfU5, jxfU6;
    JLabel jl1, jl2, jl3, jl4;
    JLabel jlU1, jlU2, jlU3, jlU4, jlU5, jlU6, jlU7, jlU8, jlU9, jlU10;
    Font font1 = new Font("SansSerif", Font.BOLD, 18);
    String selectLabels[] = {"AND", "OR"};
    String rangeLabels[] = {"=", ">", "<"};
    
    /*
    print selected category keywords
    */
    private void print_list_1(){
        String res = "";
        for(String s: category_selected){
            res += s + ", ";
        }
        System.out.println("category selected changes: " + res);
    }
    
    /*
    print selected subcategory keywords
    */
    private void print_list_2(){
        String res = "";
        for(String s: subcates){
            res += s + ", ";
        }
        System.out.println("subcategory selected changes: " + res);
    }
    
    /*
    print selected attribute keywords
    */
    private void print_list_3(){
        String res = "";
        for(String s: attributes){
            res += s + ", ";
        }
        System.out.println("attribute selected changes: " + res);
    }
    /*
    print review selected options
    */
    private void printReviewOptions(){
        String states = "";
        states += "Date from: " + jxf1.getText() + " to " + jxf2.getText();
        states += " AND star " + r1.getSelectedItem().toString() + " " + jxf3.getText();
        states += " AND vote " + r2.getSelectedItem().toString() + " " + jxf4.getText();
        System.out.println(states);
    }
    /*
    renew attribute panel
    query for valid attributes
    add check box for each attribute
    add select listener for attributes change
    */
    private void queryForB2(){
        jp3.removeAll();
        attributes.clear();
        
        ArrayList<String> att_arr = qh.queryForSecondBusiness(this);
        Color color = jp3.getBackground();
        for(String att: att_arr){
            JCheckBox jCheckBox_cur = new JCheckBox(att);
            jCheckBox_cur.setVisible(true);
            jCheckBox_cur.setBackground(color);
            jCheckBox_cur.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    AbstractButton abstractButton = (AbstractButton) evt.getSource();
                    String cur_text = abstractButton.getText();
                    boolean selected = abstractButton.getModel().isSelected();
                    if(selected){
                        attributes.add(cur_text);
                    }
                    else{
                        attributes.remove(cur_text);
                    }
                    print_list_3();
                }
            });
            jp3.add(jCheckBox_cur);
        }
        
        jp3.revalidate();
        overallFrame.repaint();
    }
    /*
    renew subcategory panel
    query for valid subcategories
    add check box for each subcategory
    add select listener for subcategories change
    */
    private void queryForB1(){
        resetB3();
        jp2.removeAll();
        subcates.clear();
        
        ArrayList<String> subcates_arr = qh.queryForFirstBusiness(this);
        Color color = jp2.getBackground();
        for(String sc: subcates_arr){
            JCheckBox jCheckBox_cur = new JCheckBox(sc);
            jCheckBox_cur.setVisible(true);
            jCheckBox_cur.setBackground(color);
            jCheckBox_cur.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    AbstractButton abstractButton = (AbstractButton) evt.getSource();
                    String cur_text = abstractButton.getText();
                    boolean selected = abstractButton.getModel().isSelected();
                    if(selected){
                        subcates.add(cur_text);
                    }
                    else{
                        subcates.remove(cur_text);
                    }
                    print_list_2();
                    queryForB2();
                }
            });
            jp2.add(jCheckBox_cur);
        }
        
        jp2.revalidate();
        overallFrame.repaint();
    }
    /* reset attribute panel */
    private void resetB3(){
        jp3.removeAll();
        jp3.revalidate();
    }
    /* reset subcategory panel as well as attribute panel */
    private void resetB2(){
        resetB3();
        jp2.removeAll();
        jp2.revalidate();
    }
    /* reset category panel and subcategory panel as well as attribute panel */ 
    private void resetB1(){
        resetB2();
        category_selected.clear();
        for(var bt: jb_list1){
            bt.setSelected(false);
        }
    }
    /* reset panels and repaint */
    private void resetAll(){
        resetB1();
        overallFrame.repaint();
    }
    /* 
    add static category check box from 26 category list
    add listener for all check box
    call for queryForB1 when check boxes are changing
    */
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
                    queryForB1();
                }
            });
            jb_list1.add(jCheckBox_cur);
            jp.add(jCheckBox_cur);
//            System.out.println("add " + cate);
        }
    }
    
    /* JPanel init for category panel */
    private JPanel firstjp(){
        jp1 = new JPanel();
        jp1.setBounds(0, 0, 400, 475);
        jp1.setVisible(true);
        jp1.setLayout(new GridLayout(0, 2));
        jp1.setBackground(new java.awt.Color(204, 204, 255));
        jp1.setBorder(new TitledBorder(new EtchedBorder(), "Categories"));
        add_button_first(jp1);
        return jp1;
    }
    /* 
    combo box init for one under category panel 
    add listener for changing, when changing reset 3 panels
    */
    private JComboBox<String> firstJcb(){
        comboBoxBusiness = new JComboBox(selectLabels);
        comboBoxBusiness.setEditable(true);
        comboBoxBusiness.setBounds(0, 476, 1400, 20);
        comboBoxBusiness.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> cb = (JComboBox<String>) e.getSource();
                String cur_bool = cb.getSelectedItem().toString();
                if(!business_bool.equals(cur_bool)){
                    business_bool = cur_bool;
                    resetAll();
                    System.out.println("Business select state changes to " + business_bool);
                }
            }
        });
        return comboBoxBusiness;
    }
    /* combo box init for one under user panel */
    private JComboBox<String> UserJcb(){
        comboBoxUser = new JComboBox(selectLabels);
        comboBoxUser.setEditable(true);
        comboBoxUser.setBounds(1201, 981, 500, 20);
        return comboBoxUser;
    }
    /* JPanel init for subcatgeory panel, inside a JScrollPane */
    private JScrollPane secondjp(){
        jp2 = new JPanel();
        jp2.setVisible(true);
        jp2.setLayout(new GridLayout(0, 2));
        jp2.setBackground(new java.awt.Color(255, 204, 204));
        jp2.setBorder(new TitledBorder(new EtchedBorder(), "Subcategories"));
        
        scrollPane1 = new JScrollPane(jp2, 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setBounds(401, 0, 400, 475);
        jp2.revalidate();
        return scrollPane1;
    }
    /* JPanel init for attribute panel, inside a JScrollPane */
    private JScrollPane thirdjp(){
        jp3 = new JPanel();
        jp3.setVisible(true);
        jp3.setLayout(new GridLayout(0, 3));
        jp3.setBackground(new java.awt.Color(204, 255, 204));
        jp3.setBorder(new TitledBorder(new EtchedBorder(), "Attributes"));
        
        scrollPane2 = new JScrollPane(jp3, 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setBounds(801, 0, 600, 475);
        jp3.revalidate();
        return scrollPane2;
    }
    /* JPanel init for review option panel
    jxf1: Date from
    jxf2: Date less than
    r1: star operator
    jxf3: star number
    r2: vote operator
    jxf4: vote number
    */
    private JPanel reviewJP(){
        jp4 = new JPanel();
        jp4.setLayout(new GridLayout(0, 1));
        jp4.setBounds(1401, 0, 300, 495);
        jp4.setBackground(new java.awt.Color(153, 255, 204));
        jp4.setBorder(new TitledBorder(new EtchedBorder(), "Review Option"));
        
        jl1 = new JLabel("From Date YYYY-MM-DD");
        jxf1 = new JTextField();
        jxf1.setFont(font1);
        jl2 = new JLabel("To Date YYYY-MM-DD");
        jxf2 = new JTextField();
        jxf2.setFont(font1);
        jl3 = new JLabel("Star ⭐⭐⭐⭐⭐ range");
        r1 = new JComboBox(rangeLabels);
        jxf3 = new JTextField();
        jxf3.setFont(font1);
        jl4 = new JLabel("Vote ♥♥♥♥♥ range");
        r2 = new JComboBox(rangeLabels);
        jxf4 = new JTextField();
        jxf4.setFont(font1);
        
        jp4.add(jl1);
        jp4.add(jxf1);
        jp4.add(jl2);
        jp4.add(jxf2);
        jp4.add(jl3);
        jp4.add(r1);
        jp4.add(jxf3);
        jp4.add(jl4);
        jp4.add(r2);
        jp4.add(jxf4);
        
        jp4.setVisible(true);
        return jp4;
    }
    /* JPanel init for user option panel
    jxfU2: date since
    u1: review count operator
    jxfU3: review count number
    u2: friend number operator
    jxfU4: friend number
    u3: average star operator
    jxfU5: average star
    u4: vote number operator
    jxfU6: vote number
    */
    private JPanel UserJP(){
        jp5 = new JPanel();
        jp5.setLayout(new GridLayout(0, 2));
        jp5.setBounds(1201, 496, 500, 485);
        jp5.setBackground(new java.awt.Color(255, 255, 204));
        jp5.setBorder(new TitledBorder(new EtchedBorder(), "User Option"));
        
        jlU1 = new JLabel("Member Since:");
        jlU2 = new JLabel("Value: YYYY-MM-DD");
        jxfU1 = new JTextField(">");
        jxfU1.setEditable(false);
        jxfU1.setFont(font1);
        jxfU2 = new JTextField();
        jxfU2.setFont(font1);
        jlU3 = new JLabel("Review Count range");
        jlU4 = new JLabel("Value: ");
        u1 = new JComboBox(rangeLabels);
        jxfU3 = new JTextField();
        jxfU3.setFont(font1);
        jlU5 = new JLabel("Number of friends range");
        jlU6 = new JLabel("Value: ");
        u2 = new JComboBox(rangeLabels);
        jxfU4 = new JTextField();
        jxfU4.setFont(font1);
        jlU7 = new JLabel("Average Stars ⭐⭐⭐⭐⭐ range");
        jlU8 = new JLabel("Value: ");
        u3 = new JComboBox(rangeLabels);
        jxfU5 = new JTextField();
        jxfU5.setFont(font1);
        jlU9 = new JLabel("Number of Votes ♥♥♥♥♥ range");
        jlU10 = new JLabel("Value: ");
        u4 = new JComboBox(rangeLabels);
        jxfU6 = new JTextField();
        jxfU6.setFont(font1);
        
        jp5.add(jlU1);
        jp5.add(jlU2);
        jp5.add(jxfU1);
        jp5.add(jxfU2);
        jp5.add(jlU3);
        jp5.add(jlU4);
        jp5.add(u1);
        jp5.add(jxfU3);
        jp5.add(jlU5);
        jp5.add(jlU6);
        jp5.add(u2);
        jp5.add(jxfU4);
        jp5.add(jlU7);
        jp5.add(jlU8);
        jp5.add(u3);
        jp5.add(jxfU5);
        jp5.add(jlU9);
        jp5.add(jlU10);
        jp5.add(u4);
        jp5.add(jxfU6);
        
        jp5.setVisible(true);
        return jp5;
    }
    /*
    Table for both business and user query
    inside a JscrollPane
    pop out review table if double click business table rows for that business
    */
    private JScrollPane firstjt(){
        jt1 = new JTable(40, 5);
        jt1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
               if (me.getClickCount() == 2 && table_state.equals("Business")) {
                    String BID = jt1.getValueAt(jt1.getSelectedRow(), 0).toString();
                    String BName = jt1.getValueAt(jt1.getSelectedRow(), 1).toString();
//                    System.out.println(BID + " " + BName);
                    printReviewOptions();
                    JTable curJt = popWindow(BName);
                    
                    curJt.getParent().repaint();
               }
            }
         });
        
        scrollPane3 = new JScrollPane(jt1, 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.setBounds(0, 496, 1200, 485);
        jt1.revalidate();
        return scrollPane3;
    }
    /*
    button for query business under table 
    add listener when clicked start check and query
    change table state
    */
    private JButton queryBusinessButton(){
        jb1 = new JButton("Execute Query for Business");
        jb1.setBounds(0, 981, 600, 20);
        jb1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    System.out.println("Current condition for query:");
                    print_list_1();
                    print_list_2();
                    print_list_3();
                    if(category_selected.size() < 1 || subcates.size() < 1){
                        System.out.println("Must select both category and subcategory:");
                        return;
                    }
                    Vector<Vector> qv = qh.queryBusiness(overallFrame);
                    Vector<Vector> qt = new Vector<Vector>(qv.subList(1, qv.size()));
                    Vector qh = qv.get(0);
                    DefaultTableModel tableModel = new DefaultTableModel(qt, qh){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                           return false;
                        }
                    };
                    jt1.setModel(tableModel);
                    table_state = "Business";
                    jt1.revalidate();
                    overallFrame.repaint();
                }
            });
        return jb1;
    }
    /*
    button for query business under table 
    add listener when clicked start check and query
    change table state
    */
    private JButton queryUserButton(){
        jb2 = new JButton("Execute Query for User");
        jb2.setBounds(601, 981, 600, 20);
        return jb2;
    }
    /*
    review window that will be poped out
    */
    private JTable popWindow(String BName){
        JDialog jdf1 = new JDialog();
        Container cnt = jdf1.getContentPane();
        
        JTable curJt = new JTable(40, 5);
        JScrollPane curScrollPane = new JScrollPane(curJt, 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cnt.add(curScrollPane);
        curJt.revalidate();
        
        jdf1.setSize(1200, 500);
        jdf1.setTitle("Review Table for " + BName);
        jdf1.setVisible(true);
        
        //        jdf_list.add(jdf1);
        //        jt_list.add(curJt);
        //        jsp_list.add(curScrollPane);
        
        return curJt;
    }
    /*
    initialization for main JFrame
    add all JPanel
    */
    public Demo() {
        qh = new QueryHandler();
        subcates = new HashSet<>();
        attributes = new HashSet<>();
        overallFrame = this;
        
        Container container = this.getContentPane();
        // 设置流布局管理器，2是右对齐，后两个参数分别为组件间的水平间隔和垂直间隔
        this.setLayout(null);

        container.add(firstjp());
        container.add(firstJcb());
        container.add(secondjp());
        container.add(thirdjp());
        container.add(reviewJP());
        container.add(firstjt());
        container.add(queryBusinessButton());
        container.add(queryUserButton());
        container.add(UserJP());
        container.add(UserJcb());

        this.setSize(1700, 1000);
        this.setTitle("Query Panel");
        this.setVisible(true);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
         new Demo();
    }
}
