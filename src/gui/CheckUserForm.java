package gui;

import database.SQLProxy;
import tools.StringTools;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by catten on 15/6/25.
 */
public class CheckUserForm extends JInternalFrame implements ActionListener {
    //private static CheckUserForm cuf;

    JTextField t_name;
    JButton btn_submit;
    JScrollPane scrollPane;
    JTable table;

    SQLProxy sqlProxy = SQLProxy.getProxy();

    private String userid;

    String[] columeName = new String[]{
            "User ID","用户名","备注"
    };

/*
    public static CheckUserForm getForm(){
        if(cuf == null){
            cuf = new CheckUserForm("查询用户");
        }

        return cuf;
    }
//*/
    public CheckUserForm(String title) {
        super(title, true, true, true, true);
        setSize(320, 480);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setUpUIComponents();
        setUpEventListener();
    }

    private void setUpUIComponents(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbci = new GridBagConstraints();
        gbci.fill = GridBagConstraints.BOTH;
        gbci.insets = MainForm.insets;

        gbci.gridx = 0;
        gbci.gridy = 0;
        add(new JLabel("用户名或ID",JLabel.RIGHT),gbci);

        t_name = new JTextField();
        gbci.gridx ++;
        gbci.weightx = 1;
        add(t_name,gbci);

        gbci.weightx = 0;
        gbci.gridx++;
        btn_submit = new JButton("查询");
        add(btn_submit,gbci);

        gbci.gridx = 0;
        gbci.gridy++;
        gbci.gridwidth = 3;
        add(new JLabel("查询结果",JLabel.LEFT),gbci);

        table = new JTable(new DefaultTableModel(null,columeName));
        scrollPane = new JScrollPane(table);
        gbci.gridy++;
        gbci.weighty = 1;
        add(scrollPane,gbci);
    }

    private void setUpEventListener(){
        btn_submit.addActionListener(this);
    }

    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_submit){
            String searchInfo = t_name.getText();
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            while (table.getRowCount() > 0){
                tableModel.removeRow(0);
            }
            if(searchInfo.equals("")){
                searchInfo = "%";
            }
            ArrayList arrayList;
            if(StringTools.isNumeric(searchInfo)){
                arrayList = sqlProxy.proxyQuery(String.format("select id,username,ifnull(details,'') from systemusers where id = %s",searchInfo));
            }else{
                arrayList = sqlProxy.proxyQuery(String.format("select id,username,ifnull(details,'') from systemusers where username like '%%%s%%'",searchInfo));
            }
            if(arrayList != null && arrayList.size() > 0){
                Object[] datas;
                for (int i = 0; i < arrayList.size(); i++) {
                    datas = (Object[])arrayList.get(i);
                    tableModel.addRow(datas);
                }
            }else {
                JOptionPane.showMessageDialog(this,"无搜索结果",getTitle(),JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
