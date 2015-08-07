package gui;

import database.MySQLConnector;
import database.SQLConnector;
import database.SQLProxy;
import tools.Logger;
import tools.StringTools;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by TEACH on 15-7-1.
 */
public class AddDepartmantForm extends JInternalFrame implements ActionListener {

    JTextField t_code,t_name;
    JTextArea t_infos;

    JButton btn_autofillcode,btn_submit;

    SQLProxy sqlProxy = SQLProxy.getProxy();

    public AddDepartmantForm(String title){
        super(title, false, true, false,true);//title,resizeable,closeabel,maxminizable
        setSize(300, 300);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setUpUIComponents();
        setUpEventListener();
        setResizable(false);
    }
    private void setUpUIComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = MainForm.insets;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("部门编号：", JLabel.RIGHT), gbc);

        gbc.gridx++;
        gbc.weightx = 1;
        t_code = new JTextField();
        add(t_code,gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        btn_autofillcode = new JButton("自动生成");
        add(btn_autofillcode,gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("部门名称：",JLabel.RIGHT),gbc);

        gbc.gridx++;
        gbc.gridwidth = 2;
        t_name = new JTextField();
        add(t_name,gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 1;
        t_infos = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(t_infos);
        scrollPane.setBorder(new TitledBorder("备注"));
        add(scrollPane, gbc);

        gbc.gridy++;
        gbc.weighty = 0;
        btn_submit = new JButton("添加");
        add(btn_submit,gbc);
    }

    private void setUpEventListener() {
        btn_submit.addActionListener(this);
        btn_autofillcode.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btn_submit){
            if(sqlProxy.proxyExcute(String.format(
                            "insert into DepartmentInfo values(%s,'%s','%s')",
                            t_code.getText(),
                            t_name.getText(),
                            t_infos.getText())
            ) == 0){
                /*
                SQLConnector sqlConnector = MySQLConnector.getObject();
                try {
                    ResultSet resultSet = sqlConnector.getStatement().executeQuery("select * from DepartmentInfo");
                    while (resultSet.next()){
                        t_infos.setText(t_infos.getText() + String.format("%d %s",resultSet.getInt("ID"),resultSet.getString("Name")));
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }//*/
                //JOptionPane.showMessageDialog(this,"添加部门成功",this.getTitle(),JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(this,"添加成功",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(this,"添加部门失败",this.getTitle(),JOptionPane.WARNING_MESSAGE);
            }
        }else if(e.getSource() == btn_autofillcode){
            t_code.setText(StringTools.RandomNumber());
        }//*/
    }
}
