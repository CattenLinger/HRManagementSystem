package gui;

import database.SQLProxy;
import tools.StringTools;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by catten on 15/7/3.
 */
public class EditStaffForm extends JInternalFrame implements ActionListener {

    JLabel label_staffid,label_useridinfo;
    String staffid_placeholder = "请按“搜索”按钮选择员工";
    String userid_placeholder = "不关联系统用户";
    String depid_placeholder = "选择部门";
    JTextField t_name;
    JTextArea t_infos;
    JComboBox cb_gender;
    JButton btn_submit, btn_searchstaff, btn_setlinkeduserid,btn_department;

    private String userid = "";
    private String depID = "";

    String[] gender = new String[]{"male","female"};

    //database.SQLServerConnector sqlServerConnector = SQLServerConnector.getObject();
    SQLProxy sqlProxy = SQLProxy.getProxy();
    private String staffid;

    public EditStaffForm(String title) {
        super(title, false, true, false, true);//title,resizeable,closeabel,maxminizable
        setSize(300, 400);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setUpUIComponents();
        setUpEventListener();
    }

    //*/
    private void setUpUIComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = MainForm.insets;
        setLayout(new GridBagLayout());
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.BOTH;

        label_staffid = new JLabel(staffid_placeholder,JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.5;
        add(label_staffid, gbc);

        btn_searchstaff = new JButton("搜索");
        gbc.gridx += 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        add(btn_searchstaff, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("姓名:", JLabel.RIGHT), gbc);

        t_name = new JTextField();
        gbc.gridx++;
        gbc.gridwidth = 2;
        gbc.weightx = 0.5;
        add(t_name, gbc);

        gbc.gridy++;
        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        add(new JLabel("性别:", JLabel.RIGHT), gbc);

        cb_gender = new JComboBox(new String[]{"男", "女"});
        gbc.gridx++;
        gbc.gridwidth = 2;
        add(cb_gender, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        add(new JLabel("部门:",JLabel.RIGHT),gbc);

        gbc.gridx++;
        gbc.gridwidth = 2;
        btn_department = new JButton(depid_placeholder);
        add(btn_department,gbc);

        t_infos = new JTextArea();
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weighty = 1;
        JScrollPane scrollPane = new JScrollPane(t_infos);
        scrollPane.setBorder(new TitledBorder("备注"));
        add(scrollPane, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weighty = 0;
        add(new JLabel("关联系统账号", JLabel.LEFT), gbc);

        label_useridinfo = new JLabel("不关联系统用户", JLabel.CENTER);
        gbc.gridwidth = 2;
        gbc.gridy++;
        add(label_useridinfo, gbc);

        btn_setlinkeduserid = new JButton("关联");
        gbc.gridwidth = 1;
        gbc.gridx += 2 ;
        add(btn_setlinkeduserid, gbc);

        btn_submit = new JButton("修改");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        add(btn_submit, gbc);
    }

    private void setUpEventListener() {
        btn_submit.addActionListener(this);
        btn_setlinkeduserid.addActionListener(this);
        btn_searchstaff.addActionListener(this);
        btn_department.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_searchstaff){
            //弹出对话框获得搜索条件
            ArrayList arrayList;
            String searchinfo = JOptionPane.showInputDialog(
                    this,
                    "请输入需要搜索的员工编号或名称",
                    getTitle(),
                    JOptionPane.INFORMATION_MESSAGE
            );
            //System.out.println(searchinfo);
            //判断是否有输入
            if (searchinfo != null) {
                //判断是否为数字
                if (StringTools.isNumeric(searchinfo)) {
                    //通过SQL代理对象获得数组形式的结果
                    arrayList = sqlProxy.proxyQuery(String.format("select id,name,gender,ifnull(department,''),ifnull(details,'') from staffinfo where ID = %s", searchinfo));
                    //如果结果集不为空
                } else { //如果输入不为数字
                    //通过SQL代理对象获得数组形式的结果
                    arrayList = sqlProxy.proxyQuery(String.format("select id,name,gender,ifnull(department,''),ifnull(details,'') from staffinfo where name like '%%%s%%'", searchinfo));
                    //如果结果集不为空
                }
                if (arrayList != null) {
                    //如果结果集不止一条结果
                    if (arrayList.size() > 1) {
                        //暂存结果集在StringBuilder
                        StringBuilder stringBuilder = new StringBuilder();
                        int resultsetcount = arrayList.size();
                        //控制显示的结果数目，超过10个则截断。
                        if (resultsetcount > 10){
                            resultsetcount = 10;
                            stringBuilder.append("结果数目太多，只显示前十个，请输入更精确的关键字\n\n");
                        }
                        for (int i = 0; i < resultsetcount; i++) {
                            stringBuilder.append(
                                    String.format("%s - %s\n",
                                            ((Object[]) arrayList.get(i))[0],
                                            ((Object[]) arrayList.get(i))[1]
                                    )
                            );
                        }
                        //显示所有选项并获取输入
                        String tempip = JOptionPane.showInputDialog(this,
                                String.format("有以下结果符合您的搜索\n请输入ID确定您所需的项目。\n\n%s\n",
                                        stringBuilder.toString()
                                )
                        );
                        //如果有输入
                        if (tempip != null) {
                            //只接受数字
                            if (StringTools.isNumeric(tempip)) {
                                arrayList = sqlProxy.proxyQuery(String.format("select id,name,gender,ifnull(department,''),ifnull(details,'') from staffinfo where id = %s", tempip));
                            }
                        }
                    }
                    if(arrayList.size() > 0){
                        staffid = ((Object[]) arrayList.get(0))[0].toString();
                        label_staffid.setText(String.format("%s - %s", staffid, ((Object[]) arrayList.get(0))[1].toString()));
                        t_name.setText(((Object[]) arrayList.get(0))[1].toString());
                    }
                    if(((Object[]) arrayList.get(0))[2].toString().equals(gender[0])){
                        cb_gender.setSelectedIndex(0);
                    }else {
                        cb_gender.setSelectedIndex(1);
                    }
                    if(arrayList.size() > 0){
                        depID = ((Object[]) arrayList.get(0))[3].toString();
                        btn_department.setText(
                                String.format(
                                        "%s - %s",
                                        depID,
                                        sqlProxy.proxyQuery(
                                                String.format(
                                                        "select name from departmentinfo where id = %s",
                                                        depID
                                                )
                                        ).get(0)[0].toString()
                                )
                        );
                        t_infos.setText(((Object[]) arrayList.get(0))[4].toString());
                    }
                } else { //如果结果集为空
                    JOptionPane.showMessageDialog(this, "无搜索结果", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }else if (e.getSource() == btn_setlinkeduserid) {
            ArrayList arrayList;
            //弹出对话框获得搜索条件
            String searchinfo = JOptionPane.showInputDialog(
                    this,
                    "请输入需要搜索的用户编号或名称,留空可解绑",
                    getTitle(),
                    JOptionPane.INFORMATION_MESSAGE
            );
            //System.out.println(searchinfo);
            //判断是否有输入
            if (searchinfo != null) {
                //判断是否为数字
                if (StringTools.isNumeric(searchinfo)) {
                    //通过SQL代理对象获得数组形式的结果
                    arrayList = sqlProxy.proxyQuery(String.format("select id,username from systemusers where ID = %s", searchinfo));
                    //如果结果集不为空
                    if (arrayList != null) {
                        //保存部门ID
                        userid = ((Object[]) arrayList.get(0))[0].toString();
                        //更改label的显示
                        label_useridinfo.setText(String.format("%s - %s", staffid, ((Object[]) arrayList.get(0))[1].toString()));
                        //把获得的内容填进文本框
                    } else {
                        //结果集为空则显示对话框提示
                        JOptionPane.showMessageDialog(this, "无搜索结果", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                    }
                } else { //如果输入不为数字
                    //通过SQL代理对象获得数组形式的结果
                    arrayList = sqlProxy.proxyQuery(String.format("select id,username from systemusers where username like '%%%s%%'", searchinfo));
                }
                //如果结果集不为空
                if (arrayList != null) {
                    if (arrayList.size() > 1) {
                        //暂存结果集在StringBuilder
                        StringBuilder stringBuilder = new StringBuilder();
                        int resultsetcount = arrayList.size();
                        //控制显示的结果数目，超过10个则截断。
                        if (resultsetcount > 10) {
                            resultsetcount = 10;
                            stringBuilder.append("结果数目太多，只显示前十个，请输入更精确的关键字\n\n");
                        }
                        for (int i = 0; i < resultsetcount; i++) {
                            stringBuilder.append(
                                    String.format("%s - %s\n",
                                            ((Object[]) arrayList.get(i))[0],
                                            ((Object[]) arrayList.get(i))[1]
                                    )
                            );
                        }
                        //显示所有选项并获取输入
                        String tempip = JOptionPane.showInputDialog(this,
                                String.format("有以下结果符合您的搜索\n请输入ID确定您所需的项目。\n\n%s\n",
                                        stringBuilder.toString()
                                )
                        );
                        //如果有输入
                        if (tempip != null) {
                            //只接受数字
                            if (StringTools.isNumeric(tempip)) {
                                arrayList = sqlProxy.proxyQuery(String.format("select id,username from systemusers where id = %s", tempip));
                                userid = ((Object[]) arrayList.get(0))[0].toString();
                                label_useridinfo.setText(
                                        String.format("%s - %s",
                                                staffid,
                                                ((Object[]) arrayList.get(0))[1].toString()
                                        )
                                );
                            }
                        }
                    } else {
                        staffid = ((Object[]) arrayList.get(0))[0].toString();
                        label_useridinfo.setText(String.format("%s - %s", staffid, ((Object[]) arrayList.get(0))[1].toString()));
                    }
                } else { //如果结果集为空
                    JOptionPane.showMessageDialog(this, "无搜索结果", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                userid = "";
                label_useridinfo.setText(userid_placeholder);
            }
        }else if(e.getSource() == btn_department){
//弹出对话框获得搜索条件
            String searchinfo = JOptionPane.showInputDialog(
                    this,
                    "请输入需要搜索的部门编号或名称\n留空为不选",
                    getTitle(),
                    JOptionPane.INFORMATION_MESSAGE
            );
            //System.out.println(searchinfo);
            //判断是否有输入
            if (searchinfo != null) {
                ArrayList arrayList;
                //判断是否为数字
                if (StringTools.isNumeric(searchinfo)) {
                    //通过SQL代理对象获得数组形式的结果
                    arrayList = sqlProxy.proxyQuery(String.format("select id,name from departmentinfo where ID = %s", searchinfo));
                } else { //如果输入不为数字
                    //通过SQL代理对象获得数组形式的结果
                    arrayList = sqlProxy.proxyQuery(String.format("select id,name from departmentinfo where name like '%%%s%%'", searchinfo));
                }
                //如果结果集不为空
                if (arrayList != null) {
                    //如果结果集不止一条结果
                    if (arrayList.size() > 1) {
                        //暂存结果集在StringBuilder
                        StringBuilder stringBuilder = new StringBuilder();
                        int resultsetcount = arrayList.size();
                        //控制显示的结果数目，超过10个则截断。
                        if (resultsetcount > 10){
                            resultsetcount = 10;
                            stringBuilder.append("结果数目太多，只显示前十个，请输入更精确的关键字\n\n");
                        }
                        for (int i = 0; i < resultsetcount; i++) {
                            stringBuilder.append(
                                    String.format("%s - %s\n",
                                            ((Object[]) arrayList.get(i))[0],
                                            ((Object[]) arrayList.get(i))[1]
                                    )
                            );
                        }
                        //显示所有选项并获取输入
                        String tempip = JOptionPane.showInputDialog(this,
                                String.format("有以下结果符合您的搜索\n请输入ID确定您所需的项目。\n\n%s\n",
                                        stringBuilder.toString()
                                )
                        );
                        //如果有输入
                        if (tempip != null) {
                            //只接受数字
                            if (StringTools.isNumeric(tempip)) {
                                depID = tempip;
                                arrayList = sqlProxy.proxyQuery(String.format("select id,name from departmentinfo where id = %s", tempip));
                                btn_department.setText(
                                        String.format("%s - %s",
                                                ((Object[]) arrayList.get(0))[0].toString(),
                                                ((Object[]) arrayList.get(0))[1].toString()
                                        )
                                );
                            }
                        }
                    } else { //如果只有一个项目
                        depID = ((Object[]) arrayList.get(0))[0].toString();
                        btn_department.setText(String.format("%s - %s", depID, ((Object[]) arrayList.get(0))[1].toString()));
                    }
                } else { //如果结果集为空
                    JOptionPane.showMessageDialog(this, "无搜索结果", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                depID = "";
                btn_department.setText(depid_placeholder);
            }
        }else if (e.getSource() == btn_submit){
            if(!staffid.equals("")){
                StringBuilder stringBuilder = new StringBuilder(
                        String.format("update staffinfo set name = '%s',gender = '%s',department=%s,details='%s' where id = %s",
                                t_name.getText(),
                                gender[cb_gender.getSelectedIndex()],
                                depID,
                                t_infos.getText(),
                                staffid
                        )
                );
                if(sqlProxy.proxyExcute(stringBuilder.toString()) == 0){
                    if(!userid.equals("")){
                        if(sqlProxy.proxyExcute(String.format("update systemusers set owner = %s where id = %s",staffid,userid)) == 0){
                            JOptionPane.showMessageDialog(this, "修改成功", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }else {
                            JOptionPane.showMessageDialog(this, "用户信息修改成功，但用户绑定失败", getTitle(), JOptionPane.WARNING_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "修改成功", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                }else {
                    JOptionPane.showMessageDialog(this,"修改失败",getTitle(),JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}