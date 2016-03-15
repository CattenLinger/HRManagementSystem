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
 * Created by catten on 15/7/3.
 */
public class RemoveUserForm extends JInternalFrame implements ActionListener,MouseListener{

    JButton btn_search,btn_submit;
    JTextField t_searchbox;
    JTable table_result;
    JTextArea t_infomations;

    SQLProxy sqlProxy = SQLProxy.getProxy();

    String[] tabletitle = new String[]{"ID","用户名","关联员工","备注"};

    private String userid = "";

    public RemoveUserForm(String title){
        super(title, false, true, false, true);
        setSize(320, 400);
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
        add(new JLabel("输入ID或用户名以搜索"),gbc);

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
        table_result = new JTable();
        add(new JScrollPane(table_result),gbc);

        gbc.gridy++;
        gbc.weighty = 0;
        add(new JLabel("选中待删除的用户的信息"),gbc);

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
                    arrayList = sqlProxy.proxyQuery(
                            String.format("select %s from systemusers where id = %s inner join on %s",
                                    "systemusers.id,systemusers.username,ifnull(staffinfo.name,''),ifnull(systemusers.details)",
                                    searchInfo,
                                    "systemusers.owner = staffinfo.id")
                    );
                }else{
                    arrayList = sqlProxy.proxyQuery(String.format("select id,name,gender,ifnull(department,''),ifnull(details,'') from staffinfo where name like '%%%s%%'",searchInfo));
                }
                if(arrayList.size() != 0){
                    DefaultTableModel tableModel = (DefaultTableModel)table_result.getModel();
                    Object[] data;
                    String[] buffer;
                    if(arrayList.size() > 0){
                        for (int i = 0; i < arrayList.size(); i++) {
                            data = (Object[])arrayList.get(i);
                            buffer = new String[data.length];
                            for (int j = 0; j < data.length; j++) {
                                buffer[i] = data[i].toString();
                            }
                            tableModel.addRow(buffer);
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(this,"无搜索结果",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                }
            }else {
                JOptionPane.showMessageDialog(this,"请输入搜索条件",getTitle(),JOptionPane.WARNING_MESSAGE);
            }
        }else if(e.getSource() == btn_submit){
            if(!userid.equals("")){
                if(JOptionPane.showConfirmDialog(this, "确定要删除？", getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    if(sqlProxy.proxyExcute(String.format("delete from staffinfo where id = %s",userid)) == 0){
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

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
