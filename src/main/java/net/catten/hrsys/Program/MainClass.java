package net.catten.hrsys.Program; /**
 * Created by catten on 15/6/26.
 */
import javax.swing.UIManager;

import net.catten.hrsys.database.HibernateUtil;
import net.catten.hrsys.gui.LoginForm;

public class MainClass {
    //预加载数据库连接
    //SQLProxy sqlServerConnector = MySQLConnector.getObject();

    public static void main(String[] args){

        new LoginForm("登入").setVisible(true);
    }
}
