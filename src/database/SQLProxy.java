package database;

import tools.Logger;

import java.io.UnsupportedEncodingException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by catten on 15/7/3.
 */
public class SQLProxy {
    //验证用户登陆
    private Statement statement;
    private static SQLProxy sqlProxy;
    private Logger logger = Logger.getLogger();

    public static SQLProxy getProxy(){
        if(sqlProxy == null){
            sqlProxy = new SQLProxy();
        }
        return sqlProxy;
    }

    private SQLProxy(){
        this.statement = MySQLConnector.getObject().getStatement();
    }

    //登陆验证专用检查过程
    public boolean checkUser(String username,String password){
        try{
            ResultSet resultSet = statement.executeQuery(
                    String.format("select * from SystemUsers where Username='%s'",username)
            );
            resultSet.next();
            if (resultSet.getString("password").replace(" ","").equals(password.replace(" ",""))){
                //System.out.printf("%s %s\n",resultSet.getString("username"),resultSet.getString("password"));
                return true;
            }
        }catch (SQLException e){
            System.out.print("User info update failed.\n");
            logger.logExceptions(e);
        }
        return false;
    }

    public static int ERROR_EXIST = 1;
    public int proxyExcute(String sqlCom){
        logger.logInfomation(sqlCom);
        try{
            statement.executeUpdate(sqlCom);//utf8_general_ci || WINDOWS-1252
        }catch (SQLException e){
            logger.logExceptions(e);
            return 1;
        }
        return 0;
    }

    public ArrayList<Object[]> proxyQuery(String sqlCom){
        logger.logInfomation(sqlCom);
        try{
            ArrayList<Object[]> arrayList = new ArrayList();
            ResultSet resultSet = statement.executeQuery(sqlCom);
            int columnCount = resultSet.getMetaData().getColumnCount();
            logger.logInfomation(String.format("%d Columns",columnCount));
            Object[] row = new Object[columnCount];
            while (resultSet.next()){
                for(int i = 0; i < columnCount;i++){
                    System.out.printf("%s \t", resultSet.getObject(i + 1));
                    row[i] = resultSet.getObject(i + 1);
                }
                System.out.println();
                arrayList.add(row.clone());
            }
            if(arrayList.size() == 0){
                return null;
            }
            return arrayList;
        }catch (SQLException e){
            logger.logExceptions(e);
            return null;
        }
    }

    public String checkUserByID(int userid){
        try{
            ResultSet resultSet=statement.executeQuery(
                    String.format("select Username from SystemUsers where ID = %d",userid)
            );
            if(!resultSet.next()){ return null; }
            System.out.printf("checkUserByID: %s\n",resultSet.getString("Username"));
            return resultSet.getString("Username");
        }catch (SQLException e){
            logger.logExceptions(e);
            return null;
        }
    }

    public int checkUserByName(String username){
        try{
            ResultSet resultSet = statement.executeQuery(
                    String.format("select ID from SystemUsers where Username = '%s'",username)
            );
            if(!resultSet.next()){ return -1; }
            System.out.println(String.format("checkUserByName: %d\n",resultSet.getInt("ID")));
            return resultSet.getInt("ID");
        }catch (SQLException e){
            logger.logExceptions(e);
            return -1;
        }
    }
}
