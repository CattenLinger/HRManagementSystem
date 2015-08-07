package gui;

import database.SQLProxy;
import tools.StringTools;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by catten on 15/6/21.
 */
public class CheckAttendForm extends JInternalFrame implements ActionListener{
    //private static CheckAttendForm caf;

    JTextField t_searchIDName;
    JComboBox cb_timeunit;
    JButton btn_submit,btn_department;
    String dep_placeholder = "按我可选择部门";
    String depID = "";
    JTable table;

    SQLProxy sqlProxy = SQLProxy.getProxy();

    String[] columeName = new String[]{
        "编号","姓名","在岗","出差","事假","病假"
    };

    //JInternalFrame form_addattend = AddAttendForm.getForm();
/*
    public static CheckAttendForm getForm(){
        if(caf == null){
            caf = new CheckAttendForm("考勤汇总查询");
        }
        return caf;
    }
//*/
    public CheckAttendForm(String title){
        super(title,true,true,true,true);
        setSize(640, 480);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setUpUIComponents();
        setUpEventListener();
    }

    private void setUpUIComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = MainForm.insets;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(new JLabel("员工编号和姓名：",JLabel.RIGHT),gbc);

        t_searchIDName = new JTextField();
        gbc.gridx+=2;
        gbc.weightx = 0.5;
        gbc.gridwidth = 2;
        add(t_searchIDName,gbc);

        gbc.gridy++;
        gbc.gridx=0;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        add(new JLabel("时间跨度：",JLabel.RIGHT),gbc);


        cb_timeunit = new JComboBox(new String[]{"不细分","日","月","季","年"});
        gbc.gridx++;
        add(cb_timeunit,gbc);

        gbc.gridx++;
        add(new JLabel("部门：",JLabel.RIGHT),gbc);

        btn_department = new JButton(dep_placeholder);
        gbc.gridx++;
        add(btn_department,gbc);

        btn_submit = new JButton("查询");
        gbc.gridx++;
        add(btn_submit,gbc);

        table = new JTable(new DefaultTableModel(null,columeName));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new TitledBorder("查询结果"));
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 5;
        gbc.weighty = 1;
        add(scrollPane,gbc);
    }

    private void setUpEventListener() {
        btn_submit.addActionListener(this);
        btn_department.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_department){
            //弹出对话框获得搜索条件
            String searchinfo = JOptionPane.showInputDialog(
                    this,
                    "请输入需要搜索的部门编号或名称\n不填可清除选择",
                    getTitle(),
                    JOptionPane.INFORMATION_MESSAGE
            );
            //System.out.println(searchinfo);
            //判断是否有输入
            if (searchinfo != null) {
                //判断是否为数字
                if (tools.StringTools.isNumeric(searchinfo)) {
                    //通过SQL代理对象获得数组形式的结果
                    ArrayList arrayList = sqlProxy.proxyQuery(String.format("select id,name,ifnull(details,'') from departmentinfo where ID = %s", searchinfo));
                    //如果结果集不为空
                    if (arrayList != null) {
                        //保存部门ID
                        depID = searchinfo;
                        //更改label的显示
                        btn_department.setText(String.format("%s - %s", ((Object[]) arrayList.get(0))[0], ((Object[]) arrayList.get(0))[1]));
                    } else {
                        depID = "";
                        btn_department.setText(dep_placeholder);
                    }
                } else { //如果输入不为数字
                    //通过SQL代理对象获得数组形式的结果
                    ArrayList arrayList = sqlProxy.proxyQuery(String.format("select id,name,ifnull(details,'') from departmentinfo where name like '%%%s%%'", searchinfo));
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
                                    arrayList = sqlProxy.proxyQuery(String.format("select id,name,ifnull(details,'') from departmentinfo where id = %s", tempip));
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
                        depID = "";
                        btn_department.setText(dep_placeholder);
                    }
                }
            }
        }else if(e.getSource() == btn_submit){
            String searchIDName = t_searchIDName.getText();
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            while (table.getRowCount() > 0){
                tableModel.removeRow(0);
            }
            if (searchIDName.equals("")){
                searchIDName = "%";
            }
            StringBuilder stringBuilder = new StringBuilder(
                    String.format("select %s from OrginalAttend inner join staffinfo on staffinfo.id = staffid",
                            "OrginalAttend.staffid,staffinfo.name,staffinfo.department,attend,outside,event,illed"
                    )
            );
            if(StringTools.isNumeric(searchIDName)){
                stringBuilder.append(String.format(" where staffid = %s",searchIDName));
            }else{
                stringBuilder.append(String.format(" where name like '%%%s%%'",searchIDName));
            }
            if(!depID.equals("")){
                stringBuilder.append(String.format(" and department = %s",depID));
            }
            ArrayList arrayList = sqlProxy.proxyQuery(stringBuilder.toString());
            if (arrayList != null && arrayList.size() > 0){
                Object[] datas;
                for (int i = 0; i < arrayList.size(); i++) {
                    datas = (Object[])arrayList.get(i);
                    tableModel.addRow(datas);
                }
            }else{
                JOptionPane.showMessageDialog(this,"无搜索结果",getTitle(),JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
