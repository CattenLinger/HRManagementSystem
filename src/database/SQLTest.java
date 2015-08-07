package database;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by catten on 15/7/6.
 */
public class SQLTest {
    public static void main(String[] args) throws SQLException {
        /*
        SQLProxy sqlProxy = SQLProxy.getProxy();
        ArrayList<Object[]> deps = sqlProxy.proxyQuery("select ID,Name from DepartmentInfo");
        Object[] depsrow;
        int[] depIDList = new int[deps.size()];
        //String[] deptitles = new String[deps.size()];
        for (int i = 0; i < deps.size(); i++) {
            depsrow = deps.get(i);
            depIDList[i] = (int)depsrow[0];
            System.out.printf(String.format("%d - %s",depIDList[i],depsrow[1].toString()));
        }//*/
        String address = "localhost";
        Connection connection = DriverManager.getConnection(
                String.format("jdbc:mysql://%s:3306/HRDatabase", address),
                "cocoa",
                "2580-3748"
        );

        Statement statement = connection.createStatement();
        //ResultSet resultSet = statement.executeQuery("show variables like \"%character%\"");
        //ResultSet resultSet = statement.executeQuery("select ID,Name from DepartmentInfo");
        //ResultSet resultSet = statement.executeQuery("select Username from SystemUsers where ID = 1");
        //ResultSet resultSet = statement.executeQuery("select ID from SystemUsers where username = 'catten'");
        ResultSet resultSet = statement.executeQuery("select id,name,details from staffinfo where ID = 1");
        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++){
            while (resultSet.next()){
                for (int j = 1; j <= resultSet.getMetaData().getColumnCount(); j++) {
                    System.out.printf("%s \t", resultSet.getString(j));
                }
                System.out.println();
                //System.out.printf("%s %s\n",resultSet.getString(1),resultSet.getString(2));
            }
        }
    }
}
