package net.catten.hrsys.gui;

import net.catten.hrsys.database.SQLProxy;
import net.catten.hrsys.tools.StringTools;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by catten on 15/6/19.
 */
public class AddStaffForm extends JInternalFrame implements ActionListener{

    JLabel label_useridinfo;
    String useridinfo_placeholder = "不关联系统用户";
    JTextField t_code,t_name;
    JTextArea t_infos;
    JComboBox cb_gender,cb_department;
    JButton btn_add,btn_autofillcode,btn_setlinkeduserid;

    private int userid = -1;
    private int[] depIDList;
    private String[] deptitles;

    //database.SQLServerConnector sqlServerConnector = SQLServerConnector.getObject();
    SQLProxy sqlProxy = SQLProxy.getProxy();

    public AddStaffForm(String title){
        super(title, false, true, false,true);//title,resizeable,closeabel,maxminizable
        setSize(300, 400);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setUpUIComponents();
        setUpEventListener();
    }

    //*/
    private void setUpUIComponents(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = MainForm.insets;
        setLayout(new GridBagLayout());
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("编号:", JLabel.RIGHT), gbc);

        gbc.weightx = 1;
        gbc.gridx++;
        gbc.weightx = 0.5;
        t_code = new JTextField();
        add(t_code,gbc);

        btn_autofillcode = new JButton("随机");
        gbc.gridx++;
        gbc.weightx = 0;
        add(btn_autofillcode,gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("姓名:",JLabel.RIGHT),gbc);

        gbc.gridx++;
        gbc.gridwidth = 2;
        t_name = new JTextField();
        add(t_name,gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        add(new JLabel("性别:", JLabel.RIGHT), gbc);

        cb_gender = new JComboBox(new String[]{"男","女"});
        gbc.gridwidth = 2;
        gbc.gridx++;
        add(cb_gender, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        add(new JLabel("部门:",JLabel.RIGHT),gbc);

        gbc.gridx++;
        gbc.gridwidth = 2;
        cb_department = new JComboBox();
        add(cb_department,gbc);

        ArrayList deps = sqlProxy.proxyQuery("select ID,Name from DepartmentInfo");
        Object[] depsrow;
        depIDList = new int[deps.size()];
        deptitles = new String[deps.size()];
        for (int i = 0; i < deps.size(); i++) {
            depsrow = ((Object[])deps.get(i)).clone();
            depIDList[i] = (int)depsrow[0];
            cb_department.addItem(String.format("%d - %s",depIDList[i],(String)depsrow[1]));
        }//*/

        t_infos = new JTextArea();
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 1;
        JScrollPane scrollPane = new JScrollPane(t_infos);
        scrollPane.setBorder(new TitledBorder("备注"));
        add(scrollPane, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weighty = 0;
        add(new JLabel("关联系统账号",JLabel.LEFT),gbc);

        label_useridinfo = new JLabel(useridinfo_placeholder,JLabel.CENTER);
        gbc.gridwidth = 2;
        gbc.gridy++;
        add(label_useridinfo,gbc);

        btn_setlinkeduserid = new JButton("关联用户");
        gbc.gridwidth = 1;
        gbc.gridx+=2;
        add(btn_setlinkeduserid, gbc);

        btn_add = new JButton("添加");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        add(btn_add,gbc);
    }

    private void setUpEventListener(){
        btn_add.addActionListener(this);
        btn_setlinkeduserid.addActionListener(this);
        btn_autofillcode.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_setlinkeduserid) {
            String searchinfo = JOptionPane.showInputDialog(
                    this,
                    "输入需要添加的用户名或用户ID\n 输入-1可解绑 \n(会覆盖原有绑定)",
                    "关联用户",
                    JOptionPane.INFORMATION_MESSAGE
            );
            //System.out.println(searchinfo);
            String temp_username;
            if (!searchinfo.equals("")) {
                if (StringTools.isNumeric(searchinfo)) {
                    if (searchinfo.equals("-1")) {
                        userid = -1;
                        label_useridinfo.setText(useridinfo_placeholder);
                    } else if ((temp_username = sqlProxy.checkUserByID(Integer.parseInt(searchinfo))) != null) {
                        userid = Integer.parseInt(searchinfo);
                        label_useridinfo.setText(String.format("%d - %s", userid, temp_username));
                    }
                } else {
                    if (!((userid = sqlProxy.checkUserByName(searchinfo)) == -1)) {
                        label_useridinfo.setText(String.format("%d - %s", userid, searchinfo));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "没有符合条件的用户。",
                        "搜索用户",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }else if(e.getSource() == btn_autofillcode){
            t_code.setText(StringTools.RandomNumber());

        }else if(e.getSource() == btn_add){
            /*
            System.out.printf(
                    "Add staff action:Code:%s Name:%s Gender:%s\n",
                    t_searchIDName.getText(),
                    t_searchbox.getText(),
                    cb_gender.getSelectedItem().toString()
            );//*/
            String gender;
            if (cb_gender.getSelectedIndex() == 0){ gender = "male"; }else {gender = "female";};
            if(sqlProxy.proxyExcute(String.format(
                            "insert into StaffInfo(id,name,gender,department,details) values(%s,\'%s\',\'%s\',%s,\'%s\');",
                            t_code.getText(),
                            t_name.getText(),
                            gender,
                            depIDList[cb_department.getSelectedIndex()],
                            t_infos.getText())
            ) == 0){
                if(sqlProxy.proxyExcute(String.format(
                        "update systemusers set owner = %s where ID = %d",
                        t_code.getText(),
                        userid
                )) != 0){
                    JOptionPane.showMessageDialog(this,"关联用户失败",getTitle(),JOptionPane.WARNING_MESSAGE);
                }
                JOptionPane.showMessageDialog(this,"添加成功",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }else{
                JOptionPane.showMessageDialog(this,"添加失败",getTitle(),JOptionPane.WARNING_MESSAGE);
            }/*
            Logger.getLogger().logInfomation(String.format(
                    "insert into StaffInfo(id,name,gender,department,details) values(%s,\'%s\',\'%s\',%s,\'%s\');",
                    t_searchIDName.getText(),
                    t_searchbox.getText(),
                    gender,
                    depIDList[btn_department.getSelectedIndex()],
                    t_infos.getText())
            );*/
        }
    }
//------------------Events-----------------

}
