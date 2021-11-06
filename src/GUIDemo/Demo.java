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
/**
 *
 * @author 10337
 */
public class Demo extends JFrame {
    ArrayList<JCheckBox> jb_list1;
    HashSet<String> category_selected, subcates, attributes;
    String business_bool = "AND";
    QueryHandler qh;
    
    Demo overallFrame;
    JPanel jp1, jp2, jp3;
    JComboBox<String> comboBoxBusiness;
    JScrollPane scrollPane1, scrollPane2, scrollPane3;
    JTable jt1;
    JButton jb1, jb2;
    ArrayList<JDialog> jdf_list;
    ArrayList<JTable> jt_list;
    ArrayList<JScrollPane> jsp_list;
    
    private void print_list_1(){
        String res = "";
        for(String s: category_selected){
            res += s + ", ";
        }
        System.out.println("category selected changes: " + res);
    }
    
    private void print_list_2(){
        String res = "";
        for(String s: subcates){
            res += s + ", ";
        }
        System.out.println("subcategory selected changes: " + res);
    }
    
    private void print_list_3(){
        String res = "";
        for(String s: attributes){
            res += s + ", ";
        }
        System.out.println("attribute selected changes: " + res);
    }
    
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
    
    private void resetB3(){
        jp3.removeAll();
        jp3.revalidate();
    }
    
    private void resetB2(){
        resetB3();
        jp2.removeAll();
        jp2.revalidate();
    }
        
    private void resetB1(){
        resetB2();
        category_selected.clear();
        for(var bt: jb_list1){
            bt.setSelected(false);
        }
    }
    
    private void resetAll(){
        resetB1();
        overallFrame.repaint();
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
                    queryForB1();
                }
            });
            jb_list1.add(jCheckBox_cur);
            jp.add(jCheckBox_cur);
//            System.out.println("add " + cate);
        }
    }
    
    private JPanel firstjp(){
        jp1 = new JPanel();
        jp1.setBounds(0, 0, 400, 470);
        jp1.setVisible(true);
        jp1.setLayout(new GridLayout(0, 2));
        jp1.setBackground(new java.awt.Color(204, 204, 255));
        jp1.setBorder(new TitledBorder(new EtchedBorder(), "Categories"));
        add_button_first(jp1);
        return jp1;
    }
    
    private JComboBox<String> firstJcb(){
        String labels[] = {"AND", "OR"};
        comboBoxBusiness = new JComboBox<String>(labels);
        comboBoxBusiness.setEditable(true);
        comboBoxBusiness.setBounds(0, 471, 1500, 25);
        comboBoxBusiness.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> cb = (JComboBox<String>) e.getSource();
                String cur_bool = cb.getSelectedItem().toString();
                if(business_bool != cur_bool){
                    business_bool = cur_bool;
                    resetAll();
                    System.out.println("Business select state changes to " + business_bool);
                }
            }
        });
        return comboBoxBusiness;
    }
    
    private JScrollPane secondjp(){
        jp2 = new JPanel();
        jp2.setVisible(true);
        jp2.setLayout(new GridLayout(0, 2));
        jp2.setBackground(new java.awt.Color(255, 204, 204));
        jp2.setBorder(new TitledBorder(new EtchedBorder(), "Subcategories"));
        
        scrollPane1 = new JScrollPane(jp2, 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setBounds(401, 0, 400, 470);
        jp2.revalidate();
        return scrollPane1;
    }
    
    private JScrollPane thirdjp(){
        jp3 = new JPanel();
        jp3.setVisible(true);
        jp3.setLayout(new GridLayout(0, 3));
        jp3.setBackground(new java.awt.Color(204, 255, 204));
        jp3.setBorder(new TitledBorder(new EtchedBorder(), "Attributes"));
        
        scrollPane2 = new JScrollPane(jp3, 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setBounds(801, 0, 700, 470);
        jp3.revalidate();
        return scrollPane2;
    }
    
    private JScrollPane firstjt(){
        jt1 = new JTable(40, 5);
        jt1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
               if (me.getClickCount() == 2) {
                  String BID = jt1.getValueAt(jt1.getSelectedRow(), 0).toString();
                  String BName = jt1.getValueAt(jt1.getSelectedRow(), 1).toString();
                  System.out.println(BID + " " + BName);
                  JTable curJt = popWindow(BName);
               }
            }
         });
        
        scrollPane3 = new JScrollPane(jt1, 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.setBounds(0, 496, 1200, 485);
        jt1.revalidate();
        return scrollPane3;
    }
    
    private JButton queryBusinessButton(){
        jb1 = new JButton("Execute Query for Business");
        jb1.setBounds(0, 980, 600, 20);
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
                    jt1.revalidate();
                    overallFrame.repaint();
                }
            });
        return jb1;
    }
    
    private JButton queryUserButton(){
        jb2 = new JButton("Execute Query for User");
        jb2.setBounds(601, 980, 600, 20);
        return jb2;
    }
    
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
        jdf1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        jdf_list.add(jdf1);
        jt_list.add(curJt);
        jsp_list.add(curScrollPane);
        
        return curJt;
    }
    
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
        container.add(firstjt());
        container.add(queryBusinessButton());
        container.add(queryUserButton());

        this.setSize(2000, 1000);
        this.setTitle("Query Panel");
        this.setVisible(true);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
         new Demo();
    }
}
