package gui;

import database.SQLProxy;
import sun.applet.Main;
import tools.StringTools;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by catten on 15/6/21.
 */
public class AddAttendForm extends JInternalFrame implements ActionListener{
    //private static AddAttendForm asf;

    String[] attend_states = new String[]{"a","o","i","e"};

    SQLProxy sqlProxy = SQLProxy.getProxy();

    JLabel label_staffName;
    String staffName_placeholder = "按搜索按钮搜索员工";
    JTextField t_date;
    JComboBox cb_state;
    JButton btn_search;
    JTextArea t_note;
    JButton btn_submit;

    int staffid = -1;
    //private boolean edit_mode = false;

    public AddAttendForm(String title){
        super(title, false, true, false, true);
        setSize(300, 400);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setUpUIComponents();
        setUpEventListeners();
    }
/*
    public AddAttendForm(String title,String[] withData){
        this(title);
        withData(withData);
        edit_mode = true;
    }
//*/
    private void setUpUIComponents(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = MainForm.insets;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        label_staffName = new JLabel(staffName_placeholder,JLabel.CENTER);
        add(label_staffName,gbc);

        gbc.gridx += 2;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        btn_search = new JButton("搜索");
        add(btn_search,gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("考勤日期：",JLabel.RIGHT),gbc);

        gbc.gridx++;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        t_date = new JTextField();
        add(t_date, gbc);
        t_date.setText(DateFormat.getDateTimeInstance().format(new Date()));

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.weightx = 0;
        add(new JLabel("考勤状态：",JLabel.RIGHT),gbc);

        gbc.gridx++;
        gbc.gridwidth = 2;
        cb_state = new JComboBox(new String[]{"在岗","出差","病假","事假"});
        add(cb_state,gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.weighty = 1;
        t_note = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(t_note);
        scrollPane.setBorder(new TitledBorder("考勤备注"));
        add(scrollPane, gbc);

        gbc.gridy++;
        gbc.weighty = 0;
        gbc.weighty = 0;
        btn_submit = new JButton("确定");
        add(btn_submit,gbc);

    }
/*
    private void _clear_textboxs(){
        t_searchIDName.setText("");
        t_attend.setText("");
        t_event.setText("");
        t_out.setText("");
        t_ill.setText("");
        t_note.setText("");
        //edit_mode = false;
    }
//*/
    /*
    * 0:code
    * 1:attend
    * 2:out
    * 3:event
    * 4:ill
    * 5:note
    *
    public void withData(String[] datas){

        edit_mode = true;
        //this.setVisible(true);
    }
//*/
    private void setUpEventListeners(){
        btn_submit.addActionListener(this);
        btn_search.addActionListener(this);
    }

    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_submit){
            if(staffid == -1){
                JOptionPane.showMessageDialog(this,"请先选择员工编号",getTitle(),JOptionPane.WARNING_MESSAGE);
            }else{
                if(sqlProxy.proxyExcute(String.format("insert into AttendTable(timepoint,staffid,state,details) values('%s',%d,'%s','%s')",
                                t_date.getText(),
                                staffid,
                                attend_states[cb_state.getSelectedIndex()],
                                t_note.getText())
                ) != 0){
                    JOptionPane.showMessageDialog(this,"添加失败",getTitle(),JOptionPane.WARNING_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(this,"添加成功",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }
            }
        }else if (e.getSource() == btn_search){
            //弹出对话框获得搜索条件
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
                    ArrayList arrayList = sqlProxy.proxyQuery(String.format("select id,name from staffinfo where ID = %s", searchinfo));
                    //如果结果集不为空
                    if (arrayList != null) {
                        //保存部门ID
                        staffid = Integer.parseInt(searchinfo);
                        //更改label的显示
                        label_staffName.setText(String.format("%d - %s", ((Object[]) arrayList.get(0))[0], ((Object[]) arrayList.get(0))[1]));
                        //把获得的内容填进文本框
                    } else {
                        //结果集为空则显示对话框提示
                        JOptionPane.showMessageDialog(this, "无搜索结果", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                    }
                } else { //如果输入不为数字
                    //通过SQL代理对象获得数组形式的结果
                    ArrayList arrayList = sqlProxy.proxyQuery(String.format("select id,name from staffinfo where name like '%%%s%%'", searchinfo));
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
                                    arrayList = sqlProxy.proxyQuery(String.format("select id,name from staffinfo where id = %s", tempip));
                                    if(arrayList != null){
                                        label_staffName.setText(
                                                String.format("%s - %s",
                                                        ((Object[]) arrayList.get(0))[0].toString(),
                                                        ((Object[]) arrayList.get(0))[1].toString()
                                                )
                                        );
                                        staffid = Integer.parseInt(((Object[]) arrayList.get(0))[0].toString());
                                    }
                                }
                            }
                        } else { //如果只有一个项目
                            staffid = Integer.parseInt(((Object[]) arrayList.get(0))[0].toString());
                            label_staffName.setText(String.format("%d - %s", staffid, ((Object[]) arrayList.get(0))[1].toString()));
                        }
                    } else { //如果结果集为空
                        JOptionPane.showMessageDialog(this, "无搜索结果", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
        //*/
    }
}
