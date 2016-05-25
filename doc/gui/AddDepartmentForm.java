package net.catten.hrsys.gui;

import net.catten.hrsys.data.Department;
import net.catten.hrsys.database.HibernateUtil;
import net.catten.hrsys.tools.StringTools;
import org.hibernate.classic.Session;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by TEACH on 15-7-1.
 */
public class AddDepartmentForm extends JInternalFrame{

    JTextField t_code,t_name;
    JTextArea t_infos;

    JButton btn_autofillcode,btn_submit;

    Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    public AddDepartmentForm(String title){
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

        btn_submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertDepartment();
            }
        });

        btn_autofillcode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                t_code.setText(StringTools.RandomNumber());
            }
        });
    }

    private void insertDepartment(){
        Department department = new Department();
        department.setCommit(t_infos.getText());
        department.setId(Integer.parseInt(t_code.getText()));
        department.setName(t_name.getText());
        if(session.save(department) != null){
            JOptionPane.showMessageDialog(this,"添加成功",getTitle(),JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(this,"添加部门失败",this.getTitle(),JOptionPane.WARNING_MESSAGE);
        }
    }
}
