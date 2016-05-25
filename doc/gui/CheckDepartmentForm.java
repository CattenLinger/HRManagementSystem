package net.catten.hrsys.gui;

import net.catten.hrsys.database.SQLProxy;
import net.catten.hrsys.tools.StringTools;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CheckDepartmentForm extends JInternalFrame implements ActionListener{
	JTextField t_searchBox;
	JButton btn_find;
	JScrollPane scrollPane;
	JTable table;

	SQLProxy sqlProxy = SQLProxy.getProxy();

	Object[] columeNames = new String[]{"部门编号","部门名称","备注"};

	public CheckDepartmentForm(String title) {
		super(title,true,true,true,true);//title,resizeable,closeabel,maxminizable
		setSize(480, 320);
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setUpUIComponent();
		setUpEventListener();
	}

	private void setUpUIComponent(){
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = MainForm.insets;

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("部门名称或编号",JLabel.RIGHT),gbc);
		
		t_searchBox = new JTextField();
		gbc.gridx++;
		gbc.weightx = 1;
		add(t_searchBox,gbc);
		
		btn_find = new JButton("查找");
		gbc.gridx++;
		gbc.weightx = 0;
		add(btn_find,gbc);

		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.WEST;
		add(new JLabel("查询结果"),gbc);

		table = new JTable(new DefaultTableModel(null,columeNames));
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		gbc.gridy++;
		gbc.weighty = 1;
		add(scrollPane,gbc);
	}
	
	private void setUpEventListener() {
		btn_find.addActionListener(this);
	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_find){
			DefaultTableModel tableModel = (DefaultTableModel)table.getModel();;
			while(table.getRowCount() > 0){
				tableModel.removeRow(0);
			}
			String searchinfo = t_searchBox.getText();
			if(!searchinfo.equals("")){
				ArrayList arrayList;
				Object[] datas;
				if(StringTools.isNumeric(searchinfo)){
					arrayList = sqlProxy.proxyQuery(String.format("select id,name,ifnull(details,'') from departmentinfo where id = %s",searchinfo));
				}else{
					arrayList = sqlProxy.proxyQuery(String.format("select id,name,ifnull(details,'') from departmentinfo where name like '%%%s%%'",searchinfo));
				}
				if(arrayList != null && arrayList.size() > 0) {
					for (int i = 0; i < arrayList.size(); i++) {
						datas = (Object[])arrayList.get(i);
						tableModel.addRow(datas);
					}
				}else{
					JOptionPane.showMessageDialog(this,"无搜索结果",getTitle(),JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(this,"请输入搜索结果",getTitle(),JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
