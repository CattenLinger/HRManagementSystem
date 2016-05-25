package net.catten.hrsys.gui;

import net.catten.hrsys.tools.StringTools;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by catten on 15/7/2.
 */
public class CheckDetailAttendForm extends JInternalFrame implements ActionListener{
    //private static CheckDetailAttendForm daf;
    JTextField t_date,t_code;
    JComboBox cb_state;
    JButton btn_submit;
    JTable table_result;

    private String staffid = "";

    //SQLProxy sqlProxy = SQLProxy.getProxy();

    String[] columnName = new String[]{
            "流水号","姓名","考勤状况","考勤时间","备注"
    };

    String[] statelist = new String[]{"a","o","i","e"};
    String[] statelistmask = new String[]{"在岗","出差","病假","事假"};

    public CheckDetailAttendForm(String title){
        super(title,true,true,false,true);
        setSize(640, 480);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setUpUIComponents();
        setUpEventListener();
    }

    private void setUpUIComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = MainForm.insets;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(new JLabel("员工编号或姓名：",JLabel.CENTER),gbc);

        t_code = new JTextField();
        gbc.gridx++;
        gbc.weightx = 0.5;
        gbc.gridwidth = 4;
        add(t_code,gbc);

        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("日期：",JLabel.RIGHT),gbc);

        t_date = new JTextField();
        gbc.gridx++;
        gbc.weightx = 0.5;
        add(t_date,gbc);

        gbc.gridx+=2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        add(new JLabel("考勤状态：",JLabel.RIGHT),gbc);

        cb_state = new JComboBox(new String[]{"任意","在岗","出差","事假","病假"});
        gbc.gridx++;
        add(cb_state,gbc);

        btn_submit = new JButton("查询");
        gbc.gridx++;
        add(btn_submit,gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 6;
        gbc.gridy++;
        add(new JLabel("查询结果"),gbc);

        table_result = new JTable(new DefaultTableModel(null, columnName));
        gbc.gridy++;
        gbc.weighty = 0.5;
        add(new JScrollPane(table_result),gbc);
    }

    private void setUpEventListener() {
        btn_submit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_submit){
            DefaultTableModel tableModel = (DefaultTableModel)table_result.getModel();
            while(table_result.getRowCount() > 0){
                tableModel.removeRow(0);
            }
            String searchIDName = t_code.getText();
            String searchTime = t_date.getText();
            if(searchIDName.equals("")){ searchIDName = "%"; }
            ArrayList arrayList;
            Object[] datas;
            StringBuilder stringBuilder = new StringBuilder(
                    String.format("select %s from attendtable inner join staffinfo on staffinfo.id = attendtable.staffIF where",
                            "attendtable.no,staffinfo.name as name,attendtable.state,attendtable.timepoint,ifnull(attendtable.details,'')"
                    )
            );
            if(StringTools.isNumeric(searchIDName)){
                stringBuilder.append(String.format(" staffIF = %s",searchIDName));
            }else{
                stringBuilder.append(String.format(" name like '%%%s%%'",searchIDName));
            }
            if(!staffid.equals("")){
                stringBuilder.append(String.format(" and staffIF = '%s'",staffid));
            }
            if(!searchTime.equals("")){
                stringBuilder.append(String.format(" and timepoint = '%s'",searchTime));
            }
            if(cb_state.getSelectedIndex() > 0){
                stringBuilder.append(String.format(" and state = '%s'",statelist[cb_state.getSelectedIndex() - 1]));
            }
            arrayList = sqlProxy.proxyQuery(stringBuilder.toString());
            if(arrayList != null && arrayList.size() > 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    datas = (Object[])arrayList.get(i);
                    if(datas[2].toString().equals(statelist[0])){
                        datas[2] = statelistmask[0];
                    }else if(datas[2].toString().equals(statelist[1])){
                        datas[2] = statelistmask[1];
                    }else if(datas[2].toString().equals(statelist[2])){
                        datas[2] = statelistmask[2];
                    }else if(datas[2].toString().equals(statelist[3])){
                        datas[2] = statelistmask[3];
                    }
                    tableModel.addRow(datas);
                }
            }else{
                JOptionPane.showMessageDialog(this,"无搜索结果",getTitle(),JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
