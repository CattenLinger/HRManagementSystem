package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MainForm extends JFrame implements ActionListener{

	private static MainForm mf;
	JDesktopPane desktopPane;
	public static Insets insets = new Insets(4,4,4,4);

	//database.SQLServerConnector sqlServerConnector = SQLServerConnector.getObject();

	JMenuBar menu;

	JMenu menu_department;//部门管理菜单
	JMenu menu_staff;//员工管理菜单
	JMenu menu_attendance;//考勤管理菜单
	JMenu menu_users;//用户管理菜单
	JMenu menu_other;//其他菜单
	//部门管理子菜单
	JMenuItem menu_adddep;
	JMenuItem menu_editdep;
	JMenuItem menu_removedep;
	JMenuItem menu_chkdep;
	//员工管理子菜单
	JMenuItem menu_addstaff;
	JMenuItem menu_editstaff;
	JMenuItem menu_removestaff;
	JMenuItem menu_checkstaff;
	//考勤管理子菜单
	JMenuItem menu_addattend;
	JMenuItem menu_detailattendmanage;
	JMenuItem menu_manageattend;
	//用户管理子菜单
	JMenuItem menu_adduser;
	JMenuItem menu_edituser;
	JMenuItem menu_removeuser;
	JMenuItem menu_checkusers;
	//其他子菜单
		//窗口管理菜单
		JMenu menu_windows;
		JMenuItem menu_managewindows;
		JMenuItem menu_closeall;
	JMenuItem menu_aboutus;
	JMenuItem menu_logout;
	
	private MainForm(String title){
		super(title);
		setSize(800,600);
		setLocationRelativeTo(null);
		setUpUIComponents();
		setUpEventListener();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static MainForm getForm(){
		if(mf == null){
			mf = new MainForm("人事资源管理系统");
		}

		return mf;
	}

	private void setUpUIComponents() {
		menu = new JMenuBar();
		
		menu_department = new JMenu("部门管理");
		menu.add(menu_department);
		menu_chkdep = new JMenuItem("查询部门");
		menu_adddep = new JMenuItem("添加部门");
		menu_editdep = new JMenuItem("编辑部门");
		menu_removedep = new JMenuItem("删除部门");
		menu_department.add(menu_chkdep);
		menu_department.add(new JPopupMenu.Separator());
		menu_department.add(menu_adddep);
		menu_department.add(menu_editdep);
		menu_department.add(menu_removedep);

		menu_staff = new JMenu("员工管理");
		menu.add(menu_staff);
		menu_addstaff = new JMenuItem("添加员工");
		menu_editstaff = new JMenuItem("更改员工数据");
		menu_removestaff = new JMenuItem("删除员工");
		menu_checkstaff = new JMenuItem("员工查询");
		menu_staff.add(menu_addstaff);
		menu_staff.add(menu_editstaff);
		menu_staff.add(menu_removestaff);
		menu_staff.add(new JPopupMenu.Separator());
		menu_staff.add(menu_checkstaff);
		
		menu_attendance = new JMenu("考勤管理");
		menu.add(menu_attendance);
		menu_addattend = new JMenuItem("考勤");
		menu_detailattendmanage = new JMenuItem("考勤详单");
		menu_manageattend = new JMenuItem("考勤汇总查询");
		menu_attendance.add(menu_addattend);
		menu_attendance.add(new JPopupMenu.Separator());
		menu_attendance.add(menu_detailattendmanage);
		//menu_attendance.add(menu_manageattend);
		
		menu_users = new JMenu("用户管理");
		menu.add(menu_users);
		menu_adduser = new JMenuItem("添加用户");
		menu_edituser = new JMenuItem("更改用户资料");
		menu_removeuser = new JMenuItem("删除用户");
		menu_checkusers = new JMenuItem("查询用户");
		menu_users.add(menu_adduser);
		menu_users.add(menu_edituser);
		menu_users.add(menu_removeuser);
		menu_users.add(new JPopupMenu.Separator());
		menu_users.add(menu_checkusers);
		
		menu_other = new JMenu("其他");
		menu.add(menu_other);
		menu_aboutus = new JMenuItem("关于这个软件");
		menu_logout = new JMenuItem("登出");
		menu_other.add(menu_aboutus);
		menu_other.add(new JPopupMenu.Separator());
		menu_other.add(menu_logout);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);

		setJMenuBar(menu);
		getContentPane().add(desktopPane, BorderLayout.CENTER);

	}

	private void setUpEventListener() {
		//Menu Listeners
		menu_chkdep.addActionListener(this);
		menu_adddep.addActionListener(this);
		menu_removedep.addActionListener(this);
		menu_editdep.addActionListener(this);

		menu_addstaff.addActionListener(this);
		menu_editstaff.addActionListener(this);
		menu_removestaff.addActionListener(this);
		menu_checkstaff.addActionListener(this);

		menu_addattend.addActionListener(this);
		menu_detailattendmanage.addActionListener(this);
		menu_manageattend.addActionListener(this);

		menu_adduser.addActionListener(this);
		menu_edituser.addActionListener(this);
		menu_removeuser.addActionListener(this);
		menu_checkusers.addActionListener(this);

		menu_aboutus.addActionListener(this);
		menu_logout.addActionListener(this);
	}
	//用于添加窗口到desktopPane并显示
	private void _add_show_forms(JInternalFrame inf){
		desktopPane.add(inf);
		inf.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		//Menu Events
		String menuSourceTitle = ((JMenuItem)e.getSource()).getText();
		if(e.getSource() == menu_chkdep) {
			//查询部门窗口弹出
			_add_show_forms(new CheckDepartmentForm(((JMenuItem)e.getSource()).getText()));
		}else if(e.getSource() == menu_adddep){
			//添加新部门窗口
			_add_show_forms(new AddDepartmantForm(((JMenuItem)e.getSource()).getText()));
		}else if(e.getSource() == menu_removedep) {
			//删除部门窗口
			_add_show_forms(new RemoveDepartmentForm(((JMenuItem)e.getSource()).getText()));
			/*
			String predel_depinfo = JOptionPane.showInputDialog(
					this,
					"请输入需要删除的部门的编号或者名称",
					"删除部门",
					JOptionPane.WARNING_MESSAGE
			);
			if (!(predel_depinfo == null || predel_depinfo.isEmpty())) {
				if (JOptionPane.showConfirmDialog(this, String.format("确定删除部门 %s 吗？", predel_depinfo), "删除部门",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
					System.out.print("Delete department action\n");
				}
			}
			//*/
		}else if (e.getSource() == menu_editdep){
			//编辑部门数据窗口弹出
			_add_show_forms(new EditDepartmentForm(menuSourceTitle));
		}else if(e.getSource() == menu_addstaff) {
			//添加员工窗口弹出
			_add_show_forms(new AddStaffForm(menuSourceTitle));
		}else if (e.getSource() == menu_removestaff){
			//删除员工窗口弹出
			_add_show_forms(new RemoveStaffForm(menuSourceTitle));
		}else if(e.getSource() == menu_checkstaff) {
			//查询员工窗口弹出
			_add_show_forms(new CheckStaffForm(menuSourceTitle));
		}else if (e.getSource() == menu_editstaff){
			//修改员工数据窗口弹出
			_add_show_forms(new EditStaffForm(menuSourceTitle));
		}else if(e.getSource() == menu_manageattend) {
			//管理出勤窗口弹出
			_add_show_forms(new CheckAttendForm(menuSourceTitle));
		}else if (e.getSource() == menu_detailattendmanage){
			//考勤详单窗口弹出
			_add_show_forms(new CheckDetailAttendForm(menuSourceTitle));
		}else if(e.getSource() == menu_addattend){
			//添加考勤记录窗口
			_add_show_forms(new AddAttendForm(menuSourceTitle));
		}else if(e.getSource() == menu_adduser){
			//添加用户窗口弹出
			_add_show_forms(new AddUserForm(menuSourceTitle));
		}else if(e.getSource() == menu_edituser) {
			//更改密码窗口弹出
			_add_show_forms(new EditUserForm(menuSourceTitle));
		}else if(e.getSource() == menu_removeuser){
			//删除用户窗口弹出
			_add_show_forms(new RemoveUserForm(menuSourceTitle));
		}else if(e.getSource() == menu_checkusers){
			//查询用户窗口弹出
			_add_show_forms(new CheckUserForm(menuSourceTitle));
		}else if(e.getSource() == menu_aboutus){
			//关于程序 对话框
			JOptionPane.showMessageDialog(
					null,
					"HRManagementSystem\n人事资源管理系统\n\n",
					menuSourceTitle,
					JOptionPane.INFORMATION_MESSAGE
			);
		}else if(e.getSource() == menu_logout){
			//退出登录
			if(JOptionPane.showConfirmDialog(this, "确定退出登录？", "登出", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)
					== JOptionPane.YES_OPTION) {
				this.setVisible(false);
				for(JInternalFrame i : desktopPane.getAllFrames()){
					i.dispose();
				}
				(new LoginForm("重新登录")).setVisible(true);
			}
		}
	}
}
