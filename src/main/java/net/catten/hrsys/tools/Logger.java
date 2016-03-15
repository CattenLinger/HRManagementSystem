package net.catten.hrsys.tools;

import java.sql.SQLException;

/**
 * Created by catten on 15/7/3.
 */
public class Logger {
    private static Logger logger;

    public static Logger getLogger() {
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }

    public Logger(){

    }

    public boolean logInfomation(String infomation){
        System.out.println("[Infomation]");
        System.out.println(infomation);
        return true;
    }

    public boolean logExceptions(SQLException e){
        System.out.println("[Error!]");
        System.out.printf(
                "SQLException: %s\nSQLState: %s\nVendorError: %s\n",
                e.getMessage(),
                e.getSQLState(),
                e.getErrorCode()
        );
        return true;
    }
}
