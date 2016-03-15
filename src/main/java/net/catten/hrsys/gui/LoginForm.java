package net.catten.hrsys.gui;

import net.catten.hrsys.database.SQLProxy;
//import database.SQLServerConnector;
import net.catten.hrsys.tools.Logger;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LoginForm extends JFrame implements ActionListener{
	//定义文本框
	JTextField t_username;
	//定义密码框
	JPasswordField t_password;
	//定义按钮
	JButton btn_login,btn_close;
	//放按钮的容器
	JPanel panel_submit;
	
	MainForm mainForm = MainForm.getForm();

    //database.SQLServerConnector sqlServerConnector = database.SQLServerConnector.getObject();
    //SQLServerConnector sqlServerConnector = SQLServerConnector.getObject();
	SQLProxy sqlProxy = SQLProxy.getProxy();

	public LoginForm(String title){

		super(title);
		setSize(320,240);
		setLocationRelativeTo(null);
		setUpUIComponents();
		setUpEventListener();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setUpUIComponents(){
		setLayout(new GridBagLayout());
		GridBagConstraints gbci = new GridBagConstraints();
		gbci.insets = MainForm.insets;
		gbci.fill = GridBagConstraints.BOTH;

		JLabel label_title = new JLabel("人事资源管理系统",JLabel.CENTER);
		label_title.setFont(new Font("宋体",0,30));
		gbci.gridx = 0;
		gbci.gridy = 0;
		gbci.gridwidth = 2;
		add(label_title,gbci);

		gbci.gridwidth = 1;
		gbci.gridy++;
		add(new JLabel("用户名：",JLabel.RIGHT),gbci);

		gbci.gridy++;
		add(new JLabel("密码：",JLabel.RIGHT),gbci);
		
		t_username = new JTextField();
		gbci.gridx++;
		gbci.gridy = 1;
		gbci.weightx = 1;
		add(t_username,gbci);
		
		t_password = new JPasswordField();
		gbci.gridy++;
		add(t_password,gbci);
		
		panel_submit = new JPanel();
		panel_submit.setLayout(new FlowLayout(FlowLayout.CENTER));
		gbci.gridy++;
		gbci.gridx = 0;
		gbci.gridwidth = 2;

		btn_login = new JButton("登陆");
		panel_submit.add(btn_login);

		/*
		btn_close = new JButton("退出");
		btn_close.setSize(50, 30);
		panel_submit.add(btn_close);//*/

		add(panel_submit,gbci);
		
	}
	
	private void setUpEventListener(){
		//btn_close.addActionListener(this);
		btn_login.addActionListener(this);
	}
	//--------------------Event processor------------------
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btn_login){
			/*
			if(t_username.getText().equals("admin") && (new String(t_password.getPassword())).equals("000000")){
				Logger.getLogger().logInfomation("Login Successful");
				MainForm.getForm().setVisible(true);
				dispose();
			}else{
				Logger.getLogger().logInfomation("Login Failed. Username&Password in Test Mode:\nadmin 000000");
			}
			*/
			//Login button action
			if(sqlProxy.checkUser(t_username.getText(),new String(t_password.getPassword()))){
				Logger.getLogger().logInfomation("Login Successful");
				mainForm.setVisible(true);
				this.dispose();
			}else{
                JOptionPane.showMessageDialog(this,"登入失败，请检查用户名和密码","登录",JOptionPane.ERROR_MESSAGE);
				Logger.getLogger().logInfomation("Login Failed!");
			}//*/
		}
	}
}