package net.catten.hrsys.gui;

import net.catten.hrsys.data.AttendRecord;
import net.catten.hrsys.data.Staff;
import net.catten.hrsys.database.HibernateUtil;
import net.catten.hrsys.tools.StringTools;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by catten on 15/6/21.
 */
public class AddAttendForm extends JInternalFrame{

    String[] attend_states = new String[]{"a","o","i","e"};

    JLabel label_staffName;
    String staffName_placeholder = "按搜索按钮搜索员工";
    JTextField t_date;
    JComboBox<String> cb_state;
    JButton btn_search;
    JTextArea t_note;
    JButton btn_submit;

    int staffID = -1;

    Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    public AddAttendForm(String title){
        super(title, false, true, false, true);
        setSize(300, 400);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setUpUIComponents();
        setUpEventListeners();
    }
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
        cb_state = new JComboBox<String>(new String[]{"在岗","出差","病假","事假"});
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

    private void setUpEventListeners(){
        btn_submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertAttendRecord();
            }
        });

        btn_search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchAction();
            }
        });
    }

    private void insertAttendRecord(){
        if(staffID == -1){
            JOptionPane.showMessageDialog(this,"请先选择员工编号",getTitle(),JOptionPane.WARNING_MESSAGE);
        }else{
            session.getTransaction().begin();
            Staff staff = (Staff) session.get(Staff.class, staffID);
            if(staff == null){
                JOptionPane.showMessageDialog(this,"员工不存在");
                session.getTransaction().commit();
                return;
            }

            AttendRecord attendRecord = new AttendRecord();
            attendRecord.setStaff(staff);
            try {
                attendRecord.setTimePoint(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(t_date.getText()));
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this,"日期输入不正确");
                return;
            }
            attendRecord.setState(attend_states[cb_state.getSelectedIndex()]);
            attendRecord.setCommit(t_note.getText());

            if(session.save(attendRecord) != null){
                JOptionPane.showMessageDialog(this,"添加成功",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(this,"添加失败",getTitle(),JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void searchAction(){
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
                session.getTransaction().begin();
                Query query = session.createQuery("select s from Staff s where s.id="+searchinfo);
                ArrayList<Staff> arrayList = new ArrayList<Staff>();
                Iterator iterator = query.iterate();
                while (iterator.hasNext()){
                    arrayList.add((Staff) iterator.next());
                }
                session.getTransaction().commit();

                //如果结果集不为空
                if (arrayList.size() > 0) {
                    //保存部门ID
                    staffID = Integer.parseInt(searchinfo);
                    //更改label的显示
                    label_staffName.setText(String.format("%d - %s", arrayList.get(0).getId(), arrayList.get(0).getName()));
                    //把获得的内容填进文本框
                } else {
                    //结果集为空则显示对话框提示
                    JOptionPane.showMessageDialog(this, "无搜索结果", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                }
            } else { //如果输入不为数字
                //通过SQL代理对象获得数组形式的结果
                session.getTransaction().begin();
                Query query = session.createQuery("select s from Staff s where s.name like %%'"+searchinfo+"'%%");
                ArrayList<Staff> arrayList = new ArrayList<Staff>();
                Iterator iterator = query.iterate();
                while(iterator.hasNext()){
                    arrayList.add((Staff) iterator.next());
                }
                //如果结果集不为空
                if (arrayList.size() > 0) {
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
                                    String.format("%d - %s\n",
                                            arrayList.get(i).getId(),
                                            arrayList.get(i).getName()
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
                                Staff theInput = null;
                                for(Staff staff : arrayList){
                                    if(String.valueOf(staff.getId()).equals(tempip)){
                                        theInput = staff;
                                    }
                                }
                                if(theInput != null){
                                    label_staffName.setText(
                                            String.format("%d - %s",
                                                    theInput.getId(),
                                                    theInput.getName()
                                            )
                                    );
                                    staffID = theInput.getId();
                                }
                            }
                        }
                    } else { //如果只有一个项目
                        staffID = arrayList.get(0).getId();
                        label_staffName.setText(String.format("%d - %s", staffID, arrayList.get(0).getName()));
                    }
                } else { //如果结果集为空
                    JOptionPane.showMessageDialog(this, "无搜索结果", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}
