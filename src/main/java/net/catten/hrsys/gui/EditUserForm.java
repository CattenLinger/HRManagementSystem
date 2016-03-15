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
 * Created by catten on 15/6/25.
 */
public class EditUserForm extends JInternalFrame implements ActionListener{

    JLabel label_userid,label_linkedstaff;
    String linkedstaff_placeholder = "不关联员工";
    JTextField t_name;
    JPasswordField t_passwd;
    JTextArea t_infos;
    JButton btn_submit,btn_searchuser,btn_searchstaff;

    private String userid = "",staffid = "";

    SQLProxy sqlProxy = SQLProxy.getProxy();

    public EditUserForm(String title){
        super(title,false,true,false,true);
        setSize(320, 400);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setResizable(false);
        setUpUIComponent();
        setUpEventListener();
    }

    private void setUpUIComponent(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = MainForm.insets;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.5;
        label_userid = new JLabel("请按下“搜索”按钮选择用户",JLabel.CENTER);
        add(label_userid,gbc);

        btn_searchuser = new JButton("搜索");
        gbc.gridx += 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        add(btn_searchuser,gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("用户名：",JLabel.RIGHT),gbc);

        t_name = new JTextField();
        gbc.gridx++;
        gbc.gridwidth = 2;
        gbc.weightx = 0.5;
        add(t_name,gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        add(new JLabel("新密码：",JLabel.RIGHT),gbc);

        t_passwd = new JPasswordField();
        gbc.gridx++;
        gbc.gridwidth = 2;
        add(t_passwd,gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        add(new JLabel("关联员工",JLabel.LEFT),gbc);

        label_linkedstaff = new JLabel(linkedstaff_placeholder,JLabel.CENTER);
        gbc.gridwidth = 2;
        gbc.gridy++;
        add(label_linkedstaff,gbc);

        btn_searchstaff = new JButton("员工");
        gbc.gridwidth = 1;
        gbc.gridx += 2;
        add(btn_searchstaff,gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.weighty = 0.8;
        t_infos = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(t_infos);
        scrollPane.setBorder(new TitledBorder("备注"));
        add(scrollPane,gbc);

        btn_submit = new JButton("修改");
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.weighty = 0;
        add(btn_submit,gbc);
    }

    private void setUpEventListener(){
        btn_submit.addActionListener(this);
        btn_searchuser.addActionListener(this);
        btn_searchstaff.addActionListener(this);
    }
/*
    private void _clear_textboxs(){
        t_searchbox.setText("");
        t_passwd.setText("");
    }
*/
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_submit){
            if(!userid.equals("")){
                if(!t_name.equals("")){
                    String psw = new String(t_passwd.getPassword());
                    if(!psw.equals("")){
                        if(JOptionPane.showInputDialog(this,"修改密码前，请再一次确认密码",getTitle(),JOptionPane.QUESTION_MESSAGE).equals(psw)){
                            if(sqlProxy.proxyExcute(String.format("update systemusers set password = %s where id = %s",
                                    t_name.getText(),
                                    t_infos.getText(),
                                    psw,
                                    userid
                            )) != 0){
                                JOptionPane.showMessageDialog(this,"修改密码失败",getTitle(),JOptionPane.WARNING_MESSAGE);
                            }else {
                                JOptionPane.showMessageDialog(this,"修改密码成功",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                    if(sqlProxy.proxyExcute(String.format("update systemusers set username = '%s',owner = %s,details = '%s' where id = %s",
                            t_name.getText(),
                            staffid,
                            t_infos.getText(),
                            userid
                    )) != 0){
                        JOptionPane.showMessageDialog(this,"更新信息失败",getTitle(),JOptionPane.WARNING_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(this,"更新信息成功",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(this,"用户名不能为空",getTitle(),JOptionPane.WARNING_MESSAGE);
                }
            }else {
                JOptionPane.showMessageDialog(this,"请先选择需要修改的用户",getTitle(),JOptionPane.WARNING_MESSAGE);
            }
            //_clear_textboxs();
        }else if(e.getSource() == btn_searchstaff){//搜索关联员工按钮
            //弹出对话框获得搜索条件
            String searchinfo = JOptionPane.showInputDialog(
                    this,
                    "请输入需要搜索的员工编号或名称，输入-1可解绑",
                    getTitle(),
                    JOptionPane.INFORMATION_MESSAGE
            );
            //System.out.println(searchinfo);
            if(searchinfo.equals("-1")){
                staffid = "";
                label_linkedstaff.setText(linkedstaff_placeholder);
            }else if (searchinfo != null) {//判断是否有输入
                //判断是否为数字
                if (StringTools.isNumeric(searchinfo)) {
                    //通过SQL代理对象获得数组形式的结果
                    ArrayList arrayList = sqlProxy.proxyQuery(String.format("select id,name,ifnull(details,'') from staffinfo where ID = %s", searchinfo));
                    //如果结果集不为空
                    if (arrayList != null) {
                        //更改label的显示
                        label_linkedstaff.setText(String.format("%s - %s",
                                        ((Object[]) arrayList.get(0))[0].toString(),
                                        ((Object[]) arrayList.get(0))[1].toString())
                        );
                        //保存部门ID
                        staffid = ((Object[]) arrayList.get(0))[0].toString();
                        //把获得的内容填进文本框
                    } else {
                        //结果集为空则显示对话框提示
                        JOptionPane.showMessageDialog(this, "无搜索结果", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                    }
                } else { //如果输入不为数字
                    //通过SQL代理对象获得数组形式的结果
                    ArrayList arrayList = sqlProxy.proxyQuery(String.format("select id,name,ifnull(details,'') from staffinfo where name like '%%%s%%'", searchinfo));
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
                                                ((Object[]) arrayList.get(i))[0].toString(),
                                                ((Object[]) arrayList.get(i))[1].toString()
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
                                    arrayList = sqlProxy.proxyQuery(String.format("select id,name from staffinfo where id = %s", tempip));
                                    if(arrayList != null){
                                        label_linkedstaff.setText(
                                                String.format("%s - %s",
                                                        ((Object[]) arrayList.get(0))[0].toString(),
                                                        ((Object[]) arrayList.get(0))[1].toString()
                                                )
                                        );
                                        staffid = ((Object[]) arrayList.get(0))[0].toString();
                                    }
                                }
                            }
                        } else { //如果只有一个项目
                            staffid = ((Object[]) arrayList.get(0))[0].toString();
                            label_linkedstaff.setText(String.format("%s - %s", staffid, ((Object[]) arrayList.get(0))[1].toString()));
                        }
                    } else { //如果结果集为空
                        JOptionPane.showMessageDialog(this, "无搜索结果", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }else if(e.getSource() == btn_searchuser){//搜索用户按钮
            //弹出对话框获得搜索条件
            String searchinfo = JOptionPane.showInputDialog(
                    this,
                    "请输入需要搜索的用户编号或名称",
                    getTitle(),
                    JOptionPane.INFORMATION_MESSAGE
            );
            //System.out.println(searchinfo);
            //判断是否有输入
            if (searchinfo != null) {
                //判断是否为数字
                if (StringTools.isNumeric(searchinfo)) {
                    //通过SQL代理对象获得数组形式的结果
                    ArrayList arrayList = sqlProxy.proxyQuery(String.format("select id,username,ifnull(details,'') from systemusers where ID = %s", searchinfo));
                    //如果结果集不为空
                    if (arrayList != null) {
                        //保存部门ID
                        staffid = ((Object[]) arrayList.get(0))[0].toString();
                        //更改label的显示
                        label_userid.setText(String.format("%s - %s", staffid, ((Object[])arrayList.get(0))[1].toString()));
                        t_name.setText(((Object[])arrayList.get(0))[1].toString());
                        t_infos.setText(((Object[])arrayList.get(0))[2].toString());
                        //把获得的内容填进文本框
                    } else {
                        //结果集为空则显示对话框提示
                        JOptionPane.showMessageDialog(this, "无搜索结果", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                    }
                } else { //如果输入不为数字
                    //通过SQL代理对象获得数组形式的结果
                    ArrayList arrayList = sqlProxy.proxyQuery(String.format("select id,username from systemusers where username like '%%%s%%'", searchinfo));
                    //如果结果集不为空
                    if (arrayList != null) {
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
                                    arrayList = sqlProxy.proxyQuery(String.format("select id,username,ifnull(details,'') from systemusers where id = %s", tempip));
                                    staffid = ((Object[]) arrayList.get(0))[0].toString();
                                    label_userid.setText(
                                            String.format("%s - %s",
                                                    staffid,
                                                    ((Object[]) arrayList.get(0))[1].toString()
                                            )
                                    );
                                    t_name.setText(((Object[])arrayList.get(0))[1].toString());
                                    t_infos.setText(((Object[])arrayList.get(0))[2].toString());
                                }
                            }
                        } else {
                            staffid = ((Object[]) arrayList.get(0))[0].toString();
                            label_userid.setText(String.format("%s - %s", staffid, ((Object[]) arrayList.get(0))[1].toString()));
                            t_name.setText(((Object[]) arrayList.get(0))[1].toString());
                            t_infos.setText(((Object[]) arrayList.get(0))[2].toString());
                        }
                    } else { //如果结果集为空
                        JOptionPane.showMessageDialog(this, "无搜索结果", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }
}
