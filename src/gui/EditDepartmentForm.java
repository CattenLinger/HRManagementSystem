package gui;

import database.SQLProxy;
import tools.StringTools;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.OpenType;
import java.util.ArrayList;

/**
 * Created by catten on 15/7/2.
 */
public class EditDepartmentForm extends JInternalFrame implements ActionListener {

    private String departmentID = "";

    SQLProxy sqlProxy = SQLProxy.getProxy();

    JLabel label_id;
    String id_placeholder = "请按下“搜索”按钮选择部门";
    JTextField t_depname;
    JTextArea t_infos;
    JButton btn_search,btn_submit;

    public EditDepartmentForm(String title){
        super(title,false,true,false,true);
        setSize(320, 400);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setUpUIComponents();
        setUpEventListener();
    }

    protected void setUpUIComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = MainForm.insets;

        label_id = new JLabel(id_placeholder,JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        add(label_id,gbc);

        btn_search = new JButton("搜索");
        gbc.gridx+=2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        add(btn_search,gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("名称"),gbc);

        t_depname = new JTextField();
        gbc.gridx++;
        gbc.gridwidth = 2;
        add(t_depname,gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 0.5;
        t_infos = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(t_infos);
        scrollPane.setBorder(new TitledBorder("备注"));
        add(scrollPane, gbc);

        btn_submit = new JButton("更改");
        gbc.gridy++;
        gbc.weighty = 0;
        add(btn_submit,gbc);


    }

    protected void setUpEventListener() {
        btn_search.addActionListener(this);
        btn_submit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_search){
            //弹出对话框获得搜索条件
            String searchinfo = JOptionPane.showInputDialog(
                    this,
                    "请输入需要搜索的部门编号或名称",
                    getTitle(),
                    JOptionPane.INFORMATION_MESSAGE
            );
            //System.out.println(searchinfo);
            //判断是否有输入
            if (searchinfo != null) {
                ArrayList arrayList;
                //判断是否为数字
                if (tools.StringTools.isNumeric(searchinfo)) {
                    //通过SQL代理对象获得数组形式的结果
                    arrayList = sqlProxy.proxyQuery(String.format("select id,name,ifnull(details,'') from departmentinfo where ID = %s", searchinfo));
                } else { //如果输入不为数字
                    //通过SQL代理对象获得数组形式的结果
                    arrayList = sqlProxy.proxyQuery(String.format("select id,name,ifnull(details,'') from departmentinfo where name like '%%%s%%'", searchinfo));
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
                                departmentID = tempip;
                                arrayList = sqlProxy.proxyQuery(String.format("select id,name,ifnull(details,'') from departmentinfo where id = %s", tempip));
                                label_id.setText(
                                        String.format("%s - %s",
                                                ((Object[]) arrayList.get(0))[0].toString(),
                                                ((Object[]) arrayList.get(0))[1].toString()
                                        )
                                );
                                t_depname.setText(((Object[])arrayList.get(0))[1].toString());
                                t_infos.setText(((Object[])arrayList.get(0))[2].toString());
                            }
                        }
                    } else { //如果只有一个项目
                        departmentID = ((Object[]) arrayList.get(0))[0].toString();
                        label_id.setText(String.format("%s - %s", departmentID, ((Object[]) arrayList.get(0))[1].toString()));
                        t_depname.setText(((Object[])arrayList.get(0))[1].toString());
                        t_infos.setText(((Object[])arrayList.get(0))[2].toString());
                    }
                } else { //如果结果集为空
                    JOptionPane.showMessageDialog(this, "无搜索结果", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }else if(e.getSource() == btn_submit){
            if(departmentID.equals("")){
                JOptionPane.showMessageDialog(this,"请先选择部门",getTitle(),JOptionPane.WARNING_MESSAGE);
            }else{
                if(sqlProxy.proxyExcute(String.format("update DepartmentInfo set name = '%s',details='%s' where ID = %s",
                                t_depname.getText(),
                                t_infos.getText(),
                                departmentID)
                ) == 0){
                    JOptionPane.showMessageDialog(this,"修改成功",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(this,"修改失败",getTitle(),JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
