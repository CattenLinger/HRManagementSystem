package net.catten.hrsys.gui;

//import apple.laf.JRSUIUtils;

import net.catten.hrsys.database.SQLProxy;
import net.catten.hrsys.tools.StringTools;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CheckStaffForm extends JInternalFrame implements ActionListener{
	//private static CheckStaffForm sf;

	SQLProxy sqlProxy = SQLProxy.getProxy();

	JTextField t_searchbox,t_code;
	JComboBox cb_gender;
	JButton btn_search;
	JScrollPane scrollPane;
	JTable table;

	String[] cloumeName = new String[]{
		"编号","姓名","性别","部门","备注"
	};

	String[] genderlist = new String[]{"male","female"};

	public CheckStaffForm(String title){
		super(title, true, true, true, true);//title,resizeable,closeabel,maxminizable
		setSize(640, 480);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setUpUIComponent();
		setUpEventListener();
	}
/*
	public static CheckStaffForm getForm(){
		if(sf == null){
			sf = new CheckStaffForm("员工查询");
		}
		return sf;
	}
//*/
	public void setUpUIComponent(){
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2,2,2,2);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		add(new JLabel("编号或姓名:",JLabel.RIGHT),gbc);

		t_searchbox = new JTextField();
		gbc.gridx++;
		gbc.weightx = 0.5;
		add(t_searchbox,gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		add(new JLabel("性别：",JLabel.RIGHT),gbc);

		cb_gender = new JComboBox(new String[]{"任意","男","女"});
		gbc.weightx = 0.1;
		gbc.gridx++;
		add(cb_gender,gbc);

		btn_search = new JButton("搜索");
		gbc.weightx = 0;
		gbc.gridx++;
		add(btn_search,gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 5;
		add(new JLabel("查询结果：",JLabel.LEFT),gbc);

		gbc.gridy = 2;
		gbc.weighty = 1;
		table = new JTable(new DefaultTableModel(null,cloumeName));
		scrollPane = new JScrollPane(table);
		add(scrollPane,gbc);
	}

	private void setUpEventListener(){
		btn_search.addActionListener(this);
	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_search){
			DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
			while(table.getRowCount() > 0){
				tableModel.removeRow(0);
			}
			String searchinfo = t_searchbox.getText();
			if(!searchinfo.equals("")){
				ArrayList arrayList;
				Object[] datas;
				StringBuilder stringBuilder = new StringBuilder("select id,name,gender,ifnull(department,''),ifnull(details,'') from staffinfo where");
				if(StringTools.isNumeric(searchinfo)){
					stringBuilder.append(String.format(" id = %s",searchinfo));
				}else{
					stringBuilder.append(String.format(" name like '%%%s%%'",searchinfo));
				}
				if(cb_gender.getSelectedIndex() > 0){
					stringBuilder.append(String.format(" and gender = '%s'",genderlist[cb_gender.getSelectedIndex() - 1]));
				}
				arrayList = sqlProxy.proxyQuery(stringBuilder.toString());
				if(arrayList != null && arrayList.size() > 0) {
					for (int i = 0; i < arrayList.size(); i++) {
						datas = (Object[])arrayList.get(i);
						tableModel.addRow(datas);
					}
				}else{
					JOptionPane.showMessageDialog(this,"无搜索结果",getTitle(),JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(this,"请输入搜索条件",getTitle(),JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
