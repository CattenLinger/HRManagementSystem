package net.catten.hrsys.gui;

import net.catten.hrsys.database.SQLProxy;
import net.catten.hrsys.tools.StringTools;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by catten on 15/6/25.
 */
public class AddUserForm extends JInternalFrame implements ActionListener{
    //private static AddUserForm auf;

    JTextField t_name,t_code;
    JPasswordField t_passwd;
    JTextArea t_infos;
    JButton btn_submit,btn_randomID;

    SQLProxy sqlProxy = SQLProxy.getProxy();
/*
    public static AddUserForm getForm(){
        if(auf == null){
            auf = new AddUserForm("添加用户");
        }
        return auf;
    }
//*/
    public AddUserForm(String title){
        super(title,false,true,false,true);
        setSize(320, 400);
        setResizable(false);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setUpUIComponents();
        setUpEventListener();
    }

    private void setUpUIComponents(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = MainForm.insets;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("ID：", JLabel.RIGHT), gbc);

        t_code = new JTextField();
        gbc.gridx++;
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;
        add(t_code,gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        btn_randomID = new JButton("随机");
        add(btn_randomID,gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("用户名：",JLabel.RIGHT),gbc);

        gbc.gridx++;
        gbc.gridwidth = 2;
        t_name = new JTextField();
        add(t_name,gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        add(new JLabel("密码：", JLabel.RIGHT), gbc);

        gbc.gridx++;
        gbc.gridwidth = 2;
        t_passwd = new JPasswordField();
        add(t_passwd,gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 0.7;
        t_infos = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(t_infos);
        scrollPane.setBorder(new TitledBorder("备注"));
        add(scrollPane, gbc);

        btn_submit = new JButton("添加");
        gbc.gridy++;
        gbc.weighty = 0;
        add(btn_submit, gbc);
    }

    private void setUpEventListener(){
        btn_submit.addActionListener(this);
        btn_randomID.addActionListener(this);
    }
/*
    private void _clean_textboxs(){
        t_searchbox.setText("");
        t_passwd.setText("");
    }
//*/
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_submit){
            System.out.printf("Submit user:%s password:%s\n", t_name.getText(), new String(t_passwd.getPassword()));
            if(sqlProxy.proxyExcute(String.format("insert into systemusers(id,username,password,details) values(%s,'%s','%s','%s')",
                    t_code.getText(),
                    t_name.getText(),
                    new String(t_passwd.getPassword()),
                    t_infos.getText())
            ) != 0){
                JOptionPane.showMessageDialog(this,"添加失败",getTitle(), JOptionPane.WARNING_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this,"添加成功",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
        }else if(e.getSource() == btn_randomID){
            t_code.setText(StringTools.RandomNumber());
        }
    }
}
