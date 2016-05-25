package net.catten.hrsys.gui;

import net.catten.hrsys.data.User;
import net.catten.hrsys.database.HibernateUtil;
import net.catten.hrsys.gui.util.CallBackAction;
import org.hibernate.Query;
import org.hibernate.Session;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LoginForm extends JFrame{
	//定义文本框
	JTextField t_username;
	//定义密码框
	JPasswordField t_password;
	//定义按钮
	JButton btn_login,btn_close;
	//放按钮的容器
	JPanel panel_submit;

    CallBackAction callBackAction = new CallBackAction() {
        @Override
        public void doAction() {

        }
    };

    public void setCallBackAction(CallBackAction callBackAction){
        this.callBackAction = callBackAction;
    }


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

		add(panel_submit,gbci);
		
	}
	
	private void setUpEventListener(){
		btn_close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btn_login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkLogin();
            }
        });
	}
	//--------------------Event processor------------------
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

    private void checkLogin(){
        String username = t_username.getText();
        String password = new String(t_password.getPassword());

        if(username.matches("\\w{0,16}") && password.matches("\\S{0,32}")){
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.getTransaction().begin();
            Query query = session.createQuery("select u from User u where u.username='"+username+"'");
            User passport = (User) query.uniqueResult();
            session.getTransaction().commit();
            if(passport != null){
                if(username.equals(passport.getUsername()) && password.equals(passport.getPassword())){
                    System.setProperty("passport",username);
                    callBackAction.doAction();
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(this,"验证失败");
                }
            }else{
                JOptionPane.showMessageDialog(this,"用户不存在");
            }
        }else{
            JOptionPane.showMessageDialog(this,"请输入正确的用户名和密码");
        }
    }
}