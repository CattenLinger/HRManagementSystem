package net.catten.hrsys.gui;

import net.catten.hrsys.database.SQLProxy;
import net.catten.hrsys.tools.StringTools;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by catten on 15/7/2.
 */
public class RemoveStaffForm extends JInternalFrame implements ActionListener,MouseListener{

    JButton btn_search,btn_submit;
    JTextField t_searchbox;
    JTable table_result;
    JTextArea t_infomations;

    String[] tabletitle = new String[]{"ID","姓名","性别","部门","备注"};

    private String staffID;

    SQLProxy sqlProxy = SQLProxy.getProxy();

    public RemoveStaffForm(String title){
        super(title, false, true, false, true);
        setSize(400, 400);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setUpUIComponents();
        setUpEventListener();
    }

    private void setUpUIComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = MainForm.insets;

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(new JLabel("输入员工号或姓名以搜索"),gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        t_searchbox = new JTextField();
        add(t_searchbox,gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        btn_search = new JButton("搜索");
        add(btn_search,gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(new JLabel("搜索结果",JLabel.CENTER),gbc);

        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.weighty = 0.3;
        table_result = new JTable(new DefaultTableModel(null,tabletitle));
        add(new JScrollPane(table_result),gbc);

        gbc.gridy++;
        gbc.weighty = 0;
        add(new JLabel("选中待删除的员工的信息"),gbc);

        gbc.gridy++;
        gbc.weighty = 0.5;
        t_infomations = new JTextArea();
        t_infomations.setEditable(false);
        add(new JScrollPane(t_infomations),gbc);

        gbc.gridy++;
        gbc.weighty = 0;
        btn_submit = new JButton("删除");
        add(btn_submit,gbc);
    }

    private void setUpEventListener() {
        btn_search.addActionListener(this);
        btn_submit.addActionListener(this);
        table_result.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_search){
            String searchInfo = t_searchbox.getText();
            while(table_result.getRowCount() > 0){
                ((DefaultTableModel)table_result.getModel()).removeRow(0);
            }
            t_infomations.setText("");
            if(!searchInfo.equals("")){
                ArrayList arrayList;
                if(StringTools.isNumeric(searchInfo)){
                    arrayList = sqlProxy.proxyQuery(String.format("select id,name,gender,ifnull(department,''),ifnull(details,'') from staffinfo where id = '%s'",searchInfo));
                }else{
                    arrayList = sqlProxy.proxyQuery(String.format("select id,name,gender,ifnull(department,''),ifnull(details,'') from staffinfo where name like '%%%s%%'",searchInfo));
                }
                if(arrayList.size() != 0){
                    DefaultTableModel tableModel = (DefaultTableModel)table_result.getModel();
                    Object[] data;
                    if(arrayList.size() > 0){
                        for (int i = 0; i < arrayList.size(); i++) {
                            data = (Object[])arrayList.get(i);
                            tableModel.addRow(data);
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(this,"无搜索结果",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                }
            }else {
                JOptionPane.showMessageDialog(this,"请输入搜索条件",getTitle(),JOptionPane.WARNING_MESSAGE);
            }
        }else if(e.getSource() == btn_submit){
            if(!staffID.equals("")){
                if(JOptionPane.showConfirmDialog(this, "确定要删除？", getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    if(sqlProxy.proxyExcute(String.format("delete from staffinfo where id = %s",staffID)) == 0){
                        JOptionPane.showMessageDialog(this,"已删除",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(this,"删除失败",getTitle(),JOptionPane.WARNING_MESSAGE);
                    }
                }
            }else {
                JOptionPane.showMessageDialog(this,"请搜索并选择要删除的员工",getTitle(),JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (table_result.getSelectedRow() != -1){
            staffID = table_result.getValueAt(table_result.getSelectedRow(),0).toString();
            ArrayList arrayList = sqlProxy.proxyQuery(String.format("select id,name,gender,ifnull(department,''),ifnull(details,'') from staffinfo where id = %s", staffID));
            Object[] objects = (Object[])arrayList.get(0);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < objects.length; i++) {
                stringBuilder.append(String.format("%s : %s \n",tabletitle[i],objects[i].toString()));
            }
            t_infomations.setText(stringBuilder.toString());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
