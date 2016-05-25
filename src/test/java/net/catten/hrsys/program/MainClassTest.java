package net.catten.hrsys.program;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by catten on 16/5/25.
 */
public class MainClassTest {

    @Test
    public void JDBCDriverTest() throws ClassNotFoundException, SQLException {
        /*
        * 我更改了一下我的JDK为1.8，然后就测试通过了。这里是问题什锦
        * http://stackoverflow.com/questions/6865538/solving-a-communications-link-failure-with-jdbc-and-mysql
        * */
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/HRDatabase",
                "cocoa",
                "25803748");
        assertNotNull(connection);
    }

}