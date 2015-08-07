/**
 * Created by catten on 15/6/26.
 */
import javax.swing.UIManager;

import gui.LoginForm;

public class MainClass {
    //预加载数据库连接
    //SQLProxy sqlServerConnector = MySQLConnector.getObject();

    public static void main(String[] args){
        try{
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("org.gjt.mm.mysql.Driver");
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            System.out.print("Can't match theme for System.Default theme will be used.\n");
        }

        //new LoginForm("登入").setVisible(true);
        new LoginForm("登入").setVisible(true);
/*
        MainForm.getForm().setVisible(true);
        MainForm.getForm().setTitle("人事资源管理系统 -- In Test mode.");
        SQLProxy.getProxy();
        //*/
//      testConnection(serverAddress);
    }
/*
    public static boolean testConnection(String serverAddress){
        String serverAddress = "192.168.21.52";
        try {
            Connection connection = DriverManager.getConnection(
                    String.format("jdbc:sqlserver://%s:1433;databaseName=Test",serverAddress),
                    "sa",
                    "123456"
            );
            System.out.printf("Connect Successful!\n%s\n", connection);
            connection.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
*/
}
