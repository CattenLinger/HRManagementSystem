package database;

import tools.Logger;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by catten on 15/7/3.
 */
public class MySQLConnector implements SQLConnector{
    private static MySQLConnector mySQLConnector;
    private String address;
    private Logger logger = Logger.getLogger();

    private Connection connection;
    private Statement statement;

    private MySQLConnector(){
        address = "localhost";
        //address = "192.168.21.54";
        Connect();
    }

    public static MySQLConnector getObject(){
        if(mySQLConnector == null){
            mySQLConnector = new MySQLConnector();
        }
        return mySQLConnector;
    }

    private void Connect() {
        try{

            connection = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s:3306/HRDatabase",address),
                    "cocoa",
                    "2580-3748"
            );
            //*/
            /*
            connection = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s:3306/HRDatabase?useUnicode=true&amp;characterEncoding=UTF8",address),
                    "cocoa",
                    "2580-3748"
            );
            //*/
            /*
            connection = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s:3306/HRDatabase?useUnicode=true&amp;characterEncoding=WINDOWS-1252",address),
                    "cocoa",
                    "2580-3748"
            );//*/
            logger.logInfomation("Encoding set successful.");
            statement = connection.createStatement();
            logger.logInfomation("Database connect successful.");
            //statement.execute("set names utf8");
            //logger.logInfomation("Set Encoding successful.");
        }catch (SQLException e){
            logger.logExceptions(e);
        }
    }

    @Override
    public Statement getStatement() {
        return statement;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
